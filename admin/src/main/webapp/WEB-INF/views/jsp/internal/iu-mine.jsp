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
<c:set var="iu" value="${internalUser}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf">
        <jsp:param name="step" value="iu-mine"/>
    </jsp:include>

    <div class="row">
        <div class="col-xs-12">
            <div class="portlet box blue-hoki">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-gift"></i>我的资料
                    </div>
                </div>
                <div class="portlet-body form">
                    <form action="/internal/iu-modify" class="form-horizontal" method="post">
                        <!-- BEGIN FORM-->
                        <div class="form-body horizontal">
                            <div class="form-group">
                                <label class="col-md-3 control-label">id</label>
                                <div class="col-md-4">
                                    <p class="form-control-static">${iu.id}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">登陆账号</label>
                                <div class="col-md-4">
                                    <input type="text" name="login" value="${iu.login}" class="form-control" disabled
                                           placeholder="5-12位的登陆账号"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">昵称</label>
                                <div class="col-md-4">
                                    <input type="text" name="name" value="${iu.name}" class="form-control" disabled
                                           placeholder="用于称呼"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">角色</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select name="role" class="form-control input-medium" disabled>
                                            <c:forEach var="role" items="${roles}">
                                                <option value="${role.key}" ${iu.role eq role.key ? 'selected' : ''}>${role.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">联系电话</label>
                                <div class="col-md-4">
                                    <div class="input-icon">
                                        <i class="glyphicon glyphicon-phone"></i>
                                        <input type="text" name="phone" value="${iu.phone}" class="form-control"
                                               disabled
                                               placeholder="11位手机号码">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">电子邮箱</label>
                                <div class="col-md-4">
                                    <div class="input-icon">
                                        <i class="glyphicon glyphicon-envelope"></i>
                                        <input type="email" name="email" value="${iu.email}" class="form-control"
                                               disabled
                                               placeholder="Email">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">状态</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select name="available" class="form-control input-medium" disabled>
                                            <option ${iu.available ? 'selected' : ''} value="true">启用</option>
                                            <option ${not iu.available ? 'selected' : ''} value="false">禁用</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</pgt:container>