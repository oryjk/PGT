<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/product-list/product-list.css"/>
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
	<div class="main" id="mainContent">
		<div class="menu">
			rootCategoryList${rootCategoryList}
			<ul class="menu">
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list1.png">
						<h4>全部</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list2.png">
						<h4>文玩杂项</h4>
					</a>
					<ul class="menu-2">
						<li class="menu-level-2"><a href="#">文玩</a></li>
						<li class="menu-level-2"><a href="#">墨宝</a></li>
					</ul>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list3.png">
						<h4>翡翠玉石</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list4.png">
						<h4>琥珀蜜蜡</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list5.png">
						<h4>木制品</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list6.png">
						<h4>钻石饰品</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list7.png">
						<h4>精品钟表</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list8.png">
						<h4>科技产品</h4>
					</a>
				</li>
				<li class="menu-level-1">
					<a href="javascript:" path="list-all.html" class="list">
						<img src="../core/images/icon/list9.png">
						<h4>其他</h4>
					</a>
				</li>
				<div class="list-end"></div>
			</ul>
		</div>
		<div class="box-all">

			<div class="all-list">
				<div class="filter">
					<div class="breadcrumb">当前位置：文玩杂项<span>查询结果共1000个相关项目</span></div>
					<ul class="status-filter" v-on:click="tenderFilterAction">
						<li><input type="radio" name="sex" value="n" data-value="1"/>全部</li>
						<li><input type="radio" name="sex" value="v" data-value="2"/>即将开始</li>
						<li><input type="radio" name="sex" value="n" data-value="3"/>进行中</li>
						<li><input type="radio" name="sex" value="v" data-value="4"/>已结束</li>
					</ul>
					<ul class="sort" v-on:click="sortBy">
						<li class="sort-tab" v-bind:class="{'current-tab':queryRequest.sort==''}">
							<a href="#">综合推荐</a>
						</li>
						<li class="sort-tab" v-bind:class="{'current-tab':queryRequest.sort==1}">
							<a href="#" data-value="1">最新上线</a>
						</li>
						<li id="sortMoney" class="sort-tab" v-bind:class="{'current-tab':queryRequest.sort==2}">
							<a class="sort-view" href="#" data-value="2">金额最多</a>
							<ul>
								<li class="sort-tab-item hide"><a href="#" data-value="2">金额最多</a></li>
								<li class="sort-tab-item"><a href="#" data-value="2">金额最少</a></li>
							</ul>
						</li>
						<li class="sort-tab" v-bind:class="{'current-tab':queryRequest.sort==4}">
							<a class="sort-tab" href="#" data-value="4">周期最短</a>
						</li>
						<li class="sort-tab" v-bind:class="{'current-tab':queryRequest.sort==5}">
							<a class="sort-tab" href="#" data-value="5">即将结束</a>
						</li>
					</ul>
				</div>
				<div class="products">
					<jsp:include page="tenders.jsp"/>
				</div>
				<div class="page-box">
					<jsp:include page="pagination.jsp"/>
				</div>
			</div>

		</div>
	</div>
</div>
<!--content end-->

<jsp:include page="../core/recommend-horizontal.jsp"/>

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/product-list/productList"></script>
</body>
</html>
