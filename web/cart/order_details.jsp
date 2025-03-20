<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="bookDao" class="dal.bookDao.BookDAO" scope="page"/>
<%@page import="java.util.List, model.book.Book" %>

<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lịch Sử Đơn Hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* Title Styling */
            h2 {
                text-align: center;
                font-size: 2rem;
                font-weight: bold;
                color: #d70018;
                margin-bottom: 20px;
            }



            /* Order Container */
            .order-container {
                margin: 20px;
                padding: 20px;
                background-color: #f5f5f5; /* Light gray background */
                border-radius: 10px;
                box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2); /* Thicker and darker shadow */
                /*                border: 1px solid #ddd;  Subtle border for definition */
                animation: bounce 0.5s ease-in-out; /* Adding bounce animation */
            }
            /* Table styling for consistency */
            .order-container table {
                width: 100%;
                border-collapse: collapse; /* Ensure no spacing between table cells */
            }

            .order-container th, .order-container td {
                padding: 12px;
                text-align: center; /* Align text to center for all columns */
                vertical-align: middle; /* Ensures vertical centering */
                border: 1px solid #ddd; /* Add border for clear separation */
            }

            /* Specific adjustments for the "Tên Sách" column */
            .order-container th:first-child, .order-container td:first-child {
                width: 40%; /* Set width for book title to ensure consistency */
            }

            /* Adjust image size to prevent distortion */
            .order-container img {
                width: 100px; /* Set image width for consistency */
                height: auto; /* Maintain aspect ratio */
            }

            /* Ensure all other columns are evenly spaced */
            .order-container th:nth-child(2),
            .order-container td:nth-child(2) {
                width: 20%; /* Set consistent width for other columns */
            }

            /* Hover effect for rows to highlight */
            .order-container tr:hover {
                background-color: #f2f2f2; /* Light gray hover effect */
            }
            /* Bounce Effect */
            @keyframes bounce {
                0% {
                    transform: scale(0.9); /* Start slightly smaller */
                    opacity: 0; /* Start invisible */
                }
                50% {
                    transform: scale(1.05); /* Slightly larger at mid-point */
                    opacity: 1; /* Fully visible */
                }
                100% {
                    transform: scale(1); /* End at normal size */
                    opacity: 1; /* Fully visible */
                }
            }




            /* Table Styling */
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #6d4c41;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            /* Button Styling */
            .back-home-btn {
                background-color: #ff4500;
                color: white;
                padding: 12px 24px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                transition: background-color 0.3s;
                display: inline-block;
            }
            .back-home-btn:hover {
                background-color: #e63e00;
            }

            /* Header Styling */
            .order-header {
                font-size: 1.5rem;
                font-weight: bold;
                color: #4E342E;
                border-bottom: 2px solid #ddd;
                padding-bottom: 10px;
            }
            .order-container .order-row .price,
            .order-container .order-row .quantity,
            .order-container .order-row .subtotal,
            .order-container .order-row .date,
            .order-container .order-row .status {
                flex: 1;
                text-align: center;
            }
        </style>
    </head>

    <body>

        <!-- Title centered -->
        <h2>Lịch Sử Đơn Hàng</h2>

        <!-- Order History Loop -->
        <c:choose>
            <c:when test="${not empty sessionScope.orderHistory}">
                <c:forEach var="order" items="${sessionScope.orderHistory}">
                    <div class="order-container">
                   
                        <table>
                            <tr>
                                <th>Tên Sách</th>
                                <th>Hình Ảnh</th>
                                <th>Số Lượng</th>
                                <th>Ngày đặt hàng</th>
                                <th>Trạng thái</th>
                                <th>Giá</th>
                                <th>Thành Tiền</th>
                            </tr>

                            <!-- Iterate over order details -->
                            <c:forEach var="detail" items="${order.orderDetailsList}">
                                <tr>
                                    <!-- Display book details -->
                                    <c:set var="book" value="${bookDao.selectBook(detail.bookId)}" />
                                    <td>${book.title}</td>
                                    <td><img src="${book.imgUrl}" width="80" height="auto" /></td>
                                    <td>${detail.quantity}</td>                                   
                                    <td>${order.orderDate}</td>
                                     <td>${order.status}</td>
                                     <td>${detail.price} VNĐ</td>
                                    <td>${detail.subtotal} VNĐ</td>
                                </tr>
                            </c:forEach>
                        </table>
                     
                
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Không có lịch sử đơn hàng để hiển thị!</p>
            </c:otherwise>
        </c:choose>

        <!-- Back to Home Button -->
        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/home/home.jsp" class="back-home-btn">Quay về trang chủ</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
