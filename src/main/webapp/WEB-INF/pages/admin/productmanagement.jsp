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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/productmanagement.css?v=1.3" />
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
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>

        <h1>Product Management</h1>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Brand</th>
                    <th>Category ID</th>
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
                    <td><%= product.getProduct_id() %></td>
                    <td><img src="<%= contextPath + "/resources/products/" + product.getImage_url() %>" alt="Product Image" /></td>
                    <td><%= product.getProduct_name() %></td>
                    <td><%= product.getBrand() %></td>
                    <td><%= product.getCategory().getCategory_id() %></td>
                    <td>$<%= product.getPrice() %></td>
                    <td><%= product.getDiscount() %>%</td>
                    <td><%= product.getStock_quantity() %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="8">No products available</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>
