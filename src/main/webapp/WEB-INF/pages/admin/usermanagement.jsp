<%@ page import="java.util.*, com.GamingHub.model.CustomerModel"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%
List<CustomerModel> customers = (List<CustomerModel>) request.getAttribute("customers");
String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Management</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin/usermanagement.css?v=1.3" />
</head>

<body>
	<div class="dashboard">
		<div class="sidebar">
			<div class="logo">Admin Panel</div>
			<ul class="menu">
				<li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
				<li><a href="${pageContext.request.contextPath}/usermanagement"
					class="active">Users</a></li>
				<li><a
					href="${pageContext.request.contextPath}/productmanagement">Products</a></li>
				<li><a
					href="${pageContext.request.contextPath}/ordermanagement">Orders</a></li>
				<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</div>

		<h1>User Management</h1>

		<!-- Button Bar -->
		<div class="button-bar">
			<form method="get"
				action="${pageContext.request.contextPath}/addcustomer">
				<button type="submit">Add Customer</button>
			</form>

			<form method="post"
				action="${pageContext.request.contextPath}/deletecustomer"
				id="deleteForm">
				<button type="submit" id="deleteButton" class="disabled" disabled>Delete
					Customer</button>
			</form>

			<!-- Search Bar -->
			<div class="search-container" style="margin-bottom: 20px;">
				<form method="get"
					action="${pageContext.request.contextPath}/usermanagement"
					style="display: flex; gap: 10px;">
					<input type="text" name="searchTerm" placeholder="Search users..."
						value="${param.searchTerm}" />
					<button type="submit">
						<i class="fas fa-search"></i> Search
					</button>
					<!-- Reset Button -->
					<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/usermanagement'">Reset</button>
				</form>
			</div>
		</div>

		<!-- User Table -->
		<table>
			<thead>
				<tr>
					<th></th>
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
					<td><input type="radio" name="customerId"
						value="<%=customer.getCustomer_id()%>" /></td>
					<td><img
						src="<%=contextPath + "/resources/customer/" + customer.getImageURL()%>"
						alt="User Image" /></td>
					<td><%=customer.getFirst_name() + " " + customer.getLast_name()%></td>
					<td><%=customer.getUsername()%></td>
					<td><%=customer.getEmail()%></td>
					<td><%=customer.getDob()%></td>
					<td><%=customer.getGender()%></td>
					<td><%=customer.getNumber()%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="8">No users found</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<script>
        const radios = document.getElementsByName("customerId");
        const deleteForm = document.getElementById("deleteForm");
        const deleteButton = document.getElementById("deleteButton");

        // Enable delete button when a radio is selected
        for (let i = 0; i < radios.length; i++) {
            radios[i].addEventListener("click", function() {
                deleteButton.disabled = false;
                deleteButton.classList.remove("disabled");
            });
        }

        deleteForm.addEventListener("submit", function(e) {
            const selected = Array.from(radios).find(r => r.checked);
            if (!selected) {
                alert("Please select a customer to delete.");
                e.preventDefault();
            } else if (!confirm("Are you sure you want to delete this customer?")) {
                e.preventDefault();
            } else {
                // Create a hidden input dynamically for the customerId
                let input = document.createElement("input");
                input.type = "hidden";
                input.name = "customerId";
                input.value = selected.value;
                deleteForm.appendChild(input);
            }
        });
    </script>
</body>
</html>
