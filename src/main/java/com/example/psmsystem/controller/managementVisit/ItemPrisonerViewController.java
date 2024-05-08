package com.example.psmsystem.controller.managementVisit;

import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;


public class ItemPrisonerViewController {

    @FXML
    private AnchorPane ItemPrisoner;
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

    public void setPrisonerItem(Prisoner prisoner) {

        String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
        String id = prisoner.getPrisonerId();
        String name = prisoner.getPrisonerName();
        String imagePath = prisoner.getImagePath();
        int idShow = Integer.parseInt(id);
        try {
            File imageFile;
            if (imagePath != null && !imagePath.isEmpty()) {
                imageFile = new File(imagePath);
            } else {
                imageFile = new File(defaultPath);
            }

            Image image = new Image(imageFile.toURI().toString());
                if (idShow < 10) {
                    prisonerId.setText("00"+idShow);
                }
                else if (idShow < 100) {
                    prisonerId.setText("0"+idShow);
                }
                else
                {
                    prisonerId.setText(""+idShow);
                }

            prisonerName.setText(name);
            imagePrisoner.setImage(image);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void btnEditOnAction(ActionEvent actionEvent) {}

    public void btnDetailOnAction(ActionEvent actionEvent) {}

    public void btnDeleteOnAction(ActionEvent actionEvent) {}

}
