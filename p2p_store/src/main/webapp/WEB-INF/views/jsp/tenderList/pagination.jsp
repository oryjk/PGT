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
			<li class="page-item"><a href="/tender/tenderList?page=${currentPage-1}" class="pre-page">上一页</a></li>
		</c:if>

		<c:choose>
			<c:when test="${paginationBean.currentPage<6}">
				<c:choose>
					<c:when test="${totalPage>7}">
						<c:forEach begin="1" end="7" var="current">
							<li class="page-item">
								<c:choose>
									<c:when test="${current==currentPage}">
										<a href="/tender/tenderList?page=${current}" class="<c:if test="${current==currentPage}">page-link page-current</c:if>"
										   data-value="${current}">${current}</a>
									</c:when>
									<c:otherwise>
										<a href="/tender/tenderList?page=${current}" class="page-link" data-value="${current}">${current}</a>
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
										<a href="/tender/tenderList?page=${current}" class="<c:if test="${current==currentPage}">page-link page-current</c:if>"
										   data-value="${current}">${current}</a>
									</c:when>
									<c:otherwise>
										<a href="/tender/tenderList?page=${current}" class="page-link" data-value="${current}">${current}</a>
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
				<li class="page-item"><a class="page-link" class="page-link" href="/tender/tenderList?page=1" data-value="1">1</a></li>
				<li class="page-item"><a class="page-link" class="page-link" href="/tender/tenderList?page=2" data-value="2">2</a></li>
				<li class="page-item"><span class="page-omit">...</span></li>
				</li>
				<li class="page-item"><a href="/tender/tenderList?page=${totalPage-4}" class="page-link" data-value="${totalPage-4}">${totalPage-4}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${totalPage-3}" data-value="${totalPage-3}">${totalPage-3}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${totalPage-2}" class="page-link" data-value="${totalPage-2}">${totalPage-2}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${totalPage-1}" class="page-link" data-value="${totalPage-1}">${totalPage-1}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${totalPage}" class="page-link" data-value="${totalPage}">${totalPage}</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a href="/tender/tenderList?page=1" class="page-link" data-value="1">1</a></li>
				<li class="page-item"><a  href="/tender/tenderList?page=1" class="page-link" data-value="2">2</a></li>
				<li class="page-item"><span class="page-omit">...</span></li>
				</li>
				<li class="page-item"><a href="/tender/tenderList?page=${currentPage-2}" class="page-link" data-value="${currentPage-2}">${currentPage-2}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${currentPage-1}" class="page-link" data-value="${currentPage-1}">${currentPage-1}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${currentPage}" data-value="${currentPage}">${currentPage}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${currentPage+1}" class="page-link" data-value="${currentPage+1}">${currentPage+1}</a></li>
				<li class="page-item"><a href="/tender/tenderList?page=${currentPage+2}" class="page-link" data-action="${currentPage+2}">${currentPage+2}</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${currentPage!=totalPage}">
			<li class="page-item"><a href="/tender/tenderList?page=${currentPage+1}" class="next-page">下一页</a></li>
		</c:if>
	</ol>
	<div class="page-info">
		<span>${currentPage}</span>
		<span>/</span>
		<span>${totalPage}</span>
	</div>
	<div class="page-goto">
		<form action="/tender/tenderList" method="get">
			<input type="hidden" name="keyword" value="${param.keyword}">
			<input type="hidden" name="sort" value="${param.sort}">
			<input type="hidden" name="tenderFilter" value="${param.tenderFilter}">
			<span>跳转至</span>
			<input class="page-which" name="page" type="text"/>
			<span>页</span>
			<input class="page-goto-btn" type="submit" value="跳转"/>
		</form>
	</div>
</div>

<script>
	var filter_obj = ""
	<c:if test="${not empty param.keyword}">
		+ "&keyword=${param.keyword}"
	</c:if>
	<c:if test="${not empty param.sort}">
		+ "&sort=${param.sort}"
	</c:if>
	<c:if test="${not empty param.tenderFilter}">
		+ "&tenderFilter=${param.tenderFilter}"
	</c:if>
</script>