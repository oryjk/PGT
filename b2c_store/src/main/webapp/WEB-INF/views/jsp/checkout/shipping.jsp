<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="${juedangpinStaticPath}" var="staticPath"/>
<c:set var="isDelivery" value="${'PICKUP' ne order.shippingVO.shippingType}"></c:set>
<c:set var="selectedAddress" value="${order.shippingVO.shippingAddress}"></c:set>
<c:set var="selectedPickup" value="${order.shippingVO.shippingMethod}"></c:set>
<c:set var="currentAddress" value="${empty selectedAddress? defaultAddress: selectedAddress}"/>
<c:set var="hasStores" value="${not empty storeList}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet" href="${staticPath}/shopping-cart/cart.css"/>
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <div class="top-box">
        <div class="top-status">
            <div class="status-box">
                <ul class="top-nav">
                    <li>
                        <a href="#">手机绝当品</a>
                    </li>
                    <li><a href="#">帮助中心</a></li>
                    <li><a href="#">网站导航</a></li>
                </ul>
                <ul class="have-login">
                    <li>
                        <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"><span> 欢迎您：</span><span>${currentUser.username}</span></a>
                    </li>
                    <li><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>">账户管理</a></li>
                    <li><a href="<spring:url value="${urlConfiguration.logoutPage}"/>">退出登陆</a></li>
                </ul>
            </div>
        </div>
        <div class="top-banner">
            <div class="top-banner-box">
                <a href="#"></a>
            </div>
        </div>
    </div>
    <div class="logo-box">
        <h1>
            <a href="..${urlConfiguration.shoppingCartPage}">
                购物车
            </a>
        </h1>
        <ul id="step" class="step2">
            <li>
                <h3 class="step1-text">我的购物车</h3>
            </li>
            <li>
                <h3 class="step2-text">确认订单</h3>
            </li>
            <li>
                <h3 class="step3-text">选择支付方式</h3>
            </li>
            <li>
                <h3 class="step4-text">支付成功</h3>
            </li>
        </ul>
    </div>
</div>

