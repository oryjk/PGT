<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子典当行绝当品销售平台</title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/shopping-cart/cart.css"/>" />
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <div class="top-box">
        <div class="top-status">
            <div class="status-box">
                <ul class="top-nav">
                    <!--<li>
                        <a href="#">手机绝当品</a>
                    </li>-->
                    <li><a href="<spring:url value="/helpcenter/query"/>">帮助中心</a></li>
                    <!--<li><a href="#">网站导航</a></li>-->
                </ul>
                <ul class="have-login">
                    <li>
                        <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"><span> 欢迎您：</span><span>${currentUser.username}</span></a>
                    </li>
                    <li><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>">账户管理</a></li>
                    <li><a href="<spring:url value="${urlConfiguration.logoutPage}"/>">退出登录</a></li>
                </ul>
            </div>
        </div>
        <div class="top-banner">
            <div class="top-banner-box">
                <a href="#"></a>
            </div>
        </div>
    </div>
    <div class="head-box">
        <div class="logo-box">
            <h1>
                <a href="/">
                    购物车
                </a>
            </h1>
            <ul id="step" class="step4">
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
</div>

<!--正文-->
<div class="content-box">
    <div id="content4" class="content-4" style="display: block">
        <div class="content-title">
            <h2>支付成功</h2>
        </div>

        <div class="error"></div>

        <form>
            <fieldset class="success-box">
                <p class="look-order">
                    <a class="link-btn" href="<c:url value="/myAccount/orderHistory"/>">查看我的订单</a>
                </p>
                <div class="row">
                    <h3>支付已成功</h3>
                </div>
                <div class="row">
                    <h4 class="point-p">支付金额: <span class="cost">¥<span>${orderTotal }</span></span></h4>
                    <h3 >订单号: <span>${orderId }</span></h3>
                </div>
                <div class="row">
                    <p class="point-p">如果您有任何疑问请及时联系客服,感谢您的支持!</p>
                </div>
                <div class="row row-division">
                    <p>点金子提供在线支付保障,请您放心购买.</p>
                </div>
                <div class="row">
                    <p>支付成功后点金子会尽快为您发货,请您耐心等待.</p>
                </div>
                <div class="row">
                    <a class="right-a link-btn" href="<c:url value="/"/>"> > 返回首页</a>
                </div>
            </fieldset>
        </form>
    </div>

    <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
</div>

<!--主脚部-->
<jsp:include page="../core/footer-main.jsp"/>
<jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" defer async="true" data-main="<spring:url value="${juedangpinStaticPath}/shopping-cart/shopping-cart.js"/>"></script>

</html>