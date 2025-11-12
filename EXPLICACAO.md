# 🔍 EXPLICAÇÃO DETALHADA - Como Funciona o FXdemo

Este documento explica passo a passo como o sistema JavaFX funciona, desde a inicialização até as interações do usuário, com foco especial na arquitetura de injeção de dependências e fluxo de eventos.

## 🚀 Inicialização da Aplicação

### Ponto de Entrada

Quando você executa `mvn javafx:run`, o JavaFX chama a classe `HelloApplication`:

```java
// HelloApplication.java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Carrega o arquivo FXML principal
        FXMLLoader fxmlLoader = new FXMLLoader(
            HelloApplication.class.getResource("/main-view.fxml"));

        // 2. Cria a cena com o layout carregado
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // 3. Configura a janela principal
        stage.setTitle("Aplicação JavaFX - Menu");
        stage.setScene(scene);
        stage.show();
    }
}
```

> **O que acontece aqui?**
> - O `FXMLLoader` lê o arquivo `main-view.fxml`
> - Cria todos os componentes JavaFX definidos no FXML
> - Instancia o `MainController` especificado no `fx:controller`
> - Injeta os componentes marcados com `fx:id` no controller

## 🏠 Tela Principal (MainController)

### Estrutura do Layout

O `main-view.fxml` define um `BorderPane` - um layout que divide a tela em regiões:

```xml
<BorderPane fx:id="mainPane">
    <top>
        <!-- Barra de navegação com botões -->
        <Button text="Texto" onAction="#abrirTelaTexto"/>
        <Button text="CRUD" onAction="#abrirTelaCRUD"/>
        <Button text="CSV" onAction="#abrirTelaCSV"/>
    </top>
    <!-- O centro fica vazio inicialmente -->
</BorderPane>
```

### Método Initialize

Quando o FXML é carregado, o JavaFX chama automaticamente o método `initialize()`:

```java
// MainController.java
@FXML
public void initialize() {
    // Carrega a tela de texto como padrão
    abrirTelaTexto();
}
```

### Como a Navegação Funciona

Quando você clica em "CRUD", acontece isso:

