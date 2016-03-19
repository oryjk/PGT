<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags" prefix="date" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/index/index.css"/>
	<style>
		.main,
		.hover-main:hover {
			color: #c90304;
		}

		.em,
		.hover-em:hover {
			color: #86b4ed;
		}

		.bg-main,
		.hover-bg-main:hover {
			background: #c90304;
		}

		.bg-em,
		.hover-bg-em:hover {
			background: #86b4ed;
		}

		.category .each-category-1 .category-nav-choose {
			color: #441209;
		}

		.category .each-category-2 .category-nav-choose {
			color: #bf2204;
		}

		.category .each-category-3 .category-nav-choose {
			color: #79a364;
		}

		.category .each-category-end .category-nav-choose {
			color: #333;
		}
	</style>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<!--banner begin-->
<!--super:时差滚动轮播图,把图片放在#bannerBackEnd里面,把文字和链接放在#bannerFrontEnd里面-->
<div id="bannerBox" class="banner-box">
	<div id="banner" class="banner">
		<a href="#" data-banner="0"
		   style="background: url('../core/images/data/banner-3.jpg') center center no-repeat;display: block"></a>
		<a href="#" data-banner="1"
		   style="background: url('../core/images/data/banner-2.jpg') center center no-repeat"></a>
		<a href="#" data-banner="2"
		   style="background: url('../core/images/data/banner-1.jpg') center center no-repeat"></a>
		<a href="#" data-banner="3"
		   style="background: url('../core/images/data/banner-4.jpg') center center no-repeat"></a>
	</div>
	<ol id="bannerNav" class="banner-nav">
		<li class="banner-nav-current">0</li>
		<li>1</li>
		<li>2</li>
		<li>3</li>
	</ol>
</div>
<!--banner end-->

<!--good begin-->
<div class="goods-box">
	<div class="our-goods">
		<div class="our-good">
			<div class="icon-box  bg-main">
				<i class="foundicon-people"></i>
			</div>
			<div class="good-text">
				<h4>经过严苛鉴定</h4>
				<p>典当行为安全抵押必须严格鉴定商品</p>
			</div>
		</div>
		<div class="our-good">
			<div class="icon-box bg-main">
				<i class="foundicon-heart "></i>
			</div>
			<div class="good-text">
				<h4>低于成本的超值</h4>
				<p>典当时会根据真实价值折价抵押</p>
			</div>
		</div>
		<div class="our-good">
			<div class="icon-box  bg-main">
				<i class="foundicon-star"></i>
			</div>
			<div class="good-text">
				<h4>难觅之时机</h4>
				<p>在当品抵押时是您入手的天赐良机</p>
			</div>
		</div>
		<div class="our-good">
			<div class="icon-box bg-main">
				<i class="foundicon-clock"></i>
			</div>
			<div class="good-text">
				<h4>正反都是赚</h4>
				<p>即使赎当您仍然可获得超高赔付</p>
			</div>
		</div>
		<div class="our-good">
			<div class="icon-box bg-main">
				<i class="foundicon-smiley"></i>
			</div>
			<div class="good-text">
				<h4>入手绝当</h4>
				<p>绝当即可获得市面无法买到的珍宝</p>
			</div>
		</div>
	</div>
</div>
<!--good end-->

<!--hottest begin-->
<div class="hottest">
	<h2 class="headline"><div>热门在当品</div></h2>
	<ul class="hottest-list">
		<li class="hottest-item">
			<a class="hottest-link" href="#">
				<img class="hottest-img" src="../core/images/data/hottest-0.jpg" alt=""/>
				<div class="hottest-text">精美品质 艺术瑰宝</div>
			</a>
		</li>
		<li class="hottest-item">
			<a class="hottest-link" href="#">
				<img class="hottest-img" src="../core/images/data/hottest-1.jpg" alt=""/>
				<div class="hottest-text">精美品质 艺术瑰宝</div>
			</a>
		</li>
		<li class="hottest-item">
			<a class="hottest-link" href="#">
				<img class="hottest-img" src="../core/images/data/hottest-2.jpg" alt=""/>
				<div class="hottest-text">精美品质 艺术瑰宝</div>
			</a>
		</li>
		<li class="hottest-item hottest-item-end">
			<a class="hottest-link" href="#">
				<img class="hottest-img" src="../core/images/data/hottest-3.jpg" alt=""/>
				<div class="hottest-text">精美品质 艺术瑰宝</div>
			</a>
		</li>
	</ul>
</div>
<!--hottest end-->

