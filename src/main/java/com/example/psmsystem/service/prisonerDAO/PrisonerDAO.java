package com.example.psmsystem.service.prisonerDAO;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrisonerDAO implements IPrisonerDao<Prisoner> {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlytunhan";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    private static final String SELECT_BY_PRISONER_QUERY = "SELECT * FROM prisoner";

    @Override
    public List<Prisoner> getAllPrisoner() {
        List<Prisoner> userList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setMaTN(rs.getString("maTN"));
                prisoner.setNameTN(rs.getString("nameTN"));
                userList.add(prisoner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
