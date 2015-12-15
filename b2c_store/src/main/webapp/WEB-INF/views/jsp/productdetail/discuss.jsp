<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<ul class="talking-list">
	<c:forEach items="${discussList}" var="discuss">
		<li>
			<div class="talking-name">${discuss.user.username}</div>

			<div class="talking-text">
				<p>${discuss.content}</p>
			</div>
			<div class="talking-time">
				<fmt:formatDate value="${discuss.createDate}" dateStyle="long" />
			</div>
		</li>
	</c:forEach>
</ul>
<div class="page-box">

     <input id="totalPage_discuss" type="hidden"
						value="${disPaginationBean.totalPage}">
    <input id="capacity_discuss"   type="hidden" value="${disPaginationBean.capacity}">
<c:if test="${disPaginationBean.totalAmount !=0}">
	<ul>
		
		<c:if test="${disPaginationBean.currentPage!=1}">   
		<li><a 
			onclick="gotoPageNumDiss('${disPaginationBean.currentPage-1}')">上页</a></li>
		</c:if>
		
		<li class="page-list">
			<ol>

				<c:forEach var="i" begin="${disPaginationBean.startPageIndex}"
					end="${disPaginationBean.endPageIndex}">

					<li><a id="pagediscuss"  <c:if test="${disPaginationBean.currentPage==i}">class="page-focus"</c:if>  onClick="gotoPageNumDiss('${i}')">${i}</a></li>

				</c:forEach>

			</ol>
		</li>
      <c:if test="${disPaginationBean.currentPage!=disPaginationBean.endPageIndex}"> 
		<li>
		<a 
			onclick="gotoPageNumDiss('${disPaginationBean.currentPage+1}')">下页</a></li>
	   </c:if>
	     
		<li class="page-count">共<span>${disPaginationBean.totalPage}</span>页
		</li>
		<li class="page-which">跳转到第<input type="text" id="page_discuss">页
		</li>
		<li><input type="button" value="确认" onclick="gotoPageNumDiss()"></li>
	</ul>
</c:if>
</div>
<div class="talking-total">
	<p>
		共有 <span>${disPaginationBean.totalAmount}</span> 条咨询
	</p>
</div>
<div class="talking-area">
	<form action="" id="createDisuss" method="post">
		<input type="hidden" id="discussprodcuctId" name="productId" value="${product['productId']}">
		<h3>发表讨论</h3>
		<p>
			<textarea cols="100" rows="3" name="content" id="contentDiscuss"></textarea>
		</p>
		<p>
			<input type="button" value="发布" onclick="createDiscuss()" />
		</p>
	</form>

	<form id="discussredirect" method="post" action="../user/login">
		<input type="hidden" id="redirect" name="redirect" value="">
	</form>

</div>