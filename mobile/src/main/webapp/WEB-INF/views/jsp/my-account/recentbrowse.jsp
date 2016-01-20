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
    <link href="${pageContext.request.contextPath }/resources/static/Recent－browse/Recent-browse.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
</head>
<body>

    <div class="header">
        <a href="${pageContext.request.contextPath }/web/personalInformation" class="arrow"></a>
        <div class="font">最近浏览</div>
        <a href="#" class="dian">
            <ul class="menu">
                <li class="menu2">首页</li>
                <li class="menu3">分类</li>
                <li class="menu4">搜索</li>
                <li class="menu5">购物车</li>
                <li class="menu1">我的账户</li>
            </ul>
        </a>
    </div>

    <c:forEach var="product" items="${browsedProducts}">

        <a href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>" class="box">
        <div class="img" >
            <img src="<spring:url value="/resources${product.source.frontMedia.path}"/>"
            alt="${empty product.source.frontMedia.title ? product.source.name : product.source.frontMedia.title}" />
        </div>
        <div class="font">
                ${product.source.name} ${product.source.serialNumber }
        </div>
        <div  class="btn">

        </div>
    </a>
    </c:forEach>


    <%@include file="../common/footer.jsp" %>


</body>
</html>