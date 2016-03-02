<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/2/16
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="totalPage" value="${paginationBean.totalPage}"/>
<c:set var="currentPage" value="${paginationBean.currentPage}"/>
<div class="page-box">
	<ol>
		<c:choose>
			<c:when test="${paginationBean.currentPage<6}">
				<c:choose>
					<c:when test="${totalPage>7}">
						<c:forEach begin="1" end="7" var="current">
							<li><a href="javascript:void(0);" class="<c:if test="${current==currentPage}">current</c:if>">${current}</a></li>
						</c:forEach>
						<li>...</li>
					</c:when>
					<c:otherwise>
						<c:forEach begin="1" end="${totalPage}" var="current">
							<li><a href="javascript:void(0);" class="<c:if test="${current==currentPage}">current</c:if>">${current}</a></li>
						</c:forEach>
						<li>...</li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${totalPage-currentPage<3}">
				<li><a href="javascript:void(0);">1</a></li>
				<li><a href="javascript:void(0);">2</a></li>
				<li>...</li>
				<li><a href="javascript:void(0);">${totalPage-4}</a></li>
				<li><a href="javascript:void(0);">${totalPage-3}</a></li>
				<li><a href="javascript:void(0);">${totalPage-2}</a></li>
				<li><a href="javascript:void(0);">${totalPage-1}</a></li>
				<li><a href="javascript:void(0);">${totalPage}</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="javascript:void(0);">1</a></li>
				<li><a href="javascript:void(0);">2</a></li>
				<li>...</li>
				<li><a href="javascript:void(0);">${currentPage-2}</a></li>
				<li><a href="javascript:void(0);">${currentPage-1}</a></li>
				<li><a href="javascript:void(0);">${currentPage}</a></li>
				<li><a href="javascript:void(0);">${currentPage+1}</a></li>
				<li><a href="javascript:void(0);">${currentPage+2}</a></li>
			</c:otherwise>
		</c:choose>
	</ol>
</div>
