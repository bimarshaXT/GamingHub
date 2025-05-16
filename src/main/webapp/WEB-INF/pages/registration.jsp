<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Join GamingHub</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<div class="container">
  <h1>Create Your Account</h1>

  <c:if test="${not empty error}">
    <p class="error-message">${error}</p>
  </c:if>

  <c:if test="${not empty success}">
    <p class="success-message">${success}</p>
  </c:if>

  <form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label>First Name</label>
      <input type="text" name="first_name" value="${first_name}" required>
      <c:if test="${not empty first_name_error}">
        <p class="error-message">${first_name_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Last Name</label>
      <input type="text" name="last_name" value="${last_name}" required>
      <c:if test="${not empty last_name_error}">
        <p class="error-message">${last_name_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Username</label>
      <input type="text" name="username" value="${username}" required>
      <c:if test="${not empty username_error}">
        <p class="error-message">${username_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Date of Birth</label>
      <input type="date" name="dob" value="${dob}" required>
      <c:if test="${not empty dob_error}">
        <p class="error-message">${dob_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Gender</label>
      <select name="gender" required>
        <option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
        <option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
      </select>
      <c:if test="${not empty gender_error}">
        <p class="error-message">${gender_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Email</label>
      <input type="email" name="email" value="${email}" required>
      <c:if test="${not empty email_error}">
        <p class="error-message">${email_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Phone Number</label>
      <input type="tel" name="number" value="${number}" required>
      <c:if test="${not empty number_error}">
        <p class="error-message">${number_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Password</label>
      <input type="password" name="password" required>
      <c:if test="${not empty password_error}">
        <p class="error-message">${password_error}</p>
      </c:if>
    </div>

    <div class="form-group">
      <label>Profile Picture</label>
      <input type="file" name="image">
      <c:if test="${not empty image_error}">
        <p class="error-message">${image_error}</p>
      </c:if>
    </div>

    <button type="submit">Join The Community</button>
  </form>

  <a href="${pageContext.request.contextPath}/login" class="login-button">
    Already have an account? Log in
  </a>
</div>

</body>
</html>
