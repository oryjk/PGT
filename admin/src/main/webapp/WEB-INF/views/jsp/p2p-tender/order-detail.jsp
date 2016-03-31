<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.5
Version: 4.1.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
  <meta charset="utf-8">
  <title>点金子后台管理系统</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1" name="viewport">
  <meta content="" name="description">
  <meta content="" name="author">
  <!-- BEGIN GLOBAL MANDATORY STYLES -->
  <!--<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css">-->
  <link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
        type="text/css">
  <link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
  <!-- END GLOBAL MANDATORY STYLES -->
  <!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
  <link href="../assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css">
  <link href="../assets/global/plugins/morris/morris.css" rel="stylesheet" type="text/css">
  <!-- END PAGE LEVEL PLUGIN STYLES -->
  <!-- BEGIN PAGE STYLES -->
  <link href="../assets/admin/pages/css/tasks.css" rel="stylesheet" type="text/css"/>
  <!-- END PAGE STYLES -->
  <!-- BEGIN THEME STYLES -->
  <!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
  <link href="../assets/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css">
  <link href="../assets/global/css/plugins.css" rel="stylesheet" type="text/css">
  <link href="../assets/admin/layout3/css/layout.css" rel="stylesheet" type="text/css">
  <link href="../assets/admin/layout3/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color">
  <link href="../assets/admin/layout3/css/custom.css" rel="stylesheet" type="text/css">
  <!-- END THEME STYLES -->

  <!--<link rel="shortcut icon" href="favicon.ico">-->
  <style>
    #content td {
      vertical-align: middle;
    }
    #content .productlist-box {
      overflow: visible;
    }
    #content .productlist-face-box {
      text-align: center;
      padding: 2px 8px;
    }
    #content .productlist-face-box img {
      width: 40px;
      height: 40px;
    }
    .pgt-modify {
      padding-bottom: 20px;
    }

  </style>
</head>
<!-- END HEAD -->


<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-menu-fixed" class to set the mega menu fixed  -->
<!-- DOC: Apply "page-header-top-fixed" class to set the top menu fixed  -->
<body>
<!-- BEGIN HEADER -->
<div id="header" class="page-header"></div>
<!-- END HEADER -->


