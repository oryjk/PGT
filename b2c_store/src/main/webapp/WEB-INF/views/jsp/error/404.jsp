<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/10/15
  Time: 9:59 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>绝当品</title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/searchPage/searchPage.css"/>"/>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
</head>
<body>
<div class="header" id="header">
    <jsp:include page="../core/header-main.jsp"/>
</div>
<div class="content-box">
    <div class="content">

        <!-- 搜索无结果-->
        <div class="no-result">
            <h1>404错误!
                <small>对不起,您所访问的页面不存在或者已经删除.</small>
            </h1>
            <p class="row">
                <span id="countDown">5</span> 秒后跳到主页 <a id="stopCountDown" href="#">取消</a>
            </p>
            <input type="hidden" id="redirectUrl" value="<spring:url value="/"/>"/>
        </div>

        <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
    </div>
</div>
<jsp:include page="../core/footer-main.jsp"/>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" defer async="true"
        data-main="<spring:url value="${juedangpinStaticPath}/status404/status404"/>"></script>
</html>
