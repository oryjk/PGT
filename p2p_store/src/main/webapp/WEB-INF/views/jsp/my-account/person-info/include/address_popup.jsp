<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="popUp" class="pop-up" v-bind:style="{display:display}">
	<div class="wrap">
		<div class="inner">
			<h3>
				<span id="popTitle" class="pop-title">{{title}}</span>
				<span id="popClose" @click="closePop" class="close">X</span>
			</h3>

			<form id="popForm" class="pop-content" action="">

				<div class="pop-address-title">收货人:</div>
				<div class="pop-address-value">
					<input class="pop-address-name" type="text" v-model="currentAddress.name"/>
				</div>

				<div class="pop-address-title">所在地区:</div>
				<div class="pop-address-value">
					<!-- 仿select组件begin-->
					<div class="invest-province-select">
						<a id="province" class="select-view" href="#" @click="province_display='block'">
							<span class="selected">{{currentAddress.province}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options" v-bind:style="{display:province_display}">
							<c:forEach items="${provinceList}" var="province">
								<li @click="queryCityByProvinceId(${province.id},$event)"><a class="option-view" data-value="${province.id}" href="#">${province.name}</a></li>
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
							<li v-for="city in cities" @click="getAreaByCityId(city.id,$event)">
								<a class="option-view"  href="#">{{city.name}}</a>
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

							<li v-for="district in districts" @click="saveDistrict(district.id,$event)">
								<a class="option-view" data-value="0" href="#">{{district.name}}</a>
							</li>
						</ul>
						<input class="select-value" name="" type="hidden" value=""/>
					</div>
					<!-- 仿select组件end-->
				</div>

				<div class="pop-address-title">详细地址:</div>
				<div class="pop-address-value"><input class="pop-address-address" type="text" v-model="currentAddress.address"/></div>

				<div class="pop-address-title">手机号码:</div>
				<div class="pop-address-value"><input class="pop-address-phone" type="text" v-model="currentAddress.phone"/></div>
				<div class="pop-address-title">座机号码:</div>
				<div class="pop-address-value"><input class="pop-address-phone" type="text" v-model="currentAddress.telephone"/></div>

				<div class="pop-btn">

					<span class="pop-error" style="float: left" v-if="!isValid">*{{errorMsg}}</span>
					<input id="popSubmit" class="pop-confirm" type="button" value="确认" @click="saveAddress"/>
					<input id="popReset" class="pop-cancel" type="reset" value="取消" @click="closePop"/>
				</div>
			</form>
		</div>
	</div>
</div>