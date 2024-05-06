package com.example.psmsystem.controller.managementVisit;

import com.example.psmsystem.controller.MainPanelController;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementVisitViewController implements Initializable {

    private final int itemsPerPage = 5;

    PrisonerDAO  prisonerDAO = new PrisonerDAO();
    List<Prisoner> prisonerList = prisonerDAO.getPrisonerInItem();

    @FXML
    private VBox vbShowItem;

    @FXML
    private Button btnSearch;

    @FXML
    private AnchorPane newPrisonerFun;

    @FXML
    private Pagination pgPagination;

    @FXML
    private AnchorPane printFun;

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane viewAllFun;

    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert pgPagination != null : "fx:id=\"pgPagination\" was not injected: check your FXML file 'YourFXMLFileName.fxml'.";
        setupPagination();
    }
    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) prisonerList.size() / itemsPerPage);
        pgPagination.setPageCount(pageCount);
        pgPagination.setPageFactory(this::createPage);
    }

    private VBox createPage(int pageIndex) {
        VBox pageBox = new VBox();
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, prisonerList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Prisoner prisoner = prisonerList.get(i);
            try {
//                FXMLLoader fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath + "view/" + "ItemPrisonerView.fxml")));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath + "view/" + "ItemPrisonerView.fxml"));
                AnchorPane prisonerItem = fxmlLoader.load();

//                VBox prisonerItem = fxmlLoader.load();
                ItemPrisonerViewController controller = fxmlLoader.getController();
                controller.setPrisoner(prisoner);
                pageBox.getChildren().add(prisonerItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pageBox;
    }
}
