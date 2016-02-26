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
	
	            <!-- 未绑定-->
	            <div class="no-binding" style="display: block">
	                <div class="message-box">
	                    <p>跳转至易宝中......请等待</p>
	                </div>
	            </div>
	        </div>
        </div>
    </div>

    <jsp:include page="../../core/footer-main.jsp" />
    <form action="<c:url value="/yeepay/getSgin"/>" method="post" id="yeepayDataForm" auto-submit="Y" style="display:none">
		userid:<input name="platformUserNo" type="text" value="${platformUserNo }" /><br/>
		<input name="serviceName" type="hidden" value = "${serviceName }"/>
		<input type="submit" value="提交"/>
	</form>

	<jsp:include page="include/yeepayGatewayForm.jsp"></jsp:include>
	
	
	<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/yeepay/gateway.js"/>"></script>
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.js"/>"></script>
</html>
