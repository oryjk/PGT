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
            <div class="row" style="display: ${error eq null ? 'none' : 'block'}">
                <div class="col-xs-12">
                    <div class="Metronic-alerts alert alert-danger fade in">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                        <p>${error}</p>


                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="portlet box blue-hoki">
                        <div class="portlet-title">

                          <c:if test="${empty banner.bannerId}">
                            <!-- super:新增时显示-->
                            <div class="caption">
                                <i class="fa fa-gift"></i>增加Banner
                            </div>

                          </c:if>

                            <c:if test="${!empty banner.bannerId}">
                            <!-- super:修改时显示-->
                            <div class="caption">
                                <i class="fa fa-gift"></i>修改Banner
                            </div>
                            </c:if>

                        </div>
                        <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <form action="/banner/${banner eq null ? 'addBannerSubmit' : 'updateBannerSubmit'}/ " method="post" class="form-horizontal">
                                <div class="form-body">

                                    <!-- super:修改用户时才出现下面一个div.form-group-->
                                    <c:if test="${!empty banner.bannerId}">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">id</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${banner.bannerId}
                                                <input type="hidden" name="bannerId" value="${banner.bannerId}">
                                            </p>
                                        </div>
                                    </div>
                                    </c:if>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">位置</label>

                                        <div class="col-md-4">

                                            <div class="radio-list">${param.status eq 100 ? 'choose' : ''}
                                                <select class="form-control input-medium" name="type">
                                                    <option value="HOME" ${banner.type eq 'HOME' ? "selected='selected'" :''}>首页</option>
                                                </select>
                                            </div>

                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">名称</label>
                                        <div class="col-md-4">
                                                <input type="input" value="${banner.name}" name="name" class="form-control" placeholder="Banner的名称">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3">状态</label>
                                        <div class="col-md-9">
                                            <div class="radio-list">
                                                <select class="form-control input-medium" name="status">
                                                    <option value="1" ${banner.status eq 1 ? "selected='selected'" :''}>启用</option>
                                                    <option value="0" ${banner.status eq 1 ? "selected='selected'" :''}>禁用</option>
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


</pgt:container>