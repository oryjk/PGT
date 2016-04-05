<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/product-list/tender_list_1.css"/>
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
	</style>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<div class="bread-crumbs">
	<a class="bread-crumbs-first" href="#">首页</a>
	<span> > </span>
	<a class="bread-crumbs-item" href="#">文玩杂项</a>
</div>

<div class="content">
	<div class="filter">
		<div class="filter-title">
			<span class="filter-name">${param.keyword}</span>
			<span class="filter-title-headline">商品筛选</span>
			<span class="filter-count">共计<span>100</span>个项目</span>
		</div>
		<div class="filter-content">
			<div class="filter-content-row">
				<div class="filter-content-headline">分类:</div>
				<ul class="filter-list">
					<c:forEach var="item" items="${rootCategoryList}">
						<li class="filter-item">
							<a class="filter-link"
							   href="/tender/tenderList?sort=${param.sort}&page=${param.page}&keyword=${param.keyword}&tenderFilter=${param.tenderFilter}&cid=${item.id}&ctype=TENDER_ROOT&">${item.name}</a>
							<ul class="filter-sub-list">
								<c:forEach var="sub" items="${item.children}">
									<li class="filter-sub-item">
										<a class="filter-sub-link"
										   href="/tender/tenderList?sort=${param.sort}&page=${param.page}&keyword=${param.keyword}&tenderFilter=${param.tenderFilter}&cid=${sub.id}&ctype=TENDER_HIERARCHY&">橄榄核桃系列</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</ul>
			</div>
			<%--<div class="filter-content-row">
				<div class="filter-content-headline">价格:</div>
				<ul class="filter-cost-list">
					<li class="filter-cost-item">
						<a class="filter-cost-link" href="#">1000~3000</a>
					</li>
					<li class="filter-cost-item">
						<a class="filter-cost-link" href="#">1000~3000</a>
					</li>
					<li class="filter-cost-item">
						<a class="filter-cost-link" href="#">1000~3000</a>
					</li>
					<li class="filter-cost-search">
						<input class="filter-cost-input-text" type="text"/>
						<span>-</span>
						<input class="filter-cost-input-text" type="text"/>
						<input class="filter-cost-btn" type="button" value="搜索"/>
					</li>
				</ul>
			</div>--%>
		</div>
	</div>

	<div class="content-body">
		<div class="vertical-recommend">
			<h3 class="vertical-recommend-headline">猜你喜欢</h3>
			<ul class="vertical-recommend-list">
				<li class="recommend-item">
					<a class="recommend-img-box" href="#">
						<img class="recommend-img" src="../core/images/data/product-2.jpg" alt=""/>

						<div class="recommend-hot"></div>
					</a>

					<p class="recommend-name">
						<a class="recommend-name-link" href="#">新信息拜奶奶典当行生肖戒指一套新信息拜奶奶典当行生肖戒指一套</a>
					</p>

					<div class="recommend-handle">
						<input class="recommend-buy" type="button" value="立即抢购"/>
                        <span class="recommend-count">
                            已有<span>21</span>人购买
                        </span>
					</div>
				</li>
				<li class="recommend-item">
					<a class="recommend-img-box" href="#">
						<img class="recommend-img" src="../core/images/data/product-3.jpg" alt=""/>

						<div class="recommend-hot"></div>
					</a>

					<p class="recommend-name">
						<a class="recommend-name-link" href="#">新信息拜奶奶典当行生肖戒指一套新信息拜奶奶典当行生肖戒指一套</a>
					</p>

					<div class="recommend-handle">
						<input class="recommend-buy" type="button" value="立即抢购"/>
                        <span class="recommend-count">
                            已有<span>21</span>人购买
                        </span>
					</div>
				</li>
				<li class="recommend-item">
					<a class="recommend-img-box" href="#">
						<img class="recommend-img" src="../core/images/data/product-4.jpg" alt=""/>

						<div class="recommend-hot"></div>
					</a>

					<p class="recommend-name">
						<a class="recommend-name-link" href="#">新信息拜奶奶典当行生肖戒指一套新信息拜奶奶典当行生肖戒指一套</a>
					</p>

					<div class="recommend-handle">
						<input class="recommend-buy" type="button" value="立即抢购"/>
                        <span class="recommend-count">
                            已有<span>21</span>人购买
                        </span>
					</div>
				</li>

				<li class="recommend-item">
					<a class="recommend-img-box" href="#">
						<img class="recommend-img" src="../core/images/data/product-5.jpg" alt=""/>

						<div class="recommend-hot"></div>
					</a>

					<p class="recommend-name">
						<a class="recommend-name-link" href="#">新信息拜奶奶典当行生肖戒指一套新信息拜奶奶典当行生肖戒指一套</a>
					</p>

					<div class="recommend-handle">
						<input class="recommend-buy" type="button" value="立即抢购"/>
                        <span class="recommend-count">
                            已有<span>21</span>人购买
                        </span>
					</div>
				</li>

				<li class="recommend-item">
					<a class="recommend-img-box" href="#">
						<img class="recommend-img" src="../core/images/data/product-1.jpg" alt=""/>

						<div class="recommend-hot"></div>
					</a>

					<p class="recommend-name">
						<a class="recommend-name-link" href="#">新信息拜奶奶典当行生肖戒指一套新信息拜奶奶典当行生肖戒指一套</a>
					</p>

					<div class="recommend-handle">
						<input class="recommend-buy" type="button" value="立即抢购"/>
                        <span class="recommend-count">
                            已有<span>21</span>人购买
                        </span>
					</div>
				</li>


			</ul>
		</div>
		<div class="content-main">
			<div class="sort">
				<ul class="sort-list">
					<li class="sort-item">
						<a class="sort-link" href="/tender/tenderList">综合推荐</a>

						<div class="sort-filter">
							<label class="sort-filter-current"><input class="sort-radio" name="filter" type="radio"/>全部</label>
							<label class="sort-filter-current"><input class="sort-radio" name="filter" type="radio"/>即将开始</label>
							<label class="sort-filter-current"><input class="sort-radio" name="filter" type="radio"/>在当中</label>
							<label class="sort-filter-current"><input class="sort-radio" name="filter" type="radio"/>已结束</label>
						</div>
					</li>
					<li class="sort-item">
						<a class="sort-link" href="javascript:void(0);">最新上线</a>

						<div class="sort-filter">
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>全部</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>即将开始</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>在当中</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>已结束</label>
						</div>
					</li>
					<li class="sort-item">
						<a class="sort-link" href="javascript:void(0);">金额最高</a>

						<div class="sort-filter">
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>全部</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>即将开始</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>在当中</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>已结束</label>
						</div>
					</li>
					<li class="sort-item">
						<a class="sort-link" href="javascript:void(0);">即将结束</a>

						<div class="sort-filter">
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>全部</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>即将开始</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>在当中</label>
							<label class="sort-filter-current"><input class="sort-radio" type="radio"/>已结束</label>
						</div>
					</li>
				</ul>

			</div>


			<!-- super:项目下没有时显示no-tender-->
			<div class="no-tender" style="display: block">
				<p class="no-tender-text">对不起,该项目下暂时没有内容,以下是为您推荐的项目:</p>
			</div>

			<ul class="tender-list">
				<c:forEach items="${tenderListResult}" var="tenderItem">
					<c:choose>
						<c:when test="${tenderItem.tender.tenderStatus==0}">
							<c:set value="tender-threshold" var="tenderStatus"/>
						</c:when>
						<c:when test="${tenderItem.tender.tenderStatus==10}">
							<c:set value="tender-being" var="tenderStatus"/>
						</c:when>
						<c:when test="${tenderItem.tender.tenderStatus==20||tenderItem.tender.tenderStatus==30||tenderItem.tender.tenderStatus==-10}">
							<c:set value="tender-finish" var="tenderStatus"/>
						</c:when>
					</c:choose>
					<li class="tender-item ${tenderStatus}">
						<div class="tender-img-box">
							<img class="tender-img" src="${tenderItem.tender.p2pFrontMedia.path}" alt="${tenderItem.tender.name}"/>

							<div class="tender-status"></div>
							<div class="tender-about">
								<div class="tender-about-item">
									<div class="tender-about-value"><span>${tenderItem.tender.productResidue}</span>件</div>
									<div class="tender-about-title">剩余产品</div>
								</div>
								<div class="tender-about-item">
									<div class="tender-about-value">¥<span><fmt:formatNumber value="${tenderItem.tender.tenderTotal}" pattern="0.00" type="number"/></span></div>
									<div class="tender-about-title">产品认购总额</div>
								</div>
								<div class="tender-about-item">
									<div class="tender-about-value"><span>68</span>人</div>
									<div class="tender-about-title">已购人数</div>
								</div>
							</div>
						</div>

						<p class="tender-name"><a class="tender-name-link" href="#">${tenderItem.tender.name}</a></p>

						<div class="tender-time">
							<span class="tender-time-title">倒计时:</span>
							<jsp:useBean id="nowDate" class="java.util.Date"/>
							<c:set value="${((tenderItem.tender.dueDate)/1000-(nowDate.time)/1000)/(1000 * 60 * 60 * 24)}" var="lastDay"/>
							<span class="tender-time-value"><span><fmt:formatNumber value="${lastDay}" pattern="0" type="number"/></span>天</span>
						</div>
						<div class="tender-add-favorite">
							<!-- 已加入收藏,则添加类tender-have-favorite-->
							<a class="tender-add-favorite-link tender-have-favorite" href="javascript:void(0);">收藏商品</a>
						</div>
					</li>
				</c:forEach>

			</ul>
			<jsp:include page="pagination.jsp"/>
		</div>
	</div>
</div>
<%--<jsp:include page="../core/footer-main.jsp"/>--%>

<script src="/resources/core/js/require.js" data-main="/resources/product-list/tender_list_1"></script>
</body>
</html>