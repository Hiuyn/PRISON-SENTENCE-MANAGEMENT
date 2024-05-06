package com.example.psmsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
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
//    private Label labelString;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        areaChartController();
//        lineChartController();
//        barChartController();
//        pieChartController();
//        stackedAreaChartController();
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
        NumberAxis xAxis = new NumberAxis(1960, 2020, 10);
        xAxis.setLabel("Years");
        NumberAxis yAxis = new NumberAxis(0, 350, 50);
        yAxis.setLabel("No.of schools");
        lineChart.setTitle("Line Chart Example");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("No of schools in an year");
        series.getData().add(new XYChart.Data("1970", 15));
        series.getData().add(new XYChart.Data("1980", 30));
        series.getData().add(new XYChart.Data("1990", 60));
        series.getData().add(new XYChart.Data("2000", 120));
        series.getData().add(new XYChart.Data("2013", 240));
        series.getData().add(new XYChart.Data("2014", 300));
        lineChart.getData().add(series);
    }

    public void barChartController() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart.setTitle("Bar Chart Example");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data");
        series.getData().add(new XYChart.Data<>("A", 10));
        series.getData().add(new XYChart.Data<>("B", 20));
        series.getData().add(new XYChart.Data<>("C", 30));
        barChart.getData().add(series);
    }

    public void pieChartController() {
        pieChart.setTitle("Pie Chart Example");
        PieChart.Data slice1 = new PieChart.Data("A", 30);
        PieChart.Data slice2 = new PieChart.Data("B", 40);
        PieChart.Data slice3 = new PieChart.Data("C", 30);
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);
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