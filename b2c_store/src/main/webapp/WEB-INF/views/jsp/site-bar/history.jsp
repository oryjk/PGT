<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/side-bar/history.css"/>"/>
<!--<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/index2.0/js/jquery-1.8.3.min.js"/>"></script>-->
<!--<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/side-bar/side-bar.js"/>" ></script>-->




<div class="n-all">
    <div class="n-header">
        <div class="n-header-left">
                浏览历史
        </div>
        <div class="n-header-right"></div>

    </div>

    <div class="n-content">

        <c:forEach var="product" items="${browsedProducts}">

        <div class="n-box1">
            <a href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>">
                <img data-value="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>" src="${pageContext.request.contextPath}/resources${product.source.frontMedia.path}">

                <div class="n-box1-font">${product.source.name}</div>
                <div class="n-box1-font1"><fmt:formatNumber value="${product.source.salePrice}" pattern="0.00" type="number" /></div>
                <a href="#" data-value="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>" class="n-box1-img"><img src="<spring:url value="${juedangpinStaticPath}/core/images/header/ss-sc.png"/>"></a>
                <a href="#" data-value="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>" class="n-box1-img1"><img src=" <spring:url value="${juedangpinStaticPath}/core/images/header/ss-buy.png"/>"></a>
            </a>


        </div>

        </c:forEach>

    </div>
</div>