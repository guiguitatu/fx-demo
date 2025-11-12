package com.example.fxdemo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.util.Arrays;

public class FXController {

    // Labels
    @FXML private Label lblBasico, lblStyled, lblResultado;

    // Buttons
    @FXML private Button btnNormal, btnDisabled, btnStyled;

    // Text Input Controls
    @FXML private TextField txtCampo, txtComPrompt, txtSomenteLeitura;
    @FXML private PasswordField pwdSenha;
    @FXML private TextArea txtArea;

    // Selection Controls
    @FXML private ComboBox<String> cmbOpcoes;
    @FXML private CheckBox chkOpcao1, chkOpcao2, chkOpcao3;
    @FXML private RadioButton rbOpcao1, rbOpcao2, rbOpcao3;
    @FXML private ToggleGroup grupoRadio;

    // Progress and Indicators
    @FXML private ProgressBar progressBar;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Slider slider;

    // Date and Color Pickers
    @FXML private DatePicker datePicker;
    @FXML private ColorPicker colorPicker;

    // TableView Demo
    @FXML private TableView<Pessoa> tableView;
    @FXML private TableColumn<Pessoa, String> colNome;
    @FXML private TableColumn<Pessoa, Integer> colIdade;
    @FXML private TableColumn<Pessoa, String> colCidade;

    // Spinner
    @FXML private Spinner<Integer> spinner;

    // ListView
    @FXML private ListView<String> listView;

    // ScrollPane
    @FXML private ScrollPane scrollPane;

    // TabPane
    @FXML private TabPane tabPane;
    @FXML private Tab tab1, tab2, tab3;

    // Accordion
    @FXML private Accordion accordion;
    @FXML private TitledPane pane1, pane2;

    // MenuBar
    @FXML private MenuBar menuBar;
    @FXML private Menu menuArquivo, menuEditar, menuAjuda;
    @FXML private MenuItem menuItemNovo, menuItemAbrir, menuItemSalvar;

    // ToolBar
    @FXML private ToolBar toolBar;

    // Separator
    @FXML private Separator separator;

    // StatusBar simulation
    @FXML private Label lblStatus;

    private ObservableList<Pessoa> pessoas;

    // Classe interna para demonstrar TableView
    public static class Pessoa {
        private String nome;
        private int idade;
        private String cidade;

        public Pessoa(String nome, int idade, String cidade) {
            this.nome = nome;
            this.idade = idade;
            this.cidade = cidade;
        }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public int getIdade() { return idade; }
        public void setIdade(int idade) { this.idade = idade; }

        public String getCidade() { return cidade; }
        public void setCidade(String cidade) { this.cidade = cidade; }
    }

    @FXML
    public void initialize() {
        configurarLabels();
        configurarButtons();
        configurarTextControls();
        configurarSelectionControls();
        configurarProgressControls();
        configurarPickers();
        configurarTableView();
        configurarSpinner();
        configurarListView();
        configurarAccordion();
        configurarMenus();
        lblStatus.setText("Sistema inicializado - Demonstração de Componentes JavaFX");
    }

    private void configurarLabels() {
        lblStyled.setStyle("-fx-text-fill: #007bff; -fx-font-size: 14px; -fx-font-weight: bold;");
        lblResultado.setText("Componentes carregados com sucesso!");
        lblResultado.setStyle("-fx-text-fill: #28a745;");
    }

    private void configurarButtons() {
        btnDisabled.setDisable(true);
        btnStyled.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");

        btnNormal.setOnAction(e -> lblResultado.setText("Botão normal clicado!"));
        btnStyled.setOnAction(e -> lblResultado.setText("Botão estilizado clicado!"));
    }

    private void configurarTextControls() {
        txtComPrompt.setPromptText("Digite algo aqui...");
        txtSomenteLeitura.setText("Este campo é somente leitura");
        txtSomenteLeitura.setEditable(false);

        pwdSenha.setPromptText("Digite sua senha");

        txtArea.setPromptText("Digite um texto longo aqui...\n\nEsta é uma TextArea para textos maiores.");
        txtArea.setWrapText(true);

        // Listener para atualizar resultado
        txtCampo.textProperty().addListener((obs, oldText, newText) ->
            lblResultado.setText("Texto digitado: " + newText));
    }

