<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/user/login.css"/>"/>
</head>
<body>
<div class="header" id="header">
    <jsp:include page="../core/header-simple.jsp"/>
</div>

<div class="content-box">
    <div class="content" style="background: url('<spring:url value="${juedangpinStaticPath}/core/images/user/login-bg.jpg"/>') center 0 no-repeat;">
        <form:form modelAttribute="user" method="post" action="login" id="login">
            <div class="login-head">
                <c:set var="registerUrl" value="register"/>
                <c:if test="${param.redirect!=null}">
                    <c:set var="registerUrl" value="register?redirect=${param.redirect}"/>
                </c:if>
                <a href="${registerUrl}">
                    免费注册
                </a>

                <h1>登录</h1>
            </div>
            <div class="username-box">
                <label for="username"></label>
                <form:input path="username" class="username" id="username" type="text" placeholder="登录账户名"/>

            </div>
            <div class="password-box">
                <label for="password"></label>
                <form:input class="username" id="password" path="password" type="password" placeholder="密码"/>
            </div>
            <form:errors path="loginError"/>
            <div class="other-box">
                <form:checkbox id="autoLogin" path="autoLogin"/>
                <label for="autoLogin">自动登录</label>
                <a href="<spring:url value="/user/resetPassword"/>">忘记密码</a>
                <span class="hide" id="loginPrompt">请填写验证码</span>
            </div>
            <c:if test="${code!=null||user.count>2}">
            <div id="authBox" class="auth-box">
                <form:input id="authNum" path="authCode" type="text"/>
                    <img id="loginCode" src="<spring:url value="/code/login"/>" alt=""/>
                <a href="#">看不清楚？</a>
            </div>
            </c:if>
            <div class="sub-box">
                <input id="loginSub" type="submit" value="登录"/>
            </div>
            <form:input id="loginCount" type="hidden" path="count" value=""/>
            <input type="hidden" name="redirect" value="${param.redirect }"/>
        </form:form>
    </div>
</div>

<div class="footer" id="footer">
    <jsp:include page="../core/footer-simple.jsp"/>
</div>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/user/login.js"/>"></script>
</html>