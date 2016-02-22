<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 16/2/15
  Time: 下午3:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ticket" value="${pawnTicket}" />
<c:set var="newTicket" value="${empty param.ticketId}" />
<pgt:container id="content" pageCssPath="/resources/category/pawn-add-and-modify.css" pageJsPath="/resources/category/category-add-and-modify.js">
    <jsp:include page="include/bread-crumb-row.jspf">
        <jsp:param name="step" value="${newTicket ? 'ticket-new' : 'ticket-update'}" />
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
                        <i class="fa fa-gift"></i>${newShop ? '新增' : '编辑'}当票
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="/pawn/pawn-ticket-update" class="form-horizontal" method="post">
                        <div class="form-body">
                            <c:if test="${not newTicket}">
                                <input type="hidden" name="pawnTicketId" value="${ticket.pawnTicketId}">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">id</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">${ticket.pawnTicketId}</p>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="col-md-3 control-label">当票编号</label>
                                <div class="col-md-4">
                                    <input type="text" name="number" value="${ticket.number}" class="form-control" placeholder="当票编号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">所属当铺</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select name="pawnshopId" class="form-control input-medium">
                                            <c:forEach var="shop" items="${pawnShops}">
                                                <option value="${shop.pawnshopId}"
                                                    ${ticket.pawnshopId eq shop.pawnshopId ? 'selected' : ''}>${shop.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">当票状态</label>
                                <div class="col-md-9">
                                    <div class="radio-list">
                                        <select name="status" class="form-control input-medium">
                                            <option value="1" ${1 eq ticket.status ? 'selected' : ''}>已发布</option>
                                            <option value="0" ${0 eq ticket.status ? 'selected' : ''}>已隐藏</option>
                                            <option value="－1" ${-1 eq ticket.status ? 'selected' : ''}>已删除</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">备注</label>
                                <div class="col-md-4">
                                    <input type="text" name="comments" value="${ticket.comments}" class="form-control"
                                           placeholder="备注信息">
                                </div>
                            </div>
                            <c:if test="${not newTicket}">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">创建时间</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                            <fmt:formatDate value="${ticket.creationDate}"
                                                            pattern="yyyy-MM-dd HH:mm:sss" /></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">最后更新时间</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                            <fmt:formatDate value="${ticket.updateDate}"
                                                            pattern="yyyy-MM-dd HH:mm:sss" /></p>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="form-actions top">
                            <div class="row">
                                <div class="col-md-offset-3 col-md-9">
                                    <button type="submit" class="btn blue-hoki">确认</button>
                                    <button type="button" class="btn default"><a href="/pawn/pawn-ticket-list">回到当票列表页面</a></button>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="frontMedia" value="" name="pawnTicketPhoto.id"/>
                        <input type="hidden" id="frontMediaPath" value="" name="pawnTicketPhoto.path"/>
                    </form>
                    <!-- END FORM-->

                </div>
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
                    <img class="pgt-category-img" src="${ticket.pawnTicketPhoto.path}" alt=""/>
                    <p>200 * 200</p>
                </div>
            </div>
        </div>
        <div class="row">
            <label class="col-md-12 control-label">当票图片</label>
            <div class="col-md-12">
                <form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
                    <input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
                    <input name="mediaType" type="hidden" value="category"/>
                    <button type="button" class="btn blue">选择图片</button>
                </form>
                <p></p>
            </div>
        </div>
    </div>
</pgt:container>
