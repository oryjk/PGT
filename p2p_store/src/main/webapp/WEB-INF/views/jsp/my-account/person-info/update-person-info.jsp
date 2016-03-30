<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

	<meta charset="UTF-8">
	<title>点金子典当行绝当品销售平台</title
	<link rel="Shortcut Icon" href="/resources/common/logo.png">
	<link rel="stylesheet" href="/resources/my-account/my_account_base.css"/>
	<link rel="stylesheet" href="/resources/my-account/person-info/person-info.css"/>


</head>
<body>

<!--主头部-->
<jsp:include page="../../core/header-login.jsp"/>

<!--正文-->
<div class="content">
	<jsp:include page="../vertical-my-account-directory.jsp"/>
	<div class="main-area">
		<h2 class="main-head">个人信息</h2>
		<!-- super: will-binding和already-binding,根据不同的情况显示和隐藏-->
		<div class="person-info-tab-box">
			<a class="person-info-tab person-info-tab-choose" href="#">基本信息</a>
			<a class="person-info-tab" href="/user/resetPassword">修改登录密码</a>
		</div>
		<!-- super: person-info-box和modify-password-box根据不同的情况显示-->
		<!-- person-info-box begin-->
		<div class="person-info-box" style="display: block;">
			<form action="/userinformation/update" method="post">
				<table>
					<tr>
						<th>用户名:</th>
						<td>${currentUser.username}</td>
					</tr>
					<tr>
						<th>昵称:</th>
						<c:set value="${userInformation.nickname}" var="nickname"/>
						<c:if test="${empty userInformation.nickname}">
							<c:set value="${currentUser.username}" var="nickname"/>
						</c:if>
						<td><input class="input-text" type="text" name="nickname" value="${nickname}"/></td>
					</tr>
					<tr>
						<th>手机号码:</th>
						<c:set value="${userInformation.phoneNumber}" var="phoneNumber"/>
						<c:if test="${empty userInformation.phoneNumber}">
							<c:set value="${currentUser.phoneNumber}" var="phoneNumber"/>
						</c:if>
						<td><input class="input-text" type="text" name="phoneNumber" value="${phoneNumber}" maxlength="11"/></td>
					</tr>
					<tr>
						<th>性别:</th>
						<td>
							<label><input type="radio" name="gender"
										  <c:if test="${userInformation.gender eq '男'}">checked</c:if> value="男"/>男</label>
							<label><input type="radio" name="gender"
										  <c:if test="${userInformation.gender eq '女'}">checked</c:if> value="女"/>女</label>
							<label><input type="radio" name="gender"
										  <c:if test="${empty userInformation.gender}">checked</c:if> value=""/>保密</label>
						</td>
					</tr>
					<tr>
						<th>邮箱:</th>
						<td>
							<input class="input-text" type="text" name="personEmail" value="${userInformation.personEmail}"/>
						</td>
					</tr>
					<tr>
						<th>身份证号码:</th>
						<td><input class="input-text" type="text" name="idCard" value="${userInformation.idCard}" maxlength="18"/></td>
					</tr>
					<tr>
						<th>婚姻状况:</th>
						<td>
							<label><input type="radio" name="marrage"
										  <c:if test="${userInformation.marrage eq '已婚'}">checked</c:if> value="已婚"/>已婚</label>
							<label><input type="radio" name="marrage"
										  <c:if test="${userInformation.marrage eq '未婚'}">checked</c:if> value="未婚"/>未婚</label>
							<label><input type="radio" name="marrage"
										  <c:if test="${empty userInformation.marrage}">checked</c:if> value=""/>保密</label>
						</td>
					</tr>
					<tr>
						<th>所在行业:</th>
						<td><input class="input-text" name="industry" type="text" value="${userInformation.industry}"/></td>
					</tr>
					<tr>
						<th>年收入:</th>
						<td><input class="input-text" name="income" type="text" value="${userInformation.income}"/></td>
					</tr>
					<tr>
						<th></th>
						<td>
							<c:forEach items="${error}" var="err">
								<span class="error">${err.defaultMessage}</span>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="person-info-submit" type="submit" value="确认修改"/>
							<input class="person-info-cancel" type="button" value="放弃修改"/>
						</td>
					</tr>
				</table>
			</form>
			<form class="person-face" action="#">
				<img src="../../core/images/my_account/" alt=""/>
				<!--file begin-->
				<div class="like-file">
					点击上传头像
					<input class="original-input-file" type="file" value="上传">
				</div>
				<!-- file end-->
			</form>
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