<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="header">
    <a href="#" class="arrow"></a>

    <div class="font">我的点金子</div>
    <a href="#" class="dian">
        <ul class="menu">
            <li class="menu2"><a href="${pageContext.request.contextPath}/">首页</a></li>
            <li class="menu3"><a href="#">分类</a></li>
            <li class="menu4"><a href="${pageContext.request.contextPath}/product/search">搜索</a></li>
            <li class="menu5"><a href="${pageContext.request.contextPath}/shopping/shoppingCartList">购物车</a></li>
            <li class="menu1"><a href="#">我的账户</a></li>
        </ul>
    </a>
</div>