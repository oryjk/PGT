<%--
  Created by IntelliJ IDEA.
  User: cwang7
  Date: 10/18/15
  Time: 10:14 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/user/regist.css"/>"/>
</head>
<body>
<!--简单头部-->
<div class="header" id="header">
    <div class="logo-box">
        <h1 class="logo"><a href="<spring:url value="${urlConfiguration.loginPage}"/>">点金子绝当品——欢迎登陆</a></h1>

        <h1 id="pageName">欢迎注册</h1>
    </div>
</div>

<!--表格内容区-->
<div class="content-box">
    <div class="content">
        <form:form commandName="user" id="regist" method="post" action="register">
            <table>
                <tr>
                    <td><label>登录帐号</label></td>
                    <td class="input-texts" colspan="2">
                        <form:input id="username" path="username" type="text" placeholder="6-20位数字或字母"/></td>
                    <td class="prompt" id="usernamePrompt"><form:errors path="userExist"/></td>
                </tr>
                <tr>
                    <td><label>手机号码</label></td>
                    <td colspan="2"><form:input id="userPhone" path="phoneNumber" type="text"
                                                placeholder="用于密码保护"/></td>
                    <td class="prompt" id="userPhonePrompt"></td>
                </tr>
                <tr>
                    <td><label>登录密码</label></td>
                    <td colspan="2"><form:input id="password" path="password" type="password"
                                                placeholder="6-20为数字或字母"/></td>
                    <td class="prompt" id="passwordPrompt"></td>
                </tr>
                <tr>
                    <td><label>确认密码</label></td>
                    <td colspan="2"><form:input id="confirm" path="password2" type="password"
                                                placeholder="再次确认密码"/></td>
                    <td class="prompt" id="confirmPrompt"></td>
                </tr>

                <tr class="security-code-box">
                    <input type="hidden" id="smsPath" data-value="<spring:url value="/sms/register?phoneNumber="/>">
                    <td><label>验证码</label></td>
                    <td><form:input class="security-code" path="authCode" type="text" placeholder="不区分大小写"/></td>
                    <td><img id="loginCode" src="<spring:url value="/code/register"/>" alt="#"/></td>
                    <td class="prompt" id="securityCodePrompt"></td>
                </tr>
                <tr>
                    <td><label>手机验证码</label></td>
                    <td><form:input class="phone-security" id="phoneCom" path="smsCode" type="text"
                                    placeholder="手机验证码"/>
                    </td>
                    <td><input class="getPhoneCom" id="getPhoneCom" type="button" value="获取"/></td>
                    <td class="prompt" id="getPhoneComPrompt"></td>
                </tr>
                <tr>
                    <td>
                        <form:checkbox id="agree" path="agree"/>
                        同意<a class="regist-protocol" href="#">注册协议</a>
                    </td>
                    <td colspan="2"><input class="registSub" id="registSub" type="submit" value="免费注册"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td>已有账号？<a class="toLogin" href="login">立即登录</a></td>
                </tr>
            </table>
        </form:form>
    </div>
</div>


<!--简单脚部-->
<div class="footer" id="footer">
    <span>版权所有</span>
    <span>点金子绝当品</span>
    <span>京ICP备100000000号</span>
</div>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/user/regist.js"/>"></script>
</html>