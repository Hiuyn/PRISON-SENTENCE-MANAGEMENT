package com.example.psmsystem.service.userLogDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.userlog.IUserLogDao;
import com.example.psmsystem.model.userlog.UserLog;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.Date;

public class UserLogDao implements IUserLogDao<UserLog> {
    private static final String INSERT_USER_LOG_SQL = "INSERT INTO update_log (date_update, note, user_id) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_USER_LOGS = "SELECT ul.user_id, u.username, ul.date_update, ul.note FROM update_log ul JOIN users u ON ul.user_id = u.user_id";
    private static final String DELETE_USER_LOG_SQL = "DELETE FROM update_log WHERE user_id = ? AND note LIKE '%Updated the latest data%'";
    private static final String SELECT_USER_LOG_SQL = "SELECT date_update FROM update_log WHERE user_id = ? AND note LIKE '%Updated the latest data%'";

    public void insertUserLog(UserLog userLog) {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_LOG_SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(userLog.getDateUpdate()));
            preparedStatement.setString(2, userLog.getNote());
            preparedStatement.setInt(3, userLog.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to log the history.");
        }
    }

    // Lấy tất cả các log
    public List<UserLog> selectAllUserLogs() {
        List<UserLog> userLogs = new ArrayList<>();
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER_LOGS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                LocalDateTime updateDate = rs.getTimestamp("date_update").toLocalDateTime();
                String note = rs.getString("note");
                userLogs.add(new UserLog(rs.getInt("user_id"), username, updateDate, note));
            }
        } catch (SQLException e) {
        }
        return userLogs;
    }

    public void deleteUserLogUpdateDate(UserLog userLog) {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_USER_LOG_SQL)) {

            // Xóa bản ghi cũ nếu có trong cột note chuỗi "Updated the latest data"
            deleteStatement.setInt(1, userLog.getUserId());
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Thêm xử lý lỗi ở đây
        }
    }

    public String timeUpdate(int userId) {
        String timeUpdate = "";
        try {
            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_USER_LOG_SQL);

            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Lấy ngày giờ từ cột update_date trong ResultSet
                    Date date = rs.getTimestamp("date_update");

                    // Định dạng ngày giờ theo yêu cầu ("yyyy-MM-dd HH:mm:ss")
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Chuyển đổi ngày giờ thành chuỗi theo định dạng
                    timeUpdate = dateFormat.format(date);
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return timeUpdate;
    }
}
