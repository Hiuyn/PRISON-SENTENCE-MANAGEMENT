package com.example.psmsystem.controller.prisoner;


import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddPrisonerController implements Initializable {

    @FXML
    private TextField txtIdentityCard;
    @FXML
    private TextField txtContactName;

    @FXML
    private AnchorPane anchorPaneAddPrisoner;

    @FXML
    private Button btnAddImage;

    @FXML
    private Button btnAddPrisonerFinal;

    @FXML
    private ImageView imgPrisonerAdd;

    @FXML
    private TextField txtContactPhone;

    @FXML
    private DatePicker datePrisonerDOBAdd;

    @FXML
    private TextField txtPrisonerFNAdd;

    @FXML
    private ToggleGroup tgGender;

    @FXML
    private RadioButton rbtnFemale;

    @FXML
    private RadioButton rbtnMale;

    @FXML
    private RadioButton rbtnOther;

    @FXML
    private Label lbPrisonerId;

    @FXML
    private CheckComboBox<String> ccbCrimes;

    @FXML
    private ToggleGroup tgSentenceType;

    @FXML
    private RadioButton rbtnUnlimited;

    @FXML
    private RadioButton rbtnLimited;
    @FXML
    private Label lbSentenceId;

    @FXML
    private Button btnShowYearInput;
    @FXML
    private DatePicker dateIn;
    @FXML
    private DatePicker dateOut;
    private String getRelativePath;
    private int userId;
    private boolean imageSelected = false;
    private PrisonerController prisonerController;
    private List<Integer> selectedCrimesId;
    private int sentenceId;

    public void setBtnAddPrisonerFinal(ActionEvent event) {
            if (imgPrisonerAdd.getImage() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Prisoner");
                alert.setHeaderText(null);
                alert.setContentText("Please select a prisoner image");
                alert.showAndWait();
            }
            getPrisoner();
            getSentence();
            back(event);
            prisonerController.refreshPrisonerList();
    }

    public void setPrisonerController(PrisonerController prisonerController) {
        this.prisonerController = prisonerController;
    }
    public void getSentence()
    {

        RadioButton selectedSentenceType = (RadioButton) tgSentenceType.getSelectedToggle();
        new ArrayList<>(ccbCrimes.getCheckModel().getCheckedItems());
        String sentenceId = lbSentenceId.getText();
        String sentenceTypeText = selectedSentenceType.getText();
        LocalDate dateInPut = dateIn.getValue();
        Date startDate = Date.from(dateInPut.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }

    public void setIdSentence()
    {
        SentenceDao sentenceDao = new SentenceDao();
        int sentenceIdShow = sentenceDao.getMaxIdSentence();
        this.sentenceId = sentenceIdShow;
        lbSentenceId.setText(String.valueOf(sentenceIdShow));
    }
    public void setPrisonerId()
    {
        PrisonerDAO prisonerDAO = new PrisonerDAO();
        int prisonerIdDB = prisonerDAO.getIdEmpty();

        if ( prisonerIdDB < 10) {
            lbPrisonerId.setText("0000"+prisonerIdDB);
        }
        else if (prisonerIdDB < 100 && prisonerIdDB > 10) {
            lbPrisonerId.setText("000"+prisonerIdDB);
        }
        else if (prisonerIdDB > 100 && prisonerIdDB < 1000 )
        {
            lbPrisonerId.setText("00"+prisonerIdDB);
        }
        else if (prisonerIdDB > 1000 && prisonerIdDB < 10000 )
        {
            lbPrisonerId.setText("0"+prisonerIdDB);
        }
    }
    public void getPrisoner()
    {
        PrisonerDAO prisonerDAO = new PrisonerDAO();
        RadioButton selectedGender = (RadioButton) tgGender.getSelectedToggle();
        String selectedRadioButtonText = selectedGender.getText();
        int genderInputDb;
        if (selectedRadioButtonText.equals("Male"))
        {
            genderInputDb = 1;
        }
        else if (selectedRadioButtonText.equals("Female"))
        {
            genderInputDb = 2;
        }
        else
        {
            genderInputDb = 3;
        }
        String fullName = txtPrisonerFNAdd.getText();
        LocalDate Dob = datePrisonerDOBAdd.getValue();
        String contactName = txtContactName.getText();
        String contactPhone = txtContactPhone.getText();
        String identityCard = txtIdentityCard.getText();
        String code = lbPrisonerId.getText();
        boolean status = false;
        int userIdDb = this.userId;
        System.out.println("User Id Add: " + userIdDb);
        Prisoner prisoner = new Prisoner();
        prisoner.setPrisonerCode(String.valueOf(code));
        prisoner.setPrisonerName(fullName);
        prisoner.setDOB(String.valueOf(Dob));
        prisoner.setContactName(contactName);
        prisoner.setContactPhone(contactPhone);
        prisoner.setGender(genderInputDb);
        prisoner.setImagePath(getRelativePath);
        prisoner.setStatus(status);
        prisoner.setUser_id(userIdDb);
        prisoner.setIdentityCard(identityCard);
        prisonerDAO.insertPrisonerDB(prisoner);
    }

    public void setUserIdAdd(int userId)
    {
        this.userId=userId;
    }

    public String selectImageFile() {
        if (!imageSelected) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null) {
                // Lấy tên tệp từ đường dẫn ban đầu
                String fileName = selectedFile.getName();

                String destinationFolderPath = "src/main/resources/com/example/psmsystem/imagesPrisoner/";

                String relativePath = destinationFolderPath + fileName;
                File destFile = new File(relativePath);

                // Copy file vào thư mục tài nguyên của ứng dụng
                try (InputStream inputStream = new FileInputStream(selectedFile)) {
                    Files.copy(inputStream, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Tệp đã chọn: " + relativePath);
                Image image = new Image(selectedFile.toURI().toString());
                imgPrisonerAdd.setImage(image);
                imageSelected = true; // Đánh dấu rằng đã chọn ảnh
                getRelativePath = relativePath;
                return relativePath;
            } else {
                System.out.println("Không có tệp nào được chọn.");
            }
        }
        return null;
    }
    public void back(ActionEvent event)  {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
    }
    public void setCbCrimes()
    {
        CrimeDao crimeDao = new CrimeDao();
        List<Crime> crimeList = crimeDao.getCrime();
        List<String> crimes = new ArrayList<>();
        for (Crime crime : crimeList)
        {
            crimes.add(crime.getCrimeName());
        }
        ccbCrimes.getItems().addAll(crimes);
    }

public void getSelectedCrimes() {
    CrimeDao crimeDao = new CrimeDao();
    List<Crime> crimeList = crimeDao.getCrime();
    ObservableList<String> selectedCrimes = ccbCrimes.getCheckModel().getCheckedItems();
    System.out.println("Selected Crimes: " + selectedCrimes);
    List<Integer> idList = new ArrayList<>();
    for (String selectedCrimeName : selectedCrimes) {
        for (Crime crime : crimeList) {
            if (selectedCrimeName.equals(crime.getCrimeName())) {
                idList.add(crime.getCrimeId());
                System.out.println("Crime Id: " + crime.getCrimeId());
                break; // Đã tìm thấy tên tương ứng, thoát khỏi vòng lặp trong.
            }
        }
    }
    this.selectedCrimesId = idList;
}

public String convertSelectedToString(List<Integer> idList)
{
    StringJoiner joiner = new StringJoiner(",");
    for (Integer id : idList)
    {
        joiner.add(id.toString());
    }
    return joiner.toString();
}

    public void openInputYearWindow() {
        try {
            getSelectedCrimes();
            System.out.println("List id add window: " + this.selectedCrimesId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/psmsystem/view/prisoner/InputYearCrimesView.fxml"));
            AnchorPane newWindowContent = loader.load();

            InputYearCrimes controller = loader.getController();
            controller.getIdCrimes(this.selectedCrimesId, this.sentenceId);

            Stage newStage = new Stage();
            Scene scene = new Scene(newWindowContent);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Edit Prisoner");
            newStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }




    @FXML
    void loadInputYearCrimeView(ActionEvent event) {
        openInputYearWindow();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPrisonerId();
        setIdSentence();
        setCbCrimes();
        tgGender = new ToggleGroup();
        tgSentenceType = new ToggleGroup();

        rbtnMale.setToggleGroup(tgGender);
        rbtnFemale.setToggleGroup(tgGender);
        rbtnOther.setToggleGroup(tgGender);
        rbtnLimited.setToggleGroup(tgSentenceType);
        rbtnUnlimited.setToggleGroup(tgSentenceType);

    }
}
