package com.GamingHub.controller;

import com.GamingHub.model.ProductModel;
import com.GamingHub.service.ProductManagementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = {"/productmanagement"})
public class ProductManagementController extends HttpServlet {

    private ProductManagementService ProductManagementService;

    @Override
    public void init() throws ServletException {
        super.init();
        ProductManagementService = new ProductManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ProductModel> products = ProductManagementService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/admin/productmanagement.jsp").forward(request, response);
    }
}