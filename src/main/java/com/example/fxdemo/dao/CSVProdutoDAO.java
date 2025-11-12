package com.example.fxdemo.dao;

import com.example.fxdemo.model.Produto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CSVProdutoDAO {
    private static final String CSV_FILE = "produtos.csv";

    public CSVProdutoDAO() {
        // Cria o arquivo se não existir
        try {
            File file = new File(CSV_FILE);
            if (!file.exists()) {
                // Cria cabeçalho
                Files.write(Paths.get(CSV_FILE), "nome,preco,estoque\n".getBytes());
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo CSV: " + e.getMessage());
        }
    }

    public void inserir(Produto produto) {
        try {
            String linha = String.format("%s,%.2f,%d\n",
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoque());

            Files.write(Paths.get(CSV_FILE), linha.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Erro ao inserir no CSV: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                // Pular cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] colunas = linha.split(",");
                if (colunas.length >= 3) {
                    try {
                        String nome = colunas[0].trim();
                        double preco = Double.parseDouble(colunas[1].trim());
                        int estoque = Integer.parseInt(colunas[2].trim());

                        Produto produto = new Produto();
                        produto.setNome(nome);
                        produto.setPreco(preco);
                        produto.setEstoque(estoque);

                        produtos.add(produto);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao parsear linha do CSV: " + linha);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao listar produtos do CSV: " + e.getMessage());
        }

        return produtos;
    }

    public void atualizar(Produto produtoAntigo, Produto produtoNovo) {
        List<String> linhas = new ArrayList<>();

        try {
            // Lê todas as linhas
            linhas = Files.readAllLines(Paths.get(CSV_FILE));

            // Procura e substitui a linha
            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                if (i == 0) { // Cabeçalho
                    continue;
                }

                String[] colunas = linha.split(",");
                if (colunas.length >= 3) {
                    try {
                        String nome = colunas[0].trim();
                        double preco = Double.parseDouble(colunas[1].trim());
                        int estoque = Integer.parseInt(colunas[2].trim());

                        // Verifica se é o produto a ser atualizado
                        if (nome.equals(produtoAntigo.getNome()) &&
                            Math.abs(preco - produtoAntigo.getPreco()) < 0.01 &&
                            estoque == produtoAntigo.getEstoque()) {

                            // Substitui pela nova linha
                            linhas.set(i, String.format("%s,%.2f,%d",
                                produtoNovo.getNome(),
                                produtoNovo.getPreco(),
                                produtoNovo.getEstoque()));
                            break;
                        }
                    } catch (NumberFormatException e) {
                        // Ignora linhas mal formatadas
                    }
                }
            }

            // Reescreve o arquivo
            Files.write(Paths.get(CSV_FILE), linhas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Erro ao atualizar produto no CSV: " + e.getMessage());
        }
    }

    public void deletar(Produto produto) {
        List<String> linhas = new ArrayList<>();

        try {
            // Lê todas as linhas
            linhas = Files.readAllLines(Paths.get(CSV_FILE));

            // Remove a linha do produto
            linhas.removeIf(linha -> {
                if (linha.startsWith("nome,preco,estoque")) { // Cabeçalho
                    return false;
                }

                String[] colunas = linha.split(",");
                if (colunas.length >= 3) {
                    try {
                        String nome = colunas[0].trim();
                        double preco = Double.parseDouble(colunas[1].trim());
                        int estoque = Integer.parseInt(colunas[2].trim());

                        return nome.equals(produto.getNome()) &&
                               Math.abs(preco - produto.getPreco()) < 0.01 &&
                               estoque == produto.getEstoque();
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
                return false;
            });

            // Reescreve o arquivo
            Files.write(Paths.get(CSV_FILE), linhas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Erro ao deletar produto do CSV: " + e.getMessage());
        }
    }

    public void salvarTodosProdutos(List<Produto> produtos) {
        try {
            List<String> linhas = new ArrayList<>();
            linhas.add("nome,preco,estoque"); // Cabeçalho

            for (Produto produto : produtos) {
                linhas.add(String.format("%s,%.2f,%d",
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getEstoque()));
            }

            Files.write(Paths.get(CSV_FILE), linhas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos no CSV: " + e.getMessage());
        }
    }
}

