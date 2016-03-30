<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<h1>登录成功!</h1>
<h2><a href="<spring:url value="/"/>">去首页</a></h2>
<h2><a href="<spring:url value="/userinformation/query"/>">去我的账户</a></h2>
</body>
</html>