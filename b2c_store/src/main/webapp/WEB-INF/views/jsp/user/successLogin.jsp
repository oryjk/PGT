/**
* Created by carlwang on 12/21/15.
*/
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title></title>
</head>
<body>
<div class="header" id="header">
	<jsp:include page="../core/header-simple.jsp"/>
</div>
<input type="hidden" id="redirect" value="${redirect}"/>
<div class="content-box">
	<div class="content" style="background: url('<spring:url value="${juedangpinStaticPath}/core/images/user/login-bg.jpg"/>') center 0 no-repeat;">
		<div class="success-box">
			<p class="success-message">登陆成功!</p>
			<p class="to-home-page">正在为您跳转 等待 <span id="waitTime">5</span> 秒 <a class="link-btn" href="#">去首页</a></p>
			<p class="to-myaccount-page">管理我的账户 <a class="link-btn" href="#">去用户中心</a></p>
		</div>
	</div>
</div>

<div class="footer" id="footer">
	<jsp:include page="../core/footer-simple.jsp"/>
</div>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
data-main="<spring:url value="${juedangpinStaticPath}/user/loginSuccess.js"/>"></script>
</html>