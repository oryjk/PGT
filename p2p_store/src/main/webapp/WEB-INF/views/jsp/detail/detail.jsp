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
	<link rel="stylesheet" href="/resources/detail/detail.css"/>

</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>

<!--invest-part begin-->
<div class="invest">

	<h2 class="invest-head">${ESTender.tender.name}</h2>


	<p class="invest-sub-head"><date:date value="${ESTender.tender.publishDate}"
										  style="yyyy-MM-dd HH:mm:ss"/>-<date:date value="${ESTender.tender.dueDate}"
																				   style="yyyy-MM-dd HH:mm:ss"/>火热抢购中</p>

	<div class="invest-info">
		<div class="invest-img"><img src="${ESTender.tender.p2pHeroMedias[0].path}" alt="${ESTender.tender.imageDesc}"/>
		</div>
		<div class="invest-handle">
			<div class="invest-handle-group">
				<%-- tender favourite --%>
				<c:forEach var="fav" items="${tenderFavourites}">
					<c:if test="${fav.productId eq ESTender.tender.tenderId}">
						<c:set var="tenderFav" value="${fav}"/>
					</c:if>
				</c:forEach>
				<c:choose>
					<c:when test="${empty tenderFav}">
						<a class="invest-add-favorite" href="javascript:void(0);" data-id=""
						   data-pid="${ESTender.tender.tenderId}" data-type="6" data-processed="false">
							<span class="color-hover"></span>
							添加收藏</a>
					</c:when>
					<c:otherwise>
						<a class="invest-add-favorite" href="javascript:void(0);" data-id="${tenderFav.id}"
						   data-pid="${ESTender.tender.tenderId}" data-type="6" data-processed="true">已收藏</a>
					</c:otherwise>
				</c:choose>

				<a class="invest-scan-count">已浏览<span>123</span>次</a>
			</div>
			<div class="invest-share-group">
				<div class="bdsharebuttonbox">
					<a href="#" class="bds_more" data-cmd="more"></a>
					<a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
					<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"> </a>
					<a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
					<a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
					<a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
				</div>
			</div>
		</div>
		<div class="invest-data">
			<div class="invest-status">在当中</div>
			<div class="data-row-1">当票总金额</div>
			<div class="data-row-2">
				<div class="cost-box">
					<span class="cost">¥</span><span class="cost">${ESTender.tender.tenderTotal}</span>
				</div>
				<div class="invest-surplus-count">剩余数量 : <span>${ESTender.tender.productResidue}</span>件</div>
				<div class="invest-begin-time">开始时间 : <span><date:date value="${ESTender.tender.publishDate}"
																	   style="yyyy年MM月dd"/></span></div>
				<div class="invest-end-time">结束时间 : <span><date:date value="${ESTender.tender.dueDate}"
																	 style="yyyy年MM月dd HH点mm分ss"/></span></div>

			</div>
			<div class="data-row-3">
				<div class="joiner-count"><span>4</span>名参与者</div>
				<div class="surplus-time">剩余<span>4天5小时36分钟</span></div>
			</div>
		</div>
	</div>
	<div class="item-nav">
		<ul id="itemNavBox" style="left: 0;top: 0;">
			<c:forEach items="${ESTender.tender.p2pHeroMedias}" var="media">
				<li><a href="#"><img src="${media.path}" alt="${ESTender.tender.imageDesc}"/></a></li>
			</c:forEach>
		</ul>
		<a class="item-nav-right" id="itemNavRight" href="javascript:void(0);"></a>
		<a class="item-nav-left" id="itemNavLeft" href="javascript:void(0);"></a>
	</div>
	<c:if test="${!empty ESTender.tender.description}">
		<div class="what-is-this">
				${ESTender.tender.description}
		</div>
	</c:if>

</div>
<!--invest-part end-->

