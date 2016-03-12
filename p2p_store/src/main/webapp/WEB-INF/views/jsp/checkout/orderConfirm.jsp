<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/12/16
  Time: 12:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/buy/buy.css"/>
	<script type="application/javascript" src="/resources/detail/detail.js"></script>
</head>
<body>
<jsp:include page="../core/header-main.jsp"/>

<ul class="step step-1">
	<li>订单详情</li>
	<li>订单确认</li>
	<li>支付</li>
	<li>完成</li>
</ul>
<div class="main">
	<div class="order-confirm">
		<h2>订单确认</h2>

		<div class="main-content">
			<div class="confirm-row-1">
				<div class="confirm-title">订单号:</div>
				<div class="confirm-value">919281734123</div>
			</div>
			<div class="confirm-row-2">
				<div class="confirm-title">联系人:</div>
				<div class="confirm-value">核弹</div>
			</div>
			<div class="confirm-row-3">
				<div class="confirm-title">联系方式:</div>
				<div class="confirm-value">15983439289</div>
			</div>
			<div class="confirm-row-4">
				<div class="confirm-title">备注信息:</div>
				<div class="confirm-value">请填写任何你想要的信息</div>
			</div>
			<div class="confirm-row-5">
				<div class="confirm-title">收货人信息:</div>
				<div class="confirm-value">
					<p><span>核弹</span> <span>159388279830</span></p>

					<p><span>四川省</span><span>成都市</span><span>武侯区</span><span>佳灵路下一站都市A302</span></p>
				</div>
			</div>
			<div class="confirm-row-6">
				<table>
					<tr>
						<th class="confirm-name">产品名称</th>
						<th class="confirm-post-cost">配送费用</th>
						<th class="confirm-item-cost">产品单价</th>
						<th class="confirm-item-count">产品数量</th>
						<th class="confirm-percent">赔付利率(年利率)</th>
					</tr>
					<tr>
						<td><a class="item-link" href="#">什么系列什么产品噢噢噢噢噢噢噢</a></td>
						<td><span>¥</span><span>0.00</span></td>
						<td><span class="cost">¥</span><span class="cost">123,000.00</span></td>
						<td>1</td>
						<td>10.00%</td>
					</tr>
				</table>
			</div>
			<div class="confirm-row-7">
				温馨提示:请在提交订单30分钟内付款,否则您的订单将自动关闭.
			</div>
			<div class="confirm-row-8">
				<span class="money-title">应付金额:</span>
				<span class="cost">¥</span>
				<span class="cost">186,000.00</span>
			</div>
			<div class="confirm-row-9">
				<a class="confirm-submit" href="javascript: void(0);">立即支付</a>
				<a class="confirm-reset" href="javascript: void(0);">返回上一步</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>