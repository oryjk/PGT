<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 11/16/15
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="logo-box">
  <h1 class="logo">
    <a href="<spring:url value="${urlConfiguration.homePage}"/>">
    点金子绝当品——欢迎登录
    <!--<img src="<spring:url value="${juedangpinStaticPath}/core/images/header/images/big-logo_pig.jpg"/>" alt=""/>-->
    <!--<div class="golds"></div>-->
    <div class="light"></div>
    </a>
  </h1>
  <h1 id="pageName">欢迎登录</h1>
</div>
