<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <script src="resources/static/index/js/myfocus-2.0.4.min.js" type="text/javascript"></script>
    <script src="resources/static/index/js/mf-pattern/mF_expo2010.js" type="text/javascript"></script>
    <link href="resources/static/index/js/mf-pattern/mF_expo2010.css" rel="stylesheet" type="text/css">
    <link href="resources/static/index/index.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <div class="logo"></div>
    <div class="search"><input class="input1" type="search" placeholder="搜索" autocomplete="off"></div>
    <div class="in"><a href="#">登录</a></div>
</div>
<div class="header-bottom">
    <div class="banner">
        <div class="ad" id="expo2010-box">
            <div class="pic">
                <ul>
                    <c:forEach items="${data.banner.images}" var="image" varStatus="status">
                         <li style="background:url(/resources${image.path}) no-repeat center center;background-size:100% 100%;"
                            class="banner1"></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="classification">
        <a class="box1"><img src="resources/static/img/icon1.png">

            <div>分类查询</div>
        </a>
        <a class="box1"><img src="resources/static/img/icon2.png">

            <div>购物车</div>
        </a>
        <a class="box1"><img src="resources/static/img/icon3.png">

            <div>我的收藏</div>
        </a>
        <a class="box1"><img src="resources/static/img/icon4.png">

            <div>我的账户</div>
        </a>
    </div>
</div>
<div class="content">
    <div class="like">最新热卖</div>
    <c:forEach items="${data.hotSearchList}" var="hotSearchList" varStatus="status">
        <div class="box3">
            <a href="#" class="img1-left" style="background:url(/resources${hotSearchList.frontMedia.path}) no-repeat center center;background-size:100% 100%;"></a>
        </div>
    </c:forEach>
    <div class="box2">
        <c:forEach items="${data.hotProducts}" var="hotProducts" varStatus="hotProducts-status">
            <div class="like">  ${hotProducts.name} </div>
            <div class="img-all">
                <div class="img1" style="background:url(/resources${hotProducts.category.frontMedia.path}) no-repeat center center;background-size:100% 100%;"></div>
                <c:forEach items="${hotProducts.hotProduct}" var="hotProduct" varStatus="s">
                    <c:if test="${s.index < 2}">
                        <div class="img-top" style="background:url(/resources${hotProduct.thumbnailMedia.path}) no-repeat center center;background-size:100% 100%;">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

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

<script type="text/javascript">
    myFocus.set({
        id: 'expo2010-box',//焦点图盒子ID
        pattern: 'mF_expo2010',//风格应用的名称
        time: 3,//切换时间间隔(秒)
        height: 425,
        trigger: 'mouseover',//触发切换模式:'click'(点击)/'mouseover'(悬停)
        txtHeight: 'default'//文字层高度设置(像素),'default'为默认高度，0为隐藏
    });
</script>

</html>