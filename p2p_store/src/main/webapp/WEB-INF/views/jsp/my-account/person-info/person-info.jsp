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
		<!-- person-info-box begin-->
		<div class="person-info-box" style="display: block;">
			<form action="">
				<table>
					<tr>
						<th>用户名:</th>
						<td>${currentUser.username}</td>
					</tr>
					<tr>
						<th>昵称:</th>
						<td>
							<c:choose>
								<c:when test="${!empty userInformation.nickname}">
									${userInformation.nickname}
								</c:when>
								<c:otherwise>
									未设置
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>手机号码:</th>
						<td>
							<span>${currentUser.phoneNumber}</span>
						</td>
					</tr>
					<tr>
						<th>性别:</th>
						<td>
							<label>
								<c:choose>
								<c:when test="${!empty userInformation.gender}">
									${userInformation.gender}
								</c:when>
								<c:otherwise>
								未设置
								</c:otherwise>
								</c:choose>
						</td>
					</tr>
					<tr>
						<th>邮箱:</th>
						<td>
							<span>
								<c:choose>
									<c:when test="${!empty userInformation.personEmail}">
										${userInformation.personEmail}
									</c:when>
									<c:otherwise>
										未设置
									</c:otherwise>
								</c:choose>
							</span>
						</td>
					</tr>
					<tr>
						<th>身份证号码:</th>
						<td>
							<c:choose>
								<c:when test="${!empty userInformation.idCard}">
									${userInformation.idCard}
								</c:when>
								<c:otherwise>
									未设置
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>婚姻状况:</th>
						<td>
							<label>
								<c:choose>
									<c:when test="${!empty userInformation.marrage}">
										${userInformation.marrage}
									</c:when>
									<c:otherwise>
										未设置
									</c:otherwise>
								</c:choose>
							</label>
						</td>
					</tr>
					<tr>
						<th>所在行业:</th>
						<td>
							<c:choose>
								<c:when test="${!empty userInformation.industry}">
									${userInformation.industry}
								</c:when>
								<c:otherwise>
									未设置
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>月收入:</th>
						<td>
							<c:choose>
								<c:when test="${!empty userInformation.income}">
									${userInformation.income}
								</c:when>
								<c:otherwise>
									未设置
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<a href="/userinformation/update"><input class="person-info-submit" type="button" value="编辑"/></a>
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
		<!-- person-info-box end-->
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