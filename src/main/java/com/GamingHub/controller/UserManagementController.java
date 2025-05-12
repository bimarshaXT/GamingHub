package com.GamingHub.controller;

import com.GamingHub.dao.CustomerDAO;
import com.GamingHub.model.CustomerModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/usermanagement")
public class UserManagementController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");

        List<CustomerModel> customers;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            customers = customerDAO.searchCustomers(searchTerm.trim());
        } else {
            customers = customerDAO.getAllCustomers(); // If no search term, return all customers
        }

        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/admin/usermanagement.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle delete operation or other post actions
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            customerDAO.deleteCustomerById(customerId);
        }

        // Redirect to user management page after action
        response.sendRedirect(request.getContextPath() + "/usermanagement");
    }
}

