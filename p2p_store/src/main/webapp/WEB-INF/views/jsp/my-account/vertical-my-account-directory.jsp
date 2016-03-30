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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="menu">
	<div class="user-face">
		<div class="img-box"><img src="${userInfo.path}" alt=""/></div>
		<p class="face-title">${currentUser.username}</p>
	</div>
	<ul class="menu-list">
		<c:forEach items="${navigationList}" var="currentItem">
			<li><a href="${currentItem.url}" class="${currentItem.label==currentAccountItem.label?'menu-choose':''}">${currentItem.label}</a></li>
		</c:forEach>
	</ul>
	<div class="menu-two-dimension">
		<img src="" alt=""/>

		<p>扫一扫,淘在当手机版</p>
	</div>
</div>
