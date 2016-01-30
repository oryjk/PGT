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
                    <a href="#">热门搜索管理</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li class="active">
                    <a href="#">新增热门搜索</a>
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
                    <form action="/hotSearch/${hotSearch.hotSearchId eq null ? 'addHotSearch' : 'updateHotSearch'}/ " class="form-horizontal" method="post">
                        <div class="form-body">
                            <c:if test="${!empty hotSearch.hotSearchId}">
                                <input id="hotSearchId" type="hidden" name="hotSearchId" value="${hotSearch.hotSearchId}">

                                <!-- 只有在修改时才出现id行-->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">id</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                        ${hotSearch.hotSearchId}
                                        </p>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label class="col-xs-3 control-label">关键字:</label>

                                <div class="col-xs-4">
                                    <input ms-duplex="term" path="term" name="term"  value="${hotSearch.term}" placeholder="" class="form-control  pgt-requisite-name"/>
                                </div>
                                <div class="col-xs-4">
                                    <p class="form-control-static pgt-error">
                                    </p>
                                </div>
                            </div>


                        </div>
                        <div class="form-actions top">
                            <div class="row">
                                <div class="col-md-offset-3 col-md-9">
                                    <c:if test="${empty hotSearch.hotSearchId}">
                                        <button type="submit" class="btn blue-hoki">下一步</button>
                                    </c:if>

                                    <c:if test="${!empty hotSearch.hotSearchId}">
                                    <button type="submit" class="btn blue-hoki">确认</button>
                                    </c:if>
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

    <c:if test="${!empty hotSearch.hotSearchId}">
    <div id="imgUpload" class="pgt-img-upload">
        <div class="row">
            <div class="col-md-8">
                <div class="pgt-each-img">
                    <div class="pgt-handle-box">
                        <a class="pgt-img-delete" href="#">删除</a>
                    </div>
                    <img id="pgt-category-img" class="pgt-category-img"  src="${hotSearch.frontMedia.path}" alt="${image.path}"/>
                    <p>200 * 200</p>
                </div>
            </div>
        </div>
        <div class="row">
            <label class="col-md-12 control-label">热门搜索主图</label>
            <div class="col-md-12">
                <form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
                    <input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
                    <input name="mediaType" type="hidden" value="hot"/>
                    <button type="button" class="btn blue">选择图片</button>
                </form>
                <p></p>
            </div>
        </div>
    </div>

    </c:if>

    </div>
    </div>
    </div>
</admin:container>
