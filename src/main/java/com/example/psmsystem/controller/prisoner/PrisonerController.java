package com.example.psmsystem.controller.prisoner;

// import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
// import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrisonerController implements Initializable {
    private static IPrisonerDao<Prisoner> prisonerDao;
    @FXML
    private TableView<Prisoner> dataTablePrisoner;

    @FXML
    private TableColumn<Prisoner, Void> actionColumn;

    @FXML
    private TableColumn<Prisoner, String> maTNColumn;

    @FXML
    private TableColumn<Prisoner, String> nameTNColumn;

    @FXML
    private MFXTextField maTN;

    @FXML
    private Pagination pagination;

    private final int itemsPerPage = 8;

    ObservableList<Prisoner> listTable = FXCollections.observableArrayList();

    String fxmlPath = "/com/example/psmsystem/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prisonerDao = new PrisonerDAO();
        listTable.addAll(prisonerDao.getAllPrisoner());

        loadDataTable();

        setupPagination();
    }

    private void loadDataTable() {
        maTNColumn.setCellValueFactory(new PropertyValueFactory<>("maTN"));
        nameTNColumn.setCellValueFactory(new PropertyValueFactory<>("nameTN"));

        maTNColumn.setStyle("-fx-alignment: CENTER;");
        nameTNColumn.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<Prisoner, Void>, TableCell<Prisoner, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Prisoner, Void> call(final TableColumn<Prisoner, Void> param) {
                final TableCell<Prisoner, Void> cell = new TableCell<>() {
                    private final Button editButton = new Button("");
                    private final Button deleteButton = new Button("");

//                    FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
//                    FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                    {
//                        editButton.setPrefSize(22, 22);
                        editButton.getStyleClass().addAll("btn", "infor");
                        Image editImage = new Image(getClass().getResourceAsStream("/com/example/psmsystem/view/images/edit-white.png"));
                        ImageView editImageView = new ImageView(editImage);
                        editImageView.setFitWidth(22); // Đặt độ rộng là 22
                        editImageView.setFitHeight(22); // Đặt chiều cao là 22
                        editButton.setGraphic(editImageView);
//                        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/example/psmsystem/view/images/edit.png"))));
                        editButton.setOnAction((ActionEvent event) -> {
                            Prisoner prisoner = getTableView().getItems().get(getIndex());
                            // Handle edit action here
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource(fxmlPath + "view/PrisonerEditView.fxml"));

                                Stage stage = new Stage();
                                Scene scene = new Scene(root);

                                stage.setScene(scene);
                                stage.setTitle("Create Prisoner");
                                stage.initStyle(StageStyle.UTILITY);
                                stage.getIcons().add(new Image("file: " + fxmlPath + "assets/icon.png"));
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setPrefSize(22, 22);
                        deleteButton.getStyleClass().addAll("btn", "danger");
                        Image deleteImage = new Image(getClass().getResourceAsStream("/com/example/psmsystem/view/images/delete-white.png"));
                        ImageView deleteImageView = new ImageView(deleteImage);
                        deleteImageView.setFitWidth(22); // Đặt độ rộng là 22
                        deleteImageView.setFitHeight(22); // Đặt chiều cao là 22
                        deleteButton.setGraphic(deleteImageView);
//                        deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/example/psmsystem/view/images/delete.png"))));
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Prisoner prisoner = getTableView().getItems().get(getIndex());
                            // Handle delete action here
                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.setTitle("Delete Prisoner");
                            dialog.setHeaderText("Do you want delete this prisoner?");

                            ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

                            dialog.setResultConverter(buttonType -> {
                                if (buttonType == deleteButtonType) {
                                    // Xử lý khi người dùng nhấn Delete
                                    // Thực hiện xoá prisoner ở đây
                                    return ButtonType.OK;
                                }
                                return ButtonType.CANCEL;
                            });

                            Optional<ButtonType> result = dialog.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                // Xoá prisoner nếu người dùng nhấn Delete
                            } else {
                                // Đóng dialog nếu người dùng nhấn Cancel
                                dialog.close();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox managebtn = new HBox(editButton, deleteButton);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editButton, new Insets(2, 3, 0, 2));
                            setGraphic(managebtn);

                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
        dataTablePrisoner.setItems(listTable);
        dataTablePrisoner.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) listTable.size() / itemsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, listTable.size());
        dataTablePrisoner.setItems(FXCollections.observableArrayList(listTable.subList(fromIndex, toIndex)));
        return new BorderPane(dataTablePrisoner);
    }

    @FXML
    void isCreate(ActionEvent event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath + "view/PrisonerCreateView.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Create Prisoner");
        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("file: " + fxmlPath + "assets/icon.png"));
        stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void isSearch(ActionEvent event) {

    }

    @FXML
    void isClearn(ActionEvent event) {
        maTN.clear();
    }
}
