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
    <style>
        .btn1{
            cursor: pointer;
        }
        .btn2{
            cursor: pointer;
        }
    </style>
</head>
<body>

<%@include file="../common/header.jsp"%>

<div class="ding">
    <div class="bg2">
        <div id="total"></div>
        <div><span>运费：</span><span id="fee"></span></div>
    </div>
    <div class="btn2">
        结算
    </div>
</div>

<content></content>

<data></data>

<a class="btn-clean">
    清空
</a>

<%@include file="../common/footer.jsp"%>
<script>
    $(function(){
        var order = new Object();
        var url = "${pageContext.request.contextPath}/shoppingCart/ajaxCart";
        $.get(url, function(result){
            $("data").html(JSON.stringify(result));
            var del_url = '${pageContext.request.contextPath}/resources/static/img/del.png';
            for(var i = 0; i < result.data.order.commerceItemCount; i++){
                var img = "background:url(/resources" + result.data.order.commerceItems[i].snapshotMedia.path + ") no-repeat center center;background-size:100% 100%";
                $("content").append(
                    "<div class='box'>" +
                        "<div class='img1' style='" + img +"'></div>" +
                        "<div class='font'>" +
                            "<span class='font1-1'>" + result.data.order.commerceItems[i].name + "</span>" +
                            "<span class='font1-2'>" + result.data.order.commerceItems[i].salePrice +"</span>" +
                        "</div>" +
                        "<div class='btn1' productIds=" + result.data.order.commerceItems[i].referenceId + ">" +
                            "<img src=" + del_url + ">" +
                        "</div>" +
                    "</div>"
                )
            }
            $("#fee").text(result.data.order.shippingFee);
            $("#total").text(result.data.order.total);
            order.orderId = result.data.order.id;
        },'json');

        $(".btn1").live("click",function(){
            var del_url = '${pageContext.request.contextPath}/shoppingCart/ajaxRemoveItemFromOrder';
            var productId = $(this).attr("productIds");
            $.post(del_url, { productId: productId },
                    function(data){
                            if(data.success == 1) {
                                window.location.reload();
                            }
            });
        });

        $(".btn2").live("click",function(){
            var url = '${pageContext.request.contextPath}/checkout/shipping';
            window.location.href = url + "?orderId=" + order.orderId;
        });
    })
</script>
</body>
</html>