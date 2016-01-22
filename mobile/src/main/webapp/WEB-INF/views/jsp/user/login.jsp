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
    <link href="${pageContext.request.contextPath }/resources/static/login/login.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
</head>
<body>
<div class="header">
    <a href="#" class="arrow"></a>
    <div class="font">登录</div>
</div>

<form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath }/user/login" id="login">
    <form:errors path="loginError"/>
<div class="name">
    <div class="username">账号</div>
    <form:input path="username" class="text" id="username" name="username" type="text" placeholder="登录账户名"/>
    <div class="kong2"></div>
</div>
<div class="password">
    <div class="username">密码</div>
    <form:input class="text" id="password" name="password" path="password" type="password" placeholder="密码"/>
    <div class="kong2"></div>
</div>
<c:if test="${code!=null||user.count>2}">
    <div id="authBox" class="password">
        <div class="username">注册码</div>
        <form:input id="authNum" path="authCode" class="text" type="text"/>
        <img id="loginCode" src="<spring:url value="/code/login"/>" alt=""/>
        <a href="#">看不清楚？</a>
    </div>
</c:if>
<div class="login">
   <%-- <input id="autoLogin" name="autoLogin" type="checkbox" class="check"><div>自动登录</div>--%>

    <form:checkbox id="autoLogin" path="autoLogin" class="check"/>
    <div for="autoLogin">自动登录</div>
</div>

<form:input id="loginCount" type="hidden" path="count" value=""/>
<input type="hidden" name="redirect" value="${param.redirect }"/>


    <input type="submit" class="btn-clean" value="登录">

    </form:form>


<div class="font-1">
<a href="${pageContext.request.contextPath }/user/register" class="font1">免费注册</a>
<a href="#" class="font2">忘记密码</a>
</div>

<%@include file="../common/footer.jsp" %>

</body>
</html>