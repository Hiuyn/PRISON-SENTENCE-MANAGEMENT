package com.example.psmsystem.controller;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {
    @FXML
    private Button addPurchase;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Text idLogin;

    @FXML
    private Button home;

    @FXML
    private Button logout;

    @FXML
    private Button page01;

    @FXML
    private Button page02;

    @FXML
    private Button page03;

    @FXML
    private Button page04;

    @FXML
    private Button page05;

    @FXML
    private Button page06;

    @FXML
    private Button page07;

    @FXML
    private Button page08;

    @FXML
    private Button page09;

    @FXML
    private Button page10;

    @FXML
    private Button purchaseDetail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void initData(User user) {
//        this.currentUser = user;
        idLogin.setText(user.toString());
        // Xử lí thông tin người dùng ở đây
    }

    public MainPanelController() {
    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void loadHomeView(ActionEvent event) {

    }

    @FXML
    void loadPage01View(ActionEvent event) {

    }

    @FXML
    void loadPage02View(ActionEvent event) {

    }

    @FXML
    void loadPage03View(ActionEvent event) {

    }

    @FXML
    void loadPage04View(ActionEvent event) {

    }

    @FXML
    void loadPage05View(ActionEvent event) {

    }

    @FXML
    void loadPage06View(ActionEvent event) {

    }

    @FXML
    void loadPage07View(ActionEvent event) {

    }

    @FXML
    void loadPage08View(ActionEvent event) {

    }

    @FXML
    void loadPage09View(ActionEvent event) {

    }

    @FXML
    void loadPage10View(ActionEvent event) {

    }

}
