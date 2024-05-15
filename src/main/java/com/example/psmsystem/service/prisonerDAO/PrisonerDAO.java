package com.example.psmsystem.service.prisonerDAO;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrisonerDAO implements IPrisonerDao<Prisoner> {
    private static final String INSERT_QUERY = "INSERT INTO prisoners (prisoner_code, prisoner_name, date_birth, gender, contact_name, contact_phone, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String SELECT_BY_USERNAME_QUERY = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_BY_PRISONER_QUERY = "SELECT * FROM prisoners";
//    private static final String SELECT_BY_PRISONER_QUERY = "SELECT * FROM prisoner";
////    private static final String INSERT_INTO_PRISONER_QUERY = "INSERT INTO  prisoner VALUES (prisonerId = ?)";

    @Override
    public List<Prisoner> getAllPrisoner() {
        List<Prisoner> prisonerList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerCode(rs.getString("prisoner_code"));
                prisoner.setPrisonerName(rs.getString("prisoner_name"));
                prisoner.setDOB(rs.getString("date_birth"));
                prisoner.setGender(rs.getString("gender"));
                prisoner.setContactName(rs.getString("contact_name"));
                prisoner.setContactPhone(rs.getString("contact_phone"));
                prisoner.setImagePath(rs.getString("image_path"));

                prisonerList.add(prisoner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prisonerList;
    }
    public List<Prisoner> getPrisonerInItem() {
        List<Prisoner> PrisonerList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerCode(String.valueOf(rs.getInt("prisoner_id")));
                prisoner.setPrisonerName(rs.getString("prisoner_name"));
                prisoner.setImagePath(rs.getString("image_path"));
                PrisonerList.add(prisoner);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return PrisonerList;
    }

    @Override
    public ObservableList<Prisoner> getPrisonerName() {
        List<Prisoner> prisoners = getAllPrisoner();
        ObservableList<Prisoner> prisonerList = FXCollections.observableArrayList(prisoners);
        return prisonerList;
    }

    public void insertPrisonerDB(Prisoner prisoner)
    {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,prisoner.getPrisonerCode());
            ps.setString(2,prisoner.getPrisonerName());
            ps.setString(3, prisoner.getDOB());
            ps.setString(4,prisoner.getGender());
            ps.setString(5,prisoner.getContactName());
            ps.setString(6,prisoner.getContactPhone());
            ps.setString(7,prisoner.getImagePath());

            int rowAffected = ps.executeUpdate();
            if (rowAffected>0)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("");
                alert.setHeaderText("Add prisoner");
                alert.setContentText("Add prisoner success!");
                alert.showAndWait();
            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Add prisoner fail!");alert.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
