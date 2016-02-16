<%--
  Created by IntelliJ IDEA.
  User: jeniss
  Date: 16/2/15
  Time: 上午11:32
  To change this template use File | Settings | File Templates.
--%>
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
<c:set var="shop" value="${pawnShop}" />
<c:set var="newShop" value="${empty param.shopId}" />
<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf">
        <jsp:param name="step" value="${newShop ? 'shop-new' : 'shop-update'}" />
    </jsp:include>
    <c:choose>
        <c:when test="${not empty errorMessage}">
            <div class="row" style="display: block">
                <div class="col-xs-12">
                    <div class="Metronic-alerts alert alert-danger fade in">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                        <p>${errorMessage}</p>
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${param.suc gt 0}">
            <div class="row" style="display: block">
                <div class="col-xs-12">
                    <div class="Metronic-alerts alert alert-success fade in">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                        <p>您的操作已经保存。</p>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet box blue-hoki">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-gift"></i>${newShop ? '新增' : '编辑'}当铺
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="/pawn/pawn-shop-update" class="form-horizontal" method="post">
                        <div class="form-body">
                            <c:if test="${not newShop}">
                                <input type="hidden" name="pawnshopId" value="${shop.pawnshopId}">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">id</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">${shop.pawnshopId}</p>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="col-md-3 control-label">名称</label>
                                <div class="col-md-4">
                                    <input type="text" name="name" value="${shop.name}" class="form-control" placeholder="当铺名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">所有者id</label>
                                <div class="col-md-4">
                                    <input type="text" name="ownerId" value="${shop.ownerId}" class="form-control"
                                           placeholder="当铺所有者的id">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">管理者id</label>
                                <div class="col-md-4">
                                    <input type="text" name="managerId" value="${shop.managerId}" class="form-control"
                                           placeholder="当铺管理者的id">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">地址</label>
                                <div class="col-md-4">
                                    <input type="text" name="address" value="${shop.address}" class="form-control"
                                           placeholder="当铺的具体地址">
                                </div>
                            </div>
                            <c:if test="${not newShop}">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">创建时间</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                            <fmt:formatDate value="${shop.creationDate}"
                                                            pattern="yyyy-MM-dd HH:mm:sss" /></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">最后更新时间</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                            <fmt:formatDate value="${shop.updateDate}"
                                                            pattern="yyyy-MM-dd HH:mm:sss" /></p>
                                    </div>
                                </div>
                            </c:if>
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