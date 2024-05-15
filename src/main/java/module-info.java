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
    requires MaterialFX;
    requires java.sql.rowset;

    opens com.example.psmsystem to javafx.fxml;
    exports com.example.psmsystem;
    exports com.example.psmsystem.controller;
    opens com.example.psmsystem.controller to javafx.fxml;
    opens com.example.psmsystem.model.prisoner to javafx.base;
    exports com.example.psmsystem.controller.ManagementVisit;
    opens com.example.psmsystem.controller.ManagementVisit to javafx.fxml;

    exports com.example.psmsystem.controller.prisoner;
    opens com.example.psmsystem.controller.prisoner;
    opens com.example.psmsystem.model.managementvisit to javafx.base;

    exports com.example.psmsystem.controller.health;
    opens com.example.psmsystem.controller.health to javafx.fxml;
    opens com.example.psmsystem.model.health to javafx.base;
}