<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
    <title>Product Click Counter</title>
</head>
<body>
  
    
    
    <h2>Product Click Counter</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Stock</th>
            <th>View Count</th> <!-- Số lượt click -->
        </tr>
        <c:forEach var="entry" items="${productClickMap}">
            <tr>
                <td>${entry.key.id}</td>
                <td>${entry.key.name}</td>
                <td>${entry.key.price}</td>
                <td>${entry.key.description}</td>
                <td>${entry.key.stock}</td>
                <td>${entry.value}</td> <!-- Số lần click -->
            </tr>
        </c:forEach>
    </table>
</body>
</html>
