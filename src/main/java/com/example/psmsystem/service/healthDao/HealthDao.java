package com.example.psmsystem.service.healthDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.health.Health;
import com.example.psmsystem.model.health.IHealthDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HealthDao implements IHealthDao<Health> {
    private static final String INSERT_QUERY = "INSERT INTO healths (prisoner_code, weight, height, checkup_date, physical_condition, psychological_signs, situation, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_HEALTH_QUERY = "UPDATE healths SET prisoner_code = ?, weight = ?, height = ?, checkup_date = ?, physical_condition = ?, psychological_signs = ?, situation = ?, notes = ? WHERE health_id = ?";
    private static final String DELETE_HEALTH_QUERY = "DELETE FROM healths WHERE health_id = ?";
    private static final String SELECT_BY_HEALTH_QUERY = "SELECT * FROM healths";
    private static final String SELECT_BY_CODE_DATE_HEALTH_QUERY = "SELECT * FROM healths WHERE prisoner_code = ? AND checkup_date = ?";

    @Override
    public void addHealth(Health health) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,health.getPrisonerCode());
            ps.setDouble(2,health.getWeight());
            ps.setDouble(3,health.getHeight());
            ps.setString(4,health.getCheckupDate());
            ps.setString(5,health.getPhysicalCondition());
            ps.setString(6,health.getPsychologicalSigns());
            ps.setString(7,health.getSituation());
            ps.setString(8,health.getNotes());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Health> getHealth() {
        List<Health> healthList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_HEALTH_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Health health = new Health();
                health.setPrisonerCode(rs.getString("prisoner_code"));
                health.setWeight(rs.getDouble("weight"));
                health.setHeight(rs.getDouble("weight"));
                health.setCheckupDate(rs.getString("checkup_date"));
                health.setPhysicalCondition(rs.getString("physical_condition"));
                health.setPsychologicalSigns(rs.getString("psychological_signs"));
                health.setSituation(rs.getString("situation"));
                health.setNotes(rs.getString("notes"));
                healthList.add(health);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return healthList;
    }

    @Override
    public void updateHealth(Health health, int id) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(UPDATE_HEALTH_QUERY)) {
                ps.setString(1,health.getPrisonerCode());
                ps.setDouble(2,health.getWeight());
                ps.setDouble(3,health.getHeight());
                ps.setString(4,health.getCheckupDate());
                ps.setString(5,health.getPhysicalCondition());
                ps.setString(6,health.getPsychologicalSigns());
                ps.setString(7,health.getSituation());
                ps.setString(8,health.getNotes());
                ps.setInt(9, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteHealth(int id) {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(DELETE_HEALTH_QUERY)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getVisitationId(String prisonerCode, String checkupDate) {
        int healthId = -1;

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE_DATE_HEALTH_QUERY)) {
                ps.setString(1, prisonerCode);
                ps.setString(2, checkupDate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        healthId = rs.getInt("health_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return healthId;
    }
}
