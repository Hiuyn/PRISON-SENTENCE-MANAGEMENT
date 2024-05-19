package com.example.psmsystem.controller.assess;

import com.example.psmsystem.helper.AlertHelper;
import com.example.psmsystem.model.assess.Assess;
import com.example.psmsystem.model.assess.IAssessDao;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.assessDao.AssessDao;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AssessController implements Initializable {
    private IPrisonerDao<Prisoner> prisonerDao;
    private IAssessDao<Assess> assessDao;

    @FXML
    private ComboBox<String> cbEventType;

    @FXML
    private TableView<Assess> dataTable;

    @FXML
    private DatePicker dateEventDate;

    @FXML
    private TableColumn<Assess, String> desctiptionColumn;

    @FXML
    private TableColumn<Assess, String> eventDateColumn;

    @FXML
    private TableColumn<Assess, String> eventTypeColumn;

    @FXML
    private SearchableComboBox<Prisoner> filterCombo;

    @FXML
    private TableColumn<Assess, String> noteColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<Assess, String> prisonerCodeColumn;

    @FXML
    private TableColumn<Assess, String> prisonerNameColumn;

    @FXML
    private TextField txtDesctiption;

    @FXML
    private TextField txtNote;

    @FXML
    private TextField txtSearch;
    private final int itemsPerPage = 12;

    Integer index;
    Window window;
    int visitationId;

    ObservableList<Assess> listTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prisonerDao = new PrisonerDAO();
        assessDao = new AssessDao();

        listTable.addAll(assessDao.getAssess());
        dataTable.setFixedCellSize(42);

        StringConverter<Prisoner> converter = FunctionalStringConverter.to(prisoner -> (prisoner == null) ? "" : prisoner.getPrisonerCode() + ": " + prisoner.getPrisonerName());
        filterCombo.setItems(prisonerDao.getPrisonerName());
        filterCombo.setConverter(converter);

        cbEventType.setItems(FXCollections.observableArrayList("Bonus", "Participate in renovation", "Vbreach of discipline"));
        cbEventType.getSelectionModel().select("Participate in renovation");

        loadDataTable();
        setupPagination();
        initUI();
        setupSearch();
    }

    private void loadDataTable() {
        prisonerCodeColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerCode"));
        prisonerNameColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        desctiptionColumn.setCellValueFactory(new PropertyValueFactory<>("desctiption"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        dataTable.setItems(listTable);
        dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupSearch() {
        FilteredList<Assess> filteredData = new FilteredList<>(listTable, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(assess -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (assess.getPrisonerCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (assess.getPrisonerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (assess.getEventDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (assess.getEventType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (assess.getDesctiption().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (assess.getNote().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
            updatePagination(filteredData);
        });

        dataTable.setItems(filteredData);
    }

    private void updatePagination(FilteredList<Assess> filteredData) {
        int pageCount = (int) Math.ceil((double) filteredData.size() / itemsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * itemsPerPage;
            int toIndex = Math.min(fromIndex + itemsPerPage, filteredData.size());
            dataTable.setItems(FXCollections.observableArrayList(filteredData.subList(fromIndex, toIndex)));
            return new BorderPane(dataTable);
        });
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
        dateEventDate.setConverter(converter);
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

    private boolean isValidate() {
        if (filterCombo.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Please select a prisoner.");
            filterCombo.requestFocus();
            return false;
        }

        if (dateEventDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Event Date is required.");
            dateEventDate.requestFocus();
            return false;
        }
        return true;
    }


    @FXML
    void getItem(MouseEvent event) {
        index = dataTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        for (Prisoner prisoner : filterCombo.getItems()) {
            if (prisoner.getPrisonerCode().contains(prisonerCodeColumn.getCellData(index))) {
                filterCombo.setValue(prisoner);
                break;
            }
        }
        Prisoner selectedValue = filterCombo.getValue();
        String prisonerCode = selectedValue.getPrisonerCode();
        LocalDate date = LocalDate.parse(eventDateColumn.getCellData(index).toString());
        dateEventDate.setValue(date);
        cbEventType.setValue(eventTypeColumn.getCellData(index).toString());
        txtDesctiption.setText(desctiptionColumn.getCellData(index).toString());
        txtNote.setText(noteColumn.getCellData(index).toString());

        LocalDate selectedDate = dateEventDate.getValue();
        String dateString = selectedDate.toString();

        visitationId = assessDao.getAssessId(prisonerCode, dateString);
    }

    @FXML
    void onClean(ActionEvent event) {
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        cbEventType.getSelectionModel().select("Participate in renovation");
        txtDesctiption.clear();
        txtNote.clear();
        dateEventDate.setValue(null);
        dateEventDate.setPromptText("YYYY-MM-DD");

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
        String eventType = cbEventType.getValue();
        LocalDate selectedStartDate = dateEventDate.getValue();
        String eventDate = selectedStartDate.toString();
        String desctiption = txtDesctiption.getText();
        String note = txtNote.getText();

        Assess assess = new Assess(prisonerCode, prisonerName, eventDate, eventType, desctiption, note);
        assessDao.addAssess(assess);
        listTable.add(assess);
        dataTable.setItems(listTable);

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success", "Assess created successfully.");

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

            if (visitationId == -1) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "No sentence found for the selected prisoner.");
                return;
            }

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Delete assess!");
            confirmationDialog.setContentText("Are you sure you want to delete it?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationDialog.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = confirmationDialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                Assess selected = dataTable.getSelectionModel().getSelectedItem();

                if (selected != null) {

                        assessDao.deleteAssess(visitationId);
                        listTable.remove(selected);
                        dataTable.setItems(listTable);
                        resetValue();
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                                "Assess deleted successfully.");
                    }



            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "An error occurred while deleting the sentence.");
        }
    }

    @FXML
    void onEdit(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Prisoner selectedValue = filterCombo.getValue();
        String eventType = cbEventType.getValue();

        String prisonerCode = selectedValue.getPrisonerCode();
        String prisonerName = selectedValue.getPrisonerName();
        LocalDate selectedStartDate = dateEventDate.getValue();
        String eventDate = selectedStartDate.toString();

        String desctiption = txtDesctiption.getText();
        String note = txtNote.getText();

        if (visitationId == -1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No sentence found for the selected prisoner.");
            return;
        }

        Assess sentence = new Assess(prisonerCode, prisonerName, eventDate, eventType, desctiption, note);
        assessDao.updateAssess(sentence, visitationId);

        index = dataTable.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Assess s = listTable.get(index);
            s.setPrisonerCode(prisonerCode);
            s.setPrisonerName(prisonerName);
            s.setEventDate(eventDate);
            s.setEventType(eventType);
            s.setDesctiption(desctiption);
            s.setNote(note);

            dataTable.setItems(listTable);
            dataTable.refresh();
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                    "Assess updated successfully.");

            onClean(event);
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No assess selected.");
        }
    }

    private void resetValue(){
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        cbEventType.getSelectionModel().select("Participate in renovation");
        txtDesctiption.clear();
        txtNote.clear();
        dateEventDate.setValue(null);
        dateEventDate.setPromptText("YYYY-MM-DD");
    }
}
