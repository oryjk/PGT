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
    <link href="${pageContext.request.contextPath }/resources/static/address/new-address.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/address/addAddress.js"></script>

</head>
<body>
<div class="header">
    <a href="${pageContext.request.contextPath }/my-account/person-info/address" class="arrow"></a>

    <div class="font">新增地址</div>
    <a href="#" class="dian">
        <ul class="menu">
            <li class="menu2">首页</li>
            <li class="menu3">分类</li>
            <li class="menu4">搜索</li>
            <li class="menu5">购物车</li>
            <li class="menu1">我的账户</li>
        </ul>
    </a>
</div>

<div class="content">

    <div class="name">
        <div class="kong2"></div>
        <span>姓名：</span><input id="name" name="name" class="text" type="text" value="${addressInfo.name}" >

        <div class="kong2"></div>
    </div>

    <div class="name1">
        <div class="kong2"></div>
        <span>手机：</span><input id="phone" name="phone" class="text" type="text" value="${addressInfo.phone}">

        <div class="kong2"></div>
    </div>
    <div class="name1">
        <div class="kong2"></div>
        <span>地址：</span>
        <div class="sel">
            <select id="province" name="province">
                <option value="volvo">请选择省份</option>

                <c:forEach items="${provinceList}" var="province">
                    <option id="province_op" name="province_op" data-value="${province.id}"   value="${province.name}">${province.name}</option>
                </c:forEach>
            </select>
            <select id="city" name="city">
                <option value="volvo">请选择城市</option>


            </select>
            <select id="district" name="district">
                <option value="volvo">请选择区县</option>

            </select>

        </div>
        <div class="kong2"></div>
    </div>
    <div class="name1">
        <div class="kong2"></div>
        <span>街道：</span><input id="address" name="address" class="text-new" type="text" value="${addressInfo.address}">

        <div class="kong2"></div>
    </div>

    <div class="btn-clean">
        <a href="#" onclick="addAddressSubmit()">提交</a>
    </div>

    </form>
</div>
<%@include file="../../common/footer.jsp" %>


</body>
</html>