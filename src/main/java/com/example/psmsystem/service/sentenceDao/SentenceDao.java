package com.example.psmsystem.service.sentenceDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.health.Health;
import com.example.psmsystem.model.sentence.ISentenceDao;
import com.example.psmsystem.model.sentence.Sentence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentenceDao implements ISentenceDao<Sentence> {
    private static final String INSERT_QUERY = "INSERT INTO sentences (prisoner_code, sentence_type, sentence_code, start_date, end_date, status, parole_eligibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SENTENCE_QUERY = "UPDATE sentences SET prisoner_code = ?, sentence_type = ?, sentence_code = ?, start_date = ?, end_date = ?, status = ?, parole_eligibility = ? WHERE sentence_id = ?";
    private static final String DELETE_SENTENCE_QUERY = "DELETE FROM sentences WHERE sentence_id = ?";
    private static final String SELECT_BY_SENTENCE_QUERY = "SELECT s.prisoner_code, p.prisoner_name, s.sentence_type, s.sentence_code, s.start_date, s.end_date, s.status, s.parole_eligibility FROM sentences s JOIN prisoners p ON p.prisoner_code = s.prisoner_code";
    private static final String SELECT_BY_CODE_SENTENCE_QUERY = "SELECT * FROM sentences WHERE prisoner_code = ?";
    private static final String COUNT_PRISONERS_BY_SENTENCE_TYPE_QUERY = "SELECT sentence_type, COUNT(*) AS prisoner_count FROM sentences GROUP BY sentence_type";


    @Override
    public void addSentence(Sentence sentence) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,sentence.getPrisonerCode());
            ps.setString(2,sentence.getSentenceType());
            ps.setString(3,sentence.getSentenceCode());
            ps.setString(4,sentence.getStartDate());
            ps.setString(5,sentence.getEndDate());
            ps.setString(6,sentence.getStatus());
            ps.setString(7,sentence.getParoleEligibility());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sentence> getSentence() {
        List<Sentence> sentenceList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_SENTENCE_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Sentence sentence = new Sentence();
                sentence.setPrisonerCode(rs.getString("prisoner_code"));
                sentence.setPrisonerName(rs.getString("prisoner_name"));
                sentence.setSentenceType(rs.getString("sentence_type"));
                sentence.setSentenceCode(rs.getString("sentence_code"));
                sentence.setStartDate(rs.getString("start_date"));
                sentence.setEndDate(rs.getString("end_date"));
                sentence.setStatus(rs.getString("status"));
                sentence.setParoleEligibility(rs.getString("parole_eligibility"));
                sentenceList.add(sentence);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sentenceList;
    }

    @Override
    public void updateSentence(Sentence sentence, int id) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(UPDATE_SENTENCE_QUERY)) {
                ps.setString(1,sentence.getPrisonerCode());
                ps.setString(2,sentence.getSentenceType());
                ps.setString(3,sentence.getSentenceCode());
                ps.setString(4,sentence.getStartDate());
                ps.setString(5,sentence.getEndDate());
                ps.setString(6,sentence.getStatus());
                ps.setString(7,sentence.getParoleEligibility());
                ps.setInt(8, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSentence(int id) {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(DELETE_SENTENCE_QUERY)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSentenceId(String prisonerCode) {
        int sentenceId = -1;

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE_SENTENCE_QUERY)) {
                ps.setString(1, prisonerCode);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        sentenceId = rs.getInt("sentence_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sentenceId;
    }

    public Map<String, Integer> countPrisonersBySentenceType() {
        Map<String, Integer> prisonersBySentenceType = new HashMap<>();

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(COUNT_PRISONERS_BY_SENTENCE_TYPE_QUERY);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String sentenceType = rs.getString("sentence_type");
                int prisonerCount = rs.getInt("prisoner_count");
                prisonersBySentenceType.put(sentenceType, prisonerCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prisonersBySentenceType;
    }
}
