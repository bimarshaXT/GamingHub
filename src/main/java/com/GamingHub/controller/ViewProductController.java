package com.GamingHub.controller;

import com.GamingHub.dao.CustomerDAO;
import com.GamingHub.dao.OrderDAO;
import com.GamingHub.dao.ProductDAO;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/product/details")
public class ViewProductController extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productIdParam = request.getParameter("id");

        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                ProductModel product = productDAO.getProductById(productId);

                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/pages/viewproduct.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                }

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID format.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        CustomerModel customer = customerDAO.getCustomerByUsername(username);

        if (customer == null) {
            req.setAttribute("error", "Customer not found.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            ProductModel product = productDAO.getProductById(productId);

            if (product == null) {
                req.setAttribute("error", "Product not found.");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }

            if (quantity <= 0 || quantity > product.getStock_quantity()) {
                req.setAttribute("error", "Invalid quantity.");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }

            // Calculate total price with discount
            float price = product.getPrice();
            float discount = product.getDiscount();
            float discountedPrice = price - (price * discount / 100);
            float totalPrice = discountedPrice * quantity;

            boolean success = orderDAO.placeOrder(customer.getCustomer_id(), productId, quantity, totalPrice);

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/order");
            } else {
                req.setAttribute("error", "Order failed. Maybe out of stock?");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid input.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}
