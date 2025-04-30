package com.GamingHub.service;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PortfolioService {

    public CustomerModel getCustomerProfile(String username) {
        String query = "SELECT * FROM customers WHERE username = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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
                    rs.getString("password"),
                    rs.getString("imageURL")
                );
            }

        } catch (Exception e) {
            e.printStackTrace(); // You can replace this with proper logging
        }

        return null;
    }
}