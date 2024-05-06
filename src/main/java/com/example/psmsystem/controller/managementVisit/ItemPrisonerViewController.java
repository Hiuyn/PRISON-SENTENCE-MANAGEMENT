package com.example.psmsystem.controller.managementVisit;

import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;


public class ItemPrisonerViewController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDetail;

    @FXML
    private Button btnEdit;

    @FXML
    private ImageView imagePrisoner;
    @FXML
    private Label testImage;
    @FXML
    private Label prisonerId;

    @FXML
    private Label prisonerName;

    public void setPrisoner(Prisoner prisoner) {

        String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
        String id = prisoner.getPrisonerId();
        String name = prisoner.getPrisonerName();
        String  imagePath = prisoner.getImagePrisonerPath();

        prisonerId.setText(id);
        prisonerName.setText(name);
//        testImage.setText(imagePath);

        File imageFile = new File(imagePath);
        File imageDefault = new File(defaultPath);
        if (imageFile.exists()) {
            imagePrisoner.setImage(new Image(imageFile.toURI().toString()));
        } else {
            imagePrisoner.setImage(new Image(imageDefault.toURI().toString()));
        }
    }

    public void btnEditOnAction(ActionEvent actionEvent) {}

    public void btnDetailOnAction(ActionEvent actionEvent) {}

    public void btnDeleteOnAction(ActionEvent actionEvent) {}

}
