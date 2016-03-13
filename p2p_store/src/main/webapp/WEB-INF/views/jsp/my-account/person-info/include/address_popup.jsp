<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="popUp" class="pop-up" v-bind:style="{display:display}">
	<div class="wrap">
		<div class="inner">
			<h3>
				<span id="popTitle" class="pop-title">新增收货地址</span>
				<span id="popClose" @click="closePop" class="close">X</span>
			</h3>

			<form id="popForm" class="pop-content" action="">

				<div class="pop-address-title">收货人: <span class="pop-error">错误提示</span></div>
				<div class="pop-address-value"><input class="pop-address-name" type="text" value="{{currentAddress.name}}"/></div>

				<div class="pop-address-title">所在地区: <span class="pop-error">错误提示</span></div>
				<div class="pop-address-value">
					<!-- 仿select组件begin-->
					<div class="invest-province-select">
						<a id="province" class="select-view" href="#" @click="province_display='block'">
							<span class="selected">{{currentAddress.province}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options" v-bind:style="{display:province_display}">
							<c:forEach items="${provinceList}" var="province">
								<li><a class="option-view" data-value="${province.id}" href="#" @click="queryCityByProvinceId(${province.id},$event)">${province.name}</a></li>
							</c:forEach>
						</ul>
						<input class="select-value" name="" type="hidden" value=""/>
					</div>
					<!-- 仿select组件end-->
					<!-- 仿select组件begin-->
					<div class="invest-city-select">
						<a id="city" class="select-view" href="#" @click="city_display='block'">
							<span class="selected">{{currentAddress.city}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options" v-bind:style="{display:city_display}">
							<li v-for="city in cities">
								<a class="option-view" @click="getAreaByCityId(city.id,$event)" href="#">{{city.name}}</a>
							</li>
						</ul>
						<input class="select-value" name="" type="hidden" value=""/>
					</div>
					<!-- 仿select组件begin-->
					<!-- 仿select组件end-->
					<div class="invest-country-select">
						<a id="country" class="select-view" href="#" @click="district_display='block'">
							<span class="selected">{{currentAddress.district}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options" v-bind:style="{display:district_display}">

							<li v-for="district in districts">
								<a class="option-view" data-value="0" href="#" @click="saveDistrict(district.id,$event)">{{district.name}}</a>
							</li>
						</ul>
						<input class="select-value" name="" type="hidden" value=""/>
					</div>
					<!-- 仿select组件end-->
				</div>

				<div class="pop-address-title">详细地址: <span class="pop-error">错误提示</span></div>
				<div class="pop-address-value"><input class="pop-address-address" type="text" value="{{currentAddress.address}}"/></div>

				<div class="pop-address-title">手机号码: <span class="pop-error">错误提示</span></div>
				<div class="pop-address-value"><input class="pop-address-phone" type="text" value="{{currentAddress.phone}}"/></div>
				<div class="pop-address-title">座机号码: <span class="pop-error">错误提示</span></div>
				<div class="pop-address-value"><input class="pop-address-telephone" type="text" value="{{currentAddress.telephone}}"/></div>

				<div class="pop-btn">
					<input id="popSubmit" class="pop-confirm" type="button" value="确认" @click="saveAddress"/>
					<input id="popReset" class="pop-cancel" type="reset" value="取消" @click="closePop"/>
				</div>
			</form>
		</div>
	</div>
</div>