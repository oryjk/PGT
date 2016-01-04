<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
  <link rel="stylesheet" href="<spring:url value="/resources/core/css/page.css"/>"/>
  <div class="col-xs-2">
    <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
      第
      <span>${paginationBean.sqlStartIndex + 1}</span>
      条 到 第
      <span>${paginationBean.sqlStartIndex + paginationBean.capacity  > paginationBean.totalAmount ? paginationBean.totalAmount : paginationBean.sqlStartIndex + paginationBean.capacity }</span>
      条 共
      <span>${paginationBean.totalAmount}</span>
      条
    </div>
  </div>
  <div class="col-xs-2">
    <div class="dataTables_length pgt-each-page">
      <label>每页显示
        <select name="sample_3_length" aria-controls="sample_3"
                class="form-control input-xsmall input-inline select2-offscreen"
                tabindex="-1" title="" id="changeCapacity">
          <option value="2" <c:if test="${paginationBean.capacity == 2}">selected</c:if> >5</option>
          <option value="3" <c:if test="${paginationBean.capacity == 3}">selected</c:if> >15</option>
          <option value="4" <c:if test="${paginationBean.capacity == 4}">selected</c:if>>20</option>
        </select> 条</label>
    </div>
  </div>
  <div class="col-md-4 col-sm-4">
    <div class="dataTables_paginate paging_simple_numbers pgt-page-box">
      <!-- 当前页需要增加active类,首页末页的禁用是增加disabled类-->
      <ul class="pagination">

        <li class="paginate_button previous<c:if test="${paginationBean.firstPage}"> disabled</c:if>" ><a href="#" <c:if test="${not paginationBean.firstPage}">class="pageable"</c:if>><i
                class="fa fa-angle-left"></i></a></li>
        <li class="paginate_button<c:if test="${paginationBean.firstPage}"> disabled</c:if>"><a
                href="#" <c:if test="${not paginationBean.firstPage}"> class="pageable"</c:if>>首页</a></li>
        <c:if test="${paginationBean.showLeftDots}">
          <li class="paginate_button disabled"><a
                  href="javascript:;">...</a></li>
        </c:if>
        <c:forEach var="leftSpan" items="${paginationBean.leftSpan}">
          <li class="paginate_button " ><a
                  href="#" data-index="${leftSpan}" class="pageable">${leftSpan + 1}</a></li>
        </c:forEach>
        <c:if test="${paginationBean.showCurrentPage}">
          <li class="paginate_button active"><a
                  href="#">${paginationBean.currentIndex + 1}</a></li>
        </c:if>
        <c:forEach var="rightSpan" items="${paginationBean.rightSpan}">
          <li class="paginate_button" data-index="${rightSpan}"><a
                  href="#" data-index="${rightSpan}" class="pageable">${rightSpan + 1}</a></li>
        </c:forEach>
        <c:if test="${paginationBean.showRightDots}">
        <li class="paginate_button disabled"><a
                href="javascript:;">...</a></li>
        </c:if>
        <li class="paginate_button<c:if test="${paginationBean.lastPage}" > disabled</c:if>" data-index="${paginationBean.maxIndex}"><a
                href="#" data-index="${paginationBean.maxIndex}" <c:if test="${not paginationBean.lastPage}" > class="pageable"</c:if>>末页</a></li>
        <li class="paginate_button next<c:if test="${paginationBean.lastPage}"> disabled</c:if>" data-index="${paginationBean.nextIndex}"><a href="#" data-index="${paginationBean.nextIndex}" <c:if test="${not paginationBean.lastPage}" > class="pageable"</c:if>><i class="fa fa-angle-right"></i></a></li>
      </ul>
    </div>
  </div>
  <div class="col-xs-2">
      <label>
        <input type="search" class="form-control input-xsmall input-inline" placeholder="第几页" id="jumpToIndex">
        <input type="submit" class="btn blue" value="跳转" id="jumpTo">
      </label>
  </div>
</div>