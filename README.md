# FXdemo - Sistema de Gerenciamento de Produtos JavaFX

[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)](https://openjfx.io/)
[![SQLite](https://img.shields.io/badge/SQLite-3.44.1.0-green.svg)](https://www.sqlite.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-orange.svg)](https://maven.apache.org/)

Um sistema desktop moderno desenvolvido em JavaFX para gerenciamento de produtos com suporte a múltiplas formas de persistência de dados (SQLite e CSV).

## 🚀 Funcionalidades

### 📊 CRUD Completo de Produtos
- **Inserir**: Adicionar novos produtos com validação de campos
- **Editar**: Modificar produtos existentes com confirmação
- **Deletar**: Remover produtos com confirmação de segurança
- **Visualizar**: Listagem organizada em tabelas com formatação

### 💾 Múltiplas Opções de Persistência
- **SQLite**: Banco de dados relacional local com operações ACID
- **CSV**: Arquivo de texto estruturado para importação/exportação

### 🎨 Interface Moderna
- Design responsivo com BorderPane
- Navegação intuitiva entre módulos
- Header dinâmico com controle de estados
- Validações em tempo real

## 🏗️ Arquitetura

```
FXdemo/
├── controllers/          # Controladores JavaFX
│   ├── MainController    # Navegação principal
│   ├── CRUDController    # CRUD SQLite
│   ├── CSVController     # CRUD CSV
│   └── HeaderController  # Controle de botões
├── dao/                  # Camada de acesso a dados
│   ├── ProdutoDAO        # SQLite operations
│   └── CSVProdutoDAO     # CSV operations
├── model/                # Modelos de dados
│   └── Produto           # Entidade Produto
└── resources/            # Arquivos FXML e recursos
    ├── main-view.fxml    # Layout principal
    ├── crud-view.fxml    # Layout CRUD
    ├── csv-view.fxml     # Layout CSV
    └── header-view.fxml  # Layout Header
```

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **JavaFX 21** - Framework para interfaces desktop
- **SQLite JDBC** - Driver para banco de dados SQLite
- **Maven** - Gerenciamento de dependências e build
- **FXML** - Linguagem declarativa para interfaces

## 📦 Instalação e Execução

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+

### Passos para execução

1. **Clone o repositório**
   ```bash
   git clone https://github.com/SEU_USERNAME/FXdemo.git
   cd FXdemo
   ```

2. **Compile e execute**
   ```bash
   mvn clean javafx:run
   ```

3. **Ou compile e execute manualmente**
   ```bash
   mvn clean compile
   mvn javafx:run
   ```

## 🎯 Como Usar

1. **Inicie a aplicação** - Execute o comando Maven acima
2. **Navegue entre módulos** - Use os botões "Texto", "CRUD" e "CSV" no topo
3. **CRUD SQLite** - Gerencie produtos no banco de dados
4. **CRUD CSV** - Gerencie produtos em arquivos CSV
5. **Texto** - Visualize conteúdo de exemplo

### Campos obrigatórios:
- **Nome**: Texto não vazio
- **Preço**: Número decimal positivo
- **Estoque**: Número inteiro positivo

## 📁 Estrutura de Dados

### Produto
```java
Produto {
    int id;           // Identificador único (SQLite)
    String nome;      // Nome do produto
    double preco;     // Preço unitário
    int estoque;      // Quantidade em estoque
}
```

### Arquivo CSV
```csv
nome,preco,estoque
Notebook Dell,2500.50,15
Mouse Logitech,25.90,50
```

## 🔧 Desenvolvimento

### Dependências Maven
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21</version>
</dependency>
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.44.1.0</version>
</dependency>
```

### Scripts disponíveis
- `mvn clean compile` - Compilar o projeto
- `mvn javafx:run` - Executar aplicação
- `mvn clean package` - Gerar JAR executável

## 📈 Funcionalidades Avançadas

- **Validação de entrada** em tempo real
- **Formatação automática** de preços (R$ XX.XX)
- **Controle de estado** dos botões baseado na seleção
- **Confirmação de ações** destrutivas
- **Importação de CSV** externos
- **Persistência automática** de dados

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Guilherme** - *Desenvolvimento inicial* - [Seu GitHub](https://github.com/SEU_USERNAME)

## 🙏 Agradecimentos

- Oracle pela plataforma Java
- OpenJFX pela framework JavaFX
- SQLite pela engine de banco de dados
- Apache Maven pelo sistema de build
