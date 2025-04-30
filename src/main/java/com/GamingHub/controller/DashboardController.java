package com.GamingHub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller to handle admin dashboard access
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/dashboard"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Check for login and admin role
        String username = (String) request.getSession().getAttribute("username");
        String role = (String) request.getSession().getAttribute("role");

        if (username == null || role == null || !"admin".equals(role)) {
            // ❌ Not logged in or not an admin — redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Admin verified, forward to dashboard view
        request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
    }
}
