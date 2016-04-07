<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/29/2015
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Required url which usually contains query conditions to generate page button link --%>
<c:set var="paginationURL" value="${param.paginationURL}"/>

<link rel="stylesheet" href="${ctx}/resources/core/css/page.css"/>
<div class="col-xs-2">
    <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
        第
        <span>${pagination.firstRecordIndex}</span>
        条 到 第
        <span>${pagination.lastRecordIndex}</span>
        条 共
        <span>${pagination.count}</span>
        条
    </div>
</div>
<div class="col-xs-2">
    <div class="dataTables_length pgt-each-page">
        <label>每页显示
            <select name="capacity" aria-controls="sample_3"
                    class="form-control input-xsmall input-inline select2-offscreen" tabindex="-1" title="">
                <c:forEach var="capacity" items="${pagination.paginationCapacities}">
                    <option value="${capacity}" ${capacity eq pagination.capacity ? 'selected' : ''}>${capacity}</option>
                </c:forEach>
                <option value="${pagination.paginationCapacityAll}" ${pagination.paginationCapacityAll eq pagination.capacity ? 'selected' : ''}>
                    所有
                </option>
            </select> 条</label>
    </div>
</div>
<div class="col-md-4 col-sm-4">
    <div class="dataTables_paginate paging_simple_numbers pgt-page-box">
        <ul class="pagination">
            <c:choose>
                <c:when test="${pagination.currentIndex gt 0}">
                    <li class="paginate_button previous" aria-controls="sample_3" tabindex="0" id="sample_3_previous">
                        <a href="${paginationURL}&capacity=${pagination.capacity}&currentIndex=${pagination.currentIndex - 1}">
                            <i class="fa fa-angle-left"></i></a></li>
                    <li class="paginate_button"><a
                            href="${paginationURL}&capacity=${pagination.capacity}&currentIndex=0">首页</a></li>
                </c:when>
                <c:otherwise>
                    <li class="paginate_button previous disabled" aria-controls="sample_3" tabindex="0"
                        id="sample_3_previous">
                        <a href="#"><i class="fa fa-angle-left"></i></a></li>
                    <li class="paginate_button disabled"><a href="#">首页</a></li>
                </c:otherwise>
            </c:choose>
            <c:forEach var="pageNum" items="${pagination.pageNumbers}">
                <c:choose>
                    <c:when test="${pageNum eq pagination.currentIndex}">
                        <li class="paginate_button active" aria-controls="sample_3" tabindex="0"><a
                                href="#">${pageNum + 1}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="paginate_button" aria-controls="sample_3" tabindex="0">
                            <a href="${paginationURL}&capacity=${pagination.capacity}&currentIndex=${pageNum}">${pageNum + 1}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${pagination.currentIndex lt pagination.maxIndex}">
                    <li class="paginate_button"><a
                            href="${paginationURL}&capacity=${pagination.capacity}&currentIndex=${pagination.maxIndex}">末页</a>
                    </li>
                    <li class="paginate_button next" aria-controls="sample_3" tabindex="0" id="sample_3_next">
                        <a href=${paginationURL}&capacity=${pagination.capacity}&currentIndex=${pagination.currentIndex + 1}">
                            <i class="fa fa-angle-right"></i></a></li>
                </c:when>
                <c:otherwise>
                    <li class="paginate_button disabled"><a href="#">末页</a></li>
                    <li class="paginate_button next disabled" aria-controls="sample_3" tabindex="0" id="sample_3_next">
                        <a href="#"><i class="fa fa-angle-right"></i></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<div class="col-xs-2">
    <form class="dataTables_filter pgt-goto-page">
        <label>
            <input name="currentPage" type="search" class="form-control input-xsmall input-inline" placeholder="第几页"
                   value="${pagination.currentIndex + 1}">
            <input type="submit" class="btn blue" value="跳转">
        </label>
    </form>
</div>