    private void configurarSelectionControls() {
        // ComboBox
        ObservableList<String> opcoes = FXCollections.observableArrayList(
            "Opção 1", "Opção 2", "Opção 3", "Opção 4", "Opção 5");
        cmbOpcoes.setItems(opcoes);
        cmbOpcoes.setValue("Selecione uma opção");
        cmbOpcoes.setOnAction(e -> lblResultado.setText("ComboBox: " + cmbOpcoes.getValue()));

        // CheckBoxes
        chkOpcao1.setOnAction(e -> atualizarResultadoCheckBoxes());
        chkOpcao2.setOnAction(e -> atualizarResultadoCheckBoxes());
        chkOpcao3.setOnAction(e -> atualizarResultadoCheckBoxes());

        // RadioButtons
        grupoRadio = new ToggleGroup();
        rbOpcao1.setToggleGroup(grupoRadio);
        rbOpcao2.setToggleGroup(grupoRadio);
        rbOpcao3.setToggleGroup(grupoRadio);

        grupoRadio.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                lblResultado.setText("RadioButton selecionado: " + selected.getText());
            }
        });
    }

    private void atualizarResultadoCheckBoxes() {
        StringBuilder resultado = new StringBuilder("CheckBoxes: ");
        if (chkOpcao1.isSelected()) resultado.append("Op1 ");
        if (chkOpcao2.isSelected()) resultado.append("Op2 ");
        if (chkOpcao3.isSelected()) resultado.append("Op3 ");
        lblResultado.setText(resultado.toString().trim());
    }

    private void configurarProgressControls() {
        progressBar.setProgress(0.7); // 70%
        progressIndicator.setProgress(0.7);

        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(70);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double progresso = newVal.doubleValue() / 100.0;
            progressBar.setProgress(progresso);
            progressIndicator.setProgress(progresso);
            lblResultado.setText(String.format("Progresso: %.1f%%", newVal.doubleValue()));
        });
    }

    private void configurarPickers() {
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(e -> lblResultado.setText("Data selecionada: " +
            datePicker.getValue().toString()));

        colorPicker.setValue(Color.BLUE);
        colorPicker.setOnAction(e -> {
            Color cor = colorPicker.getValue();
            lblResultado.setText(String.format("Cor selecionada: RGB(%.0f, %.0f, %.0f)",
                cor.getRed() * 255, cor.getGreen() * 255, cor.getBlue() * 255));
            lblResultado.setTextFill(cor);
        });
    }

    private void configurarTableView() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        pessoas = FXCollections.observableArrayList(
            new Pessoa("João Silva", 25, "São Paulo"),
            new Pessoa("Maria Santos", 30, "Rio de Janeiro"),
            new Pessoa("Pedro Oliveira", 35, "Belo Horizonte"),
            new Pessoa("Ana Costa", 28, "Porto Alegre"),
            new Pessoa("Carlos Lima", 42, "Salvador")
        );

        tableView.setItems(pessoas);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblResultado.setText("Pessoa selecionada: " + newSelection.getNome());
            }
        });
    }

    private void configurarSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50);
        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener((obs, oldVal, newVal) ->
            lblResultado.setText("Spinner: " + newVal));
    }

    private void configurarListView() {
        ObservableList<String> itens = FXCollections.observableArrayList(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7");
        listView.setItems(itens);
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
            lblResultado.setText("ListView: " + newVal));
    }

    private void configurarAccordion() {
        // Accordion já está configurado no FXML
        accordion.setExpandedPane(pane1);
    }

    private void configurarMenus() {
        menuItemNovo.setOnAction(e -> lblResultado.setText("Menu: Novo clicado"));
        menuItemAbrir.setOnAction(e -> lblResultado.setText("Menu: Abrir clicado"));
        menuItemSalvar.setOnAction(e -> lblResultado.setText("Menu: Salvar clicado"));
    }

    // Métodos para demonstração de eventos
    @FXML
    private void onBtnLimparClick() {
        txtCampo.clear();
        pwdSenha.clear();
        txtArea.clear();
        cmbOpcoes.setValue("Selecione uma opção");
        chkOpcao1.setSelected(false);
        chkOpcao2.setSelected(false);
        chkOpcao3.setSelected(false);
        grupoRadio.selectToggle(null);
        slider.setValue(0);
        datePicker.setValue(LocalDate.now());
        colorPicker.setValue(Color.BLUE);
        spinner.getValueFactory().setValue(0);
        listView.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
        lblResultado.setText("Campos limpos!");
        lblResultado.setTextFill(Color.BLACK);
    }

    @FXML
    private void onBtnDemonstracaoClick() {
        lblResultado.setText("Demonstrando todos os componentes JavaFX!");
        // Animação simples
        new Thread(() -> {
            try {
                Thread.sleep(500);
                lblResultado.setText("Labels ✓");
                Thread.sleep(500);
                lblResultado.setText("Buttons ✓");
                Thread.sleep(500);
                lblResultado.setText("TextFields ✓");
                Thread.sleep(500);
                lblResultado.setText("ComboBox ✓");
                Thread.sleep(500);
                lblResultado.setText("CheckBox ✓");
                Thread.sleep(500);
                lblResultado.setText("RadioButton ✓");
                Thread.sleep(500);
                lblResultado.setText("ProgressBar ✓");
                Thread.sleep(500);
                lblResultado.setText("TableView ✓");
                Thread.sleep(500);
                lblResultado.setText("Todos os componentes funcionando! 🎉");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
