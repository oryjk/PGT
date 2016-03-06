<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/user/login.css"/>
    <link rel="stylesheet" href="/resources/user/login.js"/>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp" />
<!--header end-->
<!--content begin-->
<div class="content">
    <div class="content-box">
        <div class="content-img"></div>

        <div class="login">

            <div class="login-title">
                <h2>登录点金子网</h2>
                <span>还没账号？<a href="/user/register"> 立即注册</a></span>
            </div>

            <form:form modelAttribute="user" method="post" action="login" id="login">

            <div class="login-box" id="login-box">
                <div class="username-box username-focus">
                    <label for="username"></label>
                    <form:input path="username" class="username" id="username" type="text" placeholder="登录账户名"/>
                </div>
                <!-- 下面div,使用.password-focus呈现蓝色,使用.password-wrong呈现红色.-->
                <div class="password-box password-focus password-wrong">
                    <label  for="password"></label>
                    <form:input class="username" id="password" path="password" type="password" placeholder="密码"/>
                </div>
                <form:errors path="loginError"/>
                <div class="other-box">
                    <form:checkbox id="autoLogin" path="autoLogin"/>
                    <label for="autoLogin">自动登录</label>
                    <a href="/user/resetPassword"/>忘记密码</a>
                    <!-- 下面span,使用.hide时隐藏,移除.hide时显示-->
                    <span class="hide" id="loginPrompt">请填写验证码</span>
                </div>
                <!-- 下面span,使用.hide时隐藏,移除.hide时显示-->
                <c:if test="${code!=null||user.count>2}">
                <div id="authBox" class="auth-box">
                    <form:input id="authNum" path="authCode" type="text"/>
                    <img id="loginCode" src="/code/login" alt=""/>
                    <a href="#">看不清楚？</a>
                </div>
                </c:if>
                <div class="sub-box">
                    <input id="loginSub" type="submit" value="登录"/>
                </div>
                <form:input id="loginCount" type="hidden" path="count" value=""/>
                <div class="domain">
                    快捷登录方式：
                    <a href="#"><img src="../core/images/user/QQ.png"></a>
                    <a href="#"><img src="../core/images/user/pay.jpg"></a>
                    <a href="#"><img src="../core/images/user/wet.jpg"></a>
                    <a href="#"><img src="../core/images/user/sina.jpg"></a>
                </div>
            </div>

            </form:form>
        </div>
    </div>
</div>
<!--content end-->


<!--footer begin-->
<jsp:include page="../core/footer-main.jsp" />
<!--footer end-->
</body>
</html>