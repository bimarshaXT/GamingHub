<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Portfolio</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/portfolio.css?v=1.2.3" />
    <%@ include file="/WEB-INF/pages/header.jsp" %>
</head>

<body>

	<form action="${pageContext.request.contextPath}/portfolio" method="post" enctype="multipart/form-data">
    <div class="portfolio-container">
        <h1>User Portfolio</h1>

        <div class="profile-box">
        	<a href="${pageContext.request.contextPath}/updatePortfolio">
    		<img src="${pageContext.request.contextPath}/resources/images/editIcon.png" alt="Edit" class="edit-icon" />
			</a>
        
            <div class="profile-image-wrapper">
    <img class="profile-image" src="${contextPath}/resources/customer/${customer.imageURL}" alt="Profile Image" />
</div>


            
            

            <div class="info">
                <p><strong>Name:</strong> ${customer.first_name} ${customer.last_name}</p>
                <p><strong>Username:</strong> ${customer.username}</p>
                <p><strong>Email:</strong> ${customer.email}</p>
                <p><strong>Date of Birth:</strong> ${customer.dob}</p>
                <p><strong>Gender:</strong> ${customer.gender}</p>
                <p><strong>Phone:</strong> ${customer.number}</p>
            </div>
        </div>
    </div>
    </form>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
</body>


</html>
