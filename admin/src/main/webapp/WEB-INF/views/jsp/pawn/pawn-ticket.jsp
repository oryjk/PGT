<%--
  Created by IntelliJ IDEA.
  User: jeniss
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
<pgt:container id="content">
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
                                <label class="col-md-3 control-label">所属当铺</label>
                                <div class="col-md-4">
                                    <input type="text" name="pawnshopId" value="${ticket.pawnshopId}" class="form-control"
                                           placeholder="所属当铺的id">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">当票状态</label>
                                <div class="col-md-4">
                                    <input type="text" name="status" value="${ticket.status}" class="form-control"
                                           placeholder="当票的显示状态">
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