```java
@FXML
private void abrirTelaCRUD() {
    // Chama o método genérico
    carregarTela("/crud-view.fxml");
}

private void carregarTela(String fxmlPath) {
    try {
        // 1. Cria um novo loader para o FXML específico
        FXMLLoader loader = new FXMLLoader(
            MainController.class.getResource(fxmlPath));

        // 2. Carrega o layout (isto instancia o controller também!)
        Parent root = loader.load();

        // 3. Substitui o conteúdo do centro do BorderPane
        mainPane.setCenter(root);

    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

> **Importante:** Cada vez que `carregarTela()` é chamado, uma **nova instância** do controller é criada!

## 🎯 Tela CRUD (CRUDController)

### Carregamento da Tela

Quando `crud-view.fxml` é carregado, o JavaFX:

1. Cria o layout visual (VBox, TableView, TextFields, etc.)
2. Instancia o `CRUDController`
3. Injeta todos os componentes com `fx:id`
4. **Chama `initialize()` automaticamente**

### O Método Initialize Mais Importante

```java
// CRUDController.java
@FXML
public void initialize() {
    // 1. Inicializar DAO e lista observável
    produtoDAO = new ProdutoDAO();
    produtos = FXCollections.observableArrayList();

    // 2. Configurar TableView
    configurarColunas();

    // 3. ⚠️ INJEÇÃO DO HEADER AQUI! ⚠️
    injetarHeader();

    // 4. Carregar dados do banco
    carregarDados();

    // 5. Limpar campos (ativa botão Salvar)
    limparCampos();
}
```

## 🎪 **INJEÇÃO DO HEADER - O PONTO CHAVE!**

### Como o Header é "Injetado" Dinamicamente

```java
private void injetarHeader() {
    try {
        // 1. Criar um loader específico para o header
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/header-view.fxml"));

        // 2. Carregar o HBox do header (isto cria o HeaderController!)
        HBox headerNode = loader.load();

        // 3. Pegar referência do controller criado
        headerController = loader.getController();

        // 4. ⚠️ CONECTAR OS CONTROLLERS! ⚠️
        headerController.setCRUDController(this);

        // 5. Adicionar o header visualmente no layout
        header.getChildren().clear();
        header.getChildren().add(headerNode);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

### O que está acontecendo aqui?

1. **Carregamento Dinâmico**: O header não está "hardcoded" no FXML, é carregado em runtime
2. **Instanciação Separada**: HeaderController é criado separadamente do CRUDController
3. **Conexão Manual**: Usamos `setCRUDController(this)` para conectar os dois controllers
4. **Composição Visual**: O HBox do header é adicionado ao container `header` no layout

> **Por que isso é poderoso?** Permite reutilizar o Header em diferentes telas e conectar dinamicamente!

## 🎮 Como os Botões do Header Funcionam

### Configuração das Ações

```java
// HeaderController.java
public void setCRUDController(CRUDController crudController) {
    this.crudController = crudController;
    setupButtonActions(); // ⚠️ CONFIGURA AS AÇÕES AQUI!
}

private void setupButtonActions() {
    if (crudController != null) {
        // Conecta cada botão ao método correspondente
        btnSalvar.setOnAction(e -> crudController.salvar());
        btnEditar.setOnAction(e -> crudController.editar());
        btnDeletar.setOnAction(e -> crudController.deletar());
        btnNovo.setOnAction(e -> crudController.novo());
    }
}
```

### Fluxo Completo de um Clique

Quando você clica em "Salvar":

1. **Evento do JavaFX** → `btnSalvar.setOnAction()`
2. **Chama método** → `crudController.salvar()`
3. **Validação** → `validarCampos()`
4. **Persistência** → `produtoDAO.inserir(produto)`
5. **Atualização da UI** → `carregarDados()` e `limparCampos()`
6. **Feedback** → `mostrarAlerta("Sucesso"...)`

## 🔄 Estado dos Botões (Dinâmico)

### Como os Botões Mudam de Estado

```java
// CRUDController.java
private void limparCampos() {
    txtNome.clear();
    txtPreco.clear();
    txtEstoque.clear();
    produtoSelecionado = null;

    // Atualiza estado: Salvar=ON, Editar=OFF, Deletar=OFF
    if (headerController != null) {
        headerController.updateButtonStates(true, false, false);
    }
}
```

### Método UpdateButtonStates

```java
// HeaderController.java
public void updateButtonStates(boolean salvarEnabled,
                              boolean editarEnabled,
                              boolean deletarEnabled) {
    btnSalvar.setDisable(!salvarEnabled);
    btnEditar.setDisable(!editarEnabled);
    btnDeletar.setDisable(!deletarEnabled);
    // btnNovo sempre fica habilitado
}
```

### Quando Cada Estado Acontece

- **Estado Inicial/Limpo**: `updateButtonStates(true, false, false)`
  - Salvar: ✅ Habilitado (pode inserir novo)
  - Editar/Deletar: ❌ Desabilitados (nada selecionado)

- **Produto Selecionado**: `updateButtonStates(false, true, true)`
  - Salvar: ❌ Desabilitado (modo edição)
  - Editar/Deletar: ✅ Habilitados (produto selecionado)

## 📊 TableView e Seleção

### Como a Seleção Funciona

```java
// CRUDController.java - no initialize()
tableView.getSelectionModel().selectedItemProperty().addListener(
    (obs, oldSelection, newSelection) -> {
        produtoSelecionado = newSelection;
        if (newSelection != null) {
            // Preenche os campos com dados do produto
            preencherCampos(newSelection);

            // Atualiza botões: foco na edição
            headerController.updateButtonStates(false, true, true);
        }
    }
);
```

### Preenchimento dos Campos

```java
private void preencherCampos(Produto produto) {
    txtNome.setText(produto.getNome());
    txtPreco.setText(String.valueOf(produto.getPreco()));
    txtEstoque.setText(String.valueOf(produto.getEstoque()));
}
```

## 💾 Persistência de Dados

### Operação Salvar (Novo Produto)

```java
public void salvar() {
    if (validarCampos()) {
        try {
            // Criar produto com dados dos campos
            Produto produto = new Produto(
                txtNome.getText(),
                Double.parseDouble(txtPreco.getText()),
                Integer.parseInt(txtEstoque.getText())
            );

            // Persistir no banco
            produtoDAO.inserir(produto);

            // Atualizar interface
            carregarDados();
            limparCampos();

            mostrarAlerta("Sucesso", "Produto salvo!", INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Dados inválidos!", ERROR);
        }
    }
}
```

### Carregamento de Dados

```java
private void carregarDados() {
    produtos.clear();                          // Limpa lista
    produtos.addAll(produtoDAO.listarTodos()); // Recarrega do banco
}
```

## 🔗 Conexão Entre Componentes

### Diagrama de Dependências

```
MainController
    ↓ carrega
CRUDController (FXML)
    ↓ injeta dinamicamente
HeaderController (programaticamente)
    ↓ conecta ações
CRUDController.métodos (salvar, editar, deletar, novo)
    ↓ chamam
ProdutoDAO (persistência)
```

### Ciclo de Vida dos Controllers

1. **MainController**: Criado uma vez, vive durante toda aplicação
2. **CRUDController**: Criado cada vez que "CRUD" é clicado
3. **HeaderController**: Criado cada vez que CRUDController inicializa

### Comunicação Bidirecional

- **Header → CRUD**: Ações dos botões chamam métodos do CRUD
- **CRUD → Header**: Estado dos botões é atualizado pelo CRUD

## 🎨 Interface Declarativa (FXML)

### Como o FXML Conecta Tudo

```xml
<!-- crud-view.fxml -->
<VBox fx:controller="com.example.fxdemo.controllers.CRUDController">
    <Label text="CRUD de Produtos"/>

    <!-- Header será injetado aqui -->
    <fx:include source="header-view.fxml" fx:id="header"/>

    <!-- Campos de entrada -->
    <TextField fx:id="txtNome"/>
    <TextField fx:id="txtPreco"/>
    <TextField fx:id="txtEstoque"/>

    <!-- Tabela -->
    <TableView fx:id="tableView">
        <columns>
            <TableColumn fx:id="colNome" text="Nome"/>
            <!-- outras colunas -->
        </columns>
    </TableView>
</VBox>
```

### Processo de Injeção FXML

1. **Parsing**: JavaFX lê o XML
2. **Instanciação**: Cria objetos Java para cada elemento
3. **Controller**: Cria instância da classe especificada
4. **Injeção**: Atribui objetos aos campos marcados com `@FXML`
5. **Initialize**: Chama `initialize()` se existir

## 🐛 Tratamento de Erros

### Try-Catch Estratégico

```java
private void carregarTela(String fxmlPath) {
    try {
        FXMLLoader loader = new FXMLLoader(getResource(fxmlPath));
        Parent root = loader.load();  // Pode lançar IOException
        mainPane.setCenter(root);
    } catch (IOException e) {
        e.printStackTrace();          // Logging básico
        // Interface continua funcional mesmo com erro
    }
}
```

### Validação Robusta

```java
private boolean validarCampos() {
    String nome = txtNome.getText().trim();
    if (nome.isEmpty()) {
        mostrarAlerta("Erro", "Nome obrigatório!");
        txtNome.requestFocus();  // Foco no campo com erro
        return false;
    }

    try {
        double preco = Double.parseDouble(txtPreco.getText());
        if (preco < 0) throw new IllegalArgumentException();
    } catch (Exception e) {
        mostrarAlerta("Erro", "Preço deve ser positivo!");
        txtPreco.requestFocus();
        return false;
    }

    // Validações similares para estoque...
    return true;
}
```

## 🚀 Execução Completa

### Sequência de Eventos Típica

1. **Usuário executa**: `mvn javafx:run`
2. **JavaFX inicializa**: `HelloApplication.start()`
3. **Carrega main-view**: Instancia `MainController`
4. **Initialize chamado**: Carrega tela de texto por padrão
5. **Usuário clica "CRUD"**: `abrirTelaCRUD()` chamado
6. **Carrega crud-view**: Instancia `CRUDController`
7. **Initialize CRUD**: Injeta Header, carrega dados
8. **Header criado**: `HeaderController` conectado ao CRUD
9. **Interface pronta**: Usuário pode interagir

### Interação Completa

1. **Clique em linha da tabela** → Seleção muda → Campos preenchidos → Botões atualizados
2. **Clique "Novo"** → Campos limpos → Botão Salvar ativado
3. **Preencher campos** → Clique "Salvar" → Validação → Persistência → UI atualizada
4. **Selecionar produto** → Clique "Editar" → Modificar campos → Salvar alterações
5. **Selecionar produto** → Clique "Deletar" → Confirmação → Remoção

## 💡 Conceitos Chave Demonstrados

- **Injeção de Dependência Manual**: Controllers conectados programaticamente
- **Carregamento Dinâmico de UI**: Telas carregadas sob demanda
- **Comunicação Entre Controllers**: Header delega ações ao CRUD
- **Estado Reativo da UI**: Botões mudam baseado no contexto
- **Tratamento de Eventos**: Actions conectadas dinamicamente
- **Observables do JavaFX**: TableView responde a mudanças na lista
- **Separação de Responsabilidades**: DAO isola persistência
- **Validação em Camadas**: UI + Lógica + Dados

Este sistema demonstra uma arquitetura JavaFX moderna e escalável, com injeção de dependências, carregamento dinâmico de componentes e fluxo de eventos bem estruturado.
