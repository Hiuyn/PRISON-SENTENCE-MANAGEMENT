package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
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


//    public void setPrisonerEdit( Prisoner prisonerEdit)
//    {
////        ItemPrisonerViewController itemPrisonerViewController = new ItemPrisonerViewController();
////        Prisoner prisonerShowEdit =itemPrisonerViewController.getPrisonerShowEdit();
//        String id= prisonerEdit.getPrisonerId();
//        String name = prisonerEdit.getPrisonerId();
//
//        txtPrisonerId.setText(id);
//        txtPrisonerFNAdd.setText(name);
//
//    }

    public void setPrisonerEdit(Prisoner prisoner) {

        this.prisonerEdit = prisoner;
        setInformation();

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
