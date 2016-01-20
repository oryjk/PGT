<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath }/resources/static/sign-up/sign-up.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/sign-up/signup.js"></script>
</head>
<body>
<div class="header">
    <a href="#" class="arrow"></a>
    <div class="font">登录</div>
</div>
<form:form commandName="user" id="regist" method="post" action="${pageContext.request.contextPath }/web/wregister">
<div class="name">
    <div class="kong2"></div>
    <form:input id="username" name="username" path="username" class="text" type="text" placeholder="登录名:6-20位数字或字母"/>
    <div class="kong2"></div>
</div>
<div class="name1">
    <div class="kong2"></div>
    <form:input id="phoneNumber" name="phoneNumber" class="text" path="phoneNumber" type="text" placeholder="请输入手机号"/>
    <div class="kong2"></div>
</div>
<div class="name1">
    <div class="kong2"></div>
    <input id="authCode" name="authCode" type="hidden" value="4df5" >
    <form:input id="smsCode" name="smsCode" class="text" path="smsCode" type="text" placeholder="请输入验证码"/>
    <a  class="pass" id="getPhoneCom">获取验证码</a>
    <div class="kong2"></div>
</div>
<div class="name1">
    <div class="kong2"></div>
    <form:input id="password" name="password" class="text" path="password" type="password" placeholder="请输入密码"/>
    <div class="kong2"></div>
</div>
<div class="name1">
    <div class="kong2"></div>
    <form:input id="password2" name="password2" class="text" path="password2" type="password"  placeholder="再次确认密码"/>
    <div class="kong2"></div>
</div>
<div class="login">
    <input type="checkbox" class="check"><div>我已阅读同意<a href="#">《xxxx》协议</a></div>
</div>

    <input type="submit" class="btn-clean" value="注册">

</form:form>
<%@include file="../common/footer.jsp" %>


</body>
</html>