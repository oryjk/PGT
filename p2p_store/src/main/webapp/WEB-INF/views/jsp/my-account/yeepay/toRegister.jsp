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
		<form class="info-import" style="display: block" action="/yeepay/getSgin" method="post" id="yeepayDataForm">
			<input name="platformUserNo" type="hidden" value="${platformUserNo }" />
			<input name="serviceName" type="hidden" value = "${serviceName }"/>
			<div class="row1">
				<span class="row-title">昵称 :</span>
				<span class="row-text"><input type="text" class="input-text" name="nickName" /></span>
			</div>
			<div class="row1">
				<span class="row-title">真实姓名 :</span>
				<span class="row-text"><input type="text" class="input-text" name="realName" /></span>
			</div>
			<div class="row1">
				<span class="row-title">身份证类型 :</span>
                    <span class="row-text">
                        <label><input type="radio" name="idCardType" value="G1_IDCARD">第一代</label>
                        <label><input type="radio" name="idCardType" value="G2_IDCARD">第二代</label>
                    </span>
			</div>
			<div class="row1">
				<span class="row-title">身份证号 :</span>
				<span class="row-text"><input type="text" class="input-text" name="idCardNo"/></span>
				<!--<span class="row-tips">身份证格式不正确</span>-->
			</div>
			<div class="row1">
				<span class="row-title">手机号 :</span>
				<span class="row-text"><input type="text" class="input-text" name="mobile" /></span>
				<!--<span class="row-tips">手机格式不正确</span>-->
			</div>
			<div class="row1">
				<span class="row-title">邮箱 :</span>
				<span class="row-text"><input type="text" class="input-text" name="email" /></span>
				<!--<span class="row-tips">邮箱格式不正确</span>-->
			</div>
			<div class=row2>
				<input class="yeepay-btn" type="submit" value="提交"/>
			</div>
		</form>
		<jsp:include page="include/yeepayGatewayForm.jsp"></jsp:include>
		<!-- person-info-box end-->
	</div>
</div>

<!--主脚部-->
<jsp:include page="../../core/footer-main.jsp"></jsp:include>
<jsp:include page="../../core/baidu.jsp"></jsp:include>
</body>
<script src="/resources/core/js/jquery.min.js"></script>
<script src="/resources/core/js/jquery.form.js"></script>
<script src="/resources/core/js/require.js"
		data-main="/resources/yeepay/gateway.js"></script>

</html>