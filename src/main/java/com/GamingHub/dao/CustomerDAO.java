package com.GamingHub.dao;

import java.sql.*;
import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.model.OrderModel;
import com.GamingHub.model.ProductModel;
import com.GamingHub.util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());

    public boolean exists(String username, String email, String number) throws SQLException {
        String sql = "SELECT 1 FROM customers WHERE username = ? OR email = ? OR number = ? LIMIT 1";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, number);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean insert(CustomerModel customer) {
        String sql = "INSERT INTO customers (first_name, last_name, username, dob, gender, email, number, password, imageURL) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            stmt.setString(1, customer.getFirst_name());
            stmt.setString(2, customer.getLast_name());
            stmt.setString(3, customer.getUsername());
            stmt.setDate(4, Date.valueOf(customer.getDob()));
            stmt.setString(5, customer.getGender());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getNumber());
            stmt.setString(8, customer.getPassword());
            stmt.setString(9, customer.getImageURL());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Insert failed: " + e.getMessage(), e);
            return false;
        }
    }
    
    public void deleteCustomerById(int customerId) {
    	String DELETE_SQL = "DELETE FROM customers WHERE customer_id = ?";
    	
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, customerId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    
    public boolean hasOrders(int customerId) {
        String CHECK_SQL = "SELECT COUNT(*) FROM customer_order WHERE customer_id = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_SQL)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public CustomerModel getCustomerByUsername(String username) {
        String sql = "SELECT * FROM customers WHERE username = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
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
                        rs.getString("password"),
                        rs.getString("imageURL")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get customer: " + e.getMessage(), e);
        }
        return null;
    }
    
    public CustomerModel getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
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
                        rs.getString("password"),
                        rs.getString("imageURL")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get customer: " + e.getMessage(), e);
        }
        return null;
    }
    
    
    public List<CustomerModel> searchCustomers(String searchTerm) {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE first_name LIKE ? OR last_name LIKE ? OR username LIKE ? OR email LIKE ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String keyword = "%" + searchTerm + "%";
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, keyword);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CustomerModel customer = new CustomerModel(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getDate("dob").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("email"),
                    rs.getString("number"),
                    rs.getString("password"),
                    rs.getString("imageURL")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during customer search: " + e.getMessage(), e);
        }

        return customers;
    }
    
    public List<CustomerModel> getAllCustomers() {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CustomerModel customer = new CustomerModel(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getDate("dob").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("email"),
                    rs.getString("number"),
                    rs.getString("password"),
                    rs.getString("imageURL")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    
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
