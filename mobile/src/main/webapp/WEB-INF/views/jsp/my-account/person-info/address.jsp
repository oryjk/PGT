<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath }/resources/static/address/address.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/address/address.js"></script>
</head>
<body>
<div class="header">
    <a href="<spring:url value="/userinformation/query"/>" class="arrow"></a>
    <div class="font">收货地址管理</div>
</div>
<a>${defaultError}</a>
<c:forEach items="${addressList }" var="address" varStatus="i">
<div class="content">

    <div class="content-top ">
        <div class="name">${address.name }</div>
        <div class="phone">${address.phone }</div>
        <div class="ad">
            <c:if test="${i.index==0}">
                <a href="${pageContext.request.contextPath }/web/setDefaultAddress/${address.id}">默认</a>
            </c:if>
            <c:if test="${i.index!=0}">
                <a  onclick="setDefaultById(${address.id})">设为默认地址</a>
            </c:if>
        </div>
    </div>
    <div class="content-mid">
        <div class="content-font">
             ${address.province }${address.city }${address.district }
              <span>${address.address}</span>

        </div>
        <div class="content-change">
            <div class="kong3"></div>
            <a href="${pageContext.request.contextPath }/my-account/person-info/updateAddress/${address.id}" class="content-change-font1">编辑</a>

            <c:if test="${i.index!=0}">
                <div class="kong4">|</div>
                <a href="#" class="content-change-font2" onclick="deleteById(${address.id})">删除</a>
            </c:if>
            <div class="kong4"></div>
        </div>
    </div>

</div>
</c:forEach>


<a class="btn-clean" href="${pageContext.request.contextPath }/my-account/person-info/addAddress">
    添加新地址
</a>
<%@include file="../../common/footer.jsp" %>


</body>
</html>