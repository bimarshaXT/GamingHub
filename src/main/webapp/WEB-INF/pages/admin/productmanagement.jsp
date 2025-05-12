<%@ page import="java.util.*, com.GamingHub.model.ProductModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/productmanagement.css?v=1.4" />
    <style>
        table img {
            width: 60px;
            height: auto;
            border-radius: 8px;
        }
    </style>
</head>

<body>
    <div class="dashboard">
        <div class="sidebar">
            <div class="logo">Admin Panel</div>
            <ul class="menu">
                <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/usermanagement">Users</a></li>
                <li><a href="${pageContext.request.contextPath}/productmanagement" class="active">Products</a></li>
                <li><a href="${pageContext.request.contextPath}/ordermanagement">Orders</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>

<!-- Replace your existing h1 and button-bar with this sticky header section -->
<div class="sticky-header">
    <h1>Product Management</h1>
    
    <div class="search-container">
    <form method="get" action="${pageContext.request.contextPath}/productmanagement" style="display: flex; gap: 10px;">
        <input type="text" name="searchTerm" placeholder="Search products..." value="${param.searchTerm}" />
        <button type="submit"><i class="fas fa-search"></i> Search</button>
        <a href="${pageContext.request.contextPath}/productmanagement">
            <button type="button">Reset</button>
        </a>
    </form>
</div>
    
    <div class="button-bar">
        <form method="get" action="${pageContext.request.contextPath}/addproduct">
            <button type="submit">Add Product</button>
        </form>

        <form method="get" action="${pageContext.request.contextPath}/updateproduct" id="updateForm">
            <input type="hidden" name="id" id="updateProductId" />
            <button type="submit" id="updateButton" class="disabled" disabled>Update Product</button>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/deleteproduct" id="deleteForm">
            <input type="hidden" name="productId" id="deleteProductId" />
            <button type="submit" id="deleteButton" class="disabled" disabled>Delete Product</button>
        </form>
    </div>
</div>


        <!-- Product Table -->
        <table>
            <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Brand</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Discount</th>
                    <th>Stock</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (products != null && !products.isEmpty()) {
                    for (ProductModel product : products) {
            %>
                <tr>
                    <td><input type="radio" name="selectedProduct" value="<%= product.getProduct_id() %>" /></td>
                    <td><%= product.getProduct_id() %></td>
                    <td><img src="<%= contextPath + "/resources/products/" + product.getImage_url() %>" alt="Product Image" /></td>
                    <td><%= product.getProduct_name() %></td>
                    <td><%= product.getBrand() %></td>
                    <td><%= product.getCategory().getCategory_name() %></td>
                    <td>$<%= product.getPrice() %></td>
                    <td><%= product.getDiscount() %>%</td>
                    <td><%= product.getStock_quantity() %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="9">No products available</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <script>
    const radios = document.getElementsByName("selectedProduct");
    const updateForm = document.getElementById("updateForm");
    const deleteForm = document.getElementById("deleteForm");
    const updateInput = document.getElementById("updateProductId");
    const deleteInput = document.getElementById("deleteProductId");
    const updateButton = document.getElementById("updateButton");
    const deleteButton = document.getElementById("deleteButton");

    // Enable buttons when a radio button is selected
    for (let i = 0; i < radios.length; i++) {
        radios[i].addEventListener("click", function() {
            // Enable the buttons by removing the 'disabled' class
            updateButton.disabled = false;
            deleteButton.disabled = false;
            updateButton.classList.remove("disabled");
            deleteButton.classList.remove("disabled");
        });
    }

    updateForm.addEventListener("submit", function(e) {
        const selected = Array.from(radios).find(r => r.checked);
        if (!selected) {
            alert("Please select a product to update.");
            e.preventDefault();
        } else {
            updateInput.value = selected.value;
        }
    });

    deleteForm.addEventListener("submit", function(e) {
        const selected = Array.from(radios).find(r => r.checked);
        if (!selected) {
            alert("Please select a product to delete.");
            e.preventDefault();
        } else if (!confirm("Are you sure you want to delete this product?")) {
            e.preventDefault();
        } else {
            deleteInput.value = selected.value;
        }
    });


    </script>
</body>
</html>
