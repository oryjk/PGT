<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags" prefix="date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/index/index.css"/>
    <style>
        /*super:请在此处写上各个分类的字体颜色,在html里面,注意类名要递增*/
        .each-category-1 .list-head .category-nav-choose {
            color: #fe6c6c;
        }
        .each-category-2 .list-head .category-nav-choose {
            color: #7ecafe;
        }
        .each-category-3 .list-head .category-nav-choose {
            color: #fec64f;
        }
        .each-category-end .list-head .category-nav-choose {
            color: #b1dd7a;
        }
    </style>
</head>
<body>
<img class="search-img" src="/resources/core/images/app-qrcode.png" alt="点金子绝当淘"/>
<!--header begin-->
<div class="header">
    <div class="header-inner">
        <h1 class="logo">
            <a href="#"></a>
        </h1>
        <ul class="nav">
            <li><a href="#">在当品大厅</a></li>
            <li><a href="#">在线典当</a></li>
            <li><a href="#">绝当品</a></li>
        </ul>
        <form class="search" action="">
            <input type="text" placeholder="点击搜索"/>
        </form>
        <div class="status">
            <div class="status-login" style="display: none">
                <a class="status-login-btn" href="#">登录</a>
                <a class="status-register-btn" href="#">注册</a>
                <a class="status-help-center" href="#">帮助</a>
            </div>
            <div class="status-logout" style="display: block">
                <div class="status-my-account"  id="statusMyAccount">
                    <a class="status-user-face-box" href="#"><img class="status-user-face" src="" alt=""/></a>
                    <ul class="status-my-account-detail" id="statusMyAccountDetail">
                        <li><a href="#">我的订单</a></li>
                        <li><a href="#">我的收藏</a></li>
                        <li><a href="#">最近浏览</a></li>
                        <li><a href="#">退出</a></li>
                    </ul>
                </div>
                <a class="status-help-center" href="#">帮助</a>

            </div>
        </div>
    </div>
</div>
<!--header end-->

<!--banner begin-->
<!--super:时差滚动轮播图,把图片放在#bannerBackEnd里面,把文字和链接放在#bannerFrontEnd里面-->
<ul id="bannerBackEnd" class="banner-bg">
    <c:forEach items="${banner.images}" var="image" varStatus="status">
    <li class="banner-bg-current" style="background:url('${image.path}') no-repeat center center"></li>
    </c:forEach>
</ul>
<div id="bannerBox" class="banner">
    <ul id="bannerFrontEnd">
        <c:forEach items="${banner.images}" var="image" varStatus="status">
        <li class="banner-current"><a class="banner-link" href="${image.url}">${image.title}</a></li>
        </c:forEach>
    </ul>

    <ol id="bannerNav">
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

<!--banner end-->

<!--menu begin-->
    <ul class="menu-1">
        <c:forEach items="${siteHot}" var="searchHit">
            <c:set value="${searchHit.source}" var="categoryList"></c:set>
        <li class="menu-1-item">
            <a class="menu-1-title" href="javascript:void(0);">
                <h3>${categoryList.category.name}</h3>
                <p>${categoryList.category.description}</p>
            </a>
            <div class="menu-detail">
                <h4>${categoryList.category.name} <small>精品简陋手要快</small></h4>
                <ul class="menu-2">
                    <c:forEach items="${categoryList.category.children}" var="childrenCategory">
                    <li class="menu-2-item"><a href="#">${childrenCategory.name}</a></li>
                    </c:forEach>
                </ul>
                <div class="menu-row-1">
                    新品抢购中!
                </div>
                <div class="menu-row-2">
                    <a href="#">
                        西藏牦牛角杯一套(四只装)
                    </a>
                </div>
                <div class="menu-row-3">
                    <a class="menu-img-box" href="#">
                        <img src="/resources/core/images/product/invest_ad_1_00.jpg" alt=""/>
                    </a>
                    <div class="menu-complain">天然牦牛 优质珍品</div>
                    <div class="menu-people-hot">
                        <span class="menu-people-count">35</span>人购买
                    </div>
                    <div class="menu-btn-box">
                        <a href="#">立即抢购</a>
                    </div>
                </div>
            </div>
        </li>
        </c:forEach>
    </ul>


<!--menu end-->

