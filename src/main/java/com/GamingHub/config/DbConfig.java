package com.GamingHub.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbConfig is a configuration class for managing database connections.
 * It handles the connection to a MySQL database using JDBC.
 */
public class DbConfig {

    // Database configuration information
    private static final String DB_NAME = "gaminghub";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Establishes a connection to the database.
     *
     * @return Connection object for the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getDbConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Make sure it's added to the classpath.", e);
        }

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
