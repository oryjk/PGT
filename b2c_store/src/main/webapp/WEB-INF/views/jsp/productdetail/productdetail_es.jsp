<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

   <!--正文-->
    <div class="content-box">
        <!-- 面包屑-->
        <jsp:include page="../core/breadCrumb.jsp"/>
        <!-- 基本信息-->
        <div class="major-info">
            <div class="pic-box">
                <div class="middle-pic">
                    <img id="middlePic"
                        src="${pageContext.request.contextPath}/resources${product['heroMedias'][0]['path']}"   
                        alt="1" />
                    <c:if test="${product.stock<1}">
                    <div class="out-of-stock"></div>
                    </c:if>
                    <div id="glass" class="glass"></div>   
                </div>
                <ul id="smallPic" class="small-pic">
                    <c:forEach items="${product.heroMedias}" var="productMedia">
                        <li><img
                            src="${pageContext.request.contextPath}/resources${productMedia['path']}"
                            alt="" /></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="info-box">
                <h1>${product.name}</h1>
                <h2>${product.title}</h2>
                <div class="other-description"></div>
                <div class="cost">
                    <c:if test="${!empty product['listPrice']}">
                        <p>
                            <span>市场价:</span> ¥ <span class="old-cost"><fmt:formatNumber value="${product.listPrice}" pattern="0.00" type="number" /></span>
                        </p>
                    </c:if>
                    <p>
                        <span>绝当价:</span> <span class="our-cost">
                            ¥<fmt:formatNumber value="${product.salePrice}" pattern="0.00" type="number" /></span> (<span><fmt:formatNumber
                                type="number" value="${product['salePrice']/product['listPrice']*10}"
                                maxFractionDigits="0" /></span>折)
                    </p>
                </div>
                <p class="style">
                    <span>款式:</span> 
                    ${product['shortDescription']}
<!--
                    <a href="#">宝石蓝</a> <a href="#">斜阳红</a> <a
                        href="#">翡翠绿</a> <a href="#">琥珀黄</a> <a href="#">高级灰</a>
                    -->
                </p>
                <p class="level">
                    <span>成色:</span> <span>${product['isNew']}</span>
                </p>
                <div class="buy">
                	<c:if test="${not empty currentUser}">
                    	<a class="buy-now" href="<spring:url value="/shoppingCart/addItemToOrder?productId=${product.productId}&easyBuy=1"/>"> <span>立即购买</span></a>
                	</c:if>
                     <a class="join-cart" href="#" data-value="${product.productId}" data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>" > <span>加入购物车</span>
                    </a> <a class="collect" href="#"> <span>添加收藏</span>
                    </a>
                </div>
                <div class="tips">
                    <p>温馨提示</p>
                    <p>1.本商品不支持货到付款，无质量问题不支持退换货.</p>
                    <p>2.因绝当商品每款仅一件且与线下典当行同步销售，偶尔会出现典当行售出无货情况</p>
                    <p>3.出现此类情况我们会尽快为您办理退款事宜，敬请谅解。</p>
                </div>
                <div class="bdsharebuttonbox">
                    <a href="#" class="bds_more" data-cmd="more"></a> <a href="#"
                        class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a> <a
                        href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"> </a>
                    <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a> <a
                        href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
                    <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                </div>
            </div>
            <div id="bigPic" class="big-pic"
                style="background: url('${pageContext.request.contextPath}/resources${product.heroMedias[0].path}')"></div>
        </div>

        <ul class="aside">
            <jsp:include page="../shopping-cart/vertical-recommend-bar.jsp" />
        </ul>

        <!-- 详细内容-->
        <div class="concreteness">
            <!-- 类似商品-->
            <div class="similar-box">
                <h2>类似商品</h2>
                <ul class="similar" id="rowList">

                <c:forEach items="${similarProducts}" var="product">
                    <li><a class="similar-pic-box"
                        href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>"><img
                        src="${pageContext.request.contextPath}/resources${product.source.frontMedia.path}"
                        alt="${empty product.source.frontMedia.source.title ? product.source.name : product.source.frontMedia.title}"/></a>
                    <a class="similar-name" href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>">
                        ${product.source.name} ${product.source.serialNumber} </a>

                    <p class="similar-cost">
                        ¥ <span><fmt:formatNumber value="${product.source.salePrice}" pattern="0.00"
                                              type="number"/></span>
                    </p>
                    <div class="product-handle">
                        <a class="addEnjoy"  href="#" data-value="${product.source.productId}"><i class="foundicon-heart"></i></a>
                        <a class="addCart" href="#" data-value="${product.source.productId}"><i class="foundicon-cart"></i></a>
                    </div>
                    <div class="product-message">添加成功</div>

                        <c:if test="${product.source.stock<1}">
                    <div class="out-of-stock"></div>
                        </c:if>

                    </li>
                </c:forEach>
                </ul>
                <div class="move-left-box">
                    <a href="#"  id="moveLeft"><</a>
                </div>
                <div class="move-right-box">
                    <a href="#"  id="moveRight">></a>
                </div>
            </div>
            
              <!-- 主要信息-->
            <div class="main-info">
                <ul id="tab" class="tab">
                    <li class="choose"><h2 data-tab="0">商品详情</h2></li>
                    <li><h2 data-tab="1">商品咨询</h2></li>
                    <li><h2 data-tab="2">网友讨论</h2></li>
                </ul>
                <div id="info" class="info">
                    
                    <c:forEach items="${product['mainMedias']}" var="MEDIA">
                    
                    <img
                        src="${pageContext.request.contextPath}/resources${MEDIA['path']}"
                        alt="1" />
                        </c:forEach>
                        <img
                        src="${pageContext.request.contextPath}/resources${product['expertMedia']['path']}"
                        alt="1" />
                        
                        
                </div>
                
                <input id="productId" type="hidden" value="${product['productId']}">