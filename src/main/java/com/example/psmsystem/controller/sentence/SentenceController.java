package com.example.psmsystem.controller.sentence;

import com.example.psmsystem.helper.AlertHelper;
import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.crime.ICrimeDao;
import com.example.psmsystem.model.health.Health;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.sentence.ISentenceDao;
import com.example.psmsystem.model.sentence.Sentence;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SentenceController implements Initializable {
    private IPrisonerDao<Prisoner> prisonerDao;
    private ICrimeDao<Crime> crimeDao;
    private ISentenceDao<Sentence> sentenceDao;

    @FXML
    private ComboBox<String> cbSentenceType;

    @FXML
    private CheckComboBox<Crime> ccbSentenceCode;

    @FXML
    private DatePicker dateEndDate;

    @FXML
    private DatePicker dateStartDate;

    @FXML
    private TableColumn<Sentence, String> endDateColumn;

    @FXML
    private SearchableComboBox<Sentence> filterCombo;

    @FXML
    private TableColumn<Sentence, String> paroleEligibilityColumn;

    @FXML
    private TableColumn<Sentence, String> prisonerCodeColumn;

    @FXML
    private TableColumn<Sentence, String> prisonerNameColumn;

    @FXML
    private TableColumn<Sentence, String> crimesColumn;

    @FXML
    private TableColumn<Sentence, String> sentenceTypeColumn;

    @FXML
    private TableColumn<Sentence, String> startDateColumn;

    @FXML
    private TableColumn<Sentence, String> statusColumn;

    @FXML
    private TableView<Sentence> dataTable;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtParoleEligibility;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtYear;

    @FXML
    private Pagination pagination;

    private final int itemsPerPage = 10;

    Integer index;
    Window window;
    int visitationId;

    ObservableList<Sentence> listTable = FXCollections.observableArrayList();
    ObservableList<Crime> listCrime = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prisonerDao = new PrisonerDAO();
        crimeDao = new CrimeDao();
        sentenceDao = new SentenceDao();

        listTable.addAll(sentenceDao.getSentence());
        listCrime.addAll(crimeDao.getCrime());

        ccbSentenceCode.getItems().addAll(listCrime);

        dataTable.setFixedCellSize(40);

        StringConverter<Sentence> converter = FunctionalStringConverter.to(sentence -> (sentence == null) ? "" : sentence.getSentenceCode() + ": " + sentence.getPrisonerName());
        filterCombo.setItems(sentenceDao.getPrisonerName());
        filterCombo.setConverter(converter);

        cbSentenceType.setItems(FXCollections.observableArrayList("life imprisonment", "limited time"));

        cbSentenceType.getSelectionModel().select("limited time");

        cbSentenceType.setOnAction(event -> {
            String selectedType = cbSentenceType.getSelectionModel().getSelectedItem();
            if (selectedType != null) {
                switch (selectedType) {
                    case "life imprisonment":
                        txtMonth.setDisable(true);
                        txtYear.setDisable(true);
                        txtMonth.clear();
                        txtYear.clear();
                        break;
                    case "limited time":
                        txtMonth.setDisable(false);
                        txtYear.setDisable(false);
                        break;
                    default:
                        txtMonth.setDisable(false);
                        txtYear.setDisable(false);
                        break;
                }
            }
        });

        ccbSentenceCode.setConverter(new StringConverter<Crime>() {
            @Override
            public String toString(Crime crime) {
                return crime != null ? crime.getSentenceCode() : "";
            }

            @Override
            public Crime fromString(String string) {
                return listCrime.stream().filter(crime -> crime.getSentenceCode().equals(string)).findFirst().orElse(null);
            }
        });

        setNumericTextField(txtYear);
        setNumericTextField(txtMonth);

        txtYear.textProperty().addListener((observable, oldValue, newValue) -> updateEndDate());
        txtMonth.textProperty().addListener((observable, oldValue, newValue) -> updateEndDate());
        dateStartDate.valueProperty().addListener((observable, oldValue, newValue) -> updateEndDate());

        loadDataTable();
        setupPagination();
        initUI();
        setupSearch();
    }

    private void loadDataTable() {
        prisonerCodeColumn.setCellValueFactory(new PropertyValueFactory<>("sentenceCode"));
        prisonerNameColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerName"));
        sentenceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sentenceType"));
        crimesColumn.setCellValueFactory(new PropertyValueFactory<>("crimesCode"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        paroleEligibilityColumn.setCellValueFactory(new PropertyValueFactory<>("paroleEligibility"));

        dataTable.setItems(listTable);
        dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupSearch() {
        FilteredList<Sentence> filteredData = new FilteredList<>(listTable, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sentence -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (sentence.getSentenceCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (sentence.getPrisonerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (String.valueOf(sentence.getSentenceType()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (String.valueOf(sentence.getSentenceCode()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (sentence.getStartDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (sentence.getEndDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (sentence.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (sentence.getParoleEligibility().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
            updatePagination(filteredData);
        });

        dataTable.setItems(filteredData);
    }

    private void updatePagination(FilteredList<Sentence> filteredData) {
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
        dateStartDate.setConverter(converter);
        dateEndDate.setConverter(converter);
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
    void getItem(MouseEvent event) {
        index = dataTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        for (Sentence sentence : filterCombo.getItems()) {
            if (sentence.getSentenceCode().contains(prisonerCodeColumn.getCellData(index))) {
                filterCombo.setValue(sentence);
                break;
            }
        }
        Sentence selectedValue = filterCombo.getValue();
        String prisonerCode = selectedValue.getSentenceCode();
        cbSentenceType.setValue(sentenceTypeColumn.getCellData(index).toString());

        ccbSentenceCode.getCheckModel().clearChecks();
        List<Crime> crimes = createCrimesFromSentenceCode(crimesColumn.getCellData(index).toString());

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < ccbSentenceCode.getItems().size(); i++) {
            Crime currentItem = ccbSentenceCode.getItems().get(i);
            for (Crime crime : crimes) {
                if (currentItem.getSentenceCode().equals(crime.getSentenceCode())) {
                    indices.add(i);
                    break;
                }
            }
        }

        for (int idx : indices) {
            ccbSentenceCode.getCheckModel().check(idx);
        }

        LocalDate startDate = LocalDate.parse(startDateColumn.getCellData(index).toString());
        dateStartDate.setValue(startDate);

        String endDateCellValue = endDateColumn.getCellData(index) != null ? endDateColumn.getCellData(index).toString() : null;
        if (endDateCellValue != null && !endDateCellValue.isEmpty()) {
            LocalDate endDate = LocalDate.parse(endDateCellValue);
            dateEndDate.setValue(endDate);
        } else {
            dateEndDate.setValue(null);
        }

        txtStatus.setText(statusColumn.getCellData(index).toString());
        txtParoleEligibility.setText(paroleEligibilityColumn.getCellData(index).toString());

        visitationId = sentenceDao.getSentenceId(prisonerCode);

        if (startDate != null && LocalDate.parse(endDateCellValue) != null) {
            long yearsBetween = ChronoUnit.YEARS.between(startDate, LocalDate.parse(endDateCellValue));
            long monthsBetween = ChronoUnit.MONTHS.between(startDate.plusYears(yearsBetween), LocalDate.parse(endDateCellValue));

            txtYear.setText(String.valueOf(yearsBetween));
            txtMonth.setText(String.valueOf(monthsBetween));
        } else {
            txtYear.setText("");
            txtMonth.setText("");
        }
    }

    private List<Crime> createCrimesFromSentenceCode(String sentenceCode) {
        List<Crime> crimes = new ArrayList<>();

        String[] codes = sentenceCode.split(", ");

        for (String code : codes) {
            Crime crime = new Crime();
            crime.setSentenceCode(code);
            crimes.add(crime);
        }

        return crimes;
    }


    @FXML
    void onClean(ActionEvent event) {
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        cbSentenceType.getSelectionModel().select("limited time");
        ccbSentenceCode.getCheckModel().clearChecks();
        txtYear.clear();
        txtMonth.clear();
        dateStartDate.setValue(null);
        dateStartDate.setPromptText("YYYY-MM-DD");
        dateEndDate.setValue(null);
        dateEndDate.setPromptText("YYYY-MM-DD");
        txtStatus.clear();
        txtParoleEligibility.clear();

        dataTable.getSelectionModel().clearSelection();
    }

    private String listCrime(){
        StringBuilder selectedCrimeCodes = new StringBuilder();
        ObservableList<Crime> selectedCrimes = ccbSentenceCode.getCheckModel().getCheckedItems();

        for (Crime crime : selectedCrimes) {
            selectedCrimeCodes.append(crime.getSentenceCode()).append(", ");
        }

        String result = selectedCrimeCodes.toString().replaceAll(", $", "");
        return result;
    }

    @FXML
    void onCreate(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Sentence selectedValue = filterCombo.getValue();
        String prisonerId = selectedValue.getPrisonerId();
        String sentenceCode = selectedValue.getSentenceCode();
        String prisonerName = selectedValue.getPrisonerName();
        String sentenceType = cbSentenceType.getValue();
        String crimeCode = listCrime();
        LocalDate selectedStartDate = dateStartDate.getValue();
        String startDate = selectedStartDate.toString();
        LocalDate selectedEndDate = dateEndDate.getValue();
        String endDate = selectedEndDate.toString();
        String status = txtStatus.getText();
        String paroleeligibility = txtParoleEligibility.getText();
        String releaseDate = null;

        Sentence sentence = new Sentence(prisonerId, prisonerName, sentenceCode, sentenceType, crimeCode, startDate, endDate, releaseDate, status, paroleeligibility);
        sentenceDao.addSentence(sentence);
        listTable.add(sentence);
        dataTable.setItems(listTable);

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success", "Sentence created successfully.");

        onClean(event);
    }

    @FXML
    void onCrimeSetting(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/psmsystem/view/crime/CrimeView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Crime");
        stage.show();
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

            if (visitationId == -1) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "No sentence found for the selected prisoner.");
                return;
            }

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Delete sentence!");
            confirmationDialog.setContentText("Are you sure you want to delete it?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationDialog.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = confirmationDialog.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                Sentence selected = dataTable.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    LocalDate today = LocalDate.now();
                    String endDate = selected.getEndDate() != null ? selected.getEndDate() : null;

                    if (endDate == null) {
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Warning",
                                "Cannot delete record because the prisoner is life imprisonment.");
                    }
                    else if (LocalDate.parse(endDate).isBefore(today)) {

                        sentenceDao.deleteSentence(visitationId);
                        listTable.remove(selected);
                        dataTable.setItems(listTable);
                        resetValue();
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Success",
                                "Sentence deleted successfully.");
                    }
                    else {
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Warning",
                                "Cannot delete record because end date is not before today.");
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "An error occurred while deleting the sentence.");
        }
    }

    private void resetValue(){
        filterCombo.setValue(null);
        filterCombo.setPromptText("Select Prisoner ID");
        cbSentenceType.getSelectionModel().select("limited time");
        ccbSentenceCode.getCheckModel().clearChecks();
        txtYear.clear();
        txtMonth.clear();
        dateStartDate.setValue(null);
        dateStartDate.setPromptText("YYYY-MM-DD");
        dateEndDate.setValue(null);
        dateEndDate.setPromptText("YYYY-MM-DD");
        txtStatus.clear();
        txtParoleEligibility.clear();
    }


    @FXML
    void onEdit(ActionEvent event) {
        if (!isValidate()) {
            return;
        }

        Sentence selectedValue = filterCombo.getValue();
        String sentenceType = cbSentenceType.getValue();
        String sentenceCode = selectedValue.getSentenceCode();
        String crimeCode = listCrime();

        String prisonerId = selectedValue.getPrisonerId();
        String prisonerName = selectedValue.getPrisonerName();
        LocalDate selectedStartDate = dateStartDate.getValue();
        String startDate = selectedStartDate.toString();

        LocalDate selectedEndDate = dateEndDate.getValue();
        String endDate = selectedEndDate.toString();
        String status = txtStatus.getText();
        String paroleEligibility = txtParoleEligibility.getText();

        if (visitationId == -1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "No sentence found for the selected prisoner.");
            return;
        }

        String releaseDate = null;

        Sentence sentence = new Sentence(prisonerId, prisonerName, sentenceCode, sentenceType, crimeCode, startDate, endDate, releaseDate, status, paroleEligibility);
        sentenceDao.updateSentence(sentence, visitationId);

        index = dataTable.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Sentence s = listTable.get(index);
            s.setPrisonerId(prisonerId);
            s.setPrisonerName(prisonerName);
            s.setSentenceType(sentenceType);
            s.setSentenceCode(sentenceCode);
            s.setCrimesCode(crimeCode);
            s.setStartDate(startDate);
            s.setEndDate(endDate);
            s.setStatus(status);
            s.setReleaseDate(releaseDate);
            s.setParoleEligibility(paroleEligibility);

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

    private boolean isValidate() {
        if (filterCombo.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Please select a prisoner.");
            filterCombo.requestFocus();
            return false;
        }

        String prisonerCode = filterCombo.getValue().getSentenceCode();
        int sentenceId = sentenceDao.getSentenceId(prisonerCode);
        if (sentenceId != -1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Selected prisoner already has a sentence.");
            return false;
        }

        return true;
    }

    private void setNumericTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void updateEndDate() {
        if (dateStartDate.getValue() != null) {
            try {
                String textYear = txtYear.getText();
                String textMonth = txtMonth.getText();
                if (textYear.isEmpty()){
                    txtYear.setText("0");
                }
                if (textMonth.isEmpty()){
                    txtMonth.setText("0");
                }
                int years = textYear.isEmpty() ? 0 : Integer.parseInt(textYear);
                int months = textMonth.isEmpty() ? 0 :Integer.parseInt(textMonth);
                LocalDate startDate = dateStartDate.getValue();
                System.out.println(startDate);
                LocalDate endDate = startDate.plusYears(years).plusMonths(months);

                dateEndDate.setValue(endDate);
            } catch (NumberFormatException e) {
//                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Input",
//                        "Please enter valid numbers for year and month.");
                dateEndDate.setValue(null);
                dateEndDate.setPromptText("YYYY-MM-DD");
            }
        }
        else{
            txtYear.clear();
            txtMonth.clear();
        }
    }
}