<!--content begin-->
<div class="content">
    <div class="advantage">
        <div class="advantage-inner">
            <h2>预售特点</h2>
            <ul>
                <li class="advantage-li-1"><a style="background-image: url('../core/images/index/advantage-1.png')" href="javascript:void(0);"></a></li>
                <li class="advantage-li-2"><a style="background-image: url('../core/images/index/advantage-2.png')" href="javascript:void(0);"></a></li>
                <li class="advantage-li-3"><a style="background-image: url('../core/images/index/advantage-3.png')" href="javascript:void(0);"></a></li>
                <li class="advantage-li-4"><a style="background-image: url('../core/images/index/advantage-4.png')" href="javascript:void(0);"></a></li>
            </ul>
        </div>
    </div>
    <div class="advert" style="background: white;" >
        <a class="advert-inner" href="#"></a>
    </div>
    <div class="content-part">
        <div class="hot">
            <div class="hot-head">
                <div class="hot-tab">
                    <a class="hot-tab-choose" href="javascript:void(0);">热门推荐</a>
                    <a class="" href="javascript:void(0);">在线典当</a>
                </div>
                <a class="hot-more" href="javascript:void(0);">更多>></a>
            </div>
            <div class="hot-main">
                <ul class="invest-list">
                    <c:forEach items="${categoryHot}" var="hit" varStatus="status">
                        <c:set value="${hit.source.tender}" var="tender"></c:set>
                    <c:if test="${status.index<3}">
                        <li>
                        <div class="invest-inner">
                            <a class="img-box" href="#"><img src="${tender.p2pAdvertisement.path}" alt="产品的名字"/></a>
                            <h4><a href="#">${tender.name}</a></h4>
                            <div class="invest-row-1">特点:<span>${tender.description}</span></div>
                            <div class="invest-row-2">截止日期:<span><date:date value="${tender.dueDate}" style="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="invest-row-3">剩余数量:<span>6</span>个</div>
                        </div>
                    </li>
                    </c:if>
                    </c:forEach>
                </ul>
                <div class="need-invest">
                    <div class="need-invest-opacity">

                    </div>
                </div>
            </div>

        </div>
        <div class="category">
            <h2>在当预售</h2>

            <c:forEach items="${siteHot}" var="searchHit" varStatus="status">
                <c:set value="${searchHit.source}" var="categoryList"></c:set>
                <c:if test="${status.last!=true}">
            <div class="each-category each-category-${status.count}" style="border-top: 2px solid #fc6c6c;">
                <div class="list-head" style="background-image: linear-gradient(180deg,#fc6c6c,#ffafb0);">
                    <h3>${categoryList.category.name}</h3>
                    <ul class="category-nav">
                        <c:forEach items="${categoryList.category.children}" var="childrenCategory">
                        <li><a class="category-nav-item" href="javascript:void(0);">${childrenCategory.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
                <ul class="invest-list">
                    <li>
                        <div class="invest-inner">
                            <a class="img-box" href="#"><img src="../core/images/product/invest_ad_0_00.jpg" alt="产品的名字"/></a>
                            <h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>
                            <div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
                            <div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
                            <div class="invest-row-3">剩余数量:<span>6</span>个</div>
                        </div>
                    </li>
                    <li>
                        <div class="invest-inner">
                            <a class="img-box" href="#"><img src="../core/images/product/invest_ad_1_00.jpg" alt="产品的名字"/></a>
                            <h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>
                            <div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
                            <div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
                            <div class="invest-row-3">剩余数量:<span>6</span>个</div>
                        </div>
                    </li>
                    <li>
                        <div class="invest-inner">
                            <a class="img-box" href="#"><img src="../core/images/product/invest_ad_2_00.jpg" alt="产品的名字"/></a>
                            <h4><a href="#">鑫鑫百年典当行生肖戒指一套(12个)</a></h4>
                            <div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
                            <div class="invest-row-2">截止日期:<span>2016年3月6日</span></div>
                            <div class="invest-row-3">剩余数量:<span>6</span>个</div>
                        </div>
                    </li>
                </ul>
            </div>

                </c:if>
                <c:if test="${status.last==true}">
                    <div class="each-category each-category-end" style="border-top: 2px solid #b1dd7a;">
                        <div class="list-head" style="background-image: linear-gradient(180deg,#b1dd7a,#cbe890);">
                            <h3>珠宝在当</h3>
                            <ul class="category-nav">
                                <li><a class="category-nav-item category-nav-choose" href="javascript:void(0);">新手专区</a></li>
                            </ul>
                        </div>
                        <div class="help">
                            <div class="help-hot">
                                <a href="#" class="help-img-box"><img src="../core/images/product/help_img_1.png" alt="#"/></a>
                                <a href="#" class="help-img-box"><img src="../core/images/product/help_img_2.png" alt="#"/></a>
                            </div>
                            <ul class="help-list">
                                <li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
                                <li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
                                <li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
                                <li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
                                <li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
                                <li><a href="#">干货get: 实现财务自由必须经历的四个原则.</a></li>
                            </ul>
                        </div>
                        <div class="app-code">
                            <div class="app-row-1">点金子淘在当APP</div>
                            <div class="app-row-2">玩转购物新方式</div>
                            <div class="app-row-3"><img src="../core/images/product/app-qrcode.png" alt=""/></div>
                            <div class="app-row-4">扫我 轻松得实惠</div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>

        </div>
        <div class="notice"></div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="index"></script>
</body>
</html>