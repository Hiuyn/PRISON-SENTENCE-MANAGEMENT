package com.example.psmsystem.controller;

import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PrisonerController implements Initializable {
    private static IPrisonerDao<Prisoner> prisonerDao;
    @FXML
    private TableView<Prisoner> dataTablePrisoner;

    @FXML
    private TableColumn<Prisoner, Void> actionButton;

    @FXML
    private TableColumn<Prisoner, String> maTNColumn;

    @FXML
    private TableColumn<Prisoner, String> nameTNColumn;

    ObservableList<Prisoner> listTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prisonerDao = new PrisonerDAO();
        listTable.addAll(prisonerDao.getAllPrisoner());

        loadDataTable();
    }

//    public PrisonerController(){
//        userDao = new UserDao();
//        listTable.addAll(userDao.getAllUsers());
//        System.out.println(listTable);
//        loadDataTable();
//    }

    private void loadDataTable() {
        maTNColumn.setCellValueFactory(new PropertyValueFactory<>("maTN"));
        nameTNColumn.setCellValueFactory(new PropertyValueFactory<>("nameTN"));
        dataTablePrisoner.setItems(listTable);
    }

    @FXML
    void isCreate(ActionEvent event) {

    }

    @FXML
    void isSearch(ActionEvent event) {

    }
}
