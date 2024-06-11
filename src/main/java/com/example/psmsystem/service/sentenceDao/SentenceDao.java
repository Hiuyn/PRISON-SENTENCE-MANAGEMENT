package com.example.psmsystem.service.sentenceDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.sentence.ISentenceDao;
import com.example.psmsystem.model.sentence.Sentence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentenceDao implements ISentenceDao<Sentence> {
    private static final String INSERT_QUERY = "INSERT INTO sentences (prisoner_id, sentences_code, sentence_type, crimes_code, start_date, end_date, release_date, status, parole_eligibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SENTENCE_QUERY = "UPDATE sentences SET prisoner_id = ?, sentences_code = ?, sentence_type = ?, crimes_code = ?, start_date = ?, end_date = ?, release_date = ?, status = ?, parole_eligibility = ?WHERE sentence_id = ?";
    private static final String DELETE_SENTENCE_QUERY = "DELETE FROM sentences WHERE sentence_id = ?";
    private static final String SELECT_BY_SENTENCE_QUERY = "SELECT s.sentence_id, s.prisoner_id, p.prisoner_name, s.sentence_type, s.sentences_code, s.crimes_code, s.start_date, s.end_date, s.release_date, s.status, s.parole_eligibility FROM sentences s JOIN prisoners p ON p.prisoner_id = s.prisoner_id";
    private static final String SELECT_BY_CODE_SENTENCE_QUERY = "SELECT * FROM sentences WHERE sentences_code = ?";
    private static final String COUNT_PRISONERS_BY_SENTENCE_TYPE_QUERY = "SELECT sentence_type, COUNT(*) AS prisoner_count FROM sentences GROUP BY sentence_type";
    private static final String SELECT_SENTENCE_ID_MAX = "SELECT MAX(sentences_code) AS max_sentence_code FROM sentences";

    private static final String INSERT_SENTENCE_CRIME = "INSERT INTO sentence_crimes VALUE (?, ?, ?)";
    @Override
    public boolean addSentence(Sentence sentence) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setInt(1,sentence.getPrisonerId());
            ps.setInt(2,sentence.getSentenceCode());
            ps.setString(3,sentence.getSentenceType());
            ps.setString(4,sentence.getCrimesCode());
            ps.setDate(5, sentence.getStartDate());
            ps.setDate(6, sentence.getEndDate());
            ps.setDate(7, sentence.getReleaseDate());
            ps.setBoolean(8,sentence.isStatus());
            ps.setString(9,sentence.getParole());
            int rowAffected = ps. executeUpdate();
            if (rowAffected > 0)
            {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
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
                sentence.setSentenceId(rs.getInt("sentence_id"));
                sentence.setPrisonerId(rs.getInt("prisoner_id"));
                sentence.setPrisonerName(rs.getString("prisoner_name"));
                sentence.setSentenceCode(rs.getInt("sentences_code"));
                sentence.setSentenceType(rs.getString("sentence_type"));
                sentence.setCrimesCode(rs.getString("crimes_code"));
                sentence.setStartDate(rs.getDate("start_date"));
                sentence.setEndDate(rs.getDate("end_date"));
                sentence.setReleaseDate(rs.getDate("release_date"));
                sentence.setStatus(rs.getBoolean("status"));
                sentence.setParole(rs.getString("parole_eligibility"));
                sentenceList.add(sentence);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sentenceList;
    }

    @Override
    public int getMaxIdSentence()
    {
        try {
            Connection connection = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_SENTENCE_ID_MAX);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_sentence_code")+1;
            } else {
                return -1;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    @Override
    public void updateSentence(Sentence sentence, int id) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(UPDATE_SENTENCE_QUERY)) {
//                ps.setInt(1, sentence.getSentenceId());
                ps.setInt(1,sentence.getPrisonerId());
                ps.setInt(2,sentence.getSentenceCode());
                ps.setString(3,sentence.getSentenceType());
                ps.setString(4,sentence.getCrimesCode());
                ps.setDate(5, (Date) sentence.getStartDate());
                ps.setDate(6, (Date) sentence.getEndDate());
                ps.setDate(7, (Date) sentence.getReleaseDate());
                ps.setBoolean(8,sentence.isStatus());
                ps.setString(9,sentence.getParole());
                ps.setInt(10, id);
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
    public int getSentenceId(int sentenceCode) {
        int sentenceId = -1;

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE_SENTENCE_QUERY)) {
                ps.setInt(1, sentenceCode);
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

    public void insertSentenceCrimes(int sentenceId, int crimeId, int year)
    {
        try{
            Connection connection = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_SENTENCE_CRIME);
            ps.setInt(1,sentenceId);
            ps.setInt(2,crimeId);
            ps.setInt(3,year);
            ps.executeQuery();
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
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

    @Override
    public ObservableList<Sentence> getPrisonerName() {
        List<Sentence> sentence = getSentence();
        ObservableList<Sentence> prisonerList = FXCollections.observableArrayList(sentence);
        return prisonerList;
    }
}
