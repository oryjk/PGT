<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangxiaodong
  Date: 16-2-23
  Time: 下午12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div class="header">
	<a class="top-banner" href="#" style="background: #9e150e url('/resources/core/images/data/top-banner.jpg') no-repeat center center"></a>

	<div class="top">
		<div class="top-content">
			<div class="top-welcome">
				<span class="top-welcome-text">欢迎光临点金子绝当淘!</span>
				<span class="top-welcome-phone-title">客服电话:</span>
				<span class="top-welcome-phone-value main">028-88888888</span>
			</div>
			<div class="top-user">
				<c:choose>
					<c:when test="${currentUser==null}">
						<div class="top-before-login">
							<a class="top-user-login hover-main" href="/user/login">登录</a>
							<a class="top-user-register hover-main" href="/user/register">注册</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="top-after-login">
							<span>欢迎您:</span>
							<a href="/myAccount/orderHistory?status=0">${currentUser.username}</a>
							<a class="top-user-logout hover-main" href="/user/logout">退出</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="head">
		<div class="head-content">
			<h1 class="logo">
				<a class="logo-link" href="/"></a>
			</h1>
			<a href="#" class="hot-left" style="background: url('/resources/core/images/data/hot-left.gif') no-repeat center center"></a>
			keyword:${param.keyword}
			<form class="search" action="/tender/tenderList" method="get">
				<div class="search-content">
					<a class="search-input-submit" href="javascript:($('.search').submit());"></a>
					<input class="search-input-text" type="text" name="keyword" value="${param.keyword}" placeholder="请输入要搜索的关键字"/>
				</div>
			</form>
			<a href="#" class="hot-right" style="background: url('/resources/core/images/data/hot-right.gif') no-repeat center center"></a>
		</div>
	</div>
	<c:if test="${!empty rootHomeCategories}">
		<div class="nav">
			<div class="nav-content">
				<div class="menu">
					<h2 class="menu-title bg-main">开启购物新体验</h2>
					<ul class="menu-list">
						<c:forEach items="${rootHomeCategories}" var="rootCategory">
							<li class="menu-item" style="background:url('') no-repeat center center">
								<h3 class="menu-item-title">${rootCategory.name}</h3>

								<div class="menu-item-sub-box">
									<c:forEach items="${rootCategory.esChildren}" var="category">
										<a class="menu-item-sub hover-main" href="/tender/tenderList?cid=${category.id}&ctype=TENDER_HIERARCHY">${category.name}</a>
									</c:forEach>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<ul class="nav-list">
					<li id="navHover" class="nav-item-hover" style="width: 96px; height: 54px; top: 0; left:0;"></li>
					<li class="nav-item"><a class="nav-item-link" href="/">首页</a></li>
					<li class="nav-item"><a class="nav-item-link" href="/tender/tenderList">在当大厅</a></li>
					<li class="nav-item"><a class="nav-item-link" href="/pawnPersonInfo/createPawnPersonInfo">在线典当</a></li>
					<li class="nav-item"><a class="nav-item-link" href="http://mp.dianjinzi.com">绝当品商城</a></li>
					<li class="nav-item"><a class="nav-item-link" href="">帮助中心</a></li>
				</ul>
			</div>
		</div>
	</c:if>
</div>

