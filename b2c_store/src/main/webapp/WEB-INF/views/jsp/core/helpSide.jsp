<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/9/15
  Time: 8:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="fixed-aside-1">
    <li>
        <a class="aside-person" href="#">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/component/_0003_USER.png"/>" alt=""/>
        </a>
    </li>
    <li>
        <a class="aside-cart" href="<spring:url value="${urlConfiguration.shoppingCartPage}"/>">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/component/_0002_SHOPPING-CAR.png"/>" alt=""/>
            <span>购</span>
            <span>物</span>
            <span>车</span>
                <span class="count-box">
                    <span id="asideCartCount" class="count-number">${empty order.commerceItemCount ? 0 : order.commerceItemCount}</span>
                </span>
        </a>
    </li>
    <li>
        <a class="aside-order" href="#">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/component/_0000_NOTEPAD.png"/>" alt=""/>
            <span>订单</span>
        </a>
    </li>
    <li>
        <a class="aside-account" href="#">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/component/_0001_yen.png"/>" alt=""/>
            <span>账户</span>
        </a>
    </li>
    <li>
        <a class="aside-top" href="#">
            <img src="<spring:url value="${juedangpinStaticPath}/core/images/component/_0005_arrow-top.png"/>" alt=""/>
            <span>返回</span>
            <span>顶部</span>
        </a>
    </li>
</ul>
