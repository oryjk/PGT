<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/2/16
  Time: 6:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="products">

  <c:forEach items="${tenderListResult}" var="tenderItem">
    <a href="/tender/${tenderItem.tender.tenderId}" class="item">
      <div class="item-img">
        <img src="${tenderItem.tender.p2pFrontMedia.path}" alt=""/>
      </div>
      <div class="item-name">
          ${tenderItem.tender.name}
      </div>
      <div class="progress-bar">
        <div class="inner" style="width: 120px;" data-value="${tenderItem.tender.productResidue/tenderItem.tender.productQuantity}"></div>
      </div>
      <div class="info-box">
        <div class="surplus-count">
          <p class="info-value">${tenderItem.tender.productResidue}</p>

          <p class="info-title">剩余产品</p>
        </div>
        <div class="cost-sum">
          <p class="info-value"><span>¥</span>
            <span><fmt:formatNumber value="${tenderItem.tender.tenderTotal}" pattern="0.00" type="number"/></span>
          </p>

          <p class="info-title">产品认购总额</p>
        </div>
        <div class="surplus-time">
          <jsp:useBean id="nowDate" class="java.util.Date"/>
          <c:set value="${((tenderItem.tender.dueDate)/1000-(nowDate.time)/1000)/(1000 * 60 * 60 * 24)}" var="lastDay"/>
          <p class="info-value"><span><fmt:formatNumber value="${lastDay}" pattern="0" type="number"/></span>天</p>

          <p class="info-title">截止时间</p>
        </div>
      </div>
      <div class="invest-status">
        <c:choose>
          <c:when test="${tenderItem.tender.tenderStatus==10}">
            在当中
          </c:when>
          <c:when test="${tenderItem.tender.tenderStatus==20}">
            已绝当
          </c:when>
          <c:when test="${tenderItem.tender.tenderStatus==30}">
            已赎当
          </c:when>
        </c:choose>
      </div>
      <a class="invest-join-favorite" href=""></a>
    </a>
  </c:forEach>
</div>
