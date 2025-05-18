<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>${product.product_name}- Product Details</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/viewproduct.css?v=1.3" />
</head>
<body>

	<%@ include file="/WEB-INF/pages/header.jsp"%>

	<div class="container product-details">

		<h1>${product.product_name}</h1>

		<div class="product-info">
			<img src="${contextPath}/resources/products/${product.image_url}"
				alt="${product.product_name}" />

			<div class="product-meta">
				<p>
					<strong>Brand:</strong> ${product.brand}
				</p>
				<p>
					<strong>Price:</strong> ${product.price}
				</p>
				<p>
					<strong>Discount:</strong> ${product.discount}%
				</p>
				<c:set var="discountedPrice"
					value="${product.price - (product.price * product.discount / 100)}" />

				<p>
					<strong>Price After Discount:</strong> ₹${discountedPrice}
				</p>

				<p>
					<strong>Stock:</strong> ${product.stock_quantity}
				</p>
				<p>
					<strong>Description:</strong> ${product.product_description}
				</p>
				<p>
					<strong>Category:</strong> ${product.category.category_name}
				</p>
				<p>
					<strong>Category Description:</strong>
					${product.category.category_description}
				</p>

				<form id="orderForm" action="${contextPath}/product/details"
					method="post">
					<input type="hidden" name="productId" value="${product.product_id}" />
					<input type="hidden" name="productName"
						value="${product.product_name}" /> <input type="hidden"
						name="productPrice" value="${product.price}" /> <input
						type="hidden" name="productStock"
						value="${product.stock_quantity}" /> <label for="quantity"><strong>Quantity:</strong></label>
					<div class="quantity-selector">
						<button type="button" onclick="changeQuantity(-1)">−</button>
						<input type="number" id="quantity" name="quantity" value="1"
							min="1" max="${product.stock_quantity}" readonly />
						<button type="button" onclick="changeQuantity(1)">+</button>
					</div>

					<button type="submit" class="buy-now">Buy Now</button>
				</form>

				<!-- Form for Redirecting to Orders Page -->
				<form id="orderPageForm" action="${contextPath}/order" method="get">
					<button type="submit" class="view-orders">Go to Orders</button>
				</form>

			</div>
		</div>

		<a href="${contextPath}/product" class="back-link">← Back to
			Products</a>
	</div>

	<%@ include file="/WEB-INF/pages/footer.jsp"%>

	<!-- JavaScript for quantity control -->
	<script>
		function changeQuantity(amount) {
			const input = document.getElementById("quantity");
			const max = parseInt(input.max);
			let current = parseInt(input.value);

			current += amount;
			if (current < 1)
				current = 1;
			if (current > max)
				current = max;

			input.value = current;
		}
	</script>

</body>
</html>
