package com.example.psmsystem.service;

import com.example.psmsystem.model.IUserDao;
import com.example.psmsystem.model.User;
import java.sql.*;

import java.util.List;

public class UserDao implements IUserDao<User> {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlytunhan";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    private static final String INSERT_QUERY = "INSERT INTO users (first_name, last_name, email, user_name, password) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM users WHERE user_name = ? and password = ?";
    private static final String SELECT_BY_USERNAME_QUERY = "SELECT * FROM users WHERE user_name = ?";

    @Override
    public void addUser(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserName());
            stmt.setString(5, user.getPassword());
            int row = stmt.executeUpdate();
            if(row > 0){}
            else{}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User checkLogin(String username, String password) {
        User user = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_PASSWORD_QUERY)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUserName(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean checkUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_QUERY)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

}
