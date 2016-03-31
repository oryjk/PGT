<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/help/help-center.css"/>
    <style>
        .main,
        .hover-main:hover {
            color: #c90304;
        }

        .em,
        .hover-em:hover {
            color: #86b4ed;
        }

        .bg-main,
        .hover-bg-main:hover {
            background: #c90304;
        }

        .bg-em,
        .hover-bg-em:hover {
            background: #86b4ed;
        }

        .content{
            height:1200px;
        }
    </style>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<!-- content begin -->
<div class="content">
    <ul class="content-nav">
        <li class="content-nav-title"><h2>帮助中心</h2></li>
        <c:forEach items="${helpCategorViewList}" var="categoryVo">
            <c:forEach items="${categoryVo.helpCenterList}" var="helpVo">
                <c:if test="${not empty helpVo}">
                    <li><a class="menu-level-end"   href="/helpcenter/${helpVo.id}">${helpVo.title}</></li>
                </c:if>
            </c:forEach>
        </c:forEach>
    </ul>
    <div class="content-main">
        <div class="content-main-title"><h2>帮助中心</h2></div>
        <div class="content-main-mid">
            <p>
                <c:if test="${not empty helpCenter.content}">
                    ${helpCenter.content}
                </c:if>
            </p>
        </div>
    </div>
</div>
<!-- content end -->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->
</body>