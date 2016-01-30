<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 11/16/15
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="top-box">
    <div class="top-status">
        <div class="status-box">
            <ul class="top-nav">
                <li>
                    <a href="#">手机绝当品</a>
                </li>
                <li><a href="#">帮助中心</a></li>
                <li><a href="#">网站导航</a></li>
            </ul>
            <c:choose>
                <c:when test="${currentUser==null}">
                    <ul class="will-login">
                        <li><a href="<spring:url value="${urlConfiguration.loginPage}"/>">立即登录</a></li>
                        <li><a href="<spring:url value="${urlConfiguration.registerPage}"/>">免费注册</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="have-login">
                        <li>
                            <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"><span> 欢迎您：</span><span>${currentUser.username}</span></a>
                        </li>
                        <li><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>">账户管理</a></li>
                        <li><a href="<spring:url value="${urlConfiguration.logoutPage}"/>">退出登录</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="top-banner">


        <div class="top-banner-box">

            <a href="${pageContext.request.contextPath}${TopBanner.images[0].url}">
                <img src="${pageContext.request.contextPath}${TopBanner.images[0].path}"
                     alt="${TopBanner.images[0].title}"/>
            </a>

        </div>

    </div>
</div>

<div class="head-box">
    <div class="logo-box">
        <h1>
            <a href="/">
                点金子绝当品——欢迎登陆
                <!--<img src="../core/images/header/images/big-logo_pig.jpg" alt="#"/>-->
                <!--<div class="golds"></div>-->
                <div class="light"></div>
            </a>
        </h1>
    </div>
    <div class="search-box">
        <form action="${pageContext.request.contextPath}/essearch">
            <input class="search-text" name="term" value="${term}" type="text"/><!--
             --><input class="search-sub" type="submit" value="搜索"/>
        </form>
        <ul class="search-tips" id="searchTips">
            <li>小万</li>
            <li>王霸</li>
            <li>厉害</li>
            <li>搜索</li>
            <li>提示</li>
        </ul>
        <div class="hot-words">

            <c:forEach items="${hotSearchList}" var="hotSearch" varStatus="status">
                <a class="" href="${pageContext.request.contextPath}/essearch?term=${hotSearch.term}">
                        ${hotSearch.term}
                </a>
            </c:forEach>
        </div>
    </div>
    <div class="cart-box">
        <a class="cart" href="<spring:url value="/shoppingCart/cart" />">
            <i class="foundicon-cart"></i>
            <span>我的购物车</span>
            <span id="cartCount" class="cart-count">${empty order.commerceItemCount ? 0 : order.commerceItemCount}</span>
        </a>
    </div>
</div>

<div id="fixedHead" class="fixed-head-box">
    <div class="fixed-head">
        <div class="logo-box">
            <h1>
                <a href="<spring:url value="" />">
                    <img src="<spring:url value="${juedangpinStaticPath}/core/images/header/logo-red.jpg"/>"
                         alt="点金子名品"/>
                </a>
            </h1>
        </div>
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/essearch">
                <input name="term" class="search-text" type="text"/><!--
             --><input class="search-sub" type="submit" value="搜索"/>
            </form>
            <ul class="search-tips" id="fixedSearchTips">
                <li>小万</li>
                <li>王霸</li>
                <li>厉害</li>
                <li>搜索</li>
                <li>提示</li>
            </ul>
        </div>
        <div class="cart-box">
            <a href="<spring:url value="/shoppingCart/cart" />" class="cart">
                <i class="foundicon-cart"></i>
                <span>我的购物车</span>
                <span class="cart-count"
                      id="fixedCartCount">${empty order.commerceItemCount ? 0 : order.commerceItemCount}</span>
            </a>
        </div>
    </div>
</div>

<div class="nav-box">
    <div class="menu">
        <a class="menu-head" href="#">
            <span>全部商品分类</span>
        </a>

        <ul class="menu-list">
            <c:forEach items="${rootHomeCategories}" var="category" varStatus="status">
                <c:set var="rootCategory" value="${category.source}"/>
                <li class="menu-0<c:if test="${status.last eq true}">-1</c:if>">
                    <a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${rootCategory.id}"
                       class="menu-h">${rootCategory.name}</a>
                    <ul class="menu-1">
                        <c:forEach items="${rootCategory.children}" var="subCategory" varStatus="st">
                            <li <c:if test="${st.last eq true}">class="li-l"</c:if>><a
                                    href="${pageContext.request.contextPath}/essearch?parentCategoryId=${subCategory.id}">${subCategory.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>

    <a href="#" class="hottest">
        <h1 style="width: 300px">点金子绝当品震撼上线！</h1>
    </a>

    <div class="nav">
        <ul>
            <c:forEach var="category" items="${rootHomeCategories}" begin="0" end="5">
                <c:set var="rootCategory" value="${category.source}"/>
                <li><a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${rootCategory.id}">${rootCategory.name}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

