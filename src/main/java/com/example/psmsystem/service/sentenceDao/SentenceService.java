package com.example.psmsystem.service.sentenceDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.dto.SentenceDTO;
import com.example.psmsystem.model.sentence.SentenceServiceImpl;
import com.example.psmsystem.model.sentence.Sentence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SentenceService implements SentenceServiceImpl<SentenceDTO> {
    private static final String SELECT_BY_SENTENCE_QUERY = "SELECT s.prisoner_id, p.prisoner_name, p.identity_card, s.sentence_type, s.sentences_code, s.crimes_code, s.start_date, s.end_date, s.release_date, s.status, s.parole_eligibility FROM sentences s JOIN prisoners p ON p.prisoner_id = s.prisoner_id";

    @Override
    public List<SentenceDTO> getSentence() {

        List<SentenceDTO> sentenceList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_SENTENCE_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Sentence sentence = new Sentence();
                sentence.setSentenceCode(rs.getInt("sentences_code"));
                sentence.setSentenceType(rs.getString("sentence_type"));
//                sentence.setCrimesCode(rs.getString("crimes_code"));
                sentence.setStartDate(rs.getDate("start_date"));
                sentence.setEndDate(rs.getDate("end_date"));
                sentence.setReleaseDate(rs.getDate("release_date"));
                sentence.setStatus(rs.getBoolean("status"));
                sentence.setParole(rs.getString("parole_eligibility"));

                SentenceDTO sentenceDTO = new SentenceDTO(
                        rs.getString("prisoner_name"),
                        rs.getInt("prisoner_id"),
                        rs.getString("identity_card"),
                        sentence
                );
                sentenceList.add(sentenceDTO);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sentenceList;
    }

    @Override
    public ObservableList<SentenceDTO> getPrisonerName() {
        List<SentenceDTO> sentence = getSentence();
        ObservableList<SentenceDTO> prisonerList = FXCollections.observableArrayList(sentence);
        return prisonerList;
    }
}
