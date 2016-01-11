<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<c:set var="currentPage" value="${empty param.currentPage ? 1 : param.currentPage}"/>
<c:set var="maxPageNum" value="${paginationBean.maxPageNum}"/>
<pgt:container id="main">
<div class="page-container" id="content">
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
                            <a href="#">用户管理</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <a href="#">管理员管理</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">管理员列表</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <a href="#">新增管理员</a>
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
                    <div class="portlet box blue-hoki">
                        <div class="portlet-title">
                            <!-- super:新增时显示-->
                            <div class="caption">
                                <i class="fa fa-gift"></i>增加Banner
                            </div>
                            <!-- super:修改时显示-->
                            <div class="caption">
                                <i class="fa fa-gift"></i>修改Banner
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <form action="javascript:;" class="form-horizontal">
                                <div class="form-body">
                                    <!-- super:修改用户时才出现下面一个div.form-group-->
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">id</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                012
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">位置</label>
                                        <div class="col-md-4">
                                            <!-- super:新增时显示-->
                                            <input type="text" class="form-control" placeholder="Banner显示的位置">
                                            <!-- super:修改时显示-->
                                            <p class="form-control-static">

                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">名称</label>
                                        <div class="col-md-4">
                                                <input type="email" class="form-control" placeholder="Banner的名称">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3">状态</label>
                                        <div class="col-md-9">
                                            <div class="radio-list">
                                                <select class="form-control input-medium">
                                                    <option value="1000">启用</option>
                                                    <option value="5000">禁用</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="form-actions top">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button type="submit" class="btn blue-hoki">确认</button>
                                            <button type="button" class="btn default">取消</button>
                                        </div>
                                    </div>
                                </div>

                            </form>
                            <!-- END FORM-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- END PAGE CONTAINER -->


<!-- BEGIN JAVASCRIPTS (Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>

<!--BEGIN super: load 本段内容在套模版时不引入,直接在相应位置引入jsp即可-->
<script>
    $('#header').load('../core/html/header.html');
</script>
<!--END load-->

<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->

<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
