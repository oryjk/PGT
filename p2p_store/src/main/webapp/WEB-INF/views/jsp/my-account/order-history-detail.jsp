<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2>订单详情</h2>
<div class="base-info">
    <div class="base-row-1">
        <div class="base-title">收货人信息:</div>
        <div class="base-value">
            <span class="name">${historyOrder.shippingVO.shippingAddress.name}</span>
            <span class="phone">${historyOrder.shippingVO.shippingAddress.phone}</span>
            <span class="address">${historyOrder.shippingVO.shippingAddress.province} ${historyOrder.shippingVO.shippingAddress.city} ${historyOrder.shippingVO.shippingAddress.district} ${historyOrder.shippingVO.shippingAddress.address}</span>
            <%--<a class="link-btn" href="javacript:void(0);">修改</a>--%>
        </div>
    </div>
    <div class="base-row-2">
        <div class="base-title">订单信息:</div>
        <div class="base-value">
            <p>
                <span class="order-number">订单号:</span><span>${historyOrder.id}</span>
            </p>
            <p>
                <span class="order-time">下单时间:</span>
                <span><fmt:formatDate value="${historyOrder.submitDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            </p>
        </div>
    </div>
    <div class="base-row-3">
        <div class="base-title">备注信息:</div>
        <div class="base-value">${historyOrder.userComments}</div>
    </div>
</div>
<c:choose>
    <c:when test="${historyOrder.tender.state eq 20}">
        <div class="send-info">
            <c:choose>
                <c:when test="${historyOrder.status eq 30 or historyOrder.status eq 50}">
                    <!-- super:绝当未发货时,以及在当时显示-->
                    <div class="send-will" style="display: block;">
                        <div class="send-row-1">
                            <div class="send-title">预计发货时间:</div>
                            <div class="send-value"><fmt:formatDate
                                    value="${historyOrder.estimatedShipDate}"
                                    pattern="yyyy-MM-dd HH:mm:ss"/></div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${historyOrder.status eq 80 or historyOrder.status eq 100}">
                    <!-- super:绝当已发货时显示-->
                    <div class="send-already" style="display: block;">
                        <div class="send-row-2">
                            <div class="send-title">发货时间:</div>
                            <div class="send-value"><fmt:formatDate
                                    value="${historyOrder.commerceItems[0].delivery.deliveryTime}"
                                    pattern="yyyy-MM-dd HH:mm:ss"/></div>
                        </div>
                        <div class="send-row-3">
                            <div class="send-title">快递单号:</div>
                            <div class="send-value">${historyOrder.commerceItems[0].delivery.trackingNo}</div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </c:when>
    <c:when test="${historyOrder.tender.state eq 30}">
        <div class="compensate">
            <c:choose>
                <c:when test="${historyOrder.status eq 30 or historyOrder.status eq 60}">
                    <!-- super:赎当未赔付时显示-->
                    <div class="compensate-will">
                        <div class="tip">温馨提示:您所预定的商品已经赎当,我们将会对您进行一定的赔付,请您耐心等待.</div>
                        <div class="compensate-info">
                            <div class="compensate-time"><span>预计收益发放时间:</span><span>2016年3月22日</span></div>
                            <div class="compensate-money"><span>预计赔付金额:</span><span class="cost">¥</span><span
                                    class="cost">213.00</span></div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${historyOrder.status eq 100}">
                    <!-- super:赎当已赔付时显示-->
                    <div class="compensate-already">
                        <div class="tip">温馨提示:您所预定的商品已经赎当,我们已对您进行了赔付,请核对查收,谢谢您的支持!.</div>
                        <div class="compensate-info">
                            <div class="compensate-time"><span>收益发放时间:</span><span>2016年3月22日</span></div>
                            <div class="compensate-money"><span>赔付金额:</span><span class="cost">¥</span><span
                                    class="cost">213.00</span>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </c:when>
</c:choose>
<div class="item-table">
    <table>
        <tr>
            <th class="confirm-name">产品名称</th>
            <th class="confirm-post-cost">配送费用</th>
            <th class="confirm-item-cost">产品单价</th>
            <th class="confirm-item-count">产品数量</th>
            <th class="confirm-percent">赔付利率(年利率)</th>
        </tr>
        <tr>
            <td><a class="item-link" href="#">${historyOrder.commerceItems[0].name}</a></td>
            <td><span>¥</span><span>0.00</span></td>
            <td><span class="cost">¥</span><span class="cost">${historyOrder.commerceItems[0].amount}</span></td>
            <td>${historyOrder.commerceItems[0].quantity}</td>
            <td><fmt:formatNumber value="${historyOrder.p2pInfo.interestRate}" pattern="0.00" type="number"/>%</td>
        </tr>
    </table>
</div>
<div class="money-box">
    <c:choose>
        <c:when test="${historyOrder.tender.state eq 20}">
            <!--super: 在当何绝当时显示-->
            <div style="display: block">
                <span>订单总额:</span>
                <span class="cost">¥</span>
                <span class="cost"><fmt:formatNumber value="${historyOrder.total}" pattern="0.00" type="number"/></span>
            </div>
        </c:when>
        <c:when test="${historyOrder.tender.state eq 30}">
            <!--super: 赎当后显示-->
            <div style="display: block">
                <span>将获得本金加赔付共计:</span>
                <span class="cost">¥</span>
                <span class="cost">
                <fmt:formatNumber value="${historyOrder.p2pInfo.actualIncoming}" pattern="0.00" type="number"/></span>
            </div>
        </c:when>
    </c:choose>
</div>
<div class="close-box">
    <a class="close" href="javascript: void(0);">关闭</a>
</div>
<div class="invest-status"></div>