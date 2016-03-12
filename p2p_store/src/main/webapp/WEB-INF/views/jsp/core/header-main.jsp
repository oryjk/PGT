<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangxiaodong
  Date: 16-2-23
  Time: 下午12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header">
    <div class="header-inner">
        <h1 class="logo">
            <a href="/"></a>
        </h1>
        <ul class="nav">
            <li><a href="/tender/tenderList">在当品大厅</a></li>
            <li><a href="/pawnPersonInfo/createPawnPersonInfo">在线典当</a></li>
            <li><a href="${b2cStoreUrl}">绝当品</a></li>
        </ul>
        <form class="search" action="">
            <input type="text" placeholder="点击搜索"/>
        </form>
        <div class="status">
            <div class="status-login"></div>
            <div class="status-logout"></div>
        </div>
    </div>
</div>

