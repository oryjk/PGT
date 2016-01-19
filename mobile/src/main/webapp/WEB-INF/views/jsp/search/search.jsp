<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath}/resources/static/search/search.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <a href="#" class="arrow"></a>
    <form action = "searchProduct">
        <input type="search" class="input1" name="term" placeholder="点击搜索宝贝" autocomplete="off">
        <input type="submit" value="提交">
    </form>
    <div class="kong"></div>
</div>
<div class="search">
    <div class="search-font">
        热门搜索
    </div>
    <div class="search-all">
        <c:forEach items="${categorys}" var="categorys">
            <a href="searchProduct?term=${categorys.name}" class="search-box">${categorys.name}</a>
        </c:forEach>
    </div>
    <a href="#" class="search-box1">清楚搜索历史纪录</a>
</div>
</body>
</html>