package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.sentence.Sentence;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class InputYearCrimes implements Initializable {

    @FXML
    private VBox vbYOC;
    @FXML
    private VBox vbNameOC;
    @FXML
    private VBox vbNameCrime;

    @FXML
    private HBox hbYear;

    private List<Integer> idList;
    private List<TextField> textFieldList = new ArrayList<>();
    private int sentenceId;
    private List<Integer> getIdList;
    private Map<Integer, Integer> monthCrimeMap;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.idList != null) {
            getYearOfCrimes(this.idList);
        }

    }

    public void getIdCrimes(List<Integer> idList, int sentenceId) {
        this.idList = idList;
        this.sentenceId = sentenceId;
        // Gọi phương thức này để cập nhật giao diện nếu cần
        if (idList != null) {
            getYearOfCrimes(idList);
            System.out.println("id list" + this.idList);
            System.out.println("sentenceID YOC : " + this.sentenceId);
        }
        else
        {
            System.out.println("id list is null ");
        }
    }

//    public void insertMonthOfCrime(ActionEvent event)
//    {
//        SentenceDao sentenceDao = new SentenceDao();
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        VBox vBox = new VBox(10);
//        int totalMonth = 0;
//
//        for (TextField textField : textFieldList)
//        {
//            Label labelName = new Label();
//            int year = Integer.parseInt(textField.getText());
//            String name = textField.getPromptText();
//
//            labelName.setText(name +": " + year + " month");
//            totalMonth += year;
//            vBox.getChildren().addAll(labelName);
//        }
//        sentenceDao.insertSentenceCrimes();
//        System.out.println("Total month: " + totalMonth);
//        alert.getDialogPane().setContent(vBox);
//        alert.setOnCloseRequest(event1 -> {
//            back();
//        });
//
//        alert.showAndWait();
//    }
public void insertMonthOfCrime(ActionEvent event) {
    SentenceDao sentenceDao = new SentenceDao();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    VBox vBox = new VBox(10);
    int totalMonth = 0;
    for (int i = 0; i < textFieldList.size(); i++) {
        TextField textField = textFieldList.get(i);
        Label labelName = new Label();
        int year = Integer.parseInt(textField.getText());
        String name = textField.getPromptText();

        labelName.setText(name + ": " + year + " month");
        totalMonth += year;
        vBox.getChildren().addAll(labelName);

        int crimeId = idList.get(i); // Assuming getIdList stores crimeIds
        System.out.println("Crime id: " + crimeId + " year: " + year);
        this.monthCrimeMap.put(crimeId, year);
    }

    System.out.println("Total month: " + totalMonth);
    alert.getDialogPane().setContent(vBox);
    alert.setOnCloseRequest(event1 -> {
        back();
    });

    alert.showAndWait();
}

public Map<Integer, Integer> getMonthOfCrimes() {
        return this.monthCrimeMap;
}

    public void getYearOfCrimes(List<Integer> selectedCrimeIdList) {
        try {
            if (selectedCrimeIdList != null) {
                CrimeDao crimeDao = new CrimeDao();
                List<Crime> crimeList = crimeDao.getCrime();
                for (Crime crime : crimeList) {
                    for (int id : selectedCrimeIdList) {
                        if (id == crime.getCrimeId()) {
                            HBox hbYear = new HBox();
                            HBox hbName = new HBox();
                            TextField add = new TextField();
                            Label label = new Label();
                            String name = crime.getCrimeName();
                            label.setText(name);
                            add.setPromptText(name);
                            label.setPrefHeight(25);
                            label.setPrefWidth(120);
                            add.setPrefHeight(25);
                            add.setPrefWidth(120);
                            label.setMinHeight(25);
                            label.setMinWidth(120);
                            add.setMinHeight(25);
                            add.setMinWidth(120);
                            hbName.getChildren().add(label);
                            hbYear.getChildren().add(add);
                            vbNameCrime.getChildren().add(hbName);
                            vbYOC.getChildren().addAll(hbYear);
                            textFieldList.add(add);
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
    private void back() {
        Stage currentStage = (Stage) vbYOC.getScene().getWindow();
        currentStage.close();
    }
}
