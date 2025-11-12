package com.example.fxdemo.controllers;

import com.example.fxdemo.dao.ProdutoDAO;
import com.example.fxdemo.model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class CRUDController {
    @FXML
    private TableView<Produto> tableView;
    @FXML
    private TableColumn<Produto, Integer> colId;
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

    @FXML
    private HBox header;

    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> produtos;
    private Produto produtoSelecionado;
    private HeaderController headerController;

    @FXML
    public void initialize() {
        produtoDAO = new ProdutoDAO();
        produtos = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                    if (headerController != null) {
                        headerController.updateButtonStates(false, true, true);
                    }
                }
            }
        );

        // Inicializar HeaderController
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/header-view.fxml"));
            HBox headerNode = loader.load();
            header.getChildren().clear();
            header.getChildren().add(headerNode);

            headerController = loader.getController();
            headerController.setCRUDController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        carregarDados();
        limparCampos();
    }

    public void salvar() {
        if (validarCampos()) {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                int estoque = Integer.parseInt(txtEstoque.getText());
                Produto produto = new Produto(txtNome.getText(), preco, estoque);
                produtoDAO.inserir(produto);
                carregarDados();
                limparCampos();
                mostrarAlerta("Sucesso", "Produto salvo com sucesso!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Preço e Estoque devem ser números válidos!", Alert.AlertType.ERROR);
            }
        }
    }

    public void editar() {
        if (produtoSelecionado != null && validarCampos()) {
            try {
                produtoSelecionado.setNome(txtNome.getText());
                produtoSelecionado.setPreco(Double.parseDouble(txtPreco.getText()));
                produtoSelecionado.setEstoque(Integer.parseInt(txtEstoque.getText()));
                produtoDAO.atualizar(produtoSelecionado);
                carregarDados();
                limparCampos();
                mostrarAlerta("Sucesso", "Produto atualizado com sucesso!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Preço e Estoque devem ser números válidos!", Alert.AlertType.ERROR);
            }
        }
    }

    public void deletar() {
        if (produtoSelecionado != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText("Deseja realmente deletar este produto?");
            confirmacao.setContentText("Esta ação não pode ser desfeita.");

            confirmacao.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    produtoDAO.deletar(produtoSelecionado.getId());
                    carregarDados();
                    limparCampos();
                    mostrarAlerta("Sucesso", "Produto deletado com sucesso!", Alert.AlertType.INFORMATION);
                }
            });
        }
    }

    public void novo() {
        limparCampos();
        tableView.getSelectionModel().clearSelection();
    }

    private void carregarDados() {
        produtos.clear();
        produtos.addAll(produtoDAO.listarTodos());
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
        if (headerController != null) {
            headerController.updateButtonStates(true, false, false);
        }
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

