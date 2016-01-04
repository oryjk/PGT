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

<pgt:container id="content">
    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">Modal title</h4>
                </div>
                <div class="modal-body">
                    Widget settings form goes here
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn blue">Save changes</button>
                    <button type="button" class="btn default" data-dismiss="modal">Close</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <!-- BEGIN PAGE BREADCRUMB -->
    <ul class="page-breadcrumb breadcrumb">
        <li>
            <a href="#">首页</a><i class="fa fa-circle"></i>
        </li>
    </ul>
    <!-- END PAGE BREADCRUMB -->
    <!-- BEGIN PAGE CONTENT INNER -->
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 margin-bottom-10">
            <a class="dashboard-stat dashboard-stat-light blue-madison" href="javascript:;">
                <div class="visual">
                    <i class="fa fa-briefcase fa-icon-medium"></i>
                </div>
                <div class="details">
                    <div class="number">
                        111,127
                    </div>
                    <div class="desc">
                        总订单数
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
            <a class="dashboard-stat dashboard-stat-light red-intense" href="javascript:;">
                <div class="visual">
                    <i class="fa fa-shopping-cart"></i>
                </div>
                <div class="details">
                    <div class="number">
                        162,123
                    </div>
                    <div class="desc">
                        总销售数量
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
            <a class="dashboard-stat dashboard-stat-light green-haze" href="javascript:;">
                <div class="visual">
                    <i class="fa fa-group fa-icon-medium"></i>
                </div>
                <div class="details">
                    <div class="number">
                        <span>¥</span>
                        <span>123,231,000</span>
                    </div>
                    <div class="desc">
                        总销售额
                    </div>
                </div>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <!-- Begin: life time stats -->
            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-bar-chart font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase">销售统计</span>
                        <span class="caption-helper">近一周</span>
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="tabbable-line">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#overview_1" data-toggle="tab">
                                    大类排行</a>
                            </li>
                            <li>
                                <a href="#overview_2" data-toggle="tab">
                                    热销次类</a>
                            </li>
                            <li>
                                <a href="#overview_3" data-toggle="tab">
                                    商铺排名 </a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="overview_1">
                                <div class="table-responsive">
                                    <table class="table table-hover table-light">
                                        <thead>
                                        <tr class="uppercase">
                                            <th>
                                                大类名称
                                            </th>
                                            <th>
                                                总价格
                                            </th>
                                            <th>
                                                销售占比
                                            </th>
                                            <th>
                                                总数量
                                            </th>
                                            <th>
                                                库存
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    文玩杂项</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>120000</span>
                                            </td>
                                            <td>
                                                25%
                                            </td>
                                            <td>
                                                100
                                            </td>
                                            <td>
                                                35
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    玉石</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>120000</span>
                                            </td>
                                            <td>
                                                25%
                                            </td>
                                            <td>
                                                100
                                            </td>
                                            <td>
                                                35
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    琥珀</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>120000</span>
                                            </td>
                                            <td>
                                                25%
                                            </td>
                                            <td>
                                                100
                                            </td>
                                            <td>
                                                35
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    木制品</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>120000</span>
                                            </td>
                                            <td>
                                                25%
                                            </td>
                                            <td>
                                                100
                                            </td>
                                            <td>
                                                35
                                            </td>
                                        </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="tab-pane" id="overview_2">
                                <div class="table-responsive">
                                    <table class="table table-hover table-light">
                                        <thead>
                                        <tr class="uppercase">
                                            <th>
                                                次类名称
                                            </th>
                                            <th>
                                                总价格
                                            </th>
                                            <th>
                                                总数量
                                            </th>
                                            <th>
                                                库存
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    翡翠</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>12000</span>
                                            </td>
                                            <td>
                                                10
                                            </td>
                                            <td>
                                                12
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    独山玉</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>9000</span>
                                            </td>
                                            <td>
                                                8
                                            </td>
                                            <td>
                                                6
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    琥珀</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>8000</span>
                                            </td>
                                            <td>
                                                10
                                            </td>
                                            <td>
                                                12
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    沉香</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>7500</span>
                                            </td>
                                            <td>
                                                20
                                            </td>
                                            <td>
                                                35
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    骆驼骨头</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>6000</span>
                                            </td>
                                            <td>
                                                3
                                            </td>
                                            <td>
                                                5
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    牦牛角</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>5000</span>
                                            </td>
                                            <td>
                                                2
                                            </td>
                                            <td>
                                                0
                                            </td>
                                        </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="tab-pane" id="overview_3">
                                <div class="table-responsive">
                                    <table class="table table-hover table-light">
                                        <thead>
                                        <tr class="uppercase">
                                            <th>
                                                商户名称
                                            </th>
                                            <th>
                                                销售总价
                                            </th>
                                            <th>
                                                数量
                                            </th>
                                            <th>
                                                库存
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    自营</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>120000</span>
                                            </td>
                                            <td>
                                                100
                                            </td>
                                            <td>
                                                1200
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    鑫鑫珠宝行</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>20000</span>
                                            </td>
                                            <td>
                                                8
                                            </td>
                                            <td>
                                                60
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    森森珠宝行</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>18000</span>
                                            </td>
                                            <td>
                                                10
                                            </td>
                                            <td>
                                                120
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    淼淼珠宝行</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>12000</span>
                                            </td>
                                            <td>
                                                6
                                            </td>
                                            <td>
                                                35
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    焱焱珠宝行</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>9000</span>
                                            </td>
                                            <td>
                                                25%
                                            </td>
                                            <td>
                                                2
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <a href="javascript:;">
                                                    垚垚珠宝行</a>
                                            </td>
                                            <td>
                                                <span>¥</span>
                                                <span>5000</span>
                                            </td>
                                            <td>
                                                2
                                            </td>
                                            <td>
                                                10
                                            </td>
                                        </tr>


                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End: life time stats -->
        </div>
        <div class="col-md-6">
            <!--- Begin: life time stats -->
            <div class="portlet light">
                <div class="portlet-title tabbable-line">
                    <div class="caption">
                        <i class="icon-share font-red-sunglo"></i>
                        <span class="caption-subject font-red-sunglo bold uppercase">走势图</span>
                        <span class="caption-helper">近一周</span>
                    </div>
                    <!-- super:请把数据写在下面div里面-->
                    <div style="display: none">
                        <p id="amountSale">
                            [
                            ['12/23', 102540],
                            ['12/24', 120540],
                            ['12/25', 65540],
                            ['12/26', 32540],
                            ['12/27', 122540],
                            ['12/28', 65540],
                            ['12/29', 50540],
                            ['12/30', 99540],
                            ['12/31', 90540]
                            ]
                        </p>

                        <p id="amountOrder">
                            [
                            ['12/23', 102],
                            ['12/24', 112],
                            ['12/25', 65],
                            ['12/26', 32],
                            ['12/27', 84],
                            ['12/28', 70],
                            ['12/29', 50],
                            ['12/30', 102],
                            ['12/31', 87]
                            ]
                        </p>

                        <p id="amountProduct">
                            [
                            ['12/23', 122],
                            ['12/24', 122],
                            ['12/25', 85],
                            ['12/26', 22],
                            ['12/27', 84],
                            ['12/28', 98],
                            ['12/29', 50],
                            ['12/30', 122],
                            ['12/31', 97]
                            ]
                        </p>
                    </div>
                    <ul class="nav nav-tabs">
                        <li>
                            <a href="#portlet_tab3" data-toggle="tab" id="statistics_product_tab">
                                日售件数</a>
                        </li>
                        <li>
                            <a href="#portlet_tab2" data-toggle="tab" id="statistics_order_tab">
                                日成单数</a>
                        </li>
                        <li class="active">
                            <a href="#portlet_tab1" data-toggle="tab">
                                日销售额</a>
                        </li>
                    </ul>
                </div>
                <div class="portlet-body">
                    <div class="tab-content">
                        <div class="tab-pane active" id="portlet_tab1">
                            <div id="statistics_1" class="chart">
                            </div>
                        </div>
                        <div class="tab-pane" id="portlet_tab2">
                            <div id="statistics_2" class="chart">
                            </div>
                        </div>
                        <div class="tab-pane" id="portlet_tab3">
                            <div id="statistics_3" class="chart">
                            </div>
                        </div>
                    </div>
                    <div class="margin-top-20 no-margin no-border">
                        <div class="row">
                            <div class="col-md-3 col-sm-3 col-xs-6 text-stat">
										<span class="label label-success uppercase">
										今日总销售: </span>

                                <h3>
                                    <span>¥</span>
                                    <span>123,123.00</span>
                                </h3>
                            </div>
                            <div class="col-md-3 col-sm-3 col-xs-6 text-stat">
										<span class="label label-info uppercase">
										今日成单量: </span>

                                <h3><span>89</span></h3>
                            </div>
                            <div class="col-md-3 col-sm-3 col-xs-6 text-stat">
										<span class="label label-danger uppercase">
										今日销售数: </span>

                                <h3><span>120</span></h3>
                            </div>
                            <div class="col-md-3 col-sm-3 col-xs-6 text-stat">
										<span class="label label-warning uppercase">
										平均单价: </span>

                                <h3>
                                    <span>¥</span>
                                    <span>1,383.00</span>
                                </h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End: life time stats -->
        </div>
    </div>
    <!-- END PAGE CONTENT INNER -->
</pgt:container>