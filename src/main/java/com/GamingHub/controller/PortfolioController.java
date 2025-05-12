package com.GamingHub.controller;

import com.GamingHub.model.CustomerModel;
import com.GamingHub.service.PortfolioService;
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

    private PortfolioService portfolioService;
    private ImageUtil imageUtil;

    @Override
    public void init() {
        portfolioService = new PortfolioService();
        imageUtil = new ImageUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            String username = (String) session.getAttribute("username");
            CustomerModel customer = portfolioService.getCustomerProfile(username);
            
            if (customer == null) {
                handleError(req, resp, "Profile not found", username);
                return;
            }

            req.setAttribute("customer", customer);
            req.setAttribute("editMode", "true".equals(req.getParameter("editMode")));
            req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);

        } catch (SQLException e) {
            handleError(req, resp, "Database error", null);
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
            // 1. Validate required fields
            String validationError = validateProfileFields(req);
            if (validationError != null) {
                handleError(req, resp, validationError, username);
                return;
            }

            // 2. Update profile fields (without image)
            CustomerModel customer = extractCustomerModel(req, username);

            if (!portfolioService.updateProfile(customer, currentPassword)) {
                handleError(req, resp, "Profile update failed (incorrect password?)", username);
                return;
            }

            // 3. Handle image upload separately if provided
            Part imagePart = req.getPart("profileImage");
            String newImageName = null;

            if (imagePart != null && imagePart.getSize() > 0) {
                // Get image name and file path
                newImageName = imageUtil.getImageNameFromPart(imagePart);
                String realPath = req.getServletContext().getRealPath("/");

                // Debug output to ensure image is uploaded
                System.out.println("Uploading image...");
                System.out.println("Image name extracted: " + newImageName);

                boolean imageUploadSuccessful = imageUtil.uploadImage(imagePart, realPath, "customer");

                if (imageUploadSuccessful) {
                    // Debug output to verify the image was uploaded
                    System.out.println("Image uploaded successfully. Image name: " + newImageName);

                    // Update image URL in the database (save only the image name)
                    String imagePath = newImageName; // This should only be the image name
                    System.out.println("Image URL being saved in DB: " + imagePath);

                    // Call the updateProfileImage() method and check the result
                    boolean imageUpdated = portfolioService.updateProfileImage(username, imagePath);
                    System.out.println("Image DB update result: " + imageUpdated);
                    
                    if (!imageUpdated) {
                        handleSuccessWithWarning(req, resp, 
                            "Profile updated but image URL not saved", username);
                        return;
                    }
                } else {
                    handleSuccessWithWarning(req, resp, 
                        "Profile updated but image upload failed", username);
                    return;
                }
            }

            // 4. Handle password change if provided
            String newPassword = req.getParameter("newPassword");
            if (newPassword != null && !newPassword.isEmpty()) {
                if (!portfolioService.updatePassword(username, currentPassword, newPassword)) {
                    handleSuccessWithWarning(req, resp, 
                        "Profile updated but password change failed", username);
                    return;
                }
            }

            // 5. Success case
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

        // 1. Required field checks
        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required";
        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required";
        if (ValidationUtil.isNullOrEmpty(dobStr)) return "Date of birth is required";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required";
        if (ValidationUtil.isNullOrEmpty(number)) return "Phone number is required";
        if (ValidationUtil.isNullOrEmpty(password)) return "Current password is required";

        // 2. Parse and validate date
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            return "Invalid date format. Please use YYYY-MM-DD.";
        }

        // 3. Format and logic validations
        if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
            return "Username must start with a letter and contain only letters and numbers.";
        if (!ValidationUtil.isValidGender(gender))
            return "Gender must be 'male' or 'female'.";
        if (!ValidationUtil.isValidEmail(email))
            return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(number))
            return "Phone number must be 10 digits and start with 98.";
        if (!ValidationUtil.isValidPassword(password))
            return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
        if (!ValidationUtil.isAgeAtLeast16(dob))
            return "You must be at least 16 years old to register.";

        // 4. Image extension check (if image provided)
        try {
            Part image = req.getPart("profileImage");
            if (image != null && image.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(image))
                    return "Invalid image format. Only jpg, jpeg, png, webp and gif are allowed.";
            }
        } catch (IOException | ServletException e) {
            return "Error handling image file. Please ensure the file is valid.";
        }

        return null; // All validations passed
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
            CustomerModel updatedProfile = portfolioService.getCustomerProfile(username);
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
            CustomerModel updatedProfile = portfolioService.getCustomerProfile(username);
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
        // Always set the customer profile in the request for the edit form
        if (username != null) {
            CustomerModel customer = portfolioService.getCustomerProfile(username);
            req.setAttribute("customer", customer);
        }
        
        // Set the error message
        req.setAttribute("error", error);
        
        // Ensure the form remains in edit mode even after error
        req.setAttribute("editMode", true);  // This ensures edit mode stays true
        
        // Forward the request to the JSP
        req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
    } catch (SQLException e) {
        // In case of SQL error, we still need to forward the error message
        req.setAttribute("error", "System error occurred");
        req.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(req, resp);
    }
}

}
