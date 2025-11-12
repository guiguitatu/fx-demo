package com.example.fxdemo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HeaderController {
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovo;

    private CRUDController crudController;

    public void setCRUDController(CRUDController crudController) {
        this.crudController = crudController;
        setupButtonActions();
    }

    private void setupButtonActions() {
        if (crudController != null) {
            btnSalvar.setOnAction(e -> crudController.salvar());
            btnEditar.setOnAction(e -> crudController.editar());
            btnDeletar.setOnAction(e -> crudController.deletar());
            btnNovo.setOnAction(e -> crudController.novo());
        }
    }

    public void updateButtonStates(boolean salvarEnabled, boolean editarEnabled, boolean deletarEnabled) {
        btnSalvar.setDisable(!salvarEnabled);
        btnEditar.setDisable(!editarEnabled);
        btnDeletar.setDisable(!deletarEnabled);
    }
}

