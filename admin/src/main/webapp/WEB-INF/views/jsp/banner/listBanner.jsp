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
                            <a href="#">Banner列表</a>
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
                                <button class="btn green-haze btn-circle"  onclick="javascript:window.location.href='/banner/addBanner/';"><i class="fa fa-plus"></i> 新增</button>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                                <div class="table-scrollable productlist-box">
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                           id="list1" role="grid" aria-describedby="sample_3_info">
                                        <thead>
                                        <tr role="row">
                                            <th>
                                                类型
                                            </th>
                                            <th>
                                                名称
                                            </th>
                                            <th>
                                                BannerId
                                            </th>

                                            <th>
                                                状态
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                            <th>
                                                图片操作
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${bannerList}" var="banner">

                                            <tr class="gradeX odd" role="row">
                                                <td>
                                                    ${banner.type}
                                                </td>
                                                <td>
                                                    ${banner.name}
                                                </td>
                                                <td>
                                                    ${banner.bannerId}
                                                </td>
                                                <td>
                                                    <div class="btn-group">

                                                        <c:if test="${banner.status==1}">
                                                            <a class="btn btn-xs default btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="">
                                                                启用 </i>
                                                            </a>

                                                        </c:if>
                                                        <c:if test="${banner.status==0}">
                                                        <a class="btn btn-xs default btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="">
                                                            禁用 </i>
                                                        </a>
                                                        </c:if>

                                                    </div>
                                                </td>
                                                <td>
                                                    <button class="btn btn-xs green btn-circle" onclick="javascript:window.location.href='/banner/updateBanner/${banner.bannerId}'">修改状态</button>
                                                </td>
                                                <td>
                                                    <button class="btn btn-xs green btn-circle" onclick="javascript:window.location.href='/bannerImage/createImageUI/${banner.bannerId}'" >新增</button>
                                                    <button class="btn btn-xs red btn-circle" onclick="javascript:window.location.href='/banner/queryBanner/${banner.bannerId}'">列表</button>
                                                </td>
                                            </tr>

                                        </c:forEach>



                                        </tbody>
                                    </table>
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