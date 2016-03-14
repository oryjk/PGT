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
			<jsp:include page="categoryMenu.jsp"/>
		</div>
		<div class="box-all">

			<div class="all-list">
				<div class="filter">
					<div class="breadcrumb">
						当前位置：
						<c:forEach items="${breadCrumbs}" var="bread">
							${bread.breadName} >
						</c:forEach>
						<span>查询结果共1000个相关项目</span>
					</div>
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

						<li id="sortTime" class="sort-tab" v-bind:class="{'current-tab':queryRequest.sort==4}">
							<a class="sort-view" href="#">周期最长</a>

							<ul>
								<li class="sort-tab-item hide"><a href="#">周期最长</a></li>
								<li class="sort-tab-item"><a href="#">周期最短</a></li>
							</ul>
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
<jsp:include page="../core/baidu.jsp" />
</body>
</html>
