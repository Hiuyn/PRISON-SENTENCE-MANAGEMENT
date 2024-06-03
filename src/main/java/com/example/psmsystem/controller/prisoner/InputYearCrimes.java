package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InputYearCrimes implements Initializable {

    @FXML
    private VBox vbYOC;

    @FXML
    private VBox vbNameCrime;

    @FXML
    private HBox hbYear;

    private List<Integer> idList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.idList != null) {
            getYearOfCrimes(this.idList);
        }

    }

    public void getIdCrimes(List<Integer> idList) {
        this.idList = idList;
        // Gọi phương thức này để cập nhật giao diện nếu cần
        if (idList != null) {
            getYearOfCrimes(idList);
            System.out.println("id list" + this.idList);
        }
        else
        {
            System.out.println("id list is null ");
        }
    }

    public void getYearOfCrimes(List<Integer> selectedCrimeIdList) {
        try {
            if (selectedCrimeIdList != null) {
                CrimeDao crimeDao = new CrimeDao();
                List<Crime> crimeList = crimeDao.getCrime();
                for (Crime crime : crimeList) {
                    for (int id : selectedCrimeIdList) {
                        if (id == crime.getCrimeId()) {
                            TextField add = new TextField();
                            Label label = new Label();
                            label.setText(crime.getCrimeName());
                            add.setPromptText(crime.getCrimeName());
                            HBox hbYear = new HBox(10);
                            hbYear.getChildren().addAll(add,label);
//                            vbNameCrime.getChildren().add(label);
                            vbYOC.getChildren().add(hbYear);
                        }
                    }
                }
            } else {
                System.out.println("selectedCrimeIdList is null. Make sure it is initialized before calling getYearOfCrimes().");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void back(ActionEvent event) {
        Stage currentStage = (Stage) vbYOC.getScene().getWindow();
        currentStage.close();
    }
}
