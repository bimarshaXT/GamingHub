<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>My Orders</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/order.css?v=1.1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <%@ include file="/WEB-INF/pages/header.jsp" %>
    
    <div class="container">
        <div class="orders-header">
            <h2>My Orders</h2>
            <p>View and track all your orders</p>
        </div>

        <c:choose>
            <c:when test="${empty orders}">
                <div class="empty-orders">
                    <p>You haven't placed any orders yet.</p>
                    <a href="${contextPath}/product" class="shop-now-btn">Shop Now</a>
                </div>
            </c:when>
            <c:otherwise>
                <table class="orders-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Date</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Total Price</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td><span class="order-id">${order.order_id}</span></td>
                                <td>${order.order_date}</td>
                                <td><div class="product-name">${order.product.product_name}</div></td>
                                <td>${order.quantity}</td>
                                <td><span class="price">${order.total_amount}</span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.orderStatus == 'Processing'}">
                                            <span class="status status-processing">Processing</span>
                                        </c:when>
                                        <c:when test="${order.orderStatus == 'Completed'}">
                                            <span class="status status-completed">Completed</span>
                                        </c:when>
                                        <c:when test="${order.orderStatus == 'Shipped'}">
                                            <span class="status status-shipped">Shipped</span>
                                        </c:when>
                                        <c:when test="${order.orderStatus == 'Cancelled'}">
                                            <span class="status status-cancelled">Cancelled</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    
    <%@ include file="/WEB-INF/pages/footer.jsp" %>
</body>
</html>