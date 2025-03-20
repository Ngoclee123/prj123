<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="bookDao" class="dal.bookDao.BookDAO" scope="page"/>
<%@page import="java.util.List, model.book.Book" %>

<%
    List<Book> allBooks = bookDao.selectAllBooks();
    int maxDisplay = 16;
    request.setAttribute("books", allBooks.size() > maxDisplay ? allBooks.subList(0, maxDisplay) : allBooks);
    request.setAttribute("hasMoreBooks", allBooks.size() > maxDisplay);
%>


<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FAHASA Clone</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>




            /*search*/

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
            /* Xem thêm */
            .load-more-container {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 20px;
            }

            .load-more-container button {
                background-color: white; /* White background */
                color: black; /* Dark text */
                border: 1px solid #d9d9d9; /* Light grey border */
                padding: 12px 30px; /* Adjust padding */
                font-size: 16px;
                font-weight: 500;
                cursor: pointer;
                border-radius: 5px; /* Slightly rounded corners */
                transition: all 0.3s ease-in-out;
            }

            .load-more-container button:hover {
                background-color: #f5f5f5; /* Light grey hover effect */
            }

            /* banner*/
            .banner {
                width: 100%;
            }
            .banner img {
                width: 100%;
            }
            .navbar {
                position: sticky;
                top: 0;
                z-index: 1000;
                background-color: #ffffff !important;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }
            .navbar-brand {
                font-size: 1.5rem;
                font-weight: bold;
                color: #d70018 !important;
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

            .slideshow-container {
                max-width: 1230px;
                margin: auto;
                overflow: hidden;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
                position: relative;
            }
            .carousel-indicators button {
                background-color: #d70018 !important;
            }
            .carousel-item img {
                width: 100%;
                border-radius: 10px;
            }
            .carousel-control-prev,
            .carousel-control-next {
                width: 50px;
                height: 50px;
                background-color: rgba(255, 255, 255, 0.9);
                border-radius: 50%;
                top: 50%;
                transform: translateY(-50%);
                opacity: 1;
                border: 2px solid #ccc;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3);
                display: flex;
                align-items: center;
                justify-content: center;
                position: absolute;
                z-index: 100;
                transition: all 0.3s ease-in-out;
            }
            .carousel-control-prev {
                left: 10px;
            }
            .carousel-control-next {
                right: 10px;
            }
            .carousel-control-prev:hover,
            .carousel-control-next:hover {
                background-color: #d70018;
                border: 2px solid #d70018;
            }
            .carousel-control-prev-icon,
            .carousel-control-next-icon {
                background-image: none;
                font-size: 24px;
                color: #333;
                font-weight: bold;

            }
            .carousel-control-prev:hover .carousel-control-prev-icon,
            .carousel-control-next:hover .carousel-control-next-icon {
                color: white;

            }

            /* duoi slideshow */
            .card {
                border: none;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                width: 90%;
                max-width: 250px;
                transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
                margin-bottom: 50px;

            }
            .card img {
                border-top-left-radius: 10px;
                border-top-right-radius: 10px;
                object-fit: cover;
                height: 290px;
                transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
                cursor: pointer;

            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.2);
                cursor: pointer;
            }
            .card:hover img {
                transform: scale(1.05);
                box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
            }
            .card-body {
                padding: 15px;
            }
            .card-title {
                font-size: 1rem;
                font-weight: bold;
                margin-bottom: 10px;
                height: 40px ;
            }
            /* phần sách nổi bật */
            .featured-books-section {
                text-align: center;
                padding: 20px 0;
                position: relative;
            }

            .featured-title {
                font-size: 28px;
                font-weight: bold;
                color: #222;
                position: relative;
                display: inline-block;
                transition: color 0.3s ease-in-out, text-shadow 0.3s ease-in-out;
            }

            .featured-title::before,
            .featured-title::after {
                content: "";
                position: absolute;
                top: 50%;
                width: 80px;
                height: 2px;
                background-color: #ddd;
            }

            .featured-title::before {
                left: -100px;
            }

            .featured-title::after {
                right: -100px;
            }

            /* Hiệu ứng khi di chuột vào */
            .featured-title:hover {
                color: #00ff5e86; /* Màu xanh neon */
                text-shadow: 0 0 0.1px #00ff5e6d, 0 0 0.1px #00ff5e8f, 0 0 0.1px #00ff5e89;
            }


            .featured-description {
                max-width: 700px;
                margin: 10px auto;
                font-size: 16px;
                color: #777;
                font-style: italic;
                border: 5px solid rgb(235, 235, 235);
                padding: 15px;
                border-radius: 1px;
            }


            /* hình thêm vào cho đẹp */
            .service-features {
                display: flex;
                justify-content: space-around;
                align-items: center;
                padding: 20px 0;
                border-top: 1px solid #ddd;
                border-bottom: 1px solid #ddd;
                max-width: 1000px;
                margin: 20px auto;
            }

            .feature {
                text-align: center;
                color: #555;
                font-size: 16px;
                flex: 1;
                max-width: 200px;
                transition: color 0.3s ease-in-out;
            }

            .feature img {
                width: 50px;
                height: 50px;
                margin-bottom: 10px;
                opacity: 0.7;
                transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out, filter 0.3s ease-in-out;
                cursor: pointer;
            }

            /* Hiệu ứng hover - phát sáng và đổi màu */
            .feature:hover img {
                transform: scale(1.1);
                opacity: 1;
                filter: drop-shadow(0px 0px 8px #0dff0066); /* Tạo hiệu ứng phát sáng */
            }

            .feature:hover {
                color: #00c853; /* Đổi màu chữ khi hover */
            }




            /* footer */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            .footer {
                background-color: #f26c21;
                color: white;
                text-align: center;
                padding: 20px 0;
                font-size: 16px;
                font-weight: bold;
                display: flex;
                justify-content: space-around;
                align-items: center;
                margin: 10px 0;
            }
            .footer div {
                display: flex;
                align-items: center;
                gap: 12px;
            }
            .footer img {
                width: 48px;
            }
            .footer-section {
                padding: 40px 10%;
                background: #ffffff;
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
            }
            .footer-section div {
                width: 20%;
            }
            .footer-section h3 {
                font-size: 16px;
                margin-bottom: 12px;
                font-weight: bold;
                color: #333;
            }
            .footer-section a {
                display: block;
                text-decoration: none;
                color: black;
                font-size: 14px;
                margin-bottom: 8px;
            }
            .newsletter input {
                width: 90%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            .newsletter button {
                background: #f26c21;
                color: white;
                border: none;
                padding: 10px 18px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                margin-top: 5px;
            }
            .logo {
                display: flex;
                align-items: center;
                gap: 12px;
            }
            .logo img {
                width: 200px;
            }
            .logo-text {
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            .logo-text h3 {
                margin: 0;
            }
            .logo-text span {
                font-size: 12px;
                color: #666;
                margin-top: 2px;
            }

            a{
                text-decoration: none;
            }
            .voucher-promotion {
                text-align: center;
                margin: 20px 0;
                padding: 15px;
                background-color: #fff3e0;
                border: 2px dashed #ff5722;
                border-radius: 10px;
            }
            .voucher-promotion p {
                margin: 0;
                font-size: 18px;
                color: #d70018;
                font-weight: bold;
            }
            .voucher-promotion a {
                color: #ff5722;
                text-decoration: underline;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <!-- Banner -->
        <div class="banner">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/home/home.jsp">
                <img src="<%= request.getContextPath() %>/bannner-logo/banner top.png">
            </a>
        </div>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg p-3">
            <div class="container">

                <!--home-->
                <a class="navbar-brand" href="<%=request.getContextPath()%>/home/home.jsp">
                    <img src="<%= request.getContextPath() %>/images/logo.jpg" alt="a" style="height: 50px;">
                </a>


                <!--search-->
                <div class="input-group w-50">
                    <form class="search-form" action="<%=request.getContextPath()%>/bookc" method="GET">
                        <input hidden="" value="LoadMore" name="action"> 
                        <input  required="" name="searchKeyword" type="text" class="form-control" placeholder="Tìm kiếm sách, truyện, văn phòng phẩm...">
                        <button class="" type="submit"></button>
                    </form>
                </div>

                <%-- đăng nhập/đăng ký --%>
                <div>
                    <%-- Kiểm tra nếu chưa đăng nhập thì hiển thị đăng nhập/đăng ký --%>
                    <% if (session.getAttribute("username") == null) { %>
                    <div class="account-dropdown">
                        <button class="btn btn-outline-light me-2">👤</button>
                        <div class="dropdown-menu">
                            <a href="<%=request.getContextPath()%>/login?action=login">Đăng nhập</a>
                            <a href="<%= request.getContextPath()%>/auth/register.jsp">Đăng ký</a>
                        </div>
                    </div>
                    <% } else { %>
                    <%-- Hiển thị thông tin người dùng nếu đã đăng nhập --%>
                    <span class="text-black me-2">👤 Xin chào,${fullname}</span>
                    <a href="<%= request.getContextPath() %>/logout" class="btn btn-outline-light">Đăng xuất</a>
                    <% } %>




                    <a href="<%= request.getContextPath() %>/cart">  <button class="btn btn-outline-light">🛒</button>
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

        <!-- Phần khuyến khích đăng ký nhận voucher -->
        <% if (session.getAttribute("username") == null) { %>
        <div class="voucher-promotion">
            <p>Đăng ký ngay hôm nay để nhận voucher giảm giá đặc biệt! 
                <a href="<%= request.getContextPath() %>/auth/register.jsp">Đăng ký ngay</a></p>
        </div>
        <% } %>


        <!-- Slideshow -->
        <div id="carouselExample" class="carousel slide slideshow-container mt-4" data-bs-ride="carousel" data-bs-interval="3000" data-bs-touch="true">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="2" aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="3" aria-label="Slide 4"></button>
                <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="4" aria-label="Slide 5"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="<%= request.getContextPath() %>/bannner-logo/banner 1.png" class="d-block w-100" alt="Slide 1">
                </div>
                <div class="carousel-item">
                    <img src="<%= request.getContextPath() %>/bannner-logo/banner 2.png" class="d-block w-100" alt="Slide 2">
                </div>
                <div class="carousel-item">
                    <img src="<%= request.getContextPath() %>/bannner-logo/banner 3.png" class="d-block w-100" alt="Slide 3">
                </div>
                <div class="carousel-item">
                    <img src="<%= request.getContextPath() %>/bannner-logo/banner 4.png" class="d-block w-100" alt="Slide 4">
                </div>
                <div class="carousel-item">
                    <img src="<%= request.getContextPath() %>/bannner-logo/banner 5.png" class="d-block w-100" alt="Slide 5">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                <span class="carousel-control-prev-icon">❮</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                <span class="carousel-control-next-icon">❯</span>
            </button>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!---->




        <!-- Danh sách sách từ Servlet -->
        <!-- Danh sách sách tối đa 8 sản phẩm -->
        <div class="container mt-4" style="max-width: 1230px;">
            <div class="featured-books-section">
                <h2 class="featured-title">DANH SÁCH SÁCH</h2>
            </div>
            <div class="row" id="bookList">
                <c:forEach var="book" items="${books}">
                    <div class="col-md-3">
                        <a href="<%=request.getContextPath()%>/BookDetailController?bookId=${book.id}">
                            <div class="card h-100">
                                <img src="${book.imgUrl}" class="card-img-top" alt="${book.title}">
                                <div class="card-body text-center">
                                    <h5 class="card-title">${book.title}</h5>
                                    <p class="text-danger">
                                        ${book.price}đ 
                                        <span class="text-muted text-decoration-line-through">${book.price * 0.9}đ</span>
                                    </p>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>


        <!-- Xem thêm-->


        <div class="load-more-container">  
            <form action="<%=request.getContextPath()%>/display" method="GET">
                <input value="LoadMore" hidden name="action">
                <button type="submit">Xem Thêm</button>
            </form>
        </div>





        <!--hình thêm vào cho đẹp  -->
        <div class="service-features">
            <div class="feature">
                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3Xa08XCPk-l3UOQYuuNZfmYeX1Cb8HrqVOnTw7mwIvkdceGt_kpjYiHRYx6KJXZJjLRI&usqp=CAU" alt="Giao hàng miễn phí">
                <p>Giao hàng miễn phí</p>
            </div>
            <div class="feature">
                <img src="https://png.pngtree.com/png-clipart/20191122/original/pngtree-cart-icon-isolated-on-abstract-background-png-image_5165752.jpg" alt="Đặt hàng nhanh chóng">
                <p>Đặt hàng nhanh chóng</p>
            </div>
            <div class="feature">
                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWTPvcJV9U5CLbIeAHkNQsG5lkWiKklmcHdqiESQ5lz0Hy5GTgaDpkSLqPruOTaLmTJq4&usqp=CAU" alt="Giá tiêu chuẩn">
                <p>Giá tiêu chuẩn</p>
            </div>
            <div class="feature">
                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3ZCwwgPWRyf71y0CfANkETSY9nS7HfCH2Xwc-WLa9sewNfwwr1YhFUCq2ONQbsyUYCmg&usqp=CAU" alt="Đổi trả trong 3 ngày">
                <p>Đổi trả trong 3 ngày</p>
            </div>
        </div>





        <!-- phần sách nổi bậc -->
        <div class="container mt-4" style="max-width: 1230px;">
            <div class="featured-books-section">
                <h2 class="featured-title">SÁCH NỔI BẬT</h2>
                <p class="featured-description">
                    Trong lịch sử nhân loại, hiếm thấy ai phát biểu quan điểm này về con người. Ngày nay, quan điểm này hầu như không tồn tại. Tuy nhiên, chính quan điểm này - dù tồn tại ở các cấp độ khác nhau.
                </p>
            </div>
            <div id="bookCarouselNew" class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">

                <div class="carousel-inner">
                    <c:set var="counter" value="0" />
                    <c:forEach var="book" items="${books}" varStatus="status" begin="2" end="5">
                        <c:if test="${status.index % 2 == 0}">
                            <div class="carousel-item ${status.index == 2 ? 'active' : ''}">
                                <div class="row">
                                </c:if>

                                <div class="col-md-6 d-flex">
                                    <img src="${book.imgUrl}" class="w-50" alt="${book.title}">
                                    <div class="ms-3">
                                        <h5>${book.title}</h5>
                                        <p class="text-danger">${book.price}đ   
                                            <span class="text-muted text-decoration-line-through">${book.price * 0.9}đ</span>
                                        </p>
                                        <p>${book.description}</p>
                                        <form action="<%=request.getContextPath()%>/cart?action=add" method="post">
                                            <input hidden="" type="number" name="quantity" value="1" min="1" />

                                            <input type="hidden" name="bookId" value="${book.id}" />
                                            <button type="submit" class="btn btn-success">🛒 Đặt hàng ngay</button>
                                        </form>
                                    </div>
                                </div>

                                <c:if test="${status.index % 2 == 1 || status.last}">
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>



                <button class="carousel-control-prev" type="button" data-bs-target="#bookCarouselNew" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon">❮</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#bookCarouselNew" data-bs-slide="next">
                    <span class="carousel-control-next-icon">❯</span>
                </button>
            </div>
        </div>


        <!--             phan goi ý       -->

        <c:if test="${not empty recommendBooks}">
            <div class="container mt-4" style="max-width: 1230px;">
                <div class="featured-books-section">
                    <h2 class="featured-title">GỢI Ý CHO BẠN</h2>
                    <p class="featured-description">
                        Dựa trên lịch sử xem của bạn, đây là những cuốn sách có thể bạn sẽ thích.
                    </p>
                </div>

                <div class="row">
                    <c:forEach var="book" items="${recommendBooks}" varStatus="status">
                        <c:if test="${status.index < 3}">
                            <div class="col-md-4 d-flex">
                                <img src="${book.imgUrl}" class="w-50" alt="${book.title}">
                                <div class="ms-3">
                                    <h5>${book.title}</h5>
                                    <p class="text-danger">${book.price}đ  
                                        <span class="text-muted text-decoration-line-through">${book.price * 0.9}đ</span>
                                    </p>
                    <!--                <p>${book.description}</p>-->

                                    <!-- Form gửi dữ liệu -->
                                    <form action="<%=request.getContextPath()%>/cart?action=add" method="post">
                                        <input hidden="" type="number" name="quantity" value="1" min="1" />

                                        <input type="hidden" name="bookId" value="${book.id}" />
                                        <button type="submit" class="btn btn-success">🛒 Đặt hàng ngay</button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>


                </div>
            </div>
        </c:if>






        <!-- Footer -->

        <div class="footer">
            <div><img src="https://pos.nvncdn.com/fd5775-40602/bn/20240229_VRTtNAFp.gif" alt="icon"> MIỄN PHÍ ĐỔI TRẢ NHANH CHÓNG</div>
            <div><img src="https://pos.nvncdn.com/fd5775-40602/bn/20240229_p6BxmJ26.gif" alt="icon"> 100% SÁCH CÓ BẢN QUYỀN</div>
            <div><img src="https://pos.nvncdn.com/fd5775-40602/bn/20240229_AWHeJxti.gif" alt="icon"> GIAO HÀNG TOÀN QUỐC</div>
            <div><img src="https://pos.nvncdn.com/fd5775-40602/bn/20240229_uFWE6qqs.gif" alt="icon"> QUÀ TẶNG CHO ĐƠN HÀNG***</div>
        </div>
        <div class="footer-section">
            <div>
                <div class="logo">
                    <img src="https://pos.nvncdn.com/fd5775-40602/store/20240220_CGdGAsiN.png" alt="AlphaBooks">

                </div>
                <a href="#">Bán hàng Online</a>
                <a href="#">Chăm sóc Khách Hàng</a>
            </div>
            <div>
                <h3>Hỗ trợ khách hàng</h3>
                <a href="#">Chính sách bảo mật</a>
                <a href="#">Hướng dẫn mua hàng</a>
                <a href="#">Chính sách đổi, trả</a>
                <a href="#">Chính sách khách sỉ</a>
            </div>
            <div>
                <h3>Liên hệ</h3>
                <a href="#">Hotline: 0932329959</a>
                <a href="#">Fb: m.me/nhasachAlphaBooks</a>
                <a href="#">Email: cskh@alphabooks.vn</a>
            </div>
            <div>
                <h3>Danh mục</h3>
                <a href="#">Sách tư duy - kĩ năng</a>
                <a href="#">Sách kinh tế</a>
                <a href="#">Sách lịch sử</a>
            </div>

        </div>
        <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
        intent="WELCOME"
        chat-title="Test_AI_chat"
        agent-id="75ccaead-30e2-45c1-9953-c4725ac235fd"
        language-code="vi"
        ></df-messenger>
    `

</body>
</html>

