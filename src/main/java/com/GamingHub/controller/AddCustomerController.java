package com.GamingHub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;

import com.GamingHub.model.CustomerModel;
import com.GamingHub.service.RegisterService;
import com.GamingHub.util.ImageUtil;
import com.GamingHub.util.PasswordUtil;
import com.GamingHub.util.ValidationUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/addcustomer" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AddCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ImageUtil imageUtil = new ImageUtil();
    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/admin/addcustomer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validationError = validateForm(req);
        if (validationError != null) {
            handleError(req, resp, validationError);
            return;
        }

        try {
            CustomerModel customerModel = extractCustomerModel(req);
            Boolean isAdded = registerService.addCustomer(customerModel);

            if (isAdded == null) {
                handleError(req, resp, "Server maintenance in progress. Please try again later!");
            } else if (isAdded) {
                if (uploadImage(req)) {
                    handleSuccess(req, resp, "Customer added successfully.");
                } else {
                    handleError(req, resp, "Customer added, but image upload failed.");
                }
            } else {
                handleError(req, resp, "Username or Email already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "Unexpected server error. Please try again.");
        }
    }

    private String validateForm(HttpServletRequest req) {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("username");
        String dobStr = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String number = req.getParameter("number");
        String passwordRaw = req.getParameter("password");

        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required.";
        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required.";
        if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(dobStr)) return "Date of birth is required.";
        if (ValidationUtil.isNullOrEmpty(gender)) return "Gender is required.";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(number)) return "Phone number is required.";
        if (ValidationUtil.isNullOrEmpty(passwordRaw)) return "Password is required.";

        try {
            LocalDate dob = LocalDate.parse(dobStr);
            if (!ValidationUtil.isAgeAtLeast16(dob)) return "Customer must be at least 16 years old.";
        } catch (Exception e) {
            return "Invalid date format. Please use YYYY-MM-DD.";
        }

        if (!ValidationUtil.isAlphanumericStartingWithLetter(username)) return "Username must start with a letter and be alphanumeric.";
        if (!ValidationUtil.isValidGender(gender)) return "Invalid gender.";
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email.";
        if (!ValidationUtil.isValidPhoneNumber(number)) return "Phone number must start with 98 and be 10 digits.";
        if (!ValidationUtil.isValidPassword(passwordRaw)) return "Password must have 8+ chars, 1 uppercase, 1 number, 1 symbol.";

        try {
            Part image = req.getPart("image");
            if (image != null && image.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(image)) return "Image must be jpg, jpeg, png, gif or webp.";
            }
        } catch (Exception e) {
            return "Error validating image file.";
        }

        return null;
    }

    private CustomerModel extractCustomerModel(HttpServletRequest req) throws Exception {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("username");
        LocalDate dob = LocalDate.parse(req.getParameter("dob"));
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String number = req.getParameter("number");
        String password = PasswordUtil.encrypt(username, req.getParameter("password"));

        Part image = req.getPart("image");
        String imageUrl = image != null && image.getSize() > 0 ? imageUtil.getImageNameFromPart(image) : null;

        return new CustomerModel(0, firstName, lastName, username, dob, gender, email, number, password, imageUrl);
    }

    private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
        Part image = req.getPart("image");
        return image != null && image.getSize() > 0
                ? imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "customer")
                : true;
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
    	req.setAttribute("addCustomerSuccess", message);
        req.getRequestDispatcher("/WEB-INF/pages/admin/addcustomer.jsp").forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
    	req.setAttribute("addCustomerError", "some message");
        req.setAttribute("first_name", req.getParameter("first_name"));
        req.setAttribute("last_name", req.getParameter("last_name"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("dob", req.getParameter("dob"));
        req.setAttribute("gender", req.getParameter("gender"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("number", req.getParameter("number"));
        req.getRequestDispatcher("/WEB-INF/pages/admin/addcustomer.jsp").forward(req, resp);
    }
}
