<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Book Details - ${book.title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.cdnfonts.com/css/ranade" rel="stylesheet">
        <style>
            /* Styles cho navbar */
            .navbar {
                position: fixed; /* Sử dụng fixed để navbar nằm trên cùng và full-width */
                top: 0;
                left: 0;
                width: 100%; /* Full-width */
                z-index: 1000;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                padding: 0 ;

            }
            .navbar .container {
                max-width: 100%; /* Giới hạn nội dung bên trong */
                margin: 0 auto; /* Căn giữa nội dung */
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .navbar-brand {
                font-size: 1.5rem;
                font-weight: bold;
                color: #d70018 !important;
                margin: 0;
            }
            .navbar .btn-outline-light {
                color: #d70018 !important;
                border-color: #d70018 !important;
            }
            .navbar .btn-outline-light:hover {
                background-color: #d70018 !important;
                color: #ffffff !important;
            }
            .account-dropdown {
                position: relative;
                display: inline-block;
            }
            .dropdown-menu {
                display: none;
                position: absolute;
                top: 100%;
                right: 0;
                background: white;
                border: 1px solid #ddd;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
                border-radius: 5px;
                padding: 10px;
                min-width: 150px;
            }
            .dropdown-menu a {
                display: block;
                padding: 10px;
                text-decoration: none;
                color: #d70018;
                font-weight: bold;
                text-align: center;
                border-radius: 5px;
            }
            .dropdown-menu a:hover {
                background-color: #d70018;
                color: white;
            }
            .account-dropdown:hover .dropdown-menu,
            .dropdown-menu:hover {
                display: block;
            }
            .search-form {
                display: flex;
                align-items: center;
                width: 100%;
                border: 2px solid #ff4500;
                border-radius: 15px;
                overflow: hidden;
            }
            .search-form input[type="text"] {
                flex-grow: 1;
                border: none;
                padding: 10px 15px;
                font-size: 16px;
                outline: none;
            }
            .search-form button {
                background-color: #ff4500;
                border: none;
                color: white;
                padding: 10px 15px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .search-form button:hover {
                background-color: #e63e00;
            }
            .search-form button::before {
                content: "🔍";
                font-size: 16px;
            }
            .navbar-actions {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            /* Styles cho trang detail */
            body {
                font-family: 'Ranade', sans-serif;
                color: #333333;
                font-size: 1rem;
                padding-top: 80px; /* Thêm padding-top để nội dung không bị che bởi navbar fixed */
            }
            .container {
                max-width: 1200px;
                margin: 40px auto;
                padding: 30px;
                background: #FFFFFF;
                border-radius: 20px;
                box-shadow: 0 10px 25px rgba(109, 76, 65, 0.2);
            }
            .featured-title {
                font-size: 2.5rem;
                text-align: center;
                margin-bottom: 20px;
                font-weight: 700;
                text-transform: uppercase;
                color: #6D4C41;
                text-shadow: 2px 2px 4px rgba(109, 76, 65, 0.1);
            }
            .book-image {
                border-radius: 15px;
                overflow: hidden;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .book-image img {
                width: 100%;
                height: auto;
                object-fit: cover;
                filter: brightness(1.1) saturate(1.2);
            }
            .book-image:hover {
                transform: scale(1.05);
                box-shadow: 0 8px 20px rgba(109, 76, 65, 0.5);
            }
            .card-body {
                padding: 25px;
            }
            .price {
                color: #6D4C41;
                font-size: 1.8rem;
                font-weight: 700;
                margin-bottom: 15px;
            }
            .discount-price {
                color: #FF9999;
                font-size: 1.1rem;
                text-decoration: line-through;
                margin-left: 10px;
            }
            .details-section {
                background: #F5F5F5;
                border-radius: 15px;
                padding: 25px;
                margin-top: 25px;
                box-shadow: 0 0 0 1px rgba(109, 76, 65, 0.3) inset;
            }
            .details-section h4 {
                color: #6D4C41;
                margin-bottom: 20px;
                font-weight: 700;
                font-size: 1.3rem;
            }
            .btn-buy, .back-btn {
                background: #6D4C41;
                color: #FFFFFF;
                border: none;
                padding: 12px 0;
                font-size: 0.9rem;
                border-radius: 30px;
                transition: all 0.3s ease;
                width: 200px;
                margin: 0 10px;
                display: inline-block;
                box-shadow: 0 4px 10px rgba(109, 76, 65, 0.3);
                text-align: center;
            }
            .btn-buy:hover, .back-btn:hover {
                background: #4A2F27;
                transform: translateY(-5px);
                box-shadow: 0 8px 20px rgba(74, 47, 39, 0.6);
            }
            .no-book {
                text-align: center;
                color: #6D4C41;
                font-size: 1.5rem;
                padding: 25px;
            }
            .description-section {
                background: #F5F5F5;
                border-radius: 15px;
                padding: 25px;
                margin-top: 30px;
                box-shadow: 0 0 0 1px rgba(109, 76, 65, 0.3) inset;
            }
            .description-section h4 {
                color: #6D4C41;
                margin-bottom: 20px;
                font-weight: 700;
                font-size: 1.3rem;
            }
            .button-section {
                margin-top: 25px;
                text-align: center;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .quantity-section {
                margin-top: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .quantity-section .quantity-control {
                display: flex;
                align-items: center;
                background: #FFFFFF;
                border-radius: 30px;
                box-shadow: 0 0 0 2px #6D4C41;
                padding: 5px;
            }
            .quantity-section .quantity-btn {
                background: #6D4C41;
                color: #FFFFFF;
                border: none;
                padding: 8px 15px;
                font-size: 1rem;
                cursor: pointer;
                transition: background 0.3s ease;
                border-radius: 25px;
            }
            .quantity-section .quantity-btn:hover {
                background: #4A2F27;
                transform: translateY(-3px);
                box-shadow: 0 4px 10px rgba(74, 47, 39, 0.4);
            }
            .quantity-section input {
                width: 70px;
                padding: 8px;
                border: none;
                background: transparent;
                color: #333333;
                text-align: center;
                font-size: 1rem;
                font-family: 'Ranade', sans-serif;
                outline: none;
            }
            .review-section {
                background: #F5F5F5;
                border-radius: 15px;
                padding: 25px;
                margin-top: 30px;
                box-shadow: 0 0 0 1px rgba(109, 76, 65, 0.3) inset;
            }
            .review-section h4 {
                color: #6D4C41;
                margin-bottom: 20px;
                font-weight: 700;
                font-size: 1.3rem;
            }
            .review-item {
                box-shadow: 0 1px 0 rgba(109, 76, 65, 0.2);
                padding: 15px 0;
            }
            .review-item:last-child {
                box-shadow: none;
            }
            .review-item p {
                margin-bottom: 8px;
            }
            .rating-stars {
                color: #6D4C41;
                font-size: 1.2rem;
            }
            .review-form {
                margin-top: 30px;
            }
            .review-form textarea {
                width: 100%;
                padding: 10px;
                border-radius: 10px;
                box-shadow: 0 0 0 2px #6D4C41 inset;
                font-family: 'Ranade', sans-serif;
                resize: vertical;
                border: none;
            }
            .review-form .btn-submit {
                background: #6D4C41;
                color: #FFFFFF;
                border: none;
                padding: 10px 20px;
                border-radius: 30px;
                transition: all 0.3s ease;
                box-shadow: 0 4px 10px rgba(109, 76, 65, 0.3);
            }
            .review-form .btn-submit:hover {
                background: #4A2F27;
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(74, 47, 39, 0.4);
            }
            .success-message {
                color: #6D4C41;
                font-size: 1.1rem;
                text-align: center;
                margin-bottom: 20px;
            }
            .back-button {
                position: fixed;
                top: 20px;
                left: 20px;
                display: inline-block;
                padding: 10px 15px;
                background: #dc3545;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: background 0.3s ease;
                z-index: 1001; /* Đặt z-index cao hơn navbar */
            }
            .back-button:hover {
                background: #c82333;
            }

            /* Feedback Styles (Đánh giá cảm xúc) */
            .feedback {
                --normal: #FFFFFF;
                --normal-shadow: #90A4AE;
                --normal-shadow-top: #CFD8DC;
                --normal-mouth: #B0BEC5;
                --normal-eye: #B0BEC5;
                --active: #FFD700;
                --active-shadow: #FFC107;
                --active-shadow-top: #FFF9C4;
                --active-mouth: #FF5722;
                --active-eye: #F5F5F5;
                --active-tear: #76b5e7;
                --active-shadow-angry: #D32F2F;
                --hover: #FFD700;
                --hover-shadow-top: #A1887F;
                margin: 0 auto;
                padding: 0;
                list-style: none;
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .feedback label {
                position: relative;
                transition: transform 0.3s;
                cursor: pointer;
            }
            .feedback label:not(:last-child) {
                margin-right: 20px;
            }
            .feedback label input {
                appearance: none;
                outline: none;
                border: none;
                display: block;
                position: absolute;
                width: 40px;
                height: 40px;
                left: 0;
                top: 0;
                margin: 0;
                padding: 0;
                border-radius: 50%;
                background: var(--sb, var(--normal));
                box-shadow: inset 3px -3px 4px var(--sh, var(--normal-shadow)),
                    inset -1px 1px 2px var(--sht, var(--normal-shadow-top));
                transition: background 0.4s, box-shadow 0.4s, transform 0.3s;
                -webkit-tap-highlight-color: transparent;
            }
            .feedback label div {
                width: 40px;
                height: 40px;
                position: relative;
                transform: perspective(240px) translateZ(4px);
            }
            .feedback label div svg,
            .feedback label div:before,
            .feedback label div:after {
                display: block;
                position: absolute;
                left: var(--l, 9px);
                top: var(--t, 13px);
                width: var(--w, 8px);
                height: var(--h, 1px);
                transform: rotate(var(--r, 0deg)) scale(var(--sc, 1)) translateZ(0);
            }
            .feedback label div svg {
                fill: none;
                stroke: var(--s);
                stroke-width: 1.5px;
                stroke-linecap: round;
                stroke-linejoin: round;
                transition: stroke 0.4s;
            }
            .feedback label div svg.eye {
                --s: var(--e, var(--normal-eye));
                --t: 17px;
                --w: 7px;
                --h: 4px;
            }
            .feedback label div svg.eye.right {
                --l: 23px;
            }
            .feedback label div svg.mouth {
                --s: var(--m, var(--normal-mouth));
                --l: 11px;
                --t: 23px;
                --w: 18px;
                --h: 7px;
            }
            .feedback label div:before,
            .feedback label div:after {
                content: "";
                z-index: var(--zi, 1);
                border-radius: var(--br, 1px);
                background: var(--b, var(--e, var(--normal-eye)));
                transition: background 0.4s;
            }
            .feedback label.angry {
                --step-1-rx: -24deg;
                --step-1-ry: 20deg;
                --step-2-rx: -24deg;
                --step-2-ry: -20deg;
            }
            .feedback label.angry div:before {
                --r: 20deg;
            }
            .feedback label.angry div:after {
                --l: 23px;
                --r: -20deg;
            }
            .feedback label.angry div svg.eye {
                stroke-dasharray: 4.55;
                stroke-dashoffset: 8.15;
            }
            .feedback label.angry input:checked {
                animation: angry 1s linear;
            }
            .feedback label.angry input:checked + div:before {
                --middle-y: -2px;
                --middle-r: 22deg;
                animation: toggle 0.8s linear forwards;
            }
            .feedback label.angry input:checked + div:after {
                --middle-y: 1px;
                --middle-r: -18deg;
                animation: toggle 0.8s linear forwards;
            }
            .feedback label.sad {
                --step-1-rx: 20deg;
                --step-1-ry: -12deg;
                --step-2-rx: -18deg;
                --step-2-ry: 14deg;
            }
            .feedback label.sad div:before,
            .feedback label.sad div:after {
                --b: var(--active-tear);
                --sc: 0;
                --w: 5px;
                --h: 5px;
                --t: 15px;
                --br: 50%;
            }
            .feedback label.sad div:after {
                --l: 25px;
            }
            .feedback label.sad div svg.eye {
                --t: 16px;
            }
            .feedback label.sad div svg.mouth {
                --t: 24px;
                stroke-dasharray: 9.5;
                stroke-dashoffset: 33.25;
            }
            .feedback label.sad input:checked + div:before,
            .feedback label.sad input:checked + div:after {
                animation: tear 0.6s linear forwards;
            }
            .feedback label.ok {
                --step-1-rx: 4deg;
                --step-1-ry: -22deg;
                --step-1-rz: 6deg;
                --step-2-rx: 4deg;
                --step-2-ry: 22deg;
                --step-2-rz: -6deg;
            }
            .feedback label.ok div:before {
                --l: 12px;
                --t: 17px;
                --h: 4px;
                --w: 4px;
                --br: 50%;
                box-shadow: 12px 0 0 var(--e, var(--normal-eye));
            }
            .feedback label.ok div:after {
                --l: 13px;
                --t: 26px;
                --w: 14px;
                --h: 2px;
                --br: 1px;
                --b: var(--m, var(--normal-mouth));
            }
            .feedback label.ok input:checked + div:before {
                --middle-s-y: 0.35;
                animation: toggle 0.2s linear forwards;
            }
            .feedback label.ok input:checked + div:after {
                --middle-s-x: 0.5;
                animation: toggle 0.7s linear forwards;
            }
            .feedback label.good {
                --step-1-rx: -14deg;
                --step-1-rz: 10deg;
                --step-2-rx: 10deg;
                --step-2-rz: -8deg;
            }
            .feedback label.good div:before {
                --b: var(--m, var(--normal-mouth));
                --w: 5px;
                --h: 5px;
                --br: 50%;
                --t: 22px;
                --zi: 0;
                opacity: 0.5;
                box-shadow: 16px 0 0 var(--b);
                filter: blur(2px);
            }
            .feedback label.good div:after {
                --sc: 0;
            }
            .feedback label.good div svg.eye {
                --t: 15px;
                --sc: -1;
                stroke-dasharray: 4.55;
                stroke-dashoffset: 8.15;
            }
            .feedback label.good div svg.mouth {
                --t: 22px;
                --sc: -1;
                stroke-dasharray: 13.3;
                stroke-dashoffset: 23.75;
            }
            .feedback label.good input:checked + div svg.mouth {
                --middle-y: 1px;
                --middle-s: -1;
                animation: toggle 0.8s linear forwards;
            }
            .feedback label.happy {
                --step-1-rx: 18deg;
                --step-1-ry: 24deg;
                --step-2-rx: 18deg;
                --step-2-ry: -24deg;
            }
            .feedback label.happy div:before {
                --sc: 0;
            }
            .feedback label.happy div:after {
                --b: var(--m, var(--normal-mouth));
                --l: 11px;
                --t: 23px;
                --w: 18px;
                --h: 8px;
                --br: 0 0 8px 8px;
            }
            .feedback label.happy div svg.eye {
                --t: 14px;
                --sc: -1;
            }
            .feedback label.happy input:checked + div:after {
                --middle-s-x: 0.95;
                --middle-s-y: 0.75;
                animation: toggle 0.8s linear forwards;
            }
            .feedback label input:checked {
                --sb: var(--active);
                --sh: var(--active-shadow);
                --sht: var(--active-shadow-top);
            }
            .feedback label input:checked + div {
                --m: var(--active-mouth);
                --e: var(--active-eye);
                animation: shake 0.8s linear forwards;
            }
            .feedback label input:not(:checked):hover {
                --sb: var(--hover);
                --sht: var(--hover-shadow-top);
            }
            .feedback label input:not(:checked):active {
                transform: scale(0.925);
            }
            .feedback label input:not(:checked):active + div {
                transform: scale(0.925);
            }
            .feedback label:hover {
                transform: scale(1.08);
            }

            @keyframes shake {
                30% {
                    transform: perspective(240px) rotateX(var(--step-1-rx, 0deg))
                        rotateY(var(--step-1-ry, 0deg)) rotateZ(var(--step-1-rz, 0deg))
                        translateZ(10px);
                }
                60% {
                    transform: perspective(240px) rotateX(var(--step-2-rx, 0deg))
                        rotateY(var(--step-2-ry, 0deg)) rotateZ(var(--step-2-rz, 0deg))
                        translateZ(10px);
                }
                100% {
                    transform: perspective(240px) translateZ(4px);
                }
            }
            @keyframes tear {
                0% {
                    opacity: 0;
                    transform: translateY(-2px) scale(0) translateZ(0);
                }
                50% {
                    transform: translateY(12px) scale(0.6, 1.2) translateZ(0);
                }
                20%, 80% {
                    opacity: 1;
                }
                100% {
                    opacity: 0;
                    transform: translateY(24px) translateX(4px) rotateZ(-30deg) scale(0.7, 1.1) translateZ(0);
                }
            }
            @keyframes toggle {
                50% {
                    transform: translateY(var(--middle-y, 0))
                        scale(var(--middle-s-x, var(--middle-s, 1)), var(--middle-s-y, var(--middle-s, 1)))
                        rotate(var(--middle-r, 0deg));
                }
            }
            @keyframes angry {
                40% {
                    background: var(--active);
                }
                45% {
                    box-shadow: inset 3px -3px 4px var(--active-shadow),
                        inset 0 8px 10px var(--active-shadow-angry);
                }
            }

            /* Related Books Section */
            .related-books-section {
                background: #F5F5F5;
                border-radius: 15px;
                padding: 25px;
                margin-top: 30px;
                box-shadow: 0 0 0 1px rgba(109, 76, 65, 0.3) inset;
            }
            .related-books-section h4 {
                color: #6D4C41;
                margin-bottom: 20px;
                font-weight: 700;
                font-size: 1.3rem;
            }
            .related-book-item {
                background: #FFFFFF;
                border-radius: 10px;
                padding: 15px;
                margin-bottom: 15px;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                box-shadow: 0 2px 5px rgba(109, 76, 65, 0.1);
            }
            .related-book-item:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(109, 76, 65, 0.3);
            }
            .related-book-item img {
                width: 80px;
                height: 120px;
                object-fit: cover;
                border-radius: 5px;
                margin-right: 15px;
            }
            .related-book-item h5 {
                color: #6D4C41;
                font-size: 1.1rem;
                margin-bottom: 5px;
                font-weight: 600;
            }
            .related-book-item p {
                color: #333333;
                font-size: 0.9rem;
                margin-bottom: 5px;
            }
            .related-book-item .price {
                color: #6D4C41;
                font-size: 1rem;
                font-weight: 700;
            }
            .rating-summary {
                margin-top: 5px;
                font-size: 1rem;
                color: #6D4C41;
            }
            .rating-summary .stars {
                margin-right: 10px;
            }
            .rating-summary .count {
                font-style: italic;
            }
            .shipping-section, .service-section {
                margin-bottom: 15px;
            }
            .shipping-section h5, .service-section h5 {
                color: #6D4C41;
                font-size: 1.1rem;
                font-weight: 600;
                margin-bottom: 8px;
                display: flex;
                align-items: center;
            }
            .shipping-section h5::before, .service-section h5::before {
                content: '';
                display: inline-block;
                width: 20px;
                height: 20px;
                margin-right: 8px;
                background-size: contain;
                background-repeat: no-repeat;
            }
            .shipping-section h5::before {
                background-image: url('https://cdn-icons-png.flaticon.com/512/3081/3081840.png');
            }
            .service-section h5::before {
                background-image: url('https://cdn-icons-png.flaticon.com/512/2921/2921188.png');
            }
            .shipping-section p, .service-section p {
                font-size: 0.95rem;
                color: #333333;
                margin: 0;
            }
            .shipping-section .voucher {
                color: #FF5555;
                font-style: italic;
            }
            .service-section .commitments {
                line-height: 1.4;
            }

            /* Pagination Styles */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }
            .pagination a {
                color: #6D4C41;
                padding: 8px 16px;
                text-decoration: none;
                border: 1px solid #ddd;
                margin: 0 4px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }
            .pagination a:hover {
                background-color: #6D4C41;
                color: #FFFFFF;
            }
            .pagination a.active {
                background-color: #6D4C41;
                color: #FFFFFF;
                border: 1px solid #6D4C41;
            }
            .pagination a.disabled {
                color: #ccc;
                pointer-events: none;
            }
        </style>
    </head>
    <body>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg">
            <div class="container">
                <!-- Logo -->
                <a class="navbar-brand" href="<%=request.getContextPath()%>/home/home.jsp">
                    <img src="<%= request.getContextPath() %>/images/logo.jpg" alt="Logo" style="height: 50px;">
                </a>

                <!-- Search -->
                <div class="input-group w-50">
                    <form class="search-form" action="<%=request.getContextPath()%>/bookc" method="GET">
                        <input hidden value="LoadMore" name="action"> 
                        <input required name="searchKeyword" type="text" class="form-control" placeholder="Tìm kiếm sách, truyện, văn phòng phẩm...">
                        <button type="submit"></button>
                    </form>
                </div>

                <!-- Account and Cart -->
                <div class="navbar-actions">
                    <% if (session.getAttribute("username") == null) { %>
                    <div class="account-dropdown">
                        <button class="btn btn-outline-light me-2">👤</button>
                        <div class="dropdown-menu">
                            <a href="<%=request.getContextPath()%>/login?action=login">Đăng nhập</a>
                            <a href="<%= request.getContextPath()%>/auth/register.jsp">Đăng ký</a>
                        </div>
                    </div>
                    <% } else { %>
                    <span class="text-black me-2">👤 Xin chào, ${fullname}</span>
                    <a href="<%= request.getContextPath() %>/logout" class="btn btn-outline-light">Đăng xuất</a>
                    <% } %>
                    <a href="<%= request.getContextPath() %>/cart">  
                        <button class="btn btn-outline-light">🛒</button>
                    </a>
                </div>
            </div>
        </nav>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let accountDropdown = document.querySelector(".account-dropdown");
                let dropdownMenu = document.querySelector(".dropdown-menu");

                accountDropdown.addEventListener("mouseenter", function () {
                    dropdownMenu.style.display = "block";
                });

                accountDropdown.addEventListener("mouseleave", function (event) {
                    if (!dropdownMenu.contains(event.relatedTarget)) {
                        dropdownMenu.style.display = "none";
                    }
                });

                dropdownMenu.addEventListener("mouseenter", function () {
                    dropdownMenu.style.display = "block";
                });

                dropdownMenu.addEventListener("mouseleave", function () {
                    dropdownMenu.style.display = "none";
                });
            });
        </script>


        <div class="container">
            <div class="row" id="bookDetail">
                <c:if test="${not empty book}">
                    <div class="col-md-4">
                        <div class="book-image">
                            <c:if test="${not empty book.imgUrl}">
                                <img src="${book.imgUrl}" alt="${book.title}">
                            </c:if>
                            <c:if test="${empty book.imgUrl}">
                                <img src="path/to/default-image.jpg" alt="No Image">
                            </c:if>
                        </div>
                    </div>
                    <div class="col-md-8 book-info">
                        <h3 class="mb-3" style="color: #6D4C41; font-weight: 700; font-size: 1.8rem;">${book.title}</h3>
                        <div class="rating-summary">
                            <span class="stars">
                                <c:forEach begin="1" end="5" var="i">
                                    <c:choose>
                                        <c:when test="${i <= averageRating + 1}">★</c:when>
                                        <c:otherwise>☆</c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </span>
                            <span class="count">(${reviewCount} người đánh giá)</span>
                        </div>
                        <div class="price">
                            ${book.price}đ 
                            <span class="discount-price">
                                <c:set var="discountPrice" value="${book.price * 0.9}" />
                                ${discountPrice}đ
                            </span>
                        </div>
                        <div class="shipping-section">
                            <h5>Vận chuyển</h5>
                            <p>
                                Nhận từ 
                                <%
                                    Date today = new Date();
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(today);
                                    cal.add(Calendar.DATE, 3);
                                    Date minShippingDate = cal.getTime();
                                    cal.add(Calendar.DATE, 2);
                                    Date maxShippingDate = cal.getTime();
                                %>
                                <fmt:formatDate value="<%=minShippingDate%>" pattern="dd MMM"/> - 
                                <fmt:formatDate value="<%=maxShippingDate%>" pattern="dd MMM"/>, phí giao ₫0
                            </p>
                            <p class="voucher">Tặng Voucher ₫15.000 nếu đơn giao sau thời gian trên.</p>
                        </div>
                        <div class="service-section">
                            <h5>An tâm mua sắm cùng Booksale</h5>
                            <p class="commitments">Xử lý đơn hàng bởi BOOKSALE · Trả hàng miễn phí 15 ngày · Chính hãng 100% · Miễn phí vận chuyển · Bảo hiểm bảo vệ người tiêu dùng</p>
                        </div>
                        <div class="col align-items-center mt-3">
                            <div class="col-auto">
                                <div class="quantity-section">
                                    <div class="quantity-control">
                                        <button class="quantity-btn" onclick="decreaseQuantity()">-</button>
                                        <input type="number" id="quantity" class="quantity-input" name="quantity" min="1" max="${book.stock}" value="1" readonly>
                                        <button class="quantity-btn" onclick="increaseQuantity()">+</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-auto">
                                <div class="button-section">
                                    <!-- Form Thêm vào giỏ hàng -->
                                    <form action="<%=request.getContextPath()%>/cart" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="bookId" value="${book.id}">
                                        <input type="hidden" name="quantity" id="formQuantity" value="1">
                                        <input type="hidden" name="redirect" value="detail"> <!-- Thêm tham số redirect -->
                                        <button type="submit" class="btn-buy">Thêm vào giỏ hàng</button>
                                    </form>
                                    <!-- Form Mua ngay -->
                                    <form action="<%=request.getContextPath()%>/cart" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="buyNow">
                                        <input type="hidden" name="bookId" value="${book.id}">
                                        <input type="hidden" name="quantity" id="buyNowQuantity" value="1">
                                        <input type="hidden" name="redirect" value="cart"> <!-- Thêm tham số redirect -->
                                        <button type="submit" class="btn-buy" style="background: #FF5555;">Mua ngay</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty book}">
                    <div class="col-12 no-book">
                        Không tìm thấy sách!
                    </div>
                </c:if>
            </div>
            <div class="details-section">
                <h4>Thông tin chi tiết</h4>
                <p><strong>Tác giả:</strong> ${book.author}</p>
                <p><strong>Ngày xuất bản:</strong> <fmt:formatDate value="${book.publishDate}" pattern="dd/MM/yyyy" /></p>
                <p><strong>Giá gốc:</strong> ${book.price}đ</p>
                <p><strong>Số lượng tồn:</strong> ${book.stock}</p>
                <p><strong>Trạng thái:</strong> <c:choose>
                        <c:when test="${book.status}">Còn Hàng</c:when>
                        <c:otherwise>Hết Hàng</c:otherwise>
                    </c:choose></p>
                <p><strong>Thể loại:</strong> ${book.categoryName}</p>
            </div>
            <div class="description-section">
                <h4>Mô tả sản phẩm</h4>
                <p>${book.description}</p>
            </div>

            <!-- Phần review -->
            <div class="review-section">
                <h4>Đánh giá và Nhận xét</h4>
                <c:if test="${not empty message}">
                    <p class="success-message">${message}</p>
                </c:if>

                <!-- Tùy chọn số lượng review mỗi trang -->
                <div class="mb-3">
                    <form action="<%=request.getContextPath()%>/BookDetailController" method="get" class="d-flex align-items-center">
                        <input type="hidden" name="bookId" value="${book.id}">
                        <label for="pageSize" class="me-2">Số lượng review mỗi trang:</label>
                        <select name="pageSize" id="pageSize" class="form-select w-auto" onchange="this.form.submit()">
                            <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                            <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                            <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                        </select>
                    </form>
                </div>

                <c:if test="${not empty reviews}">
                    <c:forEach var="review" items="${reviews}">
                        <div class="review-item">
                            <p><strong>Tên người đánh giá:</strong> ${review.fullname}</p>
                            <p class="rating-stars">
                                <c:forEach begin="1" end="${review.rating}">★</c:forEach>
                                <c:forEach begin="${review.rating + 1}" end="5">☆</c:forEach>
                                </p>
                                <p><strong>Nhận xét:</strong> ${review.comment}</p>
                            <p><small><fmt:formatDate value="${review.reviewDate}" pattern="dd/MM/yyyy HH:mm" /></small></p>
                        </div>
                    </c:forEach>

                    <!-- Phân trang -->
                    <nav aria-label="Review navigation">
                        <ul class="pagination justify-content-center mt-4">
                            <!-- Nút Previous -->
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="<%=request.getContextPath()%>/BookDetailController?bookId=${book.id}&page=${currentPage - 1}&pageSize=${pageSize}">Trước</a>
                                </li>
                            </c:if>
                            <c:if test="${currentPage <= 1}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">Trước</a>
                                </li>
                            </c:if>

                            <!-- Các số trang -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="<%=request.getContextPath()%>/BookDetailController?bookId=${book.id}&page=${i}&pageSize=${pageSize}">${i}</a>
                                </li>
                            </c:forEach>

                            <!-- Nút Next -->
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="<%=request.getContextPath()%>/BookDetailController?bookId=${book.id}&page=${currentPage + 1}&pageSize=${pageSize}">Tiếp</a>
                                </li>
                            </c:if>
                            <c:if test="${currentPage >= totalPages}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">Tiếp</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
                <c:if test="${empty reviews}">
                    <p>Chưa có đánh giá nào cho sách này.</p>
                </c:if>

                <!-- Form thêm đánh giá -->
                <div class="review-form">
                    <h5>Thêm đánh giá của bạn</h5>
                    <form action="<%=request.getContextPath()%>/BookReviewController" method="post">
                        <input type="hidden" name="bookId" value="${book.id}">
                        <input type="hidden" name="userId" value="<%= session.getAttribute("userId") != null ? session.getAttribute("userId") : 1 %>">
                        <input type="hidden" name="page" value="${currentPage}">
                        <input type="hidden" name="pageSize" value="${pageSize}">
                        <div class="feedback">
                            <label class="angry">
                                <input type="radio" value="1" name="rating" />
                                <div>
                                    <svg class="eye left"><use xlink:href="#eye"></use></svg>
                                    <svg class="eye right"><use xlink:href="#eye"></use></svg>
                                    <svg class="mouth"><use xlink:href="#mouth"></use></svg>
                                </div>
                            </label>
                            <label class="sad">
                                <input type="radio" value="2" name="rating" />
                                <div>
                                    <svg class="eye left"><use xlink:href="#eye"></use></svg>
                                    <svg class="eye right"><use xlink:href="#eye"></use></svg>
                                    <svg class="mouth"><use xlink:href="#mouth"></use></svg>
                                </div>
                            </label>
                            <label class="ok">
                                <input type="radio" value="3" name="rating" />
                                <div></div>
                            </label>
                            <label class="good">
                                <input type="radio" value="4" name="rating" checked />
                                <div>
                                    <svg class="eye left"><use xlink:href="#eye"></use></svg>
                                    <svg class="eye right"><use xlink:href="#eye"></use></svg>
                                    <svg class="mouth"><use xlink:href="#mouth"></use></svg>
                                </div>
                            </label>
                            <label class="happy">
                                <input type="radio" value="5" name="rating" />
                                <div>
                                    <svg class="eye left"><use xlink:href="#eye"></use></svg>
                                    <svg class="eye right"><use xlink:href="#eye"></use></svg>
                                </div>
                            </label>
                        </div>
                        <textarea name="comment" rows="4" placeholder="Nhập nhận xét của bạn..." required></textarea>
                        <button type="submit" class="btn-submit">Gửi đánh giá</button>
                    </form>
                </div>
            </div>  
            <div class="related-books-section">
                <h4>Sách cùng thể loại</h4>
                <c:if test="${not empty relatedBooks}">
                    <c:forEach var="relatedBook" items="${relatedBooks}">
                        <c:if test="${relatedBook.id != book.id}">
                            <a href="<%=request.getContextPath()%>/BookDetailController?bookId=${relatedBook.id}" style="text-decoration: none; color: inherit;">
                                <div class="related-book-item d-flex">
                                    <c:if test="${not empty relatedBook.imgUrl}">
                                        <img src="${relatedBook.imgUrl}" alt="${relatedBook.title}">
                                    </c:if>
                                    <c:if test="${empty relatedBook.imgUrl}">
                                        <img src="path/to/default-image.jpg" alt="No Image">
                                    </c:if>
                                    <div>
                                        <h5>${relatedBook.title}</h5>
                                        <p><strong>Tác giả:</strong> ${relatedBook.author}</p>
                                        <p class="price">${relatedBook.price}đ</p>
                                    </div>
                                </div>
                            </a>
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${empty relatedBooks}">
                    <p>Không có sách nào cùng thể loại.</p>
                </c:if>
            </div>
        </div>

        <!-- Định nghĩa SVG cho mắt và miệng -->
        <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol xmlns="http://www.w3.org/2000/svg" viewBox="0 0 7 4" id="eye">
        <path d="M1,1 C1.83333333,2.16666667 2.66666667,2.75 3.5,2.75 C4.33333333,2.75 5.16666667,2.16666667 6,1"></path>
    </symbol>
    <symbol xmlns="http://www.w3.org/2000/svg" viewBox="0 0 18 7" id="mouth">
        <path d="M1,5.5 C3.66666667,2.5 6.33333333,1 9,1 C11.6666667,1 14.3333333,2.5 17,5.5"></path>
    </symbol>
    </svg>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
                        function increaseQuantity() {
                            const input = document.getElementById('quantity');
                            const max = parseInt(input.max) || Infinity;
                            let value = parseInt(input.value) || 1;
                            if (value < max) {
                                input.value = value + 1;
                                document.getElementById('formQuantity').value = input.value;
                                document.getElementById('buyNowQuantity').value = input.value;
                            } else {
                                alert("Số lượng đã đạt tối đa tồn kho!");
                            }
                        }

                        function decreaseQuantity() {
                            const input = document.getElementById('quantity');
                            let value = parseInt(input.value) || 1;
                            if (value > 1) {
                                input.value = value - 1;
                                document.getElementById('formQuantity').value = input.value;
                                document.getElementById('buyNowQuantity').value = input.value;
                            } else {
                                alert("Số lượng tối thiểu là 1!");
                            }
                        }

                        function goBack() {
                            window.history.back();
                        }
    </script>
</body>
</html>