<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<admin:container id="productList" pageJsPath="/resources/others/background-add-and-modify.js" pageCssPath="/resources/banner/banner-add-and-modify.css" >


    <input type="hidden" value="${pageBackground.pageBackgroundId}" id="pageBackgroundId"/>
    <div class="page-content">
        <div class="container-fluid pgt-container">
            <div class="row">
                <div class="col-xs-12">
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <a href="#">首页</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <a href="#">背景操作</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">新增背景</a>
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
                    <div class="portlet box blue-hoki">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-gift"></i>基本信息
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <form action="/pageBackground/${pageBackground.pageBackgroundId eq null ? 'createPageBackground' : 'updatePageBackground'}/ " class="form-horizontal" method="post">
                                <div class="form-body">
                                    <input type="hidden" name="pageBackgroundId" value="${pageBackground.pageBackgroundId}">
                                    <c:if test="${!empty ppageBackground.pageBackgroundId}">
                                    <!-- 只有在修改时才出现id行-->
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">id</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                012
                                            </p>
                                        </div>
                                    </div>
                                    </c:if>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">开始时间:</label>
                                        <div class="col-md-4">
                                            <input type="text" name="startDate" value="${pageBackground.startDate}" class="form-control" placeholder="" maxlength="35" onfocus="$(this).calendar()">
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3">结束时间:</label>
                                        <div class="col-md-4">
                                            <input type="text" name="endDate" value="${pageBackground.endDate}" class="form-control" placeholder="" maxlength="35" onfocus="$(this).calendar()">
                                            </p>
                                        </div>
                                    </div>



                                </div>
                                <div class="form-actions top">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button type="submit" class="btn blue-hoki">下一步</button>
                                            <button type="button" class="btn default">取消</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- END FORM-->
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

</admin:container>
<script src="/resources/assets/others/Jquery-date-and-time/jquery-calendar.js"></script>