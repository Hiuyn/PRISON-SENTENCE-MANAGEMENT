package com.example.psmsystem.controller.ManagementVisit;

import com.example.psmsystem.helper.AlertHelper;
import com.example.psmsystem.model.managementvisit.IManagementVisitDao;
import com.example.psmsystem.model.managementvisit.ManagementVisit;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.managementvisitDao.ManagementVisitDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.stage.Window;

public class ManagementVisitController implements Initializable {
    private static IPrisonerDao<Prisoner> prisonerDao;
    private static IManagementVisitDao<ManagementVisit> managementVisitDao;

    @FXML
    private TableView<ManagementVisit> dataTable;

    @FXML
    private DatePicker dateVisitDate;

    @FXML
    private ComboBox<Prisoner> filterCombo;

    @FXML
    private TableColumn<ManagementVisit, String> noteColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<ManagementVisit, String> prisonercodeColumn;

    @FXML
    private TableColumn<ManagementVisit, String> relationshipColumn;

    @FXML
    private TableColumn<ManagementVisit, String> cccdColumn;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtcccd;

    @FXML
    private TextField txtRelationship;

    @FXML
    private TextField txtVisitorName;

    @FXML
    private TextArea txtaNote;

    @FXML
    private TableColumn<ManagementVisit, String> visitdateColumn;

    @FXML
    private TableColumn<ManagementVisit, String> visitnameColumn;

    private final int itemsPerPage = 20;

    Integer index;
    Window window;
    int visitationId;

    ObservableList<ManagementVisit> listTable = FXCollections.observableArrayList();
//    ObservableList<Prisoner> getPrisonerList = FXCollections.observableArrayList();

    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prisonerDao = new PrisonerDAO();
        managementVisitDao = new ManagementVisitDao();

        listTable.addAll(managementVisitDao.getManagementVisits());
        dataTable.setFixedCellSize(37);
        StringConverter<Prisoner> converter = FunctionalStringConverter.to(prisoner -> (prisoner == null) ? "" : prisoner.getPrisonerCode() + ": " + prisoner.getPrisonerName());
//        Function<String, Predicate<Prisoner>> filterFunction = s -> prisoner -> StringUtils.containsIgnoreCase(converter.toString(prisoner), s);
        filterCombo.setItems(prisonerDao.getPrisonerName());
        filterCombo.setConverter(converter);
//        filterCombo.setFilterFunction(filterFunction);

