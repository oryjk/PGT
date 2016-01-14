<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../core/head.jspf">
    <jsp:param name="cssPath" value="/index/index.css" />
</jsp:include>
<body>
<!--头部-->
<div class="header" id="header">

    <c:if test="${ES==true}">
        <jsp:include page="../core/header-main.jsp"/>
    </c:if>

    <c:if test="${ES==false}">
        <jsp:include page="../core/header-main-db.jsp"/>
    </c:if>

</div>

<!--正文-->

<div id="content" class="content-box" style="
background:  url('<spring:url value="${juedangpinStaticPath}${pageBackground.headerMedia.path}"/>') center 500px no-repeat,
url('<spring:url value="${juedangpinStaticPath}${pageBackground.footerMedia.path}"/>') center bottom no-repeat,
url('<spring:url value="${juedangpinStaticPath}${pageBackground.middleMedia.path}"/>') center top repeat-y
">

    <c:if test="${ES==true}">

        <jsp:include page="index_es.jsp"/>

    </c:if>

    <c:if test="${ES==false}">

        <jsp:include page="index_db.jsp"/>

    </c:if>

</div>
<jsp:include page="../core/helpSide.jsp"/>
<jsp:include page="../core/footer-main.jsp"/>

<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
data-main="<spring:url value="${juedangpinStaticPath}/index/index.js"/>"></script>
</body>
</html>