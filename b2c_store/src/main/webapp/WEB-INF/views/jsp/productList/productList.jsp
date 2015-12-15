
<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 11/16/15
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>绝当品</title>
<link rel="stylesheet"
	href="<spring:url value="${juedangpinStaticPath}/productList/productList.css"/>" />
<script
	src="<spring:url value="${juedangpinStaticPath}/core/js/pageView.js"/>"></script>
</head>
<body>
	<!--主头部-->
	<div class="header" id="header">
		<jsp:include page="../core/header-main.jsp" />
	</div>
	<!--正文-->

	
	  <c:if test="${!empty ES}">
	  <jsp:include page="productList_es.jsp" />
	  </c:if>
	  

	  <c:if test="${empty ES}">
	  <jsp:include page="productList_db.jsp" />
	  </c:if>
	

	<jsp:include page="../core/footer-main.jsp" />
</body>
<script
	src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
	data-main="<spring:url value="${juedangpinStaticPath}/productList/productList.js"/>"></script>
</html>