/**
* Created by carlwang on 12/21/15.
*/
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title></title>
</head>
<body>
<div class="header" id="header">
	<jsp:include page="../core/header-simple.jsp"/>
</div>
<input type="hidden" id="redirect" value="${redirect}"/>

<div class="footer" id="footer">
	<jsp:include page="../core/footer-simple.jsp"/>
</div>
</body>
</html>