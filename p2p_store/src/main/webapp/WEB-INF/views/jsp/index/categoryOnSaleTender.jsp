<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/21/16
  Time: 1:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags" prefix="date" %>
<c:forEach items="${categoryOnSaleTender}" var="onSaleTender">
	<li>
		<div class="invest-inner">
			<a class="img-box" href="#">
				<img src="${onSaleTender.tender.p2pAdvertisement.path}" alt="产品的名字"/>
			</a>
			<h4><a href="#">${onSaleTender.tender.name}</a></h4>

			<div class="invest-row-1">特点:<span>名家出品,精美工艺</span></div>
			<div class="invest-row-2">截止日期:<span><date:date value="${onSaleTender.tender.dueDate}" style="yyyy年MM月dd日"/></span></div>
			<div class="invest-row-3">剩余数量:<span>${onSaleTender.tender.productResidue}</span>个</div>
		</div>
	</li>
</c:forEach>
