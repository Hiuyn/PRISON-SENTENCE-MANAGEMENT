package com.example.psmsystem.controller;

import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.crime.ICrimeDao;
import com.example.psmsystem.model.managementvisit.IManagementVisitDao;
import com.example.psmsystem.model.managementvisit.ManagementVisit;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.sentence.ISentenceDao;
import com.example.psmsystem.model.sentence.Sentence;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.managementvisitDao.ManagementVisitDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private IPrisonerDao<Prisoner> prisonerDao;
    private IManagementVisitDao<ManagementVisit> managementVisitDao;
    private ICrimeDao<Crime> crimeDao;
    private ISentenceDao<Sentence> sentenceDao;

    @FXML
    private AreaChart<String, Number> areaChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private StackedAreaChart<Number, Number> stackedAreaChart;

    @FXML
    private Label txtCrime;

    @FXML
    private Label txtPrisoner;

    @FXML
    private Label txtVisitor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prisonerDao = new PrisonerDAO();
        managementVisitDao = new ManagementVisitDao();
        sentenceDao = new SentenceDao();
        crimeDao = new CrimeDao();

        txtPrisoner.setText(String.valueOf(prisonerDao.getCountPrisoner()));
        txtVisitor.setText(String.valueOf(managementVisitDao.getCountManagementVisit()));
        txtCrime.setText(String.valueOf(crimeDao.getCountCrime()));

        areaChartController();
        lineChartController();
        barChartController();
        pieChartController();
        stackedAreaChartController();
    }

    public void areaChartController() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 15, 2.5);
        areaChart.setTitle("Temperature Monitoring (in Degrees C)");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("John");
        series1.getData().add(new XYChart.Data<>("Monday", 3));
        series1.getData().add(new XYChart.Data<>("Tuesday", 4));
        series1.getData().add(new XYChart.Data<>("Wednesday", 3));
        series1.getData().add(new XYChart.Data<>("Thursday", 5));
        series1.getData().add(new XYChart.Data<>("Friday", 4));
        series1.getData().add(new XYChart.Data<>("Saturday", 10));
        series1.getData().add(new XYChart.Data<>("Sunday", 12));
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Jane");
        series2.getData().add(new XYChart.Data<>("Monday", 1));
        series2.getData().add(new XYChart.Data<>("Tuesday", 3));
        series2.getData().add(new XYChart.Data<>("Wednesday", 4));
        series2.getData().add(new XYChart.Data<>("Thursday", 3));
        series2.getData().add(new XYChart.Data<>("Friday", 3));
        series2.getData().add(new XYChart.Data<>("Saturday", 5));
        series2.getData().add(new XYChart.Data<>("Sunday", 4));
        areaChart.getData().addAll(series1, series2);
    }

    public void lineChartController() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of Visits");

        lineChart.setTitle("Number of Visits per Month");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Visits");

        Map<String, Integer> visitsByMonth = managementVisitDao.countVisitsByMonth();
        for (Map.Entry<String, Integer> entry : visitsByMonth.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        lineChart.getData().add(series);
    }

    public void barChartController() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Sentence Type");
        yAxis.setLabel("Number of Prisoners");

        barChart.setTitle("Number of Prisoners by Sentence Type");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Prisoners");

        Map<String, Integer> prisonersBySentenceType = sentenceDao.countPrisonersBySentenceType();
        for (Map.Entry<String, Integer> entry : prisonersBySentenceType.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
    }

    public void pieChartController() {
        Map<String, Integer> genderCount = prisonerDao.countGender();

        pieChart.setTitle("Gender Prisoner Chart");
        for (Map.Entry<String, Integer> entry : genderCount.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart.getData().add(slice);
        }
    }

    public void stackedAreaChartController() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        stackedAreaChart.setTitle("Stacked Area Chart Example");

        // Tạo dữ liệu cho series 1
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Series 1");
        series1.getData().add(new XYChart.Data<>(1, 10));
        series1.getData().add(new XYChart.Data<>(2, 20));
        series1.getData().add(new XYChart.Data<>(3, 15));
        series1.getData().add(new XYChart.Data<>(4, 25));

        // Tạo dữ liệu cho series 2
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Series 2");
        series2.getData().add(new XYChart.Data<>(1, 15));
        series2.getData().add(new XYChart.Data<>(2, 25));
        series2.getData().add(new XYChart.Data<>(3, 20));
        series2.getData().add(new XYChart.Data<>(4, 30));

        // Thêm series vào chart
        stackedAreaChart.getData().addAll(series1, series2);
    }
}