package com.GamingHub.dao;

import com.GamingHub.config.DbConfig;
import java.sql.*;
import java.util.*;

public class DashboardDAO {

    private Connection conn;

    public DashboardDAO() throws SQLException {
        this.conn = DbConfig.getDbConnection();
    }

    public int getUserCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getProductCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM product";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getOrderCountByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer_order WHERE order_status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public float getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(total_price) FROM customer_order WHERE order_status = 'SHIPPED'";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getFloat(1) : 0;
        }
    }

    public Map<String, Integer> getCategorySalesData() throws SQLException {
        String sql = """
            SELECT c.category_name, SUM(co.total_price) AS total_sold
            FROM customer_order co
            JOIN product p ON co.product_id = p.product_id
            JOIN category c ON p.category_id = c.category_id
            GROUP BY c.category_name
        """;
        Map<String, Integer> data = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.put(rs.getString("category_name"), rs.getInt("total_sold"));
            }
        }
        return data;
    }

    public Map<String, Float> getDailySalesData() throws SQLException {
        String sql = """
                SELECT DATE_FORMAT(order_date, '%W') AS day_name, SUM(total_price) AS total_sales
                FROM customer_order
                WHERE order_status = 'SHIPPED'
                GROUP BY day_name
                ORDER BY FIELD(day_name, 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday')
            """;
        Map<String, Float> data = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.put(rs.getString("day_name"), rs.getFloat("total_sales"));
            }
        }
        return data;
    }


}
