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
    <title>点金子典当行绝当品销售平台</title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
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
                <jsp:param name="step" value="browsed" />
            </jsp:include>

            <!-- 详细内容列表-->
            <div id="main" class="main-box">
                <div class="content-search">
                    <%--
                    <input class="content-search" type="text" placeholder="商品搜索"/>
                    <a href=""><i class="foundicon-search"></i></a>
                    --%>
                </div>
                <!--面包屑-->
                <div class="bread-nav">
                    <p>
                        <a href="#">个人中心</a>
                        >
                        <a href="<spring:url value="/myAccount/browsedProducts"/>">最近浏览</a>
                    </p>
                </div>

                <div class="product-list">
                    <c:forEach var="product" items="${browsedProducts}">
                        <div class="list-product">
                            <div class="inner">
                                <a class="list-img-box" href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>">
                                    <img src="${pageContext.request.contextPath}/resources${product.source.frontMedia.path}"
                                         alt="${empty product.source.frontMedia.title ? product.source.name : product.source.frontMedia.title}"/></a>
                                <div class="list-price-box"><span>¥</span><span><fmt:formatNumber value="${product.source.salePrice}" pattern="0.00" type="number" /></span></div>
                                <p class="product-link"><a href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>">${product.source.name}+${product.source['stock']}</a></p>
                                <p><span></span></p>
                                <div class="product-handle">
                                </div>
                                <c:if test="${product.source['stock'] ne -1 and product.source['stock'] lt 1}">
                                    <div class="out-of-stock"></div>
                                </c:if>
                                <div class="product-message">添加成功</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp">
                <jsp:param name="excludeBrowsed" value="true" />
            </jsp:include>
        </div>
    </div>

    <jsp:include page="../core/footer-main.jsp" />
    <jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
    <script src="<spring:url value="${juedangpinStaticPath}/core/js/module/url.js"/>"></script>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.js"/>"></script>
</html>
