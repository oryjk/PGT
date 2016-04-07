<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/guide-pages/guide-pages.css"/>
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
</head>
<body>
<jsp:include page="../core/head-main.jsp"/>

<!--banner begin-->
<div id="bannerBox" class="banner-box">
    <div id="banner" class="banner">
        <a href="#" data-banner="0"
           style="background: url('../core/images/data/banner-3.jpg') center center no-repeat;display: block"></a>
        <a href="#" data-banner="1"
           style="background: url('../core/images/data/banner-2.jpg') center center no-repeat"></a>
    </div>
    <ol id="bannerNav" class="banner-nav">
        <li class="banner-nav-current">0</li>
        <li>1</li>
    </ol>
</div>
<!--banner end-->

<!--content begin -->
<div class="content">
    <div class="b2c-title">
        <img src="/resources/core/images/guide-pages/lin3.png" alt=""/>
        淘在当
        <img src="/resources/core/images/guide-pages/lin1.png" alt=""/>
    </div>
    <p>中国首家在线在当品交易平台</p>

    <p>超低价收购绝当品，超高本金赔付收益</p>
    <a href="#" class="content-btn">查看更多</a>

    <div class="content-tender">
        <%--<!-- 推荐 begin-->--%>
        <%--<a href="#" class="content-tender-box">--%>
            <%--<img class="tender-img-border" src="../core/images/guide-pages/product.jpg" alt=""/>--%>
            <%--<p class="tender-p-border">天然翡翠天然翡翠天然翡翠天然翡翠天然翡翠天然翡翠</p>--%>
            <%--<div class="content-tender-img-border"></div>--%>
            <%--<div class="content-tender-left-img-border"></div>--%>
        <%--</a>--%>
        <%--<!-- 推荐 end-->--%>

        <!-- 精选-->
        <c:forEach items="${advertisementB2CList}" var="b2cList" varStatus="status">
            <c:if test="${status.count < 9}">
                <a href="#" class="content-tender-box">
                <img src="${b2cList.url}" alt=""/>
                <p>${b2cList.name}</p>
                <div class="content-tender-img" style="background: url('/resources/core/images/guide-pages/h-jiantou.png') no-repeat"></div>
                <div class="content-tender-left-img" style="background: url('/resources/core/images/guide-pages/jinxuan.png') no-repeat;"></div>
            </c:if>
        </a>
        </c:forEach>
    </div>
</div>
<a href="#" class="content-banner" style="background:url('/resources/core/images/data/banner-4.jpg') no-repeat 50% 50% "></a>

<div class="content">
    <div class="b2c-title">
        <img src="/resources/core/images/guide-pages/lin3.png" alt=""/>
        绝当淘
        <img src="/resources/core/images/guide-pages/lin1.png" alt=""/>
    </div>
    <p>中国首家在线在当品交易平台</p>

    <p>超低价收购绝当品，超高本金赔付收益</p>

    <a href="#" class="content-btn">查看更多</a>
    <div class="content-product">
            <div class="content-product-box">
                <a href="#" class="content-product-box-left">
                    <img src="${advertisementP2PList[0].url}" alt=""/>
                    <p>${advertisementP2PList[0].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
                <a href="#" class="content-product-box-right">
                    <img src="${advertisementP2PList[1].url}" alt=""/>
                    <p>${advertisementP2PList[1].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
            </div>
            <div class="content-product-box box-bg">
                <a href="#" class="content-product-box-left">
                    <img src="${advertisementP2PList[2].url}" alt=""/>
                    <p>${advertisementP2PList[2].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
                <a href="#" class="content-product-box-right">
                    <img src="${advertisementP2PList[3].url}" alt=""/>
                    <p>${advertisementP2PList[3].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
            </div>
            <div class="content-product-box box-bg">
                <a href="#" class="content-product-box-left">
                    <img src="${advertisementP2PList[4].url}" alt=""/>
                    <p>${advertisementP2PList[4].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
                <a href="#" class="content-product-box-right">
                    <img src="${advertisementP2PList[5].url}" alt=""/>
                    <p>${advertisementP2PList[5].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
            </div>
            <div class="content-product-box">
                <a href="#" class="content-product-box-left">
                    <img src="${advertisementP2PList[6].url}" alt=""/>
                    <p>${advertisementP2PList[6].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
                <a href="#" class="content-product-box-right">
                    <img src="${advertisementP2PList[7].url}" alt=""/>
                    <p>${advertisementP2PList[7].name}</p>
                    <p><span>1111.00</span>元</p>
                </a>
            </div>

        <div class="content-product-img1" style="background: url('/resources/core/images/guide-pages/juezt.png') no-repeat;"></div>
        <div class="content-product-img2" style="background: url('/resources/core/images/guide-pages/lin3.png') no-repeat;"></div>
        <div class="content-product-img3" style="background: url('/resources/core/images/guide-pages/lin1.png') no-repeat;"></div>
        <div class="content-product-img4" style="background: url('/resources/core/images/guide-pages/lin2.png') no-repeat;"></div>
        <div class="content-product-img5" style="background: url('/resources/core/images/guide-pages/lin4.png') no-repeat;"></div>
    </div>
</div>
<!--content end -->

<jsp:include page="../core/footer-main.jsp"/>

<script src="/resources/core/js/require.js" data-main="/resources/guide-pages/guide-pages"></script>
</body>
</html>
