package com.GamingHub.service;

import com.GamingHub.dao.ProductDAO;
import com.GamingHub.model.CategoryModel;
import com.GamingHub.model.ProductModel;

import java.util.List;

public class ProductManagementService {
    private final ProductDAO productDAO = new ProductDAO();

    public List<ProductModel> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public ProductModel getProductById(int id) {
        return productDAO.getProductById(id);
    }

    public boolean addProduct(ProductModel product) {
        return productDAO.addProduct(product);
    }

    public boolean updateProduct(ProductModel product) {
        return productDAO.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }
    
    public List<CategoryModel> getAllCategories() {
        return productDAO.getAllCategories();
    }
    
    public boolean reduceProductQuantity(int productId, int quantity) {
        return productDAO.reduceQuantity(productId, quantity);
    }
    
    public List<ProductModel> searchProducts(String keyword) {
        return new ProductDAO().searchProducts(keyword);
    }

}
