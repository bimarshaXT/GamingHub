<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css?v=1.3.1">
  <link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  

</head>
<body>
  <div id="header">
    <header class="header">
      <h1 class="logo">
        <a href="${pageContext.request.contextPath}/Home">
          <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Logo" />
          <span class="site-title">GamingHub</span>
        </a>
      </h1>
      
      <ul class="main-nav">
        <li><a href="${pageContext.request.contextPath}/Home" class="home-button">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/product" class="product-button">Products</a></li>
        <li>
          <a href="#" class="icon-container">
            <!-- Placeholder for wishlist icon image -->
           <i class="fas fa-heart wishlist-icon" style="font-size: 20px; color: #ff4d4d;"></i>
          </a>
        </li>
        <li class="profile-container">
          <div class="profile-icon">
            <!-- Placeholder for profile image -->
            <img src="${pageContext.request.contextPath}/resources/images/download.png" alt="Profile" style="width: 100%; height: 100%; object-fit: cover;" />
          </div>
          <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/portfolio">My Portfolio</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
          </div>
        </li>
      </ul>
    </header>
  </div>


</body>
</html>