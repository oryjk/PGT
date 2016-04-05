<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/product-list/tender_list_1.css"/>
	<script src="/resources/core/js/jquery.min.js" data-main="invest"></script>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->
<!--content begin-->
<!-- super:项目下没有时显示no-tender-->

<div class="content">
	<div class="filter">
		<div class="filter-title">
			<span class="filter-name">${param.keyword}</span>
			<span class="filter-title-headline">商品筛选</span>
			<span class="filter-count">共计<span>${fn:length(tenderListResult)}</span>个项目</span>
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
										   href="/tender/tenderList?sort=${param.sort}&page=${param.page}&keyword=${param.keyword}&tenderFilter=${param.tenderFilter}&cid=${sub.id}&ctype=TENDER_HIERARCHY&">${sub.name}</a>
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

			<div class="content-main">
				<div class="no-tender" style="display: block">
					<p class="no-tender-text">对不起,该项目下暂时没有内容,以下是为您推荐的项目:</p>
				</div>
			</div>


		</div>
	</div>
</div>
<!--content end-->


<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/product-list/tender_list_1"></script>
<jsp:include page="../core/baidu.jsp"/>
</body>
</html>
