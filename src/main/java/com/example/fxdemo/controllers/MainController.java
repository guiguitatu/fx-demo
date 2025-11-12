package com.example.fxdemo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize() {
        // Carrega a tela de texto como padrão ao iniciar
        abrirTelaTexto();
    }

    @FXML
    private void abrirTelaTexto() {
        carregarTela("/texto-view.fxml");
    }

    @FXML
    private void abrirTelaCRUD() {
        carregarTela("/crud-view.fxml");
    }

    @FXML
    private void abrirTelaCSV() {
        carregarTela("/csv-view.fxml");
    }

    @FXML
    private void abrirTelaFX() {
        carregarTela("/fx-view.fxml");
    }

    @FXML
    private void abrirTelaSimples() {
        carregarTela("/simple-view.fxml");
    }

    @FXML
    private void abrirTelaBotoes() {
        carregarTela("/buttons-view.fxml");
    }

    private void carregarTela(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(fxmlPath));
            Parent root = loader.load();
            mainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

