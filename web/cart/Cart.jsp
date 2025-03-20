<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="dal.bookDao.BookDAO" %>
<%@ page import="dal.voucher.VoucherDAO" %>
<jsp:useBean id="bookDao" class="dal.bookDao.BookDAO" scope="page" />
<jsp:useBean id="voucherDao" class="dal.voucher.VoucherDAO" scope="page" />

<!DOCTYPE html>
<html>
    <head>
        <title>Giỏ Hàng</title>
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: rgb(222,222,222);
            margin: 0;
            padding: 20px 0 300px;
            text-align: center;
        }
        .container {
            width: 60%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        }
        h2 {
            color: #6d4c41;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: -30px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #6d4c41;
            color: white;
        }
        td {
            background-color: #fafafa;
        }
        .btn {
            background-color: #8d6e63;
            color: white;
            padding: 10px 15px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 10px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #5d4037;
        }
        .logout-btn {
            position: absolute;
            top: 10px;
            right: 20px;
            background-color: #ff4d4d;
            padding: 8px 12px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .logout-btn:hover {
            background-color: #d32f2f;
        }
        select {
            padding: 8px;
            border-radius: 5px;
            border: 1px solid #ccc;
            background-color: white;
        }
        h3 {
            margin-top: 15px;
            color: #6d4c41;
        }
        button {
            padding: 10px 15px;
            background-color: #6d4c41;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #4e342e;
        }
        .container-tt {
            background: white;
            padding: 20px;
            width: 50%;
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h3 {
            color: #333;
        }
        select {
            padding: 8px;
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .price-container {
            background: #f8f8f8;
            padding: 10px;
            margin-top: 10px;
            border-radius: 5px;
            display: inline-block;
        }
        .fixed-footer {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            background: white;
            padding: 20px;
            text-align: center;
            box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
            border-top: 1px solid #ddd;
        }
        .fixed-footer .container-tt {
            width: 60%;
            margin: auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        td img {
            width: 80px;
            height: auto;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }
        .continue-shopping {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #8d6e63;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .continue-shopping:hover {
            background-color: #5d4037;
        }
        .update-form {
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .update-form input[type="number"] {
            width: 50px;
            padding: 5px;
            font-size: 16px;
            text-align: center;
            border: 1px solid #ccc;
            border-radius: 5px;
            outline: none;
        }
        .update-form button {
            background-color: grey;
            color: white;
            border: none;
            padding: 6px 12px;
            cursor: pointer;
            font-size: 14px;
            border-radius: 5px;
            transition: 0.3s;
        }
        .update-form button:hover {
            background-color: #6d4c41;
        }
        .banner {
            width: 100%;
            margin-top: -20px;
        }
        .banner img {
            width: 100%;
        }
        .thank-you {
            max-width: 800px;
            margin: 50px auto;
            padding: 25px;
            background-color: #E3F2FD;
            border: 2px solid #90CAF9;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            font-size: 20px;
            color: #1565C0;
            width: 600px;
            min-height: 680px;
            position: relative;
            z-index: 1001;
        }
        .thank-you h2 {
            color: #0D47A1;
            font-size: 24px;
        }
        .thank-you h3 {
            margin-top: 15px;
            color: #1976D2;
            font-size: 20px;
            border-bottom: 2px solid #90CAF9;
            padding-bottom: 5px;
            display: inline-block;
        }
        .thank-you p {
            margin: 10px 0;
            font-size: 16px;
            color: #333;
        }
        .thank-you strong {
            color: #0D47A1;
        }
        .thank-you img {
            width: 100px;
            margin-top: 15px;
        }
        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
    </style>
    <body>
        <div class="banner">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/home/home.jsp">
                <img src="<%= request.getContextPath() %>/bannner-logo/banner top.png">
            </a>
        </div>
        <div class="container">
            <div >
                <!-- Nút để xem đơn hàng -->
                <form action="${pageContext.request.contextPath}/orderDetails?orderId=${sessionScope.orderId}" 
                      style="display: inline; position: absolute; top: 80px; right: 100px;">
                    <button type="submit" class="btn" style="background-color: #8d6e63; color: white; padding: 8px 12px; cursor: pointer; border-radius: 5px;">
                        Đơn Mua
                    </button>
                </form>                       
            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.msg}">
                    <div class="overlay"></div>
                    <div class="thank-you">
                        <h2>Cảm ơn bạn đã đặt hàng!</h2>
                        <h3>Thông tin giao hàng</h3>
                        <p><strong>Khách hàng:</strong> ${sessionScope.fullname}</p>
                        <p><strong>Số điện thoại:</strong> ${sessionScope.phone}</p>
                        <p><strong>Địa chỉ giao hàng:</strong> ${sessionScope.address}</p>
                        <p><strong>Hình thức thanh toán:</strong> ${sessionScope.paymentMethod}</p>
                        <p><strong>Phương thức giao hàng:</strong> ${sessionScope.shippingMethod}</p>
                        <p><strong>Ghi chú đơn hàng:</strong> ${sessionScope.orderNote}</p>
                        <h3>Thông tin thanh toán</h3>
                        <p><strong>Tổng tiền:</strong> ${sessionScope.finalPrice2} VND</p>
                        <p>${sessionScope.msg}</p>
                        <p>Chúc bạn một ngày vui vẻ 🎉</p>
                        <img src="https://thepkz.github.io/minthep-portfolio/blog/Speaking/meo.gif" alt="Gift">
                        <br/>
                        <a href="${pageContext.request.contextPath}/home/home.jsp" class="continue-shopping">Tiếp Tục Mua Sắm</a>
                    </div>
                    <c:remove var="msg" scope="session"/>
                </c:when>
                <c:otherwise>
                    <div class="container">
                        <h2>Giỏ Hàng Của Bạn</h2>
                        <br/>
                        <table border="1">
                            <tr>
                                <th>Tên Sách</th>
                                <th>Hình Ảnh</th>
                                <th>Số Lượng</th>
                                <th>Giá</th>
                                <th>Thành Tiền</th>
                                <th>Thao Tác</th>
                            </tr>
                            <c:set var="totalPrice" value="0" />
                            <c:choose>
                                <c:when test="${not empty sessionScope.cart and not empty sessionScope.cart.items}">
                                    <c:forEach var="entry" items="${sessionScope.cart.items}">
                                        <c:set var="book" value="${bookDao.selectBook(entry.key)}" />
                                        <c:set var="quantity" value="${entry.value.quantity}" />
                                        <c:set var="subtotal" value="${quantity * book.price}" />
                                        <c:set var="totalPrice" value="${totalPrice + subtotal}" />
                                        <tr>
                                            <td>${book.title}</td>
                                            <td><img src="${book.imgUrl}"></td>
                                            <td>
                                                <form action="<%= request.getContextPath()%>/cart?action=update" method="post" class="update-form">
                                                    <input type="hidden" name="bookId" value="${book.id}">
                                                    <input type="number" name="quantity" value="${quantity}" min="1" id="quantity-${book.id}">
                                                    <button type="submit" onclick="return validateQuantity(${book.id}, ${book.stock})">Cập nhật</button>
                                                </form>
                                            </td>
                                            <td>${book.price} VNĐ</td>
                                            <td>${subtotal} VNĐ</td>
                                            <td>
                                                <form action="<%=request.getContextPath()%>/cart" method="post">
                                                    <input type="hidden" name="action" value="remove">
                                                    <input type="hidden" name="bookId" value="${entry.key}">
                                                    <button type="submit" class="btn">Xóa</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="7" style="text-align: center;">Giỏ hàng trống!</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </table>
                        <a href="${pageContext.request.contextPath}/home/home.jsp" class="continue-shopping">Tiếp Tục Mua Sắm</a>

                        <div class="fixed-footer">
                            <div class="container-tt">
                                <div>
                                    <h3>Tổng tiền:</h3>
                                    <div class="price-container">
                                        <fmt:formatNumber value="${totalPrice}" type="number" pattern="#,##0"/> VNĐ
                                    </div>
                                </div>
                                <form id="voucherForm" method="post" action="<%=request.getContextPath()%>/checkout">
                                    <c:set var="userVouchers" value="${voucherDao.getAvailableVouchersForUser(sessionScope.userId)}" scope="request"/>
                                    <select name="voucher" onchange="this.form.submit();">
                                        <option value="none" ${sessionScope.voucher == null || sessionScope.voucher == 'none' ? 'selected' : ''}>
                                            -- Chọn mã giảm giá --
                                        </option>
                                        <c:choose>
                                            <c:when test="${not empty userVouchers}">
                                                <c:forEach var="voucher" items="${userVouchers}">
                                                    <option value="${voucher.code}" ${sessionScope.voucher == voucher.code ? 'selected' : ''}>
                                                        ${voucher.code} - Giảm ${voucher.discount}%
                                                    </option>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="none" disabled>Không có voucher khả dụng</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </form>
                                <div>
                                    <h3>Giảm giá:</h3>
                                    <div class="price-container">
                                        <span id="voucherPrice">
                                            <fmt:formatNumber value="${sessionScope.voucherPrice != null ? sessionScope.voucherPrice : 0}" type="number" pattern="#,##0.00"/> VNĐ
                                        </span>
                                    </div>
                                </div>
                                <div>
                                    <h3>Thành tiền:</h3>
                                    <div class="price-container">
                                        <span id="finalPrice">
                                            <fmt:formatNumber value="${sessionScope.finalPrice != null ? sessionScope.finalPrice : totalPrice}" type="number" pattern="#,##0.00"/> VNĐ
                                        </span>
                                    </div>
                                </div>
                                <form action="<%=request.getContextPath()%>/checkout?action=confirm" method="post">
                                    <input type="hidden" name="finalPrice2" value="${sessionScope.finalPrice != null ? sessionScope.finalPrice : totalPrice}">
                                    <button type="submit">Thanh Toán</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
    <script>
        function validateQuantity(bookId, stock) {
            let quantityInput = document.getElementById("quantity-" + bookId);
            let quantity = parseInt(quantityInput.value);
            if (quantity > stock) {
                alert("Số lượng không được vượt quá " + stock);
                return false;
            }
            return true;
        }
    </script>
</html>