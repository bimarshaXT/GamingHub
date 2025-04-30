<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>User Management</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/usermanagement.css?v=1.2.2" />
</head>

<body>
  <div class="dashboard">
    <div class="sidebar">
      <div class="logo">Admin Panel</div>
      <ul class="menu">
        <li><a href="${pageContext.request.contextPath}/pages/admin/dashboard.jsp" class="active">Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/pages/admin/usermanagement.jsp">Users</a></li>
        <li><a href="${pageContext.request.contextPath}/pages/admin/productmanagement.jsp">Products</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
      </ul>
    </div>
    <h1>Product Management</h1>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Product Name</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>P001</td>
                <td>Laptop Dell XPS 13</td>
                <td>Electronics</td>
                <td>$1299.99</td>
                <td>15</td>
            </tr>
            <tr>
                <td>P002</td>
                <td>Wireless Headphones</td>
                <td>Electronics</td>
                <td>$199.99</td>
                <td>42</td>
            </tr>
            <tr>
                <td>P003</td>
                <td>Office Chair</td>
                <td>Furniture</td>
                <td>$249.99</td>
                <td>8</td>
            </tr>
            <tr>
                <td>P004</td>
                <td>Cotton T-Shirt</td>
                <td>Clothing</td>
                <td>$24.99</td>
                <td>120</td>
            </tr>
            <tr>
                <td>P005</td>
                <td>Design Patterns Book</td>
                <td>Books</td>
                <td>$39.99</td>
                <td>27</td>
            </tr>
        </tbody>
    </table>
</body>
</html>