<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/side-bar/buy.css"/>"/>
<!--<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/index2.0/js/jquery-1.8.3.min.js"/>"></script>-->
<!--<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/side-bar/side-bar.js"/>" ></script>-->



<div class="n-all">
<div class="n-header">
    <div class="n-header-left">

        购物车
    </div>
    <div class="n-header-right"></div>

</div>

<div class="n-content">

    <c:forEach var="commerceItem" items="${order.commerceItems}">

    <div class="n-box1">
        <a class="cart-product" href="#">
            <img class="cart-thumnail" data-value="${commerceItem.referenceId}" src="${pageContext.request.contextPath}/resources${commerceItem['snapshotMedia']['path']}">
            <div class="n-box1-font">${commerceItem.name}</div>
            <div class="n-box1-font1"> ¥ <span><fmt:formatNumber value="${commerceItem.salePrice}" pattern="0.00" type="number" /></span></div>
            <a data-value="${commerceItem.referenceId}" class="n-box1-img sideRemoveCart" ><img class="remove-cart"  data-value="${commerceItem.referenceId}" src="<spring:url value="${juedangpinStaticPath}/core/images/header/s-del2.png"/>"></a>
        </a>
    </div>

    </c:forEach>

</div>
    <div class="n-footer">
        <div class="n-btn0">
        <div class="n-btn">已选1件</div>
        <div class="n-btn1"><fmt:formatNumber value="${order.subtotal}" pattern="0.00" type="number" /></div>
        </div>
        <a class="n-btn2"  onclick="javascript:window.location.href='..${urlConfiguration.shippingPage}?orderId=${order.id}'">结算</a>
    </div>
</div>