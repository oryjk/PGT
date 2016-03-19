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
			   style="background: url('/${image.path}') center center no-repeat;display: block"></a>
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
	<ul class="pawn-point-list">
		<li class="pawn-point-item pawn-point-item-current" style="background:url('../core/images/data/pawn-1.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">房产典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item" style="background:url('../core/images/data/pawn-2.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">贵金属典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item" style="background:url('../core/images/data/pawn-3.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">珠宝典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item" style="background:url('../core/images/data/pawn-4.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">车辆典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item" style="background:url('../core/images/data/pawn-5.jpg') no-repeat 77% center">
			<a class="pawn-point-link" href="#">
				<div class="pawn-point-text">
					<div class="pawn-point-head">名表典当</div>
					<div class="pawn-point-content">专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅速放款.典当率高.专业的房产典当,当天迅</div>
				</div>
			</a>
		</li>
		<li class="pawn-point-item" style="background:url('../core/images/data/pawn-6.jpg') no-repeat 77% center">
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
							<li><a class="category-nav-item" href="/tender/tenderList?ctype=TENDER_HIERARCHY&cid=${subCategory.id}">${subCategory.name}</a></li>
						</c:forEach>
						<!---<li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">和田碧玉</a></li>-->
					</ul>
				</div>
			</div>
			<ul class="invest-list">
				<c:forEach items="${rootCategory.esChildren[0].hotTenders}" var="onSaleTender">
					<li>
						<div class="invest-inner">
							<a class="img-box" href="#">
								<img src="${onSaleTender.p2pAdvertisement.path}" alt="产品的名字"/>
							</a>
							<h4><a href="#">${onSaleTender.name}</a></h4>

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

<script src="/resources/core/js/require.js" data-main="/resources/index/index"></script>
</body>
</html>