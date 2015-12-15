<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

	<div id="content" class="content-box">
		<div class="content">
			<div id="bannerBox" class="banner-box">
				<div id="banner" class="banner">
					<c:forEach items="${banner.images}" var="image" varStatus="status">

						<c:if test="${status.index=='0'}">
							<a href="#" data-banner="${stauts.index}"
								style="background: url('${pageContext.request.contextPath}/resources${image.path}') center center no-repeat ${image.color}; display: block"></a>
						</c:if>

						<c:if test="${status.index!='0'}">
							<a href="#" data-banner="${status.index}"
								style="background: url('${pageContext.request.contextPath}/resources${image.path}') center center no-repeat ${image.color}"></a>
						</c:if>

					</c:forEach>

				</div>

				<ol id="bannerNav" class="banner-nav">

					<c:forEach items="${banner.images}" var="image" varStatus="status">

						<c:if test="${status.index=='0'}">
							<li class="banner-nav-now">${status.index}</li>
						</c:if>
						<c:if test="${status.index!='0'}">
							<li>${status.index}</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
			<div class="classify-box">
				<h2 class="classify-head">
					最新热卖 <small>全网性价比第一!</small>
				</h2>
				<div class="classify">

					<c:forEach items="${hotSearchList}" var="hotSearch" varStatus="status">
					
						<a class="classify-${status.index}" href="${pageContext.request.contextPath}/essearch?term=${hotSearch.term}"> <img
							src="${pageContext.request.contextPath}/resources${hotSearch.frontMedia.path}"
							alt="${hotSearch.term}"/>
							<div class="light"></div>

						</a>

					</c:forEach>

				</div>
			</div>

			<c:forEach items="${hotProducts}" var="homeCategory"
				varStatus="status">

				<c:if test="${status.index % 2 !=0}">

					<div class="products-list-cardinal">

						<ul class="products-nav">
									
									<c:forEach items="${homeCategory['source']['category']['children']}"
								var="subCategory">
								<li><a href="#">${subCategory['name']}</a></li>
							</c:forEach>
									
						</ul>

						<h2 style="border-bottom: 2px solid ${homeCategory['source']['category']['color']}">
							${homeCategory['source']['category']['name']} <small style="color: ${homeCategory['source']['category']['color']}">品真 质纯 艺臻</small>
						</h2>
						<div class="products">

							<a class="product-main" href="#"> <img
								src="${pageContext.request.contextPath}/resources${homeCategory['source']['category']['frontMedia']['path']}" alt="${homeCategory['source']['category']['name']}" />
								<div class="light"></div>
							</a>

							<c:forEach items="${homeCategory['source']['hotProduct']}" var="product"
								varStatus="st">

								<c:if test="${st.index<6}">

									<a href="product/${product['productId']}"> <img
										src="${pageContext.request.contextPath}/resources${product['medias'][0]['path']}"
										alt="${product['name']}" />

										<p class="product-name">${product['name']}</p>

										<p class="product-cost">
											￥<span>${product['salePrice']}</span>
										</p>

                                    <c:if test="${product['stock']<1}">
									<div class="out-of-stock">
									
									</div>
									</c:if>

									<div class="product-message">添加成功</div>

										<div class="product-hover-area" style="background: ${homeCategory['source']['category']['color']}">
											<input type="button" value="查看详情" data-btn="showInfo"/> <input
												type="button" value="添加收藏" data-value="${product['productId']}" data-url="<spring:url value="/"/>" data-btn="addEnjoy" /> <input
												type="button" value="加入购物车" data-value="${product['productId']}" data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>" data-btn="addCart" />
										</div>
									</a>
								</c:if>
                            
							</c:forEach>
							</div>
							</div>
							
				</c:if>
				
					<c:if test="${status.index % 2 == 0}">


					<div class="products-list-even">
						<ul class="products-nav">
								<c:forEach items="${homeCategory['source']['category']['children']}"
								var="subCategory">
								<li><a href="#">${subCategory['name']}</a></li>
							</c:forEach>
							
							<li><a
								href="${urlConfiguration.plpPage}/${homeCategory['source']['category']['id']}">
									更多></a></li>
						</ul>                                       
						<h2 style="border-bottom: 2px solid ${homeCategory['source']['category']['color']}">
							${homeCategory['source']['category']['name']} <small style="color: ${homeCategory['source']['category']['color']}">品真 质纯 艺臻</small>
						</h2>
						<div class="products">

							<a class="product-main" href="#"> <img
								src="${pageContext.request.contextPath}/resources${homeCategory['source']['category']['frontMedia']['path']}" alt="${homeCategory['source']['category']['name']}" />
								<div class="light"></div>
							</a>

							<c:forEach items="${homeCategory['source']['hotProduct']}" var="product"
								varStatus="st">

								<c:if test="${st.index<6}">
									<a href="product/${product['productId']}"> <img
										src="${pageContext.request.contextPath}/resources${product['frontMedia']['path']}"
										alt="${product['name']}" />

										<p class="product-name">${product['name']}</p>

										<p class="product-cost">
											￥<span>${product['salePrice']}</span>
										</p>

									  <c:if test="${product['stock']<1}">
									<div class="out-of-stock">
									
									</div>
									</c:if>

									<div class="product-message">添加成功</div>

										<div class="product-hover-area" style="background: ${homeCategory['source']['category']['color']}">
											<input type="button" value="查看详情" data-btn="showInfo" /> <input
												type="button" value="添加收藏" data-value="${product['productId']}" data-btn="addEnjoy" /> <input
												type="button" value="加入购物车" data-value="${product['productId']}" data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>" data-btn="addCart" />
										</div>
									</a>
								</c:if>       
							</c:forEach>
                             </div>
					</div>
				</c:if>
				
				
</c:forEach>
		</div>
	</div>
	</div>