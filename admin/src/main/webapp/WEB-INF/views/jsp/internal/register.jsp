<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/26/2015
  Time: 00:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf">
        <jsp:param name="step" value="signup" />
    </jsp:include>
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet box blue-hoki">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-gift"></i>注册后台用户
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="<spring:url value="/internal/register" />" class="form-horizontal" method="post">
                        <input type="hidden" name="investType" value="NONEED">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label">登陆账号</label>
                                <div class="col-md-4">
                                    <input type="text" name="login" class="form-control" placeholder="5-12位的登陆账号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">昵称</label>
                                <div class="col-md-4">
                                    <input type="text" name="name" class="form-control" placeholder="用于称呼">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">密码</label>
                                <div class="col-md-4">
                                    <input type="password" name="password" class="form-control" placeholder="6-12位的字母或数字, 至少有一个大写字母/小写字母/数字">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">密码确认</label>
                                <div class="col-md-4">
                                    <input type="password" name="passwordConfirm" class="form-control" placeholder="再次输入密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">角色</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select name="role" class="form-control input-medium">
                                            <option value="INVESTOR" selected="selected">投资管理员</option>
                                            <option value="ADMINISTRATOR">后台管理员</option>
                                            <option value="DEVELOPER">开发管理员</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">联系电话</label>
                                <div class="col-md-4">
                                    <div class="input-icon">
                                        <i class="glyphicon glyphicon-phone "></i>
                                        <input type="text" name="phone" class="form-control" placeholder="11位手机号码">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">电子邮箱</label>
                                <div class="col-md-4">
                                    <div class="input-icon">
                                        <i class="glyphicon glyphicon-envelope"></i>
                                        <input type="email" phone="email" class="form-control" placeholder="Email">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">状态</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select name="available" class="form-control input-medium">
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
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
</pgt:container>