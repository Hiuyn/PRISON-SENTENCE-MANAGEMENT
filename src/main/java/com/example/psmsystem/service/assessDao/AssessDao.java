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
    private static final String INSERT_QUERY = "INSERT INTO incareration_process (process_code, sentence_id, prisoner_id, date_of_occurrence, event_type, level, note) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ASSESS_QUERY = "UPDATE incareration_process SET process_code = ?, sentence_id = ?, prisoner_id = ?, date_of_occurrence = ?, event_type = ?, level = ?, note = ? WHERE process_id = ?";
    private static final String DELETE_ASSESS_QUERY = "DELETE FROM incareration_process WHERE process_id = ?";
    private static final String SELECT_BY_ASSESS_QUERY = "SELECT ip.process_code, ip.sentence_id, s.sentences_code, ip.prisoner_id, p.prisoner_name,ip.date_of_occurrence, ip.event_type, ip.level, ip.note FROM incareration_process ip JOIN sentences s ON s.sentence_id = ip.sentence_id JOIN prisoners p ON p.prisoner_id = ip.prisoner_id ORDER BY date_of_occurrence";
    private static final String SELECT_BY_CODE_DATE_ASSESS_QUERY = "SELECT * FROM incareration_process WHERE process_code = ? AND date_of_occurrence = ?";
    private static final String MAX_PROCESS_CODE_QUERY = "SELECT MAX(CAST(SUBSTRING(process_code, 2) AS UNSIGNED)) AS max_health_code FROM incareration_process WHERE process_code REGEXP '^H[0-9]+$'";

    @Override
    public void addAssess(Assess assess) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,assess.getProcessCode());
            ps.setString(2,assess.getSentencesId());
            ps.setInt(3,assess.getPrisonerId());
            ps.setString(4,assess.getDateOfOccurrence());
            ps.setString(5,assess.getEventType());
            ps.setInt(6,assess.getLevel());
            ps.setString(7,assess.getNote());

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
                assess.setProcessCode(rs.getString("process_code"));
                assess.setSentencesId(rs.getString("sentence_id"));
                assess.setSentencesCode(rs.getInt("sentences_code"));
                assess.setPrisonerId(rs.getInt("prisoner_id"));
                assess.setPrisonerName(rs.getString("prisoner_name"));
                assess.setDateOfOccurrence(rs.getString("date_of_occurrence"));
                assess.setEventType(rs.getString("event_type"));
                assess.setLevel(rs.getInt("level"));
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
                ps.setString(1,assess.getProcessCode());
                ps.setString(2,assess.getSentencesId());
                ps.setInt(3,assess.getPrisonerId());
                ps.setString(4,assess.getDateOfOccurrence());
                ps.setString(5,assess.getEventType());
                ps.setInt(6,assess.getLevel());
                ps.setString(7,assess.getNote());
                ps.setInt(8, id);
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

    @Override
    public int getCountAssess() {
        int maxNumber = 0;
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(MAX_PROCESS_CODE_QUERY);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maxNumber = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return maxNumber;
    }
}
