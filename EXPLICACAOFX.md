# 🎨 EXPLICAÇÃO DA TELA FX - Componentes JavaFX

Este documento explica detalhadamente a **tela FX** (`fx-view.fxml` e `FXController.java`), que demonstra todos os componentes JavaFX mais utilizados em uma única interface interativa.

## 📁 Arquivos Relacionados

- **`fx-view.fxml`** - Layout declarativo com todos os componentes
- **`FXController.java`** - Lógica de controle e interatividade

## 🏗️ Arquitetura da Tela FX

### Estrutura Visual (ScrollPane)
```xml
<ScrollPane fitToWidth="true">
    <VBox spacing="20.0">
        <!-- Título, Barra de Status, Componentes organizados em seções -->
    </VBox>
</ScrollPane>
```

A tela usa um `ScrollPane` para permitir rolagem vertical, organizando os componentes em seções `VBox` com espaçamento consistente.

## 🎯 Componentes Demonstrados

### 1. **Labels** - Exibição de Texto

```java
// FXController.java
@FXML private Label lblBasico, lblStyled, lblResultado;

private void configurarLabels() {
    lblStyled.setStyle("-fx-text-fill: #007bff; -fx-font-size: 14px; -fx-font-weight: bold;");
    lblResultado.setText("Componentes carregados com sucesso!");
}
```

**Funcionalidades:**
- Label básico para texto simples
- Label estilizado com CSS inline
- Label dinâmico que mostra resultados das interações

### 2. **Buttons** - Botões Interativos

```java
@FXML private Button btnNormal, btnDisabled, btnStyled;

private void configurarButtons() {
    btnDisabled.setDisable(true);
    btnStyled.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");

    btnNormal.setOnAction(e -> lblResultado.setText("Botão normal clicado!"));
    btnStyled.setOnAction(e -> lblResultado.setText("Botão estilizado clicado!"));
}
```

**Demonstra:**
- Botão normal funcional
- Botão desabilitado (não clicável)
- Botão estilizado com cores customizadas
- Conexão de eventos `setOnAction()`

### 3. **Text Input Controls** - Entrada de Texto

```java
@FXML private TextField txtCampo, txtComPrompt, txtSomenteLeitura;
@FXML private PasswordField pwdSenha;
@FXML private TextArea txtArea;

private void configurarTextControls() {
    txtComPrompt.setPromptText("Digite algo aqui...");
    txtSomenteLeitura.setEditable(false);
    txtArea.setWrapText(true);

    txtCampo.textProperty().addListener((obs, oldText, newText) ->
        lblResultado.setText("Texto digitado: " + newText));
}
```

**Componentes:**
- **TextField**: Campo de texto básico
- **TextField com Prompt**: Texto de dica que desaparece ao digitar
- **TextField Somente Leitura**: Campo não editável
- **PasswordField**: Campo para senhas (máscara os caracteres)
- **TextArea**: Área de texto multilinha com quebra automática

### 4. **Selection Controls** - Controles de Seleção

```java
@FXML private ComboBox<String> cmbOpcoes;
@FXML private CheckBox chkOpcao1, chkOpcao2, chkOpcao3;
@FXML private RadioButton rbOpcao1, rbOpcao2, rbOpcao3;
@FXML private ToggleGroup grupoRadio;

private void configurarSelectionControls() {
    // ComboBox
    ObservableList<String> opcoes = FXCollections.observableArrayList(
        "Opção 1", "Opção 2", "Opção 3", "Opção 4", "Opção 5");
    cmbOpcoes.setItems(opcoes);

    // RadioButtons com ToggleGroup
    grupoRadio = new ToggleGroup();
    rbOpcao1.setToggleGroup(grupoRadio);
    rbOpcao2.setToggleGroup(grupoRadio);
    rbOpcao3.setToggleGroup(grupoRadio);
}
```

**Funcionalidades:**
- **ComboBox**: Lista dropdown para seleção única
- **CheckBox**: Caixas de seleção múltipla independentes
- **RadioButton**: Seleção única com ToggleGroup
- Eventos dinâmicos que atualizam o resultado

### 5. **Progress Controls** - Barras de Progresso

```java
@FXML private ProgressBar progressBar;
@FXML private ProgressIndicator progressIndicator;
@FXML private Slider slider;

private void configurarProgressControls() {
    progressBar.setProgress(0.7); // 70%
    progressIndicator.setProgress(0.7);

    slider.valueProperty().addListener((obs, oldVal, newVal) -> {
        double progresso = newVal.doubleValue() / 100.0;
        progressBar.setProgress(progresso);
        progressIndicator.setProgress(progresso);
        lblResultado.setText(String.format("Progresso: %.1f%%", newVal.doubleValue()));
    });
}
```

**Componentes:**
- **ProgressBar**: Barra horizontal de progresso
- **ProgressIndicator**: Indicador circular de progresso
- **Slider**: Controle deslizante que atualiza ambos os indicadores

### 6. **Pickers** - Seletores Especiais

```java
@FXML private DatePicker datePicker;
@FXML private ColorPicker colorPicker;
@FXML private Spinner<Integer> spinner;

private void configurarPickers() {
    datePicker.setValue(LocalDate.now());
    datePicker.setOnAction(e -> lblResultado.setText("Data: " + datePicker.getValue()));

    colorPicker.setValue(Color.BLUE);
    colorPicker.setOnAction(e -> {
        Color cor = colorPicker.getValue();
        lblResultado.setText(String.format("Cor: RGB(%.0f, %.0f, %.0f)",
            cor.getRed() * 255, cor.getGreen() * 255, cor.getBlue() * 255));
        lblResultado.setTextFill(cor);
    });

    SpinnerValueFactory<Integer> valueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50);
    spinner.setValueFactory(valueFactory);
}
```

