<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../core/head.jspf">
    <jsp:param name="css_path" value="/resources/my-account/my_account_base.css"/>
    <jsp:param name="custom_css_path" value="/resources/my-account/my-orders/my-order.css"/>
</jsp:include>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<div class="content">
    <jsp:include page="vertical-my-account-directory.jsp"/>
    <div class="main-area">
        <h2 class="main-head">我的订单</h2>
        <ul class="filter-invest-status">
            <li class="${empty param.status or param.status eq 0 ? 'filter-invest-choose' : ''}">
                <a href="/myAccount/orderHistory?status=0">全部订单</a></li>
            <li class="${param.status ge 10 and param.status lt 20 ? 'filter-invest-choose' : ''}">
                <a href="/myAccount/orderHistory?status=10">在当</a></li>
            <li class="${param.status ge 20 and param.status lt 30 ? 'filter-invest-choose' : ''}">
                <a href="/myAccount/orderHistory?status=20">绝当</a></li>
            <li class="filter-invest-end ${param.status ge 30 and param.status lt 40 ? 'filter-invest-choose' : ''}">
                <a href="/myAccount/orderHistory?status=30">赎当</a></li>
        </ul>
        <ul class="filter-order-status">
            <c:choose>
                <c:when test="${param.status ge 10 and param.status lt 20}">
                    <%-- 在当 --%>
                    <li><a ${param.status eq 10 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=10">全部</a></li>
                    <li><a ${param.status eq 11 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=11">待支付</a></li>
                    <li class="filter-order-end"><a ${param.status eq 12 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=12">已支付</a></li>
                </c:when>
                <c:when test="${param.status ge 20 and param.status lt 30}">
                    <%-- 绝当 --%>
                    <li><a ${param.status eq 20 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=20">全部</a></li>
                    <li><a ${param.status eq 21 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=21">待发货</a></li>
                    <li><a ${param.status eq 22 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=22">已发货</a></li>
                    <li class="filter-order-end"><a ${param.status eq 29 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=29">已完成</a></li>
                </c:when>
                <c:when test="${param.status ge 30 and param.status lt 40}">
                    <%-- 赎当 --%>
                    <li><a ${param.status eq 30 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=30">全部</a></li>
                    <li><a ${param.status eq 31 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=31">已赔付</a></li>
                    <li class="filter-order-end"><a ${param.status eq 32 ? 'class="filter-order-choose"' : ''}
                            href="/myAccount/orderHistory?status=32">待赔付</a></li>
                </c:when>
            </c:choose>
        </ul>
        <div class="order-list">
            <table>
                <!-- thead begin-->
                <tr class="order-list-head">
                    <th class="col-1">项目信息</th>
                    <th class="col-2"></th>
                    <th class="col-3">收货人</th>
                    <th class="col-4">产品金额(元)</th>
                    <th class="col-5">订单状态</th>
                    <th class="col-6">操作</th>
                </tr>
                <!-- thead end-->

                <!-- tbody begin-->
                <c:forEach var="order" items="${historyOrders.result}">
                    <tr class="order-list-item">
                        <td class="order-img">
                            <a href="#"><img src="../../core/images/product/hero_2_01.jpg" alt="商品名"/></a>
                        </td>
                        <td class="order-base-info">
                            <c:if test="${not empty order.submitDate}">
                                <p>订单生成时间: <span>
                                    <fmt:formatDate value="${order.submitDate}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
                            </c:if>
                            <p><a href="#">${order.commerceItems[0].name}</a></p>
                            <p>订单编号: <span>${order.id}</span></p>
                        </td>
                        <td>${order.shippingVO.shippingAddress.name}</td>
                        <td><span>¥</span><span>&nbsp;
                            <fmt:formatNumber value="${order.total}" pattern="0.00" type="number"/></span></td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status eq -10}">
                                    <span>已取消</span>
                                </c:when>
                                <c:when test="${order.status lt 30}">
                                    <span class="will-pay">待付款</span>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${order.tender.state eq 10}">
                                            <c:if test="${order.status eq 30}">
                                                <span>已付款</span>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${order.tender.state eq 20}">
                                            <c:choose>
                                                <c:when test="${order.status eq 30 or order.status eq 50}">
                                                    <span class="will-pay">待发货</span>
                                                </c:when>
                                                <c:when test="${order.status eq 80}">
                                                    <span class="will-pay">已发货</span>
                                                </c:when>
                                                <c:when test="${order.status eq 100}">
                                                    <span class="will-pay">已完成</span>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${order.tender.state eq 30}">
                                            <c:choose>
                                                <c:when test="${order.status eq 30 or order.status eq 60}">
                                                    <span class="will-pay">待赔付</span>
                                                </c:when>
                                                <c:when test="${order.status eq 100}">
                                                    <span class="will-pay">已赔付</span>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="order-handle">
                            <c:choose>
                                <c:when test="${order.status eq -10}">
                                    <p>订单已取消</p>
                                </c:when>
                                <c:when test="${order.status lt 30}">
                                    <p><a class="pay-now" href="#">立即支付</a></p>
                                </c:when>
                                <c:otherwise>
                                    <p><a class="link-btn watch-detail" href="javascript:void(0);"
                                          data-order-id="${order.id}">订单详情</a></p>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <!-- tbody end-->
            </table>
        </div>
        <div class="page-box">
            <ol>
                <li class="page-list">
                    <c:if test="${historyOrders.maxIndex gt 0}">
                        <ol id="pages">
                            <c:forEach var="pageNum" items="${historyOrders.pageNumbers}">
                                <c:choose>
                                    <c:when test="${pageNum gt 0}">
                                        <li>
                                            <a href="/myAccount/orderHistory?status=${param.status}&currentIndex=${pageNum}">${pageNum + 1}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="/myAccount/orderHistory?status=${param.status}">${pageNum + 1}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ol>
                    </c:if>
                </li>
            </ol>
        </div>
    </div>
</div>

<div class="order-detail-pop">
    <div class="order-detail-wrap">
        <div class="order-detail-pop-content"></div>
    </div>
</div>

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/my-account/my-orders/my-order"></script>
</body>
</html>