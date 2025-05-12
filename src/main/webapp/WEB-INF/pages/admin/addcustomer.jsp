<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Customer - GamingHub</title>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/addcustomer.css?v=1.7.3">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
    <div class="container">
        <h1>Add New Customer</h1>
        
        <c:if test="${not empty addCustomerError}">
    <p class="error-message">${addCustomerError}</p>
</c:if>

<c:if test="${not empty addCustomerSuccess}">
    <p class="success-message">${addCustomerSuccess}</p>
</c:if>

        
        <form action="${pageContext.request.contextPath}/addcustomer" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="first_name">First Name</label>
                <input type="text" id="first_name" name="first_name" value="${first_name}" required>
            </div>
            
            <div class="form-group">
                <label for="last_name">Last Name</label>
                <input type="text" id="last_name" name="last_name" value="${last_name}" required>
            </div>
            
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" value="${username}" required>
            </div>
            
            <div class="form-group">
                <label for="birthday">Date of Birth</label>
                <input type="date" id="birthday" name="dob" value="${dob}" required>
            </div>
            
            <div class="form-group">
                <label for="gender">Gender</label>
                <select id="gender" name="gender" required>
                    <option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
                    <option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${email}" required>
            </div>
            
            <div class="form-group">
                <label for="number">Phone Number</label>
                <input type="tel" id="number" name="number" value="${number}" required>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" value="${password}" required>
            </div>
            
            <div class="form-group">
                <label for="image">Profile Picture</label>
                <input type="file" id="image" name="image">
            </div>
            
            <div class="form-actions">
                <button type="submit">Add Customer</button>
                <a href="${pageContext.request.contextPath}/admin/usermanagement" class="cancel-button">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>