<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../core/head.jspf">
    <jsp:param name="cssPath" value="/index/index.css" />
    <jsp:param name="title" value="闲置物品典当与销售平台" />
    <jsp:param name="keywords" value="点金子,淘在当,绝当淘,典当,典当行,当铺,在线典当,在线典当商城,绝当商城,绝当预售,网上抵押借款,网上当铺,网上典当" />
    <jsp:param name="description" value="点金子，典当行业互联网风控专家，专业绝当品销售、在当品预售平台，绝当品保真价优，方便享受典当业务，操作便捷，放款迅速。" />
</jsp:include>
<body>
<img class="search-img" src="/resources/juedangpin/core/images/index/baidu_logo.jpg" alt="点金子绝当淘"/>

<!--头部-->
<div class="header" id="header">
    <jsp:include page="../core/header-main.jsp"/>

</div>

<!--正文-->

<div id="content" class="content-box">
    <div id="bannerBox" class="banner-box">

            <div id="banner" class="banner">
                <c:forEach items="${banner.images}" var="image" varStatus="status">
                    <c:if test="${status.index=='0'}" >
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
                            style="background-image: url('${pageContext.request.contextPath}${hotSearch.frontMedia.path}');"></a>
                    </c:if>
                </c:forEach>
            </div>
        </div>


        <c:forEach items="${hotProducts}" var="homeCategory"
                   varStatus="status">
            <c:set var="category" value="${homeCategory.category.source}"/>

            <c:if test="${status.index % 2 ==0}">

                <div class="products-list-cardinal">

                    <ul class="products-nav">


                        <c:forEach items="${category.children}"
                                   var="subCategory">
                            <li><a href="<spring:url value="essearch?parentCategoryId=${subCategory.id}"/>">${subCategory['name']}</a></li>
                        </c:forEach>

                        <li><a href="<spring:url value="essearch?parentCategoryId=${category.id}"/>">
                            更多></a></li>
                    </ul>

                    <h2 style="border-bottom: 2px solid ${category.color}">
                            ${category.name} <small style="color: ${category.color}">${category.description}</small>
                    </h2>
                    <div class="products">

                        <a class="product-main" href="${pageContext.request.contextPath}/essearch?rootCategoryId=${category.id}">
                            <img
                                    src="${pageContext.request.contextPath}/resources${category.frontMedia.path}" alt="${category.imageDesc}" />
                            <div class="light"></div>
                        </a>

                        <c:forEach items="${homeCategory.hotProducts}" var="homeProduct"
                                   varStatus="st">
                            <c:set var="product" value="${homeProduct.source}"/>
                            <c:if test="${st.index<6}">

                                <a href="product/${product['productId']}">
                                    <img
                                            src="${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}"
                                            alt="${product['imageDesc']}" />

                                    <p class="product-name">${product['name']}</p>

                                    <p class="product-cost">
                                        ￥<span><fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number"/></span>
                                    </p>

                                    <c:if test="${product['stock']<1}">
                                        <div class="out-of-stock">

                                        </div>
                                    </c:if>

                                    <div class="product-message">添加成功</div>

                                    <div class="product-hover-area" style="background: ${category.color}">
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

                        <c:forEach items="${category.children}"
                                   var="subCategory">
                            <li><a href="<spring:url value="essearch?parentCategoryId=${subCategory.id}"/>">${subCategory['name']}</a></li>
                        </c:forEach>
                        <li><a href="<spring:url value="essearch?parentCategoryId=${category.id}"/>">
                            更多></a></li>
                    </ul>
                    <h2 style="border-bottom: 2px solid ${category.color}">
                            ${category.name} <small style="color: ${category.color}">${category.description}</small>
                    </h2>
                    <div class="products">

                        <a class="product-main" href="${pageContext.request.contextPath}/essearch?rootCategoryId=${category.id}"> <img
                                src="${pageContext.request.contextPath}/resources${category.frontMedia.path}" alt="${category.imageDesc}" />
                            <div class="light"></div>
                        </a>

                        <c:forEach items="${homeCategory.hotProducts}" var="homeProduct"
                                   varStatus="st">
                            <c:set var="product" value="${homeProduct.source}"/>

                            <c:if test="${st.index<6}">
                                <a href="product/${product['productId']}"> <img
                                        src="${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}"
                                        alt="${product['imageDesc']}" />

                                    <p class="product-name">${product['name']}</p>

                                    <p class="product-cost">
                                        ￥<span><fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number"/></span>
                                    </p>

                                    <c:if test="${product['stock']<1}">
                                        <div class="out-of-stock">

                                        </div>
                                    </c:if>

                                    <div class="product-message">添加成功</div>

                                    <div class="product-hover-area" style="background: ${category.color}">
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
<jsp:include page="../core/footer-main.jsp">
    <jsp:param name="useside" value="true" />
</jsp:include>
<div class="classify-product-box" id="classifyPop">
    <div class="classify-product-content">
        <p class="close"><span class="link-btn" id="closeClassifyPop">关闭分类</span></p>
        <div id="classifyPopCentent"></div>
    </div>
</div>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" data-main="<spring:url value="${juedangpinStaticPath}/index/index.js"/>"></script>
<jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
</html>