<!--正文-->
<div class="content-box">
    <div class="content-2"  id="content2" style="display: block">
        <div class="content-title">
            <h2>确认订单</h2>
            <div class="back-last">
                <a class="link-btn" href="..${urlConfiguration.shoppingCartPage}">
                    返回购物车 >
                </a>
            </div>
        </div>

        <form id="addPickup" class="post-way" action="addPickup">
            <h3>配送方式</h3>
            <fieldset>
                <div class="row">
                    <input id="js-delivery-shipping" class="${isDelivery ? 'd-btn': 'l-btn'}" type="button" data-shipping-type="DELIVERY" value="顺丰包邮"/>
	                <c:if test="${hasStores}">  
	                  <input id="js-pickup-shipping" class="${!isDelivery ? 'd-btn': 'l-btn'}" type="button" data-shipping-type="PICKUP" value="上门自提"/>
	                </c:if>
                </div>
                <div class="row" id="js-selected-delivery-info" ${isDelivery ? '':'style="display:none"'}>
                    <span>您的选择是:</span>
                    	<span>顺丰包邮 预计12月3日到达</span>
                    	<span>
	                    	<c:if test="${not empty selectedAddress }">
	                    		${selectedAddress.province}${selectedAddress.city}${selectedAddress.district} ${selectedAddress.address} ${selectedAddress.phone} ${selectedAddress.name}
	                    	</c:if>
	                    </span>
                    <a class="link-btn js-change-shipping" data-is-delivery="${isDelivery}" href="javascript:void(0)" ${empty selectedAddress.id ? 'style="display:none"' : ''}>更改</a>
                </div>
                <c:if test="${hasStores}">
                <div class="row" id="js-selected-pickup-info" ${isDelivery ? 'style="display:none"' : ''}>
                    <span>您的选择是:</span>
                   	<span>上门自提</span>
                   	<span>  
                    	<c:forEach items="${storeList}" var="store">
                    		<c:if test="${store.id eq selectedPickup.locationId }">
                    			${store.address } ${store.phone } ${order.shippingVO.shippingMethod.locationId }
                    		</c:if>
                    	</c:forEach>
                    </span>
                    <a class="link-btn js-change-shipping" href="javascript:void(0)" ${empty selectedPickup.id ? 'style="display:none"' : ''}>更改</a>
                </div>
                </c:if>
                <div class="drop-area" style="display:none">
                    <div class="inner">
                        <div class="row">
                            <span>顺丰包邮</span>
                            <span>预计12月3日到达</span>
                        </div>
                        <div class="row">
                            <input class="d-btn" type="button" value="确定"/>
                            <input class="l-btn" type="button" value="取消"/>
                        </div>
                    </div>
                </div>
                <c:if test="${hasStores}">
                <div id="js-pickup-area" class="drop-area" ${isDelivery || not empty selectedPickup ? 'style="display:none"': ''}>
                    <div class="inner">
                        <p class="choose-has">
                            <a class="link-btn" href="#">查看全部自提点</a>
                        </p>
                        <div class="row">
                            <label>
                                姓名
                                <input type="text" name="name" maxlength="50" value="${selectedPickup.name}"/>
                            </label>
                            <label>
                                手机号码
                                <input type="text" name="phone" maxlength="11" value="${selectedPickup.phone}"/>
                            </label>
                        </div>
                        <div class="row">
                            <h4>请选择自提点:</h4>
                            <c:forEach items="${storeList}" var="store">
                            	<p class="get-self">
	                                <input name="locationId" type="radio" value="${store.id }" ${store.id eq selectedPickup.locationId ? 'checked':'' }/>
	                              ${store.address }
	                                <span>联系电话: ${store.phone }</span>
	                            </p>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <input id="js-add-pickup" class="d-btn" type="button" value="确定"/>
                            <input class="l-btn" type="button" value="取消"/>
                        </div>
                    </div>
                </div>
                </c:if>
            </fieldset>
        </form>

        <form id="addAddressForm" class="address" action="../my-account/person-info/addAddress" ${not empty selectedAddress ? 'style="display:none"':'' }>
            <h3>收货地址</h3>
            <fieldset>
                <p class="choose-has" ${empty defaultAddress ? 'style="display:none"':''}>
                    <a id="show-address-form" class="link-btn" href="javascript:void(0)">新增配送地址</a>
                </p>
                <c:forEach items="${addressInfoList }" var="addressInfo">
                	<div class="row">
	                    <input type="radio" class="js-address-item" name="addressInfoId" value="${addressInfo.id}"/>
	                    <span>${addressInfo.name} ${addressInfo.phone} ${addressInfo.province}${addressInfo.city}${addressInfo.district} ${addressInfo.address}</span>
	                    <a class="link-btn js-update-address" href="javascript:void(0)" data-href="../my-account/person-info/updateAddress/${addressInfo.id}" 
	                    data-find-adress-url="../my-account/person-info/findAddress/${addressInfo.id}">修改</a>
	                    <a class="link-btn js-delete-address" href="javascript:void(0)" data-href="../my-account/person-info/deleteAddress/${addressInfo.id}">删除</a>
	                </div>
                </c:forEach>
                <div id="js-address-form-area" class="drop-area" ${not empty defaultAddress ? 'style="display:none"':''}>
                    <div class="inner" style="background: url('${staticPath}/core/images/index/classify-2.jpg') no-repeat right center">
                        <table>
                            <colgroup class="col-1">
                            </colgroup>
                            <colgroup>
                            </colgroup>
                            <tr>
                                <th><span>*</span>收货人</th>
                                <td><input type="text" name="name" maxlength="50" placeholder="请准确填写"/></td>
                                <td><span class="js-error-msg"></span></td>
                            </tr>
                            <tr>
                                <th><span>*</span>手机</th>
                                <td><input type="text" name="phone" maxlength="11" placeholder="请准确填写11位手机号码"/></td>
                                <td><span class="js-error-msg"></span></td>
                            </tr>
                            <tr>
                                <th>电话</th>
                                <td><input type="text" name="telephone"  maxlength="20" placeholder="座机号码"/></td>
                                <td><span class="js-error-msg"></span></td>
                            </tr>
                            <tr>
                                <th><span>*</span>所在区域</th>
                                <td colspan="2">

                                    <!-- 仿select-->
                                    <div class="province">
                                        <a id="province" class="select-view"  href="#">
                                            <span class="selected">请选择</span>
                                            <i class="foundicon-down-arrow"></i>
                                        </a>
                                        <ul class="options">
                                        	<c:forEach items="${provinceList}" var="province">
                                        		<li><a class="option-view" data-value="${province.id}" href="#">${province.name}</a></li>
                                        	</c:forEach>
                                        </ul>
                                        <input class="select-value" name="province" type="hidden" value=""/>
                                    </div>省

                                    <!-- 仿select-->
                                    <div class="city">
                                        <a id="city" class="select-view"  href="#">
                                            <span class="selected">请选择</span>
                                            <i class="foundicon-down-arrow"></i>
                                        </a>
                                        <ul class="options">

                                        </ul>
                                        <input class="select-value" name="city" type="hidden" value=""/>
                                    </div>市

                                    <!-- 仿select-->
                                    <div class="county">
                                        <a id="country" class="select-view"  href="#">
                                            <span class="selected">请选择</span>
                                            <i class="foundicon-down-arrow"></i>
                                        </a>
                                        <ul class="options">

                                        </ul>
                                        <input class="select-value" name="district" type="hidden" value=""/>
                                    </div>区/县

                                </td>
                                <td><span class="js-error-msg"></span></td>
                            </tr>
                            <tr>
                                <th><span>*</span>详细地址</th>
                                <td><input type="text" name="address" maxlength="250" placeholder="不用重复填写省市信息区"/></td>
                                <td><span class="js-error-msg"></span></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td><input type="checkbox" name="primary"/>设置为默认收货地址</td>
                                <td></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td>
                                    <input class="d-btn" id="js-add-address" type="button" value="确认"/>
                                    <input class="l-btn" id="js-hide-address-form" type="button" value="选择已有"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </fieldset>

        </form>
		<form id="addAddressToOrder" action="addAddressToOrder" method="post" style="display:none">
			<input id="addressInfoId" name="addressInfoId" value="">
			<input id="js-add-address-to-order" type="button"/>
		</form>
		
        <form class="product-info">
            <h3>商品信息</h3>
            <fieldset>
                <table>
                    <tbody>
	                    <c:forEach var="commerceItem" items="${order.commerceItems}">
		                    <tr>
		                        <td>
		                           <img src="${pageContext.request.contextPath}/resources${commerceItem['snapshotMedia']['path']}"
	                                         alt="${empty commerceItem['snapshotMedia']['title'] ? commerceItem.name : commerceItem['snapshotMedia']['title']}" />
		                        </td>
		                        <td class="product-name">
		                            ${commerceItem.name}
		                        </td>
		                        <td>
		                            <span class="level">十成</span>
		                        </td>
		                        <td>
		                            有货
		                        </td>
		                        <td class="product-now-cost">
		                            ¥<span>${commerceItem.salePrice}</span>
		                        </td>
		                    </tr>
	                    </c:forEach>
                    </tbody>
                </table>

            </fieldset>
        </form>
        <form action="redirectToPayment" method="get">
	        <div class="sub-box">
	            <input class="d-btn" type="submit" value="提交订单"/>
	            <input class="l-btn" type="button" value="返回我的购物车" onclick="javascript:window.location.href='..${urlConfiguration.shoppingCartPage}'"/>
	        </div>
        </form>
    </div>

    <div id="recommend" class="recommend">
        <ul id="tab" class="tab">
            <li class="choose"><h2 data-tab="0">商品详情</h2></li>
            <li><h2 data-tab="1">我的收藏</h2></li>
            <li><h2 data-tab="2">最近浏览</h2></li>
        </ul>
        <div class="similar-box">
            <ul class="similar" id="rowList1">
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
            </ul>
            <div class="move-left-box">
                <a href="#"  id="moveLeft1"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight1">></a>
            </div>
        </div>
        <div class="similar-box" style="display: none">
            <ul class="similar" id="rowList2">
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>2698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
            </ul>
            <div class="move-left-box">
                <a href="#"  id="moveLeft2"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight2">></a>
            </div>
        </div>
        <div class="similar-box" style="display: none">
            <ul class="similar" id="rowList3">
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>3698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
            </ul>
            <div class="move-left-box">
                <a href="#"  id="moveLeft3"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight3">></a>
            </div>
        </div>

    </div>

</div>

<!--主脚部-->
<jsp:include page="../core/footer-main.jsp"></jsp:include>
</body>
<script src="${staticPath}/core/js/require.js" defer async="true" data-main="${staticPath}/shopping-cart/cart"></script>

</html>