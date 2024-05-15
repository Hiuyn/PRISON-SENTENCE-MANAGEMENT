package com.example.psmsystem.controller.health;

import com.example.psmsystem.model.health.Health;
import com.example.psmsystem.model.health.IHealthDao;
import com.example.psmsystem.model.managementvisit.ManagementVisit;
import com.example.psmsystem.service.health.HealthDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HealthController implements Initializable {
    private static IHealthDao<Health> healthDao;

    @FXML
    private TableColumn<Health, String> checkupDateColumn;

    @FXML
    private TableView<Health> dataTable;

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
    private TableColumn<Health, String> psychologicalSignsColumn;

    @FXML
    private TableColumn<Health, String> situationColumn;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Health, Double> weightColumn;

    private final int itemsPerPage = 20;

    ObservableList<Health> listTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        healthDao = new HealthDao();
        listTable.addAll(healthDao.getHealth());
        dataTable.setFixedCellSize(37);

        loadDataTable();
        setupPagination();
//        initUI();
        setupSearch();
    }

    @FXML
    void getItem(MouseEvent event) {

    }

//    private void initUI() {
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        // Tạo StringConverter tùy chỉnh để chuyển đổi giữa LocalDate và String
//        StringConverter<LocalDate> converter = new StringConverter<>() {
//            @Override
//            public String toString(LocalDate date) {
//                if (date != null) {
//                    return dateFormatter.format(date);
//                } else {
//                    return "";
//                }
//            }
//
//            @Override
//            public LocalDate fromString(String string) {
//                if (string != null && !string.isEmpty()) {
//                    return LocalDate.parse(string, dateFormatter);
//                } else {
//                    return null;
//                }
//            }
//        };
//        dateVisitDate.setConverter(converter);
//    }

    private void loadDataTable() {
        prisonercodeColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerCode"));
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
            filteredData.setPredicate(managementVisit -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (managementVisit.getPrisonerCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
//                else if (managementVisit.getVisitorName().toLowerCase().contains(lowerCaseFilter)) {
//                    return true;
//                }
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
}
