package com.example.psmsystem.service.userDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.user.IUserDao;
import com.example.psmsystem.model.user.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao<User> {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/prisonerms";
//    private static final String DB_USER = "root";
////    private static final String DB_PASSWORD = "12345678";
//    private static final String DB_PASSWORD = "";
    private static final String INSERT_QUERY = "INSERT INTO users (name, username, hash_password) VALUES (?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM users WHERE username = ? and hash_password = ?";
    private static final String SELECT_BY_USERNAME_QUERY = "SELECT * FROM users WHERE user_name = ?";
    private static final String SELECT_BY_USER_QUERY = "SELECT * FROM users";

    @Override
    public void addUser(User user) {
        try {
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY);

            String hashPassword = hashPassword(user.getPassword());
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, hashPassword);


            int row = stmt.executeUpdate();
            if(row > 0){
                System.out.println("them thanh cong");
            }
            else{
                System.out.println("khong thanh cong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User checkLogin(String username, String password) {
        User user = null;
        try {
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_PASSWORD_QUERY);
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setFullName(rs.getString("last_name"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("hash_password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean checkUsername(String username) {
        try{
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_QUERY);
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_USER_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setFullName(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("hash_password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    public String hashPassword(String password) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Chuyển đổi mật khẩu thành mảng byte
            byte[] hash = digest.digest(password.getBytes());

            // Chuyển đổi mảng byte thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            System.out.println("Hashed Password: " + hexString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }


}
