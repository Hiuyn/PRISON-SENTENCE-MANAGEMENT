package com.example.psmsystem.controller;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.helper.AlertHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.psmsystem.model.IUserDao;
import com.example.psmsystem.model.User;
import com.example.psmsystem.service.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterController implements Initializable{

    private static IUserDao<User> userDao;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Button registerButton;

    Window window;

    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public RegisterController() {
        userDao = new UserDao();
    }

    @FXML
    private void register() {
        window = registerButton.getScene().getWindow();
        if (this.isValidated()) {
            User user = new User(firstName.getText(), lastName.getText(), email.getText(), username.getText(), password.getText());
            userDao.addUser(user);
            this.clearForm();
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                            "You have registered successfully.");
        }
    }

    private boolean isAlreadyRegistered() {
        return userDao.checkUsername(username.getText());
    }

    private boolean isValidated() {

        window = registerButton.getScene().getWindow();
        if (firstName.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "First name text field cannot be blank.");
            firstName.requestFocus();
        } else if (firstName.getText().length() < 2 || firstName.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "First name text field cannot be less than 2 and greator than 25 characters.");
            firstName.requestFocus();
        } else if (lastName.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Last name text field cannot be blank.");
            lastName.requestFocus();
        } else if (lastName.getText().length() < 2 || lastName.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Last name text field cannot be less than 2 and greator than 25 characters.");
            lastName.requestFocus();
        } else if (email.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Email text field cannot be blank.");
            email.requestFocus();
        } else if (email.getText().length() < 5 || email.getText().length() > 45) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Email text field cannot be less than 5 and greator than 45 characters.");
            email.requestFocus();
        } else if (username.getText().equals("")) {
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
        } else if (confirmPassword.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Confirm password text field cannot be blank.");
            confirmPassword.requestFocus();
        } else if (confirmPassword.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Confirm password text field cannot be less than 5 and greator than 25 characters.");
            confirmPassword.requestFocus();
        } else if (!password.getText().equals(confirmPassword.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password and confirm password text fields does not match.");
            password.requestFocus();
        } else if (isAlreadyRegistered()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "The username is already taken by someone else.");
            username.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private boolean clearForm() {
        firstName.clear();
        lastName.clear();
        email.clear();
        username.clear();
        password.clear();
        confirmPassword.clear();
        return true;
    }

    @FXML
    private void showLoginStage() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath + "view/LoginView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.getIcons().add(new Image("file: " + fxmlPath + "assets/icon.png"));
        stage.show();
    }

}
