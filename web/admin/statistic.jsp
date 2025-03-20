<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống Kê - Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700;900&display=swap" rel="stylesheet">
    <!-- Nhúng Chart.js từ CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Roboto', 'Segoe UI', sans-serif; }
        body { background: #f0f2f5; color: #333; line-height: 1.6; }
        .container { display: flex; min-height: 100vh; }
        .sidebar { 
            width: 250px; 
            background: linear-gradient(135deg, #2b0f2b, #4b1e4b); /* Tím đậm cho Admin */
            padding: 20px; 
            color: white; 
            position: fixed; 
            height: 100vh; 
            transition: width 0.3s, box-shadow 0.3s; 
            box-shadow: 0 5px 25px rgba(75, 30, 75, 0.5); 
            overflow: hidden; 
            border-right: 1px solid rgba(255, 255, 255, 0.1); 
        }
        .sidebar::before { 
            content: ""; 
            position: absolute; 
            top: 0; 
            left: 0; 
            width: 100%; 
            height: 100%; 
            background: linear-gradient(45deg, rgba(255, 255, 255, 0.05), rgba(255, 255, 255, 0)); 
            pointer-events: none; 
        }
        .sidebar:hover { 
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.7); 
        }
        .sidebar h2 { 
            margin-bottom: 40px; 
            font-size: 28px; 
            font-weight: 800; 
            text-align: center; 
            padding: 15px; 
            border-radius: 12px; 
            text-transform: uppercase; 
            letter-spacing: 2px; 
            position: relative; 
            overflow: hidden; 
            display: flex; 
            align-items: center; 
            justify-content: center; 
            text-shadow: 0 2px 5px rgba(0, 0, 0, 0.5); 
            transition: all 0.4s ease; 
        }
        .sidebar h2::before { 
            content: ""; 
            position: absolute; 
            top: 0; 
            left: -100%; 
            width: 100%; 
            height: 100%; 
            background: rgba(255, 255, 255, 0.1); 
            transform: skewX(-20deg); 
            transition: all 0.6s ease; 
        }
        .sidebar h2:hover::before { 
            left: 100%; 
        }
        .sidebar h2.admin-title { 
            background: linear-gradient(45deg, #ff416c, #ff4b2b); 
            color: #fff; 
            box-shadow: 0 0 20px rgba(255, 75, 43, 0.6); 
            animation: glow-admin 1.5s infinite alternate; 
        }
        .sidebar h2.admin-title::after { 
            content: "🔒"; 
            position: absolute; 
            left: 15px; 
            font-size: 24px; 
            animation: spin-icon 2s infinite linear; 
        }
        .sidebar h2:hover { 
            transform: scale(1.1) translateY(-5px); 
            box-shadow: 0 0 30px rgba(255, 255, 255, 0.8); 
        }
        .sidebar a { 
            color: #dfe6e9; 
            text-decoration: none; 
            display: block; 
            padding: 12px 15px; 
            margin: 10px 0; 
            border-radius: 5px; 
            transition: all 0.3s; 
            font-weight: 500; 
            letter-spacing: 1px; 
        }
        .sidebar a:hover { 
            background: #ff4b2b; 
            color: white; 
            transform: translateX(10px); 
            box-shadow: 0 0 15px rgba(255, 75, 43, 0.5); 
        }
        @keyframes glow-admin {
            0% { box-shadow: 0 0 20px rgba(255, 75, 43, 0.6); }
            100% { box-shadow: 0 0 40px rgba(255, 75, 43, 1); }
        }
        @keyframes spin-icon {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .main-content { margin-left: 250px; flex: 1; padding: 30px; }
        .header { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 30px; }
        .tab-container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .stats-container { display: flex; justify-content: space-between; flex-wrap: wrap; gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); width: 22%; text-align: center; transition: all 0.3s ease; }
        .stat-card:hover { transform: translateY(-5px); box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2); }
        .stat-card.clickable { cursor: pointer; }
        .stat-value { font-size: 24px; font-weight: bold; color: #3498db; display: block; margin-bottom: 10px; }
        .stat-icon { font-size: 24px; color: #7f8c8d; display: block; margin-bottom: 10px; }
        .stat-label { font-size: 14px; color: #333; display: block; }
        .user-info-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            display: none;
            background-color: #fff;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
        }
        .user-info-table th, .user-info-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .user-info-table th {
            background-color: #34495e;
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 14px;
        }
        .user-info-table td {
            background-color: #f9f9f9;
            color: #333;
            font-size: 14px;
        }
        .user-info-table tr:hover {
            background-color: #f5f5f5;
        }

        /* Cải thiện CSS cho phần biểu đồ */
        .chart-wrapper {
            position: relative;
            max-width: 800px;
            margin: 30px auto;
            padding: 20px;
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 5px 25px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }
        .chart-wrapper:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
        }
        .tab-container h2 {
            font-size: 22px;
            font-weight: 700;
            color: #333;
            text-align: center;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            margin-bottom: 20px;
            position: relative;
            padding-bottom: 10px;
        }
        .tab-container h2::after {
            content: "";
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 60px;
            height: 3px;
            background: linear-gradient(90deg, #ff416c, #ff4b2b);
            border-radius: 2px;
        }
        canvas#ordersChart {
            width: 100% !important;
            height: 400px !important;
            border-radius: 10px;
            background: linear-gradient(135deg, #f9f9f9, #f5f5f5);
            padding: 10px;
        }

        @media (max-width: 768px) { 
            .sidebar { width: 80px; } 
            .main-content { margin-left: 80px; }
            .stat-card { width: 48%; }
            .chart-wrapper { max-width: 100%; }
        }
        @media (max-width: 480px) { 
            .stat-card { width: 100%; }
            canvas#ordersChart { height: 300px !important; }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar admin-panel">
            <h2 class="admin-title">Admin Panel</h2>
            <a href="<%= request.getContextPath() %>/AdminServlet?activeTab=products">Quản lý sản phẩm</a>
            <a href="<%= request.getContextPath() %>/AdminServlet?activeTab=users">Quản lý người dùng</a>
            <a href="<%= request.getContextPath() %>/ProfileServlet">Hồ sơ</a>
            <a href="<%= request.getContextPath() %>/StatisticServlet" class="active">Thống Kê</a>
            <a href="<%= request.getContextPath() %>/logout">Đăng xuất</a>
        </div>
        <div class="main-content">
            <div class="header">
                <h1>Thống Kê Hệ Thống</h1>
            </div>
            <div class="tab-container">
                <div class="stats-container">
                    <div class="stat-card">
                        <span class="stat-value">${activeUsers}</span>
                        <span class="stat-icon">👤</span>
                        <span class="stat-label">Tổng người dùng hoạt động</span>
                    </div>
                    <div class="stat-card clickable" onclick="toggleUserInfo()">
                        <span class="stat-value">${onlineUsers}</span>
                        <span class="stat-icon">💻</span>
                        <span class="stat-label">Số người đang online</span>
                    </div>
                    <div class="stat-card">
                        <span class="stat-value">${totalRevenue} VNĐ</span>
                        <span class="stat-icon">💰</span>
                        <span class="stat-label">Tổng doanh thu</span>
                    </div>
                    <div class="stat-card">
                        <span class="stat-value">${completedOrderCount}</span>
                        <span class="stat-icon">🛒</span>
                        <span class="stat-label">Số đơn hàng đã bán</span>
                    </div>
                </div>
                <table class="user-info-table" id="userInfoTable">
                    <thead>
                        <tr>
                            <th>Tên người dùng</th>
                            <th>Thời gian đăng nhập</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${activeUsersList}">
                            <tr>
                                <td>${entry.key}</td>
                                <td>${entry.value}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty activeUsersList}">
                            <tr>
                                <td colspan="2">Không có người dùng nào đang đăng nhập.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>

                <!-- Thêm biểu đồ với wrapper -->
                <h2>Biểu đồ số đơn hàng hoàn thành - Tháng 3/2025</h2>
                <div class="chart-wrapper">
                    <canvas id="ordersChart"></canvas>
                </div>
            </div>
        </div>
    </div>
    <script>
        function toggleUserInfo() {
            var table = document.getElementById("userInfoTable");
            if (table.style.display === "none" || table.style.display === "") {
                table.style.display = "table";
            } else {
                table.style.display = "none";
            }
        }

        // Dữ liệu biểu đồ từ Servlet
        const ordersData = ${ordersByDay};
        const days = Array.from({length: 31}, (_, i) => i + 1); // Tạo mảng từ 1 đến 31
        const orderCounts = days.map(day => {
            const found = ordersData.find(item => item.orderDay === day);
            return found ? found.orderCount : 0;
        });

        // Tạo biểu đồ
        const ctx = document.getElementById('ordersChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: days,
                datasets: [{
                    label: 'Số đơn hàng hoàn thành',
                    data: orderCounts,
                    backgroundColor: 'rgba(255, 75, 43, 0.5)', // Màu cam nhạt phù hợp với theme Admin
                    borderColor: 'rgba(255, 75, 43, 1)', // Màu cam đậm
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255, 75, 43, 0.8)',
                    hoverBorderColor: 'rgba(255, 75, 43, 1)'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: { 
                        beginAtZero: true, 
                        title: { display: true, text: 'Số đơn hàng', font: { size: 14 } },
                        grid: { color: 'rgba(0, 0, 0, 0.05)' }
                    },
                    x: { 
                        title: { display: true, text: 'Ngày trong tháng', font: { size: 14 } },
                        grid: { display: false }
                    }
                },
                plugins: {
                    legend: {
                        labels: { font: { size: 14 }, color: '#333' }
                    }
                },
                animation: {
                    duration: 1000,
                    easing: 'easeOutBounce'
                }
            }
        });
    </script>
</body>
</html>