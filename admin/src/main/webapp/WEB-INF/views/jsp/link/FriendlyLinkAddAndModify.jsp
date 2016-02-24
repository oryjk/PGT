<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<admin:container id="productList" pageJsPath="/resources/others/hotSearch-add-and-modify.js" pageCssPath="/resources/banner/banner-add-and-modify.css" >

    <input type="hidden" value="${staticServer}" id="staticServer"/>
    <div class="row">
        <div class="col-xs-12">
            <ul class="page-breadcrumb breadcrumb">
                <li>
                    <a href="#">首页</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li>
                    <a href="#">友情链接管理</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li class="active">
                    <a href="#">友情链接操作</a>
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
                    <form action="/friendlyLink/${friendlyLink.id eq null ? 'addFriendlyLink' : 'updateFriendlyLink'}/ " class="form-horizontal" method="post">
                        <div class="form-body">
                            <c:if test="${!empty friendlyLink.id}">
                                <input id="id" type="hidden" name="id" value="${friendlyLink.id}">

                                <!-- 只有在修改时才出现id行-->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">id</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                        ${friendlyLink.id}
                                        </p>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label class="col-xs-3 control-label">名称:</label>

                                <div class="col-xs-4">
                                    <input  name="name"  value="${friendlyLink.name}" placeholder="" class="form-control  pgt-requisite-name"/>
                                </div>
                                <div class="col-xs-4">
                                    <p class="form-control-static pgt-error">
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-3 control-label">链接地址:</label>

                                <div class="col-xs-4">
                                    <input  name="link"  value="${friendlyLink.link}" placeholder="" class="form-control  pgt-requisite-name"/>
                                </div>
                                <div class="col-xs-4">
                                    <p class="form-control-static pgt-error">
                                    </p>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-3">状态</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select class="form-control input-medium" name="state">
                                            <option value="1" ${friendlyLink.state eq 1 ? "selected='selected'" :''}>启用</option>
                                            <option value="0" ${friendlyLink.state eq 0 ? "selected='selected'" :''}>禁用</option>
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
    </div>
</admin:container>
