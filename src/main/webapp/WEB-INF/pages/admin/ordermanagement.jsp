<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.GamingHub.model.OrderModel" %>
<%@ page import="com.GamingHub.model.CustomerModel, com.GamingHub.model.ProductModel" %>

<%
    List<OrderModel> orders = (List<OrderModel>) request.getAttribute("orders");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/ordermanagement.css?v=1.2" />
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
            <li><a href="${pageContext.request.contextPath}/productmanagement">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/ordermanagement" class="active">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </div>

    <div class="sticky-header">
        <h1>Order Management</h1>
        
        <form method="get" action="${pageContext.request.contextPath}/ordermanagement" style="margin-bottom: 20px;">
    <input type="text" name="search" placeholder="Search by Order ID, Customer, Product..." value="${searchTerm != null ? searchTerm : ''}" />
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/ordermanagement"><button type="button">Reset</button></a>
</form>
        
        
<form method="post" action="${pageContext.request.contextPath}/ordermanagement" id="dispatchForm">
    <input type="hidden" name="orderId" id="dispatchOrderId" />
    <input type="hidden" name="action" value="dispatch" />
    <button type="submit" id="dispatchButton" class="disabled" disabled>Dispatch Order</button>
</form>

<form method="post" action="${pageContext.request.contextPath}/ordermanagement" id="cancelForm">
    <input type="hidden" name="orderId" id="cancelOrderId" />
    <input type="hidden" name="action" value="cancel" />
    <button type="submit" id="cancelButton" class="disabled" disabled>Cancel Order</button>
</form>


    <!-- Order Table -->
    <table>
        <thead>
        <tr>
            <th></th>
            <th>Order ID</th>
            <th>Date</th>
            <th>Customer</th>
            <th>Product</th>
            <th>Quantity</th>
            <th>Total Price</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (orders != null && !orders.isEmpty()) {
                for (OrderModel order : orders) {
        %>
            <tr>
                <td><input type="radio" name="selectedOrder" value="<%= order.getOrder_id() %>" /></td>
                <td><%= order.getOrder_id() %></td>
                <td><%= order.getOrder_date() %></td>
                <td><%= order.getCustomer().getFirst_name() + " " + order.getCustomer().getLast_name() %></td>
                <td><%= order.getProduct().getProduct_name() %></td>
                <td><%= order.getQuantity() %></td>
                <td>$<%= order.getTotal_amount() %></td>
                <td><%= order.getOrderStatus() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="8">No orders found.</td></tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<script>
    const radios = document.getElementsByName("selectedOrder");
    const dispatchForm = document.getElementById("dispatchForm");
    const cancelForm = document.getElementById("cancelForm");
    const dispatchInput = document.getElementById("dispatchOrderId");
    const cancelInput = document.getElementById("cancelOrderId");
    const dispatchButton = document.getElementById("dispatchButton");
    const cancelButton = document.getElementById("cancelButton");

    // Enable buttons when a radio is selected
    radios.forEach(radio => {
        radio.addEventListener("click", function () {
            dispatchButton.disabled = false;
            cancelButton.disabled = false;
            dispatchButton.classList.remove("disabled");
            cancelButton.classList.remove("disabled");
        });
    });

    dispatchForm.addEventListener("submit", function (e) {
        const selected = Array.from(radios).find(r => r.checked);
        if (!selected) {
            alert("Please select an order to dispatch.");
            e.preventDefault();
        } else {
            dispatchInput.value = selected.value;
        }
    });

    cancelForm.addEventListener("submit", function (e) {
        const selected = Array.from(radios).find(r => r.checked);
        if (!selected) {
            alert("Please select an order to cancel.");
            e.preventDefault();
        } else if (!confirm("Are you sure you want to cancel this order?")) {
            e.preventDefault();
        } else {
            cancelInput.value = selected.value;
        }
    });
</script>

</body>
</html>