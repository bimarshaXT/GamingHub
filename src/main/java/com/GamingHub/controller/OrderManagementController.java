package com.GamingHub.controller;

import com.GamingHub.dao.OrderDAO;
import com.GamingHub.model.OrderModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = { "/ordermanagement"})
public class OrderManagementController extends HttpServlet {

    private OrderDAO orderDAO;

    public OrderManagementController() {
        super();
        this.orderDAO = new OrderDAO(); // Initialize DAO (consider using dependency injection if necessary)
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<OrderModel> orders;

        if (search != null && !search.trim().isEmpty()) {
            orders = orderDAO.searchOrders(search.trim());
            request.setAttribute("searchTerm", search);
        } else {
            orders = orderDAO.getAllOrders();
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("WEB-INF/pages/admin/ordermanagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String orderIdStr = request.getParameter("orderId");

        if (action != null && orderIdStr != null) {
            int orderId = Integer.parseInt(orderIdStr);
            boolean isSuccess = false;

            if (action.equals("dispatch")) {
                // Call DAO method to update the status of the order to "Shipped"
                isSuccess = orderDAO.updateOrderStatus(orderId, "Shipped");
            } else if (action.equals("cancel")) {
                // Call DAO method to update the status of the order to "Cancelled"
                isSuccess = orderDAO.updateOrderStatus(orderId, "Cancelled");
            }

            // Optionally, set a message to show on the JSP if needed
            if (isSuccess) {
                request.setAttribute("successMessage", "Order status updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update order status.");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid order or action.");
        }

        // After processing, redirect to the order management page (GET request)
        doGet(request, response);
    }
}
