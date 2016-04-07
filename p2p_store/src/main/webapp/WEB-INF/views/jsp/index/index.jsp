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
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<!--banner begin-->
<!--super:时差滚动轮播图,把图片放在#bannerBackEnd里面,把文字和链接放在#bannerFrontEnd里面-->
<div id="bannerBox" class="banner-box">
	<div id="banner" class="banner">
		<c:forEach items="${banner.images}" var="image" varStatus="status">
			<a href="${image.url}" data-banner="${status.index}"
			   style="background: url('${image.path}') center center no-repeat;display: ${status.index==0?'block':'none'}"></a>
		</c:forEach>
	</div>
	<ol id="bannerNav" class="banner-nav">
		<c:forEach items="${banner.images}" varStatus="status">
			<li class="${status.index==0?'banner-nav-current':''}">${status.index}</li>
		</c:forEach>
	</ol>
</div>
<!--banner end-->

<!--good begin-->
<div class="goods-box">
	<div class="our-goods">
		<div class="our-good">
			<div class="icon-box  bg-main">
				<img src="../core/images/index/icon1.png" alt=""/>
			</div>

		</div>
		<div class="our-good">
			<div class="icon-box bg-main">
				<img src="../core/images/index/icon2.png" alt=""/>
			</div>

		</div>
		<div class="our-good">
			<div class="icon-box  bg-main">
				<img src="../core/images/index/icon3.png" alt=""/>
			</div>

		</div>
		<div class="our-good">
			<div class="icon-box bg-main">
				<img src="../core/images/index/icon4.png" alt=""/>
			</div>

		</div>
		<div class="our-good">
			<div class="icon-box bg-main">
				<img src="../core/images/index/icon5.png" alt=""/>
			</div>

		</div>
	</div>
</div>
<div class="goods-box">
	<div class="our-goods">
		<div class="our-good">
			<div class="good-text">
				<h4>经过严苛鉴定</h4>
				<p>典当行为安全</p>
				<p>抵押必须严格鉴定商品</p>
			</div>

		</div>
		<div class="our-good">
			<div class="good-text">
				<h4>低于成本的超值</h4>
				<p>典当时会根据</p>
				<p>真实价值折价抵押</p>
			</div>

		</div>
		<div class="our-good">
			<div class="good-text">
				<h4>难觅之时机</h4>
				<p>在当品抵押时</p>
				<p>是您入手的天赐良机</p>
			</div>

		</div>
		<div class="our-good">
			<div class="good-text">
				<h4>正反都是赚</h4>
				<p>即使赎当</p>
				<p>您仍然可获得超高赔付</p>
			</div>

		</div>
		<div class="our-good">
			<div class="good-text">
				<h4>入手绝当</h4>
				<p>绝当即可获得</p>
				<p>市面无法买到的珍宝</p>
			</div>

		</div>
	</div>
</div>
<!--good end-->

<!--hottest begin-->
<div class="hottest">
	<h2 class="headline">
		<div>热门在当品</div>
	</h2>
	<ul class="hottest-list">
		<c:forEach items="${siteHot}" var="tender">
			<li class="hottest-item">
				<a class="hottest-link" href="#">
					<img class="hottest-img" src="${tender.tender.p2pExpertMedia}" alt=""/>

					<div class="hottest-text">${tender.tender.name}</div>
				</a>
			</li>
		</c:forEach>
	</ul>
</div>
<!--hottest end-->

<!--pawn begin-->
<div class="pawn">
	<h2 class="headline">
		<div>在线典当</div>
	</h2>
	<ul class="pawn-point-list" style="overflow: hidden">
		<c:forEach items="${livePawnList}" var="livePawn" varStatus="status">
			<li class="pawn-point-item pawn-point-item-current" style="background:url('${livePawn.frontMedia.path}') no-repeat 77% center;background-size:100% 100%">
				<a class="pawn-point-link" href="#">
					<div class="pawn-point-text">
						<div class="pawn-point-head">${livePawn.name}</div>
						<div class="pawn-point-content">${livePawn.description}</div>
					</div>
				</a>
			</li>
		</c:forEach>
	</ul>
	<ul class="pawn-nav-list">
		<c:forEach items="${livePawnList}" var="livePawn">
			<li class="pawn-nav-item pawn-nav-current" data-value="0">
				<a class="pawn-nav-link" href="/pawnPersonInfo/createPawnPersonInfo?url=${livePawn.frontMedia.path}&type=${livePawn.name}">立即了解${livePawn.frontMedia.id}</a>
				<img class="pawn-nav-img" src="${livePawn.frontMedia.path}" alt=""/>
			</li>
		</c:forEach>
	</ul>

</div>
<!--pawn end-->

<!--category begin-->
<div class="category" style="min-height:300px;">
	<h2 class="headline">
		<div>在当品预售</div>
	</h2>

	<c:forEach items="${rootHomeCategories}" var="rootCategory" varStatus="status">
		<div class="each-category each-category-${status.index+1}" style="border-top: 2px solid #441209;">
			<div class="list-head" style="background-image: linear-gradient(180deg,#441209,#793591);">
				<div class="list-head-content" style="background: url('') no-repeat left bottom">
					<h3>${rootCategory.name}</h3>
					<ul class="category-nav">
						<c:forEach items="${rootCategory.esChildren}" var="subCategory">
							<li><a class="category-nav-item" href="/tender/tenderList?ctype=TENDER_HIERARCHY&cid=${subCategory.id}" data-value="${subCategory.id}">${subCategory.name}</a></li>
						</c:forEach>
						<!---<li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">和田碧玉</a></li>-->
					</ul>
				</div>
			</div>
			<ul class="invest-list">
				<c:forEach items="${rootCategory.esChildren[0].hotTenders}" var="onSaleTender">
					<li>${tenderId}
						<div class="invest-inner">
							<a class="img-box" href="/tender/${onSaleTender.tenderId}">
								<img src="${onSaleTender.p2pAdvertisement.path}" alt="产品的名字"/>
							</a>
							<h4><a href="/tender/${onSaleTender.tenderId}">${onSaleTender.name}</a></h4>

							<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
							<div class="invest-row-2">截止日期:<span><date:date value="${onSaleTender.dueDate}" style="yyyy年MM月dd日"/></span></div>
							<div class="invest-row-3">剩余数量:<span>${onSaleTender.productResidue}</span>个</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</div>

<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<!-- side-bar begin -->
<div class="side-bar">
	<a class="side-bar-img" href="#">
		<img src="../core/images/side-bar/icon1.png" alt=""/>
		<p>个人中心</p>
	</a>
	<a class="side-bar-img" href="#">
		<img src="../core/images/side-bar/icon2.png" alt=""/>
		<p>订单管理</p>
	</a>
	<a class="side-bar-img" href="#"><img src="../core/images/side-bar/icon3.png" alt=""/>
		<p>最近浏览</p></a>
	<a class="side-bar-img" href="#"><img src="../core/images/side-bar/icon4.png" alt=""/>
		<p>我的收藏</p></a>
	<a class="side-bar-img" href="#"><img src="../core/images/side-bar/icon6.png" alt=""/>
		<p>客服中心</p></a>
	<a class="side-bar-img" href="#"><img src="../core/images/side-bar/icon5.png" alt=""/>
		<p>回到顶端</p></a>
</div>

<script src="/resources/core/js/require.js" data-main="/resources/index/index"></script>
</body>
</html>