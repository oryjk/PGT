<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags" prefix="date" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/buy/buy.css"/>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<!--step begin-->
<!--super: 下面四步依次加上step-1到step-4-->
<ul class="step step-2">
	<li>订单详情</li>
	<li>订单确认</li>
	<li>支付</li>
	<li>完成</li>
</ul>
<!--step end-->

<!--content begin-->
<div class="main">
	<div class="order-confirm">
		<h2>订单确认</h2>

		<div class="main-content">
			<div class="confirm-row">
				<div class="confirm-title">订单号:</div>
				<div class="confirm-value">${order.id}</div>
			</div>
			<div class="confirm-row">
				<div class="confirm-title">联系人:</div>
				<div class="confirm-value">${order.shippingVO.shippingAddress.name}</div>
			</div>
			<div class="confirm-row">
				<div class="confirm-title">联系方式:</div>
				<div class="confirm-value">${order.shippingVO.shippingAddress.phone}</div>
			</div>
			<div class="confirm-row confirm-row-bottom-line">
				<div class="confirm-title">备注信息:</div>
				<div class="confirm-value">${order.userComments}</div>
			</div>
			<div class="confirm-row confirm-address-info">
				<div class="confirm-title">收货人信息:</div>
				<div class="confirm-value">
					<p><span>${order.shippingVO.shippingAddress.name}</span> <span>${order.shippingVO.shippingAddress.phone}</span></p>

					<p>
						<span>${order.shippingVO.shippingAddress.province}</span><span>${order.shippingVO.shippingAddress.province}</span><span>${order.shippingVO.shippingAddress.district}</span><span>${order.shippingVO.shippingAddress.address}</span>
					</p>
				</div>
			</div>
			<div class="confirm-row confirm-product-list">
				<table>
					<tbody>
					<tr>
						<th class="confirm-name">产品名称</th>
						<th class="confirm-post-cost">配送费用</th>
						<th class="confirm-item-cost">产品单价</th>
						<th class="confirm-item-count">产品数量</th>
						<th class="confirm-percent">赔付利率(年利率)</th>
					</tr>
					<c:forEach items="${order.commerceItems}" var="item">
						<tr>
							<td><a class="item-link" href="#">${item.name}</a></td>
							<td><span>¥</span><span>0.00</span></td>
							<td><span class="cost">¥</span><span class="cost">${item.amount}</span></td>
							<td>${item.quantity}</td>
							<td><fmt:formatNumber pattern="#0.00" value="${p2pInfo.interestRate}"/>%</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="confirm-row-7">
				温馨提示:请在提交订单30分钟内付款,否则您的订单将自动关闭.
			</div>
			<div class="confirm-row-8">
				<span class="money-title">应付金额:</span>
				<span class="cost">¥</span>
				<span class="cost">${order.total}</span>
			</div>
			<div class="confirm-row-9">
				<a class="confirm-submit" href="/checkout/payment?orderId=${order.id}">立即支付</a>
				<a class="confirm-reset" href="/checkout/shipping?orderId=${order.id}">返回上一步</a>
			</div>
		</div>
	</div>
</div>
<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<jsp:include page="../core/baidu.jsp"/>
<!--footer end-->
<script src="/resources/core/js/require.js" data-main="/resources/order-detail/orderDetails"></script>
</body>
</html>