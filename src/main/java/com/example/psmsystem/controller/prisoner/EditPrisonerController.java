package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private Label lbPrisonerId;

    @FXML
    private Prisoner prisonerEdit;

    @FXML
    private CheckComboBox<String> ccbCrimes;

    @FXML
    private ToggleGroup tgGender;

    @FXML
    private RadioButton rbtnFemale;

    @FXML
    private RadioButton rbtnMale;

    @FXML
    private RadioButton rbtnOther;
    @FXML
    private ToggleGroup tgSentenceType;

    @FXML
    private RadioButton rbtnUnlimited;

    @FXML
    private RadioButton rbtnLimited;

    public void setPrisonerEdit(Prisoner prisoner) {
        this.prisonerEdit = prisoner;
        setInformation();
    }
    public void setInformation() {
        try {
            String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
            if (prisonerEdit != null) {
                String id = prisonerEdit.getPrisonerCode();
                List<Prisoner> prisonerList;
                PrisonerDAO prisonerDAO = new PrisonerDAO();
                prisonerList = prisonerDAO.getAllPrisoner();
                for (Prisoner prisoner : prisonerList) {
                    if (prisoner.getPrisonerCode().equals(id)) {
                        String name = prisoner.getPrisonerName();
                        String DOB = prisoner.getDOB();
                        int gender = prisoner.getGender();
                        String contactName = prisoner.getContactName();
                        String contactPhone = prisoner.getContactPhone();
                        String imagePath = prisoner.getImagePath();
                        lbPrisonerId.setText(id);
                        txtPrisonerFNAdd.setText(name);
                        datePrisonerDOBAdd.setValue(LocalDate.parse(DOB));
                        if (gender == 1) {
                            rbtnMale.setSelected(true);
                        } else if (gender == 2) {
                            rbtnFemale.setSelected(true);
                        } else {
                            rbtnOther.setSelected(true);
                        }
                        txtContactName.setText(contactName);
                        txtContactPhone.setText(contactPhone);
                        File imageFile;
                        if (imagePath != null && !imagePath.isEmpty()) {
                            imageFile = new File(imagePath);
                        } else {
                            imageFile = new File(defaultPath);
                        }
                        Image image = new Image(imageFile.toURI().toString());
                        imgPrisonerAdd.setImage(image);
                        lbPrisonerId.setVisible(false);
                    }
                }
            } else {
                    System.out.println("prisonerEdit is null");
                }
        }catch (Exception e)
        {
            System.out.println("Edit prisoner - setInformation: " + e.getMessage() );
        }


//            String name = prisonerEdit.getPrisonerName();
//            String DOB = prisonerEdit.getDOB();
//            int gender = prisonerEdit.getGender();
//            String contactName = prisonerEdit.getContactName();
//            String contactPhone = prisonerEdit.getContactPhone();
//            String imagePath = prisonerEdit.getImagePath();
//
//            lbPrisonerId.setText(String.valueOf(id));
//            txtPrisonerFNAdd.setText(name);
//            datePrisonerDOBAdd.setValue(LocalDate.parse(DOB));
//            if (gender == 1) {
//                rbtnMale.setSelected(true);
//            }
//            else if (gender == 2) {
//                rbtnFemale.setSelected(true);
//            }else
//            {
//                rbtnOther.setSelected(true);
//            }
//            txtContactName.setText(contactName);
//            txtContactPhone.setText(contactPhone);
//            File imageFile;
//            if (imagePath != null && !imagePath.isEmpty()) {
//                imageFile = new File(imagePath);
//            } else {
//                imageFile = new File(defaultPath);
//            }
//            Image image = new Image(imageFile.toURI().toString());
//            imgPrisonerAdd.setImage(image);
////            lbPrisonerId.setVisible(false);
    }
    public void back(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    public void setCbCrimes()
    {
        CrimeDao crimeDao = new CrimeDao();
        List<Crime> crimes;
        crimes = crimeDao.getCrime();
        List<String> crimesName = new ArrayList<>();
        for (Crime crime : crimes)
        {
            String name = crime.getCrimeName();
            crimesName.add(name);
        }
        ccbCrimes.getItems().addAll(crimesName);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tgGender = new ToggleGroup();
        tgSentenceType = new ToggleGroup();
        rbtnMale.setToggleGroup(tgGender);
        rbtnFemale.setToggleGroup(tgGender);
        rbtnOther.setToggleGroup(tgGender);
        rbtnLimited.setToggleGroup(tgSentenceType);
        rbtnUnlimited.setToggleGroup(tgSentenceType);
        setInformation();
        setCbCrimes();
    }
}
