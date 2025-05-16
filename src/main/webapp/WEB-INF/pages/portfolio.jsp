<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="editMode"
	value="${param.editMode eq 'true' or not empty error}" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Portfolio</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/portfolio.css?v=1.3" />
<%@ include file="/WEB-INF/pages/header.jsp"%>
</head>

<body>

	<form action="${contextPath}/portfolio" method="post"
		enctype="multipart/form-data">
		<div class="portfolio-container">
			<h1>User Portfolio</h1>

			<div class="profile-box">

				<!-- Edit Button Only in View Mode -->
				<c:if test="${!editMode}">
					<a href="${contextPath}/portfolio?editMode=true"> <img
						src="${contextPath}/resources/images/editIcon.png" alt="Edit"
						class="edit-icon" />
					</a>
				</c:if>

				<!-- Profile Image Section -->
				<div class="profile-image-container">
					<img class="profile-image"
						src="${contextPath}/resources/customer/${customer.imageURL}"
						alt="Profile Image" />

					<!-- Show file input only in edit mode -->
					<c:if test="${editMode}">
						<div class="image-upload-section">
							<label for="profileImage" class="custom-file-upload"> Change Profile Picture
							</label> <input type="file" id="profileImage" name="profileImage"
								accept="image/*" />
						</div>
					</c:if>
				</div>


				<div class="info">

					<!-- Name -->
					<label><strong>Name:</strong></label>
					<c:choose>
						<c:when test="${editMode}">
							<input type="text" name="first_name"
								value="${customer.first_name}" required />
							<input type="text" name="last_name" value="${customer.last_name}"
								required />
						</c:when>
						<c:otherwise>
							<p>${customer.first_name}${customer.last_name}</p>
						</c:otherwise>
					</c:choose>

					<!-- Username -->
					<label><strong>Username:</strong></label>
					<c:choose>
						<c:when test="${editMode}">
							<input type="text" name="username" value="${customer.username}"
								readonly />
						</c:when>
						<c:otherwise>
							<p>${customer.username}</p>
						</c:otherwise>
					</c:choose>

					<!-- Email -->
					<label><strong>Email:</strong></label>
					<c:choose>
						<c:when test="${editMode}">
							<input type="email" name="email" value="${customer.email}"
								required />
						</c:when>
						<c:otherwise>
							<p>${customer.email}</p>
						</c:otherwise>
					</c:choose>

					<!-- Date of Birth -->
					<label><strong>Date of Birth:</strong></label>
					<c:choose>
						<c:when test="${editMode}">
							<input type="date" name="dob" value="${customer.dob}" required />
						</c:when>
						<c:otherwise>
							<p>${customer.dob}</p>
						</c:otherwise>
					</c:choose>

					<!-- Gender -->
					<label><strong>Gender:</strong></label>
					<c:choose>
						<c:when test="${editMode}">
							<select name="gender" required>
								<option value="Male"
									${customer.gender == 'Male' ? 'selected' : ''}>Male</option>
								<option value="Female"
									${customer.gender == 'Female' ? 'selected' : ''}>Female</option>
								<option value="Other"
									${customer.gender == 'Other' ? 'selected' : ''}>Other</option>
							</select>
						</c:when>
						<c:otherwise>
							<p>${customer.gender}</p>
						</c:otherwise>
					</c:choose>

					<!-- Phone -->
					<label><strong>Phone:</strong></label>
					<c:choose>
						<c:when test="${editMode}">
							<input type="text" name="number" value="${customer.number}"
								required />
						</c:when>
						<c:otherwise>
							<p>${customer.number}</p>
						</c:otherwise>
					</c:choose>

					<!-- Password Fields in Edit Mode -->
					<c:if test="${editMode}">
						<label><strong>Current Password:</strong></label>
						<input type="password" name="password" />

						<label><strong>New Password:</strong></label>
						<input type="password" name="newPassword" />
					</c:if>

				</div>
			</div>

			<!-- Error Message -->
			<c:if test="${not empty error}">
				<div style="color: red; font-weight: bold; margin: 10px 0;">${error}</div>
			</c:if>

			<!-- Save Button -->
			<c:if test="${editMode}">
				<div style="margin-top: 20px;">
					<button type="submit">Save Changes</button>
					<a href="${contextPath}/portfolio" class="cancel-btn">Cancel
						Changes</a>
				</div>
			</c:if>
		</div>
	</form>

	<%@ include file="/WEB-INF/pages/footer.jsp"%>

</body>
</html>
