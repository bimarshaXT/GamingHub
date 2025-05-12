package com.GamingHub.controller;

import com.GamingHub.dao.CustomerDAO;
import com.GamingHub.dao.OrderDAO;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.model.OrderModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerModel customer = customerDAO.getCustomerByUsername(username);

        if (customer == null) {
            req.setAttribute("error", "Customer not found.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        List<OrderModel> orders = orderDAO.getOrdersByCustomerId(customer.getCustomer_id());

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/pages/order.jsp").forward(req, resp);
    }
}

