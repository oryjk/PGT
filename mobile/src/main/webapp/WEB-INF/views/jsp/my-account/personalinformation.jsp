<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/static/personal-information/personal-information.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
</head>
<body>
<div class="header">
    <a href="#" class="arrow"></a>

    <div class="font">我的点金子</div>
    <a href="#" class="dian">
        <ul class="menu">
            <li class="menu2"><a href="#">首页</a></li>
            <li class="menu3"><a href="#">分类</a></li>
            <li class="menu4"><a href="#">搜索</a></li>
            <li class="menu5"><a href="#">购物车</a></li>
            <li class="menu1"><a href="#">我的账户</a></li>
        </ul>
    </a>
</div>

<div class="content">
    <div class="background"></div>
    <div class="my"><img src="${pageContext.request.contextPath }/resources/static/img/personal.png"></div>
</div>
<div class="list">
    <div class="kong"></div>
    <a href="${pageContext.request.contextPath }/web/webFavourites" class="list1">我的收藏</a>
    <a href="#" class="list2">我的购物车</a>
    <a href="${pageContext.request.contextPath }/web/recentBrowse" class="list3">最近浏览</a>

    <div class="kong"></div>
</div>

<div class="box1">
    <div class="box1-top">
        <span class="box1-top-left">我的订单</span>
        <a href="#" class="box1-top-right">查看全部订单</a>
    </div>
    <div class="box1-content">
        <a href="#" class="pay">待付款</a>
        <a href="#" class="receive">待收货</a>
        <a href="#" class="sales-return">退换货</a>
    </div>
</div>
<div class="box1">
    <div class="box2-top">
        <span class="box1-top-left">我的账户</span>
        <a href="#" class="box1-top-right"></a>
    </div>
    <div class="box1-content">
        <a href="#" class="pay">账户余额</a>
        <a href="#" class="box2-2">我的易宝</a>
        <a href="#" class="box2-3">充值</a>
    </div>
</div>

<div class="box1">
    <div class="box3">
        <span class="box1-top-left">地址管理</span>
        <a href="${pageContext.request.contextPath }/web/waddress" class="box1-top-right">更多</a>
    </div>
</div>

<div class="box1">
    <div class="box4">
        <span class="box1-top-left">设置</span>
        <a href="#" class="box1-top-right">更多</a>
    </div>
</div>
<div class="box1">
    <div class="box5">
        <span class="box1-top-left">帮助与反馈</span>
        <a href="#" class="box1-top-right">更多</a>
    </div>
</div>

<input type="button" class="btn" onclick="javascript:window.location.href='${pageContext.request.contextPath }/web/wlogout'" value="退出账号">


    <%@include file="../common/footer.jsp" %>
</body>
</html>