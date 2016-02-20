<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<div class="page-box">
	
	<c:if test="${commPaginationBean.totalAmount !=0}">
		<ul>

			<c:if test="${commPaginationBean.currentPage!=1}">

				<li><a
					onclick="gotoPageNum('${ commPaginationBean.currentPage-1}')">上页</a></li>
			</c:if>

			<li class="page-list">
				<ol>

					<c:forEach var="i" begin="${commPaginationBean.startPageIndex}"
						end="${ commPaginationBean.endPageIndex}">

						<li><a
							<c:if test="${commPaginationBean.currentPage==i}">class="page-focus"</c:if>
							onClick="gotoPageNum('${i}')">${i}</a></li>

					</c:forEach>

				</ol>
			</li>

			<c:if
				test="${commPaginationBean.currentPage!= commPaginationBean.endPageIndex}">
				<li><a
					onclick="gotoPageNum('${ commPaginationBean.currentPage+1}')">下页</a></li>
			</c:if>

			<li class="page-count">共<span>${commPaginationBean.totalPage}</span>页
			</li>
			<li class="page-which">跳转到第<input type="text"
				id="pageInputNum">页
			</li>
			<li><input class="d-btn" type="button" value="确认" onclick="gotoPageNum()"></li>
		</ul>
	</c:if>

</div>