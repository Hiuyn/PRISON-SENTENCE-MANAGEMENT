module com.example.psmsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.psmsystem to javafx.fxml;
    exports com.example.psmsystem;
    exports com.example.psmsystem.controller;
    opens com.example.psmsystem.controller to javafx.fxml;
}