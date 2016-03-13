
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
	<script type="text/javascript">
	var jsRoot = "<c:url value="/"/>";
	</script>
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
	            <!-- 填写个人信息-->
	            <!-- 填写个人信息-->
	            <form class="info-import" style="display: block" action="<c:url value="/yeepay/getSgin"/>" method="post" id="yeepayDataForm">
	                <input name="platformUserNo" type="hidden" value="${platformUserNo }" /><br/>
					<input name="serviceName" type="hidden" value = "${serviceName }"/>
					<input name="withdrawType" type="hidden" value = "${yeepayConfig.withdrawType }"/>
	                <div class="row1">
	                    <span class="row-title">提现金额 :</span>
	                    <span class="row-text"><input name="amount" /></span>
	                </div>
	                <div class=row2>
	                    <input class="yeepay-btn" type="submit" value="提交"/>
	                </div>
	            </form>
	            <jsp:include page="include/yeepayGatewayForm.jsp"></jsp:include>
	        </div>
        </div>
    </div>

    <jsp:include page="../../core/footer-main.jsp" />
    <script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/yeepay/gateway.js"/>"></script>
	<jsp:include page="../../core/baidu.jsp"></jsp:include>
</body>
        
</html>