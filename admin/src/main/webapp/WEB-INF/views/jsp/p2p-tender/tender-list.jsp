<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<admin:container id="tenderList" pageJsPath="/resources/category/category-list.js">
    <c:set value="${paginationBean.currentIndex}" var="currentIndex"/>
    <c:set value="${paginationBean.maxIndex}" var="maxIndex"/>

            <div class="row">
                <div class="col-xs-12">
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <a href="#">首页</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">在当标列表</a>
                            <i class="fa fa-circle"></i>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- super:把错误内容放在span里面,有两种提示框 alert-danger 和 alert-success 两种.如果不需要显示时把display改为none-->
            <div class="row" style="display: none">
                <div class="col-xs-12">
                    <div class="Metronic-alerts alert alert-danger fade in">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                        <p>错误信息</p>
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
                                <button class="btn green-haze btn-circle" onclick="javascript:window.location.href='/tender/create'"><i class="fa fa-plus"></i> 新增</button>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                                <div class="row">
                                    <div class="col-md-2 col-sm-2">
                                        <div class="dataTables_filter">
                                            <label>ID:
                                                <input
                                                       class="form-control input-small input-inline" placeholder="订单ID"
                                                       aria-controls="sample_3"/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-2 col-sm-2">
                                        <div class="dataTables_filter">
                                            <label>标名:
                                                <input
                                                       class="form-control input-small input-inline" placeholder=""
                                                       aria-controls="sample_3">
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <div class="dataTables_filter">
                                            <label>金额:
                                                <input
                                                       class="form-control input-xsmall input-inline"
                                                       placeholder="最低" aria-controls="sample_3"/>
                                                -
                                                <input
                                                       class="form-control input-xsmall input-inline"
                                                       placeholder="最高" aria-controls="sample_3"/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <div class="dataTables_filter">
                                            <label>状态:
                                                <select name="sample_3_length" aria-controls="sample_3"
                                                        class="form-control input-small input-inline select2-offscreen"
                                                        tabindex="-1" title="">
                                                    <option value="-1">所有</option>
                                                    <option value="5">状态1</option>
                                                    <option value="15">状态1</option>
                                                    <option value="20">状态1</option>
                                                </select>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-6">
                                        <div class="dataTables_filter">
                                            <div class="row">
                                                <div class="col-xs-1 pgt-time-tittle">
                                                    <span>时间</span>
                                                </div>
                                                <div class="col-xs-4 pgt-time-tittle">
                                                    <select
                                                            class="form-control input-small input-inline select2-offscreen"
                                                            tabindex="-1" title="">
                                                        <option value="-1">发布时间</option>
                                                        <option value="5">开始时间</option>
                                                        <option value="15">结束时间</option>
                                                    </select>
                                                </div>
                                                <div class="col-xs-3 pgt-begin-date" style="position: relative">
                                                    <input
                                                           class="jcDate jcDateIco form-control input-small input-inline" maxlength="16"
                                                           onfocus="$(this).calendar()">
                                                </div>
                                                <div class="col-xs-1 pgt-date-divide">
                                                    <span style="padding-left: 30px;">至</span>
                                                </div>
                                                <div class="col-xs-3">
                                                    <input
                                                           class="jcDate jcDateIco form-control input-small input-inline" maxlength="16"
                                                           onfocus="$(this).calendar()">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-1">
                                        <div class="dataTables_filter">
                                            <label>网站热门:
                                                <input type="checkbox" name="" id=""/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-1">
                                        <div class="dataTables_filter">
                                            <label>分类热门:
                                                <input type="checkbox" name="" id=""/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="dataTables_filter">
                                            <label>切换有/无子项投资:
                                                <input type="checkbox" name="" id=""/>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col-xs-1">
                                        <input type="submit" class="btn blue" value="搜索"/>
                                    </div>
                                </div>
                                <div class="table-scrollable list-box">
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer" style="table-layout: fixed; width: 1370px;">
                                        <thead>
                                        <tr role="row">
                                            <th>
                                                序号
                                            </th>
                                            <th style="width: 120px;">
                                                名字
                                            </th>
                                            <th>
                                                分类
                                            </th>
                                            <th style="white-space: normal; width:60px;">
                                                所属典当行
                                            </th>
                                            <th style="white-space: normal; width:60px;">
                                                当铺所有者
                                            </th>
                                            <th style="white-space: normal; width:60px;">
                                                当票编号
                                            </th>
                                            <th style="width: 80px;">
                                                总金额
                                            </th>

                                            <th style="white-space: normal; width:50px;">
                                                产品数量
                                            </th>

                                            <th style="white-space: normal; width:60px;">
                                                发布时间
                                            </th>
                                            <th style="white-space: normal; width:60px;">
                                                开标时间
                                            </th>
                                            <th  style="white-space: normal; width:60px;">
                                                截止时间
                                            </th>
                                            <th  style="white-space: normal; width:60px;">
                                                准备计息天数
                                            </th>
                                            <th  style="white-space: normal; width:50px;">
                                                无息天数
                                            </th>
                                            <th  style="white-space: normal; width:50px;">
                                                手续费率
                                            </th>
                                            <th  style="white-space: normal; width:50px;">
                                                分类热门
                                            </th>
                                            <th  style="white-space: normal; width:50px;">
                                                网站热门
                                            </th>
                                            <th>
                                                状态
                                            </th>
                                            <th  style="white-space: normal; width:120px;">
                                                操作
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody id="list">

                                        <c:forEach items="${tenderList}" var="tender">
                                        <tr class="gradeX odd" role="row">
                                            <td  style="word-break: break-all;">
                                                    ${tender.tenderId}
                                            </td>
                                            <td>
                                                    ${tender.name}
                                            </td>
                                            <td>
                                                    ${tender.category.name}
                                            </td>
                                            <td>
                                                    ${tender.pawnshop.name}
                                            </td>
                                            <td>
                                                王睿
                                            </td>
                                            <td style="word-break: break-all;">
                                                    ${tender.pawnTicketId}
                                            </td>
                                            <td>
                                                    ${tender.tenderTotal}
                                            </td>
                                            <td>
                                                    ${fn:length(tender.products)}
                                            </td>
                                            <td>

                                                <fmt:formatDate value="${tender.creationDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${tender.publishDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${tender.dueDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </td>
                                            <td>
                                                ${tender.prePeriod}
                                            </td>
                                            <td>
                                                ${tender.postPeriod}
                                            </td>
                                            <td>
                                                ${tender.handlingFeeRate}
                                            </td>
                                            <td>
                                                <c:if test="${tender.categoryHot==false}">否</c:if>
                                                <c:if test="${tender.categoryHot==true}">是</c:if>
                                            </td>
                                            <td>
                                                <c:if test="${tender.siteHot==false}">否</c:if>
                                                <c:if test="${tender.siteHot==true}">是</c:if>
                                            </td>
                                            <td>
                                                <c:if test="${tender.state==1}">启用</c:if>
                                                <c:if test="${tender.state==0}">禁止</c:if>
                                            </td>
                                            <td>
                                                <p>
                                                    <button class="btn btn-xs blue btn-circle" onclick="window.location.href='/tender/queryTenderById/${tender.tenderId}'">查看</button>
                                                    <button class="btn btn-xs yellow btn-circle"  onclick="window.location.href='/tender/addProductStepBase?tenderId=${tender.tenderId}'">添加</button>
                                                </p>
                                                <p>
                                                    <button class="btn btn-xs green btn-circle" data-pgt-btn="modify"
                                                            data-url="/tender/updateUI/${tender.tenderId}">
                                                        修改
                                                    </button>
                                                    <button class="btn btn-xs red btn-circle" data-pgt-btn="delete"
                                                            data-url="/tender/delete/${tender.tenderId}">
                                                        删除
                                                    </button>
                                                </p>
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
        </div>
    </div>

</div>

</admin:container>
