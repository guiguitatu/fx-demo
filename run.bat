@echo off
echo Executando aplicacao JavaFX...
echo.

REM Tentar executar com Maven wrapper se existir
if exist mvnw.cmd (
    echo Usando Maven wrapper...
    call mvnw.cmd clean compile javafx:run
    goto end
)

REM Se Maven wrapper nao existir, tentar executar diretamente
echo Maven wrapper nao encontrado. Tentando execucao direta...
echo Certifique-se de que o JavaFX esta configurado no seu ambiente.
echo.

REM Tentar com classpath direto (ajuste os caminhos conforme necessario)
java --module-path "C:\path\to\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp target\classes com.example.fxdemo.HelloApplication

:end
echo.
echo Aplicacao finalizada.
pause
