<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="addressLimitNumber" value="20"/>
<c:set var="isLimited" value="${fn:length(addressList) == addressLimitNumber}"></c:set>
<!-- 详细内容列表-->
<div id="main" class="main-box">
    <!-- 消息-->
    <div class="message-box">
        <p>
            您已经保存了<span>${empty addressList? 0: fn:length(addressList)}</span>个地址, 还可以增加<span>${empty addressList? addressLimitNumber: addressLimitNumber - fn:length(addressList) }</span>个地址
        </p>
    </div>

    <!--面包屑-->
    <div class="bread-nav">
        <p>
            <a href="#">个人中心</a>
            >
            <a href="#">个人管理</a>
            >
            <a href="#">地址管理</a>
        </p>
    </div>

    <!-- 地址列表-->
    <div class="address-list">
    	<c:if test="${!isLimited }">
	        <div id="addAddress" class="add-address">
	            <div class="row1">
	                <i class="foundicon-plus"></i>
	            </div>
	            <div class="row2">
	                <span>增加新地址</span>
	            </div>
	        </div>
        </c:if>
       	<c:forEach items="${addressList }" var="address" varStatus="i">
        	<div class="address ${i.index==0? 'default-address' : ''}">
	            <div class="row1">
		            <c:if test="${i.index!=0}">
		                <a class="link-btn js-set-default-address" href="javascript:void(0);"  data-href="setDefaultAddress/${address.id}"><i class="foundicon-home"></i> 设为默认地址</a>
		            </c:if>
	                <a class="link-btn right js-update-address" href="javascript:void(0);" data-href="updateAddress/${address.id}" data-address-id="${address.id}">修改地址</a>
	            </div>
	            <div class="row2">
	                <span>${address.name }</span><!--
	                --><span class="divide">|</span><!--
	                --><span>${address.phone }</span>
	            </div>
	            <div class="row3">${address.province }${address.city }${address.district }</div>
	            <div class="row4"><span>${address.address}</span></div>
	            <div class="row5">
	                <a class="link-btn right js-delete-address" href="javascript:void(0);" data-href="deleteAddress/${address.id}"><i class="foundicon-trash"></i> 删除</a>
	            </div>
	        </div>
        </c:forEach>
    </div>
</div>