<!-- BEGIN PAGE CONTAINER -->
<div class="page-container" id="content">
  <div class="page-content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-xs-12">
          <ul class="page-breadcrumb breadcrumb">
            <li>
              <a href="#">首页</a>
              <i class="fa fa-circle"></i>
            </li>
            <li>
              <a href="#">用户管理</a>
              <i class="fa fa-circle"></i>
            </li>
            <li>
              <a href="#">管理员管理</a>
              <i class="fa fa-circle"></i>
            </li>
            <li class="active">
              <a href="#">管理员列表</a>
              <i class="fa fa-circle"></i>
            </li>
            <li>
              <a href="#">新增管理员</a>
            </li>
          </ul>
        </div>
      </div>

      <!-- super:把错误内容放在span里面,有两种提示框 alert-danger 和 alert-success 两种.如果不需要显示时把display改为none-->
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
              <!-- super:新增用户时显示-->
              <div class="caption">
                <i class="fa fa-gift"></i>订单详情
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
                            <label class="control-label col-md-4">订单号</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                ${p2pOrder.id}
                              </p>
                            </div>
                          </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                          <div class="form-group">
                            <label class="control-label col-md-4">商品数量</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                <c:set var="itemQuantity" value="0"/>
                                <c:set var="occupy" value="false"/>
                                <c:forEach var="item" items="${p2pOrder.commerceItems}">
                                  <c:set var="itemQuantity" value="${itemQuantity + item.quantity}"/>
                                  <c:if test="${item.occupy}">
                                    <c:set var="occupy" value="true"/>
                                  </c:if>
                                </c:forEach>
                                ${itemQuantity}
                              </p>
                            </div>
                          </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                          <div class="form-group">
                            <label class="control-label col-md-4">总金额</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                ${p2pOrder.total}
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-4">
                          <div class="form-group">
                            <label class="control-label col-md-4">用户id</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                ${p2pOrder.userId}
                              </p>
                            </div>
                          </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                          <div class="form-group">
                            <label class="control-label col-md-4">产品状态</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                <c:if test="${p2pOrder.status eq 30}">
                                  在当
                                </c:if>
                                <c:if test="${p2pOrder.status eq 50}">
                                  绝当
                                </c:if>
                                <c:if test="${p2pOrder.status eq 60}">
                                  赎当
                                </c:if>
                                <c:if test="${p2pOrder.status eq 100}">
                                  <c:if test="${occupy}">
                                    绝当
                                  </c:if>
                                  <c:if test="${not occupy}">
                                    赎当
                                  </c:if>
                                </c:if>
                              </p>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <div class="form-group">
                            <label class="control-label col-md-4">订单状态</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                <c:choose>
                                  <c:when test="${p2pOrder.status eq 10}">
                                    未填收获地址
                                  </c:when>
                                  <c:when test="${p2pOrder.status eq 20 or 25 eq p2pOrder.status}">
                                    未支付
                                  </c:when>
                                  <c:when test="${p2pOrder.status = 30}">
                                    已支付
                                  </c:when>
                                  <c:when test="${p2pOrder.status = 50}">
                                    待发货
                                  </c:when>
                                  <c:when test="${p2pOrder.status = 60}">
                                    待赔付
                                  </c:when>
                                  <c:when test="${p2pOrder.status = 80}">
                                    发货中
                                  </c:when>
                                  <c:when test="${p2pOrder.status = 100}">
                                    已完成
                                  </c:when>
                                  <c:when test="${p2pOrder.status = -10}">
                                    已取消
                                  </c:when>
                                </c:choose>
                              </p>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <div class="form-group">
                            <label class="control-label col-md-4">下单时间</label>
                            <div class="col-md-8">
                              <p class="form-control-static">
                                <fmt:formatDate value="${p2pOrder.submitDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
                                商品图
                              </th>
                              <th>
                                商品名称
                              </th>
                              <th>
                                投资ID
                              </th>
                              <th>
                                投资名称
                              </th>

                              <th>
                                收货人
                              </th>
                              <th>
                                收货人手机号
                              </th>
                              <th>
                                收货人座机号
                              </th>
                              <th>
                                配送地址
                              </th>
                              <th>
                                产品状态
                              </th>
                              <th>
                                赔付
                              </th>
                              <th>
                                到期时间
                              </th>
                              <th>
                                发货操作
                              </th>

                            </tr>
                            </thead>
                            <tbody>
                            <tr class="gradeX odd" role="row">
                              <td class="productlist-face-box">
                                <img src="" alt=""/>
                              </td>
                              <td>
                                小蛮妖珠宝
                              </td>
                              <td>
                                6000.00
                              </td>
                              <td >
                                11111
                              </td>
                              <td>
                                周杰伦
                              </td>
                              <td>
                                180000000
                              </td>
                              <td>
                                028-88888888
                              </td>
                              <td>
                                成都市下一站都市
                              </td>
                              <td>
                                <!--super: blue:赎当 yellow在当 green绝当 red取消-->
                                <div class="btn-group">
                                  <a class="btn btn-xs yellow " href="javascript:;" data-toggle="dropdown">
                                    在当 <i class="fa fa-angle-down"></i>
                                  </a>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="javascript:;" data-pgt-btn="modify">
                                        赎当 </a>
                                    </li>
                                    <li>
                                      <a href="javascript:;"  data-pgt-btn="modify">
                                        在当 </a>
                                    </li>
                                    <li>
                                      <a href="javascript:;"  data-pgt-btn="modify">
                                        绝当 </a>
                                    </li>
                                    <li>
                                      <a href="javascript:;"  data-pgt-btn="modify">
                                        取消 </a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                              <td>
                                无
                              </td>
                              <td>
                                2016年10月1日
                              </td>
                              <td>
                                无
                              </td>
                            </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- 收货人信息-->
                  <c:set var="address" value="${p2pOrder.shippingVO.shippingAddress}"/>
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
                                ${address.name}
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
                                ${address.phone}
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
                                ${address.telephone}
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
                                ${address.province}-${address.city}-${address.district}
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
                                ${address.address}
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
                  <c:set var="payment" value="${p2pOrder.payment}"/>
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
                                <fmt:formatDate value="${p2pInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                              </td>
                              <td>
                                <c:choose>
                                  <c:when test="${payment.type eq 1}">
                                    支付宝支付
                                  </c:when>
                                  <c:when test="${payment.type eq 2}">
                                    易宝支付
                                  </c:when>
                                  <c:when test="${payment.type eq 3}">
                                    微信支付
                                  </c:when>
                                </c:choose>
                              </td>
                              <td>
                                ${payment.amount}
                              </td>
                              <td>
                                ${payment.trackingNo}
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
    </div>
  </div>
</div>

<!--modal-->
<div class="modal fade bs-modal-sm" id="confirmModal" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">确认</h4>
      </div>
      <form action="#" class="form-horizontal">
        <div class="modal-body" id="confirmText">
          物流单号：111111111111111 <br>
          确认发货？
        </div>
        <div class="modal-footer">
          <button type="button" class="btn default" data-dismiss="modal">取消</button>
          <a href="#" type="button" class="btn blue">确定</a>
        </div>
      </form>
    </div>
  </div>
  <!-- /.modal-content -->
  <!-- /.modal-dialog -->
</div>

<!-- END PAGE CONTAINER -->

<!-- BEGIN JAVASCRIPTS (Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>

<!--BEGIN super: load 本段内容在套模版时不引入,直接在相应位置引入jsp即可-->
<script>
  $('#header').load('../core/html/header.html');
</script>
<!--END load-->

<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>

<!-- END CORE PLUGINS -->

<script>
  $('#modifySwitch').click(function() {
    var that = $(this);

    that.parent().siblings('.pgt-scan').toggle();
    that.parent().siblings('.pgt-modify').toggle();
  });

  $(document).on('click', '[data-pgt-btn="receipt"]', function() {
    $('#confirmModal').modal('show');
  });
  $(document).on('click', '[data-pgt-btn="receipt1"]', function() {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确认赔付?')
  });



</script>

<!--&lt;!&ndash; END JAVASCRIPTS &ndash;&gt;-->
</body>
<!-- END BODY -->
</html>
