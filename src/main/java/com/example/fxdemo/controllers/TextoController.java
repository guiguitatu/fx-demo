package com.example.fxdemo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TextoController {
    @FXML
    private Label textoLabel;

    @FXML
    public void initialize() {
        textoLabel.setText("aqui é uma outra tela");
    }
}

