<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">

    <title></title>
    <script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/new-index/js/jquery-1.8.3.min.js"/>"></script>
    <script src="<spring:url value="${juedangpinStaticPath}/new-index/js/myfocus-2.0.4.min.js"/>" type="text/javascript"></script>
    <script src="<spring:url value="${juedangpinStaticPath}/new-index/js/mf-pattern/mF_expo2010.js"/>" type="text/javascript"></script>
    <link href="<spring:url value="${juedangpinStaticPath}/new-index/js/mf-pattern/mF_expo2010.css"/>" rel="stylesheet" type="text/css">
    <link href="<spring:url value="${juedangpinStaticPath}/new-index/dianjinzi.css"/>" rel="stylesheet">

</head>
<script>
    $(function(){
        var height = $('body').height();
        $(".right").css("height",height);
    });
</script>
<body>
<!--侧边栏-->
<div class="right">
    <a href="#" class="right1">
        <img src="<spring:url value="${juedangpinStaticPath}/new-index/images/component/_0002_SHOPPING-CAR.png"/>">
        <div>购物车</div>
    </a>
    <a href="#" class="right1">
        <img src="<spring:url value="${juedangpinStaticPath}/new-index/images/component/_0000_NOTEPAD.png"/>">
        <div>订单</div>
    </a>
    <a href="#" class="right1">
        <img src="<spring:url value="${juedangpinStaticPath}/new-index/images/component/_0004_yen.png"/>">
        <div>账户</div>
    </a>
    <a href="#" class="right1">
        <img src="<spring:url value="${juedangpinStaticPath}/new-index/images/component/_0005_arrow-top.png"/>">
        <div>返回顶部</div>
    </a>
</div>
<!--header-->
<div class="header">
    <div class="header-content">
        <ul class="nav">
            <li class="nav-1"><a href="#"> 网站导航</a></li>
            <li class="nav-2"><a href="#"> 消息</a></li>
            <li class="nav-3"><a href="#"> 登录</a></li>
            <li class="nav-4"><a href="#"> 注册</a></li>
            <li class="nav-5"><a href="#"> 我的订单</a></li>
            <li class="nav-6"><a href="#"> 我的绝当品</a></li>
            <li class="nav-7"><a href="#"> 购物车</a></li>
            <li class="nav-8"><a href="#"> 手机点金子</a></li>
            <li class="nav-9"><a href="#"> 政企采购</a></li>
            <li class="nav-0"><a href="#"> 服务中心</a></li>
        </ul>
    </div>
</div>
<!--header-b-->
<div class="content-top">
    <div class="logo"></div>
    <div class="search">
        <form action="${pageContext.request.contextPath}/essearch">
        <input type="text" class="search1" name="term" value="${term}">
        <a href="#" class="btn1">搜索</a>
        </form>

        <ul class="font1">
            <c:forEach items="${hotSearchList}" var="hotSearch" varStatus="status">
            <li><a href="${pageContext.request.contextPath}/essearch?term=${hotSearch.term}">
            ${hotSearch.term}</a></li>
            </c:forEach>
        </ul>
    </div>
    <a href="#" class="btn2"><img src="${juedangpinStaticPath}/new-index/images/shoppingcart_03.png">我的购物车</a>
</div>

<div class="menu-top">
    <a href="#" class="menu-top1">全部绝当品分类</a>
    <ul class="font2">
        <c:forEach items="${rootHomeCategories}" var="category" varStatus="status">
            <c:set var="rootCategory" value="${category.source}"/>
            <li><a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${rootCategory.id}">${rootCategory.name}</a></li>
        </c:forEach>
    </ul>
</div>

<div class="banner-all">
    <div class="banner">
        <ul class="menu">

            <c:forEach items="${rootHomeCategories}" var="category" varStatus="status">

            <c:set var="rootCategory" value="${category.source}"/>
                <li class="menu-0" <c:if test="${status.last==true}">style="border: none"></c:if>>
                <a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${rootCategory.id}" class="menu-h">${rootCategory.name}</a>

                <ul class="menu-1">
                    <c:forEach items="${rootCategory.children}" var="subCategory" varStatus="st">

                    <li <c:if test="${st.last eq true}">class="li-l"></c:if>><a href="${pageContext.request.contextPath}/essearch?parentCategoryId=${subCategory.id}">${rootCategory.name}</a></li>
                    </c:forEach>
                </ul>
               </li>

                </c:forEach>
        </ul>
    </div>
</div>
<!--content-->
<div class="content">

    <div class="box1">
        <c:forEach items="${hotSearchList}" var="hotSearch" varStatus="status">
            <c:if test="${status.index<4}">
            <a  href="${pageContext.request.contextPath}/essearch?term=${hotSearch.term}" class="box1-1"
            style="background: url('${pageContext.request.contextPath}/resources${hotSearch.frontMedia.path}') no-repeat;"></a>
            </c:if>
        </c:forEach>
    </div>


    <div class="box2">
        <div class="box-font">
            最近上新
        </div>
        <c:forEach items="${newProducts}" var="searchProduct">
            <c:set var="product" value="${searchProduct.source}"/>
        <a href="product/${product.productId}" class="box2-1" style="background: url('${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}') no-repeat;"></a>
        </c:forEach>
    </div>


    <c:forEach items="${hotProducts}" var="homeCategory"
               varStatus="status">
    <div class="box-banner1" style="background: url('${homeCategory.source.category.banner.images[0].path}') "></div>
    <div class="box4">
        <div class="box-font" style="color: ${homeCategory['source']['category']['color']}">
        ${homeCategory['source']['category']['name']}
        </div>
        <div class="box4-all">
            <div class="box4-left" style="background: url('${pageContext.request.contextPath}/resources${homeCategory['source']['category']['frontMedia']['path']}') no-repeat;"></div>

            <div class="box4-right">


                <c:forEach items="${homeCategory['source']['hotProduct']}" var="product"
                           varStatus="st">
                    <c:if test="${st.index<6}">

                        <div class="box4-1">
                        <a href="product/${product['productId']}" class="box4-1-1" style="background-image: url('${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}');display: block;">
                        </a>
                        <div class="box4-1-2">
                        ${product['name']}
                        <div class="box4-1-2-1">
                            ￥<span><fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number"/></span>
                        </div>
                        </div>
                        </div>

                    </c:if>
                </c:forEach>

            </div>
        </div>
    </div>

    </c:forEach>


</div>

<!--footer-->
<jsp:include page="../core/footer-main.jsp"/>

</body>
</html>