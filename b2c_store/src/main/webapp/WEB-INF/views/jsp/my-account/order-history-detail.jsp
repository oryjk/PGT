<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子绝当淘商城</title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/order-detail.css"/>"/>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>"/>
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <jsp:include page="../core/header-main.jsp" />
</div>

<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <jsp:include page="vertical-my-account-directory.jsp">
            <jsp:param name="step" value="orderHistory" />
        </jsp:include>

        <!-- 详细内容列表-->
        <!-- 根据订单的具体情况,添加step-x-->
        <c:set value="" var="step"/>
        <c:choose>
            <c:when test="${historyOrder.status==20}">
                <c:set value="1" var="step"/>
            </c:when>
            <c:when test="${historyOrder.status==30}">
                <c:set value="2" var="step"/>
            </c:when>
            <c:when test="${historyOrder.status==40}">
                <c:set value="4" var="step"/>
            </c:when>
            <c:when test="${historyOrder.status==100}">
                <c:set value="5" var="step"/>
            </c:when>
            <c:otherwise>
                <c:set value="1" var="step"/>
            </c:otherwise>
        </c:choose>
        <div id="main" class="main-box  step-${step}">

            <!--面包屑-->
            <div class="bread-nav">
                <p>
                    <a href="#">订单详情</a>
                </p>
            </div>

            <div class="floor-1">
                <h3>订单状态</h3>

                <div class="floor-content">
                    <div class="detail-status"></div>
                    <ul>
                        <li>
                            <p class="step-name">提交订单</p>

                            <p class="step-time">${historyOrder.submitDate}</p>

                        </li>
                        <li>
                            <p class="step-name">付款成功</p>

                            <p class="step-time"></p>

                            <p class="step-time"></p>
                        </li>
                        <li>
                            <p class="step-name">商品出库</p>

                            <p class="step-time"></p>

                            <p class="step-time"></p>
                        </li>
                        <li>
                            <p class="step-name">等待收货</p>

                            <c:if test="${historyOrder.status==40}">
                                <p class="step-time">运单号</p>

                                <p class="step-time">${historyOrder.shippingVO.trackingNumber}</p>
                            </c:if>
                        </li>
                        <li>
                            <p class="step-name">交易完成</p>

                            <p class="step-time"></p>

                            <p class="step-time"></p>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="floor-2">
                <h3>收货信息</h3>

                <div class="floor-content-wrap">
                    <div class="floor-content">
                        <h4>收货人信息</h4>
                        <div class="floor-row">
                            <div class="floor-col">收货人: <span>${historyOrder.shippingVO.shippingAddress.name}</span></div>
                            <div class="floor-col">手机号码: <span>${historyOrder.shippingVO.shippingAddress.phone}</span></div>
                        </div>
                        <div class="floor-row">地址:
                            <span>${historyOrder.shippingVO.shippingAddress.province} ${historyOrder.shippingVO.shippingAddress.city} ${historyOrder.shippingVO.shippingAddress.district} ${historyOrder.shippingVO.shippingAddress.address} </span>
                        </div>
                    </div>
                </div>
                <div class="floor-content-wrap">
                    <div class="floor-content">
                        <h4>支付和配货方式</h4>

                        <div class="floor-row">
                            <div class="floor-col">支付方式:<span>
                                <c:choose>
                                    <c:when test="${historyOrder.payment.type==1}">
                                        易宝支付
                                    </c:when>
                                    <c:when test="${historyOrder.payment.type==2}">
                                    支付宝支付
                                </c:when>
                                </c:choose>
                            </span></div>
                            <div class="floor-col">订单号:<span>${historyOrder.id}</span></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="floor-3">
                <h3>商品信息</h3>

                <div class="each-order">
                    <table>
                        <c:forEach var="item" items="${historyOrder.commerceItems}">
                            <tr>
                                <td class="col1">
                                    <a href="/product/${item.referenceId}"><img src="${item.snapshotMedia.path}" alt=""/></a>
                                </td>
                                <td class="col2">
                                    <a class="product-name" href="/product/${item.referenceId}">${item.name}</a>
                                </td>
                                <td class="col3">
                                    <span>${item.quality}</span>
                                </td>
                                <c:choose>
                                    <c:when test="${historyOrder.status>30}">
                                        <td class="col4">
                                            <p>顺丰快递</p>

                                            <p>${historyOrder.shippingVO.trackingNumber}</p>
                                        </td>
                                    </c:when>
                                </c:choose>
                                <td class="col6" rowspan="100">
                                    <p>数量:<span>${historyOrder.commerceItemCount}</span>件</p>

                                    <p>金额:<span class="cost">¥<span>${historyOrder.total}</span></span></p>
                                    <!-- 付款链接只有在未付款状态才显示-->
                                    <c:choose>
                                        <c:when test="${historyOrder.status>=40}">
                                            已付款
                                        </c:when>
                                        <c:otherwise>
                                            <a class="link-btn" href="/payment/gateway?orderId=${historyOrder.id}">付款</a>
                                        </c:otherwise>
                                    </c:choose>
                                    <!-- 已收货状态显示-->
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                </div>
            </div>
        </div>




        <div class="clear-float"></div>
        <!-- 类似商品-->
        <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
    </div>
</div>

<!--主脚部-->
<jsp:include page="../core/footer-main.jsp" />

<%--<!-- 弹出框-->
<div class="pop-up">
    <div class="inner">
        <h3>
            申请退货
            <span class="close">X</span>
        </h3>
        <form class="pop-content">

            <div class="row1">
                <label for="#">
                    <span>联系电话:</span>
                    <span class="pop-tips"></span>
                </label>
                <div class="text">
                    <input type="text"/>
                </div>
            </div>

            <div class="row1">
                <label for="#">
                    <span>退货原因:</span>
                </label>
                <div class="text">
                    <textarea name="" id="" cols="50" rows="4"></textarea>
                </div>
            </div>

            <div class="row1">
                <div class="text">
                    <span>请准确填写联系电话, 并详细说明退货原因. 我们将尽快与您联系. 绝当淘祝您购物愉快!</span>
                </div>
            </div>

            <div class="row2">
                <input class="d-btn" type="button" value="保存"/>
                <input class="l-btn" type="reset" value="取消"/>
            </div>
        </form>
    </div>
</div>--%>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" defer async="true" data-main="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/my-orders"/>"></script>
</html>