<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子典当行绝当品销售平台</title>
	<link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/pay-binding/pay-binding.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>" />
</head>
<body>
    <!--主头部-->
    <div class="header" id="header">
        <jsp:include page="../../core/header-main.jsp" />
    </div>

    <!--正文-->
    <div class="content-box">

        <div class="content">

            <!-- 侧边栏-->
            <jsp:include page="../vertical-my-account-directory.jsp"/>
            <!-- 详细内容列表-->
            <div id="main" class="main-box">

	            <!--面包屑-->
	            <div class="bread-nav">
	                <p>
	                    <a href="#">个人中心</a>
	                    &gt;
	                    <a href="#">支付绑定</a>
	                </p>
	            </div>
	            <!-- 已绑定-->
	            <div class="have-binding" style="display: block">
	                <div class="message-box">
	                    <p>
	                        已绑定易宝账户:
	                        <span>${accountNo }</span>
	                    </p>
	                </div>
	                <div class="yeepay-info">
	                    <div class="row1">
	                        <span class="row-title">易宝账户 :</span>
	                        <span class="row-text">${accountNo }</span>
	                    </div>
	                    <div class="row1">
	                        <span class="row-title">账户类型 :</span>
	                        <span class="row-text"><c:choose><c:when test="${result['memberType'] eq 'PERSONAL'}">个人会员</c:when><c:when test="${result['memberType'] eq 'ENTERPRISE'}">企业会员</c:when><c:otherwise>担保公司</c:otherwise></c:choose></span>
	                    </div>
	                    <div class="row1">
	                        <span class="row-title">银行卡号 :</span>
	                        <c:choose>
	                        <c:when test="${empty result['cardNo'] }">
	                        	<span class="row-text">未绑定</span>
	                        	<a class="link-btn" href="<c:url value="/yeepay/yeepayForm?serviceName=toBindBankCard"/>">绑卡</a>
	                        </c:when>
	                        <c:otherwise>
	                        	<span class="row-text">${result['cardNo'] }</span>
	                        	<a class="link-btn" href="<c:url value="/yeepay/yeepayForm?serviceName=toUnbindBankCard"/>">解绑</a>
	                        </c:otherwise>
	                        </c:choose>
	                        
	                        
	                    </div>
	                </div>
	                <div class="person-info">
	                    <div class="row1">
	                        <span class="row-title">姓名 :</span>
	                        <span class="row-text">${name }</span>
	                    </div>
	                    <div class="row1">
	                        <span class="row-title">绑定手机号 :</span>
	                        <span class="row-text">${result['bindMobileNo']}</span>
	                    </div>
	                    <div class="row1">
	                        <span class="row-title">身份证号 :</span>
	                        <span class="row-text">${idNo }</span>
	                    </div>
	                </div>
	                <div class="money-info">
	                    <div class="row1">
	                        <span class="row-title">可用余额 :</span>
	                        <span class="row-text">
	                            <span>¥</span>
	                            <span>${result['availableAmount']}</span>
	                        </span>
	                    </div>
	                    <div class="row1">
	                        <span class="row-title empty-title"></span>
	                        <span class="row-text">
	                            <a class="link-btn" href="<c:url value="/yeepay/yeepayForm?serviceName=toRecharge"/>">充值</a>
	                            <span class="divide">|</span>
	                            <a class="link-btn" href="<c:url value="/yeepay/yeepayForm?serviceName=toWithdraw"/>">提现</a>
	                        </span>
	                    </div>
	                </div>
	            </div>
	        </div>
        </div>
    </div>

    <jsp:include page="../../core/footer-main.jsp" />
	<jsp:include page="../core/baidu.jsp"></jsp:include>
    
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.js"/>"></script>
</html>
	
