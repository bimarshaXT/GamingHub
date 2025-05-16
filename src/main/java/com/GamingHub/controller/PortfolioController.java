package com.GamingHub.controller;

import com.GamingHub.dao.CustomerDAO;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.util.ImageUtil;
import com.GamingHub.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/portfolio")
@MultipartConfig
public class PortfolioController extends HttpServlet {

    private CustomerDAO customerDAO;
    private ImageUtil imageUtil;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
        imageUtil = new ImageUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");

        try {
            CustomerModel customer = customerDAO.getCustomerProfile(username);
            if (customer == null) {
                handleError(req, resp, "Profile not found", username);
                return;
            }

            req.setAttribute("customer", customer);
            req.setAttribute("editMode", "true".equals(req.getParameter("editMode")));
            req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);

        } catch (SQLException e) {
            handleError(req, resp, "Database error: " + e.getMessage(), username);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        String currentPassword = req.getParameter("password");

        try {
            String validationError = validateProfileFields(req);
            if (validationError != null) {
                handleError(req, resp, validationError, username);
                return;
            }

            CustomerModel customer = extractCustomerModel(req, username);

            if (!customerDAO.updateProfile(customer, currentPassword)) {
                handleError(req, resp, "Profile update failed. Incorrect password?", username);
                return;
            }

            Part imagePart = req.getPart("profileImage");
            if (imagePart != null && imagePart.getSize() > 0) {
                String imageName = imageUtil.getImageNameFromPart(imagePart);
                String realPath = req.getServletContext().getRealPath("/");

                boolean uploaded = imageUtil.uploadImage(imagePart, realPath, "customer");

                if (uploaded) {
                    boolean imageUpdated = customerDAO.updateProfileImage(username, imageName);
                    if (!imageUpdated) {
                        handleSuccessWithWarning(req, resp, "Profile updated but image URL not saved", username);
                        return;
                    }
                } else {
                    handleSuccessWithWarning(req, resp, "Profile updated but image upload failed", username);
                    return;
                }
            }

            String newPassword = req.getParameter("newPassword");
            if (newPassword != null && !newPassword.isEmpty()) {
                if (!customerDAO.updatePassword(username, currentPassword, newPassword)) {
                    handleSuccessWithWarning(req, resp, "Profile updated but password change failed", username);
                    return;
                }
            }

            handleSuccess(req, resp, "Profile updated successfully", username);

        } catch (SQLException e) {
            handleError(req, resp, "Database error: " + e.getMessage(), username);
        } catch (Exception e) {
            handleError(req, resp, "Error: " + e.getMessage(), username);
        }
    }

    private String validateProfileFields(HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String dobStr = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String number = req.getParameter("number");
        String password = req.getParameter("password");

        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required";
        if (!ValidationUtil.isValidName(firstName)) return "First name must contain only letters and no spaces.";

        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required";
        if (!ValidationUtil.isValidName(lastName)) return "Last name must contain only letters and no spaces.";

        if (ValidationUtil.isNullOrEmpty(dobStr)) return "Date of birth is required";

        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required";
        if (email.contains(" ")) return "Email must not contain spaces.";
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email format.";

        if (ValidationUtil.isNullOrEmpty(number)) return "Phone number is required";
        if (!ValidationUtil.isValidPhoneNumber(number)) return "Phone number must be 10 digits and start with 98.";

        if (ValidationUtil.isNullOrEmpty(password)) return "Current password is required";
        if (!ValidationUtil.isValidPassword(password)) return "Password must be at least 8 characters with 1 uppercase, 1 number, and 1 symbol.";

        if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
            return "Username must start with a letter and contain only letters and numbers.";
        if (username.contains(" ")) return "Username must not contain spaces.";

        if (!ValidationUtil.isValidGender(gender))
            return "Gender must be 'male', 'female', or 'other'.";

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            return "Invalid date format. Please use YYYY-MM-DD.";
        }

        if (!ValidationUtil.isAgeAtLeast16(dob))
            return "You must be at least 16 years old to register.";

        try {
            Part image = req.getPart("profileImage");
            if (image != null && image.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(image))
                    return "Invalid image format. Only jpg, jpeg, png, webp, and gif are allowed.";
            }
        } catch (IOException | ServletException e) {
            return "Error handling image file. Please ensure the file is valid.";
        }

        return null;
    }

    private CustomerModel extractCustomerModel(HttpServletRequest req, String username) {
        return new CustomerModel(
                0,
                req.getParameter("first_name"),
                req.getParameter("last_name"),
                username,
                LocalDate.parse(req.getParameter("dob")),
                req.getParameter("gender"),
                req.getParameter("email"),
                req.getParameter("number"),
                null,
                null
        );
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String username)
            throws ServletException, IOException {
        try {
            CustomerModel updatedProfile = customerDAO.getCustomerProfile(username);
            req.setAttribute("customer", updatedProfile);
            req.setAttribute("success", message);
            req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
        } catch (SQLException e) {
            handleError(req, resp, "Error retrieving updated profile", username);
        }
    }

    private void handleSuccessWithWarning(HttpServletRequest req, HttpServletResponse resp,
                                          String message, String username) throws ServletException, IOException {
        try {
            CustomerModel updatedProfile = customerDAO.getCustomerProfile(username);
            req.setAttribute("customer", updatedProfile);
            req.setAttribute("warning", message);
            req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
        } catch (SQLException e) {
            handleError(req, resp, "Error retrieving updated profile", username);
        }
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String error, String username)
            throws ServletException, IOException {
        try {
            if (username != null) {
                CustomerModel customer = customerDAO.getCustomerProfile(username);
                req.setAttribute("customer", customer);
            }
            req.setAttribute("error", error);
            req.setAttribute("editMode", true);
            req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "System error occurred");
            req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
        }
    }
}
