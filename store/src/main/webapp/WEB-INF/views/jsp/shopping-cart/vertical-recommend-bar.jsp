<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/11/2015
  Time: 3:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 侧边栏:向您推荐-->

    <h2>精品推荐</h2>
    <a class="move-top" id="moveTop" href="#"><i class="foundicon-up-arrow"></i></a>
    <div class="inner">
        <ul id="verticalList" >
        <c:forEach var="product" items="${recommendProducts}" begin="0" end="5">
            <li class="aside-product">
                <a class="aside-product-imgbox" href="<spring:url value="${urlConfiguration.pdpPage}/${product.productId}"/>">
                    <img src="${pageContext.request.contextPath}/resources${product.frontMedia.path}"
                         alt="${empty product.frontMedia.title ? product.name : product.frontMedia.title}" /></a>
                <p>
                    <span class="aside-product-priceh"></span>
                    <span class="aside-product-price">¥
                        <span><fmt:formatNumber value="${product.salePrice}" pattern="#.00" type="number" /></span>
                    </span>
                </p>
                <p>
                    <a class="aside-product-name" href="<spring:url value="${urlConfiguration.pdpPage}/${product.productId}"/>">${product.name} ${product.serialNumber}</a>
                </p>
                <div class="product-handle">
                    <a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i></a>
                    <a class="addEnjoy" href="#" data-value="${product.productId}"><i class="foundicon-heart"></i></a>
                    <a class="addCart" href="#"  data-value="${product.productId}"><i class="foundicon-cart"></i></a>
                </div>
                <div class="out-of-stock"></div>
                <div class="product-message">添加成功</div>
            </li>
        </c:forEach>
        </ul>
    </div>
    <a class="move-bottom" id="moveBottom" href="#"><i class="foundicon-down-arrow"></i></a>
