<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<ul class="question-list">

	<c:forEach items="${consultings}" var="consulting">

		<li>

			<div class="question-time">
				<fmt:formatDate value="${consulting.createDate}" dateStyle="long" />
			</div>
			<p class="question-q">
				<span class="question-name">${consulting.user.username}</span>: <span
					class="question-content">${consulting.content}</span>
			</p> <c:forEach items="${consulting.childrens}" var="reply">
				<p class="question-a">
					<span class="question-name">点金子回复</span>: <span
						class="question-content">${reply.content}</span>
				</p>
			</c:forEach>
		</li>

	</c:forEach>


</ul>
     
<div class="page-box">
    
    
    <input id="endPageIndex_consulting" type="hidden" value="${conPaginationBean.endPageIndex}">
    <input id="capacity_consulting"     type="hidden" value="${conPaginationBean.capacity}">
    
	<c:if test="${conPaginationBean.totalAmount !=0}">  
	<ul>
	
	
	<c:if test="${conPaginationBean.currentPage!=1}">  
	
		<li><a 
			onclick="gotoPageNum('${conPaginationBean.currentPage-1}')">上页</a></li>
    </c:if>
    
		<li class="page-list">
			<ol>

				<c:forEach var="i" begin="${conPaginationBean.startPageIndex}"
					end="${conPaginationBean.endPageIndex}">

					<li><a  
						    <c:if test="${conPaginationBean.currentPage==i}">class="page-focus"</c:if>  onClick="gotoPageNum('${i}')">${i}</a></li>

				</c:forEach>

			</ol>
		</li>
		
		<c:if test="${conPaginationBean.currentPage!=conPaginationBean.endPageIndex}"> 
		<li><a 
			onclick="gotoPageNum('${conPaginationBean.currentPage+1}')">下页</a></li>
			</c:if>
		
		<li class="page-count">共<span>${conPaginationBean.totalPage}</span>页
		</li>
		<li class="page-which">跳转到第<input type="text" id="pageconsulting">页
		</li>
		<li><input type="button" value="确认" onclick="gotoPageNum()"></li>
	</ul>
</c:if>

</div>


<form action="../consulting/createconsulting" id="createConsulitng"
	method="post">
	<input type="hidden"  id="discussprodcuctId" name="productId" value="${product['productId']}" >
	<p>对商品有什么问题,请在这里咨询吧.</p>
	<p>
		共有 <span>${conPaginationBean.totalAmount}</span> 条咨询
	</p>
	<h3>发表咨询</h3>
	<p>
		<textarea cols="100" rows="3" name="content" id="contentConsulting"></textarea>
	</p>
	<p class="phone-area">
		<label for="">电话号码</label> <input type="text" name="phoneNumber"
			id="phoneNumber" />
	</p>
	<p>
		<!--  
		<input type="submit" value="匿名咨询" /> 
		<input type="submit" value="会员登陆" />
-->
		<input type="button" value="咨询" onclick="createConsulitng()" />
	</p>



</form>

<form id="consultingfrom" method="post" action="../user/login">
		<input type="hidden" id="consultingredirect" name="redirect">
</form>


