<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

	<meta charset="UTF-8">
	<title>点金子典当行绝当品销售平台</title>
	<link rel="Shortcut Icon" href="/resources/common/logo.png">
	<link rel="stylesheet" href="/resources/my-account/my_account_base.css"/>
	<link rel="stylesheet" href="/resources/my-account/my-property/my-property.css"/>


</head>
<body>

<!--主头部-->
<jsp:include page="../../core/header-login.jsp"/>

<!--正文-->
<div class="content">
	<jsp:include page="../vertical-my-account-directory.jsp"/>
	<div class="main-area">
		<h2 class="main-head">我的资产</h2>
		<!-- super: will-binding和already-binding,根据不同的情况显示和隐藏-->
		<div class="will-binding" style="display: block;">
			<div class="will-row-1">
				您还没有绑定易宝!
			</div>
			<div class="will-row-2">
				为了您的购物更加安全方便快捷,淘在当网站推荐用户与易宝进行支付绑定!
			</div>
			<div class="will-row-3">
				<a class="yeepay-binding" href="#">绑定易宝</a>
				<span class="yeepay-tips">点击跳转到易宝绑定页面</span>
			</div>
		</div>
		<!-- person-info-box end-->
	</div>
</div>
<form action="/yeepay/yeepayForm" method="get" style="display:hidden">
	<input type="hidden" name="serviceName" value="toRegister"/>
	<input class="yeepay-btn" type="submit" value="绑定易宝">
	<span>点击跳转到易宝绑定页面</span>
</form>

<!--主脚部-->
<jsp:include page="../../core/footer-main.jsp"></jsp:include>
<jsp:include page="../../core/baidu.jsp"></jsp:include>
</body>
<script src="/resources/core/js/jquery.min.js"></script>
<script src="/resources/core/js/jquery.form.js"></script>

<script type="text/javascript">
	$(function() {
		$(".yeepay-binding").click(function() {
			$(".yeepay-btn").click();
		})
	});

</script>

</html>