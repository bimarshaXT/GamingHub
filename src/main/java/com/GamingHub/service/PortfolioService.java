package com.GamingHub.service;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.util.PasswordUtil;

import java.sql.*;
import java.time.LocalDate;

public class PortfolioService {

    public CustomerModel getCustomerProfile(String username) throws SQLException {
        String sql = "SELECT customer_id, first_name, last_name, username, dob, gender, email, number, imageURL " +
                     "FROM customers WHERE username = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CustomerModel(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getDate("dob").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("email"),
                    rs.getString("number"),
                    null, // Never return password
                    rs.getString("imageURL")
                );
            }
        }
        return null;
    }

    public boolean updateProfile(CustomerModel customer, String currentPassword) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DbConfig.getDbConnection();
            conn.setAutoCommit(false);

            // 1. Verify current password (mandatory)
            if (!verifyCurrentPassword(customer.getUsername(), currentPassword)) {
                return false;
            }

            // 2. Update all profile fields except image
            String sql = "UPDATE customers SET first_name=?, last_name=?, dob=?, " +
                        "gender=?, email=?, number=? WHERE username=?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getFirst_name());
            stmt.setString(2, customer.getLast_name());
            stmt.setDate(3, Date.valueOf(customer.getDob()));
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getNumber());
            stmt.setString(7, customer.getUsername());

            int rowsUpdated = stmt.executeUpdate();
            conn.commit();
            return rowsUpdated == 1;
            
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    public boolean updateProfileImage(String username, String imageUrl) throws SQLException {
        String sql = "UPDATE customers SET imageURL=? WHERE username=?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, imageUrl);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(String username, String currentPassword, String newPassword) throws SQLException {
        // First verify current password
        if (!verifyCurrentPassword(username, currentPassword)) {
            return false;
        }

        String sql = "UPDATE customers SET password=? WHERE username=?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, PasswordUtil.encrypt(username, newPassword));
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        }
    }

    private boolean verifyCurrentPassword(String username, String currentPassword) throws SQLException {
        String sql = "SELECT password FROM customers WHERE username = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedEncryptedPassword = rs.getString("password");
                String decryptedPassword = PasswordUtil.decrypt(storedEncryptedPassword, username);
                return currentPassword.equals(decryptedPassword);
            }
        }
        return false;
    }
}