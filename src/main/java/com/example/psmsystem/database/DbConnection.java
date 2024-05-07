/**
 * @author inforkgodara
 */
package com.example.psmsystem.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class DbConnection {

    private Connection con;
    private static DbConnection dbc;

    private DbConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/prisoner_sentence";
            String username = "root";
            String password = "12345678";
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DbConnection getDatabaseConnection() {
        if (dbc == null) {
            dbc = new DbConnection();
        }
        return dbc;
    }

    public Connection getConnection() {
        return con;
    }

}
