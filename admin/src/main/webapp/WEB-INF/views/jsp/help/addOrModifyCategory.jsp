<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<pgt:container id="content">
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
                            <a href="#">分类列表</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">目录分类</a>
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
                            <div class="caption">
                                <i class="fa fa-gift"></i>目录
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <form action="${actionUrl }" class="form-horizontal" method="post">
                            	<input type="hidden" name="id" value="${category.id}"/>
                                <div class="form-body">
                                    <!-- 只有在修改时才出现id行-->
                                    <c:if test="${not empty category.id }">
	                                    <div class="form-group">
	                                        <label class="col-md-3 control-label">id</label>
	                                        <div class="col-md-4">
	                                            <p class="form-control-static">
	                                                ${category.id }
	                                            </p>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">分类名称:</label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" name="name" value="${category.name}" placeholder="6个字以下">
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3">状态</label>
                                        <div class="col-md-9">
                                            <div class="radio-list">
                                                <select name="status" class="form-control input-medium">
                                                    <option value="1" ${1 == category.status ? 'selected' : ''}>启用</option>
                                                    <option value="0" ${0 == category.status ? 'selected' : ''}>禁用</option>
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
                        </div>
                        <!-- END FORM-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</pgt:container>