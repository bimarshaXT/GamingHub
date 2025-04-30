<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login to GamingHub</title>

  <!-- CSS for the login form -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css?v=1.7.3">

  <!-- Font Awesome for social icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<!-- Login Form Content -->
<div class="login-wrapper">
  <div class="login-box">
    <h2>Welcome Back</h2>
    
    <c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>
		
    <form action="${pageContext.request.contextPath}/login" method="post">
      <div class="row">
        <div class="col">
          <label for="username">Username</label>
          <input type="text" id="username" name="username" required>
        </div>
      </div>
      
      <div class="row">
        <div class="col">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" required>
        </div>
      </div>
      
      <button type="submit">Login</button>
    </form>
    
    <!-- Second button for registration -->
    <form action="${pageContext.request.contextPath}/registration" method="get">
      <button type="submit" class="secondary-button">Create New Account</button>
    </form>
    
    <!-- Add forgot password link if needed -->
    <a href="${pageContext.request.contextPath}/forgot-password" style="display: block; text-align: center; margin-top: 20px; color: #AAAAAA; text-decoration: none; font-size: 14px;">
      Forgot your password?
    </a>
  </div>
</div>

</body>
</html>
