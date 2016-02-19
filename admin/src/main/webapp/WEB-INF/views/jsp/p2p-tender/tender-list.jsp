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
            <div class="row" style="display: block">
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
                                <button class="btn green-haze btn-circle" onclick="javascript:window.location.href='/tender/createUI'"><i class="fa fa-plus"></i> 新增</button>
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
                                                    ${tender.categoty.name}
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
                                                12   ${tender.products}
                                            </td>

                                            <td>
                                                    ${tender.createDate}
                                            </td>
                                            <td>
                                                    ${tender.publishDate}
                                            </td>
                                            <td>
                                                    ${tender.dueDate}
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

                                                <c:if test="${categoryHot==false}">否</c:if>
                                                <c:if test="${categoryHot==true}">是</c:if>

                                            </td>
                                            <td>
                                                <c:if test="${siteHotsiteHot==false}">否</c:if>
                                                <c:if test="${siteHotsiteHot==true}">是</c:if>
                                            </td>
                                            <td>
                                                未上线 state   状态
                                            </td>
                                            <td>
                                                <p>
                                                    <button class="btn btn-xs blue btn-circle">查看</button>
                                                    <button class="btn btn-xs yellow btn-circle">添加</button>
                                                </p>
                                                <p>
                                                    <button class="btn btn-xs green btn-circle">修改</button>
                                                    <button class="btn btn-xs red btn-circle">删除</button>
                                                </p>
                                            </td>
                                        </tr>
                                  </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                                <div class="row">
                                    <link rel="stylesheet" href="../core/css/page.css"/>
                                    <div class="col-xs-2">
                                        <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
                                            第
                                            <span>1</span>
                                            条 到 第
                                            <span>15</span>
                                            条 共
                                            <span>100</span>
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
                                                    <option value="-1">所有</option>
                                                </select> 条</label>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-sm-4">
                                        <div class="dataTables_paginate paging_simple_numbers pgt-page-box">
                                            <!-- 当前页需要增加active类,首页末页的禁用是增加disabled类-->
                                            <ul class="pagination">
                                                <li class="paginate_button previous disabled"><a href="#"><i
                                                        class="fa fa-angle-left"></i></a></li>
                                                <li class="paginate_button"><a
                                                        href="#">首页</a></li>
                                                <li class="paginate_button disabled"><a
                                                        href="javascript:;">...</a></li>
                                                <li class="paginate_button "><a
                                                        href="#">3</a></li>
                                                <li class="paginate_button "><a
                                                        href="#">4</a></li>
                                                <li class="paginate_button active"><a
                                                        href="#">5</a></li>
                                                <li class="paginate_button"><a
                                                        href="#">6</a></li>
                                                <li class="paginate_button"><a
                                                        href="#">7</a></li>
                                                <li class="paginate_button disabled"><a
                                                        href="javascript:;">...</a></li>
                                                <li class="paginate_button"><a
                                                        href="#">末页</a></li>
                                                <li class="paginate_button next"><a href="#"><i class="fa fa-angle-right"></i></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <form class="dataTables_filter pgt-goto-page">
                                            <label>
                                                <input type="search" class="form-control input-xsmall input-inline" placeholder="第几页">
                                                <input type="submit" class="btn blue" value="跳转">
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
