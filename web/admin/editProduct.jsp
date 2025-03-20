<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa Sản Phẩm</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
        body { background: #f0f2f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .container { max-width: 600px; width: 100%; padding: 30px; background: white; border-radius: 15px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); margin: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-size: 14px; font-weight: 500; color: #2c3e50; margin-bottom: 8px; }
        input, select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; font-size: 14px; transition: border-color 0.3s; }
        input:focus, select:focus { border-color: #3498db; outline: none; }
        button { padding: 12px 25px; border: none; border-radius: 8px; cursor: pointer; font-size: 14px; font-weight: 500; transition: all 0.3s; }
        .btn-update { background: #3498db; color: white; margin-right: 10px; }
        .btn-cancel { background: #e74c3c; color: white; }
        button:hover { opacity: 0.9; transform: translateY(-2px); }
        .error { color: #e74c3c; margin-bottom: 15px; padding: 10px; background-color: #fce4e4; border-radius: 8px; font-size: 14px; }
        h2 { font-size: 24px; color: #2c3e50; text-align: center; margin-bottom: 20px; }
        @media (max-width: 768px) {
            .container { padding: 20px; margin: 10px; }
            button { padding: 10px 20px; font-size: 13px; }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Sửa Sản Phẩm</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="<%=request.getContextPath()%>/AdminServlet" method="post">
            <input type="hidden" name="action" value="updateProduct">
            <input type="hidden" name="id" value="${product.id}">
            <div class="form-group">
                <label for="title">Tiêu đề:</label>
                <input type="text" id="title" name="title" value="${product.title}" required>
            </div>
            <div class="form-group">
                <label for="author">Tác giả:</label>
                <input type="text" id="author" name="author" value="${product.author}" required>
            </div>
            <div class="form-group">
                <label for="price">Giá:</label>
                <input type="number" id="price" name="price" value="${product.price}" step="0.01" required>
            </div>
            <div class="form-group">
                <label for="description">Mô tả:</label>
                <input type="text" id="description" name="description" value="${product.description}">
            </div>
            <div class="form-group">
                <label for="stock">Số lượng tồn kho:</label>
                <input type="number" id="stock" name="stock" value="${product.stock}" required>
            </div>
            <div class="form-group">
                <label for="publish_date">Ngày xuất bản:</label>
                <input type="date" id="publish_date" name="publish_date" value="${product.publishDate}">
            </div>
            <div class="form-group">
                <label for="img_url">URL hình ảnh:</label>
                <input type="text" id="img_url" name="img_url" value="${product.imgUrl}">
            </div>
            <div class="form-group">
                <label for="category_id">Danh mục:</label>
                <select id="category_id" name="category_id" required>
                    <option value="">Chọn danh mục</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}" ${category.id == product.categoryId ? 'selected' : ''}>${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="status">Trạng thái:</label>
                <input type="checkbox" id="status" name="status" ${product.status ? 'checked' : ''}>
            </div>
            <div style="text-align: center;">
                <button type="submit" class="btn-update">Cập nhật Sản Phẩm</button>
                <button type="button" class="btn-cancel" onclick="window.location.href='<%=request.getContextPath()%>/AdminServlet?activeTab=products'">Hủy</button>
            </div>
        </form>
    </div>
</body>
</html>