<!--pawn begin-->
<div class="pawn">
	<h2 class="headline"><div>在线典当</div></h2>
	<ul class="pawn-point-list">
		<li class="pawn-point-item pawn-point-item-current" style="background:url('../core/images/data/pawn-1.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">房产典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item"  style="background:url('../core/images/data/pawn-2.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">贵金属典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item"  style="background:url('../core/images/data/pawn-3.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">珠宝典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item"  style="background:url('../core/images/data/pawn-4.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">车辆典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item"  style="background:url('../core/images/data/pawn-5.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">名表典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item"  style="background:url('../core/images/data/pawn-6.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">民用品典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
	</ul>
	<ul class="pawn-nav-list">
		<li class="pawn-nav-item pawn-nav-current" data-value="0">
			<a class="pawn-nav-link" href="#">立即了解</a>
			<img class="pawn-nav-img" src="../core/images/data/pawn-1.jpg" alt=""/>
		</li>
		<li class="pawn-nav-item" data-value="1">
			<a class="pawn-nav-link" href="#">立即了解</a>
			<img class="pawn-nav-img" src="../core/images/data/pawn-2.jpg" alt=""/>
		</li>
		<li class="pawn-nav-item" data-value="2">
			<a class="pawn-nav-link" href="#">立即了解</a>
			<img class="pawn-nav-img" src="../core/images/data/pawn-3.jpg" alt=""/>
		</li>
		<li class="pawn-nav-item" data-value="3">
			<a class="pawn-nav-link" href="#">立即了解</a>
			<img class="pawn-nav-img" src="../core/images/data/pawn-4.jpg" alt=""/>
		</li>
		<li class="pawn-nav-item" data-value="4">
			<a class="pawn-nav-link" href="#">立即了解</a>
			<img class="pawn-nav-img pawn-nav-end" src="../core/images/data/pawn-5.jpg" alt=""/>
		</li>
		<li class="pawn-nav-item" data-value="5">
			<a class="pawn-nav-link" href="#">立即了解</a>
			<img class="pawn-nav-img pawn-nav-end" src="../core/images/data/pawn-6.jpg" alt=""/>
		</li>

	</ul>

</div>
<!--pawn end-->

<!--category begin-->
<div class="category">
	<h2 class="headline"><div>在当品预售</div></h2>

	<div class="each-category each-category-1" style="border-top: 2px solid #441209;">
		<div class="list-head" style="background-image: linear-gradient(180deg,#441209,#793591);">
			<div class="list-head-content" style="background: url('') no-repeat left bottom">
				<h3>珠宝在当</h3>
				<ul class="category-nav">
					<li><a class="category-nav-item" href="javascript:void(0);">翡翠</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">寿山石</a></li>
					<li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">和田碧玉</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">和田白玉</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">独山玉</a></li>
				</ul>
			</div>
		</div>
		<ul class="invest-list">
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-1.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-2.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-3.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="each-category each-category-2" style="border-top: 2px solid #bf2204;">
		<div class="list-head" style="background-image: linear-gradient(180deg,#bf2204,#793591);">
			<div class="list-head-content" style="background: url('') no-repeat left bottom">
				<h3>珠宝在当</h3>
				<ul class="category-nav">
					<li><a class="category-nav-item" href="javascript:void(0);">翡翠</a></li>
					<li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">寿山石</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">和田碧玉</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">和田白玉</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">独山玉</a></li>
				</ul>
			</div>
		</div>
		<ul class="invest-list">
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-4.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-5.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-1.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
		</ul>
	</div>

	<div class="each-category each-category-3" style="border-top: 2px solid #441209;">
		<div class="list-head" style="background-image: linear-gradient(180deg,#441209,#79a364);">
			<div class="list-head-content" style="background: url('') no-repeat left bottom">
				<h3>珠宝在当</h3>
				<ul class="category-nav">
					<li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">翡翠</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">寿山石</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">和田碧玉</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">和田白玉</a></li>
					<li><a class="category-nav-item" href="javascript:void(0);">独山玉</a></li>
				</ul>
			</div>
		</div>
		<ul class="invest-list">
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-2.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-3.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
			<li>
				<div class="invest-inner">
					<a class="img-box" href="#"><img src="../core/images/data/product-4.jpg"
													 alt="产品的名字"/></a>
					<h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>

					<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
					<div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
					<div class="invest-row-3">剩余数量:<span>6</span>个</div>
				</div>
			</li>
		</ul>
	</div>

	<div class="each-category each-category-end" style="border-top: 2px solid #333;">
		<div class="list-head" style="background-image: linear-gradient(180deg,#333,#d56a0d);">
			<div class="list-head-content" style="background: url('') no-repeat left bottom">
				<h3>珠宝在当</h3>
				<ul class="category-nav">
					<li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">新手专区</a></li>
				</ul>
			</div>
		</div>
		<div class="help">
			<div class="help-hot">
				<a href="#" class="help-img-box"><img src="../core/images/product/help_img_1.png" alt="#"/></a>
				<a href="#" class="help-img-box"><img src="../core/images/product/help_img_2.png" alt="#"/></a>
			</div>
			<ul class="help-list">
				<li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
				<li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
				<li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
				<li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
				<li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
				<li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
			</ul>
		</div>
		<div class="app-code">
			<div class="app-row-1">点金子淘在当APP</div>
			<div class="app-row-2">玩转购物新方式</div>
			<div class="app-row-3"><img src="../core/images/product/app-qrcode.png" alt=""/></div>
			<div class="app-row-4">扫我 轻松得实惠</div>
		</div>
	</div>
</div>

<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/index/index"></script>
</body>
</html>