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
    private static final String INSERT_QUERY = "INSERT INTO healths (health_code, sentence_id, prisoner_id, weight, height, checkup_date, status, level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_HEALTH_QUERY = "UPDATE healths SET hearthCode, sentence_id = ?, prisoner_id = ?, weight = ?, height = ?, checkup_date = ?, status = ?, level = ? WHERE health_id = ?";
    private static final String DELETE_HEALTH_QUERY = "DELETE FROM healths WHERE health_id = ?";
    private static final String SELECT_BY_HEALTH_QUERY = "SELECT h.health_code, h.sentence_id, s.sentences_code, h.prisoner_id, p.prisoner_name, h.weight, h.height, h.checkup_date, h.status, h.level FROM healths h JOIN sentences s ON s.sentence_id = h.sentence_id JOIN prisoners p ON p.prisoner_id = h.prisoner_id ORDER BY checkup_date";
    private static final String SELECT_BY_CODE_DATE_HEALTH_QUERY = "SELECT * FROM healths WHERE hearthCode = ? AND checkup_date = ?";
    private static final String COUNT_HEALTH_QUERY = "SELECT COUNT(*) FROM healths";

    @Override
    public void addHealth(Health health) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,health.getHealthCode());
            ps.setString(2,health.getSentenceId());
            ps.setString(3,health.getPrisonerId());
            ps.setDouble(4,health.getWeight());
            ps.setDouble(5,health.getHeight());
            ps.setString(6,health.getCheckupDate());
            ps.setBoolean(7,health.getStatus());
            ps.setInt(8,health.getLevel());

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
                health.setHealthCode(rs.getString("health_code"));
                health.setPrisonerId(rs.getString("prisoner_id"));
                health.setSentenceId(rs.getString("sentence_id"));
                health.setSentenceCode(rs.getString("sentences_code"));
                health.setPrisonerName(rs.getString("prisoner_name"));
                health.setWeight(rs.getDouble("weight"));
                health.setHeight(rs.getDouble("weight"));
                health.setCheckupDate(rs.getString("checkup_date"));
                health.setStatus(rs.getBoolean("status"));
                health.setLevel(rs.getInt("level"));
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
                ps.setString(1,health.getHealthCode());
                ps.setString(2,health.getSentenceId());
                ps.setString(3,health.getPrisonerId());
                ps.setDouble(4,health.getWeight());
                ps.setDouble(5,health.getHeight());
                ps.setString(6,health.getCheckupDate());
                ps.setBoolean(7,health.getStatus());
                ps.setInt(8,health.getLevel());
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

    @Override
    public int getCountHealth() {
        int count = 0;
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(COUNT_HEALTH_QUERY);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1); // Lấy giá trị của cột đầu tiên (đếm tổng số bản ghi)
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }
}
