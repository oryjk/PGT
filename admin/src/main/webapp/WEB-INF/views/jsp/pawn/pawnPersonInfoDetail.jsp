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
                    <a href="#">典当个人信息管理</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li class="active">
                    <a href="#">典当投资详情</a>
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
                        <i class="fa fa-gift"></i>典当投资详情
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="/pawnPersonInfo/updateStatus" class="form-horizontal" method="post">

                        <div class="form-body">

                            <div class="form-group">
                                <label class="col-md-3 control-label"></label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                    </p>
                                </div>
                            </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">id</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                                ${pawnPersonInfo.id}

                                        </p>
                                    </div>
                                </div>


                               <div class="form-group">
                                <label class="col-md-3 control-label">姓名</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                            ${pawnPersonInfo.name}

                                    </p>
                                </div>
                               </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">性别</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                        <c:if test="${pawnPersonInfo.gender==0}">男</c:if>
                                        <c:if test="${pawnPersonInfo.gender==1}">女</c:if>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">电话号码</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                        ${pawnPersonInfo.phoneNumber}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">地址</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                            ${pawnPersonInfo.address}${pawnPersonInfo.detailAddress}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">典当类型</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                           ${pawnTypes[pawnPersonInfo.pawnType]}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">投资金额</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                        ${pawnPersonInfo.price}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">创建时间</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${pawnPersonInfo.createDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                    </p>
                                </div>
                            </div>

                            <input type="hidden" name="id" value="${pawnPersonInfo.id}"/>

                            <div class="form-group">
                                <label class="col-md-3 control-label">典当状态</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">

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


                                    </p>
                                </div>
                            </div>



                            <div class="form-group">
                                <label class="col-md-3 control-label">联系人名称</label>
                                <div class="col-md-4">
                                    <input type="text" name="contacts" value="${pawnPersonInfo.contacts}" class="form-control" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3">审核状态</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select class="form-control input-medium" name="status">
                                            <option value="">请选择审核结果</option>
                                            <option value="10" ${pawnPersonInfo.status eq 10 ? "selected='selected'" :''}>通过</option>
                                            <option value="20" ${pawnPersonInfo.status eq 20 ? "selected='selected'" :''}>未通过</option>
                                            <option value="30" ${pawnPersonInfo.status eq 30 ? "selected='selected'" :''}>未处理</option>
                                            <option value="40" ${pawnPersonInfo.status eq 40 ? "selected='selected'" :''}>已查看</option>
                                            <option value="50" ${pawnPersonInfo.status eq 50 ? "selected='selected'" :''}>已联系</option>
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
    </div>
    </div>
    </div>
    </div>
</admin:container>
<script src="/resources/assets/others/Jquery-date-and-time/jquery-calendar.js"></script>