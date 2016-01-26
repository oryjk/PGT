<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath}/resources/static/My-collection/My-collection.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/right.js"></script>
</head>
<body>

<%@include file="../common/header.jsp"%>

    <c:forEach items="${bean.products}" var="products">
        <div class="box">
            <div class="img1" style="background:url(/resources${products.thumbnailMedia.path}) no-repeat center center;background-size:100% 100%;"></div>
            <c:if test="${not empty products}">
              <div class="font">
                 <span class="font1-1"><a href="searchProductDetails?id=${products.productId}">${products.name}</a></span>
                 <span class="font1-2"> ${products.salePrice}</span>
              </div>
              <div class="kong2"></div>
            </c:if>
        </div>
    </c:forEach>


    <div class="box">
        <c:if test="${page.currentIndex > 0}">
            <a href="searchProduct?term=${essearchBean.term}&&currentIndex=${page.currentIndex-1}">上一页</a>
        </c:if>
        <c:if test="${page.totalPage > page.currentIndex + 1}">
            <a href="searchProduct?term=${essearchBean.term}&&currentIndex=${page.currentIndex+1}">下一页</a>
        </c:if>
    </div>
</body>
</html>