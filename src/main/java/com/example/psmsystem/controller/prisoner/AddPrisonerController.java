package com.example.psmsystem.controller.prisoner;
import com.example.psmsystem.controller.DataStorage;
import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.sentence.Sentence;
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
import java.util.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

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
    @FXML
    private Button btnCheckIdentity;

    @FXML
    private Button btnEndDate;

    private String getRelativePath;
    private int userId;
    private boolean imageSelected = false;
    private PrisonerController prisonerController;
    private List<Integer> selectedCrimesId;
    private int sentenceId;
    private Prisoner prisoner;
    private Sentence sentence;
    private int prisonerId;

    public static boolean isPositiveInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return true;
    }


public void setBtnAddPrisonerFinal(ActionEvent event) {
    if (imgPrisonerAdd.getImage() == null) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Prisoner");
        alert.setHeaderText(null);
        alert.setContentText("Please select a prisoner image");
        alert.showAndWait();
        return;
    }
    if (getPrisoner()) {
        if (getSentence()) {
            SentenceDao sentenceDao = new SentenceDao();
            PrisonerDAO prisonerDao = new PrisonerDAO();
            prisonerDao.insertPrisonerDB(prisoner);
            sentenceDao.addSentence(this.sentence);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText("Add new information");
            alert1.setContentText("Add prisoner success!");
            alert1.showAndWait();
            back(event, () -> prisonerController.refreshPrisonerList());
        }
    }
}


    public void checkIdentityCard()
    {
        try {
            List<Prisoner> prisonerList;
            PrisonerDAO prisonerDAO = new PrisonerDAO();
            prisonerList = prisonerDAO.getAllPrisoner();
            boolean prisonerFound = false;
            String identityCard = txtIdentityCard.getText();
            if (identityCard.length() != 12 || !isPositiveInteger(identityCard)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Prisoner");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid identity card (12 integer only)!");
                alert.showAndWait();
                return;
            }

            for (Prisoner prisoner : prisonerList) {
                if (prisoner.getIdentityCard().equals(txtIdentityCard.getText())) {
                    prisonerFound = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Add Prisoner");
                    alert.setHeaderText("INFORMATION");
                    alert.setContentText("Prisoner already exists");
                    alert.showAndWait();
                    String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
                    String id = prisoner.getPrisonerCode();
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
                    }
                    else if (gender == 2) {
                        rbtnFemale.setSelected(true);
                    }else
                    {
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
//                    lbPrisonerId.setVisible(false);
                     break;
                }

            }
            if (!prisonerFound) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Prisoner");
                alert.setHeaderText("INFORMATION");
                alert.setContentText("Prisoner not already exists");
                alert.showAndWait();
                setPrisonerId();
//                txtPrisonerFNAdd.setText(null);
//                datePrisonerDOBAdd.setValue(null);
//                imgPrisonerAdd.setImage(null);
//                rbtnMale.setSelected(false);
//                rbtnFemale.setSelected(false);
//                rbtnOther.setSelected(false);
//                txtContactName.setText(null);
//                txtContactPhone.setText(null);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void setPrisonerController(PrisonerController prisonerController) {
        this.prisonerController = prisonerController;
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return new Date(calendar.getTimeInMillis());
    }
    public Map<Integer,Integer> getTimesOfCrimes()
    {
        return DataStorage.getCrimesTime();
    }

    public int calculateTotal(Map<Integer, Integer> map) {
        int total = 0;
        for (int value : map.values()) {
            total += value;
        }
        return total;
    }

