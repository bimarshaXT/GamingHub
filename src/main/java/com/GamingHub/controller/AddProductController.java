package com.GamingHub.controller;

import com.GamingHub.dao.ProductDAO;
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

@WebServlet(asyncSupported = true, urlPatterns = { "/addproduct" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AddProductController extends HttpServlet {

    private final ProductManagementService productService = new ProductManagementService();
    private final ImageUtil imageUtil = new ImageUtil();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<CategoryModel> categoryList = productDAO.getAllCategories();

        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, resp);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Step 1: Validate the form
            String errorMsg = validateForm(req);
            if (errorMsg != null) {
                // If validation fails, handle the error and return
                handleError(req, resp, errorMsg);
                return;
            }

            // Step 2: Extract product data from the request
            ProductModel product = extractProduct(req);

            // Step 3: Add product using the service
            boolean isAdded = productService.addProduct(product);

            // Step 4: Handle success or failure
            if (isAdded) {
                req.setAttribute("success", "Product added successfully!");
                // Redirect to another page after success (to prevent resubmission)
                resp.sendRedirect(req.getContextPath() + "/productmanagement"); // Redirect to product management page
                return;
            } else {
                req.setAttribute("error", "Failed to add product.");
            }

            // Step 5: Retain the category_id and categoryList in case of an error
            req.setAttribute("category_id", Integer.parseInt(req.getParameter("category_id")));
            // Fetch and set the category list for the dropdown
            ProductDAO productDAO = new ProductDAO();
            List<CategoryModel> categoryList = productDAO.getAllCategories();
            req.setAttribute("categoryList", categoryList);

            // Step 6: Forward to the add-product.jsp with error or success messages
            req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace(); // Ideally, use a logger here to log the error
            req.setAttribute("error", "An error occurred while adding the product.");
            req.getRequestDispatcher("/WEB-INF/admin/pages/addproduct.jsp").forward(req, resp);
        }
    }


    private String validateForm(HttpServletRequest req) {
        if (ValidationUtil.isNullOrEmpty(req.getParameter("product_name")))
            return "Product name is required.";
        if (!ValidationUtil.isValidProductName(req.getParameter("product_name")))
            return "Invalid product name.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("product_description")))
            return "Product description is required.";
        if (!ValidationUtil.isValidProductDescription(req.getParameter("product_description")))
            return "Invalid description.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("price")))
            return "Price is required.";
        if (!ValidationUtil.isValidPrice(req.getParameter("price")))
            return "Invalid price.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("stock_quantity")))
            return "Stock quantity is required.";
        if (!ValidationUtil.isValidStockQuantity(req.getParameter("stock_quantity")))
            return "Invalid stock quantity.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("brand")))
            return "Brand is required.";
        if (!ValidationUtil.isValidBrand(req.getParameter("brand")))
            return "Invalid brand.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("discount")))
            return "Discount is required.";
        if (!ValidationUtil.isValidDiscount(req.getParameter("discount")))
            return "Invalid discount.";

        if (ValidationUtil.isNullOrEmpty(req.getParameter("category_id")))
            return "Category must be selected.";
        if (!ValidationUtil.isValidCategoryId(req.getParameter("category_id")))
            return "Invalid category selection.";

        try {
            Part image = req.getPart("image_url");
            if (image == null || image.getSize() == 0) {
                return "Product image is required.";
            }
            if (!ValidationUtil.isValidImageExtension(image)) {
                return "Invalid image format.";
            }
        } catch (IOException | ServletException e) {
            return "Image processing failed.";
        }

        return null;
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
        uploadImage(req, imageName);

        CategoryModel category = new CategoryModel(categoryId, null, null);
        return new ProductModel(0, name, desc, price, stock, brand, discount, imageName, category);
    }

    private boolean uploadImage(HttpServletRequest req, String imageName) throws IOException, ServletException {
        Part image = req.getPart("image_url");
        return image != null && image.getSize() > 0
                ? imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "products")
                : true;
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String errorMsg)
            throws ServletException, IOException {
        req.setAttribute("addProductError", errorMsg);
        for (String param : new String[]{"product_name", "product_description", "price", "stock_quantity", "brand", "discount", "category_id"}) {
            req.setAttribute(param, req.getParameter(param));
        }
        req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, resp);
    }
}
