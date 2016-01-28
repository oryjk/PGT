<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../core/head.jspf">
    <jsp:param name="cssPath" value="/index/index.css" />
</jsp:include>
<body>
<!--头部-->
<div class="header" id="header">
    <jsp:include page="../core/header-main.jsp"/>

</div>

<!--正文-->
<div id="content" class="content-box">
    <div id="bannerBox" class="banner-box">
        <div id="banner" class="banner">
            <c:forEach items="${banner.images}" var="image" varStatus="status">
                <c:if test="${status.index=='0'}">
                    <a href="${pageContext.request.contextPath}${image.url}" data-banner="${status.index}"
                       style="background: url('${pageContext.request.contextPath}${image.path}') center center no-repeat ${image.color}; display: block"></a>
                </c:if>

                <c:if test="${status.index!='0'}">
                    <a href="${pageContext.request.contextPath}${image.url}" data-banner="${status.index}"
                       style="background: url('${pageContext.request.contextPath}${image.path}') center center no-repeat  ${image.color}"></a>
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
    <div class="content">
        <div class="classify-box">
            <h2 class="classify-head">
                最新热卖
                <small>全网性价比第一!</small>
            </h2>
            <div class="classify">
                <c:forEach items="${hotSearchList}" var="hotSearch" varStatus="status">
                    <c:if test="${status.index<4}">
                        <a  data-value="${pageContext.request.contextPath}/homeSearch?term=${hotSearch.term}" class="box1-1 each-classify"
                            style="background-image: url('${pageContext.request.contextPath}/resources${hotSearch.frontMedia.path}');"></a>
                    </c:if>
                </c:forEach>
            </div>
        </div>


        <c:forEach items="${hotProducts}" var="homeCategory"
                   varStatus="status">

            <c:if test="${status.index % 2 ==0}">

                <div class="products-list-cardinal">

                    <ul class="products-nav">

                        <c:forEach items="${homeCategory['source']['category']['children']}"
                                   var="subCategory">
                            <li><a href="<spring:url value="essearch?parentCategoryId=${subCategory.id}"/>">${subCategory['name']}</a></li>
                        </c:forEach>
                        <li><a href="<spring:url value="essearch?parentCategoryId=${homeCategory['source']['category']['id']}"/>">
                            更多></a></li>
                    </ul>

                    <h2 style="border-bottom: 2px solid ${homeCategory['source']['category']['color']}">
                            ${homeCategory['source']['category']['name']} <small style="color: ${homeCategory['source']['category']['color']}">${homeCategory['source']['category']['description']}</small>
                    </h2>
                    <div class="products">

                        <a class="product-main" href="${pageContext.request.contextPath}/essearch?rootCategoryId=${homeCategory['source']['category']['id']}">
                            <img
                                    src="${pageContext.request.contextPath}/resources${homeCategory['source']['category']['frontMedia']['path']}" alt="${homeCategory['source']['category']['name']}" />
                            <div class="light"></div>
                        </a>

                        <c:forEach items="${homeCategory['source']['hotProduct']}" var="product"
                                   varStatus="st">

                            <c:if test="${st.index<6}">

                                <a href="product/${product['productId']}">
                                    <img
                                            src="${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}"
                                            alt="${product['name']}" />

                                    <p class="product-name">${product['name']}</p>

                                    <p class="product-cost">
                                        ￥<span><fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number"/></span>
                                    </p>

                                    <c:if test="${product['stock']<1}">

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

            <c:if test="${status.index % 2 != 0}">


                <div class="products-list-even">
                    <ul class="products-nav">

                        <c:forEach items="${homeCategory['source']['category']['children']}"
                                   var="subCategory">
                            <li><a href="<spring:url value="essearch?parentCategoryId=${subCategory.id}"/>">${subCategory['name']}</a></li>
                        </c:forEach>
                        <li><a href="<spring:url value="essearch?parentCategoryId=${homeCategory['source']['category']['id']}"/>">
                            更多></a></li>
                    </ul>
                    <h2 style="border-bottom: 2px solid ${homeCategory['source']['category']['color']}">
                            ${homeCategory['source']['category']['name']} <small style="color: ${homeCategory['source']['category']['color']}">${homeCategory['source']['category']['description']}</small>
                    </h2>
                    <div class="products">

                        <a class="product-main" href="${pageContext.request.contextPath}/essearch?rootCategoryId=${homeCategory['source']['category']['id']}"> <img
                                src="${pageContext.request.contextPath}/resources${homeCategory['source']['category']['frontMedia']['path']}" alt="${homeCategory['source']['category']['name']}" />
                            <div class="light"></div>
                        </a>

                        <c:forEach items="${homeCategory['source']['hotProduct']}" var="product"
                                   varStatus="st">

                            <c:if test="${st.index<6}">
                                <a href="product/${product['productId']}"> <img
                                        src="${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}"
                                        alt="${product['name']}" />

                                    <p class="product-name">${product['name']}</p>

                                    <p class="product-cost">
                                        ￥<span><fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number"/></span>
                                    </p>

                                    <c:if test="${product['stock']<1}">

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

<!--脚部-->
<jsp:include page="../core/footer-main.jsp"/>
<!--fixed侧边栏-->
<div class="right">
    <a href="<spring:url value=""/>" class="right1" >
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/pig.png"/>">
        <div class="right2"><span>主页</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>"></div>
    </a>

    <div class="right-b">
        <a href="<spring:url value="/myAccount/orderHistory" />" class="right1">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-dd.png"/>">
            <div class="right2"><span>订单</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/search_17.png"/>">
            </div>
        </a>
        <a href="<spring:url value="/shoppingCart/cart" />" class="right1">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-buy.png"/>">
            <div class="right2"><span>购物车</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/search_17.png"/>">
            </div>
        </a>
        <a href="<spring:url value="/myAccount/favourites" />" class="right1">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-sc.png"/>">
            <div class="right2"><span>收藏</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/search_17.png"/>">
            </div>
        </a>
        <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>" class="right1">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-zh.png"/>">
            <div class="right2"><span>账户</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/search_17.png"/>">
            </div>
        </a>
        <a href="#" class="right1">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/_0005_arrow-top.png"/>">
            <div class="right2"><span>回到顶部</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/search_17.png"/>">
            </div>
        </a>
</div>

<div class="classify-product-box" id="classifyPop">
    <div class="classify-product-content">
        <p class="close"><span class="link-btn" id="closeClassifyPop">关闭分类</span></p>
        <div id="classifyPopCentent"></div>
    </div>
</div>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" data-main="<spring:url value="${juedangpinStaticPath}/index/index.js"/>"></script>

</body>

</html>