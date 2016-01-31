<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/25/2015
  Time: 00:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<pgt:container id="content" loadJsDateInput="true" >
    <jsp:include page="include/bread-crumb-row.jspf">
        <jsp:param name="step" value="delivery" />
    </jsp:include>
    <div class="row" style="display: none;">
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
                        <i class="fa fa-gift"></i>${commerceItem.delivery.delivered ? '修改' : '准备'}发货
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form action="/order/make-delivery" class="form-horizontal" method="post">
                        <input type="hidden" name="id" value="${param.id}" />
                        <input type="hidden" name="commerceItemId" value="${commerceItem.delivery.commerceItemId}" />
                        <div class="form-body">
                            <c:if test="${commerceItem.delivery.commerceItemId gt 0}">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">商品ID：</label>
                                    <div class="col-md-4">
                                        <p class="form-control-static">
                                            ${commerceItem.name} # ${commerceItem.id}
                                        </p>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="col-md-3 control-label">发货人：</label>

                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="发货人" name="consignor" value="${commerceItem.delivery.consignor}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">物流公司：</label>

                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="物流公司名称" name="logistics" value="${commerceItem.delivery.logistics}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">物流单号：</label>

                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="物流单号" name="trackingNo" value="${commerceItem.delivery.trackingNo}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">发货时间：</label>

                                <div class="col-md-4">
                                    <input name="deliveryTimeStr" value="${commerceItem.delivery.deliveryTimeStr}" class="form-control input-middle input-inline" maxlength="16"
                                           onfocus="$(this).calendar()">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">是否发货：</label>

                                <div class="col-md-4">
                                    <select name="delivered" class="form-control">
                                        <option value="true" ${commerceItem.delivery.delivered ? 'selected' : ''}>已经发货</option>
                                        <option value="false" ${not commerceItem.delivery.delivered ? 'selected' : ''}>尚未发货</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">是否收货：</label>

                                <div class="col-md-4">
                                    <select name="received" class="form-control">
                                        <option value="true" ${commerceItem.delivery.received ? 'selected' : ''}>已经收货</option>
                                        <option value="false" ${not commerceItem.delivery.received ? 'selected' : ''}>尚未收货</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions top">
                            <div class="row">
                                <div class="col-md-offset-3 col-md-9">
                                    <button type="submit" class="btn blue-hoki">确认</button>
                                    <a href="/order/order-info?id=${param.id}"><button type="button" class="btn default">取消</button></a>
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