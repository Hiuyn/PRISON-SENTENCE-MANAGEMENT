package com.example.psmsystem.controller;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.helper.AlertHelper;
import com.example.psmsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class LoginController implements Initializable{

    private final Connection con;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    Window window;

    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public LoginController() {
        DbConnection dbc = DbConnection.getDatabaseConnection();
        con = dbc.getConnection();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        if (this.isValidated()) {
            PreparedStatement ps;
            ResultSet rs;

            String query = "select * from users WHERE user_name = ? and password = ?";
            try {
                ps = con.prepareStatement(query);
                ps.setString(1, username.getText());
                ps.setString(2, password.getText());
                rs = ps.executeQuery();

                if (rs.next()) {
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    String email = rs.getString("email");
                    String user_name = rs.getString("user_name");
                    String password = rs.getString("password");

                    User user = new User(first_name, last_name, email, user_name, password);
                    loginButton.getScene().getWindow().hide();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath + "view/MainPanelView.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.setMaximized(true);;
                        stage.setTitle("Admin Panel");
                        stage.getIcons().add(new Image("file: " + fxmlPath + "assets/icon.png"));
                        stage.show();

                        MainPanelController controller = loader.getController();
                        controller.initData(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Xử lý lỗi ở đây
                    }

                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                            "Invalid username and password.");
                    username.requestFocus();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    private boolean isValidated() {

        window = loginButton.getScene().getWindow();
        if (username.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be less than 5 and greator than 25 characters.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    void showRegisterStage(MouseEvent event) throws IOException{
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath + "view/RegisterView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Registration");
        stage.getIcons().add(new Image("file: " + fxmlPath + "assets/icon.png"));
        stage.show();
    }

}
