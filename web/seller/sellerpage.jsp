<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seller Dashboard - Quản lý Website</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700;800&display=swap" rel="stylesheet">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Roboto', 'Segoe UI', sans-serif; }
        body { background: #f0f2f5; color: #333; line-height: 1.6; }
        .container { display: flex; min-height: 100vh; }
        .sidebar { 
            width: 250px; 
            background: linear-gradient(135deg, #0f2b2b, #1e4b4b); /* Xanh ngọc cho Seller */
            padding: 20px; 
            color: white; 
            position: fixed; 
            height: 100vh; 
            transition: width 0.3s, box-shadow 0.3s; 
            box-shadow: 0 5px 25px rgba(30, 75, 75, 0.5); 
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
        .sidebar h2.seller-title { 
            background: linear-gradient(45deg, #00ff87, #00c4b4); 
            color: #fff; 
            box-shadow: 0 0 20px rgba(0, 255, 135, 0.6); 
            animation: glow-seller 1.5s infinite alternate; 
        }
        .sidebar h2.seller-title::after { 
            content: "🛍️"; 
            position: absolute; 
            left: 15px; 
            font-size: 24px; 
            animation: bounce-icon 1s infinite; 
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
            background: #00c4b4; 
            color: white; 
            transform: translateX(10px); 
            box-shadow: 0 0 15px rgba(0, 196, 180, 0.5); 
        }
        @keyframes glow-seller {
            0% { box-shadow: 0 0 20px rgba(0, 255, 135, 0.6); }
            100% { box-shadow: 0 0 40px rgba(0, 255, 135, 1); }
        }
        @keyframes bounce-icon {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-10px); }
        }
        .main-content { margin-left: 250px; flex: 1; padding: 30px; }
        .header { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 30px; }
        .tab-container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .tab-buttons { display: flex; border-bottom: 2px solid #eee; margin-bottom: 20px; }
        .tab-btn { padding: 10px 20px; border: none; background: none; cursor: pointer; font-size: 16px; transition: all 0.3s; }
        .tab-btn.active { color: #3498db; border-bottom: 2px solid #3498db; }
        .table-container { overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; background: white; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background: #3498db; color: white; }
        tr:hover { background: #f8f9fa; }
        .btn { padding: 8px 15px; border: none; border-radius: 5px; cursor: pointer; transition: all 0.3s; }
        .btn-add { background: #2ecc71; color: white; margin-bottom: 20px; }
        .btn-edit { background: #3498db; color: white; }
        .btn-toggle { color: white; }
        .btn-toggle.active { background: #e74c3c; }
        .btn-toggle.inactive { background: #2ecc71; }
        .btn:hover { opacity: 0.9; transform: translateY(-2px); }
        .product-image { max-width: 100px; max-height: 100px; border-radius: 5px; object-fit: cover; }
        .message { color: #2ecc71; margin-bottom: 15px; padding: 10px; background-color: #e8f5e9; border-radius: 4px; }
        .error { color: #e74c3c; margin-bottom: 15px; padding: 10px; background-color: #fce4e4; border-radius: 4px; }
        .pagination { margin-top: 20px; text-align: center; display: flex; justify-content: center; align-items: center; }
        .pagination a, .pagination span { padding: 8px 16px; margin: 0 4px; text-decoration: none; font-size: 14px; font-weight: 500; border-radius: 5px; transition: all 0.3s ease; }
        .pagination a.prev-next { background-color: #2c3e50; color: white; border: none; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        .pagination a.prev-next:hover { background-color: #34495e; transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15); }
        .pagination a.prev-next.disabled { background-color: #bdc3c7; color: #7f8c8d; pointer-events: none; box-shadow: none; }
        .pagination a.page-number { border: 2px solid #3498db; color: #3498db; background-color: transparent; }
        .pagination a.page-number:hover { background-color: #3498db; color: white; transform: translateY(-2px); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        .pagination a.page-number.active { background-color: #3498db; color: white; border: 2px solid #3498db; font-weight: bold; }
        .status-active { color: #2ecc71; font-weight: bold; font-style: italic; }
        .status-inactive { color: #e74c3c; font-weight: bold; font-style: italic; }
        @media (max-width: 768px) { 
            .sidebar { width: 80px; } 
            .main-content { margin-left: 80px; }
            .pagination a, .pagination span { padding: 6px 12px; font-size: 12px; }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar seller-panel">
            <h2 class="seller-title">Seller Panel</h2>
            <a href="<%= request.getContextPath() %>/SellerServlet">Quản lý sản phẩm</a>
            <a href="<%= request.getContextPath() %>/SellerProfileServlet">Hồ sơ</a>
            <a href="<%= request.getContextPath() %>/SellerStatisticServlet">Thống Kê</a>
            <a href="<%= request.getContextPath() %>/logout">Đăng xuất</a>
        </div>
        <div class="main-content">
            <div class="header">
                <h1>Dashboard Quản lý</h1>
            </div>
            <div class="tab-container">
                <c:if test="${not empty message}">
                    <div class="message">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <div class="tab-buttons">
                    <button class="tab-btn ${activeTab == 'products' || activeTab == null ? 'active' : ''}" onclick="showTab('products')">Sản phẩm</button>
                </div>
                <div id="products" class="tab-content" style="display: ${activeTab == 'products' || activeTab == null ? 'block' : 'none'};">
                    <button class="btn btn-add" onclick="window.location.href='<%=request.getContextPath()%>/SellerServlet?action=addProductForm'">Thêm sản phẩm</button>
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Image</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Price</th>
                                    <th>Stock</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="product-table">
                                <c:forEach var="product" items="${products}">
                                    <tr>
                                        <td>${product.id}</td>
                                        <td><c:if test="${not empty product.imgUrl}"><img src="${product.imgUrl}" alt="${product.title} Image" class="product-image" /></c:if></td>
                                        <td>${product.title}</td>
                                        <td>${product.author}</td>
                                        <td>${product.price}</td>
                                        <td>${product.stock}</td>
                                        <td>
                                            <span class="${product.status ? 'status-active' : 'status-inactive'}">
                                                ${product.status ? 'Active' : 'Inactive'}
                                            </span>
                                        </td>
                                        <td>
                                            <button class="btn btn-edit" onclick="window.location.href='<%=request.getContextPath()%>/SellerServlet?action=editProduct&id=${product.id}'">Sửa</button>
                                            <form action="<%=request.getContextPath()%>/SellerServlet" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="toggleProductStatus">
                                                <input type="hidden" name="id" value="${product.id}">
                                                <button type="submit" class="btn btn-toggle ${product.status ? 'active' : 'inactive'}" onclick="return confirm('Bạn có chắc muốn thay đổi trạng thái của sản phẩm này?')">
                                                    ${product.status ? 'Disable' : 'Enable'}
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="pagination">
                        <c:if test="${productPage > 1}">
                            <a href="<%=request.getContextPath()%>/SellerServlet?productPage=${productPage - 1}" class="prev-next">Previous</a>
                        </c:if>
                        <c:if test="${productPage <= 1}">
                            <a href="#" class="prev-next disabled">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${productPageCount}" var="i">
                            <a href="<%=request.getContextPath()%>/SellerServlet?productPage=${i}" 
                               class="page-number ${i == productPage ? 'active' : ''}">${i}</a>
                        </c:forEach>
                        <c:if test="${productPage < productPageCount}">
                            <a href="<%=request.getContextPath()%>/SellerServlet?productPage=${productPage + 1}" class="prev-next">Next</a>
                        </c:if>
                        <c:if test="${productPage >= productPageCount}">
                            <a href="#" class="prev-next disabled">Next</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function showTab(tabId) {
            document.querySelectorAll('.tab-content').forEach(tab => tab.style.display = 'none');
            document.getElementById(tabId).style.display = 'block';
            document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
            document.querySelector(`button[onclick="showTab('${tabId}')"]`).classList.add('active');
        }

        window.onload = function() {
            showTab('products');
        };
    </script>
</body>
</html>