package com.GamingHub.dao;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CategoryModel;
import com.GamingHub.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT p.*, c.category_name, c.category_description " +
                       "FROM product p " +
                       "JOIN category c ON p.category_id = c.category_id";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You can log this instead
        }

        return products;
    }
    
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new CategoryModel(
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("category_description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log instead of printStackTrace in production
        }

        return categories;
    }


    public ProductModel getProductById(int productId) {
        ProductModel product = null;
        String query = "SELECT p.*, c.category_name, c.category_description " +
                       "FROM product p " +
                       "JOIN category c ON p.category_id = c.category_id " +
                       "WHERE p.product_id = ?";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, productId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    product = mapProduct(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public boolean addProduct(ProductModel product) {
        String sql = "INSERT INTO product (product_name, product_description, price, stock_quantity, brand, discount, image_url, category_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProduct_name());
            stmt.setString(2, product.getProduct_description());
            stmt.setFloat(3, product.getPrice());
            stmt.setInt(4, product.getStock_quantity());
            stmt.setString(5, product.getBrand());
            stmt.setFloat(6, product.getDiscount());
            stmt.setString(7, product.getImage_url());
            stmt.setInt(8, product.getCategory().getCategory_id());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Ideally use a logger
            return false;
        }
    }

    public boolean updateProduct(ProductModel product) {
    	String sql = "UPDATE product SET product_name=?, brand=?, category_id=?, price=?, discount=?, stock_quantity=?, product_description=?, image_url=? WHERE product_id=?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProduct_name());
            stmt.setString(2, product.getBrand());
            stmt.setInt(3, product.getCategory().getCategory_id());
            stmt.setFloat(4, product.getPrice());
            stmt.setFloat(5, product.getDiscount());
            stmt.setInt(6, product.getStock_quantity());
            stmt.setString(7, product.getProduct_description());
            stmt.setString(8, product.getImage_url());
            stmt.setInt(9, product.getProduct_id());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM product WHERE product_id = ?";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, productId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean isProductReferencedInOrders(int productId) {
        String query = "SELECT 1 FROM customer_order WHERE product_id = ? LIMIT 1";
        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // returns true if at least one reference exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private ProductModel mapProduct(ResultSet rs) throws SQLException {
        CategoryModel category = new CategoryModel(
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("category_description")
        );

        return new ProductModel(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("product_description"),
                rs.getFloat("price"),
                rs.getInt("stock_quantity"),
                rs.getString("brand"),
                rs.getFloat("discount"),
                rs.getString("image_url"),
                category
        );
    }
    
    public boolean reduceQuantity(int productId, int quantity) {
        String sql = "UPDATE product SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, quantity);
            pst.setInt(2, productId);
            pst.setInt(3, quantity);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<ProductModel> searchProducts(String keyword) {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name, c.category_description " +
                     "FROM product p " +
                     "JOIN category c ON p.category_id = c.category_id " +
                     "WHERE p.product_name LIKE ? OR p.brand LIKE ? OR c.category_name LIKE ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, likeKeyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<ProductModel> getProductsByCategory(int categoryId) {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name, c.category_description " +
                     "FROM product p " +
                     "JOIN category c ON p.category_id = c.category_id " +
                     "WHERE p.category_id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Use logger in production
        }

        return products;
    }

}
