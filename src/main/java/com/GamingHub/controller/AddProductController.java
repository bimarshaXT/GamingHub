package com.GamingHub.controller;

import com.GamingHub.dao.ProductDAO;
import com.GamingHub.model.CategoryModel;
import com.GamingHub.model.ProductModel;
import com.GamingHub.util.ImageUtil;
import com.GamingHub.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(asyncSupported = true, urlPatterns = { "/addproduct" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AddProductController extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryModel> categoryList = productDAO.getAllCategories();
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, String> fieldErrors = validateForm(req);
            if (!fieldErrors.isEmpty()) {
                req.setAttribute("fieldErrors", fieldErrors);
                handleError(req, resp, null);
                return;
            }

            ProductModel product = extractProduct(req);
            boolean isAdded = productDAO.addProduct(product);

            if (isAdded) {
                resp.sendRedirect(req.getContextPath() + "/productmanagement");
            } else {
                handleError(req, resp, "Failed to add product.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "An error occurred while adding the product.");
        }
    }

    private Map<String, String> validateForm(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();

        if (ValidationUtil.isNullOrEmpty(req.getParameter("product_name")))
            errors.put("product_name", "Product name is required.");
        else if (!ValidationUtil.isValidProductName(req.getParameter("product_name")))
            errors.put("product_name", "Invalid product name.");

        if (ValidationUtil.isNullOrEmpty(req.getParameter("product_description")))
            errors.put("product_description", "Product description is required.");
        else if (!ValidationUtil.isValidProductDescription(req.getParameter("product_description")))
            errors.put("product_description", "Invalid description.");

        if (ValidationUtil.isNullOrEmpty(req.getParameter("price")))
            errors.put("price", "Price is required.");
        else if (!ValidationUtil.isValidPrice(req.getParameter("price")))
            errors.put("price", "Invalid price.");

        if (ValidationUtil.isNullOrEmpty(req.getParameter("stock_quantity")))
            errors.put("stock_quantity", "Stock quantity is required.");
        else if (!ValidationUtil.isValidStockQuantity(req.getParameter("stock_quantity")))
            errors.put("stock_quantity", "Invalid stock quantity.");

        if (ValidationUtil.isNullOrEmpty(req.getParameter("brand")))
            errors.put("brand", "Brand is required.");
        else if (!ValidationUtil.isValidBrand(req.getParameter("brand")))
            errors.put("brand", "Invalid brand.");

        if (ValidationUtil.isNullOrEmpty(req.getParameter("discount")))
            errors.put("discount", "Discount is required.");
        else if (!ValidationUtil.isValidDiscount(req.getParameter("discount")))
            errors.put("discount", "Invalid discount.");

        if (ValidationUtil.isNullOrEmpty(req.getParameter("category_id")))
            errors.put("category_id", "Category must be selected.");
        else if (!ValidationUtil.isValidCategoryId(req.getParameter("category_id")))
            errors.put("category_id", "Invalid category selection.");

        try {
            Part image = req.getPart("image_url");
            if (image == null || image.getSize() == 0)
                errors.put("image_url", "Product image is required.");
            else if (!ValidationUtil.isValidImageExtension(image))
                errors.put("image_url", "Invalid image format.");
        } catch (Exception e) {
            errors.put("image_url", "Image processing failed.");
        }

        return errors;
    }

    private ProductModel extractProduct(HttpServletRequest req) throws Exception {
        String name = req.getParameter("product_name");
        String desc = req.getParameter("product_description");
        float price = Float.parseFloat(req.getParameter("price"));
        int stock = Integer.parseInt(req.getParameter("stock_quantity"));
        String brand = req.getParameter("brand");
        float discount = Float.parseFloat(req.getParameter("discount"));
        int categoryId = Integer.parseInt(req.getParameter("category_id"));

        Part image = req.getPart("image_url");
        String imageName = imageUtil.getImageNameFromPart(image);
        imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "products");

        CategoryModel category = new CategoryModel(categoryId, null, null);
        return new ProductModel(0, name, desc, price, stock, brand, discount, imageName, category);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String errorMsg)
            throws ServletException, IOException {
        if (errorMsg != null) {
            req.setAttribute("addProductError", errorMsg);
        }

        for (String param : new String[]{
                "product_name", "product_description", "price", "stock_quantity",
                "brand", "discount", "category_id"
        }) {
            req.setAttribute(param, req.getParameter(param));
        }

        List<CategoryModel> categoryList = productDAO.getAllCategories();
        req.setAttribute("categoryList", categoryList);

        req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, resp);
    }
}
