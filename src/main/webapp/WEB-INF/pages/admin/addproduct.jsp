<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Product - GamingHub</title>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/addproducts.css?v=1.8">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
    <div class="container">
        <h1>Add New Product</h1>
        
        <c:if test="${not empty addProductError}">
            <p class="error-message">${addProductError}</p>
        </c:if>

        <c:if test="${not empty addProductSuccess}">
            <p class="success-message">${addProductSuccess}</p>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/addproduct" method="post" enctype="multipart/form-data">
            <!-- Product Name -->
            <div class="form-group">
                <label for="product_name">Product Name</label>
                <input type="text" id="product_name" name="product_name" value="${product_name}" required>
            </div>
            
            <!-- Product Description -->
            <div class="form-group">
                <label for="product_description">Product Description</label>
                <textarea id="product_description" name="product_description" required>${product_description}</textarea>
            </div>
            
            <!-- Price -->
            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" id="price" name="price" value="${price}" required>
            </div>
            
            <!-- Stock Quantity -->
            <div class="form-group">
                <label for="stock_quantity">Stock Quantity</label>
                <input type="number" id="stock_quantity" name="stock_quantity" value="${stock_quantity}" required>
            </div>
            
            <!-- Brand -->
            <div class="form-group">
                <label for="brand">Brand</label>
                <input type="text" id="brand" name="brand" value="${brand}" required>
            </div>
            
            <!-- Discount -->
            <div class="form-group">
                <label for="discount">Discount (%)</label>
                <input type="number" id="discount" name="discount" value="${discount}" required>
            </div>
            
            <!-- Category -->
            <div class="form-group">
                <label for="category_id">Category</label>
                <select name="category_id" required>
    <c:forEach var="category" items="${categoryList}">
        <option value="${category.category_id}" 
            <c:if test="${category.category_id == category_id}">selected</c:if>>
            ${category.category_name}
        </option>
    </c:forEach>
</select>

            </div>
            
            <!-- Image URL -->
            <div class="form-group">
                <label for="image_url">Product Image</label>
                <input type="file" id="image_url" name="image_url" required>
            </div>
            
            <!-- Form Actions -->
            <div class="form-actions">
                <button type="submit">Add Product</button>
                <a href="${pageContext.request.contextPath}/productmanagement" class="cancel-button">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