public void setEndDate()
{
    RadioButton selectedSentenceType = (RadioButton) tgSentenceType.getSelectedToggle();
    String sentenceTypeText = selectedSentenceType.getText();
    int totalTime = calculateTotal(getTimesOfCrimes());
    if (dateIn.getValue()==null)
    {
        dateIn.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate startDate = newValue;
            LocalDate endDate = startDate.plusMonths(totalTime);
            dateOut.setValue(endDate);
        });
    }
    if (sentenceTypeText.equals("Life imprisonment")) {
        LocalDate  today = LocalDate.now();
        LocalDate unlimitedDate = today.minusYears(100);
        dateOut.setValue(unlimitedDate);
    } else if (sentenceTypeText.equals("limited time")) {
        System.out.println("Set end date");
    }
}

    public boolean getSentence()
    {
        try {
            RadioButton selectedSentenceType = (RadioButton) tgSentenceType.getSelectedToggle();
            if (selectedSentenceType == null) {
                showAlert("Sentence type must be selected");
                return false;
            }
            new ArrayList<>(ccbCrimes.getCheckModel().getCheckedItems());
            int sentenceCode = Integer.parseInt(lbSentenceId.getText());
//            String sentenceTypeText = convertSentenceType(selectedSentenceType.getText());
            String sentenceTypeText = selectedSentenceType.getText();
            System.out.println("Sentence type: " + sentenceTypeText);
            LocalDate dateInput = dateIn.getValue();


            if (dateInput == null || dateInput.isAfter(LocalDate.now())) {
                showAlert("Invalid start date");
                return false;
            }


            if (getTimesOfCrimes() == null) {
                showAlert("Select crime and input times");
                return false;
            }

            Date startDate = convertToDate(dateInput);
            System.out.println("Start Date: " + startDate);
            Date endDate = Date.valueOf(dateOut.getValue());
            System.out.println("End Date: " + startDate);
            int prisoner_id = this.prisonerId;
            System.out.println("Prisoner ID getSentence: " + prisoner_id);
            String parole = " ";
            String prisonerName = txtPrisonerFNAdd.getText();
            Date releaseDate = endDate;
            LocalDate now = LocalDate.now();
            Date updateDate = Date.valueOf(now);
            boolean status = false;
//            sentence = new Sentence(prisoner_id,prisonerName, sentenceCode, sentenceTypeText, startDate, endDate, releaseDate, status, parole, updateDate, userId);
//            this.sentence = sentence;
            return true;
        }catch (Exception e)
        {
            System.out.println("getSentence - addPrisonerController : " + e.getMessage());
        }
        return false;
    }

    public void setIdSentence()
    {
        SentenceDao sentenceDao = new SentenceDao();
        int sentenceIdShow = sentenceDao.getMaxIdSentence();
        this.sentenceId = sentenceIdShow;
        lbSentenceId.setText(String.valueOf(sentenceIdShow));
    }

    public String convertSentenceType (String sentenceTypeText)
    {
        String sentenceType = sentenceTypeText.toLowerCase();
        for (int i = 0; i < sentenceType.length(); i++) {
            if (sentenceType.charAt(i) == ' ') {
               sentenceType = sentenceType.replace(' ', '_');
            }
        }
        return sentenceType;
    }
    public void setPrisonerId()
    {
        PrisonerDAO prisonerDAO = new PrisonerDAO();
        int prisonerIdDB = prisonerDAO.getIdEmpty();
        this.prisonerId = prisonerIdDB;
        if ( prisonerIdDB < 10) {
            lbPrisonerId.setText("0000"+this.prisonerId);
        }
        else if (prisonerIdDB < 100 && prisonerIdDB > 10) {
            lbPrisonerId.setText("000"+this.prisonerId);
        }
        else if (prisonerIdDB > 100 && prisonerIdDB < 1000 )
        {
            lbPrisonerId.setText("00"+this.prisonerId);
        }
        else if (prisonerIdDB > 1000 && prisonerIdDB < 10000 )
        {
            lbPrisonerId.setText("0"+this.prisonerId);
        }
    }

    public boolean getPrisoner() {
        try {
            PrisonerDAO prisonerDAO = new PrisonerDAO();
            RadioButton selectedGender = (RadioButton) tgGender.getSelectedToggle();
            String selectedRadioButtonText = selectedGender.getText();
            int genderInputDb;
            if (selectedRadioButtonText.equals("Male")) {
                genderInputDb = 1;
            } else if (selectedRadioButtonText.equals("Female")) {
                genderInputDb = 2;
            } else {
                genderInputDb = 3;
            }
            String fullName = txtPrisonerFNAdd.getText();
            LocalDate dob = datePrisonerDOBAdd.getValue();
            String contactName = txtContactName.getText();
            String contactPhone = txtContactPhone.getText();
            String identityCard = txtIdentityCard.getText();
            String code = lbPrisonerId.getText();
            boolean status = false;
            int userIdDb = this.userId;
            System.out.println("User Id Add: " + userIdDb);

            if (!identityCard.matches("\\d{12}")) {
                showAlert("Invalid identity card");
                return false;
            }

            if (!fullName.matches("[\\p{L}]+(\\s+[\\p{L}]+)*"
            )) {
                showAlert("Invalid prisoner name");
                return false;
            }

            if (!contactName.matches("[\\p{L}]+(\\s+[\\p{L}]+)*"
            )) {
                showAlert("Invalid contact name");
                return false;
            }
            // Kiểm tra ngày sinh
            LocalDate currentDate = LocalDate.now();
            LocalDate eighteenYearsAgo = currentDate.minusYears(18);
            if (!dob.isBefore(eighteenYearsAgo)) {
                showAlert("Prisoner must be at least 18 years old");
                return false;
            }

            // Kiểm tra số điện thoại
            if (!contactPhone.matches("^0[0-9]{9}$")) {
                showAlert("Invalid contact phone");
                return false;
            }
            Prisoner prisoner = new Prisoner();
            prisoner.setPrisonerCode(String.valueOf(code));
            prisoner.setPrisonerName(fullName);
            prisoner.setDOB(String.valueOf(dob));
            prisoner.setContactName(contactName);
            prisoner.setContactPhone(contactPhone);
            prisoner.setGender(genderInputDb);
            prisoner.setImagePath(getRelativePath);
            prisoner.setStatus(status);
            prisoner.setUser_id(userIdDb);
            prisoner.setIdentityCard(identityCard);
            this.prisoner = prisoner;
            return true;
        } catch (Exception e) {
            showAlert("An error occurred while adding prisoner information");
            System.out.println("getPrisoner - AddPrisonerController ; " + e.getMessage());
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


    public interface Callback {
        void execute();
    }

    public void back(ActionEvent event, Callback callback)  {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        System.out.println("Cửa sổ đã được đóng");
        if (callback != null) {
            System.out.println("Thực hiện callback");
            callback.execute();
        }
    }

    public void backPrisoner(ActionEvent event)
    {
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
        RadioButton selectedSentenceType = (RadioButton) tgSentenceType.getSelectedToggle();
        String sentenceTypeText = selectedSentenceType.getText();
        if (sentenceTypeText.equals("limited time"))
        {
//            Date dateInValue = Date.valueOf(dateIn.getValue());
            int totalTime = calculateTotal(getTimesOfCrimes());
//            Date calculatedDateOut = addMonths(dateInValue, totalTime);
//            dateOut.setValue(calculatedDateOut.toLocalDate());
            dateOut.setDisable(true);

        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPrisonerId();
        setIdSentence();
        setCbCrimes();
        LocalDate currentDate = LocalDate.now();
        LocalDate eighteenYearsAgo = currentDate.minusYears(18);
        datePrisonerDOBAdd.setValue(eighteenYearsAgo);
//        dateOut.setVisible(false);
//        dateOut.setDisable(true);
        lbPrisonerId.setVisible(false);
        tgGender = new ToggleGroup();
        tgSentenceType = new ToggleGroup();
        rbtnMale.setToggleGroup(tgGender);
        rbtnFemale.setToggleGroup(tgGender);
        rbtnOther.setToggleGroup(tgGender);
        rbtnLimited.setToggleGroup(tgSentenceType);
        rbtnUnlimited.setToggleGroup(tgSentenceType);
    }
}
