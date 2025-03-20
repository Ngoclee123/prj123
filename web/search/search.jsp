<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Kết Quả Tìm Kiếm</title>
        <style>
            body { font-family: Arial, sans-serif; text-align: center; background-color: #f4f4f4; }
            .container { width: 80%; margin: auto; padding: 20px; }
            h2 { color: #333; }
            .search-box { margin-bottom: 20px; }
            .search-box input { padding: 10px; width: 60%; border: 1px solid #ccc; }
            .search-box button { padding: 10px; background-color: #282745; color: white; border: none; cursor: pointer; }
            .products { display: flex; flex-wrap: wrap; justify-content: center; gap: 20px; }
            .product { background: white; border-radius: 5px; padding: 15px; width: 250px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
            .product h3 { color: #282745; }
            .product p { color: #555; }
            .product button { background-color: #282745; color: white; padding: 8px; border: none; cursor: pointer; margin-top: 10px; }
            .no-results { color: red; font-size: 18px; margin-top: 20px; }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Kết Quả Tìm Kiếm</h2>
            <div class="search-box">
                <form action="<%=request.getContextPath()%>/products" method="get">
                <input type="hidden" name="action" value="search">
                <input type="text" name="searchKeyword" placeholder="Nhập từ khóa..." required>
                <button type="submit">Tìm kiếm</button>
</form>

            </div>

            <c:if test="${empty books}">
                <p class="no-results">Không có sản phẩm nào phù hợp!</p>
            </c:if>

            <div class="products">
                <c:forEach var="book" items="${books}">
                    <div class="product">
                        <h3>${book.title}</h3>
                        <p>Giá: ${book.price} VNĐ</p>
                        <form action="cart?action=add" method="post">
                            <input type="hidden" name="productId" value="${book.id}" />
                            <input type="number" name="quantity" value="1" min="1" />
                            <button type="submit">Thêm vào giỏ</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
