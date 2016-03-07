<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 11/3/2015
  Time: 10:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子典当行绝当品销售平台</title>
    <link rel="Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/shopping-cart/cart.css"/>"/>
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <jsp:include page="../shopping-cart/header-cart.jsp"/>
</div>

<!--正文-->
<div class="content-box">
    <div class="content-1" id="content1" style="display: block">
        <div class="content-title">
            <h2>我的购物车</h2>

            <div class="back-last">
                <a class="link-btn" href="<spring:url value="${urlConfiguration.homePage}"/>">
                    继续购物 >
                </a>
            </div>
        </div>

        <div class="error"><c:if test="${not empty error_message}"><span>${error_message}</span></c:if></div>

        <c:choose>
            <c:when test="${empty order or order.commerceItemCount eq 0}">
                <div class="none-product">
                    <p>对不起,您的购物车是空的.下面是为您推荐的商品:</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="some-products" style="display: block">
                    <table>
                        <thead>
                        <tr>
                            <td class="product-hidden"></td>
                            <td>商品信息</td>
                            <td class="product-name"></td>
                            <td class="product-level">成色</td>
                            <td>市场价</td>
                            <td>绝当价</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="invalidItemCount" value="0"/>
                        <c:forEach var="commerceItem" items="${order.commerceItems}">
                            <tr data-value="${commerceItem.referenceId}">
                                <td class="product-hidden">
                                    <input type="hidden" data-item-id="${commerceItem.id}"/>
                                </td>
                                <td class="img-box">
                                    <c:if test="${not commerceItem.inStock}">
                                        <c:set var="invalidItemCount" value="${invalidItemCount + 1}"/>
                                        <img src="<spring:url value="${juedangpinStaticPath}/core/images/productList/out-of-stock-s.png" />"
                                             class="out-of-stock"/>
                                    </c:if>
                                    <img src="${pageContext.request.contextPath}/resources${commerceItem['snapshotMedia']['path']}"
                                         alt="${empty commerceItem['snapshotMedia']['title'] ? commerceItem.name : commerceItem['snapshotMedia']['title']}"/>
                                </td>
                                <td class="product-name">
                                    <a href="<spring:url value="${urlConfiguration.pdpPage}/${commerceItem.referenceId}"/>">${commerceItem.name}</a>
                                </td>
                                <td class="product-level">
                                    <span class="level">${commerceItem.quality}</span>
                                </td>
                                <td class="product-old-cost">
                                    ¥ <span><fmt:formatNumber value="${commerceItem.listPrice}" pattern="0.00"
                                                              type="number"/></span>
                                </td>
                                <td class="product-now-cost">
                                    ¥ <span><fmt:formatNumber value="${commerceItem.salePrice}" pattern="0.00"
                                                              type="number"/></span>
                                </td>
                                <td>
                                    <c:if test="${not empty currentUser}">
                                        <p><a class="link-btn"
                                              href="<spring:url value="/myAccount/favouriteItemFromCart?productId=${commerceItem.referenceId}"/>">移入收藏</a>
                                        </p>
                                    </c:if>
                                    <p><a class="link-btn"
                                          href="<spring:url value="/shoppingCart/removeItemFromOrder?productId=${commerceItem.referenceId}"/>">删除</a>
                                    </p>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="settlement">
                        <div class="right">
                            <p>
                                <span class="settlement-name">商品总金额:</span>
                                <span class="cost">¥<span class="order-sub-total"><fmt:formatNumber
                                        value="${order.subtotal}" pattern="0.00" type="number"/></span></span>
                            </p>
                                <%--<p>
                                    <span class="settlement-name">已选商品:</span>
                                    <span>${commerceItem.count}</span>
                                    <span>件</span>
                                </p>--%>
                        </div>
                        <div class="left">
                            <p>
                                <a class="link-btn" href="<spring:url value="/shoppingCart/emptyCart"/>">清空购物车</a>
                                    <%--
                                    <a class="link-btn" href="#">删除选中商品</a>
                                    <a class="link-btn" href="#">移入收藏</a>
                                    --%>
                            </p>
                        </div>
                        <div class="bottom">
                            <span>账面应付金额 <span class="cost">¥<span class="order-total"><fmt:formatNumber
                                    value="${order.total}" pattern="0.00" type="number"/></span></span></span>
                            <c:choose>
                                <c:when test="${invalidItemCount gt 0}">
                                    <input class="d-btn" type="button" value="请先删除无效的商品"/>
                                </c:when>
                                <c:when test="${order.commerceItemCount gt orderItemLimit}">
                                    <input class="d-btn" type="button" value="最多一次购买 ${orderItemLimit} 个商品"/>
                                </c:when>
                                <c:otherwise>
                                    <input class="d-btn" type="button" value="去结算"
                                           onclick="window.location.href='..${urlConfiguration.shippingPage}?orderId=${order.id}'"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="horizontal-recommend-bar.jsp"/>
    <jsp:include page="../core/footer-main.jsp"/>
</div>
<jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/shopping-cart/shopping-cart.js"/>"></script>
</html>