<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title></title>
        <script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/index/js/jquery-1.8.3.min.js"/>"></script>
        <link href="<spring:url value="${juedangpinStaticPath}/index/dianjinzi.css"/>" rel="stylesheet">
        <script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/index/dianjinzi.js"/>"></script>

    </head>

    <body>


    <div class="right">
        <a href="<spring:url value=""/>" class="right1" >
            <img src="<spring:url value="${juedangpinStaticPath}/index/images/s-index.png"/>">
            <div class="right2"><span>主页</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/index/images/search_17.png"/>"></div>
        </a>

        <div class="right-b">
            <a href="<spring:url value="/myAccount/orderHistory" />" class="right1">
                <img src="<spring:url value="${juedangpinStaticPath}/index/images/s-dd.png"/>">
                <div class="right2"><span>订单</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/index/images/search_17.png"/>">
                </div>
            </a>
            <a href="<spring:url value="/shoppingCart/cart" />" class="right1">
                <img src="<spring:url value="${juedangpinStaticPath}/index/images/s-buy.png"/>">
                <div class="right2"><span>购物车</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/index/images/search_17.png"/>">
                </div>
            </a>
            <a href="<spring:url value="/myAccount/favourites" />" class="right1">
                <img src="<spring:url value="${juedangpinStaticPath}/index/images/s-sc.png"/>">
                <div class="right2"><span>收藏</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/index/images/search_17.png"/>">
                </div>
            </a>
            <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>" class="right1">
                <img src="<spring:url value="${juedangpinStaticPath}/index/images/s-zh.png"/>">
                <div class="right2"><span>账户</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/index/images/search_17.png"/>">
                </div>
            </a>
            <a href="#" class="right1">
                <img src="<spring:url value="${juedangpinStaticPath}/index/images/component/_0005_arrow-top.png"/>">
                <div class="right2"><span>回到顶部</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/index/images/search_17.png"/>">
                </div>
            </a>
        </div>
    </div>

    <!--header-->
    <input id="path" type="hidden" value="${pageContext.request.contextPath}">
    <div class="header">
        <div class="header-content">
            <c:if test="${currentUser==null}">
            <ul class="nav">
                <li class="nav-3"><a href="<spring:url value="${urlConfiguration.loginPage}"/>"> 登录</a></li>
                <li class="nav-4"><a href="<spring:url value="${urlConfiguration.registerPage}"/>"> 注册</a></li>
                <li class="nav-4"><a href="#"> 帮助中心</a> </li>
            </ul>
            </c:if>
            <c:if test="${currentUser!=null}">
            <ul class="nav">
                <li class="nav-2">欢迎您：${currentUser.username}</li>
                <li class="nav-4"><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"> 账户管理</a></li>
                <li class="nav-4"><a href="<spring:url value="${urlConfiguration.logoutPage}"/>"> 退出登录</a> </li>
                <li class="nav-4"><a href="#"> 帮助中心</a> </li>

            </ul>
            </c:if>


        </div>
    </div>
    <!--header-b-->
    <div class="content-top">
        <a href="#" class="logo" alt="sadhashdk"></a>

        <div class="search">
            <form action="${pageContext.request.contextPath}/essearch" method="get">
            <input type="text" class="search1" name="term" value="${term}">
            <input type="submit" class="btn1" value="搜索">
            </form>
            <ul class="font1">
                <li><a href="${pageContext.request.contextPath}/essearch?term=玉镯">玉镯</a></li>
                <li><a href="${pageContext.request.contextPath}/essearch?term=核桃糊">核桃糊</a></li>
                <li><a href="${pageContext.request.contextPath}/essearch?term=琥珀">琥珀</a></li>
                <li><a href="${pageContext.request.contextPath}/essearch?term=钻戒">钻戒</a></li>
                <li><a href="${pageContext.request.contextPath}/essearch?term=玉镯">玉镯</a></li>
            </ul>
        </div>
        <a href="<spring:url value="/shoppingCart/cart"/>" class="btn2"><img src="<spring:url value="${juedangpinStaticPath}/index/images/shoppingcart_03.png"/>">我的购物车</a>
    </div>

    <div class="menu-top">
        <a href="#" class="menu-top1">全部绝当品分类</a>
        <ul class="font2">
            <c:forEach items="${rootHomeCategories}" var="category">
            <c:set var="rootCategory" value="${category.source}"/>
            <li><a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${rootCategory.id}">${rootCategory.name}</a></li>
            </c:forEach>
        </ul>

        <ul class="menu">
            <c:forEach items="${rootHomeCategories}" var="category" varStatus="status">

                <c:set var="rootCategory" value="${category.source}"/>
                <li class="menu-0" <c:if test="${status.last==true}">style="border: none"></c:if>>
                    <a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${rootCategory.id}" class="menu-h">${rootCategory.name}</a>

                    <ul class="menu-1">
                        <c:forEach items="${rootCategory.children}" var="subCategory" varStatus="st">

                            <li <c:if test="${st.last eq true}">class="li-l"></c:if>><a href="${pageContext.request.contextPath}/essearch?parentCategoryId=${subCategory.id}">${subCategory.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>

            </c:forEach>
        </ul>
    </div>



    <div class="banner-all">
        <div class="banner1">
            <div id="bannerBox" class="banner-box">
                <div id="banner" class="banner">
                    <c:forEach items="${banner.images}" var="image" varStatus="status">

                        <c:if test="${status.index=='0'}">
                            <a href="${pageContext.request.contextPath}${image.url}" data-banner="${status.index}"
                               style="background: url('${pageContext.request.contextPath}${image.path}') no-repeat center center ; display: block"></a>
                        </c:if>

                        <c:if test="${status.index!='0'}">
                            <a href="${pageContext.request.contextPath}${image.url}" data-banner="${status.index}"
                               style="background: url('${pageContext.request.contextPath}${image.path}') no-repeat center center  ${image.color}"></a>
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

        </div>
    </div>

<!--content-->
<div class="content">
    <div class="box1">
        <div class="box-font" style="background: url('<spring:url value="${juedangpinStaticPath}/index/images/hot-sale_03.png"/>') no-repeat 100px 0">
            最近热卖
        </div>
        <c:forEach items="${hotSearchList}" var="hotSearch" varStatus="status">
            <c:if test="${status.index<4}">
            <a  href="${pageContext.request.contextPath}/essearch?term=${hotSearch.term}" class="box1-1"
            style="background-image: url('${pageContext.request.contextPath}/resources${hotSearch.frontMedia.path}')"></a>
            </c:if>
        </c:forEach>
    </div>


    <div class="box2">
        <div class="box-font" style="background-image: url('<spring:url value="${juedangpinStaticPath}/index/images/yingwen_22.png"/>') 100px 0">
        最近上新
       </div>
        <c:forEach items="${newProducts}" var="searchProduct">
            <c:set var="product" value="${searchProduct.source}"/>
        <a href="product/${product.productId}" class="box2-1" style="background-image: url('${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}') "></a>
        </c:forEach>
    </div>


    <c:forEach items="${hotProducts}" var="homeCategory"
               varStatus="status">
        <a class="box-banner1" href="${pageContext.request.contextPath}${homeCategory.source.category.banner.images[0].url}" style="background-image: url('${homeCategory.source.category.banner.images[0].path}')"></a>
    <div class="box4">
        <div class="box-font" style="color: ${homeCategory['source']['category']['color']}">
            <span class="span1"><a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${homeCategory['source']['category']['id']}">${homeCategory['source']['category']['name']}</a> </span>

            <span class="span2">
                <c:forEach items="${homeCategory['source']['category']['children']}"
                           var="subCategory">
                <a href="<spring:url value="essearch?parentCategoryId=${subCategory.id}"/>" class="span2-1">${subCategory['name']}</a>
                </c:forEach>
            </span>

        </div>
        <div class="box4-all">
            <a href="${pageContext.request.contextPath}/essearch?rootCategoryId=${homeCategory['source']['category']['id']}" class="box4-left" style="background-image: url('${pageContext.request.contextPath}/resources${homeCategory['source']['category']['frontMedia']['path']}')"></a>
            <div class="box4-right">

                <c:forEach items="${homeCategory['source']['hotProduct']}" var="product"
                           varStatus="st">
                <c:if test="${st.index<6}">
                <div class="box4-all1">
                    <a href="#" class="box4-1" style="background-image: url('${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}') ">

                        <!--
                        <div class="box4-on" >
                            <a href="product/${product['productId']}" class="box4-on-top" style="background-image: url('${pageContext.request.contextPath}/resources${product['advertisementMedia']['path']}') ">
                            </a>
                            <div class="box4-on-bottom">
                                <div class="box4-1-2">
                                        ${product['name']}
                                    <div class="box4-1-2-1">
                                        ￥<fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number"/>
                                    </div>
                                </div>
                                <a onclick="addEnjoy('${product['productId']}')" class="box4-fa">
                                    <img src="<spring:url value="${juedangpinStaticPath}/index/images/favorite.png"/>">
                                    收藏</a>

                                <a onclick="addCart('${product['productId']}')" class="box4-by">
                                    <img src="<spring:url value="${juedangpinStaticPath}/index/images/shopping-cart.png"/>">
                                    购物车</a>

                            </div>
                        </div>
                        -->

                    </a>
                </div>
                </c:if>
                </c:forEach>

            </div>
        </div>
    </div>
    </c:forEach>

</div>
<!--footer-->
<jsp:include page="../core/footer-main.jsp"/>
    <jsp:include page="../core/baidu.jsp"></jsp:include>

</body>
    <script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" data-main="<spring:url value="${juedangpinStaticPath}/index/index.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/index/js/index2.0.js"/>"></script>
    </html>