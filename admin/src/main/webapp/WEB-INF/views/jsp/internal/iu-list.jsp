<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/26/2015
  Time: 00:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf" />
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-cogs font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase">表格</span>
                    </div>
                    <div class="actions btn-set">
                        <button class="btn green-haze btn-circle"><i class="fa fa-plus"></i> 新增</button>
                        <div class="btn-group">
                            <a class="btn yellow btn-circle" href="javascript:;" data-toggle="dropdown">
                                <i class="fa fa-check-circle"></i> 批量操作 <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu pull-right">
                                <li>
                                    <a href="javascript:;">
                                        启用所选项 </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        禁用所选项 </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        删除所选项 </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                        <div class="row">
                            <div class="col-md-2 col-sm-2">
                                <div class="dataTables_length" id="sample_3_length">
                                    <label>每页显示
                                        <select name="sample_3_length" aria-controls="sample_3"
                                                class="form-control input-xsmall input-inline select2-offscreen"
                                                tabindex="-1" title="">
                                            <option value="10">10</option>
                                            <option value="30">30</option>
                                            <option value="100">100</option>
                                            <option value="-1">所有</option>
                                        </select> 条</label>
                                </div>
                            </div>
                            <div class="col-md-2 col-sm-2 col-xs-push-4">
                                <div class="dataTables_filter">
                                    <label>
                                        <input type="search" class="form-control input-small input-inline"
                                               placeholder="" aria-controls="sample_3">

                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-2 col-xs-push-4">
                                <button class="btn blue">
                                    搜索
                                </button>
                            </div>
                        </div>
                        <div class="table-scrollable productlist-box">
                            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                   id="sample_3" role="grid" aria-describedby="sample_3_info">
                                <thead>
                                <tr role="row">
                                    <th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                        <input type="checkbox">
                                    </th>
                                    <th class="sorting_asc" tabindex="0" aria-controls="sample_3" rowspan="1"
                                        colspan="1" aria-sort="ascending" aria-label="Username : activate to sort column ascending">
                                        序号
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label=" Email : activate to sort column ascending">
                                        级别
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label=" Email : activate to sort column ascending">
                                        昵称
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        账号
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        电话
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        最后登陆时间
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        最后登陆ip
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        状态
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="list">
                                <c:forEach var="iu" items="${internalUserPage.result}" varStatus="index">
                                    <tr class="gradeX odd" role="row">
                                        <td><input type="checkbox"></td>
                                        <td class="sorting_1">${iu.id}</td>
                                        <td><button class="btn btn-xs blue">${iu.role}</button></td>
                                        <td><a class="link-name" href="#">${iu.name}</a></td>
                                        <td><a class="link-name" href="#">${iu.login}</a></td>
                                        <td>${iu.phone}</td>
                                        <td>${iu.lastLoginDate}</td>
                                        <td>${iu.ip}</td>
                                        <td><button class="btn btn-xs gray btn-circle">${iu.available}</button></td>
                                        <td><button class="btn btn-xs blue btn-circle">修改</button>
                                            <button class="btn btn-xs yellow btn-circle">删除</button></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="row">
                            <div class="col-xs-3 col-sm-3">
                                <div class="dataTables_info" id="sample_3_info" role="status" aria-live="polite">
                                    第
                                    <span>${internalUserPage.firstRecordIndex}</span>
                                    条 到 第
                                    <span>${internalUserPage.lastRecordIndex}</span>
                                    条 共
                                    <span>${internalUserPage.count}</span>
                                    条
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>跳转到第
                                    <input type="search" class="form-control input-small input-inline"
                                           placeholder="" aria-controls="sample_3">
                                    页
                                </label>
                            </div>
                            <div class="col-xs-6 col-sm-6">
                                <div class="dataTables_paginate paging_simple_numbers" id="sample_3_paginate">
                                    <ul class="pagination">
                                        <c:if test="${internalUserPage.currentIndex gt 0}">
                                            <li class="paginate_button previous disabled" aria-controls="sample_3" tabindex="0" id="sample_3_previous"><a href="#"><i class="fa fa-angle-left"></i></a></li>
                                        </c:if>
                                        <c:forEach var="pageNum" items="${internalUserPage.pageNumbers}">
                                            <li class="paginate_button ${pageNum eq internalUserPage.currentIndex ? 'active' : ''}"
                                                aria-controls="sample_3" tabindex="0"><a href="#">${pageNum + 1}</a></li>
                                        </c:forEach>
                                        <c:if test="${internalUserPage.currentIndex lt internalUserPage.maxIndex}">
                                            <li class="paginate_button next" aria-controls="sample_3" tabindex="0" id="sample_3_next"><a href="#"><i class="fa fa-angle-right"></i></a></li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</pgt:container>