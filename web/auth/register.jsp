<%-- 
    Document   : register
    Created on : Feb 5, 2025, 5:37:59 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đăng ký tài khoản</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }

        h1 {
            text-align: center;
            color: #1877f2;
            margin-bottom: 1.5rem;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #606770;
        }

        .form-control {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #dddfe2;
            border-radius: 5px;
            font-size: 1rem;
        }

        .form-control:focus {
            outline: none;
            border-color: #1877f2;
            box-shadow: 0 0 0 2px #e7f3ff;
        }

        .btn {
            width: 100%;
            padding: 0.8rem;
            background: #1877f2;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 1rem;
        }

        .btn:hover {
            background: #166fe5;
        }

        .login-link {
            text-align: center;
            margin-top: 1.5rem;
        }

        .login-link a {
            color: #1877f2;
            text-decoration: none;
        }

        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Đăng ký tài khoản</h1>
        <c:if test="${requestScope.err!=null}">
            <h1 style="color: red">${requestScope.err}</h1>
        </c:if>
        <form action="<%=request.getContextPath()%>/registerc" method="post">
            <div class="form-group">
                <label>User Name</label>
                <input name="username" type="text" class="form-control" placeholder="Nhập tên đăng nhập" required>
            </div>

            <div class="form-group">
                <label>Họ và tên</label>
                <input name="fullname" type="text" class="form-control" placeholder="Nhập họ và tên đầy đủ" required>
            </div>

            <div class="form-group">
                <label>Số điện thoại</label>
                <input name="phonenum" type="tel" class="form-control" placeholder="Nhập số điện thoại" pattern="[0-9]{10}" required>
            </div>

            <div class="form-group">
                <label>Địa chỉ</label>
                <input name="address" type="text" class="form-control" placeholder="Nhập địa chỉ" required>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input name="email" type="email" class="form-control" placeholder="Nhập email" required>
            </div>
            
            <div class="form-group">
                <label>Mật khẩu</label>
                <input name="password" type="password" class="form-control" placeholder="Nhập mật khẩu" required>
            </div>
            
            <div class="form-group">
                <label>Xác nhận mật khẩu</label>
                <input name="confirmpassword" type="password" class="form-control" placeholder="Nhập lại mật khẩu" required>
            </div>
            
            <button type="submit" class="btn">Đăng ký</button>
        </form>
        
        <div class="login-link">
            Đã có tài khoản? <a href="login.jsp">Đăng nhập</a>
        </div>
        <!-- Phần mới thêm -->
        <p style="text-align: center; margin-top: 1rem; color: #606770;">
            Sau khi đăng ký thành công, bạn sẽ nhận được một voucher giảm giá đặc biệt qua email!
        </p>
    </div>
</body>
</html>