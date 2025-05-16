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
import java.util.HashMap;
import java.util.Map;

import com.GamingHub.model.CustomerModel;
import com.GamingHub.service.RegisterService;
import com.GamingHub.util.ImageUtil;
import com.GamingHub.util.PasswordUtil;
import com.GamingHub.util.ValidationUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/registration" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ImageUtil imageUtil = new ImageUtil();
    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = validateRegistrationForm(req);
        if (!errors.isEmpty()) {
            handleFieldErrors(req, resp, errors);
            return;
        }

        try {
            CustomerModel customerModel = extractCustomerModel(req);
            Boolean isAdded = registerService.addCustomer(customerModel);

            if (isAdded == null) {
                handleError(req, resp, "Our server is under maintenance. Please try again later!");
            } else if (isAdded) {
                if (uploadImage(req)) {
                    handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
                } else {
                    handleError(req, resp, "Could not upload the image. Please try again later!");
                }
            } else {
                handleError(req, resp, "Username or Email already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "An unexpected error occurred. Please try again later!");
        }
    }

    private Map<String, String> validateRegistrationForm(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();

        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("username");
        String dobStr = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String number = req.getParameter("number");
        String passwordRaw = req.getParameter("password");

        if (ValidationUtil.isNullOrEmpty(firstName))
            errors.put("first_name_error", "First name is required.");
        if (ValidationUtil.isNullOrEmpty(lastName))
            errors.put("last_name_error", "Last name is required.");
        if (ValidationUtil.isNullOrEmpty(username))
            errors.put("username_error", "Username is required.");
        if (ValidationUtil.isNullOrEmpty(dobStr))
            errors.put("dob_error", "Date of birth is required.");
        if (ValidationUtil.isNullOrEmpty(gender))
            errors.put("gender_error", "Gender is required.");
        if (ValidationUtil.isNullOrEmpty(email))
            errors.put("email_error", "Email is required.");
        if (ValidationUtil.isNullOrEmpty(number))
            errors.put("number_error", "Phone number is required.");
        if (ValidationUtil.isNullOrEmpty(passwordRaw))
            errors.put("password_error", "Password is required.");

        if (!ValidationUtil.isNullOrEmpty(dobStr)) {
            try {
                LocalDate dob = LocalDate.parse(dobStr);
                if (!ValidationUtil.isAgeAtLeast16(dob))
                    errors.put("dob_error", "You must be at least 16 years old to register.");
            } catch (Exception e) {
                errors.put("dob_error", "Invalid date format. Use YYYY-MM-DD.");
            }
        };
        
        if (!ValidationUtil.isValidName(lastName)) {
            errors.put("last_name_error", "Last name must contain only letters and no spaces.");
        }
        if (!ValidationUtil.isValidName(firstName)) {
            errors.put("first_name_error", "First name must contain only letters and no spaces.");
        }
        if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
            errors.put("username_error", "Username must start with a letter and contain only letters and numbers.");
        if (!ValidationUtil.isValidGender(gender))
            errors.put("gender_error", "Gender must be 'male' or 'female'.");
        if (!ValidationUtil.isValidEmail(email))
            errors.put("email_error", "Invalid email format.");
        if (!ValidationUtil.isValidPhoneNumber(number))
            errors.put("number_error", "Phone number must be 10 digits and start with 98.");
        if (!ValidationUtil.isValidPassword(passwordRaw))
            errors.put("password_error", "Password must be at least 8 characters, 1 uppercase, 1 number, and 1 symbol.");

        try {
            Part image = req.getPart("image");
            if (image != null && image.getSize() > 0 && !ValidationUtil.isValidImageExtension(image)) {
                errors.put("image_error", "Invalid image format. Only jpg, jpeg, png, webp, gif allowed.");
            }
        } catch (IOException | ServletException e) {
            errors.put("image_error", "Error handling the uploaded image.");
        }

        return errors;
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

    private void handleFieldErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errors)
            throws ServletException, IOException {
        for (Map.Entry<String, String> entry : errors.entrySet()) {
            req.setAttribute(entry.getKey(), entry.getValue());
        }

        req.setAttribute("first_name", req.getParameter("first_name"));
        req.setAttribute("last_name", req.getParameter("last_name"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("dob", req.getParameter("dob"));
        req.setAttribute("gender", req.getParameter("gender"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("number", req.getParameter("number"));
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        handleFieldErrors(req, resp, new HashMap<>());
    }
}
