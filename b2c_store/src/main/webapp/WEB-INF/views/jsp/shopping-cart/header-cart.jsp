<%--
  Created by IntelliJ IDEA.
  User: jeniss
  Date: 15/12/6
  Time: 上午11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--主头部-->
<div class="header" id="header">
    <div class="top-box">
        <div class="top-status">
            <div class="status-box">
                <ul class="top-nav">
                    <li>
                        <a href="#">手机绝当品</a>
                    </li>
                    <li>
                        <a href="#">帮助中心</a>
                    </li>
                    <li>
                        <a href="#">网站导航</a>
                    </li>
                </ul>
                <c:choose>
                    <c:when test="${currentUser==null}">
                        <ul class="will-login">
                            <li><a href="<spring:url value="${urlConfiguration.loginPage}"/>"></i> 立即登录</a></li>
                            <li><a href="<spring:url value="${urlConfiguration.registerPage}"/>">免费注册</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="have-login">
                            <li>
                                <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"><span> 欢迎您：</span><span>${currentUser.username}</span></a>
                            </li>
                            <li><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>">账户管理</a></li>
                            <li><a href="<spring:url value="${urlConfiguration.logoutPage}"/>">退出登录</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="top-banner">
            <div class="top-banner-box">
                <a href="#"></a>
            </div>
        </div>
    </div>
    <div class="logo-box">
        <h1>
            <a href="<spring:url value="${urlConfiguration.homePage}"/>">
                购物车
                <img src="<spring:url value="${juedangpinStaticPath}/core/images/header/images/big-logo_pig.jpg"/>" alt=""/>
                <div class="golds"></div>
                <div class="light"></div>
            </a>
        </h1>
        <ul id="step" class="step1">
            <li>
                <h3 class="step1-text">我的购物车</h3>
            </li>
            <li>
                <h3 class="step2-text">确认订单</h3>
            </li>
            <li>
                <h3 class="step3-text">选择支付方式</h3>
            </li>
            <li>
                <h3 class="step4-text">支付成功</h3>
            </li>
        </ul>
    </div>
</div>

