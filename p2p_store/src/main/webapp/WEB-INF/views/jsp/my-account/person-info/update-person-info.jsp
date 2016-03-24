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
	<div class="menu">
		<div class="user-face">
			<div class="img-box"><img src="" alt=""/></div>
			<p class="face-title">用户名</p>
		</div>
		<ul class="menu-list">
			<li><a href="#">我的交易单</a></li>
			<li><a class="menu-choose" href="#">我的资产</a></li>
			<li><a href="#">我的收藏</a></li>
			<li><a href="#">最近浏览</a></li>
			<li><a href="#">个人信息</a></li>
			<li><a href="#">地址管理</a></li>
			<li><a href="#">修改密码</a></li>
		</ul>
		<div class="menu-two-dimension">
			<img src="" alt=""/>

			<p>扫一扫,淘在当手机版</p>
		</div>
	</div>
	<div class="main-area">
		<h2 class="main-head">个人信息</h2>
		<!-- super: will-binding和already-binding,根据不同的情况显示和隐藏-->
		<div class="person-info-tab-box">
			<a class="person-info-tab person-info-tab-choose" href="#">基本信息</a>
			<a class="person-info-tab" href="#">修改登录密码</a>
		</div>
		<!-- super: person-info-box和modify-password-box根据不同的情况显示-->
		<!-- person-info-box begin-->
		<div class="person-info-box" style="display: block;">
			<form action="">
				<table>
					<tr>
						<th>用户名:</th>
						<td>tangchao111</td>
					</tr>
					<tr>
						<th>真实姓名:</th>
						<td><input class="input-text" type="text"/></td>
					</tr>
					<tr>
						<th>手机号码:</th>
						<td>
							<span>13198585611</span>
							<a class="link-btn" href="#">更换</a>
						</td>
					</tr>
					<tr>
						<th>性别:</th>
						<td>
							<label><input type="radio" name="gender"/>男</label>
							<label><input type="radio" name="gender"/>女</label>
							<label><input type="radio" name="gender"/>保密</label>
						</td>
					</tr>
					<tr>
						<th>邮箱:</th>
						<td>
							<span>819127251@qq.com</span>
							<a class="link-btn" href="#">更换</a>
						</td>
					</tr>
					<tr>
						<th>身份证号码:</th>
						<td><input class="input-text" type="text"/></td>
					</tr>
					<tr>
						<th>婚姻状况:</th>
						<td>
							<label><input type="radio" name="marriage"/>已婚</label>
							<label><input type="radio" name="marriage"/>未婚</label>
							<label><input type="radio" name="marriage"/>保密</label>
						</td>
					</tr>
					<tr>
						<th>所在行业:</th>
						<td><input class="input-text" type="text"/></td>
					</tr>
					<tr>
						<th>年收入:</th>
						<td><input class="input-text" type="text"/></td>
					</tr>
					<tr>
						<th></th>
						<td><span class="error">错误提示</span></td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="person-info-submit" type="button" value="确认修改"/>
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
		<!-- person-info-box end-->

		<!-- modify-password-box begin-->
		<div class="modify-password-box" style="display: block;">
			<form action="">
				<table>
					<tr>
						<th>旧密码:</th>
						<td><input class="input-text" type="password"/></td>
					</tr>
					<tr>
						<th>新密码:</th>
						<td><input class="input-text" type="password"/></td>
					</tr>
					<tr>
						<th>确认密码:</th>
						<td><input class="input-text" type="password"/></td>
					</tr>
					<tr>
						<th></th>
						<td><span class="error">错误提示</span></td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="person-info-submit" type="button" value="确认修改"/>
							<input class="person-info-cancel" type="button" value="放弃修改"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- modify-password-box end-->

		<!-- person-info-box begin-->
		<div class="person-info-box" style="display: block;">
			<form action="">
				<table>
					<tr>
						<th>用户名:</th>
						<td>tangchao111</td>
					</tr>
					<tr>
						<th>真实姓名:</th>
						<td>周杰伦</td>
					</tr>
					<tr>
						<th>手机号码:</th>
						<td>
							<span>13198585611</span>
							<a class="link-btn" href="#">更换</a>
						</td>
					</tr>
					<tr>
						<th>性别:</th>
						<td>
							<label>男</label>
						</td>
					</tr>
					<tr>
						<th>邮箱:</th>
						<td>
							<span>819127251@qq.com</span>
							<a class="link-btn" href="#">更换</a>
						</td>
					</tr>
					<tr>
						<th>身份证号码:</th>
						<td>1233211234567</td>
					</tr>
					<tr>
						<th>婚姻状况:</th>
						<td>
							<label>已婚</label>
						</td>
					</tr>
					<tr>
						<th>所在行业:</th>
						<td>IT业</td>
					</tr>
					<tr>
						<th>年收入:</th>
						<td>10元</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="person-info-submit" type="button" value="编辑"/>
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