<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.order.Cart, model.order.CartItem" %>

<%
    // Lấy giỏ hàng từ session
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.getItems().isEmpty()) {
        response.sendRedirect("Cart.jsp"); // Chuyển hướng nếu giỏ hàng trống
        return;
    }
    
    
    /*
    
        double finalPrice = totalPrice - discount;

        session.setAttribute("totalPrice", totalPrice);
        System.out.println(totalPrice);
        session.setAttribute("voucherPrice", discount);
                System.out.println(discount);

        session.setAttribute("finalPrice", finalPrice);
                System.out.println(finalPrice);

        session.setAttribute("voucher", voucherCode);
                System.out.println(voucherCode);

        System.out.println("toi day");
    }*/

    // Lấy giá trị từ session
    Double voucherPrice = (Double) session.getAttribute("voucherPrice");
    Double finalPrice = (Double) session.getAttribute("finalPrice");
    String voucher = (String) session.getAttribute("voucher");
    Double totalPrice = (Double) session.getAttribute("totalPrice");


    // Xử lý nếu giá trị null
    if (voucherPrice == null) voucherPrice = 0.0;
    if (finalPrice == null) finalPrice = 0.0;
    if (voucher == null) voucher = "";
%>

<html>
    <head>
        <title>Xác nhận đơn hàng</title>
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 60%;
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
        }

        h2, h3 {
            color: #6d4c41;
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            text-align: center;
            padding: 10px;
        }

        th {
            background: #6d4c41;
            color: white;
            font-weight: bold;
        }

        td {
            background: #fff;
        }

        .product-img {
            width: 80px;
            height: auto;
            object-fit: cover;
            border-radius: 5px;
        }

        p {
            font-size: 16px;
            font-weight: bold;
            text-align: center;
        }

        form {
            margin-top: 20px;
        }

        label {
            font-weight: bold;
            display: block;
            margin: 10px 0 5px;
        }

        input, textarea, select {
            width: 100%;
            padding: 10px;
            margin: 6px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 12px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 15px;
            transition: 0.3s;
        }

        button:hover {
            background: #6d4c41;
        }

        .back-button {
            display: block;
            width: fit-content;
            padding: 10px 15px;
            background: #dc3545;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: 0.3s;
            margin-bottom: 15px;
        }

        .back-button:hover {
            background: #c82333;
        }

        .banner {
            width: 100%;


        }
        .banner img {
            width: 100%;

        }

    </style>
    <body>
        <!-- Banner -->
        <div class="banner">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/home/home.jsp">
                <img src="<%= request.getContextPath() %>/bannner-logo/banner top.png">
            </a>
        </div>
        <div class="container">
            <button type="button" class="back-button" onclick="goBack()">← Quay lại</button>

            <h2>Xác nhận đơn hàng</h2>
            <table>
                <tr>
                    <th>Tên sản phẩm</th>
                    <th>hình ảnh </th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                </tr>
                <%
                    for (CartItem item : cart.getItems().values()) {
                %>
                <tr>
                    <td><%= item.getBook().getTitle() %></td>
                    <td> <img src="<%= item.getBook().getImgUrl() %>" alt="Hình ảnh sản phẩm" class="product-img"></td>
                    <td><%= item.getQuantity() %></td>
                    <td><%= item.getQuantity() * item.getBook().getPrice() %></td>
                </tr>
                <% } %>
            </table>

            <p><strong>Tổng giá:</strong> <%= totalPrice %></p>
            <p><strong>Giảm giá:</strong> <%= voucherPrice %></p>
            <p><strong>Giá cuối cùng:</strong> <%= finalPrice %></p>
            <p><strong>Giá  cùng:</strong> <%= voucher %></p>



            <h3>Thông tin giao hàng</h3>
            <form id="orderForm" action="<%=request.getContextPath()%>/checkout?action=done" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="done">

                <input type="hidden" name="finalPrice2" value="<%= finalPrice %>">
                <input type="hidden" name="voucherPrice" value="<%= voucherPrice %>">
                <input type="hidden" name="voucher" value="<%= voucher %>">


                <label>Số điện thoại:</label>
                <input type="text" id="phoneNumber" name="phone" required pattern="[0-9]{10,11}" 
                       title="Số điện thoại phải có 10-11 số">

                <label>Địa chỉ giao hàng:</label>
                <textarea id="address" name="address" required></textarea>

                <label>Hình thức thanh toán:</label>
                <select name="paymentMethod" id="paymentMethod" onchange="updateFormAction()">
                    <option value="cod">Thanh toán khi nhận hàng (COD)</option>
                    <option value="vnpay">VNPay</option>
                </select>

                <label>Phương thức giao hàng:</label>
                <select name="shippingMethod">
                    <option value="Standard">Giao hàng tiêu chuẩn</option>
                    <option value="Express">Giao hàng nhanh</option>
                </select>

                <label>Ghi chú đơn hàng:</label>
                <textarea name="orderNote"></textarea>

                <button type="submit">Xác nhận đặt hàng</button>
            </form>
        </div>

        <script>



            function goBack() {
                window.history.back();
            }


            function validateForm() {
                let phone = document.getElementById("phoneNumber").value.trim();
                let address = document.getElementById("address").value.trim();

                if (phone.includes(" ")) {
                    alert("Số điện thoại không được chứa khoảng trắng!");
                    return false;
                }

                if (address.length < 10) {
                    alert("Địa chỉ phải có ít nhất 10 ký tự!");
                    return false;
                }
                return true;
            }

            function updateFormAction() {
                var paymentMethod = document.getElementById("paymentMethod").value;
                var form = document.getElementById("orderForm");

                if (paymentMethod === "cod") {
                    form.action = "<%=request.getContextPath()%>/checkout?action=done"; // Chuyển đến trang /checkout nếu chọn COD
                } else {
                    form.action = "<%=request.getContextPath()%>/payment?action=done"; // Chuyển đến trang VNPay nếu chọn VNPay
                }
            }
        </script>


    </body>
</html>