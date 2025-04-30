package com.GamingHub.controller;

import java.io.IOException;

import com.GamingHub.model.CustomerModel;
import com.GamingHub.service.PortfolioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet(asyncSupported = true, urlPatterns = { "/portfolio"})
public class PortfolioController extends HttpServlet {

    private PortfolioService portfolioService;

    @Override
    public void init() {
        portfolioService = new PortfolioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            CustomerModel customer = portfolioService.getCustomerProfile(username);
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
