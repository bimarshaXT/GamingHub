<%@ page import="java.util.*, com.GamingHub.model.CustomerModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CustomerModel> customers = (List<CustomerModel>) request.getAttribute("customers");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/usermanagement.css?v=1.2.4" />
    <style>
        table img {
            width: 50px;
            height: auto;
            border-radius: 50%;
        }
    </style>
</head>

<body>
    <div class="dashboard">
        <div class="sidebar">
            <div class="logo">Admin Panel</div>
            <ul class="menu">
                <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/usermanagement" class="active">Users</a></li>
                <li><a href="${pageContext.request.contextPath}/productmanagement">Products</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>

        <h1>User Management</h1>

        <table>
            <thead>
                <tr>
                    <th>Image</th>
                    <th>Full Name</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>DOB</th>
                    <th>Gender</th>
                    <th>Phone</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (customers != null && !customers.isEmpty()) {
                        for (CustomerModel customer : customers) {
                %>
                    <tr>
                        <td><img src="<%= contextPath + "/resources/customer/" + customer.getImageURL() %>" alt="User Image" /></td>
                        <td><%= customer.getFirst_name() + " " + customer.getLast_name() %></td>
                        <td><%= customer.getUsername() %></td>
                        <td><%= customer.getEmail() %></td>
                        <td><%= customer.getDob() %></td>
                        <td><%= customer.getGender() %></td>
                        <td><%= customer.getNumber() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="7">No users found</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
