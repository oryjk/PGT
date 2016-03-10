
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
<jsp:include page="../core/head.jspf">
	<jsp:param name="cssPath" value="/productList/productList.css" />
</jsp:include>
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
	<jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/pageView.js"/>"></script>
<script
	src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
	data-main="<spring:url value="${juedangpinStaticPath}/productList/productList.js"/>"></script>
</html>