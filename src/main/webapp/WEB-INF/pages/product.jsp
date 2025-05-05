<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Products</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/product.css?v=1.3" />
</head>
<body>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="container">
    <form action="${contextPath}/product" method="get">
        <label for="category">Filter by Category:</label>
        <select name="category" id="category" onchange="this.form.submit()">
            <option value="">All</option>
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.category_id}" <c:if test="${selectedCategory == cat.category_id}">selected</c:if>>
                    ${cat.category_name}
                </option>
            </c:forEach>
        </select>
    </form>

    <div class="products">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="product" items="${products}">
                    <div class="product-card">
                        <img src="${contextPath}/resources/products/${product.image_url}" alt="${product.product_name}">
                        <h3>${product.product_name}</h3>
                        <p class="brand">Brand: ${product.brand}</p>
                        <p class="price">${product.price}</p>
                        <p class="discount">-${product.discount}%</p>
                        <a href="${contextPath}/product/${product.product_id}" class="view-product-btn">View Product</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No products found.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>

</body>
</html>