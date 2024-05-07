package com.example.psmsystem.controller.managementVisit;

import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddPrisonerController {
    @FXML
    private TextField placeOriginAdd;

    @FXML
    private AnchorPane anchorPaneAddPrisoner;

    @FXML
    private Button btnAddImage;

    @FXML
    private Button btnAddPrisonerFinal;

    @FXML
    private ImageView imgPrisonerAdd;

    @FXML
    private TextField placeResidenceAdd;

    @FXML
    private DatePicker prisonerDOBAdd;

    @FXML
    private TextField prisonerFNAdd;

    @FXML
    private TextField prisonerLNAdd;

    @FXML
    private Label printTest;
    @FXML
    private TextField prisonerSexAdd;

    @FXML
    private TextField prisonerId;
    public void setBtnAddPrisonerFinal(ActionEvent event) throws SQLException, IOException {
        PrisonerDAO prisonerDAO = new PrisonerDAO();
        int id = Integer.parseInt(prisonerId.getText());
        String firstName = prisonerFNAdd.getText();
        String lastName = prisonerLNAdd.getText();
        LocalDate Dob = prisonerDOBAdd.getValue();
        String placeOfOrigin = placeOriginAdd.getText();
        String placeOfResidence = placeResidenceAdd.getText();

//        String formattedDob = Dob != null ? Dob.toString() : "N/A";
//        printTest.setText(formattedDob);

        Prisoner prisoner = new Prisoner();
        prisoner.setPrisonerId(String.valueOf(id));
        prisoner.setPrisonerName(lastName);
        selectImageFile(prisoner);
//        Blob image = selectImageFile(prisoner.)
//        prisoner.setDateOfBirth(dob);
//        prisoner.setPlaceOfOrigin(placeOfOrigin);
//        prisoner.setPlaceOfResidence(placeOfResidence);
        prisonerDAO.insertPrisonerDB(prisoner);
    }

    public void selectImageFile(Prisoner prisoner) throws SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgPrisonerAdd.setImage(image);
            prisoner.setImagePath(selectedFile.getAbsolutePath());
            System.out.println("Tệp đã chọn: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Không có tệp nào được chọn.");
        }
    }

}
