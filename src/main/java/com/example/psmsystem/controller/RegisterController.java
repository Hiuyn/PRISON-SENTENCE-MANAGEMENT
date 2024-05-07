package com.example.psmsystem.controller;

import com.example.psmsystem.helper.AlertHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.psmsystem.model.user.IUserDao;
import com.example.psmsystem.model.user.User;
import com.example.psmsystem.service.userDao.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterController implements Initializable{

    private static IUserDao<User> userDao;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtFullName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

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
            User user = new User(txtFullName.getText(), txtUsername.getText(), txtPassword.getText());
            userDao.addUser(user);
            this.clearForm();
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                            "You have registered successfully.");
        }
    }

    private boolean isAlreadyRegistered() {
        return userDao.checkUsername(txtUsername.getText());
    }

    private boolean isValidated() {

        window = registerButton.getScene().getWindow();
        if (txtFullName.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Full name text field cannot be blank.");
            txtFullName.requestFocus();
        } else if (txtFullName.getText().length() < 2 || txtFullName.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "First name text field cannot be less than 2 and greator than 25 characters.");
            txtFullName.requestFocus();
        } else if (txtUsername.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Last name text field cannot be blank.");
            txtUsername.requestFocus();
        } else if (txtUsername.getText().length() < 2 || txtUsername.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Last name text field cannot be less than 2 and greator than 25 characters.");
            txtUsername.requestFocus();
        } else if (txtPassword.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Email text field cannot be blank.");
            txtPassword.requestFocus();
        } else if (txtPassword.getText().length() < 5 || txtPassword.getText().length() > 45) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Email text field cannot be less than 5 and greator than 45 characters.");
            txtPassword.requestFocus();
        } else if (txtConfirmPassword.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be blank.");
            txtConfirmPassword.requestFocus();
        } else if (txtConfirmPassword.getText().length() < 5 || txtConfirmPassword.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be less than 5 and greator than 25 characters.");
            txtConfirmPassword.requestFocus();
//        } else if (password.getText().equals("")) {
//            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
//                    "Password text field cannot be blank.");
//            password.requestFocus();
//        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
//            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
//                    "Password text field cannot be less than 5 and greator than 25 characters.");
//            password.requestFocus();
//        } else if (confirmPassword.getText().equals("")) {
//            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
//                    "Confirm password text field cannot be blank.");
//            confirmPassword.requestFocus();
//        } else if (confirmPassword.getText().length() < 5 || password.getText().length() > 25) {
//            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
//                    "Confirm password text field cannot be less than 5 and greator than 25 characters.");
//            confirmPassword.requestFocus();
//        } else if (!password.getText().equals(confirmPassword.getText())) {
//            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
//                    "Password and confirm password text fields does not match.");
//            password.requestFocus();
        } else if (isAlreadyRegistered()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "The username is already taken by someone else.");
            txtConfirmPassword.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private boolean clearForm() {
        txtFullName.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
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
