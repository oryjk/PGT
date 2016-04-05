<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>在线典当</title>
    <link rel="stylesheet" href="/resources/invest/invest.css"/>
    <link rel="stylesheet" href="/resources/invest/invest-end.css"/>
    <style>
        .main,
        .hover-main:hover {
            color: #c90304;
        }
        .em,
        .hover-em:hover {
            color: #86b4ed;
        }
        .bg-main,
        .hover-bg-main:hover {
            background: #c90304;
        }
        .bg-em,
        .hover-bg-em:hover {
            background: #86b4ed;
        }
    </style>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<a class="top-banner-end" href="#" style="background: #9e150e url('/resources/core/images/invest/banner.jpg') no-repeat center center"></a>

<!-- content begin -->
<div class="content">
    <div class="content-title">
        <div class="content-border"></div>
        <h1>您还可以</h1>
        <div class="content-border"></div>
    </div>
    <div class="content-box">
        <div class="content-box-show">
            <div class="b2c-img"></div>
            <p>点金子旗下绝当淘，千家质押贷款行绝当品特卖中，真品！低价！</p>
            <a href="#" >绝当淘</a>
        </div>
        <div class="content-box-show">
            <div class="index-img"></div>
            <p>点金子旗下绝当淘，千家质押贷款行绝当品特卖中，真品！低价！</p>
            <a href="#" >进入首页</a>
        </div>
        <div class="content-box-show">
            <div class="collect-img"></div>
            <p>点金子旗下绝当淘，千家质押贷款行绝当品特卖中，真品！低价！</p>
            <a href="#" >随便看看</a>
        </div>
    </div>
</div>
<!-- content end -->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->
</body>
</html>