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
	<div class="order-info">
		<h2>订单信息</h2>

		<div class="main-content">

			<c:forEach items="${checkoutOrder.commerceItems}" var="item">
				<div class="info-row-1">
					产品信息 : <a class="info-value" href="#">${item.name}</a>
				</div>
				<div class="info-row-2">
					<div class="info-col-1">购买数量: <span class="info-value">${checkoutOrder.commerceItemCount}</span></div>
					<div class="info-col-2">产品金额: <span class="buy-cost info-value">¥</span><span
							class="buy-cost">${checkoutOrder.total}</span></div>
					<div class="info-col-3">配送费用: <span class="info-value">免运费</span></div>
				</div>
			</c:forEach>
			<div class="info-row-3">
				预计发货时间: <span class="info-value"><fmt:formatDate value='${checkoutOrder.estimatedShipDate}' pattern='yyyy年MM月dd日'/></span>
			</div>
			<div class="info-row-4">
				备注信息: <input type="text" placeholder="请填写任何您想告诉我们的信息"/>
			</div>
			<div class="info-row-5">
				<span class="buy-explain-title">购买说明: </span>

				<div class="buy-explain">
					<p>预定"在当品",可以看做一种投资行为:购买的是一种"可能性",将会出现下面两种情况中的一种:</p>

					<p>1. 您所预定的商品,如果到期发生绝当,您将获得该商品.我们将在绝当发生后15个工作日内为您发货.</p>

					<p>2. 如果到期之前,您所预定的商品发生赎当,您不能获得该商品.我们将退还您的全额本金,另外赔付您:<span>¥</span><span>312,00</span>.</p>
				</div>
			</div>
			<div class="info-row-6">
				收获地址:
			</div>
			<div class="info-row-7">
				<ul>
					<li class="receive-item">
						<div class="receive-info">
							<input class="receive-radio" type="radio" name="address"/>
							<span class="receive-name">核弹</span>
							<span class="receive-phone">15756306206</span>
							<span class="receive-province">四川省</span>
							<span class="receive-city">成都市</span>
							<span class="receive-country">武侯区</span>
							<span class="address">佳灵路5号下一站都市A302</span>
						</div>
						<div class="receive-handle">
							<a href="javascript:void(0);" class="set-default link-btn">设为默认</a>
							<a href="javascript:void(0);" class="receive-modify link-btn">修改</a>
							<a href="javascript:void(0);" class="receive-delete link-btn">删除</a>
						</div>
					</li>
					<li class="receive-item">
						<input class="receive-radio" type="radio"/>
						<span class="receive-new">使用新地址</span>

						<form class="add-and-modify" action="/">
							<table>
								<tr>
									<th>收货人:</th>
									<td><input class="input-text" type="text"/></td>
								</tr>
								<tr>
									<th>手机号:</th>
									<td><input class="input-text" type="text"/></td>
								</tr>
								<tr>
									<th>地址:</th>
									<td>
										<!-- 仿select组件begin-->
										<div class="invest-province-select">
											<a id="province" class="select-view" href="#">
												<span class="selected">请选择</span>
												<i class="foundicon-down-arrow"></i>
											</a>
											<ul class="options">
												<li><a class="option-view" data-value="0" href="#">四川</a></li>
												<li><a class="option-view" data-value="1" href="#">湖南</a></li>
												<li><a class="option-view" data-value="2" href="#">江西</a></li>
											</ul>
											<input class="select-value" name="" type="hidden" value=""/>
										</div>
										<!-- 仿select组件end-->
										<!-- 仿select组件begin-->
										<div class="invest-city-select">
											<a id="city" class="select-view" href="#">
												<span class="selected">请选择</span>
												<i class="foundicon-down-arrow"></i>
											</a>
											<ul class="options">
												<li><a class="option-view" data-value="0" href="#">四川</a></li>
												<li><a class="option-view" data-value="1" href="#">湖南</a></li>
												<li><a class="option-view" data-value="2" href="#">江西</a></li>
											</ul>
											<input class="select-value" name="" type="hidden" value=""/>
										</div>
										<!-- 仿select组件begin-->
										<!-- 仿select组件end-->
										<div class="invest-country-select">
											<a id="country" class="select-view" href="#">
												<span class="selected">请选择</span>
												<i class="foundicon-down-arrow"></i>
											</a>
											<ul class="options">
												<li><a class="option-view" data-value="0" href="#">四川</a></li>
												<li><a class="option-view" data-value="1" href="#">湖南</a></li>
												<li><a class="option-view" data-value="2" href="#">江西</a></li>
											</ul>
											<input class="select-value" name="" type="hidden" value=""/>
										</div>
										<!-- 仿select组件end-->
										<input class="input-text" type="text"/>

									</td>
								</tr>
								<tr>
									<th></th>
									<td><input type="checkbox" name="" id=""/>设置为默认收获地址</td>
								</tr>
								<tr>
									<th></th>
									<td><input class="receive-button" type="button" value="添加并使用"/></td>
								</tr>
							</table>
						</form>
					</li>
				</ul>
			</div>
			<div class="info-row-8">
				<input class="receive-ifo-submit" type="button" value="确认订单并提交"/>
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