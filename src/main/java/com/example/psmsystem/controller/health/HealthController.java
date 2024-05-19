package com.example.psmsystem.controller.health;

import com.example.psmsystem.helper.AlertHelper;
import com.example.psmsystem.model.health.Health;
import com.example.psmsystem.model.health.IHealthDao;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.healthDao.HealthDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class HealthController implements Initializable {
    private static IPrisonerDao<Prisoner> prisonerDao;
    private static IHealthDao<Health> healthDao;

    @FXML
    private TableColumn<Health, String> checkupDateColumn;

    @FXML
    private TableView<Health> dataTable;

    @FXML
    private DatePicker dateCheckupDate;

//    @FXML
//    private ComboBox<Prisoner> filterCombo;

    @FXML
    private SearchableComboBox<Prisoner> filterCombo;

    @FXML
    private TableColumn<Health, Double> heightColumn;

    @FXML
    private TableColumn<Health, String> noteColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<Health, String> physicalConditionColumn;

    @FXML
    private TableColumn<Health, String> prisonercodeColumn;

    @FXML
    private TableColumn<Health, String> prisonernameColumn;

    @FXML
    private TableColumn<Health, String> psychologicalSignsColumn;

    @FXML
    private TableColumn<Health, String> situationColumn;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtPhysicalCondition;

    @FXML
    private TextField txtPsychological;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSituation;

    @FXML
    private TextField txtWeight;

    @FXML
    private TextArea txtaNote;

    @FXML
    private TableColumn<Health, Double> weightColumn;

    private final int itemsPerPage = 20;

    Integer index;
    Window window;
    int visitationId;

    ObservableList<Health> listTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prisonerDao = new PrisonerDAO();
        healthDao = new HealthDao();

        listTable.addAll(healthDao.getHealth());
        dataTable.setFixedCellSize(37);
        StringConverter<Prisoner> converter = FunctionalStringConverter.to(prisoner -> (prisoner == null) ? "" : prisoner.getPrisonerCode() + ": " + prisoner.getPrisonerName());
//        Function<String, Predicate<Prisoner>> filterFunction = s -> prisoner -> StringUtils.containsIgnoreCase(converter.toString(prisoner), s);
        filterCombo.setItems(prisonerDao.getPrisonerName());
        filterCombo.setConverter(converter);
//        filterCombo2.setItems(prisonerDao.getPrisonerName());
//        filterCombo2.setConverter(converter);
//        filterCombo.setFilterFunction(filterFunction);

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

        for (Prisoner prisoner : filterCombo.getItems()) {
            if (prisoner.getPrisonerCode().contains(prisonercodeColumn.getCellData(index))) {
                filterCombo.setValue(prisoner);
                break;
            }
        }
        Prisoner selectedValue = filterCombo.getValue();
        String prisonerCode = selectedValue.getPrisonerCode();
        txtWeight.setText(weightColumn.getCellData(index).toString());
        txtHeight.setText(heightColumn.getCellData(index).toString());
        LocalDate date = LocalDate.parse(checkupDateColumn.getCellData(index).toString());
        dateCheckupDate.setValue(date);
        LocalDate selectedDate = dateCheckupDate.getValue();
        String dateString = selectedDate.toString();
        txtPhysicalCondition.setText(physicalConditionColumn.getCellData(index).toString());
        txtPsychological.setText(psychologicalSignsColumn.getCellData(index).toString());
        txtSituation.setText(situationColumn.getCellData(index).toString());

        txtaNote.setText(noteColumn.getCellData(index).toString());

        visitationId = healthDao.getVisitationId(prisonerCode, dateString);
    }

    private void resetValue(){
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        txtWeight.clear();
        txtHeight.clear();
        txtPhysicalCondition.clear();
        dateCheckupDate.setValue(null);
        dateCheckupDate.setPromptText("YYYY-MM-DD");
        txtPsychological.clear();
        txtSituation.clear();
        txtaNote.clear();
    }

    private void initUI() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Tạo StringConverter tùy chỉnh để chuyển đổi giữa LocalDate và String
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
        prisonercodeColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerCode"));
        prisonernameColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerName"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        checkupDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkupDate"));
        physicalConditionColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCondition"));
        psychologicalSignsColumn.setCellValueFactory(new PropertyValueFactory<>("psychologicalSigns"));
        situationColumn.setCellValueFactory(new PropertyValueFactory<>("situation"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

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

                if (health.getPrisonerCode().toLowerCase().contains(lowerCaseFilter)) {
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
                else if (health.getPhysicalCondition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (health.getPsychologicalSigns().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (health.getSituation().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (health.getNotes().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
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
        filterCombo.setPromptText("Select Prisoner ID");
        txtWeight.clear();
        txtHeight.clear();
        txtPhysicalCondition.clear();
        dateCheckupDate.setValue(null);
        dateCheckupDate.setPromptText("YYYY-MM-DD");
        txtPsychological.clear();
        txtSituation.clear();
        txtaNote.clear();

        dataTable.getSelectionModel().clearSelection();
    }

    @FXML
    void onCreate(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Prisoner selectedValue = filterCombo.getValue();
        String prisonerCode = selectedValue.getPrisonerCode();
        String prisonerName = selectedValue.getPrisonerName();
        Double weight = Double.valueOf(txtWeight.getText());
        Double height = Double.valueOf(txtHeight.getText());
        String physicalCondition = txtPhysicalCondition.getText();
        String psychological = txtPsychological.getText();
        String situation = txtSituation.getText();
        String note = txtaNote.getText();
        LocalDate selectedDate = dateCheckupDate.getValue();
        String date = selectedDate.toString();

        Health health = new Health(prisonerCode, prisonerName, weight, height, date, physicalCondition, psychological, situation, note);
        healthDao.addHealth(health);
        listTable.add(health);
        dataTable.setItems(listTable);

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success", "Health created successfully.");

        onClean(event);
    }

    @FXML
    void onDelete(ActionEvent event) {
        try {
            Prisoner selectedValue = filterCombo.getValue();
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

        Prisoner selectedValue = filterCombo.getValue();

        String prisonerCode = selectedValue.getPrisonerCode();
        String prisonerName = selectedValue.getPrisonerName();
        Double weight = Double.valueOf(txtWeight.getText());
        Double height = Double.valueOf(txtHeight.getText());
        String physicalCondition = txtPhysicalCondition.getText();
        String psychological = txtPsychological.getText();
        String situation = txtSituation.getText();
        String note = txtaNote.getText();
        LocalDate selectedDate = dateCheckupDate.getValue();

        String date = selectedDate.toString();

        if (visitationId == -1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No visit found for the selected prisoner and date.");
            return;
        }

        Health mv = new Health(prisonerCode, prisonerName, weight, height, date, physicalCondition, psychological, situation, note);
        healthDao.updateHealth(mv, visitationId);

        index = dataTable.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Health health = listTable.get(index);
            health.setPrisonerCode(prisonerCode);
            health.setWeight(weight);
            health.setHeight(height);
            health.setCheckupDate(date);
            health.setPhysicalCondition(physicalCondition);
            health.setPsychologicalSigns(psychological);
            health.setSituation(situation);
            health.setNotes(note);

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
}
