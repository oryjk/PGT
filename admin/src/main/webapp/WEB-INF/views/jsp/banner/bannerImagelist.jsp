<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<admin:container id="BannerImageList" >

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
                        <li>
                            <a href="table_managed.html">banner管理</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <a href="table_managed.html">${banner.name}</a>
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
                                <button class="btn green-haze btn-circle" onclick="javascript:window.location.href='/bannerImage/createImageUI/${banner.bannerId}'"><i class="fa fa-plus"></i> 新增</button>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                              <!--  <div class="row">
                                    <div class="col-xs-2">
                                        <div class="dataTables_length">
                                            <label>banner类型
                                                <select name="sample_3_length" aria-controls="sample_3"
                                                        class="form-control input-xsmall input-inline select2-offscreen"
                                                        tabindex="-1" title="">
                                                    <option value="0">全部</option>
                                                    <option value="1">首页</option>
                                                    <option value="2">顶部</option>
                                                </select></label>
                                        </div>
                                    </div>
                                </div>
                                -->
                                <div class="table-scrollable">
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                           id="list1" role="grid" aria-describedby="sample_3_info">
                                        <thead>
                                        <tr role="row">
                                            <th>
                                                id
                                            </th>
                                            <th>
                                                开始时间
                                            </th>
                                            <th>
                                                结束时间
                                            </th>
                                            <th>
                                                链接地址
                                            </th>
                                            <th>
                                                图片
                                            </th>
                                            <th>
                                                显示序号
                                            </th>
                                            <th>
                                                备注
                                            </th>
                                            <th>
                                                状态
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${imageList}" var="image">
                                        <tr class="gradeX odd" role="row">
                                            <td>
                                               ${image.imageId}
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${image.createDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${image.endDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </td>
                                            <td>
                                                ${image.url}
                                            </td>
                                            <td class="face-box">
                                                <a href="#" style="font-size:15px">点击查看大图</a>
                                            </td>
                                            <td>
                                                ${image.location}
                                            </td>
                                            <td>
                                                ${image.title}
                                            </td>
                                            <td>

                                            ${image.type eq 0 ? '禁用' :'可用'}

                                            </td>
                                            <td>
                                                <button class="btn btn-xs green btn-circle" onclick="javascript:window.location.href='/bannerImage/updateUI/${banner.bannerId}/${image.imageId}'">修改</button>
                                                <button class="btn btn-xs red btn-circle" onclick="javascript:window.location.href='/bannerImage/deleteImage/${banner.bannerId}/${image.imageId}'">删除</button>
                                            </td>
                                        </tr>
                                        </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                                <div class="row">
                                    <link rel="stylesheet" href="/resources/core/css/page.css"/>
                                    <div class="col-xs-2">
                                        <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
                                            第
                                            <span>${paginationBean.sqlStartIndex+1}</span>
                                            条 到 第
                                            <span>${paginationBean.sqlStartIndex+fn:length(imageList)}</span>
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
                                                        href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=0">首页</a></li>
                                                <c:choose>
                                                    <c:when test="${paginationBean.maxIndex>5}">
                                                        <c:if test="${paginationBean.currentIndex>2 and paginationBean.currentIndex<paginationBean.maxIndex-3}">
                                                            <li class="paginate_button disabled">
                                                                <a href="javascript:;">...</a>
                                                            </li>
                                                            <li class="paginate_button ">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex-2}">${currentIndex-1}</a>
                                                            </li>
                                                            <li class="paginate_button ">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex-1}">${currentIndex}</a>
                                                            </li>
                                                            <li class="paginate_button active">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex}">${currentIndex+1}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex+1}">${currentIndex+2}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex+2}">${currentIndex+3}</a>
                                                            </li>
                                                            <li class="paginate_button disabled">
                                                                <a href="javascript:;">...</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${paginationBean.currentIndex<3}">

                                                            <c:forEach var="current" begin="1" end="${currentIndex+1}">
                                                                <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                                    <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${current-1}">${current}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="paginate_button">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex+1}">${currentIndex+2}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex+2}">${currentIndex+3}</a>
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
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex-2}">${currentIndex-2}</a>
                                                            </li>
                                                            <li class="paginate_button">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${currentIndex-1}">${currentIndex-1}</a>
                                                            </li>
                                                            <c:forEach var="current" begin="${currentIndex+1}" end="${maxIndex+1}">
                                                                <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                                    <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${current-1}">${current}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:forEach var="current" begin="1" end="${paginationBean.maxIndex+1}">

                                                            <li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
                                                                <a href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${current-1}">${current}</a>
                                                            </li>
                                                        </c:forEach>
                                                    </c:otherwise>
                                                </c:choose>


                                                <li class="paginate_button"><a
                                                        href="/banner/queryBanner?bannerId=${banner.bannerId}&currentIndex=${paginationBean.maxIndex}">末页</a></li>

                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <form class="dataTables_filter pgt-goto-page" action="/banner/queryBanner" method="get">
                                            <label>
                                                <input type="search" value="${currentIndex+1}" name="currentIndex" class="form-control input-xsmall input-inline" placeholder="第几页">
                                                <input type="hidden" name="bannerId" value="${banner.bannerId}">
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

</div>

</admin:container>
