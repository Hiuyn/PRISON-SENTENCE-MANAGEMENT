package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPrisonerController  implements Initializable {

    @FXML
    private Button btnAddImage;

    @FXML
    private Button btnUpdate;

    @FXML
    private DatePicker datePrisonerDOBAdd;

    @FXML
    private ImageView imgPrisonerAdd;

    @FXML
    private Label printTest;

    @FXML
    private TextField txtContactName;

    @FXML
    private TextField txtContactPhone;

    @FXML
    private TextField txtCrimes;

    @FXML
    private TextField txtPhysicalCondition;

    @FXML
    private TextField txtPrisonerFNAdd;

    @FXML
    private TextField txtPrisonerId;

    private Prisoner prisonerEdit;

    public void setPrisonerEdit(Prisoner prisoner) {
        this.prisonerEdit = prisoner;
        setInformation();
    }
    public void back(ActionEvent event) throws IOException {
//            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PrisonerView.fxml"));
//            Parent root = fxmlLoader.load();
        // Lấy stage hiện tại từ sự kiện
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Set scene mới
        currentStage.close();
    }
    public void setInformation() {
        String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
        if (prisonerEdit != null) {
            String imagePath = prisonerEdit.getImagePath();
            File imageFile;
            if (imagePath != null && !imagePath.isEmpty()) {
                imageFile = new File(imagePath);
            } else {
                imageFile = new File(defaultPath);
            }
            Image image = new Image(imageFile.toURI().toString());
            txtPrisonerId.setText(prisonerEdit.getPrisonerCode());
            txtPrisonerFNAdd.setText(prisonerEdit.getPrisonerName());
            imgPrisonerAdd.setImage(image);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInformation();
    }
}
