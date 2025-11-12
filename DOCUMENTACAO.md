# 📚 Documentação Técnica - FXdemo

## Visão Geral do Sistema

O FXdemo é uma aplicação desktop desenvolvida em JavaFX que demonstra conceitos avançados de desenvolvimento Java, incluindo arquitetura MVC, injeção de dependências, persistência de dados e design de interfaces modernas.

## 🏗️ Arquitetura do Sistema

### Padrão MVC (Model-View-Controller)

O sistema segue uma arquitetura baseada no padrão MVC com algumas adaptações para JavaFX:

```
Model (Dados)
├── Produto.java              # Entidade de domínio
└── DAO/                      # Camada de acesso a dados
    ├── ProdutoDAO.java       # SQLite operations
    └── CSVProdutoDAO.java    # CSV operations

View (Interface)
├── FXML/                     # Arquivos de layout declarativo
│   ├── main-view.fxml        # Layout principal
│   ├── crud-view.fxml        # Layout CRUD
│   ├── csv-view.fxml         # Layout CSV
│   └── header-view.fxml      # Componente Header
└── CSS/                      # Estilos (inline nos FXML)

Controller (Lógica)
├── MainController.java       # Navegação e layout principal
├── CRUDController.java       # Lógica CRUD SQLite
├── CSVController.java        # Lógica CRUD CSV
└── HeaderController.java     # Controle do componente Header
```

## 🔄 Fluxo de Funcionamento

### 1. Inicialização da Aplicação

```java
// HelloApplication.java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carrega main-view.fxml como tela inicial
        FXMLLoader fxmlLoader = new FXMLLoader(
            HelloApplication.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Aplicação JavaFX - Menu");
        stage.setScene(scene);
        stage.show();
    }
}
```

### 2. Carregamento da Tela Principal

O `main-view.fxml` define um `BorderPane` controlado pelo `MainController`:

```xml
<BorderPane fx:id="mainPane"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="com.example.fxdemo.controllers.MainController">
    <top>
        <!-- Barra de navegação -->
    </top>
    <!-- center será preenchido dinamicamente -->
</BorderPane>
```

### 3. Navegação Entre Telas

O `MainController` gerencia a navegação carregando diferentes FXML no centro do BorderPane:

```java
public class MainController {
    @FXML private BorderPane mainPane;

    @FXML
    private void abrirTelaCRUD() {
        carregarTela("/crud-view.fxml");
    }

    private void carregarTela(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                MainController.class.getResource(fxmlPath));
            Parent root = loader.load();
            mainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 🎯 Sistema CRUD SQLite

### Arquitetura do CRUDController

```java
public class CRUDController {
    // Injeção FXML
    @FXML private TableView<Produto> tableView;
    @FXML private TextField txtNome, txtPreco, txtEstoque;
    @FXML private HBox header; // Container para o Header

    // Dependências
    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> produtos;
    private Produto produtoSelecionado;
    private HeaderController headerController;
}
```

### Injeção de Dependência do Header

O Header é injetado dinamicamente no `initialize()`:

```java
@FXML
public void initialize() {
    // Inicializar DAO e dados
    produtoDAO = new ProdutoDAO();
    produtos = FXCollections.observableArrayList();

    // Configurar TableView
    configurarTableView();

    // Injetar Header dinamicamente
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/header-view.fxml"));
        HBox headerNode = loader.load();
        header.getChildren().clear();
        header.getChildren().add(headerNode);

        headerController = loader.getController();
        headerController.setCRUDController(this);
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Carregar dados e atualizar interface
    carregarDados();
    limparCampos();
}
```

### Comunicação Header ↔ CRUDController

```java
// HeaderController.java
public class HeaderController {
    @FXML private Button btnSalvar, btnEditar, btnDeletar, btnNovo;
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
}
```

### Controle de Estado dos Botões

```java
// CRUDController.java
private void limparCampos() {
    txtNome.clear();
    txtPreco.clear();
    txtEstoque.clear();
    produtoSelecionado = null;

    // Atualizar estado dos botões
    if (headerController != null) {
        headerController.updateButtonStates(true, false, false);
        // Salvar: habilitado | Editar/Deletar: desabilitados
    }
}
```

## 📊 Camada de Dados (DAO)

### ProdutoDAO (SQLite)

```java
public class ProdutoDAO {
    private static final String DB_URL = "jdbc:sqlite:produtos.db";

    public ProdutoDAO() {
        criarTabela(); // Cria tabela se não existir
    }

