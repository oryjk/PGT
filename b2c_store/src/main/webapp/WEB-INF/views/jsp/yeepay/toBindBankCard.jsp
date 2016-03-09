<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" language="java"
	contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
var jsRoot = "<c:url value="/"/>";
</script>
</head>
<body>
	<form action="<c:url value="/yeepay/getSgin"/>" method="post" id="yeepayDataForm" auto-submit="Y">
		userid:<input name="platformUserNo" type="text" value="${platformUserNo }" /><br/>
		<input name="serviceName" type="hidden" value = "${serviceName }"/>
		<input type="submit" value="提交"/>
	</form>

	<jsp:include page="include/yeepayGatewayForm.jsp"></jsp:include>
	
	
	<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/yeepay/gateway.js"/>"></script>
	<jsp:include page="../../core/baidu.jsp"></jsp:include>
</body>
</html>