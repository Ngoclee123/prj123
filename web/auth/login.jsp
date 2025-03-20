<%-- 
    Document   : login
    Created on : Feb 5, 2025, 5:40:16 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page session="false" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
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

        .register-link {
            text-align: center;
            margin-top: 1.5rem;
        }

        .register-link a {
            color: #1877f2;
            text-decoration: none;
        }

        .register-link a:hover {
            text-decoration: underline;
        }

        .remember-me {
            margin-top: 10px;
            display: flex;
            gap: 12px;

        }
        .remember-me label{
            margin-top: 8px;
        }
        a{
            text-decoration: none;
        }
    </style>
</head>
<body>
    <% // session.invalidate(); %>
    <div class="container">



        <c:if test="${requestScope.err != null}">
            <h1 style="color: red" >${requestScope.err} </h1>
        </c:if>

        <% 
         String usernameCookieSaved="";
        String passwordCookieSaved="";
        Cookie[] cookieListFromBrowser = request.getCookies() ;
        if(cookieListFromBrowser!= null){
           for(Cookie c :cookieListFromBrowser){
            if(c.getName().equals("COOKIE_USERNAME")){
                usernameCookieSaved = c.getValue();
            }
            if(c.getName().equals("COOKIE_PASSWORD")){
                passwordCookieSaved = c.getValue();
            }
            }
            }
        %>

        <h1>Đăng nhập</h1>
        <form method="post" action="<%= request.getContextPath() %>/login">

            <div class="form-group">
                <label>Tên Đăng Nhập</label>
                <input value="<%= usernameCookieSaved %>" type="text" name="username" class="form-control" placeholder="Nhập ten Dang Nhap" required>
            </div>

            <div class="form-group">
                <label>Mật khẩu</label>
                <input value="<%= passwordCookieSaved%>" type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
            </div>

            <div class="remember-me">
                <input type="checkbox" name="rememberMe" id="rememberMe"  />
                <label for="rememberMe">Ghi nhớ tôi</label>
            </div>

            <button type="submit" class="btn">Đăng nhập</button>
        </form>
        <!-- Google Login Button -->
        <div style="text-align: center; margin-bottom: 1rem;">
           <a href="https://accounts.google.com/o/oauth2/auth?client_id=716284389562-ov61pkjpflib17g77ntrbdk14h1bjesq.apps.googleusercontent.com&redirect_uri=http://localhost:8080/BookStore/login&response_type=code&scope=email%20profile%20openid&approval_prompt=force">

                <button type="button" class="btn" style="background-color: #db4437; width: 100%; font-weight: normal;">
                    Đăng nhập với Google
                </button
            </a>
        </div>
        <div class="register-link">
            Chưa có tài khoản? <a href="<%=request.getContextPath()%>/auth/register.jsp">Đăng ký ngay</a>
        </div>
    </div>
</body>
</html>
