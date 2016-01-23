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
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <link href="shopping-cart.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/right.js"></script>
</head>
<body>

<%@include file="../common/header.jsp"%>

<div class="box">
    <div class="kong2">
        <input type="checkbox">
    </div>
    <div class="img1"></div>
    <div class="font">
        <span class="font1-1">周生生钻石 18K金钻石结婚戒指女戒</span>
        <span class="font1-2">1888.00元</span>
    </div>
    <div  class="btn1">
        <img src="../img/del.png">
    </div>
</div>
<div class="box">
    <div class="kong2">
        <input type="checkbox" class="check">
    </div>
    <div class="img1"></div>
    <div class="font">
        <span class="font1-1">周生生钻石 18K金钻石结婚戒指女戒</span>
        <span class="font1-2">1888.00元</span>
    </div>
    <div  class="btn1">
        <img src="../img/del.png">
    </div>
</div>
<div class="box">
    <div class="kong2">
        <input type="checkbox">
    </div>
    <div class="img1"></div>
    <div class="font">
        <span class="font1-1">周生生钻石 18K金钻石结婚戒指女戒</span>
        <span class="font1-2">1888.00元</span>
    </div>
    <div  class="btn1">
        <img src="../img/del.png">
    </div>
</div>
<div class="box">
    <div class="kong2">
        <input type="checkbox">
    </div>
    <div class="img1"></div>
    <div class="font">
        <span class="font1-1">周生生钻石 18K金钻石结婚戒指女戒</span>
        <span class="font1-2">1888.00元</span>
    </div>
    <div  class="btn1">
        <img src="../img/del.png">
    </div>
</div>



<a class="btn-clean">
    清空
</a>

<%@include file="../common/footer.jsp"%>
<script>
    $(function(){
        var url = "${pageContext.request.contextPath}/shoppingCart/ajaxCart";
        alert(url);
        $.get(url, function(result){
            alert(JSON.stringify(result));
        });
    })
</script>
</body>
</html>