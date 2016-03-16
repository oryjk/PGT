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
<ul class="step step-1">
	<li>订单详情</li>
	<li>订单确认</li>
	<li>支付</li>
	<li>完成</li>
</ul>
<!--step end-->

<!--content begin-->
<div class="main">

	<div class="order-info" id="order-info">
		<h2>订单信息</h2>

		<div class="main-content">
			<c:forEach items="${order.commerceItems}" var="commerceItem">
				<div class="info-row">
					<span class="info-title">产品信息:</span> <a class="info-value" href="#">${commerceItem.name}</a>
				</div>
				<div class="info-row">
					<div class="info-col-1"><span class="info-title">购买数量:</span> <span class="info-value">${commerceItem.quantity}</span></div>
					<div class="info-col-2"><span class="info-title">产品金额:</span> <span class="buy-cost info-value">¥</span><span
							class="buy-cost"><fmt:formatNumber value="${commerceItem.amount}" type="currency" pattern="#,#00.00#"/></span></div>
					<div class="info-col-3"><span class="info-title">配送费用:</span> <span class="info-value">免运费</span></div>
				</div>
			</c:forEach>


			<div class="info-row">
				<span class="info-title">预计发货时间:</span> <span class="info-value"><fmt:formatDate value='${checkoutOrder.estimatedShipDate}' pattern='yyyy年MM月dd日'/></span>
			</div>
			<div class="info-row">
				<span class="info-title">备注信息:</span><input class="remark" type="text" placeholder="请填写任何您想告诉我们的信息"/>
			</div>
			<div class="info-row">
				<span class="buy-explain-title">购买说明: </span>

				<div class="buy-explain">
					<p>预定"在当品",可以看做一种投资行为:购买的是一种"可能性",将会出现下面两种情况中的一种:</p>

					<p>1. 您所预定的商品,如果到期发生绝当,您将获得该商品.我们将在绝当发生后15个工作日内为您发货.</p>

					<p>2. 如果到期之前,您所预定的商品发生赎当,您不能获得该商品.我们将退还您的全额本金,另外赔付您:<span>¥</span><span><fmt:formatNumber value="${commerceItem.amount}" type="currency"
																											  pattern="#,#00.00#"/></span>.</p>
				</div>
			</div>
			<div class="address-box">
				<span>收获地址:</span>
				<a id="addNewAddress" class="link-btn" href="javascript:void(0);">新增收获地址</a>
			</div>
			<div class="info-row">
				<div class="address-have-no">
					<a class="address-have-no-add" href="javascript:void(0);">
						您暂时还没有地址, 添加添加
					</a>
				</div>
				<ul>
					<c:set value="${order.shippingVO.shippingAddress.addressInfoId}" var="defaultAddressId"/>
					<input type="hidden" v-model="defaultAddressId" value="${defaultAddressId}"/>
					<c:forEach items="${addressInfoList}" var="address">
						<li class="receive-item" @click="setOrderAddress(${address.id},${order.id},$event)">
							<div class="receive-info">
								<span class="receive-name" v-bind:class="{'receive-choose':defaultAddressId==${address.id}}">${address.name}</span>
								<span class="receive-phone">${address.phone}</span>
								<span class="receive-province">${address.province}</span>
								<span class="receive-city">${address.city}</span>
								<span class="receive-country">${address.district}</span>
								<span class="address">${address.address}</span>
							</div>
							<div class="receive-handle">
								<a href="javascript:void(0);" class="set-default link-btn" @click="setOrderAddress(${address.id},${order.id},$event)"
								   v-if="defaultAddressId!=${address.id}">设为默认</a>
								<a href="javascript:void(0);" class="receive-modify link-btn">修改</a>
								<a href="javascript:void(0);" class="receive-delete link-btn">删除</a>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="info-row-8">
				<input class="receive-ifo-submit" type="button" value="确认订单并提交"/>
			</div>
		</div>
	</div>
	<jsp:include page="../my-account/person-info/include/address_popup.jsp"/>
</div>
<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<jsp:include page="../core/baidu.jsp"/>
<!--footer end-->
<script src="/resources/core/js/require.js" data-main="/resources/buy/buy"></script>
</body>
</html>