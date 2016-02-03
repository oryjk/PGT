<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>点金子绝当淘商城</title>
<link rel="stylesheet"
	href="<spring:url value="${juedangpinStaticPath}/searchPage/searchPage.css"/>" />
	<link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
<script
	src="<spring:url value="${juedangpinStaticPath}/core/js/pageView.js"/>"></script>
</head>
<body>
	<!--主头部-->
	<div class="header" id="header">
		<jsp:include page="../core/header-main.jsp" />
	</div>

	<!--正文-->
	<div id="content" class="content-box">

		<div class="content">

			<!-- 面包屑-->
			<jsp:include page="../core/breadCrumb.jsp" />

			<div class="have-result" style="display: ${result[0]}">
				<!-- 条件筛选框-->
				<div class="filter-box">
					<div class="filter-classify">
						<h3 class="filter-name">分类</h3>
						<ul>



							<c:forEach items="${parentCategoryList}" var="category">
							<li>


                                 <c:if test="${breadCrumb[0].breadName eq category.source.name}">

									 <a class="filter-selection-focus" href="${pageContext.request.contextPath}/essearch?parentCategoryId=${category.source.id}">${category.source.name}</a>
									</c:if>

									<c:if test="${breadCrumb[0].breadName != category.source.name}">
									<a  href="${pageContext.request.contextPath}/essearch?parentCategoryId=${category.source.id}">${category.source.name}</a>
									</c:if>

                                   </li>
							</c:forEach>



						</ul>
					</div>



					<div class="filter-keyword">
						<h3 class="filter-name">关键词</h3>
						<ul>
						
						<c:forEach items="${categoryArray}" var="catgory">   
							<li><a
								href="${pageContext.request.contextPath}/essearch?rootCategoryId=${category.source.id}">${category.source.name}</a></li>
						</c:forEach>
						</ul>
					</div>   
					<div class="filter-price">
						<h3 class="filter-name">价格区间</h3>
						<ul>


							<li><a <c:if test="${priceStart=='0'&&priceEnd=='500'}">class="filter-selection-focus"</c:if>
								href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>&priceStart=0&priceEnd=500">0-500</a>
							</li>

							<li><a <c:if test="${priceStart=='500'&&priceEnd=='1000'}">class="filter-selection-focus"</c:if>
								href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>&priceStart=500&priceEnd=1000">500-1000</a>
							</li>

							<li><a <c:if test="${priceStart=='1000'&&priceEnd=='2000'}">class="filter-selection-focus"</c:if>
								href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>&priceStart=1000&priceEnd=2000">1000-2000</a>
							</li>

							<li><a <c:if test="${priceStart=='2000'&&priceEnd=='5000'}">class="filter-selection-focus"</c:if>
								href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>&priceStart=2000&priceEnd=5000">2000-5000</a>
							</li>

							<li><a <c:if test="${priceStart=='5000'&&priceEnd=='100000000'}">class="filter-selection-focus"</c:if>
								href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>&priceStart=5000">5000以上</a>
							</li>


							<li class="filter-foruser">
								<form action="${pageContext.request.contextPath}/essearch"
									method="get">
									<input class="filter-from" type="text" name="priceStart" /> <input
										type="hidden" name="term" value="${term}"> <input
										type="hidden" name="rootCategoryId" value="${rootCategory.id}">
									<input type="hidden" name="parentCategoryId"
										value="${parentCategory.id}"> <span> - </span> <input
										class="filter-to" type="text" name="priceEnd" /> <input
										type="submit" value="确定" />
								</form>
							</li>
						</ul>
					</div>
				</div>

				<!-- 侧边栏-->
				<ul class="aside">
					<jsp:include page="../shopping-cart/vertical-recommend-bar.jsp" />
				</ul>

				<!-- 详细内容列表-->
				<div id="main" class="list-box">

					<div class="orderby-box">
						<ul>
							<li><span>排序方式</span></li>
							<li><a href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>

<c:if test='${not empty priceStart}'>&priceStart=${priceStart}</c:if><c:if test='${not empty priceEnd}'>&priceEnd=${priceEnd}</c:if>&sortKey=creationDate&key=true<c:if test='${empty sortOrder}'>&sortOrder=desc</c:if><c:if test='${not empty sortOrder}'>&sortOrder=${sortOrder}</c:if>">上架时间</a>

								<c:if test="${sortKey eq 'creationDate'}">

									<c:if test="${not empty sortOrder}">

										<c:if test="${! empty page}">

											<c:if test="${sortOrder eq 'desc'}">
												<a class="orderby-choose"> <i class="foundicon-up-arrow"></i></a>
											</c:if>

											<c:if test="${sortOrder eq 'asc'}">
												<a class="orderby-choose"><i class="foundicon-down-arrow"></i></a>
											</c:if>

										</c:if>

										<c:if test="${ empty page}">

											<c:if test="${sortOrder eq 'asc'}">
												<a class="orderby-choose"> <i class="foundicon-up-arrow"></i></a>
											</c:if>

											<c:if test="${sortOrder eq 'desc'}">
												<a class="orderby-choose"><i class="foundicon-down-arrow"></i></a>
											</c:if>

										</c:if>
									</c:if>

								</c:if>


							</li>

							<li><a href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>
