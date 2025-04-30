package com.GamingHub.service;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CustomerModel;

public class RegisterService {
    private static final Logger LOGGER = Logger.getLogger(RegisterService.class.getName());

    public Boolean addCustomer(CustomerModel customerModel) {
        Connection dbConn = null;
        PreparedStatement insertStmt = null;
        boolean success = false;

        try {
            dbConn = DbConfig.getDbConnection();
            dbConn.setAutoCommit(false);

            if (customerExists(dbConn, customerModel.getUsername(), customerModel.getEmail(), customerModel.getNumber())) {
                LOGGER.log(Level.WARNING, "Customer already exists: {0}", customerModel.getUsername());
                return false;
            }

            String insertQuery = "INSERT INTO customers (first_name, last_name, username, dob, gender, email, number, password, imageURL) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            insertStmt = dbConn.prepareStatement(insertQuery);
            insertStmt.setString(1, customerModel.getFirst_name());
            insertStmt.setString(2, customerModel.getLast_name());
            insertStmt.setString(3, customerModel.getUsername());
            insertStmt.setDate(4, Date.valueOf(customerModel.getDob()));
            insertStmt.setString(5, customerModel.getGender());
            insertStmt.setString(6, customerModel.getEmail());
            insertStmt.setString(7, customerModel.getNumber());
            insertStmt.setString(8, customerModel.getPassword());
            insertStmt.setString(9, customerModel.getImageURL());

            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                dbConn.commit();
                success = true;
                LOGGER.log(Level.INFO, "Successfully registered customer: {0}", customerModel.getUsername());
            }
        } catch (SQLException e) {
            rollbackConnection(dbConn);
            logSQLError(e);
        } catch (Exception e) {
            rollbackConnection(dbConn);
            LOGGER.log(Level.SEVERE, "Error registering customer: " + e.getMessage(), e);
        } finally {
            closeResources(insertStmt, dbConn);
        }

        return success;
    }

    private boolean customerExists(Connection conn, String username, String email, String number) throws SQLException {
        String checkQuery = "SELECT 1 FROM customers WHERE username = ? OR email = ? OR number = ?LIMIT 1";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            checkStmt.setString(3, number);
            try (ResultSet rs = checkStmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void logSQLError(SQLException e) {
        if ("23000".equals(e.getSQLState()) && e.getErrorCode() == 1062) {
            LOGGER.log(Level.WARNING, "Duplicate entry attempt: {0}", e.getMessage());
        } else {
            LOGGER.log(Level.SEVERE, "SQL Error: State=" + e.getSQLState()
                    + " Code=" + e.getErrorCode() + " Message=" + e.getMessage(), e);
        }
    }

    private void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error during rollback: " + ex.getMessage(), ex);
            }
        }
    }

    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception ex) {
                    LOGGER.log(Level.WARNING, "Error closing resource: " + ex.getMessage(), ex);
                }
            }
        }
    }
}
