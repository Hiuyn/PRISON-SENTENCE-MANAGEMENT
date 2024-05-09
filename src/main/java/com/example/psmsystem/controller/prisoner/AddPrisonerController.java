package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class AddPrisonerController {
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
    private Label printTest;
    @FXML
    private TextField prisonerSexAdd;
    @FXML
    private TextField prisonerId;

    private String getRelativePath;
    String saveImagesPath = "src/main/resources/com/example/psmsystem/imagesPrisoner";

    public void setBtnAddPrisonerFinal(ActionEvent event) throws SQLException, IOException {
            if (imgPrisonerAdd.getImage() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Prisoner");
                alert.setHeaderText(null);
                alert.setContentText("Please select a prisoner image");
                alert.showAndWait();
            }
            PrisonerDAO prisonerDAO = new PrisonerDAO();
            int id = Integer.parseInt(prisonerId.getText());
            String fullName = txtPrisonerFNAdd.getText();
            LocalDate Dob = datePrisonerDOBAdd.getValue();
            String contactName = txtContactName.getText();
            String contactPhone = txtContactPhone.getText();

            Prisoner prisoner = new Prisoner();
            prisoner.setPrisonerId(String.valueOf(id));
            prisoner.setPrisonerName(fullName);
            prisoner.setImagePath("src/main/resources"+getRelativePath);
            prisonerDAO.insertPrisonerDB(prisoner);

    }

    // Tạo một biến để kiểm tra xem đã chọn ảnh hay chưa
    private boolean imageSelected = false;

    public String selectImageFile() throws SQLException, IOException {
        if (!imageSelected) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null) {
                // Lấy tên tệp từ đường dẫn ban đầu
                String fileName = selectedFile.getName();

                // Đường dẫn đến thư mục đích trong resources
                String destinationFolderPath = "/com/example/psmsystem/imagesPrisoner/";

                // Tạo một thư mục để lưu trữ hình ảnh trong thư mục tài nguyên (nếu chưa tồn tại)
                String relativePath = destinationFolderPath + fileName;

                // Copy file vào thư mục tài nguyên của ứng dụng
                try (InputStream inputStream = new FileInputStream(selectedFile)) {
                    OutputStream outputStream = new FileOutputStream(new File(Objects.requireNonNull(getClass().getResource(relativePath)).getFile()));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
//                    outputStream.close();
//                    inputStream.close();
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



}
