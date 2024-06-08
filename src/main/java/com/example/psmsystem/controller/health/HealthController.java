package com.example.psmsystem.controller.health;

import com.example.psmsystem.helper.AlertHelper;
import com.example.psmsystem.model.health.Health;
import com.example.psmsystem.model.health.IHealthDao;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.sentence.ISentenceDao;
import com.example.psmsystem.model.sentence.Sentence;
import com.example.psmsystem.service.healthDao.HealthDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class HealthController implements Initializable {
    private static IPrisonerDao<Prisoner> prisonerDao;
    private static IHealthDao<Health> healthDao;
    private ISentenceDao<Sentence> sentenceDao;

    @FXML
    private TableColumn<Health, String> checkupDateColumn;

    @FXML
    private TableView<Health> dataTable;

    @FXML
    private DatePicker dateCheckupDate;

    @FXML
    private ComboBox<String> cbLevel;

    @FXML
    private SearchableComboBox<Sentence> filterCombo;

    @FXML
    private TableColumn<Health, Double> heightColumn;

    @FXML
    private TableColumn<Health, String> noteColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<Health, Integer> levelColumn;

    @FXML
    private TableColumn<Health, String> prisonercodeColumn;

    @FXML
    private TableColumn<Health, String> healthcodeColumn;

    @FXML
    private TableColumn<Health, String> prisonernameColumn;

    @FXML
    private TableColumn<Health, Boolean> statusColumn;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtWeight;

    @FXML
    private TableColumn<Health, Double> weightColumn;

    private final int itemsPerPage = 20;

    Integer index;
    Window window;
    int visitationId;
    private final Map<Integer, String> levelMap = new HashMap<>();
    ObservableList<Health> listTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prisonerDao = new PrisonerDAO();
        healthDao = new HealthDao();
        sentenceDao = new SentenceDao();

        listTable.addAll(healthDao.getHealth());
        dataTable.setFixedCellSize(37);

        StringConverter<Sentence> converter = FunctionalStringConverter.to(sentence -> (sentence == null) ? "" : sentence.getSentenceCode() + ": " + sentence.getPrisonerName());
        filterCombo.setItems(sentenceDao.getPrisonerName());
        filterCombo.setConverter(converter);

        cbLevel.setItems(FXCollections.observableArrayList("no illness", "mild", "severe", "requires intervention"));

        cbLevel.getSelectionModel().select("no illness");

        levelMap.put(0, "no illness");
        levelMap.put(1, "mild");
        levelMap.put(2, "severe");
        levelMap.put(3, "requires intervention");

        loadDataTable();
        setupPagination();
        initUI();
        setupSearch();
    }

    @FXML
    void getItem(MouseEvent event) {
        index = dataTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        for (Sentence sentence : filterCombo.getItems()) {
            if (sentence.getSentenceCode().contains(prisonercodeColumn.getCellData(index))) {
                filterCombo.setValue(sentence);
                break;
            }
        }
        Sentence selectedValue = filterCombo.getValue();
        String prisonerId = selectedValue.getPrisonerId();
        txtWeight.setText(weightColumn.getCellData(index).toString());
        txtHeight.setText(heightColumn.getCellData(index).toString());
        LocalDate date = LocalDate.parse(checkupDateColumn.getCellData(index).toString());
        dateCheckupDate.setValue(date);
        LocalDate selectedDate = dateCheckupDate.getValue();
        String dateString = selectedDate.toString();
        cbLevel.setValue(levelColumn.getCellData(index).toString());

        Integer levelDescription = Integer.parseInt(levelColumn.getCellData(index).toString());

        String levelValue = levelMap.get(levelDescription);
        if (levelValue != null) {
            cbLevel.setValue(levelValue);
        } else {
            System.out.println("Level description not found in map.");
        }

        visitationId = healthDao.getVisitationId(prisonerId, dateString);
    }

    private void resetValue(){
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Sentence Code");
        txtWeight.clear();
        txtHeight.clear();
        dateCheckupDate.setValue(null);
        dateCheckupDate.setPromptText("YYYY-MM-DD");
        cbLevel.getSelectionModel().select("no illness");
    }

    private void initUI() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dateCheckupDate.setConverter(converter);
    }

    private void loadDataTable() {
        healthcodeColumn.setCellValueFactory(new PropertyValueFactory<>("healthCode"));
        prisonercodeColumn.setCellValueFactory(new PropertyValueFactory<>("sentenceCode"));
        prisonernameColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerName"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        checkupDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkupDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        statusColumn.setCellFactory(col -> new TableCell<Health, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "sick" : "healthy");
                }
            }
        });
        levelColumn.setCellFactory(col -> new TableCell<Health, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item) {
                        case 0:
                            setText("no illness");
                            break;
                        case 1:
                            setText("mild");
                            break;
                        case 2:
                            setText("severe");
                            break;
                        default:
                            setText("requires intervention");
                            break;
                    }
                }
            }
        });

        dataTable.setItems(listTable);
        dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) listTable.size() / itemsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, listTable.size());
        dataTable.setItems(FXCollections.observableArrayList(listTable.subList(fromIndex, toIndex)));
        return new BorderPane(dataTable);
    }

    private void setupSearch() {
        FilteredList<Health> filteredData = new FilteredList<>(listTable, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(health -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (health.getSentenceCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (health.getPrisonerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (String.valueOf(health.getWeight()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (String.valueOf(health.getHeight()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (health.getCheckupDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                else if (lowerCaseFilter.contains("healthy") && !health.getStatus()) {
                    return true; // Tìm kiếm theo status: healthy
                }
                else if (lowerCaseFilter.contains("sick") && health.getStatus()) {
                    return true; // Tìm kiếm theo status: sick
                }
                else {
                    Integer levelValue = health.getLevel();
                    String levelString = levelMap.get(levelValue);
                    if (levelString != null && levelString.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            updatePagination(filteredData);
        });

        dataTable.setItems(filteredData);
    }

    private void updatePagination(FilteredList<Health> filteredData) {
        int pageCount = (int) Math.ceil((double) filteredData.size() / itemsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * itemsPerPage;
            int toIndex = Math.min(fromIndex + itemsPerPage, filteredData.size());
            dataTable.setItems(FXCollections.observableArrayList(filteredData.subList(fromIndex, toIndex)));
            return new BorderPane(dataTable);
        });
    }

    private boolean isValidate() {
        if (filterCombo.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Please select a prisoner.");
            filterCombo.requestFocus();
            return false;
        }
        if (txtWeight.getText() == null || txtWeight.getText().trim().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Weight is required.");
            txtWeight.requestFocus();
            return false;
        }
        if (txtHeight.getText() == null || txtHeight.getText().trim().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Height is required.");
            txtHeight.requestFocus();
            return false;
        }
        if (dateCheckupDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Visit Date is required.");
            dateCheckupDate.requestFocus();
            return false;
        }
        return true;
    }

    @FXML
    void onClean(ActionEvent event) {
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Sentence Code");
        txtWeight.clear();
        txtHeight.clear();
        dateCheckupDate.setValue(null);
        dateCheckupDate.setPromptText("YYYY-MM-DD");
        cbLevel.getSelectionModel().select("no illness");;

        dataTable.getSelectionModel().clearSelection();
    }

    @FXML
    void onCreate(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Sentence selectedValue = filterCombo.getValue();
        String prisonerId = selectedValue.getPrisonerId();
        String sentenceCode = selectedValue.getSentenceCode();
        String sentenceId = String.valueOf(sentenceDao.getSentenceId(sentenceCode));

        String prisonerName = selectedValue.getPrisonerName();
        Double weight = Double.valueOf(txtWeight.getText());
        Double height = Double.valueOf(txtHeight.getText());
        Boolean status = false;
        String level = cbLevel.getValue();

        LocalDate selectedDate = dateCheckupDate.getValue();
        String date = selectedDate.toString();

        Integer levelValue = null;
        for (Map.Entry<Integer, String> entry : levelMap.entrySet()) {
            if (entry.getValue().equals(level)) {
                levelValue = entry.getKey();
            }
        }
        String healthCode = getHealthCode();

        Health health = new Health(healthCode, prisonerId, sentenceId, sentenceCode, prisonerName, weight, height, date, status, levelValue);
        healthDao.addHealth(health);
        listTable.add(health);
        dataTable.setItems(listTable);

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success", "Health created successfully.");

        onClean(event);
    }

    @FXML
    void onDelete(ActionEvent event) {
        try {
            Sentence selectedValue = filterCombo.getValue();
            if (selectedValue == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Please select a prisoner.");
                return;
            }

            LocalDate selectedDate = dateCheckupDate.getValue();
            if (selectedDate == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Please select a visit date.");
                return;
            }

            if (visitationId == -1) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "No visit found for the selected prisoner and date.");
                return;
            }

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Delete health!");
            confirmationDialog.setContentText("Are you sure you want to delete it?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationDialog.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = confirmationDialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                healthDao.deleteHealth(visitationId);
                Health selected = dataTable.getSelectionModel().getSelectedItem();
                listTable.remove(selected);
                dataTable.setItems(listTable);
                resetValue();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                        "Health deleted successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "An error occurred while deleting the health.");
        }
    }

    @FXML
    void onEdit(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Sentence selectedValue = filterCombo.getValue();
        String prisonerId = selectedValue.getPrisonerId();
        String sentenceCode = selectedValue.getSentenceCode();
        String sentenceId = String.valueOf(sentenceDao.getSentenceId(sentenceCode));

        String prisonerName = selectedValue.getPrisonerName();
        Double weight = Double.valueOf(txtWeight.getText());
        Double height = Double.valueOf(txtHeight.getText());
        Boolean status = false;
        String level = cbLevel.getValue();

        LocalDate selectedDate = dateCheckupDate.getValue();
        String date = selectedDate.toString();

        Integer levelValue = null;
        for (Map.Entry<Integer, String> entry : levelMap.entrySet()) {
            if (entry.getValue().equals(level)) {
                levelValue = entry.getKey();
            }
        }

        if (visitationId == -1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No visit found for the selected prisoner and date.");
            return;
        }
        index = dataTable.getSelectionModel().getSelectedIndex();

        Health selectedHealth = dataTable.getItems().get(index);
        String hearthCode = healthcodeColumn.getCellData(selectedHealth);
        Health mv = new Health(hearthCode, prisonerId, sentenceId, sentenceCode, prisonerName, weight, height, date, status, levelValue);
        healthDao.updateHealth(mv, visitationId);


        if (index >= 0) {
            Health health = listTable.get(index);
            health.setPrisonerId(prisonerId);
            health.setSentenceId(sentenceId);
            health.setSentenceCode(sentenceCode);
            health.setPrisonerName(prisonerName);
            health.setWeight(weight);
            health.setHeight(height);
            health.setCheckupDate(date);
            health.setStatus(status);
            health.setLevel(levelValue);

            dataTable.setItems(listTable);
            dataTable.refresh();
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                    "Health updated successfully.");

            onClean(event);
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No health selected.");
        }
    }

    private String getHealthCode(){
        int code = healthDao.getCountHealth();
        code ++;
        return "H"+code;
    }
}
