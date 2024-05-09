package com.example.psmsystem.controller.prisoner;


import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ItemPrisonerViewController {

    @FXML
    private AnchorPane ItemPrisoner;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDetail;

    @FXML
    private Button btnEdit;

    @FXML
    private ImageView imagePrisoner;
    @FXML
    private Label testImage;
    @FXML
    private Label prisonerId;

    @FXML
    private Label prisonerName;

    private String fxmlPath = "/com/example/psmsystem/";
    private Prisoner prisonerShowEdit;

    private FXMLLoader getLoader;

    public void setPrisonerItem(Prisoner prisoner) {

        prisonerShowEdit=prisoner;

        String defaultPath = "/com/example/psmsystem/assets/imagesPrisoner/default.png";
        String id = prisoner.getPrisonerId();
        String name = prisoner.getPrisonerName();
        String imagePath = prisoner.getImagePath();
        int idShow = Integer.parseInt(id);


        try {
            File imageFile;
            if (imagePath != null && !imagePath.isEmpty()) {
                imageFile = new File(imagePath);
            } else {
                imageFile = new File(defaultPath);
            }

            Image image = new Image(imageFile.toURI().toString());
                if (idShow < 10) {
                    prisonerId.setText("00"+idShow);
                }
                else if (idShow < 100) {
                    prisonerId.setText("0"+idShow);
                }
                else
                {
                    prisonerId.setText(""+idShow);
                }

            prisonerName.setText(name);
            imagePrisoner.setImage(image);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

//    public void btnEditOnAction() {
//        try {
//
////            AnchorPane loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath + "view/prisoner/" + "EditPrisonerView.fxml")));
////            AnchorPane newWindowContent = loader;
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/EditPrisonerView.fxml"));
//            AnchorPane newWindowContent = loader.load();
//
//            EditPrisonerController editPrisonerController = loader.getController();
//            editPrisonerController.setPrisonerEdit(prisonerShowEdit);
//
//            Stage newStage = new Stage();
//            // Create a new Scene with the new window content
//            Scene scene = new Scene(newWindowContent);
//            // Set the Scene to the Stage
//            newStage.setScene(scene);
//            // Set modality to APPLICATION_MODAL to block interactions with other windows
//            newStage.initModality(Modality.APPLICATION_MODAL);
//            // Set the title of the Stage
//            newStage.setTitle("New Window");
//            // Show the Stage
//            newStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
public void btnEditOnAction() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/EditPrisonerView.fxml"));
        AnchorPane newWindowContent = loader.load();
//
//        EditPrisonerController editPrisonerController = loader.getController();
//        editPrisonerController.setPrisonerEdit(prisonerShowEdit); // Truyền thông tin về tù nhân cần chỉnh sửa

        this.getLoader=loader;
        Stage newStage = new Stage();
        Scene scene = new Scene(newWindowContent);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("Edit Prisoner");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    @FXML
    void loadEditPrisonerView(ActionEvent event) {

        btnEditOnAction();

        if (this.getLoader==null)
        {
          this.getLoader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/EditPrisonerView.fxml"));
        }
        System.out.println("this get loader: " +this.getLoader);
        EditPrisonerController editPrisonerController = this.getLoader.getController();
        editPrisonerController.setPrisonerEdit(prisonerShowEdit); // Truyền thông tin về tù nhân cần chỉnh sửa
//        if (this.getLoader==null)
//        {
//          this.getLoader = new FXMLLoader(getClass().getResource(fxmlPath + "view/prisoner/EditPrisonerView.fxml"));
//        }
//        System.out.println("this get loader: " +this.getLoader);
//        EditPrisonerController editPrisonerController = this.getLoader.getController();
//        editPrisonerController.setPrisonerEdit(prisonerShowEdit); // Truyền thông tin về tù nhân cần chỉnh sửa
//        System.out.println("prisoner edit: " + prisonerShowEdit);

    }

//    public Prisoner getPrisonerShowEdit()
//    {
//        return prisonerShowEdit;
//    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {}

}
