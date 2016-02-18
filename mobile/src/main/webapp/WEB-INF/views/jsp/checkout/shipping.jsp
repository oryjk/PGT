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
    <link href="${pageContext.request.contextPath}/resources/static/shopping-cart/shopping-cart.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
</head>
<body>

<%@include file="../common/header.jsp"%>

<form action="${pageContext.request.contextPath}/alipay/request">
    <input type="hidden" value="${checkoutOrder.commerceItems.orderId}">
    <button class="submit">提交订单</button>
</form>

<%@include file="../common/footer.jsp"%>
</body>
</html>