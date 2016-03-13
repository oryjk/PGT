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
				<div class="pop-address-value"><input class="pop-address-name" type="text"/></div>

				<div class="pop-address-title">所在地区: <span class="pop-error">错误提示</span></div>
				<div class="pop-address-value">
					<!-- 仿select组件begin-->
					<div class="invest-province-select">
						<a id="province" class="select-view" href="#">{{currentAddress}}
							<span class="selected">{{currentAddress.province}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options">
							<li><a class="option-view" data-value="0" href="#">四川</a></li>
							<li><a class="option-view" data-value="1" href="#">湖南</a></li>
							<li><a class="option-view" data-value="2" href="#">江西</a></li>
						</ul>
						<input class="select-value" name="" type="hidden" value=""/>
					</div>
					<!-- 仿select组件end-->
					<!-- 仿select组件begin-->
					<div class="invest-city-select">
						<a id="city" class="select-view" href="#">
							<span class="selected">{{currentAddress.city}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options">
							<li><a class="option-view" data-value="0" href="#">四川</a></li>
							<li><a class="option-view" data-value="1" href="#">湖南</a></li>
							<li><a class="option-view" data-value="2" href="#">江西</a></li>
						</ul>
						<input class="select-value" name="" type="hidden" value=""/>
					</div>
					<!-- 仿select组件begin-->
					<!-- 仿select组件end-->
					<div class="invest-country-select">
						<a id="country" class="select-view" href="#">
							<span class="selected">{{currentAddress.district}}</span>
							<i class="foundicon-down-arrow"></i>
						</a>
						<ul class="options">
							<li><a class="option-view" data-value="0" href="#">四川</a></li>
							<li><a class="option-view" data-value="1" href="#">湖南</a></li>
							<li><a class="option-view" data-value="2" href="#">江西</a></li>
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
					<input id="popSubmit" class="pop-confirm" type="button" value="确认"/>
					<input id="popReset" class="pop-cancel" type="reset" value="取消"/>
				</div>
			</form>
		</div>
	</div>
</div>