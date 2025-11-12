module com.example.fxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.fxdemo to javafx.fxml;
    opens com.example.fxdemo.controllers to javafx.fxml;
    opens com.example.fxdemo.model to javafx.base;
    exports com.example.fxdemo;
    exports com.example.fxdemo.controllers;
}