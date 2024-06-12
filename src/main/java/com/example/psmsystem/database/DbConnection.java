/**
 * @author inforkgodara
 */
package com.example.psmsystem.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        Properties properties = new Properties();

        // Load the properties file "application.properties" from the classpath into an InputStream
        InputStream input = DbConnection.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            // Load the properties from the input stream into the Properties object
            properties.load(input);

            // Retrieve the database connection properties from the Properties object
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Establish a connection to the database using the DriverManager class
            // and the database connection properties
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DbConnection getDatabaseConnection() throws SQLException {
        if (dbc == null) {
            dbc = new DbConnection();
        } else if (dbc.getConnection().isClosed()) {
            dbc = new DbConnection();
        }
        return dbc;
    }

    public Connection getConnection() {
        return con;
    }

}
