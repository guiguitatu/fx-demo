# CRUD de Produtos - CSV

## Funcionalidades

O sistema de CRUD para CSV permite gerenciar produtos armazenados em um arquivo `produtos.csv` com as seguintes operações:

### Operações CRUD
- **Salvar**: Adiciona um novo produto ao arquivo CSV
- **Editar**: Modifica um produto existente selecionado na tabela
- **Deletar**: Remove um produto do arquivo CSV
- **Novo**: Limpa os campos para inserir um novo produto

### Carregamento de CSV Externo
- **Carregar CSV**: Permite importar produtos de um arquivo CSV externo
- Os produtos importados são adicionados aos produtos existentes
- Formato esperado: `nome,preço,estoque`

## Formato do Arquivo CSV

```
nome,preco,estoque
Notebook Dell,2500.50,15
Mouse Logitech,25.90,50
Teclado Mecânico,150.00,30
```

## Arquivos Criados

- `CSVProdutoDAO.java`: Classe responsável pela manipulação do arquivo CSV
- Arquivo `produtos.csv`: Arquivo local onde os produtos são armazenados
- `produtos_exemplo.csv`: Arquivo de exemplo para testes

## Diferenças do CRUD SQLite

- **Persistência**: Dados salvos em arquivo CSV em vez de banco de dados
- **Carregamento**: Permite importar dados de arquivos CSV externos
- **Formatação**: Preços exibidos com formatação "R$ X.XX"
- **Arquivo**: Dados persistem em `produtos.csv` no diretório do projeto

## Como Usar

1. Execute a aplicação
2. Clique no botão "CSV" na navegação
3. Use os botões para:
   - Inserir novos produtos
   - Selecionar produtos na tabela para editar/deletar
   - Carregar produtos de arquivos CSV externos

## Validações

- Nome: Campo obrigatório
- Preço: Número decimal positivo obrigatório
- Estoque: Número inteiro positivo obrigatório
