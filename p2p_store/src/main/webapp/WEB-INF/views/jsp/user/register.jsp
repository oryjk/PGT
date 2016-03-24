<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/user/regist.css"/>
    <link rel="stylesheet" href="/resources/core/header/header-con.css"/>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-login.jsp"/>
<!--header end-->
<!--content begin-->
<div class="content">
    <div class="content-box">
        <div class="content-img"></div>
        <div class="login">
            <div class="login-title">
                <h2>新用户注册</h2>
                <span>已有账号？<a href="/user/login"> 直接登陆</a></span>
            </div>
            <form:form modelAttribute="user" method="post" action="login" id="login">
            <div class="login-content">
                <!-- 出现错误请在div上面加上input-error页面, 聚焦请加上input-focus-->
                <div class="username input-error">
                    <form:input path="username" class="username" id="username" type="text" placeholder="登录账户名" v-model="username"/>
                    <span><form:errors path="userExist"/><form:errors path="username"/></span>
                </div>
                <div class="userPhone input-focus">
                    <input type="hidden" id="smsPath" data-value="/sms/register?phoneNumber=">
                    <form:input id="userPhone" path="phoneNumber" type="text"
                                placeholder="您的手机号"/>
                    <span><form:errors path="phoneNumber"/><form:errors path="phoneExist"/></span>
                </div>
                <div class="security-code">
                    <form:input class="security-code" path="authCode" type="text" placeholder="不区分大小写"/>
                    <img src="/code/register" class="authImgBox">
                    <span><form:errors path="authCode"/></span>
                </div>
                <div class="phone-security">
                    <form:input class="phone-security" id="phoneCom" path="smsCode" type="text"
                                placeholder="手机验证码"/>
                    <input class="getPhoneCom" id="getPhoneCom" type="button" value="获取"/>
                    <!-- <input name="getPhoneCom" type="button" value="60秒重新发送"> -->
                    <span><form:errors path="smsCode"/></span>
                </div>
                <div class="password">
                    <form:input id="password" path="password" type="password"
                                placeholder="6-20为数字或字母"/>
                    <span><form:errors path="password"/></span>
                </div>
                <div class="confirm">
                    <form:input id="confirm" path="password2" type="password" placeholder="再次确认密码"/>
                    <span><form:errors path="password2"/></span>
                </div>
                <input class="submit" type="submit" value="注册">
                <div class="agreement">
                    注册即接受<a href="#">点金子网《注册协议》</a>
                </div>
            </div>
            </form:form>
        </div>
    </div>
</div>

<!--content end-->
<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<jsp:include page="../core/baidu.jsp" />
<!--footer end-->
</body>

<script src="/resources/core/js/require.js"
        data-main="/resources/user/regist.js"></script>

</html>