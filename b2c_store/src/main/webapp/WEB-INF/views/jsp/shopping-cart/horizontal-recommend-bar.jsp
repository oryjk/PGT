<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/11/2015
  Time: 12:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="clear-float"></div>

<div id="recommend" class="recommend">
    <ul id="tab" class="tab">
        <li class="choose"><h2 data-tab="0">类似商品</h2></li>
        <c:if test="${fn:length(browsedProducts) gt 0 and empty param.excludeBrowsed}">
            <li><h2 data-tab="1">最近浏览</h2></li>
        </c:if>
        <c:if test="${not empty currentUser and fn:length(favourites.result) gt 0 and empty param.excludeFavourites}">
            <li><h2 data-tab="2">我的收藏</h2></li>
        </c:if>
    </ul>
    <div class="similar-box">
        <ul class="similar" id="rowList1">
            <c:forEach var="product" items="${recommendProducts}">
                <li>
                    <a class="similar-pic-box" href="<spring:url value="${urlConfiguration.pdpPage}/${product.productId}"/>">
                        <img src="${pageContext.request.contextPath}/resources${product.frontMedia.path}"
                             alt="${empty product.frontMedia.title ? product.name : product.frontMedia.title}" /></a>
                    <a class="similar-name" href="<spring:url value="${urlConfiguration.pdpPage}/${product.productId}"/>">
                       ${product.name} ${product.serialNumber}
                    </a>
                    <p class="similar-cost">
                        ¥<span><fmt:formatNumber value="${product.salePrice}" pattern="#.00" type="number" /></span>
                    </p>
                    <div class="product-handle">
                        <a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i></a>
                        <a class="addEnjoy"  href="#" data-value="${product.productId}"><i class="foundicon-heart"></i></a>
                        <a class="addCart" href="#" data-value="${product.productId}"><i class="foundicon-cart"></i></a>
                    </div>
                    <div class="product-message">添加成功</div>
                    <div class="out-of-stock"></div>
                </li>
            </c:forEach>
        </ul>
        <div class="move-left-box">
            <a href="#" id="moveLeft1"><</a>
        </div>
        <div class="move-right-box">
            <a href="#" id="moveRight1">></a>
        </div>
    </div>
    <c:if test="${fn:length(browsedProducts) gt 0 and empty param.excludeBrowsed}">
        <div class="similar-box" style="display: none">
            <ul class="similar" id="rowList3">
                <c:forEach var="product" items="${browsedProducts}">
                    <li>
                        <a class="similar-pic-box" href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>">
                            <img src="<spring:url value="/resources${product.source.frontMedia.path}"/>"
                                 alt="${empty product.source.frontMedia.title ? product.source.name : product.source.frontMedia.title}" /></a>
                        <a class="similar-name" href="<spring:url value="${urlConfiguration.pdpPage}/${product.source.productId}"/>" />
                            ${product.source.name} ${product.source.serialNumber}
                        </a>
                        <p class="similar-cost">
                            ¥<span><fmt:formatNumber value="${product.source.salePrice}" pattern="#.00" type="number" /></span>
                        </p>
                        <div class="product-handle">
                            <a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i></a>
                            <a class="addEnjoy"  href="#" data-value="${product.source.productId}"><i class="foundicon-heart"></i></a>
                            <a class="addCart" href="#" data-value="${product.source.productId}"><i class="foundicon-cart"></i></a>
                        </div>
                        <div class="product-message">添加成功</div>
                        <div class="out-of-stock"></div>
                    </li>
                </c:forEach>
            </ul>
            <div class="move-left-box">
                <a href="#" id="moveLeft2"><</a>
            </div>
            <div class="move-right-box">
                <a href="#" id="moveRight2">></a>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty currentUser and fn:length(favourites.result) gt 0 and empty param.excludeFavourites}">
        <div class="similar-box" style="display: none">
            <ul class="similar" id="rowList2">
                <c:forEach var="fav" items="${favourites.result}">
                    <li>
                        <a class="similar-pic-box" href="<spring:url value="${urlConfiguration.pdpPage}/${fav.productId}"/>">
                            <img src="${pageContext.request.contextPath}/resources${fav['snapshotMedia']['path']}"
                                 alt="${empty fav['snapshotMedia']['title'] ? fav.name : fav['snapshotMedia']['title']}" /></a>
                        <a class="similar-name" href="<spring:url value="${urlConfiguration.pdpPage}/${fav.productId}"/>">
                            ${fav.name}
                        </a>
                        <p class="similar-cost">
                            ¥<span><fmt:formatNumber value="${fav.finalPrice}" pattern="#.00" type="number" /></span>
                        </p>
                        <div class="product-handle">
                            <a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i></a>
                            <a class="addEnjoy"  href="#" data-value="${product.productId}"><i class="foundicon-heart"></i></a>
                            <a class="addCart" href="#" data-value="${product.productId}"><i class="foundicon-cart"></i></a>
                        </div>
                        <div class="product-message">添加成功</div>
                        <div class="out-of-stock"></div>
                    </li>
                </c:forEach>
            </ul>
            <div class="move-left-box">
                <a href="#" id="moveLeft3"><</a>
            </div>
            <div class="move-right-box">
                <a href="#" id="moveRight3">></a>
            </div>
        </div>
    </c:if>
</div>