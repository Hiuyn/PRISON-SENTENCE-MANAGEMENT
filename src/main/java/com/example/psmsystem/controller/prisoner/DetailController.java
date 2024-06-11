package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.controller.MainPanelController;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetailController implements Initializable {

    @FXML
    private ImageView imgPrisonerAdd;

    @FXML
    private Label lbContactName;

    @FXML
    private Label lbContactPhone;

    @FXML
    private Label lbDOB;

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbGender;

    @FXML
    private Label lbIdentityCard;

    @FXML
    private Label lbPrisonerId;

    private int prisonerId;

    public void setPrisoner(int prisonerId) {
        this.prisonerId = prisonerId;
        getPrisonerInformation(this.prisonerId);
    }

    public void getPrisonerInformation(int prisonerId) {
        try {
            String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
            PrisonerDAO prisonerDAO = new PrisonerDAO();
            List<Prisoner> prisonerList = prisonerDAO.getAllPrisoner();
            String swPrisonerId = String.valueOf(prisonerId);
            Prisoner prisonerShow = new Prisoner();
            for (Prisoner prisoner : prisonerList) {
                if (prisoner.getPrisonerCode().equals(swPrisonerId))
                {
                    prisonerShow = prisoner;
                    break;
                }
            }


             String id = prisonerShow.getPrisonerCode();
             String prisonerName = prisonerShow.getPrisonerName();
             String DOB = prisonerShow.getDOB();
             int gender = prisonerShow.getGender();
             String identityCard = prisonerShow.getIdentityCard();
             String contactName = prisonerShow.getContactName();
             String contactPhone = prisonerShow.getContactPhone();
             String imagePath = prisonerShow.getImagePath();

            SimpleDateFormat formatDOBfromString = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = formatDOBfromString.parse(DOB);

            // Sử dụng SimpleDateFormat với định dạng "dd/MM/yyyy" để hiển thị DOB
            SimpleDateFormat formatDOB = new SimpleDateFormat("dd/MM/yyyy");
            String DOBShow = formatDOB.format(dateOfBirth);
            if (gender == 1) {
                lbGender.setText("Male");
            } else if (gender == 2) {
                lbGender.setText("FeMale");
            } else {
                lbGender.setText("Other");
            }
            File imageFile;
            if (imagePath != null && !imagePath.isEmpty()) {
                imageFile = new File(imagePath);
            } else {
                imageFile = new File(defaultPath);
            }
            Image image = new Image(imageFile.toURI().toString());

            imgPrisonerAdd.setImage(image);
             lbPrisonerId.setText(id);
             lbFullName.setText(prisonerName);
             lbContactName.setText(contactName);
             lbContactPhone.setText(contactPhone);

             lbIdentityCard.setText(identityCard);
            lbDOB.setText(DOBShow);
        }catch (Exception e){
            System.out.println("Error in getPrisonerInformation" + e.getMessage());
        }
    }

    public void moreInfo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Please go to the Sentence section");
        alert.showAndWait();
    }
    public void back(ActionEvent event) {
        Stage stage = (Stage) imgPrisonerAdd.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
