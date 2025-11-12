# FXdemo - Sistema de Gerenciamento de Produtos JavaFX

[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)](https://openjfx.io/)
[![SQLite](https://img.shields.io/badge/SQLite-3.44.1.0-green.svg)](https://www.sqlite.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-orange.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

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
- **Nova tela FX**: Demonstração completa dos componentes JavaFX mais utilizados

## 🏗️ Arquitetura

```
FXdemo/
├── controllers/          # Controladores JavaFX
│   ├── MainController    # Navegação principal
│   ├── CRUDController    # CRUD SQLite
│   ├── CSVController     # CRUD CSV
│   ├── FXController      # Demonstração de componentes
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
    ├── fx-view.fxml      # Demonstração de componentes
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
   git clone https://github.com/guiguitatu/FXdemo.git
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
2. **Navegue entre módulos** - Use os botões "Texto", "CRUD", "CSV" e "FX" no topo
3. **CRUD SQLite** - Gerencie produtos no banco de dados
4. **CRUD CSV** - Gerencie produtos em arquivos CSV
5. **FX Components** - Demonstre os componentes mais utilizados do JavaFX
6. **Texto** - Visualize conteúdo de exemplo

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

### 🎨 **Novas Telas de Demonstração**

#### **Tela FX** - Componentes Completos
Apresenta uma demonstração completa e interativa dos componentes JavaFX mais utilizados:

#### **Tela Input+Botão** - Demonstração Simples
Mostra como conectar um campo de texto a um botão que altera um label:

#### **Tela Botões** - Controles Simples
Demonstra diferentes formas de conectar ações a botões, com feedback visual lateral:

#### **Funcionalidades das Telas:**

#### Componentes Demonstrados:
- **Labels**: Texto básico e estilizado
- **Buttons**: Botões normais, desabilitados e estilizados
- **TextFields**: Campos de texto, senhas, prompts e campos somente leitura
- **TextArea**: Áreas de texto multilinha
- **ComboBox**: Listas dropdown
- **CheckBox**: Caixas de seleção múltipla
- **RadioButton**: Seleção única com ToggleGroup
- **ProgressBar/ProgressIndicator**: Barras de progresso
- **Slider**: Controle deslizante
- **DatePicker**: Seleção de datas
- **ColorPicker**: Seleção de cores
- **Spinner**: Controles numéricos
- **TableView**: Tabelas com dados
- **ListView**: Listas de itens
- **Accordion**: Paineis expansíveis
- **TabPane**: Abas organizacionais
- **MenuBar**: Barras de menu
- **ToolBar**: Barras de ferramentas

#### Interatividade:
- Todos os componentes respondem às interações do usuário
- Resultados das ações são exibidos em tempo real
- Demonstração animada de todos os componentes
- Limpeza completa dos campos com um clique

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [`LICENSE.md`](LICENSE.md) para mais detalhes.

### Termos da Licença MIT

- ✅ **Uso gratuito**: Para fins pessoais e comerciais
- ✅ **Modificação**: Você pode modificar e distribuir o código
- ✅ **Distribuição**: Inclusão em projetos proprietários permitida
- ✅ **Sem restrições**: Uso irrestrito do software

**Apenas mantenha o aviso de copyright em todas as cópias.**

## 📁 Estrutura do Projeto

```
FXdemo/
├── src/main/java/...          # Código fonte Java
├── src/main/resources/...      # Arquivos FXML e recursos
├── target/...                  # Arquivos compilados (ignorados)
├── .gitignore                  # Arquivos ignorados pelo Git
├── LICENSE.md                  # Licença MIT
├── README.md                   # Esta documentação
├── DOCUMENTACAO.md            # Documentação técnica completa
├── EXPLICACAO.md              # Explicação da arquitetura
├── EXPLICACAOFX.md            # Documentação da tela FX
├── EXECUTAR.md                # Guia de execução
├── pom.xml                    # Configuração Maven
└── run.bat                    # Script de execução
```

## 👨‍💻 Autor

**Guilherme** - *Desenvolvimento inicial* - [Seu GitHub](https://github.com/SEU_USERNAME)

## 🙏 Agradecimentos

- Oracle pela plataforma Java
- OpenJFX pela framework JavaFX
- SQLite pela engine de banco de dados
- Apache Maven pelo sistema de build
