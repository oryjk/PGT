<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/product-list/product-list.css"/>
	<link rel="stylesheet" href="/resources/product-list/list.css" >
	<script src="/resources/core/js/jquery.min.js" data-main="invest"></script>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->
<!--content begin-->
<div class="content">
	<div class="title">
		<div class="title-left">
			<img src="../core/images/icon/tenderlist1_03.png">
			<h2>项目列表</h2>
		</div>
	</div>
	<div class="main">
		<div  class="menu">
			<a  href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list1.png">
				<h4>全部</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list2.png">
				<h4>文玩杂项</h4>
			</a>
			<a  href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list3.png">
				<h4>翡翠玉石</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list4.png">
				<h4>琥珀蜜蜡</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list5.png">
				<h4>木制品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list6.png">
				<h4>钻石饰品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list7.png">
				<h4>精品钟表</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list8.png">
				<h4>科技产品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list" >
				<img src="../core/images/icon/list9.png">
				<h4>其他</h4>
			</a>
			<div class="list-end"></div>

		</div>
		<div class="box-all">

			<div class="all-list">
				<div class="filter">
					<div class="title1">当前位置：文玩杂项<span>查询结果共1000个相关项目</span></div>
					<ul class="title2">
						<li><input type="radio" name="sex" value="n" />全部</li>
						<li><input type="radio" name="sex" value="v" />即将开始</li>
						<li><input type="radio" name="sex" value="n" />进行中</li>
						<li><input type="radio" name="sex" value="v" />已结束</li>
					</ul>
					<div class="title3">
						<a href="#">综合推荐</a>
						<a href="#">最新上线</a>
						<a href="#">金额最多</a>
						<a href="#">即将结束</a>
					</div>
				</div>

				<div class="products">

					<c:forEach items="${tenderListResult}" var="tenderItem">
						<a href="/tender/${tenderItem.tender.tenderId}" class="item">
							<div class="item-img">
								<img src="${tenderItem.tender.p2pFrontMedia.path}" alt=""/>
							</div>
							<div class="item-name">
								${tenderItem.tender.name}
							</div>
							<div class="progress-bar">
								<div class="inner" style="width: 120px;" data-value="${tenderItem.tender.productResidue/tenderItem.tender.productQuantity}"></div>
							</div>
							<div class="info-box">
								<div class="surplus-count">
									<p class="info-value">${tenderItem.tender.productResidue}</p>
									<p class="info-title">剩余产品</p>
								</div>
								<div class="cost-sum">
									<p class="info-value"><span>¥</span>
										<span><fmt:formatNumber value="${tenderItem.tender.tenderTotal}" pattern="0.00" type="number" /></span>
									</p>
									<p class="info-title">产品认购总额</p>
								</div>
								<div class="surplus-time">
									<jsp:useBean id= "nowDate" class = "java.util.Date" />
									<c:set value="${((tenderItem.tender.dueDate)/1000-(nowDate.time)/1000)/(1000 * 60 * 60 * 24)}" var="lastDay"/>
									<p class="info-value"><span><fmt:formatNumber value="${lastDay}" pattern="0" type="number" /></span>天</p>
									<p class="info-title">截止时间</p>
								</div>
							</div>
							<div class="invest-status">
								<c:choose>
									<c:when test="${tenderItem.tender.tenderStatus==10}">
										在当中
									</c:when>
									<c:when test="${tenderItem.tender.tenderStatus==20}">
										已绝当
									</c:when>
									<c:when test="${tenderItem.tender.tenderStatus==30}">
										已赎当
									</c:when>
								</c:choose>
							</div>
							<a class="invest-join-favorite" href=""></a>
						</a>
					</c:forEach>
				</div>
				<div class="page-box">
					<ol>
						<li><a href="javascript:void(0);">1</a></li>
						<li>...</li>
						<li><a href="javascript:void(0);">3</a></li>
						<li><a href="javascript:void(0);">4</a></li>
						<li><a href="javascript:void(0);">5</a></li>
						<li>...</li>
						<li><a href="javascript:void(0);">7</a></li>
					</ol>
				</div>
			</div>

		</div>
	</div>
</div>
<!--content end-->

.<!-- recommend begin-->
<div class="recommend-box">
	<div class="recommend">
		<div class="recommend-head">
			<h3>猜你喜欢</h3>
			<a href="javascript:void(0);" class="recommend-change">换一批</a>
		</div>
		<div class="recommend-list">
			<ul>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>
					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>
						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
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
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>
					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>
						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
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
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>
					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>
						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
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
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>
					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>
						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
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

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/product-list/productList"></script>
</body>
</html>