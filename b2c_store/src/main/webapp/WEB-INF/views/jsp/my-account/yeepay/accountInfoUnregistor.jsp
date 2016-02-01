<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子绝当淘商城</title>
	<link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
     <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/pay-binding/pay-binding.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>" />
</head>
<body>
    <!--主头部-->
    <div class="header" id="header">
        <jsp:include page="../../core/header-main.jsp" />
    </div>

    <!--正文-->
    <div class="content-box">

        <div class="content">

            <!-- 侧边栏-->
            <jsp:include page="../vertical-my-account-directory.jsp"/>

            <!-- 详细内容列表-->
            <div id="main" class="main-box">

	            <!--面包屑-->
	            <div class="bread-nav">
	                <p>
	                    <a href="#">个人中心</a>
	                    &gt;
	                    <a href="#">支付绑定</a>
	                </p>
	            </div>
	
	            <!-- 未绑定-->
	            <div class="no-binding" style="display: block">
	                <div class="message-box">
	                    <p>您未绑定易宝!</p>
	                </div>
	                <div class="row1">
	                    <p>为了使您购物更加安全方便快捷,点金子推荐用户与易宝支付绑定,祝您购物愉快!</p>
	                </div>
	                <div class="row2">
	                	<form action="<c:url value="/yeepay/yeepayForm"/>" method="get">
	                	<input type="hidden" name="serviceName" value="toRegister"/>
	                	<input class="yeepay-btn" type="submit" value="绑定易宝">
	                    <span>点击跳转到易宝绑定页面</span>
	                	</form>
	                </div>
	            </div>
	        </div>
        </div>
    </div>

    <jsp:include page="../../core/footer-main.jsp" />
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.js"/>"></script>
</html>