    private void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                preco REAL NOT NULL,
                estoque INTEGER NOT NULL
            )""";
        // Executa SQL...
    }

    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos(nome, preco, estoque) VALUES(?,?,?)";
        // PreparedStatement para segurança...
    }
}
```

### Operações CRUD

```java
// Inserir
public void inserir(Produto produto) {
    String sql = "INSERT INTO produtos(nome, preco, estoque) VALUES(?,?,?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, produto.getNome());
        pstmt.setDouble(2, produto.getPreco());
        pstmt.setInt(3, produto.getEstoque());
        pstmt.executeUpdate();
    }
}

// Listar todos
public List<Produto> listarTodos() {
    List<Produto> produtos = new ArrayList<>();
    String sql = "SELECT * FROM produtos";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            produtos.add(new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getDouble("preco"),
                rs.getInt("estoque")
            ));
        }
    }
    return produtos;
}
```

## 📄 Sistema CSV

### CSVProdutoDAO

```java
public class CSVProdutoDAO {
    private static final String CSV_FILE = "produtos.csv";

    public void salvarProdutos(List<Produto> produtos) {
        try (PrintWriter writer = new PrintWriter(
             new FileWriter(CSV_FILE, StandardCharsets.UTF_8))) {
            writer.println("nome,preco,estoque");
            for (Produto produto : produtos) {
                writer.printf("%s,%.2f,%d%n",
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getEstoque());
            }
        }
    }
}
```

## 🎨 Interface Gráfica (FXML)

### Estrutura do main-view.fxml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<BorderPane fx:id="mainPane"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="com.example.fxdemo.controllers.MainController"
            prefHeight="600.0" prefWidth="800.0">

    <!-- Barra superior com navegação -->
    <top>
        <VBox style="-fx-background-color: #f8f9fa;">
            <HBox spacing="10.0" alignment="CENTER_LEFT">
                <Label text="Navegação:" style="-fx-font-weight: bold;"/>
                <Button text="Texto" onAction="#abrirTelaTexto"/>
                <Button text="CRUD" onAction="#abrirTelaCRUD"/>
                <Button text="CSV" onAction="#abrirTelaCSV"/>
            </HBox>
        </VBox>
    </top>

    <!-- Centro dinâmico -->
</BorderPane>
```

### Componente Header (header-view.fxml)

```xml
<HBox xmlns:fx="http://javafx.com/fxml"
      fx:controller="com.example.fxdemo.controllers.HeaderController"
      spacing="10.0"
      style="-fx-background-color: #f5f5f5;">

    <Label text="Ações:" style="-fx-font-weight: bold;"/>

    <Button fx:id="btnSalvar" text="Salvar"
            style="-fx-background-color: #4CAF50; -fx-text-fill: white;"/>
    <Button fx:id="btnEditar" text="Editar"
            style="-fx-background-color: #2196F3; -fx-text-fill: white;"/>
    <Button fx:id="btnDeletar" text="Deletar"
            style="-fx-background-color: #f44336; -fx-text-fill: white;"/>
    <Button fx:id="btnNovo" text="Novo"
            style="-fx-background-color: #FF9800; -fx-text-fill: white;"/>
</HBox>
```

## 🔧 Configuração Maven

### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>FXdemo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- JavaFX Controls -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>21</version>
        </dependency>

        <!-- JavaFX FXML -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>21</version>
        </dependency>

        <!-- SQLite JDBC -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.44.1.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>

            <!-- JavaFX Maven Plugin -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <executions>
                    <execution>
                        <id>default-cli</id>
                        <configuration>
                            <mainClass>com.example.fxdemo/com.example.fxdemo.HelloApplication</mainClass>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## 🧪 Validações e Tratamento de Erros

### Validação de Campos

```java
private boolean validarCampos() {
    if (txtNome.getText().trim().isEmpty()) {
        mostrarAlerta("Erro", "O campo Nome é obrigatório!", ERROR);
        return false;
    }

    try {
        double preco = Double.parseDouble(txtPreco.getText());
        if (preco < 0) {
            mostrarAlerta("Erro", "O preço não pode ser negativo!", ERROR);
            return false;
        }
    } catch (NumberFormatException e) {
        mostrarAlerta("Erro", "O preço deve ser um número válido!", ERROR);
        return false;
    }

    // Validações similares para estoque...
    return true;
}
```

### Tratamento de Exceções

```java
private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
    Alert alert = new Alert(tipo);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensagem);
    alert.showAndWait();
}
```

## 🚀 Execução e Deployment

### Execução via Maven

```bash
# Limpar e executar
mvn clean javafx:run

# Apenas compilar
mvn clean compile

# Gerar JAR
mvn clean package
```

### Estrutura do JAR Gerado

```
FXdemo.jar
├── com/example/fxdemo/
│   ├── HelloApplication.class
│   ├── controllers/
│   │   ├── MainController.class
│   │   ├── CRUDController.class
│   │   └── ...
│   ├── dao/
│   │   ├── ProdutoDAO.class
│   │   └── CSVProdutoDAO.class
│   ├── model/
│   │   └── Produto.class
│   └── ...
├── resources/
│   ├── main-view.fxml
│   ├── crud-view.fxml
│   └── ...
└── META-INF/MANIFEST.MF
```

## 🔍 Debugging e Monitoramento

### Logs de Debug

```java
// Em operações críticas
System.out.println("Carregando produtos...");
produtos.clear();
produtos.addAll(produtoDAO.listarTodos());
System.out.println("Produtos carregados: " + produtos.size());
```

### Tratamento de SQLExceptions

```java
try {
    // Operação de banco
} catch (SQLException e) {
    System.err.println("Erro de banco: " + e.getMessage());
    mostrarAlerta("Erro", "Erro ao acessar banco de dados!", ERROR);
}
```

## 📈 Melhorias Futuras

1. **Injeção de Dependência**: Implementar framework como Spring ou Guice
2. **Testes Unitários**: Cobertura completa com JUnit
3. **ORM**: Hibernate ou EclipseLink para mapeamento objeto-relacional
4. **Logging**: Framework como Log4j ou SLF4J
5. **Internacionalização**: Suporte a múltiplos idiomas
6. **Temas**: Sistema de temas claro/escuro
7. **API REST**: Exposição de endpoints REST
8. **Containerização**: Docker para deployment

## 🎯 Conceitos Demonstrados

- **Arquitetura MVC** com JavaFX
- **Injeção de Dependências** manual
- **Programação Orientada a Eventos**
- **Padrões DAO** para acesso a dados
- **Validação de Dados** e tratamento de erros
- **Interface Declarativa** com FXML
- **Bindings e Observables** do JavaFX
- **Prepared Statements** para segurança SQL
- **File I/O** para manipulação de CSV
- **Maven** para gerenciamento de projeto
