package com.GamingHub.controller;

import com.GamingHub.dao.ProductDAO;
import com.GamingHub.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = {"/productmanagement"})
public class ProductManagementController extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchTerm = request.getParameter("searchTerm");
        List<ProductModel> products;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            products = productDAO.searchProducts(searchTerm.trim());
        } else {
            products = productDAO.getAllProducts();
        }

        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/admin/productmanagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productIdParam = request.getParameter("productId");

        if (productIdParam != null && !productIdParam.trim().isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdParam);

                if (productDAO.isProductReferencedInOrders(productId)) {
                    request.setAttribute("deleteError", "Cannot delete product. It is referenced in customer orders.");
                } else {
                    boolean deleted = productDAO.deleteProduct(productId);
                    if (!deleted) {
                        request.setAttribute("deleteError", "Failed to delete product.");
                    }
                }

            } catch (NumberFormatException e) {
                request.setAttribute("deleteError", "Invalid product ID.");
            }
        }

        // Reload the product list
        List<ProductModel> products = productDAO.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/admin/productmanagement.jsp").forward(request, response);
    }
}
