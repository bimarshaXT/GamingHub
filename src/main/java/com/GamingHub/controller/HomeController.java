package com.GamingHub.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = {"/Home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = (String) req.getSession().getAttribute("username");

        if (username == null) {
            // User not logged in, redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // User is logged in, forward to home page
        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }

}
