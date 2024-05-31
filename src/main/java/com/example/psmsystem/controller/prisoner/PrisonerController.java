package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.controller.MainPanelController;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.model.user.User;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrisonerController implements Initializable {

    private final int itemsPerPage = 4;
    private final int rowsPerPage = 3;
    PrisonerDAO  prisonerDAO = new PrisonerDAO();
    List<Prisoner> prisonerList = prisonerDAO.getPrisonerInItem();

    @FXML
    private VBox vbShowItem;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnExport;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnViewAll;

    @FXML
    private AnchorPane newPrisonerFun;

    @FXML
    private Pagination pgPagination;

    @FXML
    private AnchorPane printFun;

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane anchoViewAll;

    @FXML
    private AnchorPane anchorPaneAddPrisoner;

    private final String fxmlPath = "/com/example/psmsystem/";

    private int userId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setupPagination();
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) prisonerList.size() / (itemsPerPage * rowsPerPage));
        pgPagination.setPageCount(pageCount);
        pgPagination.setPageFactory(this::createPage);
    }

    private VBox createPage(int pageIndex) {
        VBox pageBox = new VBox(30);
        int startIndex = pageIndex * itemsPerPage * rowsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage * rowsPerPage, prisonerList.size());

        for (int i = startIndex; i < endIndex; i += itemsPerPage) {
            HBox rowBox = new HBox(30);
            int rowEndIndex = Math.min(i + itemsPerPage, prisonerList.size());
            for (int j = i; j < rowEndIndex; j++) {
                Prisoner prisoner = prisonerList.get(j);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/" + "ItemPrisonerView.fxml"));
                    AnchorPane prisonerItem = fxmlLoader.load();
                    ItemPrisonerViewController controller = fxmlLoader.getController();
                    controller.setPrisonerController(this);
                    controller.setPrisonerItem(prisoner);
                    rowBox.getChildren().add(prisonerItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pageBox.getChildren().add(rowBox);
        }
        return pageBox;
    }

    public void refreshPrisonerList() {
        PrisonerDAO prisonerDAO = new PrisonerDAO();
        prisonerList = prisonerDAO.getPrisonerInItem();
        setupPagination();
    }

    public void openAddWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/AddPrisonerView.fxml"));
            AnchorPane newWindowContent = loader.load();

            AddPrisonerController addPrisonerController = loader.getController();
            addPrisonerController.setUserIdAdd(this.userId);
            addPrisonerController.setPrisonerController(this);
            System.out.println("User id add new : " + this.userId);

            Stage newStage = new Stage();
            Scene scene = new Scene(newWindowContent);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("New Window");

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadAddPrisonerView(ActionEvent event) {
        openAddWindow();
    }


}
