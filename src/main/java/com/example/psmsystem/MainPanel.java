/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.psmsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Ramesh Godara
 */
public class MainPanel extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file: /com/example/psmsystem/assets/icon.png"));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
