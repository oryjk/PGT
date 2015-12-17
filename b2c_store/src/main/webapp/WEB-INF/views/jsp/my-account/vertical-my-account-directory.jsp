<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/14/2015
  Time: 1:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="aside">
    <h2>账户管理</h2>
    <ul>
        <li>
            <c:choose>
                <c:when test="${fn:contains(param.step, 'orderHistory')}">
                    <a class="menu-level-1 current-page" href="<spring:url value="/myAccount/orderHistory"/>">我的订单</a>
                </c:when>
                <c:otherwise>
                    <a class="menu-level-1" href="<spring:url value="/myAccount/orderHistory"/>">我的订单</a>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
            <a class="menu-level-1" href="#">收藏夹</a>
            <ul>
                <li>
                    <c:choose>
                        <c:when test="${fn:contains(param.step, 'favourites')}">
                            <a class="menu-level-end current-page" href="<spring:url value="/myAccount/favourites"/>">我的收藏</a>
                        </c:when>
                        <c:otherwise>
                            <a class="menu-level-end" href="<spring:url value="/myAccount/favourites"/>">我的收藏</a>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li>
                    <c:choose>
                        <c:when test="${fn:contains(param.step, 'browsed')}">
                            <a class="menu-level-end current-page" href="<spring:url value="/myAccount/browsedProducts"/>">最近浏览</a>
                        </c:when>
                        <c:otherwise>
                            <a class="menu-level-end" href="<spring:url value="/myAccount/browsedProducts"/>">最近浏览</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </li>
        <li>
            <a class="menu-level-1" href="<spring:url value="/user/yeepayAccountInfo"/>">支付绑定</a>
        </li>
        <li>
            <a class="menu-level-1" href="#">个人中心</a>
            <ul>
                <li>
                    <a class="menu-level-end" href="#">个人信息</a>
                </li>
                <li>
                    <a class="menu-level-end" href="#">修改密码</a>
                </li>
                <li>
                    <a class="menu-level-end ${param.step eq 'address' ? 'current-page' : '' }" href="<spring:url value="/my-account/person-info/address"/>">地址管理</a>
                </li>
            </ul>
        </li>
        <li>
            <a class="menu-level-1" href="<spring:url value="/helpcenter/query"/>">帮助中心</a>
        </li>
    </ul>
</div>