**Funcionalidades:**
- **DatePicker**: Calendário para seleção de datas
- **ColorPicker**: Paleta de cores com preview
- **Spinner**: Controle numérico com botões + e -

### 7. **TableView** - Tabela de Dados

```java
@FXML private TableView<Pessoa> tableView;
@FXML private TableColumn<Pessoa, String> colNome;
@FXML private TableColumn<Pessoa, Integer> colIdade;
@FXML private TableColumn<Pessoa, String> colCidade;

public static class Pessoa {
    // Classe interna para dados da tabela
}

private void configurarTableView() {
    colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
    colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

    pessoas = FXCollections.observableArrayList(
        new Pessoa("João Silva", 25, "São Paulo"),
        // ... mais dados
    );
    tableView.setItems(pessoas);

    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            lblResultado.setText("Pessoa selecionada: " + newSelection.getNome());
        }
    });
}
```

**Características:**
- Tabela populada com dados reais
- Colunas configuradas com `PropertyValueFactory`
- Seleção de linhas com listener
- Classe interna `Pessoa` para modelar dados

### 8. **ListView** - Lista de Itens

```java
@FXML private ListView<String> listView;

private void configurarListView() {
    ObservableList<String> itens = FXCollections.observableArrayList(
        "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7");
    listView.setItems(itens);

    listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        lblResultado.setText("ListView: " + newVal));
}
```

### 9. **Accordion & TabPane** - Controles de Layout

```xml
<!-- Accordion -->
<Accordion fx:id="accordion">
    <panes>
        <TitledPane fx:id="pane1" text="Painel 1">
            <Label text="Conteúdo do painel..."/>
        </TitledPane>
    </panes>
</Accordion>

<!-- TabPane -->
<TabPane fx:id="tabPane">
    <tabs>
        <Tab fx:id="tab1" text="Aba 1" closable="false">
            <Label text="Conteúdo da aba"/>
        </Tab>
    </tabs>
</TabPane>
```

### 10. **MenuBar & ToolBar** - Barras de Menu

```xml
<MenuBar fx:id="menuBar">
    <menus>
        <Menu fx:id="menuArquivo" text="Arquivo">
            <items>
                <MenuItem fx:id="menuItemNovo" text="Novo"/>
                <MenuItem fx:id="menuItemAbrir" text="Abrir"/>
            </items>
        </Menu>
    </menus>
</MenuBar>

<ToolBar fx:id="toolBar">
    <items>
        <Button text="🔧"/>
        <Button text="💾"/>
        <Separator/>
        <Button text="🔍"/>
    </items>
</ToolBar>
```

## 🎮 Funcionalidades Interativas

### Barra de Status
- Mostra status da aplicação
- Botões "🚀 Demonstração" e "🧹 Limpar"

### Demonstração Automática
```java
@FXML
private void onBtnDemonstracaoClick() {
    new Thread(() -> {
        lblResultado.setText("Demonstrando Labels ✓");
        Thread.sleep(500);
        lblResultado.setText("Buttons ✓");
        // ... sequência animada
        lblResultado.setText("Todos os componentes funcionando! 🎉");
    }).start();
}
```

### Limpeza Completa
```java
@FXML
private void onBtnLimparClick() {
    // Reseta todos os campos para valores iniciais
    txtCampo.clear();
    cmbOpcoes.setValue("Selecione uma opção");
    chkOpcao1.setSelected(false);
    // ... todos os outros campos
    lblResultado.setText("Campos limpos!");
}
```

## 🔧 Padrões de Implementação

### Injeção de Dependências FXML
```java
@FXML private ComponentType componentName;
```

### Configuração no Initialize
```java
@FXML
public void initialize() {
    configurarLabels();
    configurarButtons();
    // ... configurações específicas
}
```

### Listeners para Eventos Dinâmicos
```java
component.property().addListener((observable, oldValue, newValue) -> {
    // Reagir à mudança
    lblResultado.setText("Mudou: " + newValue);
});
```

### Organização Visual
- Cada tipo de componente em sua própria `VBox`
- Espaçamento consistente (`spacing="20.0"`)
- Bordas e backgrounds para separação visual
- Cores temáticas para diferentes seções

## 🎯 Objetivos Educacionais

Esta tela serve como:

1. **Referência Completa**: Todos os componentes JavaFX importantes
2. **Exemplo Prático**: Como conectar eventos e listeners
3. **Demonstração Interativa**: Feedback visual imediato
4. **Base de Aprendizado**: Código organizado e comentado
5. **Teste de Funcionalidades**: Verificar se todos os componentes funcionam

## 🚀 Como Usar

1. Execute a aplicação
2. Clique no botão "FX" na navegação
3. Explore cada seção de componentes
4. Clique no botão "🚀 Demonstração" para ver sequência animada
5. Use "🧹 Limpar" para resetar todos os campos
6. Observe como cada interação atualiza o label de resultado

A tela FX é uma demonstração abrangente de como o JavaFX funciona, mostrando desde os componentes mais simples até os mais complexos, todos funcionando de forma integrada e interativa.
