package com.example.psmsystem.controller;

import com.example.psmsystem.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MainPanelController implements Initializable {
    @FXML
    private Button btnClose;
    @FXML
    private Label assess;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label dashboard;

    @FXML
    private Label prisoner;

    @FXML
    private Label health;

    @FXML
    private Label idLogin;

    @FXML
    private Label logout;

    @FXML
    private Label manageVisits;

    @FXML
    private  Label nameView;

    @FXML
    private Label report;

    @FXML
    private Label sentence;

    @FXML
    private AnchorPane centerPane;
    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

//    private int doGet(HttpServletRequest request, HttpServletResponse response) {
//        // Lấy session hiện tại hoặc tạo mới nếu chưa có
//        HttpSession session = request.getSession();
//
//        // Đặt thuộc tính cho session
//        session.setAttribute("userId", idLogin.getText());
//    }


    public void initData(User user) {
        idLogin.setText(user.getFullName());
        loadFXML("Dashboard");
    }

    private void loadFXML(String fileName) {
        try {
            nameView.setText(fileName);
            String pathFileName;
            if (fileName.equals("Dashboard")){
                pathFileName = fxmlPath + "view/" + fileName + "View.fxml";
            }
            else{
//                pathFileName = fxmlPath + "view/" + "Page01View.fxml";
                pathFileName = fxmlPath + "view/" + fileName.toLowerCase() + "/" + fileName + "View.fxml";
            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(pathFileName)));
//            borderPane.setCenter(root);
            centerPane.getChildren().clear(); // Xóa các node con hiện có
            centerPane.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            dashboard.getScene().getWindow();


        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainPanelController() {
    }

    @FXML
    void close(MouseEvent  event) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath + "view/LoginView.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.getIcons().add(new Image("file: " + fxmlPath + "assets/icon.png"));
        stage.show();
    }

    @FXML
    void loadDashboardView(MouseEvent  event) {
        loadFXML("Dashboard");
        setButtonStyle(dashboard);
    }

    @FXML
    void loadAssessView(MouseEvent event) {
        loadFXML("Assess");
        setButtonStyle(assess);
    }

    @FXML
    void loadHealthView(MouseEvent event) {
        loadFXML("Health");
        setButtonStyle(health);
    }

    @FXML
    void loadManageVisitsView(MouseEvent event) {
        loadFXML("ManagementVisit");
        setButtonStyle(manageVisits);
    }

    @FXML
    void loadReportView(MouseEvent event) {
        loadFXML("Dashboard");
        setButtonStyle(report);
    }

    @FXML
    void loadPrisonerView(MouseEvent event) {

        loadFXML("Prisoner");
        setButtonStyle(prisoner);
    }

    @FXML
    void loadSentenceView(MouseEvent event) {

        loadFXML("Sentence");
        setButtonStyle(sentence);
    }

    private void setButtonStyle(Label label) {
        // Xóa kiểu đã chọn từ tất cả các nút
        dashboard.getStyleClass().remove("selected");
        prisoner.getStyleClass().remove("selected");
        assess.getStyleClass().remove("selected");
        manageVisits.getStyleClass().remove("selected");
        report.getStyleClass().remove("selected");
        health.getStyleClass().remove("selected");
        sentence.getStyleClass().remove("selected");
        label.getStyleClass().add("selected");
    }

    public void onBtnCloseClick(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
