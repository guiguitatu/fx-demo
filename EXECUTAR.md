# 🚀 Como Executar a Aplicação JavaFX

## Problema Atual

Você está enfrentando erros de sintaxe XML no arquivo FXML que impedem a aplicação de executar. Estes erros foram corrigidos nos arquivos `buttons-view.fxml` e `simple-view.fxml`.

## ✅ Correções Aplicadas

### 1. **buttons-view.fxml** - Linha 86
**Erro:** Aspas duplas dentro de atributo `text`
```xml
<!-- ANTES (com erro) -->
<Label text="• Botões de controle usam onAction="#methodName" no FXML" .../>

<!-- DEPOIS (corrigido) -->
<Label text="• Botões de controle usam onAction=&quot;#methodName&quot; no FXML" .../>
```

### 2. **simple-view.fxml** - Linha 62-65
**Erro:** Aspas duplas e caracteres especiais não escapados
```xml
<!-- ANTES (com erro) -->
<TextArea text="btnTrocarTexto.setOnAction(event -> { ... });" .../>

<!-- DEPOIS (corrigido) -->
<TextArea text="btnTrocarTexto.setOnAction(event -&gt; { ... });" .../>
```

## 🔧 Como Executar

### Opção 1: Usando Maven (Recomendado)
```bash
# Se você tem Maven instalado:
mvn clean compile javafx:run

# Ou usando o wrapper do Maven (se existir):
./mvnw clean compile javafx:run
```

### Opção 2: Usando o Arquivo Batch
```bash
# Execute o arquivo criado:
run.bat
```

### Opção 3: Configuração Manual do JavaFX
```bash
# 1. Baixe o JavaFX SDK 21 de: https://gluonhq.com/products/javafx/
# 2. Descompacte em uma pasta (ex: C:\javafx-sdk-21)
# 3. Execute com:
java --module-path "C:\caminho\para\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp target/classes com.example.fxdemo.HelloApplication
```

## 🎯 Telas Disponíveis

Após executar com sucesso, você terá acesso a:

1. **Texto** - Tela inicial simples
2. **CRUD** - Gerenciamento de produtos (SQLite)
3. **CSV** - Gerenciamento de produtos (CSV)
4. **FX** - Demonstração completa de componentes JavaFX
5. **Input+Botão** - Demonstração simples de input + ação
6. **Botões** - Demonstração de botões com feedback lateral

## 🔍 Verificação de Correções

Para verificar se as correções foram aplicadas:

1. Abra `src/main/resources/buttons-view.fxml`
2. Vá para a linha 86
3. Verifique se está: `onAction=&quot;#methodName&quot;`

4. Abra `src/main/resources/simple-view.fxml`
5. Vá para a linha 62
6. Verifique se está: `event -&gt;` (não `event ->`)

## ❓ Ainda com Problemas?

Se ainda encontrar erros:

1. **Recompile o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Verifique se há outros arquivos FXML com problemas similares**

3. **Certifique-se de que o JavaFX 21 está corretamente configurado**

A aplicação agora deve executar sem os erros de sintaxe XML que estavam impedindo o carregamento dos arquivos FXML.
