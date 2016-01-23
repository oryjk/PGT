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
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <link href="${pageContext.request.contextPath}/resources/static/shopping-cart/shopping-cart.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/right.js"></script>
</head>
<body>

<%@include file="../common/header.jsp"%>

<content></content>

<data></data>

<a class="btn-clean">
    清空
</a>

<%@include file="../common/footer.jsp"%>
<script>
    $(function(){
        var url = "${pageContext.request.contextPath}/shoppingCart/ajaxCart";
        $.get(url, function(result){
            $("data").html(JSON.stringify(result));
            var del_url = '${pageContext.request.contextPath}/resources/static/img/del.png';
            for(var i = 0; i < result.data.order.commerceItemCount; i++){
                var img = "background:url(/resources" + result.data.order.commerceItems[i].snapshotMedia.path + ") no-repeat center center;background-size:100% 100%";
                $("content").append(
                    "<div class='box'>" +
                        "<input type='checkbox'>" +
                        "<div class='img1' style='" + img +"'></div>" +
                        "<div class='font'>" +
                            "<span class='font1-1'>" + result.data.order.commerceItems[i].name + "</span>" +
                            "<span class='font1-2'>" + result.data.order.commerceItems[i].salePrice +"</span>" +
                        "</div>" +
                        "<div class='btn1'>" +
                            "<img src=" + del_url + ">" +
                        "</div>" +
                    "</div>"
                )
            }
        },'json');
    })
</script>
</body>
</html>