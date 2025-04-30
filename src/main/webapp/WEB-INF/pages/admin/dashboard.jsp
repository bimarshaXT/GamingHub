<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Simple Admin Dashboard</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/dashboard.css?v=1.2.2" />
</head>

<body>
  <div class="dashboard">
    <div class="sidebar">
      <div class="logo">Admin Panel</div>
      <ul class="menu">
        <li><a href="#" class="active">Dashboard</a></li>
        <li><a href="#">Users</a></li>
        <li><a href="#">Products</a></li>
        <li><a href="#">Logout</a></li>
      </ul>
    </div>
    
    <div class="content">
      <div class="header">
        <h1 class="page-title">Dashboard</h1>
        <div class="user-info">
          <span class="user-name">Admin User</span>
        </div>
      </div>
      
      <div class="cards">
        <div class="card">
          <div class="card-title">Total Users</div>
          <div class="card-value">1,254</div>
        </div>
        <div class="card">
          <div class="card-title">Total Products</div>
          <div class="card-value">543</div>
        </div>
        <div class="card">
          <div class="card-title">Active Users</div>
          <div class="card-value">876</div>
        </div>
        <div class="card">
          <div class="card-title">Total Revenue</div>
          <div class="card-value">$15,689</div>
        </div>
      </div>
    </div>
  </div>
  
  <script>
    // Simple JS for any interactive elements could be added here
    document.addEventListener('DOMContentLoaded', function() {
      console.log('Dashboard loaded');
    });
  </script>
</body>
</html>