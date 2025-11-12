package com.example.fxdemo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ButtonsController {

    @FXML
    private Label lblResultado;

    @FXML
    private Button btnVermelho, btnVerde, btnAzul, btnAmarelo, btnReset;

    @FXML
    private Label lblStatusVermelho, lblStatusVerde, lblStatusAzul, lblStatusAmarelo;

    @FXML
    public void initialize() {
        // Inicializar labels de status
        lblStatusVermelho.setText("Não clicado");
        lblStatusVerde.setText("Não clicado");
        lblStatusAzul.setText("Não clicado");
        lblStatusAmarelo.setText("Não clicado");

        lblResultado.setText("Clique nos botões coloridos para ver as ações!");

        // Configurar ações dos botões (método 1: diretamente no código)
        configurarBotoes();
    }

    private void configurarBotoes() {
        // Método 1: Usando setOnAction diretamente
        btnVermelho.setOnAction(event -> {
            lblStatusVermelho.setText("Clicado! ✅");
            lblStatusVermelho.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
            lblResultado.setText("Botão VERMELHO foi clicado!");
            lblResultado.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
        });

        btnVerde.setOnAction(event -> {
            lblStatusVerde.setText("Clicado! ✅");
            lblStatusVerde.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
            lblResultado.setText("Botão VERDE foi clicado!");
            lblResultado.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
        });

        btnAzul.setOnAction(event -> {
            lblStatusAzul.setText("Clicado! ✅");
            lblStatusAzul.setStyle("-fx-text-fill: #007bff; -fx-font-weight: bold;");
            lblResultado.setText("Botão AZUL foi clicado!");
            lblResultado.setStyle("-fx-text-fill: #007bff; -fx-font-weight: bold;");
        });

        btnAmarelo.setOnAction(event -> {
            lblStatusAmarelo.setText("Clicado! ✅");
            lblStatusAmarelo.setStyle("-fx-text-fill: #ffc107; -fx-font-weight: bold;");
            lblResultado.setText("Botão AMARELO foi clicado!");
            lblResultado.setStyle("-fx-text-fill: #ffc107; -fx-font-weight: bold;");
        });
    }

    // Método 2: Usando @FXML annotation (conectado via FXML)
    @FXML
    private void onBtnResetClick() {
        // Resetar todos os status
        lblStatusVermelho.setText("Não clicado");
        lblStatusVerde.setText("Não clicado");
        lblStatusAzul.setText("Não clicado");
        lblStatusAmarelo.setText("Não clicado");

        // Resetar estilos
        lblStatusVermelho.setStyle("-fx-text-fill: black;");
        lblStatusVerde.setStyle("-fx-text-fill: black;");
        lblStatusAzul.setStyle("-fx-text-fill: black;");
        lblStatusAmarelo.setStyle("-fx-text-fill: black;");

        // Resetar resultado
        lblResultado.setText("Todos os botões foram resetados!");
        lblResultado.setStyle("-fx-text-fill: #6c757d; -fx-font-weight: normal;");
    }

    // Método 3: Demonstração de diferentes tipos de eventos
    @FXML
    private void onBtnDemonstracaoClick() {
        lblResultado.setText("Demonstrando sequência de cliques...");

        // Simulação de cliques automáticos
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                btnVermelho.fire(); // Simula clique no botão vermelho
                Thread.sleep(1000);
                btnVerde.fire();   // Simula clique no botão verde
                Thread.sleep(1000);
                btnAzul.fire();    // Simula clique no botão azul
                Thread.sleep(1000);
                btnAmarelo.fire(); // Simula clique no botão amarelo
                Thread.sleep(1000);
                lblResultado.setText("Demonstração concluída! Todos os botões foram clicados automaticamente.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
