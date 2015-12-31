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
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pagination" value="${b2cOrderPage}" scope="request" />

<pgt:container id="content" loadJsDateInput="true" pageJsPath="/resources/order/order-list.js">
    <jsp:include page="include/bread-crumb-row.jspf" />
    <div class="row" style="display: block">
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
                                                        <fmt:formatNumber value="${b2cOrder.total}" pattern="0.00" type="number" />
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
                                                        ${b2cOrder.submitDate}
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
                                                    <th>
                                                        商品ID
                                                    </th>
                                                    <th>
                                                        商品名称
                                                    </th>
                                                    <th>
                                                        绝当价
                                                    </th>
                                                    <th>
                                                        略缩图
                                                    </th>
                                                    <th>
                                                        商家
                                                    </th>
                                                    <th>
                                                        发货人
                                                    </th>
                                                    <th>
                                                        发货时间
                                                    </th>
                                                    <th>
                                                        物流公司
                                                    </th>
                                                    <th>
                                                        物流单号
                                                    </th>
                                                    <th>
                                                        发货
                                                    </th>
                                                    <th>
                                                        收货
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="ci" items="${b2cOrder.commerceItems}">
                                                <tr class="gradeX odd" role="row">
                                                    <td>
                                                        ${ci.id}
                                                    </td>
                                                    <td>
                                                        ${ci.name}
                                                    </td>
                                                    <td>
                                                        ¥<span><fmt:formatNumber value="${ci.salePrice}" pattern="0.00" type="number" /></span>
                                                    </td>
                                                    <td class="productlist-face-box">
                                                        <img src="${pageContext.request.contextPath}/resources${ci['snapshotMedia']['path']}"
                                                             alt="${empty ci['snapshotMedia']['title'] ? ci.name : ci['snapshotMedia']['title']}" />
                                                    </td>
                                                    <td>
                                                        鑫鑫珠宝行
                                                    </td>
                                                    <td>
                                                        关羽
                                                    </td>
                                                    <td>
                                                        2015-12-12 14:00
                                                    </td>
                                                    <td>
                                                        顺丰快递
                                                    </td>
                                                    <td>
                                                        123456789012345678
                                                    </td>
                                                    <td>
                                                        <!-- 有修改和发货两种按钮,根据是否已经有发货数据显示-->
                                                        <button class="btn btn-xs green btn-circle" data-pgt-btn="send">修改</button>
                                                    </td>
                                                    <td>
                                                        <!-- 有已收货文本和收货按钮两种,根据item状态显示-->
                                                        已收货
                                                    </td>
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
                                    <button class="btn btn-xs green" id="modifySwitch">修改</button>
                                </h3>
                                <div class="pgt-scan">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">姓名</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        刘备
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">手机</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        13198585611
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">电话</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        无
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label col-md-4">配送区域</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        四川省-成都市-龙泉驿区
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!--/span-->
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <label class="control-label col-md-2">配送地址</label>
                                                <div class="col-md-10">
                                                    <p class="form-control-static">
                                                        颈龙路99号成都航天中学1号楼1-2
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
                                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen" tabindex="-1" title="">
                                                        <option value="10">10</option>
                                                        <option value="30">30</option>
                                                        <option value="100">100</option>
                                                        <option value="-1">所有</option>
                                                    </select>省
                                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen" tabindex="-1" title="">
                                                        <option value="10">10</option>
                                                        <option value="30">30</option>
                                                        <option value="100">100</option>
                                                        <option value="-1">所有</option>
                                                    </select>市
                                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen" tabindex="-1" title="">
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

                            <!-- 状态纪录-->
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
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr class="gradeX odd" role="row">
                                                    <td class="sorting_1">
                                                        2015-12-12 14:00
                                                    </td>
                                                    <td>
                                                        易宝支付
                                                    </td>
                                                    <td>
                                                        12390.00
                                                    </td>
                                                    <td>
                                                        123456789012345678
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

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