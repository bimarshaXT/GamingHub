package com.GamingHub.service;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CategoryModel;
import com.GamingHub.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManagementService {

    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection con = DbConfig.getDbConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    private ProductModel mapProduct(ResultSet rs) throws SQLException {
        CategoryModel category = new CategoryModel(rs.getInt("category_id"), null, null);

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
