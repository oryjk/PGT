<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
	<link rel="stylesheet" href="/resources/product-list/list.css">
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
		<div class="menu">
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list1.png">
				<h4>全部</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list2.png">
				<h4>文玩杂项</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list3.png">
				<h4>翡翠玉石</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list4.png">
				<h4>琥珀蜜蜡</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list5.png">
				<h4>木制品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list6.png">
				<h4>钻石饰品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list7.png">
				<h4>精品钟表</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list8.png">
				<h4>科技产品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
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
						<li><input type="radio" name="sex" value="n"/>全部</li>
						<li><input type="radio" name="sex" value="v"/>即将开始</li>
						<li><input type="radio" name="sex" value="n"/>进行中</li>
						<li><input type="radio" name="sex" value="v"/>已结束</li>
					</ul>
					<div class="title3">
						<a href="#">综合推荐</a>
						<a href="#">最新上线</a>
						<a href="#">金额最多</a>
						<a href="#">即将结束</a>
					</div>
				</div>

				<jsp:include page="tenders.jsp"/>
				<jsp:include page="pagination.jsp"/>
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