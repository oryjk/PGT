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

<form action="<c:out value="${requestURL }"/>" id="yeepayGatewayForm" method="post" style="display:none" auto-submit="Y">
	<input id="req" name="req" type="hidden" value="<c:out value="${req }"/>"></input>
	<input id="sign" name="sign" type="hidden" value="<c:out value="${sign }"/>">
	</input> <input type="submit" value="提交"></input>
</form>	
	<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/yeepay/gateway.js"/>"></script>
</body>
</html>