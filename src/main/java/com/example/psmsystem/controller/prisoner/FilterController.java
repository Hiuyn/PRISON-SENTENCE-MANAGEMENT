package com.example.psmsystem.controller.prisoner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class FilterController {

    @FXML
    private TextField txtIdentityCard;

    @FXML
    private TextField txtPrisonerName;

    @FXML
    private DatePicker datePrisonerDOB;

    @FXML
    private RadioButton rbtnMale;

    @FXML
    private RadioButton rbtnFemale;

    @FXML
    private RadioButton rbtnOther;

    @FXML
    private TextField txtContactName;

    @FXML
    private TextField txtContactPhone;

    @FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    private void onFilter() {
        // Implement your filter logic here
        // Example: Just showing an alert with entered information
        String identityCard = txtIdentityCard.getText();
        String prisonerName = txtPrisonerName.getText();
        String dob = (datePrisonerDOB.getValue() != null) ? datePrisonerDOB.getValue().toString() : "";
        String gender = ((RadioButton) genderToggleGroup.getSelectedToggle()).getText();
        String contactName = txtContactName.getText();
        String contactPhone = txtContactPhone.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Filter Information");
        alert.setHeaderText(null);
        alert.setContentText(
                "Identity Card: " + identityCard + "\n" +
                        "Prisoner Name: " + prisonerName + "\n" +
                        "Date of Birth: " + dob + "\n" +
                        "Gender: " + gender + "\n" +
                        "Contact Name: " + contactName + "\n" +
                        "Contact Phone: " + contactPhone
        );
        alert.showAndWait();
    }



    public void back(ActionEvent event) {
        Stage stage = (Stage) txtIdentityCard.getScene().getWindow();
        stage.close();
    }
}