        loadDataTable();
        setupPagination();
        initUI();
        setupSearch();
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
        dateVisitDate.setConverter(converter);
    }

    private void loadDataTable() {
        prisonercodeColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerCode"));
        visitnameColumn.setCellValueFactory(new PropertyValueFactory<>("visitorName"));
        cccdColumn.setCellValueFactory(new PropertyValueFactory<>("nationalIdentificationNumber"));
        relationshipColumn.setCellValueFactory(new PropertyValueFactory<>("relationship"));
        visitdateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
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

    @FXML
    void onClean(ActionEvent event) {
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        txtVisitorName.clear();
        txtcccd.clear();
        txtRelationship.clear();
        dateVisitDate.setValue(null);
        dateVisitDate.setPromptText("YYYY-MM-DD");
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
        String visitorName = txtVisitorName.getText();
        String cccd = txtcccd.getText();
        String relationship = txtRelationship.getText();
        String note = txtaNote.getText();
        LocalDate selectedDate = dateVisitDate.getValue();
        String date = selectedDate.toString();

        ManagementVisit mv = new ManagementVisit(prisonerCode, visitorName, cccd, relationship, date, note);
        managementVisitDao.addManagementVisit(mv);
        listTable.add(mv);
        dataTable.setItems(listTable);

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success", "Visit created successfully.");

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

            LocalDate selectedDate = dateVisitDate.getValue();
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

            // Hiển thị hộp thoại xác nhận
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Delete visit!");
            confirmationDialog.setContentText("Are you sure you want to delete this visit?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationDialog.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Xử lý phản hồi của người dùng
            if (result.isPresent() && result.get() == okButton) {
                managementVisitDao.deleteManagementVisit(visitationId);
                ManagementVisit selected = dataTable.getSelectionModel().getSelectedItem();
                listTable.remove(selected);
                dataTable.setItems(listTable);
                resetValue();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                        "Visit deleted successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "An error occurred while deleting the visit.");
        }
    }

    @FXML
    void onEdit(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Prisoner selectedValue = filterCombo.getValue();

        String prisonerCode = selectedValue.getPrisonerCode();
        String visitorName = txtVisitorName.getText();
        String cccd = txtcccd.getText();
        String relationship = txtRelationship.getText();
        String note = txtaNote.getText();
        LocalDate selectedDate = dateVisitDate.getValue();

        String date = selectedDate.toString();

        if (visitationId == -1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No visit found for the selected prisoner and date.");
            return;
        }

        ManagementVisit mv = new ManagementVisit(prisonerCode, visitorName, cccd, relationship, date, note);
        managementVisitDao.updateManagementVisit(mv, visitationId);

        index = dataTable.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            ManagementVisit visit = listTable.get(index);
            visit.setPrisonerCode(prisonerCode);
            visit.setVisitorName(visitorName);
            visit.setNationalIdentificationNumber(cccd);
            visit.setRelationship(relationship);
            visit.setVisitDate(date);
            visit.setNotes(note);

            dataTable.setItems(listTable);
            dataTable.refresh();
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                    "Visit updated successfully.");

            onClean(event);
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No visit selected.");
        }
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
        txtVisitorName.setText(visitnameColumn.getCellData(index).toString());
        txtcccd.setText(cccdColumn.getCellData(index).toString());
        txtRelationship.setText(relationshipColumn.getCellData(index).toString());

        LocalDate date = LocalDate.parse(visitdateColumn.getCellData(index).toString());
        dateVisitDate.setValue(date);
        LocalDate selectedDate = dateVisitDate.getValue();
        String dateString = selectedDate.toString();
        txtaNote.setText(noteColumn.getCellData(index).toString());

        visitationId = managementVisitDao.getVisitationId(prisonerCode, dateString);

    }
    private void resetValue(){
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        txtVisitorName.clear();
        txtcccd.clear();
        txtRelationship.clear();
        dateVisitDate.setValue(null);
        dateVisitDate.setPromptText("YYYY-MM-DD");
        txtaNote.clear();
    }

    private void setupSearch() {
        FilteredList<ManagementVisit> filteredData = new FilteredList<>(listTable, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(managementVisit -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (managementVisit.getPrisonerCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (managementVisit.getVisitorName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (managementVisit.getNationalIdentificationNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (managementVisit.getRelationship().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (managementVisit.getVisitDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (managementVisit.getNotes().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            updatePagination(filteredData);
        });

        dataTable.setItems(filteredData);
    }

    private void updatePagination(FilteredList<ManagementVisit> filteredData) {
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
        LocalDate selectedDate = dateVisitDate.getValue();
        Prisoner selectedPrisoner = filterCombo.getValue();

        if (filterCombo.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Please select a prisoner.");
            filterCombo.requestFocus();
            return false;
        }
        if (txtVisitorName.getText() == null || txtVisitorName.getText().trim().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Visitor Name is required.");
            txtVisitorName.requestFocus();
            return false;
        }
        if (txtcccd.getText() == null || txtcccd.getText().trim().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "CCCD is required.");
            txtcccd.requestFocus();
            return false;
        }
        if (txtRelationship.getText() == null || txtRelationship.getText().trim().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Relationship is required.");
            txtRelationship.requestFocus();
            return false;
        }
        if (dateVisitDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Visit Date is required.");
            dateVisitDate.requestFocus();
            return false;
        } else if (dateVisitDate.getValue().isAfter(LocalDate.now())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Visit Date cannot be in the future.");
            dateVisitDate.requestFocus();
            return false;
        }

        if (hasVisitorThisMonth(selectedPrisoner.getPrisonerCode(), selectedDate)) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Prisoner code number " + selectedPrisoner.getPrisonerCode() + " has had visitors this month.");
            filterCombo.requestFocus();
            return false;
        }

        return true;
    }

    private boolean hasVisitorThisMonth(String prisonerCode, LocalDate visitDate) {
        LocalDate startOfMonth = visitDate.withDayOfMonth(1);
        LocalDate endOfMonth = visitDate.withDayOfMonth(visitDate.lengthOfMonth());

        for (ManagementVisit visit : managementVisitDao.getManagementVisits()) {
            if (visit.getPrisonerCode().equals(prisonerCode)) {
                LocalDate visitLocalDate = LocalDate.parse(visit.getVisitDate());
                if ((visitLocalDate.isEqual(startOfMonth) || visitLocalDate.isAfter(startOfMonth)) &&
                        (visitLocalDate.isEqual(endOfMonth) || visitLocalDate.isBefore(endOfMonth))) {
                    return true;
                }
            }
        }

        return false;
    }
}
