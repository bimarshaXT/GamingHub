package com.GamingHub.controller;

import com.GamingHub.model.CategoryModel;
import com.GamingHub.model.ProductModel;
import com.GamingHub.service.ProductManagementService;
import com.GamingHub.util.ImageUtil;
import com.GamingHub.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/updateproduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UpdateProductController extends HttpServlet {

    private final ProductManagementService productService = new ProductManagementService();
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        ProductModel product = productService.getProductById(productId);
        List<CategoryModel> categoryList = productService.getAllCategories();

        req.setAttribute("product", product);
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/WEB-INF/pages/admin/updateproduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Step 1: Validate form
            String errorMsg = validateForm(req);
            if (errorMsg != null) {
                handleError(req, resp, errorMsg);
                return;
            }

            // Step 2: Extract data
            ProductModel updatedProduct = extractProduct(req);

            // Step 3: Update in DB
            boolean isUpdated = productService.updateProduct(updatedProduct);
            if (isUpdated) {
                resp.sendRedirect(req.getContextPath() + "/productmanagement");
            } else {
                handleError(req, resp, "Failed to update the product.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "An unexpected error occurred.");
        }
    }

    private String validateForm(HttpServletRequest req) {
        if (ValidationUtil.isNullOrEmpty(req.getParameter("product_id")))
            return "Invalid product ID.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("product_name")))
            return "Product name is required.";
        if (!ValidationUtil.isValidProductName(req.getParameter("product_name")))
            return "Invalid product name.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("brand")))
            return "Brand is required.";
        if (!ValidationUtil.isValidBrand(req.getParameter("brand")))
            return "Invalid brand.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("price")))
            return "Price is required.";
        if (!ValidationUtil.isValidPrice(req.getParameter("price")))
            return "Invalid price.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("discount")))
            return "Discount is required.";
        if (!ValidationUtil.isValidDiscount(req.getParameter("discount")))
            return "Invalid discount.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("stock_quantity")))
            return "Stock quantity is required.";
        if (!ValidationUtil.isValidStockQuantity(req.getParameter("stock_quantity")))
            return "Invalid stock quantity.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("description")))
            return "Description is required.";
        if (!ValidationUtil.isValidProductDescription(req.getParameter("description")))
            return "Invalid product description.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("category_id")))
            return "Category must be selected.";
        if (!ValidationUtil.isValidCategoryId(req.getParameter("category_id")))
            return "Invalid category selection.";

        try {
            Part image = req.getPart("image");
            if (image != null && image.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(image)) {
                    return "Invalid image format.";
                }
            }
        } catch (Exception e) {
            return "Image validation failed.";
        }

        return null;
    }

    private ProductModel extractProduct(HttpServletRequest req) throws Exception {
        int productId = Integer.parseInt(req.getParameter("product_id"));
        String name = req.getParameter("product_name");
        String brand = req.getParameter("brand");
        String description = req.getParameter("description");
        float price = Float.parseFloat(req.getParameter("price"));
        float discount = Float.parseFloat(req.getParameter("discount"));
        int stock = Integer.parseInt(req.getParameter("stock_quantity"));
        int categoryId = Integer.parseInt(req.getParameter("category_id"));

        Part imagePart = req.getPart("image");
        String imageUrl;

        if (imagePart != null && imagePart.getSize() > 0) {
            String imageName = imageUtil.getImageNameFromPart(imagePart);
            imageUtil.uploadImage(imagePart, req.getServletContext().getRealPath("/"), "products");
            imageUrl = imageName;
        } else {
            // If no new image uploaded, retain the old image URL
            ProductModel existing = productService.getProductById(productId);
            imageUrl = existing.getImage_url();
        }

        CategoryModel category = new CategoryModel(categoryId, null, null);
        return new ProductModel(productId, name, description, price, stock, brand, discount, imageUrl, category);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String errorMsg)
            throws ServletException, IOException {
        req.setAttribute("error", errorMsg);

        // Repopulate category list
        List<CategoryModel> categoryList = productService.getAllCategories();
        req.setAttribute("categoryList", categoryList);

        int productId = parseIntSafe(req.getParameter("product_id"));
        String name = req.getParameter("product_name");
        String brand = req.getParameter("brand");
        String description = req.getParameter("description");
        float price = parseFloatSafe(req.getParameter("price"));
        float discount = parseFloatSafe(req.getParameter("discount"));
        int stock = parseIntSafe(req.getParameter("stock_quantity"));
        int categoryId = parseIntSafe(req.getParameter("category_id"));

        CategoryModel category = new CategoryModel(categoryId, null, null);
        ProductModel preserved = new ProductModel(productId, name, description, price, stock, brand, discount, null, category);


        req.setAttribute("product", preserved);
        req.getRequestDispatcher("/WEB-INF/pages/admin/updateproduct.jsp").forward(req, resp);
    }

    private int parseIntSafe(String param) {
        try {
            return param != null ? Integer.parseInt(param) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private float parseFloatSafe(String param) {
        try {
            return param != null ? Float.parseFloat(param) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
