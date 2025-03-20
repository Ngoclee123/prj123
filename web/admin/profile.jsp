<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ - Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700;900&display=swap" rel="stylesheet">
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
        .btn { padding: 8px 15px; border: none; border-radius: 5px; cursor: pointer; transition: all 0.3s; }
        .btn-edit { background: #3498db; color: white; }
        .btn:hover { opacity: 0.9; transform: translateY(-2px); }
        .message { color: #2ecc71; margin-bottom: 15px; padding: 10px; background-color: #e8f5e9; border-radius: 4px; }
        .error { color: #e74c3c; margin-bottom: 15px; padding: 10px; background-color: #fce4e4; border-radius: 4px; }
        .profile-form input { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 5px; }
        .profile-form label { display: block; font-weight: bold; margin-bottom: 5px; }
        .profile-form input[disabled] { background: #f0f0f0; color: #666; }
        @media (max-width: 768px) { 
            .sidebar { width: 80px; } 
            .main-content { margin-left: 80px; }
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
            <a href="<%= request.getContextPath() %>/StatisticServlet">Thống Kê</a>
            <a href="<%= request.getContextPath() %>/logout">Đăng xuất</a>
        </div>
        <div class="main-content">
            <div class="header">
                <h1>Hồ sơ Quản trị</h1>
            </div>
            <div class="tab-container">
                <c:if test="${not empty message}">
                    <div class="message">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <h2>Hồ sơ</h2>
                <form action="<%=request.getContextPath()%>/ProfileServlet" method="post" class="profile-form">
                    <input type="hidden" name="action" value="updateProfile">
                    <input type="hidden" name="userId" value="${sessionScope.account.userId}">
                    <div>
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" value="${sessionScope.account.username}" disabled>
                    </div>
                    <div>
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="${sessionScope.account.email}" required>
                    </div>
                    <div>
                        <label for="fullName">Họ và tên:</label>
                        <input type="text" id="fullName" name="fullName" value="${sessionScope.account.fullName}">
                    </div>
                    <div>
                        <label for="phoneNumber">Số điện thoại:</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" value="${sessionScope.account.phoneNumber}">
                    </div>
                    <div>
                        <label for="address">Địa chỉ:</label>
                        <input type="text" id="address" name="address" value="${sessionScope.account.address}">
                    </div>
                    <button type="submit" class="btn btn-edit">Cập nhật Hồ sơ</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>