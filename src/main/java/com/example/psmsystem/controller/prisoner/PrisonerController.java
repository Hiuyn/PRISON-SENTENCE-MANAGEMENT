package com.example.psmsystem.controller.prisoner;

import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrisonerController implements Initializable {

    private final int itemsPerPage = 5;

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
    private AnchorPane viewAllFun;

    @FXML
    private AnchorPane anchorPaneAddPrisoner;

    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        assert pgPagination != null : "fx:id=\"pgPagination\" was not injected: check your FXML file 'YourFXMLFileName.fxml'.";
        setupPagination();
    }
    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) prisonerList.size() / itemsPerPage);
        pgPagination.setPageCount(pageCount);
        pgPagination.setPageFactory(this::createPage);
    }

    private HBox createPage(int pageIndex) {
        HBox pageBox = new HBox();
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, prisonerList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Prisoner prisoner = prisonerList.get(i);
            try {
//                FXMLLoader fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath + "view/" + "ItemPrisonerView.fxml")));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/" + "ItemPrisonerView.fxml"));
                AnchorPane prisonerItem = fxmlLoader.load();

//                VBox prisonerItem = fxmlLoader.load();
                ItemPrisonerViewController controller = fxmlLoader.getController();
                controller.setPrisonerItem(prisoner);
                pageBox.getChildren().add(prisonerItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pageBox;
    }

    public void openAddWindow() {
        try {
            // Load FXML file for the new window content
            AnchorPane newWindowContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath + "view/prisoner/" + "addPrisonerView.fxml")));
            // Create a new Stage
            Stage newStage = new Stage();
            // Create a new Scene with the new window content
            Scene scene = new Scene(newWindowContent);
            // Set the Scene to the Stage
            newStage.setScene(scene);
            // Set modality to APPLICATION_MODAL to block interactions with other windows
            newStage.initModality(Modality.APPLICATION_MODAL);
            // Set the title of the Stage
            newStage.setTitle("New Window");
            // Show the Stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void openAddWindow() {
//        if (yourVariable != null) {
//            try {
//                // Thực hiện các thao tác khác tại đây
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Biến yourVariable là null.");
//        }
//    }

//    private void loadFXML(String fileName) {
//        try {
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath + "view/" + fileName + ".fxml")));
//            anchorPaneAddPrisoner.getChildren().setAll(root);
//        } catch (IOException ex) {
//            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    @FXML
    void loadAddPrisonerView(ActionEvent event) {
        openAddWindow();
    }

}
