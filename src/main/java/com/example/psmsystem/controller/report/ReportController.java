package com.example.psmsystem.controller.report;

import com.example.psmsystem.dto.Consider;
import com.example.psmsystem.model.report.IReportDao;
import com.example.psmsystem.model.report.Report;
import com.example.psmsystem.service.reportDao.ReportDao;
import com.example.psmsystem.service.sentenceDao.SentenceService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ReportController implements Initializable {
    private ReportDao reportDao = new ReportDao();
    @FXML
    private TableView<Consider> dataTable;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Label selectedKeyLabel;

    @FXML
    private TableColumn<Consider, String> imageColumn;
    @FXML
    private TableColumn<Consider, String> prisonerNameColumn;
    @FXML
    private TableColumn<Consider, String> identityCardColumn;
    @FXML
    private TableColumn<Consider, Integer> sentenceCodeColumn;
    @FXML
    private TableColumn<Consider, Integer> sentenceIdColumn;
    @FXML
    private TableColumn<Consider, String> healthColumn;
    @FXML
    private TableColumn<Consider, Integer> commendationSumColumn;
    @FXML
    private TableColumn<Consider, Integer> disciplinarySumColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private Label txtUpdate;

    private SentenceService sentenceService = new SentenceService();
    private HashMap<String, List<Consider>> result;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        dataTable.setFixedCellSize(37);
        txtUpdate.setText("Data updated on: " + reportDao.timeUpdate());

        result = sentenceService.classify();

        ObservableList<String> keyList = FXCollections.observableArrayList();
        for (Map.Entry<String, List<Consider>> entry : result.entrySet()) {
            String key = entry.getKey();
            int size = entry.getValue().size();
            String label = key + ": " + size;
            keyList.add(label);
        }
        choiceBox.setItems(keyList);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedKey = newValue.split(":")[0].trim();
                updateTableView(selectedKey);
            }
        });

        setUpTableView();
    }

    private void setUpTableView() {
        imageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        prisonerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrisonerName()));
        identityCardColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentityCard()));
        sentenceCodeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSentenceCode()).asObject());
//        sentenceIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSentenceId()).asObject());
        healthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHealth()));
        commendationSumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCommendationSum()).asObject());
        disciplinarySumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDisciplinaryMeasureSum()).asObject());

//        imageColumn.setCellFactory(column -> new TableCell<>() {
//            private final ImageView imageView = new ImageView();
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null || item.isEmpty()) {
//                    setGraphic(null);
//                } else {
//                    Image image = new Image(item, true);
//                    image.errorProperty().addListener((obs, oldError, newError) -> {
//                        if (newError) {
//                            imageView.setImage(new Image("/static/images.png"));
//                        }
//                    });
//                    imageView.setImage(image);
//                    imageView.setFitWidth(100);
//                    imageView.setFitHeight(100);
//                    setGraphic(imageView);
//                }
//            }
//        });
    }

    @FXML
    private void handleChoiceBoxAction() {
        String selectedKey = choiceBox.getSelectionModel().getSelectedItem().split(":")[0].trim();
        if (selectedKey != null) {
            updateTableView(selectedKey);
        }
    }

    private void updateTableView(String key) {
//        selectedKeyLabel.setText("Selected Key: " + key);
        dataTable.getItems().clear();

        List<Consider> considers = result.get(key);
        if (considers != null && !considers.isEmpty()) {
            dataTable.getItems().addAll(considers);
        }
    }

    @FXML
    void onUpdate(ActionEvent event) {
//        List<Report> reports = reportDao.getAll();
        Date currentDate = new Date();

        // Định dạng ngày theo chuẩn "YYYY-MM-DD hh:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        // Xử lý thưởng
//        for (Report report : reports) {
//            if (report.getTotalReward() == 25 && report.getLevel() == 4 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 0) {
//                // Giảm án 1 năm
//                report.setParoleEligibility("Reduce sentence by 1 year");
//                reportDao.updateSentence(report.getSentenceCode(), subtractYear(report.getReleaseDate(), 1), "Reduce sentence by 1 year");
//            } else if (report.getTotalReward() == 25 && report.getLevel() == 3 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 0) {
//                // Giảm án 6 tháng
//                report.setParoleEligibility("Reduce sentence by 6 months");
//                reportDao.updateSentence(report.getSentenceCode(), subtractMonth(report.getReleaseDate(), 6), "Reduce sentence by 6 months");
//            } else if (report.getTotalReward() == 30 && report.getLevel() == 2 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 0) {
//                // Giảm án 1 tháng
//                report.setParoleEligibility("Reduce sentence by 1 month");
//                reportDao.updateSentence(report.getSentenceCode(), subtractMonth(report.getReleaseDate(), 1), "Reduce sentence by 1 month");
//            } else if (report.getTotalReward() == 20 && report.getLevel() == 1 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 2) {
//                // Tặng quà
//                report.setParoleEligibility("Gift award");
//                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "Gift award");
//            }
//        }
//
//        // Xử lý phạt
//        for (Report report : reports) {
//            if (report.getTotalReward() >= 5 && report.getTotalDiscipline() <= 10 && report.getLevel() == 1 && (report.getLevel() == 1 || report.getLevel() == 2 || report.getLevel() == 3)) {
//                // Phạt 12 giờ lao động
//                report.setParoleEligibility("12-hour labor penalty");
//                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "12-hour labor penalty");
//            } else if (report.getTotalDiscipline() >= 11 && report.getTotalDiscipline() <= 15 && (report.getLevel() == 1 || report.getLevel() == 2) && (report.getLevel() == 1 || report.getLevel() == 2 || report.getLevel() == 3)) {
//                // Phạt 36 giờ lao động
//                report.setParoleEligibility("36-hour labor penalty");
//                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "36-hour labor penalty");
//            } else if (report.getTotalDiscipline() >= 16 && report.getTotalDiscipline() <= 20 && (report.getLevel() == 1 || report.getLevel() == 2 ) && (report.getLevel() == 1 || report.getLevel() == 2 || report.getLevel() == 3)) {
//                // Phạt không gặp người thân và lao động 48 tiếng
//                report.setParoleEligibility("No meeting with relatives and 48-hour labor penalty");
//                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "No meeting with relatives and 48-hour labor penalty");
//            } else if ((report.getTotalDiscipline() == 20 && (report.getLevel() == 2 || report.getLevel() == 3)) || (report.getTotalDiscipline() == 10 && report.getLevel() == 4)) {
//                // Biệt giam
//                report.setParoleEligibility("Solitary confinement");
//                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "Solitary confinement");
//            }
//        }

        // Cập nhật lại dữ liệu sau khi xử lý
//        loadReports();
        txtUpdate.setText("Data updated on: " + formattedDate);
        reportDao.updateUpdateLog(currentDate);
    }
}
