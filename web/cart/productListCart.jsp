<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dal.productDao.ProductDAO" %>
<jsp:useBean id="productDao" class="dal.productDao.ProductDAO" scope="page" />
<jsp:useBean id="products" class="java.util.ArrayList" scope="request"/>
<!--moi them-->
<%@page import="java.util.List, model.product.Product"%>

<%
    request.setAttribute("products", productDao.selectAllProducts());
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh Sách Sản Phẩm</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
            }
            .product {
                border: 1px solid #ddd;
                padding: 10px;
                margin: 10px;
                display: inline-block;
                width: 250px;
            }
            .product button {
                margin-top: 8px ;
                background-color: green;
                color: white;
                padding: 5px;
                border: none;
                cursor: pointer;
            }
            .search-container{
                margin: auto;
                width: 65%;
                display: flex;
                justify-content: center;

            }
            .search-form{
                width: 100%;
            }
            .search-container .key{
                width: 70%;
                padding: 8px;
            }
            .search-container .button-search{
                width: 20%;
                padding: 8px;
                background-color: black;
                color: white;
                /*            border-radius: 8px;*/

            }
            .pagination {
    text-align: center;
    margin-top: 20px;
}

.pagination a {
    display: inline-block;
    padding: 8px 12px;
    margin: 2px;
    text-decoration: none;
    background-color: #007bff;
    color: white;
    border-radius: 5px;
}

.pagination a:hover {
    background-color: #0056b3;
}

.pagination a.active {
    background-color: #0056b3;
    font-weight: bold;
}


.recommended-products {
    text-align: center;
    margin-top: 20px;
}

.recommended-products .product {
    border: 1px solid #ddd;
    padding: 10px;
    margin: 10px;
    display: inline-block;
    width: 250px;
}

.recommended-products .product button {
    margin-top: 8px;
    background-color: green;
    color: white;
    padding: 5px;
    border: none;
    cursor: pointer;
}

        </style>
    </head>
    <body>
        
        <a href="<%=request.getContextPath()%>/viewc" 
               style=" position: absolute;left: 20px ;text-decoration: none; background-color: orange; color: white; padding: 8px 12px; border-radius: 5px;">
                xem so lan click san pham
            </a>

        <div style="position: absolute; top: 10px; right: 20px; display: flex; align-items: center; gap: 15px;">
            <!-- Link Giỏ Hàng -->
            <a href="${pageContext.request.contextPath}/cart/Cart.jsp" 
               style="text-decoration: none; background-color: orange; color: white; padding: 8px 12px; border-radius: 5px;">
                🛒 Giỏ Hàng
            </a>

            <!-- Nút Đăng Xuất -->
            <form action="<%=request.getContextPath()%>/logout" method="get" style="margin: 0;">
                <button type="submit" style="background-color: #ff4d4d; color: white; border: none; padding: 8px 12px; cursor: pointer; border-radius: 5px;">
                    Đăng xuất
                </button>
            </form>
        </div>

        <h2>Danh Sách Sản Phẩm</h2>
        <%-- Thiết lập số sản phẩm hiển thị trên mỗi trang --%>
        <c:set var="pageSize" value="10"/>
        <c:set var="currentPage" value="${param.page != null ? param.page : 1}"/>
        <c:set var="start" value="${(currentPage - 1) * pageSize}"/>
        <c:set var="end" value="${start + pageSize}"/>
        <c:set var="totalProducts" value="${products.size()}"/>
        <c:set var="totalPages" value="${Math.ceil(totalProducts / pageSize)}"/>


<!--    <a style="text-decoration: none; font-family: sans-serif; font-size: 20px" href="${pageContext.request.contextPath}/Cart.jsp">Xem Giỏ Hàng</a>-->
        <br/><br/>

        <div  class="search-container">
            <form class="search-form" action="<%=request.getContextPath()%>/products" method="GET">
                <input class="key" type="text" name="searchKeyword" required="" placeholder="nhap tu khoa...">
                <input type="hidden" name="action" value="search" >
                <input class="button-search" type="submit" value="Search">
            </form>
        </div>

        <div class="products">
            <c:forEach var="product" items="${products}" varStatus="status">
                <c:if test="${status.index >= start && status.index < end}">
                    <div class="product">

                        <h3>${product.name}</h3>
                        <p>Giá: ${product.price} VNĐ</p>
                        <form action="<%=request.getContextPath()%>/cart?action=add" method="post">
                            <input type="hidden" name="productId" value="${product.id}" />
                            <!--                    moi them-->

                            <input type="number" name="quantity" value="1" min="1" />
                            <!--                    <button type="submit">Thêm vào giỏ</button>-->
                            <button type="submit" >Thêm vào giỏ</button>

                        </form>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div class="pagination">
    <c:if test="${currentPage > 1}">
        <a href="<%=request.getContextPath()%>/products?page=${currentPage - 1}">Previous</a>
    </c:if>

    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="<%=request.getContextPath()%>/products?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="<%=request.getContextPath()%>/products?page=${currentPage + 1}">Next</a>
    </c:if>
</div>
        
      <%
    List<Product> recommendedProducts = (List<Product>) session.getAttribute("recommendedProducts");
%>
<h2>Gợi ý sản phẩm cho bạn</h2>
<div class="recommended-products">
    <c:choose>
        <c:when test="${not empty recommendedProducts}">
            <c:forEach var="p" items="${recommendedProducts}">
                <div class="product">
                    <h3>${p.name}</h3>
                    <p>Giá: ${p.price} VND</p>
                    <a href="productDetail.jsp?productId=${p.id}" 
                       style="text-decoration: none; color: white; background-color: blue; padding: 5px; border-radius: 5px; display: block;">
                        Xem chi tiết
                    </a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Không có sản phẩm gợi ý.</p>
        </c:otherwise>
    </c:choose>
          
</div>

<img src="../images/tt1.jpg" alt="alt"/>
<<img src="../images/t1.jpg.png" alt="alt"/>
<<img src="https://cf.shopee.vn/file/0e65627a361422a1740ebb6dcb218e7e" alt="alt"/>
<script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
<df-messenger
  intent="WELCOME"
  chat-title="book-store"
  agent-id="04257597-7681-4aef-9c6c-85b5257a2b6b"
  language-code="en"
></df-messenger>
        
    </body>
</html>