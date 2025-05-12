package com.GamingHub.controller;

import com.GamingHub.model.ProductModel;
import com.GamingHub.model.CategoryModel;
import com.GamingHub.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = { "/product"})
public class ProductController extends HttpServlet {

    private ProductService productsService;

    @Override
    public void init() throws ServletException {
        super.init();
        productsService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoryParam = request.getParameter("category");
        String searchParam = request.getParameter("search");
        List<ProductModel> products;

        try {
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                products = productsService.searchProducts(searchParam.trim());
            } else if (categoryParam != null && !categoryParam.isEmpty()) {
                int categoryId = Integer.parseInt(categoryParam);
                products = productsService.getProductsByCategory(categoryId);
            } else {
                products = productsService.getAllProducts();
            }
        } catch (NumberFormatException e) {
            products = productsService.getAllProducts();
            categoryParam = null;
        }

        List<CategoryModel> categories = productsService.getAllCategories();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("selectedCategory", categoryParam);

        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

}
