<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="totalPage" value="${paginationBean.totalPage}"/>
<c:set var="currentPage" value="${paginationBean.currentPage}"/>
<div class="pager">
	<ol class="page-list">
		<c:if test="${currentPage!=1}">
			<li class="page-item"><a href="javascript:void(0);" class="pre-page">上一页</a></li>
		</c:if>

		<c:choose>
			<c:when test="${paginationBean.currentPage<6}">
				<c:choose>
					<c:when test="${totalPage>7}">
						<c:forEach begin="1" end="7" var="current">
							<li class="page-item">
								<c:choose>
									<c:when test="${current==currentPage}">
										<a href="javascript:void(0);" class="<c:if test="${current==currentPage}">page-link page-current</c:if>"
										   data-value="${current}">${current}</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" class="page-link" data-value="${current}">${current}</a>
									</c:otherwise>
								</c:choose>
							</li>
						</c:forEach>
						<li class="page-item"><span class="page-omit">...</span></li>
						</li>
					</c:when>
					<c:otherwise>
						<c:forEach begin="1" end="${totalPage}" var="current">
							<li>
								<c:choose>
									<c:when test="${current==currentPage}">
										<a href="javascript:void(0);" class="<c:if test="${current==currentPage}">page-link page-current</c:if>"
										   data-value="${current}">${current}</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" class="page-link" data-value="${current}">${current}</a>
									</c:otherwise>
								</c:choose>
							</li>
						</c:forEach>
						<li class="page-item"><span class="page-omit">...</span></li>
						</li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${totalPage-currentPage<3}">
				<li class="page-item"><a class="page-link" class="page-link" href="javascript:void(0);" data-value="1">1</a></li>
				<li class="page-item"><a class="page-link" class="page-link" href="javascript:void(0);" data-value="2">2</a></li>
				<li class="page-item"><span class="page-omit">...</span></li>
				</li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${totalPage-4}">${totalPage-4}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${totalPage-3}">${totalPage-3}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${totalPage-2}">${totalPage-2}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${totalPage-1}">${totalPage-1}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${totalPage}">${totalPage}</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="1">1</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="2">2</a></li>
				<li class="page-item"><span class="page-omit">...</span></li>
				</li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${currentPage-2}">${currentPage-2}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${currentPage-1}">${currentPage-1}</a></li>
				<li class="page-item"><a class="current-page" href="javascript:void(0);" data-value="${currentPage}">${currentPage}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-value="${currentPage+1}">${currentPage+1}</a></li>
				<li class="page-item"><a href="javascript:void(0);" class="page-link" data-action="${currentPage+2}">${currentPage+2}</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${currentPage!=totalPage}">
			<li class="page-item"><a href="javascript:void(0);" class="next-page">下一页</a></li>
		</c:if>
	</ol>
	<div class="page-info">
		<span>${currentPage}</span>
		<span>/</span>
		<span>${totalPage}</span>
	</div>
	<div class="page-goto">
		<span>跳转至</span>
		<input class="page-which" type="text"/>
		<span>页</span>
		<input class="page-goto-btn" type="button" value="跳转"/>
	</div>
</div>