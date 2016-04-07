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
		<div class="already-binding" style="display: block;">
			<div class="already-row-1">
				<span>已绑定易宝账号:</span>
				<span class="yeepay-username">${accountNo }</span>
			</div>
			<div class="already-row-2">
				<div class="already-title">易宝余额</div>
				<div class="already-value">
					<span class="cost">¥</span><span class="cost">${result['availableAmount']}</span>
					<a class="yeepay-shift-into" href="/yeepay/yeepayForm?serviceName=toRecharge">转入</a>
					<a class="yeepay-shift-out link-btn" href="/yeepay/yeepayForm?serviceName=toWithdraw">转出</a>
				</div>
			</div>
			<div class="already-row-3">
				<div class="already-title">易宝账户:</div>
				<div class="already-value">${accountNo }</div>
			</div>
			<div class="already-row-4">
				<div class="already-title">账户类型:</div>
				<c:choose><c:when test="${result['memberType'] eq 'PERSONAL'}"><div class="already-value">个人会员</div></c:when><c:when test="${result['memberType'] eq 'ENTERPRISE'}"><div class="already-value">企业会员</div></c:when></c:choose>

			</div>
			<div class="already-row-5">
				<div class="already-title">银行卡号:</div>
				<div class="already-value">
					<c:choose>
						<c:when test="${empty result['cardNo'] }">
							<span>未绑定</span>
							<a class="yeepay-un-binding link-btn" href="/yeepay/yeepayForm?serviceName=toBindBankCard">绑卡</a>
						</c:when>
						<c:otherwise>
							<span>${result['cardNo'] }</span>
							<a class="yeepay-un-binding link-btn" href="/yeepay/yeepayForm?serviceName=toUnbindBankCard">解绑</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="already-row-6">
				<div class="already-title">姓名:</div>
				<div class="already-value">${name }</div>
			</div>
			<div class="already-row-7">
				<div class="already-title">绑定手机号:</div>
				<div class="already-value">${result['bindMobileNo']}</div>
			</div>
			<div class="already-row-8">
				<div class="already-title">身份证号:</div>
				<div class="already-value">${idNo }</div>
			</div>
		</div>
	</div>
</div>


<!--主脚部-->
<jsp:include page="../../core/footer-main.jsp"></jsp:include>
<jsp:include page="../../core/baidu.jsp"></jsp:include>
</body>
<script src="/resources/core/js/jquery.min.js"></script>
<script src="/resources/core/js/jquery.form.js"></script>

<script type="text/javascript">
	$("#upload").change(function () {
		//定义参数
		var options = {
			url: "${pageContext.request.contextPath}/upload/uploadPic",
			dataType: "json",
			type: "post",
			success: function (data) {
				//回调 二个路径
				//url
				//path
				$("#allImgUrl").attr("src", "${pageContext.request.contextPath}/resources/" + data.url);
				$("#path").val(data.url);
			}
		};

		//jquery.form使用方式
		$("#jvForm").ajaxSubmit(options);

	});

</script>

</html>