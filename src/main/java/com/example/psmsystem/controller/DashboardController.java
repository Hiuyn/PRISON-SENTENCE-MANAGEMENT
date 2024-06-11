package com.example.psmsystem.controller;

import com.example.psmsystem.model.crime.Crime;
import com.example.psmsystem.model.crime.ICrimeDao;
import com.example.psmsystem.model.managementvisit.IManagementVisitDao;
import com.example.psmsystem.model.managementvisit.ManagementVisit;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.sentence.ISentenceDao;
import com.example.psmsystem.model.sentence.Sentence;
import com.example.psmsystem.service.assessDao.AssessDao;
import com.example.psmsystem.service.crimeDao.CrimeDao;
import com.example.psmsystem.service.healthDao.HealthDao;
import com.example.psmsystem.service.managementvisitDao.ManagementVisitDao;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import com.example.psmsystem.service.sentenceDao.SentenceDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private BarChart<String, Number> barChartDiscipline;

    @FXML
    private BarChart<String, Number> barChartBonus;

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
        NumberAxis yAxis = new NumberAxis();
        barChartBonus.setTitle("Prisoner Bonus Chart");
        barChartBonus.getXAxis().setLabel("Prisoner");
        barChartBonus.getYAxis().setLabel("Count");

        AssessDao assessDao = new AssessDao();
//        Map<String, Integer> breachData = assessDao.getBreachOfDisciplineData();
        Map<String, Integer> bonusData = assessDao.getBonusData();

//        System.out.println("breachData"+breachData);
//        System.out.println("bonusData"+bonusData);

//        XYChart.Series<String, Number> breachSeries = new XYChart.Series<>();
//        breachSeries.setName("Breach of Discipline");
//        breachData.forEach((prisonerName, breachCount) -> breachSeries.getData().add(new XYChart.Data<>(prisonerName, breachCount)));

        XYChart.Series<String, Number> bonusSeries = new XYChart.Series<>();
        bonusSeries.setName("Bonus");
        bonusData.forEach((prisonerName, bonusCount) -> bonusSeries.getData().add(new XYChart.Data<>(prisonerName, bonusCount)));

        barChartBonus.getData().add(bonusSeries);
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

        barChartDiscipline.setTitle("Prisoner Discipline Chart");
        barChartDiscipline.getXAxis().setLabel("Prisoner");
        barChartDiscipline.getYAxis().setLabel("Count");

        AssessDao assessDao = new AssessDao();
        Map<String, Integer> breachData = assessDao.getBreachOfDisciplineData();
//        xAxis.setLabel("Sentence Type");
//        yAxis.setLabel("Number of Prisoners");
//
//        barChart.setTitle("Number of Prisoners by Sentence Type");

//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Prisoners");

        XYChart.Series<String, Number> breachSeries = new XYChart.Series<>();
        breachSeries.setName("Breach of Discipline");
        breachData.forEach((prisonerName, breachCount) -> breachSeries.getData().add(new XYChart.Data<>(prisonerName, breachCount)));

//        Map<String, Integer> prisonersBySentenceType = sentenceDao.countPrisonersBySentenceType();
//        for (Map.Entry<String, Integer> entry : prisonersBySentenceType.entrySet()) {
//            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
//        }

        barChartDiscipline.getData().add(breachSeries);
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
        HealthDao healthDao = new HealthDao();
        int year = LocalDate.now().getYear(); // Lấy năm hiện tại
        Map<Integer, Integer> strongHealthData = healthDao.getStrongHealthDataByMonthYear(year);
        Map<Integer, Integer> weakHealthData = healthDao.getWeakHealthDataByMonthYear(year);

        NumberAxis xAxis = new NumberAxis(1, 12, 1);
        xAxis.setLabel("Month");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");
        stackedAreaChart.setTitle("Health Status by Month (" + year + ")");

        XYChart.Series<Number, Number> strongSeries = new XYChart.Series<>();
        strongSeries.setName("Strong");
        for (Map.Entry<Integer, Integer> entry : strongHealthData.entrySet()) {
            strongSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        XYChart.Series<Number, Number> weakSeries = new XYChart.Series<>();
        weakSeries.setName("Weak");
        for (Map.Entry<Integer, Integer> entry : weakHealthData.entrySet()) {
            weakSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        stackedAreaChart.getData().clear();
        stackedAreaChart.getData().addAll(strongSeries, weakSeries);
    }
}