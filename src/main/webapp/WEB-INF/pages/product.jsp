<%@ page import="java.util.*, com.GamingHub.model.ProductModel, com.GamingHub.model.CategoryModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
    List<CategoryModel> categories = (List<CategoryModel>) request.getAttribute("categories");
    String selectedCategory = (String) request.getAttribute("selectedCategory");
%>
 	
<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css?v=1.2.5" />
  
</head>
<body>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="container">
    <form action = "${pageContext.request.contextPath}/product" method="get">
        <label for="category">Filter by Category:</label>
        <select name="category" id="category" onchange="this.form.submit()">
            <option value="">All</option>
            <% for (CategoryModel category : categories) { %>
                <option value="<%= category.getCategory_id() %>"
                    <%= (selectedCategory != null && selectedCategory.equals(String.valueOf(category.getCategory_id()))) ? "selected" : "" %>>
                    <%= category.getCategory_name() %>
                </option>
            <% } %>
        </select>
    </form>

    <div class="products">
        <% if (products != null && !products.isEmpty()) {
               for (ProductModel product : products) { %>
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/resources/products/<%= product.getImage_url() %>" alt="<%= product.getProduct_name() %>">
                <h3><%= product.getProduct_name() %></h3>
                <p><%= product.getProduct_description() %></p>
                <p>Brand: <%= product.getBrand() %></p>
                <p>Price: $<%= product.getPrice() %></p>
                <p class="discount">Discount: <%= product.getDiscount() %>%</p>
                <p>In stock: <%= product.getStock_quantity() %></p>
            </div>
        <% } 
           } else { %>
            <p>No products found.</p>
        <% } %>
    </div>
</div>
</body>
</html>
