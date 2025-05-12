package com.GamingHub.controller;

import com.GamingHub.dao.DashboardDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DashboardDAO dao = new DashboardDAO();

            req.setAttribute("userCount", dao.getUserCount());
            req.setAttribute("productCount", dao.getProductCount());
            req.setAttribute("processingCount", dao.getOrderCountByStatus("processing"));
            req.setAttribute("shippedCount", dao.getOrderCountByStatus("shipped"));
            req.setAttribute("cancelledCount", dao.getOrderCountByStatus("cancelled"));
            req.setAttribute("totalRevenue", dao.getTotalRevenue());
            req.setAttribute("categorySales", dao.getCategorySalesData());
            req.setAttribute("dailySales", dao.getDailySalesData());
            req.setAttribute("avgPricePerCategory", dao.getCategoryAveragePrices());

            req.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500, "Dashboard error: " + e.getMessage());
        }
    }
}
