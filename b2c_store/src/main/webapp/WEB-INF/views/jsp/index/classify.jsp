<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="product-list">

    <c:forEach items="${productList}" var="searchHit">
        <div class="list-product">
            <div class="inner">
                <a class="list-img-box"
                   href="${pageContext.request.contextPath}/product/${searchHit.source['productId']}"><img
                        src="${pageContext.request.contextPath}/resources/${searchHit.source['frontMedia']['path']}"
                        alt="${searchHit.source['name']}"/></a>
                <div class="list-price-box">¥ <span><fmt:formatNumber value="${searchHit.source['salePrice']}"
                                                                      pattern="0.00"
                                                                      type="number"/></span></div>
                <p class="product-link"><a
                        href="${pageContext.request.contextPath}/product/${searchHit.source['productId']}">[新品上市]${searchHit.source['name']}</a>
                </p>
                <p>已有<span></span>评价</p>
                <div class="product-message">添加成功</div>
                <div class="product-handle">
                    <a class="addEnjoy" data-btn="addEnjoy"
                       data-value="${searchHit.source['productId']}" href="#"><i
                            class="foundicon-heart" data-value="${product.productId}"></i>收藏</a>
                    <a class="addCart" href="#" data-btn="addCart"
                       data-value="${searchHit.source['productId']}"
                       data-url="<spring:url value="/shoppingCart/ajaxAddItemToOrder"/>"><i
                            class="foundicon-cart"></i>购物车</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
