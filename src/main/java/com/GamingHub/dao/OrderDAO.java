package com.GamingHub.dao;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.model.OrderModel;
import com.GamingHub.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean placeOrder(int customerId, int productId, int quantity, float totalPrice) {
        String insertOrderSQL = "INSERT INTO customer_order (order_date, customer_id, product_id, quantity, total_price, order_status) " +
                                "VALUES (CURRENT_DATE, ?, ?, ?, ?, 'Processing')";
        String updateStockSQL = "UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?";

        try (Connection conn = DbConfig.getDbConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertOrderSQL);
                 PreparedStatement updateStmt = conn.prepareStatement(updateStockSQL)) {

                // Insert order
                insertStmt.setInt(1, customerId);
                insertStmt.setInt(2, productId);
                insertStmt.setInt(3, quantity);
                insertStmt.setFloat(4, totalPrice);
                int orderInserted = insertStmt.executeUpdate();

                // Update product stock
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, productId);
                updateStmt.setInt(3, quantity);
                int stockUpdated = updateStmt.executeUpdate();

                if (orderInserted > 0 && stockUpdated > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log in production
            return false;
        }
    }
    
    public List<OrderModel> getOrdersByCustomerId(int customerId) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT * FROM customer_order WHERE customer_id = ? ORDER BY order_date DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                CustomerDAO customerDAO = new CustomerDAO();
                ProductDAO productDAO = new ProductDAO();
                CustomerModel customer = customerDAO.getCustomerById(customerId);

                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    Date orderDate = rs.getDate("order_date");
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    float totalPrice = rs.getFloat("total_price");
                    String status = rs.getString("order_status");

                    ProductModel product = productDAO.getProductById(productId);

                    OrderModel order = new OrderModel(orderId, orderDate, totalPrice, customer, product, quantity, status);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();

        try (Connection conn = DbConfig.getDbConnection()) {
            String sql = "SELECT co.order_id, co.order_date, co.total_price, co.quantity, co.order_status, " +
                         "c.customer_id, c.first_name, c.last_name, c.username, " +
                         "p.product_id, p.product_name, p.price " +
                         "FROM customer_order co " +
                         "JOIN customers c ON co.customer_id = c.customer_id " +
                         "JOIN product p ON co.product_id = p.product_id";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setCustomer_id(rs.getInt("customer_id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setUsername(rs.getString("username"));

                ProductModel product = new ProductModel(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    "", 0f, 0, "", 0f, "", null
                );

                OrderModel order = new OrderModel(
                    rs.getInt("order_id"),
                    rs.getDate("order_date"),
                    rs.getFloat("total_price"),
                    customer,
                    product,
                    rs.getInt("quantity"),
                    rs.getString("order_status")
                );

                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE customer_order SET order_status = ? WHERE order_id = ? AND order_status = 'Processing'";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<OrderModel> searchOrders(String keyword) {
        List<OrderModel> orders = new ArrayList<>();

        String sql = "SELECT co.order_id, co.order_date, co.total_price, co.quantity, co.order_status, " +
                     "c.customer_id, c.first_name, c.last_name, c.username, " +
                     "p.product_id, p.product_name, p.price " +
                     "FROM customer_order co " +
                     "JOIN customers c ON co.customer_id = c.customer_id " +
                     "JOIN product p ON co.product_id = p.product_id " +
                     "WHERE CAST(co.order_id AS CHAR) LIKE ? " +
                     "OR c.first_name LIKE ? " +
                     "OR c.last_name LIKE ? " +
                     "OR c.username LIKE ? " +
                     "OR p.product_name LIKE ? " +
                     "OR co.order_status LIKE ?"; // Include order_status

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, likeKeyword);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setCustomer_id(rs.getInt("customer_id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setUsername(rs.getString("username"));

                ProductModel product = new ProductModel(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    "", 0f, 0, "", 0f, "", null
                );

                OrderModel order = new OrderModel(
                    rs.getInt("order_id"),
                    rs.getDate("order_date"),
                    rs.getFloat("total_price"),
                    customer,
                    product,
                    rs.getInt("quantity"),
                    rs.getString("order_status")
                );

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


}
