<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản trị</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f6fa;
        }
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            height: 100%;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
        }
        .sidebar h2 {
            font-size: 24px;
            margin-bottom: 30px;
        }
        .sidebar a {
            display: block;
            color: white;
            padding: 10px 0;
            text-decoration: none;
            font-size: 16px;
        }
        .sidebar a:hover {
            background-color: #34495e;
            border-radius: 5px;
        }
        .content {
            margin-left: 250px;
            padding: 20px;
        }
        .card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .card h3 {
            margin-bottom: 20px;
        }
        /* CSS cho nút Hiển thị thông tin và bảng thông tin */
        .user-info-container {
            margin: 10px 0;
        }
        .user-info-btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .user-info-btn:hover {
            background-color: #0056b3;
        }
        .user-info-table {
            display: none;
            margin-top: 10px;
            border-collapse: collapse;
            width: 100%;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }
        .user-info-table th, .user-info-table td {
            padding: 8px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .user-info-table th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2>ADMIN PANEL</h2>
        <a href="#">Quản lý sản phẩm</a>
        <a href="#">Quản lý người dùng</a>
        <a href="#">Hệ số</a>
        <a href="#">Thống Kê</a>
        <a href="<%= request.getContextPath() %>/logout">Đăng xuất</a>
    </div>

    <!-- Content -->
    <div class="content">
        <div class="card">
            <h3>Dashboard Quản trị</h3>
            <p>Sản phẩm | Người dùng | Hệ số | Thống Kê</p>
        </div>

        <div class="card">
            <h3>Thống Kê Hệ Thống</h3>
            <p>Tổng số người dùng hoạt động: ${totalUsers}</p>
            <p>Số người dùng đang online: ${onlineUsers}</p>

            <!-- Nút Hiển thị thông tin -->
            <div class="user-info-container">
                <button class="user-info-btn" onclick="toggleUserInfo()">Hiển thị thông tin</button>
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
    </script>
</body>
</html>