<!--detail-content begin-->
<div class="detail-content">
	<ul class="tab">
		<!-- super: 以下五个tab,添加类tab-choose时显示,不添加时隐藏,默认第一个显示-->
		<li class="tab-choose"><a href="javascripyt:(0);">产品简介</a></li>
		<li><a href="javascripyt:(0);">产品详情</a></li>
		<li><a href="javascripyt:(0);">产品咨询</a><span class="tab-item-count">250</span></li>
		<li><a href="javascripyt:(0);">产品讨论</a><span class="tab-item-count">250</span></li>
		<li><a href="javascripyt:(0);">参与者</a><span class="tab-item-count">250</span></li>
	</ul>
	<div class="content-box">
		<!-- super: 以下五个tab对应的content,添加类content-choose时显示,不添加时隐藏,默认第一个显示-->
		<!-- content-item-list begin -->
		<div class="content-item-list content-choose">

			<c:forEach items="${ESTender.tender.products}" var="product" varStatus="status">
				<c:set var="sq" value="${status.index}"/>
				<div class="each-item">
					<div class="item-img-box">
						<div class="middle-img-box">
							<!-- super:rel传入item的id号-->
							<a href="${product.heroMedias[0].path}" class="jqzoom" rel='${sq}' title="triumph">
								<img class="middle-img" src="${product.heroMedias[0].path}" title="triumph">
							</a>
						</div>

						<div class="small-img-box">
							<div class="thumblist">
								<ul>
									<!-- super:rel属性中,传入一个对象,gallery对应id号,smallimage对应主图的路径,largeimage对应大图的路径-->
									<c:forEach items="${product.heroMedias}" var="media" varStatus="status">
										<c:choose>
											<c:when test="${status.index==0}">
												<li>
													<a class="zoomThumbActive" href='javascript:void(0);'
													   rel="{gallery: '${sq}', smallimage: '${media.path}',largeimage: '${media.path}'}">
														<img class="small-img" src='${media.path}'>
													</a>
												</li>
											</c:when>

											<c:otherwise>
												<li>
													<a class="" href='javascript:void(0);'
													   rel="{gallery:'${sq}', smallimage: '${media.path}',largeimage: '${media.path}'}">
														<img class="small-img" src='${media.path}'>
													</a>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>

					<div class="item-info-box">
						<div class="item-row-1">
							<div class="col-title">产品名称:</div>
							<div class="col-content">${product.name}</div>
						</div>
						<div class="item-row-2">
							<div class="col-title">产品介绍:</div>
							<div class="col-content">
								<p>
										${product.title}
								</p>
							</div>
						</div>
						<div class="item-row-3">
							<div class="col-title">购买说明:</div>
							<div class="col-content">
								<p>
									金AU754镶钻水金戒指金AU754镶钻水金戒指金AU754镶钻水金戒指金AU754镶钻水金戒指金AU754镶钻水金戒指金AU754镶钻水金戒指金AU754镶钻水<span></span>
								</p>
							</div>
						</div>
						<div class="item-row-4">
							<div class="col-title">配送费用:</div>
							<div class="col-content">免运费</div>
						</div>
						<div class="item-row-5">
							<div class="col-title">预计收货时间:</div>
							<div class="col-content">现在下单,预计<span>2016年2月18日</span>送达</div>
						</div>
						<div class="item-row-6">
							<div class="col-title">单个金额:</div>
							<div class="col-content">
								<span class="item-cost">¥</span><span class="item-cost">${product.salePrice}</span>
							</div>
						</div>
						<c:choose>
							<c:when test="${product.stock<1 || ESTender.tender.residueDate<1}">
							</c:when>
							<c:otherwise>
								<div class="item-row-7">
									<div class="col-title">数量:</div>
									<div class="col-content">

										<span class="item-buy-count">1</span>

										<div class="plus-and-minus"><a class="item-buy-plus" href="javascript:void(0);">+</a><a
												class="item-buy-minus" href="javascript:void(0);">-</a>
										</div>
										( 库存数量:<span class="item-surplus-count">${product.stock}</span> )
									</div>
								</div>
							</c:otherwise>
						</c:choose>


						<div class="item-row-8">
							<div class="col-title">&nbsp; </div>
							<div class="col-content">

								<c:choose>
									<c:when test="${product.stock<1 || ESTender.tender.residueDate<1}">
										<a class="item-sold-out">已结束</a>
									</c:when>
									<c:otherwise>
										<a class="item-buy-now" id="item-buy-now" href="#">
											<span class="color-hover"></span>
											立即抢订</a>
									</c:otherwise>
								</c:choose>
									<%-- product favourite --%>
								<c:forEach var="fav" items="${productFavourites}">
									<c:if test="${fav.productId eq product.productId}">
										<c:set var="productFav" value="${fav}"/>
									</c:if>
								</c:forEach>
								<c:choose>
									<c:when test="${empty productFav}">
										<a class="item-join-favorite" href="javascript:void(0);" data-id=""
										   data-pid="${product.productId}" data-type="5" data-processed="false">
											<span class="color-hover"></span>
											添加收藏
										</a>
									</c:when>
									<c:otherwise>
										<a class="item-join-favorite" href="javascript:void(0);"
										   data-id="${productFav.id}" :q
										   data-pid="${product.productId}" data-type="5" data-processed="true">已收藏</a>
									</c:otherwise>
								</c:choose>

								<form method="post" class="addToCart" action="/checkout/create">
									<input type="hidden" name="tenderId" value="${ESTender.tender.tenderId}"/>
									<input type="hidden" name="productIds" value="${product.productId}"/>
									<input type="hidden" name="quantities" class="quantity" value="1"/>
								</form>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>


		</div>
		<!-- content-item-list end-->

		<!-- content-invest-detail begin-->
		<div class="content-invest-detail">
			<c:forEach items="${ESTender.tender.p2pMainMedia}" var="media">
				<img class="content-detail-image" src="${media.path}" alt="${ESTender.tender.imageDesc}"/>
			</c:forEach>
		</div>
		<!-- content-invest-detail end-->

		<!-- content-question begin-->
		<div class="content-question ">
			<form class="ask-question">
				<div class="ask-head">
					<h3>有什么问题想问我们的呢?</h3>

					<div class="ask-font-count">还可以输入 <span>140</span> 个字</div>
				</div>
				<div class="ask-text">
					<textarea cols="30" rows="10"></textarea>
				</div>
				<div class="ask-btn">
					<input class="ask-submit" type="button" value="提交问题"/>
				</div>
			</form>
			<ul class="question-list">
				<li class="each-question">
					<div class="user-face"><img src="../core/images/product/user-face.png" alt=""/></div>
					<div class="question-and-answer">
						<div class="question-row-1">小白菜</div>
						<div class="question-row-2">如果老婆遇到了比我更合适的男人,我应该怎么办才好?</div>
						<div class="question-row-3">回复:</div>
						<div class="question-row-4"><span>请不要问这种和产品无关的问题.</span>点金子淘在当祝您购物愉快!</div>
					</div>
					<div class="question-time">2015年12月12日 12:00</div>
				</li>
				<li class="each-question">
					<div class="user-face"><img src="../core/images/product/user-face.png" alt=""/></div>
					<div class="question-and-answer">
						<div class="question-row-1">小白菜</div>
						<div class="question-row-2">如果老婆遇到了比我更合适的男人,我应该怎么办才好?</div>
						<div class="question-row-3">回复:</div>
						<div class="question-row-4"><span>请不要问这种和产品无关的问题.</span>点金子淘在当祝您购物愉快!</div>
					</div>
					<div class="question-time">2015年12月12日 12:00</div>
				</li>
				<li class="each-question">
					<div class="user-face"><img src="../core/images/product/user-face.png" alt=""/></div>
					<div class="question-and-answer">
						<div class="question-row-1">小白菜</div>
						<div class="question-row-2">如果老婆遇到了比我更合适的男人,我应该怎么办才好?</div>
						<div class="question-row-3">回复:</div>
						<div class="question-row-4"><span>请不要问这种和产品无关的问题.</span>点金子淘在当祝您购物愉快!</div>
					</div>
					<div class="question-time">2015年12月12日 12:00</div>
				</li>
			</ul>
		</div>
		<!-- content-question end-->

		<!-- content-discuss begin-->
		<div class="content-discuss ">
			<form class="ask-question">
				<div class="ask-head">
					<h3>有什么问题想问我们的呢?</h3>

					<div class="ask-font-count">还可以输入 <span>140</span> 个字</div>
				</div>
				<div class="ask-text">
					<textarea cols="30" rows="10"></textarea>
				</div>
				<div class="ask-btn">
					<input class="ask-submit" type="button" value="提交评论"/>
				</div>
			</form>
			<ul class="question-list">
				<li class="each-question">
					<div class="user-face"><img src="../core/images/product/user-face.png" alt=""/></div>
					<div class="question-and-answer">
						<div class="question-row-1">小白菜</div>
						<div class="question-row-2">如果老婆遇到了比我更合适的男人,我应该怎么办才好?</div>
					</div>
					<div class="question-time">2015年12月12日 12:00</div>
				</li>
				<li class="each-question">
					<div class="user-face"><img src="../core/images/product/user-face.png" alt=""/></div>
					<div class="question-and-answer">
						<div class="question-row-1">小白菜</div>
						<div class="question-row-2">如果老婆遇到了比我更合适的男人,我应该怎么办才好?</div>
					</div>
					<div class="question-time">2015年12月12日 12:00</div>
				</li>
				<li class="each-question">
					<div class="user-face"><img src="../core/images/product/user-face.png" alt=""/></div>
					<div class="question-and-answer">
						<div class="question-row-1">小白菜</div>
						<div class="question-row-2">如果老婆遇到了比我更合适的男人,我应该怎么办才好?</div>
					</div>
					<div class="question-time">2015年12月12日 12:00</div>
				</li>
			</ul>
		</div>
		<!-- content-discuss end-->

		<!-- content-joiner begin-->
		<div class="content-joiner ">
			<c:forEach items="${ESTender.buyerMap}" var="buyer">
				<a class="touch-him" href="#" data-value="${buyer.userId}" data-product-name="${buyer.itemName}">
					<img class="joiner-face" src="${buyer.mediaPath}" alt="#"/>

					<div class="joiner-info">
						<div class="joiner-name">买家:${buyer.userName}</div>
						<div class="joiner-text">已预购<span>${buyer.itemName}</span></div>
					</div>
					<div class="touch-him-opacity"><span>与他联系</span></div>
				</a>
			</c:forEach>
		</div>
		<!-- content-jpiner end-->
	</div>
