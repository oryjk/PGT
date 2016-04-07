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
<c:set var="pagination" value="${b2cOrderPage}" scope="request" />

<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf">
        <jsp:param name="step" value="info" />
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
                        <i class="fa fa-gift"></i>订单详情&nbsp;#${b2cOrder.id}
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <div action="javascript:;" class="form-horizontal">
                        <div class="form-body">
                            <!-- 基本信息-->
                            <div>
                                <h3 class="form-section">
                                    基本信息
                                </h3>

                                <div class="pgt-scan">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">订单号：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">${b2cOrder.id}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">商品数量：</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">${b2cOrder.commerceItemCount}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">总金额：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        ¥&nbsp;<fmt:formatNumber value="${b2cOrder.total}" pattern="0.00" type="number" />
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">用户ID：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                            ${b2cOrder.userId}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">订单状态：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        <c:choose>
                                                            <c:when test="${b2cOrder.status eq 10}">
                                                                <span>待提交</span>
                                                            </c:when>
                                                            <c:when test="${b2cOrder.status eq 20}">
                                                                <span>待付款</span>
                                                            </c:when>
                                                            <c:when test="${b2cOrder.status eq 30}">
                                                                <span>已付款</span>
                                                            </c:when>
                                                            <c:when test="${b2cOrder.status eq 80}">
                                                                <span>待收货</span>
                                                            </c:when>
                                                            <c:when test="${b2cOrder.status eq 100}">
                                                                <span>已完成</span>
                                                            </c:when>
                                                            <c:when test="${b2cOrder.status eq -10}">
                                                                <span>已取消</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">下单时间：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        <fmt:formatDate value="${b2cOrder.submitDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 商品信息-->
                            <div>
                                <h3 class="form-section">
                                    商品信息
                                </h3>

                                <div class="pgt-scan">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                                   role="grid" aria-describedby="sample_3_info">
                                                <thead>
                                                <tr role="row">
                                                    <th> 商品ID </th>
                                                    <th> 商品名称 </th>
                                                    <th> 绝当价 </th>
                                                    <th> 略缩图 </th>
                                                    <th> 商家 </th>
                                                    <th> 发货人 </th>
                                                    <th> 发货时间 </th>
                                                    <th> 物流公司 </th>
                                                    <th> 物流单号 </th>
                                                    <th> 发货 </th>
                                                    <th> 收货 </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="ci" items="${b2cOrder.commerceItems}">
                                                    <tr class="gradeX odd" role="row">
                                                        <td>${ci.id}</td>
                                                        <td>${ci.name}</td>
                                                        <td>¥&nbsp;<span><fmt:formatNumber value="${ci.salePrice}" pattern="0.00" type="number" /></span></td>
                                                        <td class="productlist-face-box">
                                                            <img src="${ci['snapshotMedia']['path']}"
                                                                 alt="${empty ci['snapshotMedia']['title'] ? ci.name : ci['snapshotMedia']['title']}" />
                                                        </td>
                                                        <td>${ci.merchant}</td>
                                                        <td>${ci.delivery.consignor}</td>
                                                        <td>${ci.delivery.deliveryTimeStr}</td>
                                                        <td>${ci.delivery.logistics}</td>
                                                        <td>${ci.delivery.trackingNo}</td>
                                                        <td><a href="/order/delivery?id=${b2cOrder.id}&cid=${ci.id}"><c:choose>
                                                            <c:when test="${ci.delivery.delivered}">
                                                                <button class="btn btn-xs green btn-circle" data-pgt-btn="send">修改</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button class="btn btn-xs blue btn-circle" data-pgt-btn="send">发货</button>
                                                            </c:otherwise>
                                                        </c:choose></a></td>
                                                        <td><c:if test="${ci.delivery.delivered}">
                                                            <c:choose>
                                                                <c:when test="${ci.delivery.received}">已收货</c:when>
                                                                <c:otherwise>
                                                                    <a href="/order/make-receive?id=${b2cOrder.id}&cid=${ci.id}">
                                                                        <button class="btn btn-xs yellow btn-circle" data-pgt-btn="send">收货</button></a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 收货人信息-->
                            <div>
                                <h3 class="form-section">
                                    收货人信息
                                    <%--<button class="btn btn-xs green" id="modifySwitch">修改</button>--%>
                                </h3>
                                <div class="pgt-scan">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">姓名：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                            ${b2cOrder.shippingVO.shippingAddress.name}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">手机：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                            ${b2cOrder.shippingVO.shippingAddress.phone}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">电话：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                            ${b2cOrder.shippingVO.shippingAddress.telephone}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">配送区域：</label>

                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                            ${b2cOrder.shippingVO.shippingAddress.province} -
                                                            ${b2cOrder.shippingVO.shippingAddress.city} -
                                                            ${b2cOrder.shippingVO.shippingAddress.district}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <label class="control-label col-md-2">配送地址：</label>

                                                <div class="col-md-10">
                                                    <p class="form-control-static">
                                                            ${b2cOrder.shippingVO.shippingAddress.address}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <form class="pgt-modify" style="display: none">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-3">姓名</label>

                                                <div class="col-md-9">
                                                    <input type="search" class="form-control input-small input-inline" placeholder="" aria-controls="sample_3">
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-3">手机</label>

                                                <div class="col-md-9">
                                                    <input type="search" class="form-control input-small input-inline" placeholder="" aria-controls="sample_3">
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-3">电话</label>

                                                <div class="col-md-9">
                                                    <input type="search" class="form-control input-small input-inline" placeholder="" aria-controls="sample_3">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-3">配送区域</label>

                                                <div class="col-md-9">
                                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen"
                                                            tabindex="-1" title="">
                                                        <option value="10">10</option>
                                                        <option value="30">30</option>
                                                        <option value="100">100</option>
                                                        <option value="-1">所有</option>
                                                    </select>省
                                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen"
                                                            tabindex="-1" title="">
                                                        <option value="10">10</option>
                                                        <option value="30">30</option>
                                                        <option value="100">100</option>
                                                        <option value="-1">所有</option>
                                                    </select>市
                                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen"
                                                            tabindex="-1" title="">
                                                        <option value="10">10</option>
                                                        <option value="30">30</option>
                                                        <option value="100">100</option>
                                                        <option value="-1">所有</option>
                                                    </select>县
                                                </div>

                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <label class="control-label col-md-2">配送地址</label>

                                                <div class="col-md-10">
                                                    <input type="search" class="form-control input-large input-inline" placeholder="" aria-controls="sample_3">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-offset-1 col-md-9">
                                            <button type="submit" class="btn blue-hoki">确认</button>
                                            <button type="button" class="btn default">取消</button>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <%-- 状态纪录--%>
                            <c:if test="${not empty b2cOrder.payment && fn:length(b2cOrder.payment.transactions) gt 0}">
                                <div>
                                    <h3 class="form-section">付款信息</h3>
                                    <div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                                       role="grid" aria-describedby="sample_3_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0" aria-controls="sample_3" rowspan="1"
                                                            colspan="1" aria-sort="ascending" aria-label="Username : activate to sort column ascending">
                                                            付款时间
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                            aria-label=" Email : activate to sort column ascending">
                                                            付款方式
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                            aria-label=" Email : activate to sort column ascending">
                                                            付款金额
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                            aria-label=" Email : activate to sort column ascending">
                                                            交易单号
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                            aria-label=" Email : activate to sort column ascending">
                                                            交易结果
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="transaction" items="${b2cOrder.payment.transactions}">
                                                        <tr class="gradeX odd" role="row">
                                                            <td class="sorting_1">${transaction.transactionTime}</td>
                                                            <td><c:choose>
                                                                <c:when test="${b2cOrder.payment.type eq 1}">易宝支付</c:when>
                                                                <c:when test="${b2cOrder.payment.type eq 2}">支付宝支付</c:when>
                                                            </c:choose></td>
                                                            <td>¥&nbsp;<fmt:formatNumber value="${transaction.amount}" pattern="0.00" type="number" /></td>
                                                            <td>${transaction.trackingNo}</td>
                                                            <td><c:choose>
                                                                <c:when test="${transaction.status eq 1}">交易成功</c:when>
                                                                <c:when test="${transaction.status eq 0}">交易进行中</c:when>
                                                                <c:when test="${transaction.status eq -1}">交易失败</c:when>
                                                            </c:choose></td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="form-actions top"></div>
                    </div>
                    <!-- END FORM-->
                </div>
            </div>
        </div>
    </div>

    <!--super: 确认操作modal-->
    <div class="modal fade bs-modal-sm" id="confirmModal" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">确认</h4>
                </div>
                <form action="#" class="form-horizontal">
                    <div class="modal-body" id="confirmText">
                        确认你的操作
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn blue">确定</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- /.modal-content -->
        <!-- /.modal-dialog -->
    </div>
</pgt:container>