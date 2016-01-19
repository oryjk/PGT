<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath}/resources/static/My-collection.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/right.js"></script>
</head>
<body>

<div class="box">
    <div class="img1"></div>

    <c:forEach items="${bean.products}" var="products">
        <c:if test="${not empty products}">
            <div class="font">
                <span class="font1-1">${products.name}</span>
                <span class="font1-2"> ${products.salePrice}</span>
            </div>
            <div class="kong2"></div>
            <div  class="btn1">
                <input type="button" class="delete-1" value="取消">
                <input type="button" class="delete1-1" value="购物车">
            </div>
        </c:if>
    </c:forEach>
</div>

<a class="btn-clean">
    清空
</a>

</body>
</html>