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
    <link href="${pageContext.request.contextPath }/resources/static/My-collection/My-collection.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/My-collection/favourites.js"></script>
</head>
<body>
<div class="header">
    <a href="<spring:url value="/userinformation/query"/>" class="arrow"></a>
    <div class="font">我的收藏</div>
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

<c:forEach var="fav" items="${favourites.result}">
<div class="box">
    <div class="img1">
        <img src="${pageContext.request.contextPath}/resources${fav['snapshotMedia']['path']}"
                           alt="${empty fav['snapshotMedia']['title'] ? fav.name : fav['snapshotMedia']['title']}"/>
    </div>

    <div class="font">
        <span class="font1-1"><a href="<spring:url value="${urlConfiguration.pdpPage}/${fav.productId}"/>" data-favourite-id="${fav.id}">${fav.name}</a></span>
        <span class="font1-2"><fmt:formatNumber value="${fav.finalPrice}" pattern="0.00" type="number" />元</span>
    </div>
    <div class="kong2"></div>
    <div  class="btn1">
       <%-- <input type="button" class="delete-1" value="取消">

        <input type="button" class="delete1-1" value="购物车">--%>

        <a class="delete-1" data-favourite-id="${fav.id}" href="#" onclick="dislikeButton(${fav.id})">取消</a>

        <a class="delete-1" data-vaule="${fav.productId}" href="#" onclick="addShoppingCart(${fav.productId})">购物车</a>
    </div>
</div>
</c:forEach>







<a class="btn-clean">
    清空
</a>

<div class="footer">
    <div class="footer-top">
        <div class="kong1"></div>
        <a href="#" class="f1">请登录</a>
        <a href="#" class="f1">请注册</a>
        <a href="#" class="f1">客户端</a>
        <a href="#" class="f1">电脑版</a>
        <a href="#" class="f1">回顶部</a>
        <div class="kong"></div>
    </div>
    <div class="footer-bottom">
        蜀IPC备15022028号 dianjinzi, Inc. All rights reserved
    </div>
</div>


</body>
</html>