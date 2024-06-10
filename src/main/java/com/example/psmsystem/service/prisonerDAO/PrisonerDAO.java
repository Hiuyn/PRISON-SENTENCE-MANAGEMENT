package com.example.psmsystem.service.prisonerDAO;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.prisoner.IPrisonerDao;
import com.example.psmsystem.model.prisoner.Prisoner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public class PrisonerDAO implements IPrisonerDao<Prisoner> {
    private static final String INSERT_QUERY = "INSERT INTO prisoners (prisoner_id, prisoner_name, date_birth, gender, identity_card, contacter_name, contacter_phone, image, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String SELECT_BY_USERNAME_QUERY = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_BY_PRISONER_QUERY = "SELECT * FROM prisoners WHERE status = ? ";
    //    private static final String SELECT_MIN_EMPTY_PRISONER_CODE = "SELECT prisoner_id FROM prisoners WHERE status = ? ORDER BY prisoner_id ASC LIMIT 1";
    private  static final String DELETE_PRISONER_BY_ID = "DELETE FROM prisoners WHERE prisoner_id = ?";
    private  static final String DELETE_SENTENCE_BY_ID = "DELETE FROM sentences WHERE prisoner_id = ?";
    private static final String SELECT_PRISONER_BY_AGE = "SELECT * FROM prisoners " +
            "WHERE TIMESTAMPDIFF(YEAR, date_birth, CURDATE()) BETWEEN ? AND ? " +
            "AND gender = ?";
    private static final String SELECT_MAX_VALUES_PRISONER_ID ="SELECT MAX(prisoner_id) AS max_prisoner_id FROM prisoners";
    private static final String SELECT_YEAR_SENTENCE = "SELECT * FROM sentences ";
    private static final String SELECT_BY_CRIMES = "SELECT * FROM crimes";
    private static final String SELECT_BY_PRISONER_QUERY_COMBOBOX = "SELECT * FROM prisoners";
    private static final String COUNT_PRISONER_QUERY = "SELECT COUNT(*) FROM prisoners";
    private static final String COUNT_GENDER_QUERY = "SELECT gender, COUNT(*) as count FROM prisoners GROUP BY gender";

////    private static final String INSERT_INTO_PRISONER_QUERY = "INSERT INTO  prisoner VALUES (prisonerId = ?)";

    @Override
    public List<Prisoner> getAllPrisoner() {
        List<Prisoner> prisonerList = new ArrayList<>();
        try {
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            statement.setBoolean(1,false);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerCode(rs.getString("prisoner_id"));
                prisoner.setPrisonerName(rs.getString("prisoner_name"));
                prisoner.setDOB(rs.getString("date_birth"));
                prisoner.setGender(rs.getInt("gender"));
                prisoner.setIdentityCard(rs.getString("identity_card"));
                prisoner.setContactName(rs.getString("contacter_name"));
                prisoner.setContactPhone(rs.getString("contacter_phone"));
                prisoner.setImagePath(rs.getString("image"));
                prisoner.setStatus(rs.getBoolean("status"));
                prisonerList.add(prisoner);
            }
        } catch (SQLException e) {
            System.out.println("PrisonerDao getAllPrisoner : " + e.getMessage());
        }
        return prisonerList;
    }
    public List<Prisoner> getItemComboboxPrisoner() {
        List<Prisoner> prisonerList = new ArrayList<>();
        try {
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY_COMBOBOX);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerCode(rs.getString("prisoner_id"));
                prisoner.setPrisonerName(rs.getString("prisoner_name"));
                prisoner.setDOB(rs.getString("date_birth"));
                prisoner.setGender(rs.getInt("gender"));
                prisoner.setContactName(rs.getString("contacter_name"));
                prisoner.setContactPhone(rs.getString("contacter_phone"));
                prisoner.setImagePath(rs.getString("image"));
                prisonerList.add(prisoner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prisonerList;
    }
    public List<Prisoner> getPrisonerByAge(int age, int gender) {
        List<Prisoner> prisonerList = new ArrayList<>();
        try{
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_PRISONER_BY_AGE);
            if (age == 1)
            {
                ps.setInt(1,14);
                ps.setInt(2,18);
            } else if (age == 2) {
                ps.setInt(1,18);
                ps.setInt(2,40);
            }else if(age == 3){
                ps.setInt(1,40);
                ps.setInt(2,60);
            }else if(age == 4){
                ps.setInt(1,60);
                ps.setInt(2,120);
            }
            ps.setInt(3,gender);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerCode(rs.getString("prisoner_id"));
                prisoner.setPrisonerName(rs.getString("prisoner_name"));
                prisoner.setDOB(rs.getString("date_birth"));
                prisoner.setGender(rs.getInt("gender"));
                prisoner.setIdentityCard(rs.getString("identity_card"));
                prisoner.setContactName(rs.getString("contacter_name"));
                prisoner.setContactPhone(rs.getString("contacter_phone"));
                prisoner.setImagePath(rs.getString("image"));
                prisoner.setStatus(rs.getBoolean("status"));
                prisoner.setUser_id(rs.getInt("user_id"));
                prisonerList.add(prisoner);
            }
        }catch (Exception e){
            System.out.println("PrisonerDao getPrisonerByAge : " + e.getMessage());
        }
        return prisonerList;
    }
    public List<Prisoner> getPrisonerInItem() {
        List<Prisoner> PrisonerList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRISONER_QUERY);
            statement.setBoolean(1,false);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Prisoner prisoner = new Prisoner();
                prisoner.setPrisonerCode(String.valueOf(rs.getInt("prisoner_id")));
                prisoner.setPrisonerName(rs.getString("prisoner_name"));
                prisoner.setImagePath(rs.getString("image"));
                PrisonerList.add(prisoner);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return PrisonerList;
    }
    @Override
    public ObservableList<Prisoner> getPrisonerName() {
        List<Prisoner> prisoners = getItemComboboxPrisoner();
        ObservableList<Prisoner> prisonerList = FXCollections.observableArrayList(prisoners);
        return prisonerList;
    }

    public boolean insertPrisonerDB(Prisoner prisoner)
    {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,prisoner.getPrisonerCode());
            ps.setString(2,prisoner.getPrisonerName());
            ps.setString(3, prisoner.getDOB());
            ps.setInt(4,prisoner.getGender());
            ps.setString(5,prisoner.getIdentityCard());
            ps.setString(6,prisoner.getContactName());
            ps.setString(7,prisoner.getContactPhone());
            ps.setString(8,prisoner.getImagePath());
            ps.setBoolean(9,prisoner.isStatus());
            int rowAffected = ps.executeUpdate();
            if (rowAffected>0)
            {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //    public List<String> getCrimes()
//    {
//        List<String> crimesList = new ArrayList<>();
//        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
//        {
//            PreparedStatement ps = connection.prepareStatement(SELECT_BY_CRIMES);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next())
//            {
//                crimesList.add(rs.getString("crime_name"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return crimesList;
//    }
    public int getIdEmpty() {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_MAX_VALUES_PRISONER_ID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_prisoner_id")+1;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCountPrisoner(){
        int count = 0;
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(COUNT_PRISONER_QUERY);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1); // Lấy giá trị của cột đầu tiên (đếm tổng số bản ghi)
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public Map<String, Integer> countGender() {
        Map<String, Integer> genderCount = new HashMap<>();

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(COUNT_GENDER_QUERY);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String gender = rs.getString("gender");
                int count = rs.getInt("count");
                genderCount.put(gender, count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return genderCount;
    }

    public boolean deletePrisoner(String prisonerCode)
    {

        try {
            Connection connection = DbConnection.getDatabaseConnection().getConnection();
//            connection.setAutoCommit(false); // Bắt đầu giao dịch
            PreparedStatement psSentence = connection.prepareStatement(DELETE_SENTENCE_BY_ID);
            PreparedStatement psPrisoner = connection.prepareStatement(DELETE_PRISONER_BY_ID);

            psSentence.setString(1,prisonerCode);
            psPrisoner.setString(1,prisonerCode);

            psSentence.executeUpdate();
            psPrisoner.executeUpdate();


//            connection.commit(); // Hoàn thành giao dịch

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
