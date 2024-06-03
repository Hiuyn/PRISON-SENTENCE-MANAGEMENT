package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.sentence.Sentence;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
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
    private Label txtPrisonerId;

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
