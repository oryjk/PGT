<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 11/17/2015
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>" />
</head>
<body>
    <!--主头部-->
    <div class="header" id="header">
        <jsp:include page="../core/header-main.jsp" />
    </div>

    <!--正文-->
    <div class="content-box">

        <div class="content">
            <!-- 侧边栏-->
            <jsp:include page="vertical-my-account-directory.jsp">
                <jsp:param name="step" value="favourites" />
            </jsp:include>

            <!-- 详细内容列表-->
            <div id="main" class="main-box">
                <div class="content-search">
                    <input class="content-search" type="text" placeholder="商品搜索"/>
                    <a href=""><i class="foundicon-search"></i></a>
                </div>
                <!--面包屑-->
                <div class="bread-nav">
                    <p>
                        <a href="#">个人中心</a>
                        >
                        <a href="<spring:url value="/myAccount/favourites"/>">我的收藏</a>
                    </p>
                </div>

                <div class="product-list">
                    <c:forEach var="fav" items="${favourites.result}">
                        <div class="list-product">
                            <div class="inner">
                                <a class="list-img-box" href="<spring:url value="${urlConfiguration.pdpPage}/${fav.productId}"/>">
                                    <img src="${pageContext.request.contextPath}/resources${fav['snapshotMedia']['path']}"
                                         alt="${empty fav['snapshotMedia']['title'] ? fav.name : fav['snapshotMedia']['title']}"/></a>
                                <div class="list-price-box"><span>¥</span><span><fmt:formatNumber value="${fav.finalPrice}" pattern="#.00" type="number" /></span></div>
                                <p class="product-link"><a href="<spring:url value="${urlConfiguration.pdpPage}/${fav.productId}"/>" data-favourite-id="${fav.id}">${fav.name}</a></p>
                                <p><span>${fav.discussCount}</span> 评价</p>
                                <div class="product-handle">
                                    <a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i>取消</a>
                                    <a class="addEnjoy" data-value="${fav.productId}" href="#"><i class="foundicon-heart"></i>收藏</a>
                                    <a class="addCart" data-vaule="${fav.productId}" href="#"><i class="foundicon-cart"></i>购物车</a>
                                </div>
                                <c:if test="${searchHit.source['stock']<1}">
                                    <div class="out-of-stock"></div>
                                </c:if>
                                <div class="product-message">添加成功</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="page-box">
                    <ul>
                        <li><a href="#">上页</a></li>
                        <li class="page-list">
                            <ol>
                                <li><a class="page-focus" href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li><a href="#">6</a></li>
                            </ol>
                        </li>
                        <li><a href="#">下页</a></li>
                        <li class="page-count">共<span>6</span>页</li>
                        <li class="page-which">跳转到第<input type="text"/>页</li>
                        <li><input class="d-btn"  type="button" value="确认"/></li>
                    </ul>
                </div>
            </div>

            <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp">
                <jsp:param name="excludeFavourites" value="true" />
            </jsp:include>
        </div>
    </div>

    <jsp:include page="../core/footer-main.jsp" />
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.js"/>"></script>
</html>
