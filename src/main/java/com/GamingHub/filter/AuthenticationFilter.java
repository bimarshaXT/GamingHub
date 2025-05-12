package com.GamingHub.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.GamingHub.util.SessionUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/Home";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/dashboard";
	private static final String ABOUTUS = "/aboutus";
	private static final String PORTFOLIO = "/portfolio";
	private static final String CONTACTUS = "/contactus";
	private static final String PRODUCT = "/product";
	private static final String USERMANAGEMENT = "/usermanagement";
	private static final String PRODUCTMANAGEMENT = "/productmanagement";
	private static final String ORDERMANAGEMENT = "/ordermanagement";
	private static final String ADDCUSTOMER = "/addcustomer";
	private static final String DELETECUSTOMER = "/deletecustomer";
	private static final String ADDPRODUCT = "/addproduct";
	private static final String UPDATEPRODUCT = "/updateproduct";
	private static final String VIEWPRODUCT = "/product/details";
	private static final String ORDER = "/order";
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean loggedIn = SessionUtil.isLoggedIn(req);
        String role = SessionUtil.getUserRole(req);

        // Allow access to static resources
        if (path.matches(".*(\\.png|\\.jpg|\\.jpeg|\\.gif|\\.webp|\\.avif|)$")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow public pages
        if (path.equals(LOGIN) || path.equals(REGISTER) || path.equals(ROOT)) {
            chain.doFilter(request, response);
            return;
        }

        // Admin access to /dashboard only if logged in
        
           if (loggedIn && "admin".equals(role)) {
        	  if (path.startsWith(DASHBOARD) || path.startsWith(USERMANAGEMENT) || path.startsWith(PRODUCTMANAGEMENT) || path.startsWith(ORDERMANAGEMENT) || path.startsWith(ADDCUSTOMER)
        			  || path.startsWith(DELETECUSTOMER) || path.startsWith(ADDPRODUCT) || path.startsWith(UPDATEPRODUCT)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
            return;
        }

        // Customer access to /home only if logged in
           if (loggedIn && "customer".equals(role)) {
              if (path.startsWith(HOME) || path.startsWith(PRODUCT) || path.startsWith(PORTFOLIO) || path.startsWith(ABOUTUS)|| path.startsWith(CONTACTUS) 
            		  || path.startsWith(VIEWPRODUCT) || path.startsWith(ORDER)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
            return;
        }

        // Redirect everything else to login
        res.sendRedirect(req.getContextPath() + LOGIN);
    }

}