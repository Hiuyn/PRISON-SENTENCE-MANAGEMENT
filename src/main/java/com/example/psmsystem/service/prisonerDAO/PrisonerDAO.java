package com.example.psmsystem.service.prisonerDAO;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrisonerDAO implements IPrisonerDao<Prisoner> {
    private static final String INSERT_QUERY = "INSERT INTO prisoner (prisonerId, prisonerName, imagePath) VALUES (?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String SELECT_BY_USERNAME_QUERY = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_BY_PRISONER_QUERY = "SELECT * FROM prisoner";
//    private static final String SELECT_BY_PRISONER_QUERY = "SELECT * FROM prisoner";
////    private static final String INSERT_INTO_PRISONER_QUERY = "INSERT INTO  prisoner VALUES (prisonerId = ?)";

    @Override
    public List<Prisoner> getAllPrisoner() {
        List<Prisoner> userList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerId(rs.getString("maTN"));
                prisoner.setPrisonerName(rs.getString("nameTN"));
                userList.add(prisoner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public List<Prisoner> getPrisonerInItem() {
        List<Prisoner> PrisonerList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerId(String.valueOf(rs.getInt("prisonerId")));
                prisoner.setPrisonerName(rs.getString("prisonerName"));
                prisoner.setImagePath(rs.getString("imagePath"));
                PrisonerList.add(prisoner);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return PrisonerList;
    }

    public void insertPrisonerDB(Prisoner prisoner)
    {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,prisoner.getPrisonerId());
            ps.setString(2,prisoner.getPrisonerName());
            ps.setString(3,prisoner.getImagePath());
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
