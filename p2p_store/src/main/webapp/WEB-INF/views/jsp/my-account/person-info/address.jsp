<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../../core/head.jspf">
	<jsp:param name="css_path" value="/resources/my-account/my_account_base.css"/>
	<jsp:param name="custom_css_path" value="/resources/my-account/address/address.css"/>
</jsp:include>
<body>
<!--header begin-->
<jsp:include page="../../core/header-main.jsp"/>
<!--header end-->

<div class="content">
	<jsp:include page="../vertical-my-account-directory.jsp"/>
	<div class="main" id="main">
		<h2 class="main-head">我的地址信息</h2>

		<div class="address-altogether">
			<span class="address-altogether-info">您已经创建了<span>${fn:length(addressList)}</span>个收货地址,最多可以创建<span>20</span>地址</span>
			<input class="address-add" type="button" value="新增收货地址" @click="createAddress"/>
		</div>
		<ul class="address-list">
			<c:forEach items="${addressList}" var="address" varStatus="status">
				<li class="${status.index==0?'address-default':''}">
					<div class="receive-name">
						<div class="receive-title">收货人:</div>
						<div class="receive-value">${address.name}</div>
					</div>
					<div class="receive-address">
						<div class="receive-title">所在地区:</div>
						<div class="receive-value"><span>${address.province}</span> <span>${address.city}</span> <span>${address.district}</span>
						</div>
					</div>
					<div class="receive-address">
						<div class="receive-title">地址:</div>
						<div class="receive-value"><span>${address.address}</span>
						</div>
					</div>
					<div class="receive-phone">
						<div class="receive-title">手机号码:</div>
						<div class="receive-value">${address.phone}</div>
					</div>
					<div class="receive-phone">
						<div class="receive-title">固定电话:</div>
						<div class="receive-value">${address.telephone}</div>
					</div>
					<div class="address-handle">
						<a class="link-btn" @click="setDefault(${address.id},$event)" href="javascript:void(0);">设为默认地址</a>
						<a class="link-btn" @click="edit(${address.id},$event)" href="javascript:void(0);">编辑</a>
					</div>
					<a class="address-delete" href="javascript:void(0);" @click="deleteAddress(${address.id})">X</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<!--footer begin-->
<jsp:include page="../../core/footer-main.jsp"/>
<!--footer end-->

<!--pop begin-->
<jsp:include page="include/address_popup.jsp"/>
<!--pop end-->
<script src="/resources/core/js/require.js" data-main="/resources/my-account/address/address"></script>
</body>
</html>