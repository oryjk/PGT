<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>

<pgt:container id="main">
    <c:set value="${paginationBean.currentIndex}" var="currentIndex"/>
    <c:set value="${paginationBean.maxIndex}" var="maxIndex"/>
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
                            <a href="#">典当个人信息列表</a>
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
                        </div>


                        <div class="portlet-body">
                            <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                                <div class="row">
                                    <div class="col-xs-2">
                                        <div class="dataTables_length">
                                            <label>排序
                                                <select id="sortSelect" name="sample_3_length" aria-controls="sample_3"
                                                        class="form-control input-small input-inline select2-offscreen"
                                                        tabindex="-1" title="">
                                                    <option value="">请选择排序方式</option>
                                                    <option value="CREATE_DATE">时间</option>
                                                    <option value="PRICE">价格</option>
                                                </select></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-2">
                                        <div class="dataTables_length">
                                            <label>典当类型
                                                <select id="pawnTypeSelect" name="sample_3_length" aria-controls="sample_3"
                                                        class="form-control input-small input-inline select2-offscreen"
                                                        tabindex="-1" title="">
                                                    <option value="">全部</option>
                                                    <c:forEach items="${pawnTypes}" var="type">
                                                        <option ${ pawnTypeVo  eq type.key ? "selected='selected'" :''} value="${type.key}">${type.value}</option>
                                                    </c:forEach>
                                                </select></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-scrollable productlist-box">
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                           id="list1" role="grid" aria-describedby="sample_3_info">
                                        <thead>
                                        <tr role="row">
                                            <th>
                                                id
                                            </th>
                                            <th>
                                                姓名
                                            </th>
                                            <th>
                                                典当类型
                                            </th>
                                            <th>
                                                手机号码
                                            </th>
                                            <th>
                                                金额
                                            </th>
                                            <th>
                                                典当状态
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${pawnPersonInfoList}" var="pawnPersonInfo">

                                            <tr class="gradeX odd" role="row">
                                                <td>
                                                    ${pawnPersonInfo.id}
                                                </td>
                                                <td>
                                                    ${pawnPersonInfo.name}
                                                </td>
                                                <td>
                                                        ${pawnTypes[pawnPersonInfo.pawnType]}
                                                </td>
                                                <td>
                                                     ${pawnPersonInfo.phoneNumber}
                                                </td>
                                                <td>
                                                     ${pawnPersonInfo.price}
                                                </td>
                                                <td>

                                                    <c:if test="${pawnPersonInfo.status==10}">
                                                        通过
                                                    </c:if>
                                                    <c:if test="${pawnPersonInfo.status==20}">
                                                        未通过
                                                    </c:if>
                                                    <c:if test="${pawnPersonInfo.status==30}">
                                                        未处理
                                                    </c:if>
                                                    <c:if test="${pawnPersonInfo.status==40}">
                                                        已查看
                                                    </c:if>
                                                    <c:if test="${pawnPersonInfo.status==40}">
                                                        已联系
                                                    </c:if>


                                                </td>
                                                <td>
                                                    <button class="btn btn-xs green btn-circle"
                                                            onclick="window.location.href='/pawnPersonInfo/queryPawnPersonInfoById/${pawnPersonInfo.id}'">
                                                        详情
                                                    </button>
                                                    <button class="btn btn-xs red btn-circle"
                                                            onclick="window.location.href='/pawnPersonInfo/deletePawnPersonInfo/${pawnPersonInfo.id}'">
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
                                            <span>${paginationBean.sqlStartIndex+fn:length(pawnPersonInfoList)}</span>
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
                                                        href="/pawnPersonInfo/query?currentIndex=0">首页</a></li>
                                                <c:choose>
                                                    <c:when test="${paginationBean.maxIndex>5}">
                                                        <c:if test="${paginationBean.currentIndex>2 and paginationBean.currentIndex<paginationBean.maxIndex-3}">
                                                            <li class="paginate_button disabled">
                                                                <a href="javascript:;">...</a>
                                                            </li>
                                                            <li class="paginate_button ">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex-2}">${currentIndex-1}</a>
                                                            </li>
                                                            <li class="paginate_button ">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex-1}">${currentIndex}</a>
                                                            </li>
                                                            <li class="paginate_button active">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex}">${currentIndex+1}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
                                                            </li>
                                                            <li class="paginate_button disabled">
                                                                <a href="javascript:;">...</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${paginationBean.currentIndex<3}">

                                                            <c:forEach var="current" begin="1" end="${currentIndex+1}">
                                                                <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                                    <a href="/pawnPersonInfo/query?currentIndex=${current-1}">${current}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="paginate_button">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
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
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex-2}">${currentIndex-2}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${currentIndex-1}">${currentIndex-1}</a>
                                                            </li>
                                                            <c:forEach var="current" begin="${currentIndex+1}"
                                                                       end="${maxIndex+1}">
                                                                <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                                    <a href="/pawnPersonInfo/query?currentIndex=${current-1}">${current}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:forEach var="current" begin="1"
                                                                   end="${paginationBean.maxIndex+1}">

                                                            <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                                <a href="/pawnPersonInfo/query?currentIndex=${current-1}">${current}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </c:otherwise>
                                                </c:choose>


                                                <li class="paginate_button"><a
                                                        href="/pawnPersonInfo/query?currentIndex=${paginationBean.maxIndex}">末页</a>
                                                </li>

                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <form class="dataTables_filter pgt-goto-page" action="/pawnPersonInfo/query"
                                              method="get">
                                            <label>
                                                <input type="search" value="${currentIndex+1}" name="currentIndex"
                                                       class="form-control input-xsmall input-inline" placeholder="第几页">
                                                <input id="sortProperty" type="hidden" name="sortProperty"/>
                                                <input id="pawnType" type="hidden" name="pawnType">
                                                <input id="submitBtn" type="submit" class="btn blue pgt-goto-page-btn" value="跳转">
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
</pgt:container>
<script src="/resources/help/category.js"></script>