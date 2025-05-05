package com.GamingHub.service;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.ProductModel;
import com.GamingHub.model.CategoryModel;

import java.sql.*;
import java.util.*;

public class ProductService {
    // Get all products (without any limit or category filtering)
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product ORDER BY category_id";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products; // Return all products
    }

    // Get products by a specific category
    public List<ProductModel> getProductsByCategory(int categoryId) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category_id = ?";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, categoryId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // Get all categories
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categories = new ArrayList<>();
        String query = "SELECT * FROM category";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                CategoryModel category = new CategoryModel(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("category_description")
                );
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Map a ResultSet to a ProductModel object
    private ProductModel mapProduct(ResultSet rs) throws SQLException {
        int categoryId = rs.getInt("category_id");
        CategoryModel category = new CategoryModel(categoryId, null, null);

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
}
