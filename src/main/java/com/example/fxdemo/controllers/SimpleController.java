package com.example.fxdemo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimpleController {

    @FXML
    private Label lblTextoSuperior;

    @FXML
    private TextField txtInput;

    @FXML
    private Button btnTrocarTexto;

    @FXML
    private Label lblExplicacao;

    @FXML
    public void initialize() {
        // Inicializar com texto padrão
        lblTextoSuperior.setText("Texto inicial - digite algo no campo abaixo e clique no botão!");
        lblExplicacao.setText("Esta é uma demonstração simples de como o JavaFX escuta ações de botões.");

        // Configurar o botão para trocar o texto
        btnTrocarTexto.setOnAction(event -> {
            String textoDigitado = txtInput.getText().trim();

            if (textoDigitado.isEmpty()) {
                lblTextoSuperior.setText("Campo vazio! Digite algo primeiro.");
                lblTextoSuperior.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
            } else {
                lblTextoSuperior.setText("Você digitou: \"" + textoDigitado + "\"");
                lblTextoSuperior.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
            }
        });
    }

    // Método alternativo para demonstrar diferentes formas de conectar eventos
    @FXML
    private void onBtnLimparClick() {
        txtInput.clear();
        lblTextoSuperior.setText("Texto limpo! Digite algo novo.");
        lblTextoSuperior.setStyle("-fx-text-fill: #007bff; -fx-font-weight: normal;");
    }
}
