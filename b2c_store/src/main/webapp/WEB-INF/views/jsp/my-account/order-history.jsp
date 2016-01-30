<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 11/8/2015
  Time: 12:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/my-orders.css"/>" />
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
        <div id="main" class="main-box">
            <%--
            <a class="order-trash" href="#">
                <i class="foundicon-trash"></i>
                订单回收站
            </a>
            --%>
            <!--面包屑-->
            <div class="bread-nav">
                <p>
                    <a href="#">个人中心</a>
                    >
                    <a href="<spring:url value="/myAccount/orderHistory"/>">我的订单</a>
                </p>
            </div>
            <!-- 筛选-->
            <div class="filter-box">
                <form action="<spring:url value="/myAccount/orderHistory"/>" class="content-search">
                    <input class="content-search" type="text" name="keyword" placeholder="商品搜索"/>
                    <a href="#" class="search-link-btn" data-url="<spring:url value="/myAccount/orderHistory?keyword="/>" ><i class="foundicon-search"></i></a>
                    <a class="clear-search link-btn" href="<spring:url value="/myAccount/orderHistory"/>" >清除</a>
                </form>
                <ul id="tab" class="tab">
                    <li class="${empty param.status ? 'choose' : ''}"><h2 data-tab="0"><a href="<spring:url value="/myAccount/orderHistory"/>">全部订单</a></h2></li>
                    <li class="${param.status eq 20 ? 'choose' : ''}"><h2 data-tab="20"><a href="<spring:url value="/myAccount/orderHistory?status=20"/>">待付款</a></h2></li>
                    <li class="${param.status eq 30 ? 'choose' : ''}"><h2 data-tab="30"><a href="<spring:url value="/myAccount/orderHistory?status=30"/>">待收货</a></h2></li>
                    <li class="${param.status eq 100 ? 'choose' : ''}"><h2 data-tab="100"><a href="<spring:url value="/myAccount/orderHistory?status=100"/>">已完成</a></h2></li>
                </ul>
                <div></div>
            </div>
            <c:if test="${fn:length(historyOrders.result) gt 0}">
                <!-- 订单列表-->
                <div class="order-list">
                    <table class="order-list-head">
                        <tr>
                            <th class="col1">
                                商品订单
                            </th>
                            <th class="col5">收货人</th>
                            <th class="col6">总计</th>
                            <th class="col7">订单状态</th>
                        </tr>
                    </table>

                        <%--有订单时显示为.have-order 为display为block,否则为none--%>
                    <div class="have-order" id="orderList" style="display: block">
                        <c:forEach var="order" items="${historyOrders.result}">
                            <div class="each-order">
                            <c:if test="${order.status gt 1}">
                                <div class="order-info">
                                <div class="operate">
                                <c:if test="${order.status lt 3}">
                                    <!-- 取消订单链接只有在未付款状态才显示-->
                                    <a class="link-btn" href="#">取消订单</a>
                                </c:if>
                                </div>
                                    下单时间: <span class="order-time"><fmt:formatDate value="${order.submitDate}"
                                                                                   pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                订单号: <span class="order-number">${order.id}</span>
                                物流:
                                <c:choose>
                                    <c:when test="${order.status gt 3}">
                                        <span>已送达</span>
                                        <a class="link-btn" href="#"></a>
                                    </c:when>
                                    <c:otherwise>
                                        <span>尚未出库</span>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                            </c:if>
                            <table>
                            <c:forEach var="commerceItem" items="${order.commerceItems}" varStatus="ci_index">
                                <tr>
                                    <td class="col1">
                                        <a href="<spring:url value="${urlConfiguration.pdpPage}/${commerceItem.referenceId}"/>">
                                            <img src="${pageContext.request.contextPath}/resources${commerceItem['snapshotMedia']['path']}"
                                                 alt="${empty commerceItem['snapshotMedia']['title'] ? commerceItem.name : commerceItem['snapshotMedia']['title']}"/>
                                        </a>
                                    </td>
                                <td class="col2">
                                <a class="product-name" href="<spring:url value="${urlConfiguration.pdpPage}/${commerceItem.referenceId}"/>">${commerceItem.name}</a>
                                </td>

                                    <td class="col3"><a href="${pageContext.request.contextPath}/myAccount/orderHistoryDetails?orderId=${order.id}" class="product-name">订单详情</a></td>
                                    </td>
                                    <td class="col3">
                                <span>${commerceItem.quality}</span>
                                </td>
                                <td class="col4">
                                <%--
                                <a class="link-btn" href="#">申请退换货</a>
                                --%>
                                </td>
                                <c:if test="${ci_index.index eq 0}">
                                    <td class="col5" rowspan="100">${order.shippingVO.shippingAddress.name}</td>
                                    <td class="col6" rowspan="100">
                                    <span>¥</span><span><fmt:formatNumber value="${order.total}" pattern="0.00" type="number" /></span>
                                    </td>
                                    <td class="col7" rowspan="100">
                                    <c:choose>
                                        <c:when test="${order.status eq 20||order.status eq 25}">
                                            <span>待付款</span>
                                            <a class="link-btn" href="<spring:url value="/payment/gateway?orderId=${order.id}"/>">付款</a>
                                        </c:when>
                                        <c:when test="${order.status eq 30}">
                                            <span>待收货</span>
                                        </c:when>
                                        <c:when test="${order.status eq 100}">
                                            <span>已完成</span>
                                        </c:when>
                                        <c:when test="${order.status eq -10}">
                                            <span>已取消</span>
                                        </c:when>
                                    </c:choose>
                                    </td>
                                </c:if>
                                </tr>
                            </c:forEach>
                            </table>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="no-order" style="display: none">
                        <p class="no-order-text">没有订单 <a class="link-btn" href="#">去购物车结算</a> <a class="link-btn" href="#">回到首页</a></p>
                    </div>
                </div>
                <!-- 分页-->
                <div class="page-box">
                    <ul>
                        <c:if test="${historyOrders.currentIndex gt 0}">
                            <li><a id="previousPage"
                                   href="<spring:url value="/myAccount/orderHistory?keyword=${param.keyword}&currentIndex=${historyOrders.currentIndex - 1}"/>">上页</a>
                            </li>
                        </c:if>
                        <li class="page-list">
                            <ol id="pages">
                                <c:forEach var="pageNum" items="${historyOrders.pageNumbers}">
                                    <c:choose>
                                        <c:when test="${pageNum eq historyOrders.currentIndex}">
                                            <li><a class="page-focus" href="#">${pageNum + 1}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="<spring:url value="/myAccount/orderHistory?keyword=${param.keyword}&currentIndex=${pageNum}"/>">${pageNum + 1}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ol>
                        </li>
                        <c:if test="${historyOrders.currentIndex lt historyOrders.maxIndex}">
                            <li><a id="nextPage"
                                   href="<spring:url value="/myAccount/orderHistory?keyword=${param.keyword}&currentIndex=${historyOrders.currentIndex + 1}"/>">下页</a>
                            </li>
                        </c:if>
                        <li class="page-count">共<span id="pageCount">${historyOrders.maxIndex + 1}</span>页</li>
                        <form action="<spring:url value="/myAccount/orderHistory"/>" method="get">
                            <input type="hidden" name="keyword" value="${param.keyword}"/>
                            <li class="page-which">跳转到第<input name="currentIndex" type="text"/>页</li>
                            <li><input class="d-btn" id="pageSub" type="submit" value="确认"/></li>
                        </form>
                    </ul>
                </div>
            </c:if>
        </div>

        <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
    </div>
</div>
<jsp:include page="../core/footer-main.jsp" />
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/my-orders"/>"></script>
</html>