<c:if test='${not empty priceStart}'>&priceStart=${priceStart}</c:if><c:if test='${not empty priceEnd}'>&priceEnd=${priceEnd}</c:if>&sortKey=salePrice&key=true<c:if test='${ empty sortOrder}'>&sortOrder=desc</c:if><c:if test='${not empty sortOrder}'>&sortOrder=${sortOrder}</c:if>">价格</a>

								<c:if test="${sortKey eq 'salePrice'}">

									<c:if test="${not empty sortOrder}">

										<c:if test="${! empty page}">

										<c:if test="${sortOrder eq 'desc'}">
										<a class="orderby-choose"> <i class="foundicon-up-arrow"></i></a>
									</c:if>

									<c:if test="${sortOrder eq 'asc'}">
										<a class="orderby-choose"><i class="foundicon-down-arrow"></i></a>
									</c:if>

											</c:if>

										<c:if test="${ empty page}">

											<c:if test="${sortOrder eq 'asc'}">
												<a class="orderby-choose"> <i class="foundicon-up-arrow"></i></a>
											</c:if>

											<c:if test="${sortOrder eq 'desc'}">
												<a class="orderby-choose"><i class="foundicon-down-arrow"></i></a>
											</c:if>

											</c:if>
								</c:if>
								</c:if>


							</li>

							<li><a href="${pageContext.request.contextPath}/essearch?term=${term}<c:if test='${not empty rootCategory}'>&rootCategoryId=${rootCategory.id}</c:if><c:if test='${not empty parentCategory}'>&parentCategoryId=${parentCategory.id}</c:if>
<c:if test='${not empty priceStart}'>&priceStart=${priceStart}</c:if><c:if test='${not empty priceEnd}'>&priceEnd=${priceEnd}</c:if>&sortKey=productId">综合排序</a>

							</li>

						</ul>
					</div>


					<div class="product-list">
						<c:forEach items="${searchHists}" var="searchHit">

							<div class="list-product">

								<div class="inner">
									<a class="list-img-box"
										href="${pageContext.request.contextPath}/product/${searchHit.source['productId']}"><img
										src="${pageContext.request.contextPath}/resources/${searchHit.source['frontMedia']['path']}"
										alt="${searchHit.source['name']}" /></a>

									<c:if test="${searchHit.source['stock']<1}">

										<div class="out-of-stock"></div>

									</c:if>

									<div class="product-message">添加成功</div>

									<div class="list-price-box">
										¥ <span><fmt:formatNumber value="${searchHit.source['salePrice']}" pattern="0.00"
																  type="number"/></span>
									</div>

									<p>
										<a href="${pageContext.request.contextPath}/product/${searchHit.source['productId']}">${searchHit.source['name']}</a>
									</p>

									<p>
										已有<span></span>评价
									</p>

									<div class="product-handle">
										<a class="addEnjoy"
											data-value="${searchHit.source['productId']}" href="#"><i
											class="foundicon-heart" data-value="${product.productId}"></i>收藏</a>
										<a class="addCart" href="#"
											data-value="${searchHit.source['productId']}"
											data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>"><i
											class="foundicon-cart"></i>购物车</a>
									</div>
								</div>
							</div>

						</c:forEach>


					</div>

					<input id="capacity_comm" type="hidden"
						value="${commPaginationBean.capacity}"> <input
						id="totalPage" type="hidden"
						value="${commPaginationBean.totalPage}">

					<form id="pageViewFrom"
						action="${pageContext.request.contextPath}/essearch" method="get">

						<c:if test="${!empty term}">
							<input type="hidden" name="term" value="${term}">
						</c:if>

						<c:if test="${!empty rootCategory}">
							<input type="hidden" name="rootCategoryId"
								value="${rootCategory.id}">
						</c:if>

						<c:if test="${!empty parentCategory}">
							<input type="hidden" name="parentCategoryId"
								value="${parentCategory.id}">
						</c:if>
						
						
						<c:if test="${!empty priceStart}">
							<input type="hidden" name="priceStart"
								value="${priceStart}">
						</c:if>
						
						<c:if test="${!empty priceEnd}">
							<input type="hidden" name="priceEnd"
								value="${priceEnd}">
						</c:if>



						<c:if test="${! empty sortKey}">
						<input type="hidden" name="sortKey"
								value="${sortKey}">
						</c:if>

						<c:if test="${empty page}">

							<c:if test="${! empty sortOrder}">
								<input type="hidden" name="sortOrder"
									   value="${sortOrder}">
							</c:if>

						</c:if>

						<c:if test="${! empty page}">

							<c:if test="${! empty sortOrder}">

								<c:if test="${sortOrder eq 'desc'}">
								<input type="hidden" name="sortOrder"
									   value="asc">
							    </c:if>

								<c:if test="${sortOrder eq 'asc'}">
									<input type="hidden" name="sortOrder"
										   value="desc">
								</c:if>

								</c:if>

						</c:if>


						<jsp:include page="../core/pageView.jsp" />
					</form>

				</div>
			</div>

			<!-- 搜索无结果-->
			<div class="no-result" style="display:${result[1]}">
			<p class="row">
			抱歉,没有找到 "<span><c:out value="${message}"/></span>" 的搜索结果
			</p>
			</div>

			<jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp">
				<jsp:param name="excludeRecommend" value="1" />
			</jsp:include>
		</div>
	</div>
	<%--
			<jsp:include page="../core/helpSide.jsp"/>
		--%>
				<jsp:include page="../core/footer-main.jsp" >
					<jsp:param name="useside" value="true" />
				</jsp:include>

</body>

<script
	src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
	data-main="<spring:url value="${juedangpinStaticPath}/searchPage/searchPage.js"/>"></script>
</html>