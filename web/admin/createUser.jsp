<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Người Dùng</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
        body { background: #f0f2f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .container { max-width: 600px; width: 100%; padding: 30px; background: white; border-radius: 15px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); margin: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-size: 14px; font-weight: 500; color: #2c3e50; margin-bottom: 8px; }
        input, select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; font-size: 14px; transition: border-color 0.3s; }
        input:focus, select:focus { border-color: #3498db; outline: none; }
        button { padding: 12px 25px; border: none; border-radius: 8px; cursor: pointer; font-size: 14px; font-weight: 500; transition: all 0.3s; }
        .btn-add { background: #2ecc71; color: white; margin-right: 10px; }
        .btn-cancel { background: #e74c3c; color: white; }
        button:hover { opacity: 0.9; transform: translateY(-2px); }
        .error { color: #e74c3c; margin-bottom: 15px; padding: 10px; background-color: #fce4e4; border-radius: 8px; font-size: 14px; }
        h2 { font-size: 24px; color: #2c3e50; text-align: center; margin-bottom: 20px; }
        @media (max-width: 768px) {
            .container { padding: 20px; margin: 10px; }
            button { padding: 10px 20px; font-size: 13px; }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Thêm Người Dùng</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="<%=request.getContextPath()%>/AdminServlet" method="post">
            <input type="hidden" name="action" value="addUser">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${account.username}" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${account.email}" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="${account.password}" required>
            </div>
            <div class="form-group">
                <label for="roleid">Role:</label>
                <select id="roleid" name="roleid" required>
                    <option value="1" ${account.roleId == 1 ? 'selected' : ''}>Admin</option>
                    <option value="2" ${account.roleId == 2 ? 'selected' : ''}>Customer</option>
                    <option value="3" ${account.roleId == 3 ? 'selected' : ''}>Seller</option>
                </select>
            </div>
            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" value="${account.fullName}">
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber" value="${account.phoneNumber}">
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${account.address}">
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <input type="checkbox" id="status" name="status" value="true" ${account.status || empty account ? 'checked' : ''}>
            </div>
            <div style="text-align: center;">
                <button type="submit" class="btn-add">Thêm Người Dùng</button>
                <button type="button" class="btn-cancel" onclick="window.location.href='<%=request.getContextPath()%>/AdminServlet?activeTab=users'">Hủy</button>
            </div>
        </form>
    </div>
</body>
</html>