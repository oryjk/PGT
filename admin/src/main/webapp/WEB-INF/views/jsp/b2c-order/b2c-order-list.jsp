<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/25/2015
  Time: 00:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pagination" value="${b2cOrderPage}" scope="request" />

<pgt:container id="content" loadJsDateInput="true" pageJsPath="/resources/order/order-list.js">
    <jsp:include page="include/bread-crumb-row.jspf" />
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
                        <span class="caption-subject font-green-sharp bold uppercase">订单列表 </span>
                    </div>
                    <div class="actions btn-set">
                    </div>
                </div>
                <div class="portlet-body">
                    <form action="<spring:url value="/order/order-list"/>">
                        <input type="hidden" name="currentIndex" value="${param.currentIndex}" />
                        <input type="hidden" name="capacity" value="${param.capacity}" />
                        <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                            <div class="row">
                                <div class="col-md-2 col-sm-2">
                                    <div class="dataTables_filter">
                                        <label>订单ID:
                                            <input type="search" name="id" value="${param.id}"
                                                   class="form-control input-small input-inline" placeholder="订单ID" aria-controls="sample_3" />
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-2 col-sm-2">
                                    <div class="dataTables_filter">
                                        <label>用户名:
                                            <input type="search" name="userName" value="${param.userName}"
                                                   class="form-control input-small input-inline" placeholder="昵称或账号" aria-controls="sample_3">
                                        </label>
                                    </div>
                                </div>
                                <div class="col-xs-3">
                                    <div class="dataTables_filter">
                                        <label>金额:
                                            <input type="search" name="priceBeg" value="${param.priceBeg}"
                                                   class="form-control input-xsmall input-inline"
                                                   placeholder="最低" aria-controls="sample_3" />
                                            -
                                            <input type="search" name="priceEnd" value="${param.priceEnd}"
                                                   class="form-control input-xsmall input-inline"
                                                   placeholder="最高" aria-controls="sample_3" />
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-10">
                                    <div class="dataTables_filter">
                                        <div class="row">
                                            <div class="col-xs-1 pgt-time-tittle">
                                                <span>时间: </span>
                                            </div>
                                            <div class="col-xs-2 pgt-begin-date" style="position: relative">
                                                <input name="submitTimeBeg" value="${param.submitTimeBeg}" class="jcDate jcDateIco form-control input-small input-inline" />
                                            </div>
                                            <div class="col-xs-2 pgt-time" style="position: relative">
                                                <input type="search" class="form-control input-mini input-inline"
                                                       placeholder="时" aria-controls="sample_3">
                                                :
                                                <input type="search" class="form-control input-mini input-inline"
                                                       placeholder="分" aria-controls="sample_3">
                                            </div>
                                            <div class="col-xs-1 pgt-date-divide">
                                                <span>至</span>
                                            </div>
                                            <div class="col-xs-2">
                                                <input name="submitTimeEnd" value="${param.submitTimeEnd}" class="jcDate jcDateIco form-control input-small input-inline" />
                                            </div>
                                            <div class="col-xs-2 pgt-time">
                                                <input type="search" class="form-control input-mini input-inline"
                                                       placeholder="时" aria-controls="sample_3">
                                                :
                                                <input type="search" class="form-control input-mini input-inline"
                                                       placeholder="分" aria-controls="sample_3">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-1">
                                    <input type="submit" class="btn blue" value="搜索" />
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="table-scrollable list-box">
                        <table class="table table-striped table-bordered table-hover dataTable no-footer"
                               id="list" role="grid" aria-describedby="sample_3_info">
                            <thead>
                            <tr role="row">
                                <th class="sorting_asc" tabindex="0" aria-controls="sample_3" rowspan="1"
                                    colspan="1" aria-sort="ascending" aria-label="Username : activate to sort column ascending">
                                    订单号
                                </th>
                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                    aria-label=" Email : activate to sort column ascending">
                                    下单时间
                                </th>
                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                    aria-label=" Email : activate to sort column ascending">
                                    商品数量
                                </th>
                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                    aria-label=" Email : activate to sort column ascending">
                                    总金额
                                </th>
                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                    aria-label="Status : activate to sort column ascending">
                                    用户id
                                </th>
                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                    aria-label="Status : activate to sort column ascending">
                                    详细信息
                                </th>
                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1">
                                    <div class="btn-group">
                                        <a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown">
                                            订单状态 <i class="fa fa-angle-down"></i>
                                        </a>
                                        <ul class="dropdown-menu pull-right">
                                            <li>
                                                <a href="javascript:;">
                                                    全部订单 </a>
                                            </li>
                                            <li>
                                                <a href="javascript:;">
                                                    待付款 </a>
                                            </li>
                                            <li>
                                                <a href="javascript:;">
                                                    待收获 </a>
                                            </li>
                                            <li>
                                                <a href="javascript:;">
                                                    已完成 </a>
                                            </li>
                                            <li>
                                                <a href="javascript:;">
                                                    已取消 </a>
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
                            <c:forEach var="order" items="${b2cOrderPage.result}">
                                <tr class="gradeX odd" role="row">
                                    <td class="sorting_1">${order.id}</td>
                                    <td>${order.submitDate}</td>
                                    <td>${order.commerceItemCount}</td>
                                    <td><fmt:formatNumber value="${order.total}" pattern="0.00" type="number" /></td>
                                    <td>${order.userId}</td>
                                    <td><a href="<spring:url value="/order/order-info?id=${order.id}" />" class="btn btn-xs btn-circle">查看</a></td>
                                    <td>
                                        <!--super: blue:待付款 yellow待收货 green已完成 red已取消-->
                                        <div class="btn-group">
                                            <a class="btn btn-xs blue" href="javascript:;" data-toggle="dropdown">
                                                待付款 <i class="fa fa-angle-down"></i>
                                            </a>
                                            <ul class="dropdown-menu pull-right">
                                                <li>
                                                    <a href="javascript:;" data-pgt-btn="modify">
                                                        待付款 </a>
                                                </li>
                                                <li>
                                                    <a href="javascript:;" data-pgt-btn="modify">
                                                        待收货 </a>
                                                </li>
                                                <li>
                                                    <a href="javascript:;" data-pgt-btn="modify">
                                                        已完成 </a>
                                                </li>
                                                <li>
                                                    <a href="javascript:;" data-pgt-btn="modify">
                                                        已取消 </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </td>
                                    <td>
                                        <button class="btn btn-xs red btn-circle" data-pgt-btn="delete">删除</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <%-- pagination row --%>
                    <form action="<spring:url value="/order/order-list" />" method="get">
                        <div class="row">
                            <jsp:include page="include/pagination-capacity-selection.jsp">
                                <jsp:param name="paginationURL" value="/order/order-list?id=${param.id}&userName=${param.userName}&priceBeg=${param.priceBeg}&priceEnd=${param.priceEnd}&submitTimeBeg=${param.submitTimeBeg}&submitTimeEnd=${param.submitTimeEnd}" />
                            </jsp:include>
                        </div>
                        <input type="hidden" name="id" value="${param.id}" />
                        <input type="hidden" name="userName" value="${param.userName}" />
                        <input type="hidden" name="priceBeg" value="${param.priceBeg}" />
                        <input type="hidden" name="priceEnd" value="${param.priceEnd}" />
                        <input type="hidden" name="submitTimeBeg" value="${param.submitTimeBeg}" />
                        <input type="hidden" name="submitTimeEnd" value="${param.submitTimeEnd}" />
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!--super: 确认操作modal-->
    <div class="modal fade bs-modal-sm" id="confirmModal" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">确认</h4>
                </div>
                <form action="#" class="form-horizontal">
                    <div class="modal-body" id="confirmText">
                        确认你的操作
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn blue">确定</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- /.modal-content -->
        <!-- /.modal-dialog -->
    </div>
</pgt:container>