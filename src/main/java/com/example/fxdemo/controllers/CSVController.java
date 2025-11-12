package com.example.fxdemo.controllers;

import com.example.fxdemo.dao.CSVProdutoDAO;
import com.example.fxdemo.model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVController {
    @FXML
    private TextArea txtAreaCSV;
    @FXML
    private Button btnCarregarCSV;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovo;
    @FXML
    private TableView<Produto> tableView;
    @FXML
    private TableColumn<Produto, String> colNome;
    @FXML
    private TableColumn<Produto, Double> colPreco;
    @FXML
    private TableColumn<Produto, Integer> colEstoque;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtEstoque;

    private CSVProdutoDAO csvProdutoDAO;
    private ObservableList<Produto> produtos;
    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        csvProdutoDAO = new CSVProdutoDAO();
        produtos = FXCollections.observableArrayList();

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        // Formatar coluna de preço
        colPreco.setCellFactory(column -> new TableCell<Produto, Double>() {
            @Override
            protected void updateItem(Double preco, boolean empty) {
                super.updateItem(preco, empty);
                if (empty || preco == null) {
                    setText(null);
                } else {
                    setText(String.format("R$ %.2f", preco));
                }
            }
        });

        tableView.setItems(produtos);
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                produtoSelecionado = newSelection;
                if (newSelection != null) {
                    preencherCampos(newSelection);
                    btnSalvar.setDisable(true);
                    btnEditar.setDisable(false);
                    btnDeletar.setDisable(false);
                }
            }
        );

        carregarDados();
        limparCampos();
    }

    @FXML
    private void salvar() {
        if (validarCampos()) {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                int estoque = Integer.parseInt(txtEstoque.getText());
                Produto produto = new Produto(txtNome.getText(), preco, estoque);
                csvProdutoDAO.inserir(produto);
                carregarDados();
                limparCampos();
                mostrarAlerta("Sucesso", "Produto salvo no CSV com sucesso!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Preço e Estoque devem ser números válidos!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void editar() {
        if (produtoSelecionado != null && validarCampos()) {
            try {
                Produto produtoNovo = new Produto();
                produtoNovo.setNome(txtNome.getText());
                produtoNovo.setPreco(Double.parseDouble(txtPreco.getText()));
                produtoNovo.setEstoque(Integer.parseInt(txtEstoque.getText()));

                csvProdutoDAO.atualizar(produtoSelecionado, produtoNovo);
                carregarDados();
                limparCampos();
                mostrarAlerta("Sucesso", "Produto atualizado no CSV com sucesso!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Preço e Estoque devem ser números válidos!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void deletar() {
        if (produtoSelecionado != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText("Deseja realmente deletar este produto do CSV?");
            confirmacao.setContentText("Esta ação não pode ser desfeita.");

            confirmacao.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    csvProdutoDAO.deletar(produtoSelecionado);
                    carregarDados();
                    limparCampos();
                    mostrarAlerta("Sucesso", "Produto deletado do CSV com sucesso!", Alert.AlertType.INFORMATION);
                }
            });
        }
    }

    @FXML
    private void novo() {
        limparCampos();
        tableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void carregarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo CSV");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File arquivo = fileChooser.showOpenDialog(btnCarregarCSV.getScene().getWindow());

        if (arquivo != null) {
            try {
                lerArquivoCSVExterno(arquivo);
                mostrarAlerta("Sucesso", "Arquivo CSV carregado com sucesso!", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarAlerta("Erro", "Erro ao ler arquivo: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void lerArquivoCSVExterno(File arquivo) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        List<Produto> produtosExternos = csvProdutoDAO.listarTodos();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] colunas = linha.split(",");

                // Pular cabeçalho se existir
                if (primeiraLinha && (colunas[0].trim().equalsIgnoreCase("nome") ||
                    colunas[0].trim().equalsIgnoreCase("produto"))) {
                    primeiraLinha = false;
                    continue;
                }

                primeiraLinha = false;

                // Limpar espaços em branco
                for (int i = 0; i < colunas.length; i++) {
                    colunas[i] = colunas[i].trim();
                }

                // Tentar criar produto e adicionar à lista
                if (colunas.length >= 3) {
                    try {
                        String nome = colunas[0];
                        double preco = Double.parseDouble(colunas[1]);
                        int estoque = Integer.parseInt(colunas[2]);

                        Produto produto = new Produto();
                        produto.setNome(nome);
                        produto.setPreco(preco);
                        produto.setEstoque(estoque);
                        produtosExternos.add(produto);

                        // Formatar para exibição no TextArea
                        String linhaFormatada = String.format("Nome: %-20s | Preço: R$ %-10.2f | Estoque: %d",
                            nome, preco, estoque);
                        conteudo.append(linhaFormatada).append("\n");

                    } catch (NumberFormatException e) {
                        // Ignora linhas mal formatadas
                        conteudo.append("Linha mal formatada: ").append(linha).append("\n");
                    }
                }
            }
        }

        txtAreaCSV.setText(conteudo.toString());
        // Salva todos os produtos (incluindo os externos) no arquivo local
        csvProdutoDAO.salvarTodosProdutos(produtosExternos);
        carregarDados();
    }

    private void carregarDados() {
        produtos.clear();
        produtos.addAll(csvProdutoDAO.listarTodos());
    }

    private void preencherCampos(Produto produto) {
        txtNome.setText(produto.getNome());
        txtPreco.setText(String.valueOf(produto.getPreco()));
        txtEstoque.setText(String.valueOf(produto.getEstoque()));
    }

    private void limparCampos() {
        txtNome.clear();
        txtPreco.clear();
        txtEstoque.clear();
        produtoSelecionado = null;
        btnSalvar.setDisable(false);
        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "O campo Nome é obrigatório!", Alert.AlertType.ERROR);
            return false;
        }
        if (txtPreco.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "O campo Preço é obrigatório!", Alert.AlertType.ERROR);
            return false;
        }
        if (txtEstoque.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "O campo Estoque é obrigatório!", Alert.AlertType.ERROR);
            return false;
        }
        try {
            double preco = Double.parseDouble(txtPreco.getText());
            if (preco < 0) {
                mostrarAlerta("Erro", "O preço não pode ser negativo!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "O preço deve ser um número válido!", Alert.AlertType.ERROR);
            return false;
        }
        try {
            int estoque = Integer.parseInt(txtEstoque.getText());
            if (estoque < 0) {
                mostrarAlerta("Erro", "O estoque não pode ser negativo!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "O estoque deve ser um número inteiro válido!", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}

