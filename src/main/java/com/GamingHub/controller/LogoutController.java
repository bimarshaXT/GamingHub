package com.GamingHub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.GamingHub.util.CookieUtil;
import com.GamingHub.util.SessionUtil;

/**
 * Bimarsha Raut
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Common logout logic for both GET and POST.
     */
    private void performLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalidate session
        SessionUtil.invalidateSession(request);

        // Delete all cookies set by the application
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Only delete app-specific cookies (e.g., role, username, etc.)
                if ("role".equals(cookie.getName()) || "username".equals(cookie.getName())) {
                    CookieUtil.deleteCookie(response, cookie.getName());
                }
            }
        }

        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performLogout(request, response);
    }
}
