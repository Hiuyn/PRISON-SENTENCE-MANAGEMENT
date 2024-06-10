package com.example.psmsystem.controller.prisoner;
import com.example.psmsystem.model.prisoner.Prisoner;
import com.example.psmsystem.service.prisonerDAO.PrisonerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML
    private void onFilter() {
        try
        {
            PrisonerDAO prisonerDAO = new PrisonerDAO();
            List<Prisoner> prisonerListByAge = prisonerDAO.getPrisonerByAge(this.ageFilter, this.genderFilter);

            if (sortNameType == 0   && sortTimeType ==0)
            {
                System.out.println("No time or age ASC DES");
            }
            else if (sortNameType == 1) {
                Collections.sort(prisonerListByAge, (p1, p2) -> p1.getPrisonerName().compareTo(p2.getPrisonerName()));
            } else if (sortNameType == 2) {

            }
            for (Prisoner prisoner : prisonerListByAge) {
                System.out.println("prisoner id sort test : " + prisoner.getPrisonerCode());
                System.out.println("prisoner name sort test : " + prisoner.getPrisonerName());
            }
        }catch (Exception e)
        {
            System.out.println("Filter controller - onFilter : "+ e.getMessage());
        }
    }

    public void getGenderFilter(ActionEvent event) {
        if (tgGender.getSelectedToggle() == rbtnMale)
        {
            genderFilter = 1;
            btnU18.setDisable(true);
            btnU40.setDisable(true);
            btnU60.setDisable(true);
            btnOver60.setDisable(true);
        } else if (tgGender.getSelectedToggle() == rbtnFemale) {
            genderFilter = 2;
            btnU18.setDisable(true);
            btnU40.setDisable(true);
            btnU60.setDisable(true);
            btnOver60.setDisable(true);
        } else if (tgGender.getSelectedToggle() == rbtnOther) {
            genderFilter = 3;
            btnU18.setDisable(true);
            btnU40.setDisable(true);
            btnU60.setDisable(true);
            btnOver60.setDisable(true);
        }
        else
        {
            btnU18.setDisable(false);
            btnU40.setDisable(false);
            btnU60.setDisable(false);
            btnOver60.setDisable(false);
        }
        System.out.println("genderFilter :" + genderFilter);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tgGender = new ToggleGroup();
        rbtnMale.setToggleGroup(tgGender);
        rbtnFemale.setToggleGroup(tgGender);
        rbtnOther.setToggleGroup(tgGender);
    }


    @FXML
    public void getAgeFilter(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
//            System.out.println(getAgeRange(clickedButton));
            getAgeRange(clickedButton);
            rbtnMale.setDisable(true);
            rbtnFemale.setDisable(true);
            rbtnOther.setDisable(true);
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
        System.out.println("AgeFilter :" + this.ageFilter);
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
        System.out.println("get asc des Time : " + getSortTimeType(clickedButton));
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

    public void back() {
        Stage stage = (Stage) lbTitle.getScene().getWindow();
        stage.close();
    }
}
