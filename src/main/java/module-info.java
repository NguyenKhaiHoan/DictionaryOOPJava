module com.example.menudemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires jsapi;
    requires jfoenix;
    requires java.sql;

    opens com.example.menudemo to javafx.fxml;
    exports com.example.menudemo;
}