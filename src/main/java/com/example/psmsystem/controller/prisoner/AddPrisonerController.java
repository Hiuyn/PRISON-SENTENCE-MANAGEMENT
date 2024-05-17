package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddPrisonerController implements Initializable {

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
    private String getRelativePath;

    private boolean imageSelected = false;

    String saveImagesPath = "src/main/resources/com/example/psmsystem/imagesPrisoner";



    public void setBtnAddPrisonerFinal(ActionEvent event) throws SQLException, IOException {
            if (imgPrisonerAdd.getImage() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Prisoner");
                alert.setHeaderText(null);
                alert.setContentText("Please select a prisoner image");
                alert.showAndWait();
            }
            getPrisoner();
            getSentence();
    }

    public void getSentence()
    {
        PrisonerDAO prisonerDAO = new PrisonerDAO();

        RadioButton selectedSentenceType = (RadioButton) tgSentenceType.getSelectedToggle();
        String sentenceTypeText = selectedSentenceType.getText();
        new ArrayList<>(ccbCrimes.getCheckModel().getCheckedItems());

    }

    public void setIdSentence()
    {
        String sentenceIdShow = lbPrisonerId.getText();
        lbSentenceId.setText(sentenceIdShow);
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
        String fullName = txtPrisonerFNAdd.getText();
        LocalDate Dob = datePrisonerDOBAdd.getValue();
        String contactName = txtContactName.getText();
        String contactPhone = txtContactPhone.getText();
        String code = lbPrisonerId.getText();
        boolean status = true;
        Prisoner prisoner = new Prisoner();
        prisoner.setPrisonerCode(String.valueOf(code));
        prisoner.setPrisonerName(fullName);
        prisoner.setDOB(String.valueOf(Dob));
        prisoner.setContactName(contactName);
        prisoner.setContactPhone(contactPhone);
        prisoner.setGender(selectedRadioButtonText);
        prisoner.setImagePath(getRelativePath);
        prisoner.setStatus(status);
        prisonerDAO.insertPrisonerDB(prisoner);
    }
    public String selectImageFile() throws SQLException, IOException {
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
                    e.printStackTrace();
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
    public void back(ActionEvent event) throws IOException {
//            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PrisonerView.fxml"));
//            Parent root = fxmlLoader.load();
            // Lấy stage hiện tại từ sự kiện
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

    }
    public void setCbCrimes()
    {
        PrisonerDAO prisonerDAO = new PrisonerDAO();
        List<String> crimes;
        crimes = prisonerDAO.getCrimes();
        ccbCrimes.getItems().addAll(crimes);
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