</div>
<!--detail-content-end-->

<!-- recommend begin-->
<div class="recommend-box">
	<div class="recommend">
		<div class="recommend-head">
			<h3>猜你喜欢</h3>
			<a href="javascript:void(0);" class="recommend-change">换一批</a>
		</div>
		<div class="recommend-list">
			<ul>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png"
															   alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar"></div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png"
															   alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar"></div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png"
															   alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar"></div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>
				<li class="recommend-item-end">
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png"
															   alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar"></div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>

			</ul>
		</div>
	</div>
</div>
<!-- recommend end-->

<jsp:include page="../core/footer-main.jsp"/>
<jsp:include page="../core/baidu.jsp"/>
<!-- turn left and right begin-->
<c:choose>
	<c:when test="${ESTender.nextId!=-1}">
		<a class="turn-right" href="/tender/${ESTender.nextId}"></a>
	</c:when>

	<c:otherwise>
		<a class="turn-right" href="#"></a>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${ESTender.preId!=-1}">
		<a class="turn-left" href="/tender/${ESTender.preId}"></a>
	</c:when>

	<c:otherwise>
		<a class="turn-left" href="#"></a>
	</c:otherwise>
</c:choose>
<div id="popUp" class="pop-up">
	<div class="wrap">
		<div class="inner">
			<h3>
				<span id="popTitle" class="pop-title">与他联系</span>
				<span id="popClose" class="close">X</span>
			</h3>

			<form id="popForm" class="pop-content" action="">

				<div class="pop-address-title">您的手机号: <span class="pop-error" id="sayError"></span></div>
				<div class="pop-address-value"><input name="phone" id="sayPhone" class="pop-phone" type="text" maxlength="11"/></div>

				<div class="pop-address-title">想对他说的话:</div>
				<div class="pop-address-value">
					<div class="pop-text-box">
						<textarea name="text" id="sayText" class="pop-text"></textarea>
					</div>
				</div>

				<div class="pop-address-value">备注: 请输入对他的留言, 我们将尽快向他转答.</div>

				<div class="pop-btn">
					<span id="popTips" class="pop-tips"></span>
					<input id="popSubmit" class="pop-confirm" type="button" value="确认"/>
					<input id="popReset" class="pop-cancel" type="reset" value="取消"/>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- turn left and right end-->

<script src="/resources/core/js/require.js" data-main="/resources/detail/detail"></script>
<script>window._bd_share_config = {
	"common": {
		"bdSnsKey": {},
		"bdText": "",
		"bdMini": "2",
		"bdMiniList": false,
		"bdPic": "",
		"bdStyle": "0",
		"bdSize": "16"
	},
	"share": {},
	"image": {"viewList": ["qzone", "tsina", "tqq", "renren", "weixin"], "viewText": "分享到：", "viewSize": "16"},
	"selectShare": {"bdContainerClass": null, "bdSelectMiniList": ["qzone", "tsina", "tqq", "renren", "weixin"]}
};
with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];</script>
</body>
</html>