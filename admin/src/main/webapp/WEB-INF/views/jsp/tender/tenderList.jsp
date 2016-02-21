<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/25/15
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<admin:container id="tenderList" pageJsPath="/resources/product/product-list.js">
    <c:set value="${paginationBean.currentIndex}" var="currentIndex"/>
    <c:set value="${paginationBean.maxIndex}" var="maxIndex"/>
    <div class="row">
        <div class="col-xs-12">
            <ul class="page-breadcrumb breadcrumb">
                <li>
                    <a href="/">首页</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li>
                    <a href="">当铺投资管理</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li class="active">
                    <a href="#">投资列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-cogs font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase">表格</span>
                    </div>
                    <div class="actions btn-set">
                        <button class="btn green-haze btn-circle" data-pgt-btn="create" data-url="/tender/createUI"><i
                                class="fa fa-plus"></i> 新增
                        </button>
                        <div class="btn-group">
                            <a class="btn yellow btn-circle" href="javascript:;" data-toggle="dropdown">
                                <i class="fa fa-check-circle"></i> 批量操作 <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu pull-right">

                                <li class="divider">
                                <li>
                                    <a href="javascript:;">
                                        删除所选项 </a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        打印所选项 </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                        <div class="row">
                            <div class="col-xs-2">
                                <div class="dataTables_length">
                                    <label>主分类
                                        <c:set var="rootCategoryId"
                                               value="${categoryHierarchy.parentCategory.categoryId}"/>
                                        <select name="sample_3_length" aria-controls="sample_3"
                                                class="form-control input-small input-inline select2-offscreen"
                                                id="mainCategory" tabindex="-1" title="">
                                            <option value="">全部</option>
                                            <c:forEach items="${categories}" var="category">
                                                <c:choose>
                                                    <c:when test="${rootCategoryId == category.id}">
                                                        <option value="${category.id}"
                                                                selected="true">${category.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${category.id}">${category.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select></label>
                                </div>
                            </div>
                            <div class="col-xs-2">
                                <div class="dataTables_length">
                                    <label>次分类
                                        <c:set var="categoryId" value="${categoryHierarchy.categoryId}"/>
                                        <select name="sample_3_length" aria-controls="sample_3"
                                                class="form-control input-small input-inline select2-offscreen"
                                                id="viceCategory" tabindex="-1" title="">
                                            <option value="5">全部</option>
                                            <c:forEach items="${subCategories}" var="subCategory">
                                                <c:choose>
                                                    <c:when test="${subCategory.id == categoryId}">
                                                        <option value="${subCategory.id}"
                                                                selected="true">${subCategory.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${subCategory.id}">${subCategory.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select> </label>
                                </div>
                            </div>
                            <div class="col-md-2 col-sm-2">
                                <div class="dataTables_filter">
                                    <label>名称:<input id="term" type="search" value="${term}"
                                                     class="form-control input-small input-inline"
                                                     placeholder="" aria-controls="sample_3">
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-2 col-sm-2">
                                <button id="searchBtn" class="btn blue">
                                    搜索
                                </button>
                            </div>
                        </div>

                        <div class="table-scrollable list-box">
                            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                   id="list" role="grid" aria-describedby="sample_3_info">
                                <thead>
                                <tr role="row">
                                    <th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                        <input id="checkAll" type="checkbox">
                                    </th>
                                    <th class="sorting_asc" tabindex="0" aria-controls="sample_3" rowspan="1"
                                        colspan="1" aria-sort="ascending"
                                        aria-label="Username : activate to sort column ascending">
                                        序号
                                    </th>
                                    <th>
                                        投资名称
                                    </th>
                                    <th>
                                        当铺编号
                                    </th>
                                    <th>
                                        投资总金额
                                    </th>
                                    <th>
                                        可以投资的份数
                                    </th>
                                    <th>
                                        开标时间
                                    </th>
                                    <th>
                                        截至时间
                                    </th>
                                    <th>
                                        收益率
                                    </th>
                                    <th>
                                        投资创建日期
                                    </th>

                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${tenderList}" var="tender">
                                    <tr class="gradeX odd" role="row">
                                        <td>
                                            <input type="checkbox">
                                        </td>
                                        <td class="sorting_1">
                                                ${tender.tenderId}
                                        </td>
                                        <td>
                                                ${tender.name}
                                        </td>
                                        <td>
                                                ${tender.pawnTicketId}
                                        </td>
                                        <td>
                                                ${tender.tenderTotal}
                                        </td>
                                        <td>
                                                ${tender.tenderQuantity}
                                        </td>
                                        <td>
                                                ${tender.publishDate}

                                        </td>
                                        <td>
                                                ${tender.dueDate}
                                        </td>
                                        <td>
                                                ${tender.interestRate}
                                        </td>
                                        <td>
                                                ${tender.creationDate}
                                        </td>
                                        <td>
                                            <button class="btn btn-xs green btn-circle" data-pgt-btn="modify"
                                                    data-url="/tender/updateUI/${tender.tenderId}">
                                                修改
                                            </button>
                                            <button class="btn btn-xs red btn-circle" data-pgt-btn="delete"
                                                    data-url="/tender/delete/${tender.tenderId}">
                                                删除
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>

                        <div class="row">
                            <link rel="stylesheet" href="/resources/core/css/page.css"/>
                            <div class="col-xs-2">
                                <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status"
                                     aria-live="polite">
                                    第
                                    <span>${paginationBean.sqlStartIndex+1}</span>
                                    条 到 第
                                    <span>${paginationBean.sqlStartIndex+fn:length(tenderList)}</span>
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
                                                tabindex="-1" title="">
                                            <option value="5">5</option>
                                            <option value="15">15</option>
                                            <option value="20">20</option>
                                        </select> 条</label>
                                </div>
                            </div>
                            <div class="col-md-4 col-sm-4">
                                <div class="dataTables_paginate paging_simple_numbers pgt-page-box">
                                    <!-- 当前页需要增加active类,首页末页的禁用是增加disabled类-->
                                    <ul class="pagination" id="pagination">

                                        <li class="paginate_button"><a
                                                href="/tender/tenderList?currentIndex=0">首页</a></li>
                                        <c:choose>
                                            <c:when test="${paginationBean.maxIndex>5}">
                                                <c:if test="${paginationBean.currentIndex>2 and paginationBean.currentIndex<paginationBean.maxIndex-3}">
                                                    <li class="paginate_button disabled">
                                                        <a href="javascript:;">...</a>
                                                    </li>
                                                    <li class="paginate_button ">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex-2}">${currentIndex-1}</a>
                                                    </li>
                                                    <li class="paginate_button ">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex-1}">${currentIndex}</a>
                                                    </li>
                                                    <li class="paginate_button active">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex}">${currentIndex+1}</a>
                                                    </li>
                                                    <li class="paginate_button">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
                                                    </li>
                                                    <li class="paginate_button">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
                                                    </li>
                                                    <li class="paginate_button disabled">
                                                        <a href="javascript:;">...</a>
                                                    </li>
                                                </c:if>
                                                <c:if test="${paginationBean.currentIndex<3}">

                                                    <c:forEach var="current" begin="1" end="${currentIndex+1}">
                                                        <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                            <a href="/tender/tenderList?currentIndex=${current-1}">${current}</a>
                                                        </li>
                                                    </c:forEach>
                                                    <li class="paginate_button">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
                                                    </li>
                                                    <li class="paginate_button">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
                                                    </li>
                                                    <li class="paginate_button disabled">
                                                        <a href="javascript:;">...</a>
                                                    </li>
                                                </c:if>
                                                <c:if test="${paginationBean.currentIndex+4>paginationBean.maxIndex}">
                                                    <li class="paginate_button disabled">
                                                        <a href="javascript:;">...</a>
                                                    </li>
                                                    <li class="paginate_button">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex-2}">${currentIndex-2}</a>
                                                    </li>
                                                    <li class="paginate_button">
                                                        <a href="/tender/tenderList?currentIndex=${currentIndex-1}">${currentIndex-1}</a>
                                                    </li>
                                                    <c:forEach var="current" begin="${currentIndex+1}"
                                                               end="${maxIndex+1}">
                                                        <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                            <a href="/tender/tenderList?currentIndex=${current-1}">${current}</a>
                                                        </li>
                                                    </c:forEach>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="current" begin="1" end="${paginationBean.maxIndex+1}">

                                                    <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                        <a href="/tender/tenderList?currentIndex=${current-1}">${current}</a>
                                                    </li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>


                                        <li class="paginate_button"><a
                                                href="/tender/tenderList?currentIndex=${paginationBean.maxIndex}">末页</a>
                                        </li>

                                    </ul>
                                </div>
                            </div>
                            <div class="col-xs-2">
                                <form class="dataTables_filter pgt-goto-page" action="/tender/tenderList" method="get">
                                    <label>
                                        <input type="search" value="${currentIndex+1}" name="currentIndex"
                                               class="form-control input-xsmall input-inline" placeholder="第几页">
                                        <input type="submit" class="btn blue pgt-goto-page-btn" value="跳转">
                                    </label>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
</admin:container>

