<%@ page import="com.GamingHub.model.ProductModel, com.GamingHub.model.CategoryModel, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
ProductModel product = (ProductModel) request.getAttribute("product");
List<CategoryModel> categories = (List<CategoryModel>) request.getAttribute("categoryList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/updateproduct.css" />
</head>
<body>
    <h1>Update Product</h1>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/updateproduct" enctype="multipart/form-data">
        <input type="hidden" name="product_id" value="${product.product_id}" />
        
        <!-- Left side - Image display and upload -->
        <div class="image-section">
            <div class="current-image">
                <p>Current Image</p>
                <c:if test="${not empty product.image_url}">
                    <img src="${pageContext.request.contextPath}/resources/products/${product.image_url}" alt="Product Image" />
                </c:if>
                <c:if test="${empty product.image_url}">
                    <img src="${pageContext.request.contextPath}/resources/products/placeholder.png" alt="No Image Available" />
                </c:if>
            </div>
            
            <div class="file-upload">
                <label>Update Image</label>
                <input type="file" name="image" accept="image/*" />
            </div>
        </div>
        
        <!-- Right side - Form fields -->
        <div class="form-fields">
            <label>Name:</label>
            <input type="text" name="product_name" value="${product.product_name}" required />

            <label>Brand:</label>
            <input type="text" name="brand" value="${product.brand}" required />

            <label>Category:</label>
            <select name="category_id" required>
                <c:forEach var="cat" items="${categoryList}">
                    <option value="${cat.category_id}"
                        <c:if test="${cat.category_id == product.category.category_id}">selected</c:if>>
                        ${cat.category_name}
                    </option>
                </c:forEach>
            </select>

            <label>Price:</label>
            <input type="number" name="price" min="0" step="0.01" value="${product.price}" required />

            <label>Discount (%):</label>
            <input type="number" name="discount" min="0" max="100" value="${product.discount}" required />

            <label>Stock Quantity:</label>
            <input type="number" name="stock_quantity" min="0" value="${product.stock_quantity}" required />

            <label>Description:</label>
            <textarea name="description" required>${product.product_description}</textarea>
        </div>
        
        <div class="button-container">
            <button type="submit">Update Product</button>
            <a href="${pageContext.request.contextPath}/productmanagement" class="cancel-button">Cancel</a>
        </div>
    </form>
</body>
</html>