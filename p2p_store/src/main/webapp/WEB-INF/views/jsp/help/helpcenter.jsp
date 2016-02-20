<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子绝当淘商城</title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/help/helpcenter.css"/>"/>
</head>
<body>

<div class="header" id="header">
    <jsp:include page="../core/header-main.jsp"/>
</div>


<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <div class="aside">
            <div class="aside1"><h2>帮助中心</h2></div>
        <ul>
            <c:forEach items="${helpCenterList}" var="categoryVo">
                <li><a class="menu-level-1" >${categoryVo.category.name}</a>
                    <ul>
                        <c:forEach items="${categoryVo.helpCenterList}" var="helpVo">

                            <c:if test="${helpVo.id == helpCenter.id}">
                                <li><a    class="menu-level-end current-page" href="${pageContext.request.contextPath}/helpcenter/${helpVo.id}">${helpVo.title}</></li>

                            </c:if>

                            <c:if test="${helpVo.id != helpCenter.id}">
                                <li><a  class="menu-level-end"   href="${pageContext.request.contextPath}/helpcenter/${helpVo.id}">${helpVo.title}</></li>
                            </c:if>

                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>


         </ul>
        </div>

        <div id="main-box" class="main-box">

            <div class="main-right">
                <!--面包屑-->
                <div class="bread-nav">
                    <p>
                        <a href="#">帮助中心</a>
                        >
                        <a href="#">${helpCenter.category.name}</a>
                        >
                        <a href="#">${helpCenter.title}</a>
                    </p>
                </div>
                <c:if test="${not empty helpCenter.content}">
                	${helpCenter.content}
                </c:if>
                 <c:if test="${empty helpCenter.content}">
                	<img src="${pageContext.request.contextPath}/resources/${helpCenter.frontMedia.path}" alt="${helpCenter.title}">
                </c:if>
            </div>

        </div>

    <div class="clear-float"></div>

    <!-- 类似商品-->
    <div class="similar-box">




        <div class="move-left-box">
            <a href="#"  id="moveLeft"><</a>
        </div>
        <div class="move-right-box">
            <a href="#"  id="moveRight">></a>
        </div>
    </div>

    <div class="clear-float"></div>

      </div>
    </div>

      <jsp:include page="../core/footer-main.jsp" />
</body>

<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" data-main="<spring:url value="${juedangpinStaticPath}/help/helpcenter.js"/>" defer async="true"></script>
<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/admin/ueditor.config.js"/>"></script>
<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/admin/ueditor.all.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/admin/ueditor.parse.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/admin/lang/zh-cn/zh-cn.js"/>"></script>

</html>