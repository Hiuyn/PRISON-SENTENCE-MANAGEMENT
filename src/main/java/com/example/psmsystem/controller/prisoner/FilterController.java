package com.example.psmsystem.controller.prisoner;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FilterController implements Initializable {


    @FXML
    private Button btnNameAsc;
    @FXML
    private Button btnNameDes;
    @FXML
    private Button btnTimeAsc;
    @FXML
    private Button btnTimeDes;
    @FXML
    private Button btnOver60;
    @FXML
    private Button btnU60;
    @FXML
    private Button btnU40;
    @FXML
    private Button btnU18;
    @FXML
    private Label lbTitle;
    @FXML
    private RadioButton rbtnMale;

    @FXML
    private RadioButton rbtnFemale;

    @FXML
    private RadioButton rbtnOther;

    @FXML
    private ToggleGroup tgGender;


    private int ageFilter;
    private int sortNameType;
    private int sortTimeType;
    private int genderFilter;
    private boolean sortCheck;
    private PrisonerController prisonerController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tgGender = new ToggleGroup();
        rbtnFemale.setToggleGroup(tgGender);
        rbtnMale.setToggleGroup(tgGender);

        rbtnOther.setToggleGroup(tgGender);
//        rbtnMale.setSelected(true);
    }

    @FXML
    public void onFilter(ActionEvent event) {
        try
        {
            PrisonerDAO prisonerDAO = new PrisonerDAO();
//            List<Prisoner> prisonerListByAge = prisonerDAO.getPrisonerByAge(this.ageFilter, this.genderFilter);
            List<Prisoner> prisonerList = prisonerDAO.getAllPrisoner();
            if (genderFilter != 0) {
                prisonerList = filterByGender(prisonerList);
            }

            if (ageFilter != 0) {
                prisonerList = filterByAge(prisonerList);
            }

            if (sortNameType != 0 && (genderFilter == 0 || ageFilter == 0)) {
                prisonerList = sortByName(prisonerList);
            }

            if (sortTimeType != 0 && (genderFilter == 0 || ageFilter == 0)) {
                prisonerList = sortByTime(prisonerList);
            }



            if (this.prisonerController != null)
            {
                System.out.println("Prisonert controller : " + prisonerController);
            }
            System.out.println("gender : " + genderFilter);
            System.out.println("AgeFilter : " + ageFilter);
            System.out.println("name : " + sortNameType);
            System.out.println("time : " + sortTimeType);

            List<Prisoner> prisonerFilter = prisonerList;
            if (!prisonerFilter.isEmpty())
            {
                back(event,()->prisonerController.refreshPrisonerList(prisonerFilter));
            }
            else
            {
                showAlert("Not Found prisoner match the filter");
            }


        }catch (Exception e)
        {
            System.out.println("Filter controller - onFilter : "+ e.getMessage());
        }
    }

    private List<Prisoner> filterByGender(List<Prisoner> prisoners) {
        return prisoners.stream()
                .filter(prisoner -> prisoner.getGender() == genderFilter)
                .collect(Collectors.toList());
    }

    private List<Prisoner> filterByAge(List<Prisoner> prisoners) {
        LocalDate now = LocalDate.now();
        return prisoners.stream()
                .filter(prisoner -> {
                    LocalDate birthDate = LocalDate.parse(prisoner.getDOB());
                    Period period = Period.between(birthDate, now);
                    int age = period.getYears();

                    switch (ageFilter) {
                        case 1:
                            return age < 18;
                        case 2:
                            return   18 < age  &&  age < 40;
                        case 3:
                            return age >= 40 && age < 60;
                        case 4:
                            return age >= 60;
                        default:
                            return true;
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Prisoner> sortByName(List<Prisoner> prisoners) {
        if (sortNameType == 1) {
            return prisoners.stream()
                    .sorted((p1, p2) -> p1.getPrisonerName().compareToIgnoreCase(p2.getPrisonerName()))
                    .collect(Collectors.toList());
        } else if (sortNameType == 2) {
            return prisoners.stream()
                    .sorted((p1, p2) -> p2.getPrisonerName().compareToIgnoreCase(p1.getPrisonerName()))
                    .collect(Collectors.toList());
        }
        return prisoners;
    }

    private List<Prisoner> sortByTime(List<Prisoner> prisoners) {
        // Sử dụng logic tương tự nhưng với thời gian giam giữ

        return prisoners;
    }



    public void setPrisonerController(PrisonerController prisonerController)
    {
        this.prisonerController = prisonerController;
    }


    public void getGenderFilter(ActionEvent event) {
        if (tgGender.getSelectedToggle() == rbtnMale)
        {
            genderFilter = 1;
        } else if (tgGender.getSelectedToggle() == rbtnFemale) {
            genderFilter = 2;
        } else if (tgGender.getSelectedToggle() == rbtnOther) {
            genderFilter = 3;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    public void getAgeFilter(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
//            System.out.println(getAgeRange(clickedButton));
//            clickedButton.getStyleClass().
            getAgeRange(clickedButton);
        }catch (Exception e)
        {
            System.out.println("getAgeFilter: "+ e.getMessage());
        }
    }

    private void getAgeRange(Button button) {
        if (button == btnU18) {
            this.ageFilter = 1;
        } else if (button == btnU40) {
            this.ageFilter = 2;
        } else if (button == btnU60) {
            this.ageFilter =  3;
        } else if (button == btnOver60) {
            this.ageFilter = 4;
        }
        else
        {
            this.ageFilter = 0;
        }
    }


    public void setSortNameType(ActionEvent event)
    {
        Button clickedButton = (Button) event.getSource();
        if (clickedButton == btnNameAsc)
        {
            boolean isCurrentlyDisabled = btnTimeDes.isDisabled();
            btnTimeDes.setDisable(!isCurrentlyDisabled);
            btnTimeAsc.setDisable(!isCurrentlyDisabled);
            btnNameDes.setDisable(!isCurrentlyDisabled);
            this.sortCheck = true;
        }
        else if (clickedButton == btnNameDes)
        {
            boolean isCurrentlyDisabled = btnTimeDes.isDisabled();

            btnTimeDes.setDisable(!isCurrentlyDisabled);
            btnNameAsc.setDisable(!isCurrentlyDisabled);
            btnTimeAsc.setDisable(!isCurrentlyDisabled);
            this.sortCheck = true;
        }
        System.out.println("get asc des name : " + getSortNameType(clickedButton));
        this.sortNameType = getSortNameType(clickedButton);
    }
    private int getSortNameType(Button button) {
        if (button == btnNameAsc) {
            return 1;
        } else if (button == btnNameDes) {
            return 2;
        }
        return 0;
    }

    public void setSortTimeType(ActionEvent event)
    {
        Button clickedButton = (Button) event.getSource();
        if (clickedButton == btnTimeAsc)
        {
            boolean isCurrentlyDisabled = btnTimeDes.isDisabled();
            btnTimeDes.setDisable(!isCurrentlyDisabled);
            btnNameAsc.setDisable(!isCurrentlyDisabled);
            btnNameDes.setDisable(!isCurrentlyDisabled);
            this.sortCheck = true;
        }
        else if (clickedButton == btnTimeDes)
        {
            boolean isCurrentlyDisabled = btnTimeAsc.isDisabled();
            btnTimeAsc.setDisable(!isCurrentlyDisabled);
            btnNameAsc.setDisable(!isCurrentlyDisabled);
            btnNameDes.setDisable(!isCurrentlyDisabled);
        }
        this.sortTimeType = getSortTimeType(clickedButton);
    }
    private int getSortTimeType(Button button) {
        if (button == btnTimeAsc) {
            return 1;
        } else if (button == btnTimeDes) {
            return 2;
        }
        return 0;
    }

    public interface Callback {
        void execute();
    }

    public void back(ActionEvent event, Callback callback)  {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        if (callback != null) {
            callback.execute();
        }
    }

    public void backPrisoner(ActionEvent event)
    {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
