<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="content-box">

		<div class="content">

			<!-- 面包屑-->
			<div class="bread-nav">
				<div class="row">
					<a href="#">主页</a> > <a href="#">钟表</a> > <a href="#">男表</a> > <a
						href="#">某某款</a>
				</div>
				<div id="contentSearch" class="content-search">
					<input class="content-search" type="text" placeholder="在该类下搜索" /> <a
						href="#"><i class="foundicon-search"></i></a>
				</div>
			</div>

			<!-- 条件筛选框-->
			<div class="filter-box">
				<div class="filter-classify">
					<h3 class="filter-name">分类</h3>
					<ul>
						<li><a href="#">千足金</a></li>
						<li><a class="filter-selection-focus" href="#">pt金</a></li>
						<li><a href="#">玛瑙</a></li>
						<li><a href="#">绿翠</a></li>
						<li><a href="#">男表</a></li>
					</ul>
				</div>
				<div class="filter-keyword">
					<h3 class="filter-name">关键词</h3>
					<ul>
						<li><a href="#">时尚</a></li>
						<li><a href="#">定制</a></li>
						<li><a href="#">超低价</a></li>
						<li><a href="#">独一件</a></li>
						<li><a href="#">绝当</a></li>
						<li><a href="#">高端</a></li>
					</ul>
				</div>
				<div class="filter-price">
					<h3 class="filter-name">价格区间</h3>
					<ul>
						<li><a href="#">0-500</a></li>
						<li><a href="#">500-1000</a></li>
						<li><a href="#">1000-2000</a></li>
						<li><a href="#">2000-5000</a></li>
						<li><a href="#">5000以上</a></li>
						<li class="filter-foruser">
							<form action="#">
								<input class="filter-from" type="text" /> <span> - </span> <input
									class="filter-to" type="text" /> <input type="submit"
									value="确定" />
							</form>
						</li>
					</ul>
				</div>
			</div>

			<ul class="aside">
				<jsp:include page="../shopping-cart/vertical-recommend-bar.jsp" />
			</ul>

			<!-- 详细内容列表-->
			<div class="list-box">
				<div class="orderby-box">
					<ul>
						<li><span>排序方式</span></li>
						<li><a class="orderby-selection-focus" href="#">按价格</a></li>
						<li><a href="#">按销量</a></li>
						<li><a href="#">按评价</a></li>
						<li><a href="#">综合排序</a></li>
					</ul>
				</div>
				<div class="product-list">

					<c:forEach items="${productList}" var="product">

						<div class="list-product">

							<div class="inner">
								<a class="list-img-box" href="#"><img
									src="${pageContext.request.contextPath}/resources${product['source']['frontMedia']['path']}"
									alt="" /></a>
								<div class="list-price-box">
									<span>¥</span><span>${product.salePrice}</span>
								</div>
								<p class="product-link">
									<a href="#">${product.name}</a>
								</p>
								<p>
									已有<span></span>评价
								</p>
								<c:if test="${product.stock<1}">
								<div class="out-of-stock">
								</div>
								</c:if>

								<div class="product-message">添加成功</div>

								<div class="product-handle">
									<a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i>取消</a>
									<a class="addEnjoy" href="#" data-value="${product.productId}"><i class="foundicon-heart"></i>收藏</a>
									<a class="addCart" href="#" data-value="${product.productId}" data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>"><i
										class="foundicon-cart" ></i>购物车</a>
								</div>
							</div>
						</div>

					</c:forEach>

				</div>
				<div class="page-box">
					<ul>
						<li><a href="#">上页</a></li>
						<li class="page-list">
							<ol>
								<li><a class="page-focus" href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#">6</a></li>
							</ol>
						</li>
						<li><a href="#">下页</a></li>
						<li class="page-count">共<span>6</span>页
						</li>
						<li class="page-which">跳转到第<input type="text" />页
						</li>
						<li><input type="button" value="确认" /></li>
					</ul>
				</div>
			</div>

		</div>
	</div><div class="content-box">

		<div class="content">

			<!-- 面包屑-->
			<div class="bread-nav">
				<div class="row">
					<a href="#">主页</a> > <a href="#">钟表</a> > <a href="#">男表</a> > <a
						href="#">某某款</a>
				</div>
				<div id="contentSearch" class="content-search">
					<input class="content-search" type="text" placeholder="在该类下搜索" /> <a
						href="#"><i class="foundicon-search"></i></a>
				</div>
			</div>

			<!-- 条件筛选框-->
			<div class="filter-box">
				<div class="filter-classify">
					<h3 class="filter-name">分类</h3>
					<ul>
						<li><a href="#">千足金</a></li>
						<li><a class="filter-selection-focus" href="#">pt金</a></li>
						<li><a href="#">玛瑙</a></li>
						<li><a href="#">绿翠</a></li>
						<li><a href="#">男表</a></li>
					</ul>
				</div>
				<div class="filter-keyword">
					<h3 class="filter-name">关键词</h3>
					<ul>
						<li><a href="#">时尚</a></li>
						<li><a href="#">定制</a></li>
						<li><a href="#">超低价</a></li>
						<li><a href="#">独一件</a></li>
						<li><a href="#">绝当</a></li>
						<li><a href="#">高端</a></li>
					</ul>
				</div>
				<div class="filter-price">
					<h3 class="filter-name">价格区间</h3>
					<ul>
						<li><a href="#">0-500</a></li>
						<li><a href="#">500-1000</a></li>
						<li><a href="#">1000-2000</a></li>
						<li><a href="#">2000-5000</a></li>
						<li><a href="#">5000以上</a></li>
						<li class="filter-foruser">
							<form action="#">
								<input class="filter-from" type="text" /> <span> - </span> <input
									class="filter-to" type="text" /> <input type="submit"
									value="确定" />
							</form>
						</li>
					</ul>
				</div>
			</div>

			<!-- 侧边栏-->
			<ul class="aside">
				<h2>精品推荐</h2>

				<c:forEach items="${category.products}" var="product">

					<li class="aside-product"><a class="aside-product-imgbox"
						href="#"><img
							src="${pageContext.request.contextPath}/resources${product.frontMedia.path}"
							alt="" /></a>
						<p>
							<span class="aside-product-priceh">惊喜价</span> <span
								class="aside-product-price"> ¥ <span>${product.salePrice}</span>
							</span>
						</p>
						<p>
							<a class="aside-product-name" href="#">${product.name}</a>
						</p>

						<div class="product-message">添加成功</div>

						<div class="product-handle">
							<a class="addEnjoy" href="#"><i class="foundicon-heart"></i></a>
							<a class="addCart" href="#"><i class="foundicon-cart"></i></a>
						</div>
						<div class="out-of-stock"></div>

					</li>
				</c:forEach>
			</ul>

			<!-- 详细内容列表-->
			<div class="list-box">
				<div class="orderby-box">
					<ul>
						<li><span>排序方式</span></li>
						<li><a class="orderby-selection-focus" href="#">按价格</a></li>
						<li><a href="#">按销量</a></li>
						<li><a href="#">按评价</a></li>
						<li><a href="#">综合排序</a></li>
					</ul>
				</div>
				<div class="product-list">

					<c:forEach items="${productList}" var="product">

						<div class="list-product">

							<div class="inner">
								<a class="list-img-box" href="#"><img
									src="${pageContext.request.contextPath}/resources${product.frontMedia.path}"
									alt="" /></a>
								<div class="list-price-box">
									<span>¥</span><span>${product.salePrice}</span>
								</div>
								<p class="product-link">
									<a href="#">${product.name}</a>
								</p>
								<p>
									已有<span></span>评价
								</p>
								<div class="out-of-stock"></div>

								<div class="product-message">添加成功</div>

								<div class="product-handle">
									<a class="disLike" href="#" data-value="${product.productId}"><i class="foundicon-heart"></i>取消</a>
									<a class="addEnjoy" href="#" data-value="${product.productId}"><i class="foundicon-heart"></i>收藏</a>
									<a class="addCart" href="#" data-value="${product.productId}" data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>"><i
										class="foundicon-cart" ></i>购物车</a>
								</div>
							</div>
						</div>

					</c:forEach>

				</div>
				<div class="page-box">
					<ul>
						<li><a href="#">上页</a></li>
						<li class="page-list">
							<ol>
								<li><a class="page-focus" href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#">6</a></li>
							</ol>
						</li>
						<li><a href="#">下页</a></li>
						<li class="page-count">共<span>6</span>页
						</li>
						<li class="page-which">跳转到第<input type="text" />页
						</li>
						<li><input type="button" value="确认" /></li>
					</ul>
				</div>
			</div>

		</div>
	</div>