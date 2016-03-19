<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<admin:container id="productList" pageJsPath="/resources/banner/banner-add-and-modify.js" pageCssPath="/resources/banner/banner-add-and-modify.css" >

    <input type="hidden" value="${staticServer}" id="staticServer"/>
    <div class="row">
        <div class="col-xs-12">
            <ul class="page-breadcrumb breadcrumb">
                <li>
                    <a href="#">首页</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li>
                    <a href="#">Banner管理</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li class="active">
                    <a href="#">新增Banner图片</a>
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
                    image.imageId:${image.imageId}
                    <!-- BEGIN FORM-->
                    <form action="/bannerImage/${image.imageId eq null ? 'createIamge' : 'updateImage'}/ " class="form-horizontal" method="post">
                        <input type="hidden" name="bannerId" value="${banner.bannerId}">
                        <input type="hidden" name="imageId" value="${image.imageId}">
                        <div class="form-body">

                            <div class="form-group">
                                <label class="col-md-3 control-label"></label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                    </p>
                                </div>
                            </div>
                            <c:if test="${!empty image.imageId}">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">id</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                                ${image.imageId}

                                        </p>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label class="col-md-3 control-label">开始时间:</label>
                                <div class="col-md-4">
                                    <input type="text" name="createDate" value="<fmt:formatDate value="${image.createDate}" type="both" pattern="yyyy-MM-dd hh:mm" />" class="form-control" placeholder="" maxlength="25" onfocus="$(this).calendar()">
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">结束时间:</label>
                                <div class="col-md-4">
                                    <input type="text" name="endDate" value="<fmt:formatDate value="${image.endDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>" class="form-control" placeholder="" maxlength="25" onfocus="$(this).calendar()">
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">链接地址</label>
                                <div class="col-md-4">
                                    <input type="text" name="url" value="${image.url}" class="form-control" placeholder="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">备注</label>
                                <div class="col-md-4">
                                    <input type="text" name="title" value="${image.title}" class="form-control" placeholder="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">显示顺序</label>
                                <div class="col-md-4">
                                    <input type="text"  name="location" value="${image.location}"  class="form-control" placeholder="">
                                </div>
                            </div>
                            <input id="pgt-banner-img" type="hidden" name="path" value="${image.path}">


                            <div class="form-group">
                                <label class="control-label col-md-3">状态</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select class="form-control input-medium" name="type">
                                            <option value="1" ${image.type eq 1 ? "selected='selected'" :''}>启用</option>
                                            <option value="0" ${image.type eq 0 ? "selected='selected'" :''}>禁用</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- super:修改产品时才出现下面一个div.form-group-->

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
    <div id="imgUpload" class="pgt-img-upload">
        <div class="row">
            <div class="col-md-8">
                <div class="pgt-each-img">
                    <div class="pgt-handle-box">
                        <a class="pgt-img-delete" href="#">删除</a>
                    </div>
                    <img id="pgt-category-img" class="pgt-category-img"  src="${image.path}" alt="${image.path}"/>
                    <p>200 * 200</p>
                </div>
            </div>
        </div>
        <div class="row">
            <label class="col-md-12 control-label">分类主图</label>
            <div class="col-md-12">
                <form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
                    <input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
                    <input name="mediaType" type="hidden" value="banner"/>
                    <button type="button" class="btn blue">选择图片</button>
                </form>
                <p></p>
            </div>
        </div>
    </div>
    </div>
    </div>
    </div>
</admin:container>
<script src="/resources/assets/others/Jquery-date-and-time/jquery-calendar.js"></script>