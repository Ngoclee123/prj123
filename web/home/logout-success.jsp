<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="2;url=<%=request.getContextPath()%>/home/home.jsp">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>Đăng xuất thành công</title>
    <style>
        /* Reset cơ bản */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
        }

        /* Hiệu ứng background particles */
        .background-particles {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg"><circle cx="2" cy="2" r="1" fill="rgba(255,255,255,0.2)"/></svg>');
            background-size: 4px 4px;
            animation: float 15s infinite linear;
            opacity: 0.3;
        }

        .message {
            position: relative;
            background: rgba(255, 255, 255, 0.95);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 400px;
            width: 90%;
            animation: fadeInUp 0.8s ease-out;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .message h2 {
            color: #2ecc71;
            font-size: 32px;
            margin-bottom: 15px;
            text-transform: uppercase;
            letter-spacing: 1px;
            position: relative;
        }

        .message h2::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 50%;
            transform: translateX(-50%);
            width: 50px;
            height: 3px;
            background: linear-gradient(to right, #2ecc71, transparent);
            border-radius: 2px;
        }

        .message p {
            color: #555;
            font-size: 18px;
            line-height: 1.5;
        }

        /* Hiệu ứng checkmark */
        .checkmark {
            width: 60px;
            height: 60px;
            margin: 0 auto 20px;
            background: #2ecc71;
            border-radius: 50%;
            position: relative;
            animation: scaleIn 0.5s ease-out;
        }

        .checkmark::after {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 20px;
            height: 35px;
            border: solid white;
            border-width: 0 4px 4px 0;
            transform: translate(-50%, -60%) rotate(45deg);
            animation: check 0.4s ease-in-out forwards;
        }

        /* Keyframes cho animation */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes scaleIn {
            from {
                transform: scale(0);
            }
            to {
                transform: scale(1);
            }
        }

        @keyframes check {
            from {
                opacity: 0;
                height: 0;
            }
            to {
                opacity: 1;
                height: 35px;
            }
        }

        @keyframes float {
            0% { transform: translateY(0); }
            50% { transform: translateY(-20px); }
            100% { transform: translateY(0); }
        }

        /* Responsive */
        @media (max-width: 480px) {
            .message {
                padding: 30px;
            }
            .message h2 {
                font-size: 24px;
            }
            .message p {
                font-size: 16px;
            }
            .checkmark {
                width: 50px;
                height: 50px;
            }
        }
    </style>
</head>
<body>
    <div class="background-particles"></div>
    <div class="message">
        <div class="checkmark"></div>
        <h2>Đăng xuất thành công!</h2>
        <p>Bạn sẽ được chuyển hướng về trang chủ sau 2 giây...</p>
        <% System.out.println("Redirecting to home.jsp after logout success."); %>
    </div>
</body>
</html>