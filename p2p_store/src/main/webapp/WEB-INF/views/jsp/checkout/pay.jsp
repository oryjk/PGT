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
	<script type="application/javascript" src="/resources/detail/detail.js"></script>
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

	<form class="order-pay">
		<h2>最后一步，请尽快付款！</h2>

		<div class="pay-content">
			<div class="pay-title">请在<span>30</span>分钟内完成支付，否则订单将会被取消哦。</div>
			<div class="pay-info">
				<div class="amount-payable">应付金额：186000.00元</div>
				<div class="pay-add">
					收货人：<span>周杰伦</span> &nbsp; <span>1890000000</span> <br><br>
					收货地址：<span>四川省成都市武侯区中环武阳大道三段下一站都市A座302</span>
				</div>
			</div>
			<div class="pay-yeepay">
				<input type="radio">使用易宝余额支付（易宝余额为：<span>4.25元</span>）
			</div>
			<div class="payment">
				<div class="pay-check">支付宝/易宝</div>
				<div class="pay-Alipay"><input name="" type="radio"><img src="../core/images/cart/zhifubao.png"></div>
				<div class="pay-Alipay"><input name="" type="radio"><img src="../core/images/cart/yibao.png"></div>
			</div>
			<div class="pay-submit">
				<input class="pay-submit-btn" type="submit" value="立即支付">
			</div>
		</div>
	</form>

</div>
<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<jsp:include page="../core/baidu.jsp"/>
<!--footer end-->
</body>
</html>