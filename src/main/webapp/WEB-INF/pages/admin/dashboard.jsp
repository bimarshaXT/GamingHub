<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Admin Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #1a1a1a;
            color: #ffffff;
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Styles */
        .sidebar {
            width: 250px;
            background-color: #0c0c0c;
            height: 100vh;
            position: fixed;
            padding: 20px 0;
        }

        .logo {
            color: #ffb500;
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            padding: 20px 0;
            margin-bottom: 30px;
            border-bottom: 1px solid #333;
        }

        .menu {
            list-style: none;
        }

        .menu li {
            margin-bottom: 5px;
        }

        .menu a {
            display: block;
            color: #cccccc;
            text-decoration: none;
            padding: 15px 25px;
            transition: all 0.3s;
        }

        .menu a:hover {
            background-color: #333;
            color: #ffc233;
        }

        .menu .active {
            background-color: #333;
            color: #ffb500;
            border-left: 4px solid #ffb500;
        }

        /* Main Content Area */
        .main-content {
            margin-left: 250px;
            width: calc(100% - 250px);
            padding: 30px;
        }

        h1 {
            color: #ffb500;
            margin-bottom: 30px;
            font-size: 28px;
        }

        /* Summary Boxes */
        .summary-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }

        .summary-box {
            background-color: #0c0c0c;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
        }

        .summary-box:hover {
            transform: translateY(-5px);
        }

        .summary-box p {
            color: #cccccc;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .summary-box strong {
            color: #ffffff;
            font-size: 24px;
            display: block;
        }

        /* Charts Container */
        .charts-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
            gap: 30px;
            margin-top: 30px;
        }

        .chart-container {
            background-color: #0c0c0c;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .chart-container h2 {
            color: #ffb500;
            margin-bottom: 20px;
            font-size: 18px;
            text-align: center;
        }

        canvas {
            width: 100% !important;
            height: 300px !important;
        }

        hr {
            border: none;
            height: 1px;
            background-color: #333;
            margin: 30px 0;
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">Admin Panel</div>
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/dashboard" class="active">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/usermanagement">Users</a></li>
            <li><a href="${pageContext.request.contextPath}/productmanagement">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/ordermanagement">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <h1>Admin Dashboard</h1>

        <!-- Summary Boxes -->
        <div class="summary-container">
            <div class="summary-box">
                <p>Total Users</p>
                <strong>${userCount}</strong>
            </div>
            <div class="summary-box">
                <p>Total Products</p>
                <strong>${productCount}</strong>
            </div>
            <div class="summary-box">
                <p>Processing Orders</p>
                <strong>${processingCount}</strong>
            </div>
            <div class="summary-box">
                <p>Shipped Orders</p>
                <strong>${shippedCount}</strong>
            </div>
            <div class="summary-box">
                <p>Cancelled Orders</p>
                <strong>${cancelledCount}</strong>
            </div>
            <div class="summary-box">
                <p>Total Revenue</p>
                <strong>$${totalRevenue}</strong>
            </div>
        </div>

        <!-- Charts Container -->
        <div class="charts-container">
            <!-- Category Sales Pie Chart -->
            <div class="chart-container">
                <h2>Category Sales</h2>
                <canvas id="categoryChart"></canvas>
            </div>

            <!-- Daily Sales Line Chart -->
            <div class="chart-container">
                <h2>Daily Sales</h2>
                <canvas id="dailyChart"></canvas>
            </div>
        </div>
    </div>

    <script>
        // Simple Category Pie Chart
        new Chart(
            document.getElementById('categoryChart'),
            {
                type: 'pie',
                data: {
                    labels: [
                        <c:forEach var="entry" items="${categorySales}">
                            '${entry.key}',
                        </c:forEach>
                    ],
                    datasets: [{
                        data: [
                            <c:forEach var="entry" items="${categorySales}">
                                ${entry.value},
                            </c:forEach>
                        ],
                        backgroundColor: ['#ff6384', '#36a2eb', '#ffce56', '#4bc0c0', '#9966ff']
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { 
                            position: 'right',
                            labels: {
                                color: '#ffffff'
                            }
                        }
                    }
                }
            }
        );

        // Simple Daily Line Chart
        new Chart(
            document.getElementById('dailyChart'),
            {
                type: 'line',
                data: {
                    labels: [
                        <c:forEach var="entry" items="${dailySales}">
                            '${entry.key}',
                        </c:forEach>
                    ],
                    datasets: [{
                        label: 'Sales ($)',
                        data: [
                            <c:forEach var="entry" items="${dailySales}">
                                ${entry.value},
                            </c:forEach>
                        ],
                        borderColor: '#ffb500',
                        borderWidth: 2,
                        backgroundColor: 'rgba(255, 181, 0, 0.1)',
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: {
                                color: 'rgba(255, 255, 255, 0.1)'
                            },
                            ticks: {
                                color: '#cccccc'
                            }
                        },
                        x: {
                            grid: {
                                color: 'rgba(255, 255, 255, 0.1)'
                            },
                            ticks: {
                                color: '#cccccc'
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            labels: {
                                color: '#ffffff'
                            }
                        }
                    }
                }
            }
        );
    </script>
</body>
</html>