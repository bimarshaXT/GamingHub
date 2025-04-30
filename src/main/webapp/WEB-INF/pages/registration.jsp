<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Join GamingHub</title>

  <!-- CSS for the registration form -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css?v=1.7.3">

  <!-- CSS for header and footer -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css?v=1.5.1">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css?v=1.2.3">

  <!-- Font Awesome for social icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>
<body>


<!-- Registration Form Content -->
<div class="container">
  <h1>Create Your Account</h1>
  
  <c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>

  <form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="firstName">First Name</label>
      <input type="text" id="first_name" name="first_name" value="${first_name}" required>
    </div>
    
    <div class="form-group">
      <label for="lastName">Last Name</label>
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

    <button type="submit">Join The Community</button>
  </form>

  <a href="${pageContext.request.contextPath}/login" class="login-button">
    Already have an account? Log in
  </a>
</div>


</body>
</html>