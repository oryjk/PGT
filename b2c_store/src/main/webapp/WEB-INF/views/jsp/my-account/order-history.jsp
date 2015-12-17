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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
                <a class="order-trash" href="#">
                    <i class="foundicon-trash"></i>
                    订单回收站
                </a>
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
                    <div class="content-search">
                        <input class="content-search" type="text" placeholder="订单号或商品名" />
                        <a href=""><i class="foundicon-search"></i></a>
                    </div>
                    <ul id="tab" class="tab">
                        <li class="choose"><h2 data-tab="0">全部订单</h2></li>
                        <li><h2 data-tab="1">待付款</h2></li>
                        <li><h2 data-tab="1">待收货</h2></li>
                        <li><h2 data-tab="1">已完成</h2></li>
                    </ul>
                    <div></div>
                </div>
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
                                    下单时间:<span class="order-time">${order.submitDate}</span>
                                    订单号:<span class="order-number">${order.id}</span>
                                    物流:
                                    <c:choose>
                                        <c:when test="${order.status gt 3}">
                                            <span>已经送达</span>
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
                                            <img src="${pageContext.request.contextPath}/resources${commerceItem['snapshotMedia']['path']}"
                                                 alt="${empty commerceItem['snapshotMedia']['title'] ? commerceItem.name : commerceItem['snapshotMedia']['title']}" />
                                        </td>
                                        <td class="col2">
                                            <a class="product-name" href="<spring:url value="${urlConfiguration.pdpPage}/${commerceItem.referenceId}"/>">${commerceItem.name}</a>
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
                                            <td class="col5" rowspan="100">关羽</td>
                                            <td class="col6" rowspan="100">
                                                <span>¥</span><span><fmt:formatNumber value="${order.total}" pattern="0.00" type="number" /></span>
                                            </td>
                                            <td class="col7" rowspan="100">
                                                <c:choose>
                                                    <c:when test="${order.status eq 2}">
                                                        <span>待付款</span>
                                                        <a class="link-btn" href="#">付款</a>
                                                    </c:when>
                                                    <c:when test="${order.status eq 3}">
                                                        <span>已付款</span>
                                                    </c:when>
                                                    <c:when test="${order.status eq 4}">
                                                        <span>待确认完成</span>
                                                    </c:when>
                                                    <c:when test="${order.status eq 5}">
                                                        <span>已完成</span>
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
                <!-- 分页-->
                <div class="page-box">
                    <ul>
                        <li><a id="previousPage" href="#">上页</a></li>
                        <li class="page-list">
                            <ol id="pages">
                                <li><a class="page-focus" href="#">1</a></li>
                                <li><a href="#">2</a></li>
                            </ol>
                        </li>
                        <li><a id="nextPage" href="#">下页</a></li>
                        <li class="page-count">共<span id="pageCount">6</span>页</li>
                        <li class="page-which">跳转到第<input id="pageWhich" type="text"/>页</li>
                        <li><input class="d-btn" id="pageSub"  type="button" value="确认"/></li>
                    </ul>
                </div>
            </div>

            <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
        </div>
    </div>
    <jsp:include page="../core/footer-main.jsp" />
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/my-orders"/>"></script>
</html>