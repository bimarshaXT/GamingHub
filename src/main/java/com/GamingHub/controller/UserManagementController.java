package com.GamingHub.controller;

import com.GamingHub.model.CustomerModel;
import com.GamingHub.service.UserManagementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/usermanagement")
public class UserManagementController extends HttpServlet {

    private UserManagementService userService;

    @Override
    public void init() {
        userService = new UserManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CustomerModel> customers = userService.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/WEB-INF/pages/admin/usermanagement.jsp").forward(request, response);
    }
}
