package com.example.psmsystem.service.assessDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.assess.Assess;
import com.example.psmsystem.model.assess.IAssessDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssessDao implements IAssessDao<Assess> {
    private static final String INSERT_QUERY = "INSERT INTO incareration_process (prisoner_code, event_date, event_type, desctiption, note) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ASSESS_QUERY = "UPDATE incareration_process SET prisoner_code = ?, event_date = ?, event_type = ?, desctiption = ?, note = ? WHERE process_id = ?";
    private static final String DELETE_ASSESS_QUERY = "DELETE FROM incareration_process WHERE process_id = ?";
    private static final String SELECT_BY_ASSESS_QUERY = "SELECT ip.prisoner_code, p.prisoner_name,ip.event_date, ip.event_type, ip.desctiption, ip.note FROM incareration_process ip JOIN prisoners p ON p.prisoner_code = ip.prisoner_code";
    private static final String SELECT_BY_CODE_DATE_ASSESS_QUERY = "SELECT * FROM incareration_process WHERE prisoner_code = ? AND event_date = ?";

    @Override
    public void addAssess(Assess assess) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,assess.getPrisonerCode());
            ps.setString(2,assess.getEventDate());
            ps.setString(3,assess.getEventType());
            ps.setString(4,assess.getDesctiption());
            ps.setString(5,assess.getNote());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Assess> getAssess() {
        List<Assess> assessList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_ASSESS_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Assess assess = new Assess();
                assess.setPrisonerCode(rs.getString("prisoner_code"));
                assess.setPrisonerName(rs.getString("prisoner_name"));
                assess.setEventDate(rs.getString("event_date"));
                assess.setEventType(rs.getString("event_type"));
                assess.setDesctiption(rs.getString("desctiption"));
                assess.setNote(rs.getString("note"));
                assessList.add(assess);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return assessList;
    }

    @Override
    public void updateAssess(Assess assess, int id) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(UPDATE_ASSESS_QUERY)) {
                ps.setString(1,assess.getPrisonerCode());
                ps.setString(2,assess.getEventDate());
                ps.setString(3,assess.getEventType());
                ps.setString(4,assess.getDesctiption());
                ps.setString(5,assess.getNote());
                ps.setInt(6, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAssess(int id) {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(DELETE_ASSESS_QUERY)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAssessId(String prisonerCode, String eventDate) {
        int assessId = -1;

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE_DATE_ASSESS_QUERY)) {
                ps.setString(1, prisonerCode);
                ps.setString(2, eventDate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        assessId = rs.getInt("process_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assessId;
    }
}
