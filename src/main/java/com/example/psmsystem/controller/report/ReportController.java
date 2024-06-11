package com.example.psmsystem.controller.report;

import com.example.psmsystem.dto.Consider;
import com.example.psmsystem.service.sentenceDao.SentenceService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ReportController implements Initializable {
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

    private SentenceService sentenceService = new SentenceService();
    private HashMap<String, List<Consider>> result;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        sentenceIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSentenceId()).asObject());
        healthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHealth()));
        commendationSumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCommendationSum()).asObject());
        disciplinarySumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDisciplinaryMeasureSum()).asObject());

        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.isEmpty()) {
                    setGraphic(null);
                } else {
                    Image image = new Image(item, true);
                    image.errorProperty().addListener((obs, oldError, newError) -> {
                        if (newError) {
                            imageView.setImage(new Image("/static/images.png"));
                        }
                    });
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    private void handleChoiceBoxAction() {
        String selectedKey = choiceBox.getSelectionModel().getSelectedItem().split(":")[0].trim();
        if (selectedKey != null) {
            updateTableView(selectedKey);
        }
    }

    private void updateTableView(String key) {
        selectedKeyLabel.setText("Selected Key: " + key);
        dataTable.getItems().clear();

        List<Consider> considers = result.get(key);
        if (considers != null && !considers.isEmpty()) {
            dataTable.getItems().addAll(considers);
        }
    }
}
