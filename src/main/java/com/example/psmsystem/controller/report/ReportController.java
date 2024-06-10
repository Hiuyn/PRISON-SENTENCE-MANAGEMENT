package com.example.psmsystem.controller.report;

import com.example.psmsystem.model.report.Report;
import com.example.psmsystem.service.reportDao.ReportDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ReportController implements Initializable {
    @FXML
    private TableView<Report> dataTable;

    @FXML
    private TableColumn<Report, String> disciplineColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<Report, String> paroleEligibilityColumn;

    @FXML
    private TableColumn<Report, String> prisonerCodeColumn;

    @FXML
    private TableColumn<Report, String> prisonerNameColumn;

    @FXML
    private TableColumn<Report, String> releaseDateColumn;

    @FXML
    private TableColumn<Report, String> rewardColumn;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label txtUpdate;

    @FXML
    private TableColumn<Report, Integer> levelColumn;

    @FXML
    private TableColumn<Report, Boolean> statusColumn;

    private ObservableList<Report> reportList = FXCollections.observableArrayList();
    private ReportDao reportDao = new ReportDao();
    private static final int ROWS_PER_PAGE = 16;

    @FXML
    void getItem(MouseEvent event) {
        Report selectedReport = dataTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            // Hiển thị thông tin chi tiết hoặc xử lý sự kiện khi chọn mục
            System.out.println("Selected Report: " + selectedReport.getPrisonerName());
        }
    }

    @FXML
    void onExport(ActionEvent event) {
        // Thực hiện xuất dữ liệu
        System.out.println("Exporting data...");
        // Thêm mã xuất dữ liệu ở đây
    }

    @FXML
    void onUpdate(ActionEvent event) {
        List<Report> reports = reportDao.getAll();
        Date currentDate = new Date();

        // Định dạng ngày theo chuẩn "YYYY-MM-DD hh:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        // Xử lý thưởng
        for (Report report : reports) {
            if (report.getTotalReward() == 25 && report.getLevel() == 4 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 0) {
                // Giảm án 1 năm
                report.setParoleEligibility("Reduce sentence by 1 year");
                reportDao.updateSentence(report.getSentenceCode(), subtractYear(report.getReleaseDate(), 1), "Reduce sentence by 1 year");
            } else if (report.getTotalReward() == 25 && report.getLevel() == 3 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 0) {
                // Giảm án 6 tháng
                report.setParoleEligibility("Reduce sentence by 6 months");
                reportDao.updateSentence(report.getSentenceCode(), subtractMonth(report.getReleaseDate(), 6), "Reduce sentence by 6 months");
            } else if (report.getTotalReward() == 30 && report.getLevel() == 2 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 0) {
                // Giảm án 1 tháng
                report.setParoleEligibility("Reduce sentence by 1 month");
                reportDao.updateSentence(report.getSentenceCode(), subtractMonth(report.getReleaseDate(), 1), "Reduce sentence by 1 month");
            } else if (report.getTotalReward() == 20 && report.getLevel() == 1 && (report.getLevel() == 0 || report.getLevel() == 2) && report.getTotalDiscipline() == 2) {
                // Tặng quà
                report.setParoleEligibility("Gift award");
                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "Gift award");
            }
        }

        // Xử lý phạt
        for (Report report : reports) {
            if (report.getTotalReward() >= 5 && report.getTotalDiscipline() <= 10 && report.getLevel() == 1 && (report.getLevel() == 1 || report.getLevel() == 2 || report.getLevel() == 3)) {
                // Phạt 12 giờ lao động
                report.setParoleEligibility("12-hour labor penalty");
                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "12-hour labor penalty");
            } else if (report.getTotalDiscipline() >= 11 && report.getTotalDiscipline() <= 15 && (report.getLevel() == 1 || report.getLevel() == 2) && (report.getLevel() == 1 || report.getLevel() == 2 || report.getLevel() == 3)) {
                // Phạt 36 giờ lao động
                report.setParoleEligibility("36-hour labor penalty");
                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "36-hour labor penalty");
            } else if (report.getTotalDiscipline() >= 16 && report.getTotalDiscipline() <= 20 && (report.getLevel() == 1 || report.getLevel() == 2 ) && (report.getLevel() == 1 || report.getLevel() == 2 || report.getLevel() == 3)) {
                // Phạt không gặp người thân và lao động 48 tiếng
                report.setParoleEligibility("No meeting with relatives and 48-hour labor penalty");
                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "No meeting with relatives and 48-hour labor penalty");
            } else if ((report.getTotalDiscipline() == 20 && (report.getLevel() == 2 || report.getLevel() == 3)) || (report.getTotalDiscipline() == 10 && report.getLevel() == 4)) {
                // Biệt giam
                report.setParoleEligibility("Solitary confinement");
                reportDao.updateSentence(report.getSentenceCode(), report.getReleaseDate(), "Solitary confinement");
            }
        }

        // Cập nhật lại dữ liệu sau khi xử lý
        loadReports();
        txtUpdate.setText("Data updated on: " + formattedDate);
        reportDao.updateUpdateLog(currentDate);
    }

    private Date subtractYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -years);
        return calendar.getTime();
    }

    private Date subtractMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataTable.setFixedCellSize(37);
        txtUpdate.setText("Data updated on: " + reportDao.timeUpdate());
        loadDataTable();
        loadReports();
        configurePagination();
    }

    private void loadDataTable() {
        prisonerCodeColumn.setCellValueFactory(new PropertyValueFactory<>("sentenceCode"));
        prisonerNameColumn.setCellValueFactory(new PropertyValueFactory<>("prisonerName"));
        rewardColumn.setCellValueFactory(new PropertyValueFactory<>("totalReward"));
        disciplineColumn.setCellValueFactory(new PropertyValueFactory<>("totalDiscipline"));
        paroleEligibilityColumn.setCellValueFactory(new PropertyValueFactory<>("paroleEligibility"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        // Custom cell factory for statusColumn
        statusColumn.setCellFactory(new Callback<TableColumn<Report, Boolean>, TableCell<Report, Boolean>>() {
            @Override
            public TableCell<Report, Boolean> call(TableColumn<Report, Boolean> param) {
                return new TableCell<Report, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item ? "Released" : "Are serving sentence");
                        }
                    }
                };
            }
        });

        // Custom cell factory for levelColumn
        levelColumn.setCellFactory(new Callback<TableColumn<Report, Integer>, TableCell<Report, Integer>>() {
            @Override
            public TableCell<Report, Integer> call(TableColumn<Report, Integer> param) {
                return new TableCell<Report, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            switch (item) {
                                case 0:
                                    setText("No disease");
                                    break;
                                case 1:
                                    setText("Mild");
                                    break;
                                case 2:
                                    setText("Severe");
                                    break;
                                case 3:
                                    setText("Needs intervention");
                                    break;
                                default:
                                    setText("Unknown");
                                    break;
                            }
                        }
                    }
                };
            }
        });
    }

    private void loadReports() {
        reportList.clear();
        List<Report> reports = reportDao.getAll();
        reportList.addAll(reports);
        configurePagination();
        updateTableView();
    }

    private void updateTableView() {
        int pageIndex = pagination.getCurrentPageIndex();
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, reportList.size());
        dataTable.setItems(FXCollections.observableArrayList(reportList.subList(fromIndex, toIndex)));
        dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configurePagination() {
        int totalPages = (int) Math.ceil((double) reportList.size() / ROWS_PER_PAGE);
        pagination.setPageCount(totalPages);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTableView());
    }

    @FXML
    void onSearch(ActionEvent event) {
        String keyword = txtSearch.getText().toLowerCase();
        List<Report> filteredReports = reportDao.getAll().stream()
                .filter(report -> report.getPrisonerName().toLowerCase().contains(keyword) ||
                        report.getSentenceCode().toLowerCase().contains(keyword))
                .toList();
        reportList.setAll(filteredReports);
        configurePagination();
        updateTableView();
    }
}
