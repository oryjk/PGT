<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<c:set var="currentPage" value="${empty param.currentPage ? 1 : param.currentPage}"/>
<c:set var="maxPageNum" value="${paginationBean.maxPageNum}"/>
<pgt:container id="main">
	<div class="page-content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <a href="#">首页</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">目录管理</a>
                            <i class="fa fa-circle"></i>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- super:把错误内容放在span里面,有两种提示框 alert-danger 和 alert-success 两种.如果不需要显示时把display改为none-->
            <div class="row" style="display: block">
                <div class="col-xs-12">
                    <div class="Metronic-alerts alert alert-danger fade in">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                        <p><form:errors path="nameError"/></p>
                    </div>
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
                                <a class="btn green-haze btn-circle" href="addCategory"><i class="fa fa-plus"></i> 新增</a>
                                <div class="btn-group" style="display:none">
                                    <a class="btn yellow btn-circle" href="javascript:;" data-toggle="dropdown">
                                        <i class="fa fa-check-circle"></i> 批量操作 <i class="fa fa-angle-down"></i>
                                    </a>
                                    <ul class="dropdown-menu pull-right">
                                        <li>
                                            <a href="javascript:;">
                                                启用分类 </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                禁用分类 </a>
                                        </li>
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
                                <div class="table-scrollable productlist-box">
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                           id="list" role="grid" aria-describedby="sample_3_info">
                                        <thead>
                                        <tr role="row">
                                            <th>
                                                位置
                                            </th>
                                            <th>
                                                目录id
                                            </th>
                                            <th>
                                                目录名称
                                            </th>
                                            <th>
                                                文章数量
                                            </th>
                                            <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1">
                                                <div class="btn-group">
                                                    <a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="">
                                                        状态 <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu pull-right">
                                                        <li>
                                                            <a href="javascript:;" data-pgt-value="1"  data-pgt-dropdown="status-option">
                                                                启用 </a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:;" data-pgt-value="0"  data-pgt-dropdown="status-option">
                                                                禁用 </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </th>
                                            <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                aria-label="Status : activate to sort column ascending">
                                                操作
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${helpCategorVoList }" var="helpCategorVo">
	                                        <tr class="gradeX odd" role="row">
	                                            <td>
	                                                <input class="pgt-sort-num" type="text" value="1"/>
	                                            </td>
	                                            <td>${helpCategorVo.category.id }</td>
	                                            <td>${helpCategorVo.category.name }</td>
	                                            <td> ${fn:length(helpCategorVo.helpCenterList)}</td>
	                                            <td>
	                                                <div class="btn-group">
	                                                    <a class="btn btn-xs blue btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="1">
	                                                        ${helpCategorVo.category.status == 0 ? '禁用' : '启用'} <i class="fa fa-angle-down"></i>
	                                                    </a>
	                                                    <ul class="dropdown-menu pull-right">
	                                                        <li>
	                                                            <a href="javascript:;" data-pgt-value="0" data-pgt-dropdown="status-option">
	                                                                禁用 </a>
	                                                        </li>
	                                                        <li>
	                                                            <a href="javascript:;" data-pgt-value="1"  data-pgt-dropdown="status-option">
	                                                                启用 </a>
	                                                        </li>
	                                                    </ul>
	                                                </div>
	                                            </td>
	                                            <td>
	                                            <a class="btn btn-xs green btn-circle" href="updateCategory/${helpCategorVo.category.id}">修改</a>
	                                            <a class="btn btn-xs red btn-circle" href="deleteCategory/${helpCategorVo.category.id}">删除</a>
	                                            </td>
	                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="row">
                                    <link rel="stylesheet" href="${adminStaticPath}/core/css/page.css"/>
                                    <div class="col-xs-2">
                                        <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
                                            第
                                            <span>${paginationBean.currentIndex + 1}</span>
                                            条 到 第
                                            <span>${paginationBean.currentIndex + fn:length(helpCategorVoList)}</span>
                                            条 共
                                            <span>${paginationBean.totalAmount}</span>
                                            条
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="dataTables_length pgt-each-page">
                                            <label>每页显示
                                                <select id="capacityList" name="sample_3_length" aria-controls="sample_3"
                                                        class="form-control input-xsmall input-inline select2-offscreen"
                                                        tabindex="-1" title="">
                                                    <option value="2" ${paginationBean.capacity == 5 ? 'selected': ''}>5</option>
                                                    <option value="15" ${paginationBean.capacity == 15 ? 'selected': ''}>15</option>
                                                    <option value="20" ${paginationBean.capacity == 20 ? 'selected': ''}>20</option>
                                                    <option value="10000" ${empty paginationBean.capacity ? 'selected': ''}>所有</option>
                                                </select> 条</label>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-sm-4">
                                        <div class="dataTables_paginate paging_simple_numbers pgt-page-box">
                                            <!-- 当前页需要增加active类,首页末页的禁用是增加disabled类-->
                                            <ul class="pagination">
                                                <li class="paginate_button previous ${2 > currentPage ? 'disabled': ''}"><a href="#"><i
                                                        class="fa fa-angle-left"></i></a></li>
                                                <li class="paginate_button js-change-page" data-page="1"><a
                                                        href="#">首页</a></li>
                                                <c:if test="${currentPage > 3}">
                                                	 <li class="paginate_button disabled"><a
                                                        href="javascript:;">...</a></li>
                                                </c:if>
                                                <c:if test="${currentPage > 2}">
                                                	 <li class="paginate_button js-change-page" data-page="${currentPage - 2}"><a
                                                        href="#">${currentPage - 2}</a></li>
                                                </c:if>
                                                <c:if test="${currentPage > 1}">
                                                	 <li class="paginate_button js-change-page" data-page="${currentPage - 1}"><a
                                                        href="#">${currentPage - 1}</a></li>
                                                </c:if>
                                                <li class="paginate_button active"><a
                                                        href="#">${currentPage}</a></li>
                                                <c:if test="${maxPageNum - currentPage > 0}">
                                                	 <li class="paginate_button js-change-page" data-page="${currentPage + 1}"><a
                                                        href="#">${currentPage + 1}</a></li>
                                                </c:if>
                                                <c:if test="${maxPageNum - currentPage > 1}">
                                                	 <li class="paginate_button js-change-page" data-page="${currentPage + 2}"><a
                                                        href="#">${currentPage + 2}</a></li>
                                                </c:if>
                                                 <c:if test="${currentPage < maxPageNum - 2}">
                                                	 <li class="paginate_button disabled"><a
                                                        href="javascript:;">...</a></li>
                                                </c:if>
                                                <li class="paginate_button js-change-page" data-page="${maxPageNum}"><a
                                                        href="#">末页</a></li>
                                                <li class="paginate_button next ${maxPageNum > currentPage ? '': 'disabled'}"><a href="#"><i class="fa fa-angle-right"></i></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <form class="dataTables_filter pgt-goto-page" action="categoryList" method="get">
                                            <label>
                                                <input id="currentPage" type="search" name="currentPage" value="${param.currentPage}" class="form-control input-xsmall input-inline" placeholder="第几页">
                                                <input id="capacity" type="hidden" name="capacity" value="${paginationBean.capacity}"/>
                                                <input id="submitBtn" type="submit" class="btn blue" value="跳转">
                                            </label>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</pgt:container>
 <script src="${adminStaticPath}/help/category.js"></script>