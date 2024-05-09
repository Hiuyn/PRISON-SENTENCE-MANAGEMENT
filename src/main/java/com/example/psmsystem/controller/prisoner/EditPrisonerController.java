package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditPrisonerController implements Initializable {

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
    private TextField txtPrisonerId;

    private Prisoner prisonerEdit;


//    public void setPrisonerEdit( Prisoner prisonerEdit)
//    {
////        ItemPrisonerViewController itemPrisonerViewController = new ItemPrisonerViewController();
////        Prisoner prisonerShowEdit =itemPrisonerViewController.getPrisonerShowEdit();
//        String id= prisonerEdit.getPrisonerId();
//        String name = prisonerEdit.getPrisonerId();
//
//        txtPrisonerId.setText(id);
//        txtPrisonerFNAdd.setText(name);
//
//    }

    public void setPrisonerEdit(Prisoner prisoner) {
        this.prisonerEdit = prisoner;

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sử dụng thông tin về tù nhân cần chỉnh sửa để cập nhật giao diện
        if (this.prisonerEdit != null) {
            txtPrisonerId.setText(this.prisonerEdit.getPrisonerId());
            txtPrisonerFNAdd.setText(this.prisonerEdit.getPrisonerName());
            // Cập nhật các trường thông tin khác tương ứng
        }
        else
        {
            System.out.println("Edit prisoner edit: "+ null);
        }
    }
}
