package com.GamingHub.controller;

import com.GamingHub.dao.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller to handle deleting a customer
 */
@WebServlet("/deletecustomer")
public class DeleteCustomerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the customerId from the request parameter
        String customerIdParam = request.getParameter("customerId");

        if (customerIdParam == null || customerIdParam.isEmpty()) {
            // If no customerId is found in the request, redirect or show an error
            response.sendRedirect(request.getContextPath() + "/usermanagement");
            return;
        }

        try {
            // Parse customerId as integer
            int customerId = Integer.parseInt(customerIdParam);

            // Create an instance of CustomerDAO and call the deleteCustomerById method
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.deleteCustomerById(customerId);

            // After deletion, redirect to the user management page
            response.sendRedirect(request.getContextPath() + "/usermanagement");

        } catch (NumberFormatException e) {
            // Handle invalid customerId format
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/usermanagement?error=invalidId");
        }
    }
}
