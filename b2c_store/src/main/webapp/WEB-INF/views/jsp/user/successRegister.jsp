<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/21/15
  Time: 7:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title></title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/user/regist.css"/>"/>

</head>
<body>
<div class="header" id="header">
  <jsp:include page="../core/header-simple.jsp"/>
</div>

<div class="content-box">
  <div class="content" style="background: url('<spring:url value="${juedangpinStaticPath}/core/images/user/regist-bg.jpg"/>') right center no-repeat;">
    <div class="success-box">
      <p class="success-message">注册成功!</p>

      <p class="to-login-page">正在为您跳转 等待 <span id="waitTime">5</span> 秒 <a class="link-btn" href="<spring:url value="/user/login"/>">马上登陆</a></p>

      <p class="to-home-page">暂时不登陆? <a class="link-btn" href="<spring:url value=""/>">去首页</a></p>
    </div>
  </div>
</div>

<div class="footer" id="footer">
  <jsp:include page="../core/footer-simple.jsp"/>
</div>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
  data-main="<spring:url value="${juedangpinStaticPath}/user/requireSuccess.js"/>"></script>
</html>
