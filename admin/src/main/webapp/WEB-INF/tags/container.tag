<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="id" required="false" rtexprvalue="true" %>
<%@ attribute name="extraPage" required="false" rtexprvalue="true" %>
<%@ attribute name="pageJsPath" required="false" rtexprvalue="true" %>
<%@ attribute name="pageCssPath" required="false" rtexprvalue="true" %>
<%@ attribute name="loadJsDateInput" required="false" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<%
    String ctx = request.getContextPath();
    request.setAttribute("ctx", ctx);
%>
<c:set var="ctx" value="${ctx}" scope="request" />
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
    <link href="${ctx}/resources/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
          type="text/css">
    <link href="${ctx}/resources/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
    <link href="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/assets/global/plugins/morris/morris.css" rel="stylesheet" type="text/css">
    <!-- END PAGE LEVEL PLUGIN STYLES -->
    <!-- BEGIN PAGE STYLES -->
    <link href="${ctx}/resources/assets/admin/pages/css/tasks.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE STYLES -->
    <!-- BEGIN THEME STYLES -->
    <!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
    <link href="${ctx}/resources/assets/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/assets/global/css/plugins.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/assets/admin/layout3/css/layout.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/assets/admin/layout3/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color">
    <link href="${ctx}/resources/assets/admin/layout3/css/custom.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/core/css/table.css"/>
    <link rel="stylesheet" href="${ctx}/resources/core/css/page.css"/>
    <!-- END THEME STYLES -->
    <!--<link rel="shortcut icon" href="favicon.ico">-->

    <script>
        ctx = "${ctx}";
    </script>
</head>
<!-- END HEAD -->


<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-menu-fixed" class to set the mega menu fixed  -->
<!-- DOC: Apply "page-header-top-fixed" class to set the top menu fixed  -->
<body>
<!-- BEGIN HEADER -->
<div id="header" class="page-header">
    <!-- BEGIN HEADER TOP -->
    <div class="page-header-top">
        <div class="container">
            <!-- BEGIN LOGO -->
            <div class="page-logo">
                <a href="<spring:url value="/"/>"><img src="${ctx}/resources/assets/admin/layout3/img/logo-default.png" alt="logo"
                                          class="logo-default"></a>
            </div>
            <!-- END LOGO -->
            <!-- BEGIN RESPONSIVE MENU TOGGLER -->
            <a href="javascript:alert('hello!');" class="menu-toggler"></a>
            <!-- END RESPONSIVE MENU TOGGLER -->
            <!-- BEGIN TOP NAVIGATION MENU -->
            <div class="top-menu">
                <ul class="nav navbar-nav pull-right">
                    <!-- BEGIN INBOX DROPDOWN -->
                    <li class="dropdown dropdown-extended dropdown-dark dropdown-inbox" id="header_inbox_bar">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                           data-close-others="true">
                            <span class="circle">3</span>
                            <span class="corner"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <p>
                                    You have 14 new notifications
                                </p>
                            </li>
                            <li>
                                <ul class="dropdown-menu-list scroller" style="height: 250px;">
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-success">
                                    <i class="fa fa-plus"></i>
                                    </span>
                                            New user registered. <span class="time">
                                    Just now </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-danger">
                                    <i class="fa fa-bolt"></i>
                                    </span>
                                            Server #12 overloaded. <span class="time">
                                    15 mins </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-warning">
                                    <i class="fa fa-bell-o"></i>
                                    </span>
                                            Server #2 not responding. <span class="time">
                                    22 mins </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-info">
                                    <i class="fa fa-bullhorn"></i>
                                    </span>
                                            Application error. <span class="time">
                                    40 mins </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-danger">
                                    <i class="fa fa-bolt"></i>
                                    </span>
                                            Database overloaded 68%. <span class="time">
                                    2 hrs </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-danger">
                                    <i class="fa fa-bolt"></i>
                                    </span>
                                            2 user IP blocked. <span class="time">
                                    5 hrs </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-warning">
                                    <i class="fa fa-bell-o"></i>
                                    </span>
                                            Storage Server #4 not responding. <span class="time">
                                    45 mins </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-info">
                                    <i class="fa fa-bullhorn"></i>
                                    </span>
                                            System Error. <span class="time">
                                    55 mins </span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                    <span class="label label-sm label-icon label-danger">
                                    <i class="fa fa-bolt"></i>
                                    </span>
                                            Database overloaded 68%. <span class="time">
                                    2 hrs </span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="external">
                                <a href="#">
                                    See all notifications <i class="m-icon-swapright"></i>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- END INBOX DROPDOWN -->
                    <!-- BEGIN USER LOGIN DROPDOWN -->
                    <li class="dropdown dropdown-user dropdown-dark">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                           data-close-others="true">
                            <img alt="" class="img-circle" src="${ctx}/resources/assets/admin/layout3/img/avatar9.jpg">
                            <span class="username username-hide-mobile">超级管理员</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-default">
                            <li>
                                <a href="extra_profile.html">
                                    <i class="icon-user"></i> 我的资料 </a>
                            </li>
                            <li>
                                <a href="page_calendar.html">
                                    <i class="icon-calendar"></i> 我的收益 </a>
                            </li>
                            <li>
                                <a href="javascript:;">
                                    <i class="icon-rocket"></i> 我的产品
                                </a>
                            </li>
                            <li>
                                <a href="inbox.html">
                                    <i class="icon-envelope-open"></i> 系统信息 <span class="badge badge-danger">
								3 </span>
                                </a>
                            </li>
                            <li class="divider">
                            </li>
                            <li>
                                <a href="login.html">
                                    <i class="icon-key"></i> 退出登陆 </a>
                            </li>
                        </ul>
                    </li>
                    <!-- END USER LOGIN DROPDOWN -->
                    <!-- BEGIN USER LOGIN DROPDOWN -->
                    <li class="dropdown dropdown-extended quick-sidebar-toggler">
                        <span class="sr-only">Toggle Quick Sidebar</span>
                        <i class="icon-logout"></i>
                    </li>
                    <!-- END USER LOGIN DROPDOWN -->
                </ul>
            </div>
            <!-- END TOP NAVIGATION MENU -->
        </div>
    </div>
    <!-- END HEADER TOP -->
    <!-- BEGIN HEADER MENU -->
    <div class="page-header-menu">
        <div class="container">
            <!-- BEGIN MEGA MENU -->
            <!-- DOC: Apply "hor-menu-light" class after the "hor-menu" class below to have a horizontal menu with white background -->
            <!-- DOC: Remove data-hover="dropdown" and data-close-others="true" attributes below to disable the dropdown opening on mouse hover -->
            <div class="hor-menu ">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="<spring:url value="/"/>">首页</a>
                    </li>

                    <!-- !!!list 商品管理-->
                    <li class="menu-dropdown mega-menu-dropdown ">
                        <a data-hover="megamenu-dropdown" data-close-others="true" data-toggle="dropdown"
                           href="<spring:url value="/product/productList"/>" class="dropdown-toggle">
                            商品管理 <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<spring:url value="/product/productList"/>">
                                    <i class="fa-angle-right"></i>
                                    商品列表 </a>
                            </li>
                            <li>
                                <a href="<spring:url value="/category/categoryList"/>">
                                    <i class="fa-angle-right"></i>
                                    分类管理 </a>
                            </li>
                            <li>
                                <a href="<spring:url value="/order/order-list" />">
                                    <i class="fa-angle-right"></i>
                                    订单管理 </a>
                            </li>
                        </ul>
                    </li>

                    <!-- !!!list 投资管理-->
                    <li class="menu-dropdown mega-menu-dropdown">
                        <a data-hover="megamenu-dropdown" data-close-others="true" data-toggle="dropdown"
                           href="javascript:;" class="dropdown-toggle">
                            投资管理 <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    投资产品列表 </a>
                            </li>
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    投资申请列表 </a>
                            </li>
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    会员投资明细 </a>
                            </li>
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    投资审核 </a>
                            </li>
                        </ul>
                    </li>

                    <!-- !!!list 资源管理-->
                    <li class="menu-dropdown mega-menu-dropdown">
                        <a data-hover="megamenu-dropdown" data-close-others="true" data-toggle="dropdown"
                           href="javascript:;" class="dropdown-toggle">
                            资源管理 <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    文章管理 </a>
                            </li>
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    广告管理 </a>
                            </li>
                        </ul>
                    </li>

                    <!-- !!!list 用户管理-->
                    <li class="menu-dropdown mega-menu-dropdown">
                        <a data-hover="megamenu-dropdown" data-close-others="true" data-toggle="dropdown"
                           href="javascript:;" class="dropdown-toggle">
                            用户管理 <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    我的信息 </a>
                            </li>
                            <li class=" dropdown-submenu">
                                <a href=":;">
                                    <i class="icon-briefcase"></i>
                                    管理员管理 </a>
                                <ul class="dropdown-menu">
                                    <li class=" ">
                                        <a href="<spring:url value="/internal/iu-list" />">
                                            管理员列表</a>
                                    </li>
                                    <li class=" ">
                                        <a href="table_basic.html">
                                            管理员权限管理 </a>
                                    </li>
                                    <li class=" ">
                                        <a href="table_basic.html">
                                            行为日志 </a>
                                    </li>
                                </ul>
                            </li>

                            <li class=" dropdown-submenu">
                                <a href=":;">
                                    <i class="icon-briefcase"></i>
                                    会员管理 </a>
                                <ul class="dropdown-menu">
                                    <li class=" ">
                                        <a href="table_basic.html">
                                            会员列表</a>
                                    </li>
                                    <li class=" ">
                                        <a href="table_basic.html">
                                            行为日志 </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>

                    <!-- !!!list 系统设置-->
                    <li class="menu-dropdown classic-menu-dropdown ">
                        <a data-hover="megamenu-dropdown" data-close-others="true" data-toggle="dropdown"
                           href="javascript:;">
                            系统设置 <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    网站设置 </a>
                            </li>
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    数据备份 </a>
                            </li>
                            <li>
                                <a href=":;">
                                    <i class="fa-angle-right"></i>
                                    帮助提示 </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- END MEGA MENU -->
        </div>
    </div>
    <!-- END HEADER MENU -->

</div>
<!-- END HEADER -->


<!-- BEGIN PAGE CONTAINER -->
<div class="page-container" id="main">
    <div class="page-content">
        <div class="container-fluid">
            <jsp:doBody />
        </div>
    </div>

</div>
<jsp:include page="${extraPage}" />

<!-- END PAGE CONTAINER -->

    <!--super: 确认操作modal-->
    <div class="modal fade bs-modal-sm" id="confirmModal" tabindex="-1" role="basic" aria-hidden="true" style="display:
    none;">
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
    <button type="button" class="btn blue" id="comfirmBtn">确定</button>
    </div>
    </form>
    </div>
    </div>
    <!-- /.modal-content -->

<!-- BEGIN JAVASCRIPTS (Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${ctx}/resources/assets/global/plugins/respond.min.js"></script>
<script src="${ctx}/resources/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${ctx}/resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!--END load-->
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${ctx}/resources/assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<%--<!-- BEGIN PAGE LEVEL PLUGINS -->--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/jquery.vmap.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js" type="text/javascript"></script>--%>
<%--<!-- IMPORTANT! fullcalendar depends on jquery-ui.min.js for drag & drop support -->--%>
<%--<script src="${ctx}/resources/assets/global/plugins/morris/raphael-min.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/global/plugins/jquery.sparkline.min.js" type="text/javascript"></script>--%>
<%--<!-- END PAGE LEVEL PLUGINS -->--%>
<%--<!-- BEGIN PAGE LEVEL SCRIPTS -->--%>
<%--<script src="${ctx}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/admin/layout3/scripts/layout.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/admin/layout2/scripts/quick-sidebar.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/admin/layout3/scripts/demo.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/admin/pages/scripts/index3.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/resources/assets/admin/pages/scripts/tasks.js" type="text/javascript"></script>--%>

<script src="${ctx}/resources/core/js/jquery.form.js"></script>
<%--<!-- END PAGE LEVEL SCRIPTS -->--%>
<%--<script>--%>
    <%--jQuery(document).ready(function () {--%>
        <%--Metronic.init(); // init metronic core componets--%>
        <%--Layout.init(); // init layout--%>
        <%--Demo.init(); // init demo(theme settings page)--%>
        <%--QuickSidebar.init(); // init quick sidebar--%>
        <%--Index.init(); // init index page--%>
        <%--Tasks.initDashboardWidget(); // init tash dashboard widget--%>
    <%--});--%>
<%--</script>--%>

<c:if test="${loadJsDateInput}">
    <link href="${ctx}/resources/core/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/resources/core/css/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css">
    <script src="${ctx}/resources/core/js/jquery-ui.min.js"></script>
    <script src="${ctx}/resources/core/js/jquery-ui-timepicker-addon.js"></script>
    <!--日期插件框-->
    <div id="jcDate" style="left: 0; top: 28px; display: none;"><input
            type="hidden" value="11" id="dateHideText">
        <div id="jcDateTt"><a id="d_prev"></a>
            <div><span>2015</span><b>年</b><samp>12</samp><b>月</b></div>
            <a id="d_next"></a></div>
        <dl id="jcYeas" style="display: none;">
            <dt>2006年</dt>
            <dt>2007年</dt>
            <dt>2008年</dt>
            <dt>2009年</dt>
            <dt>2010年</dt>
            <dt>2011年</dt>
            <dt>2012年</dt>
            <dt>2013年</dt>
            <dt>2014年</dt>
            <dd class="visited">2015年</dd>
            <dt>2016年</dt>
            <dt>2017年</dt>
            <dt>2018年</dt>
            <dt>2019年</dt>
            <dt>2020年</dt>
            <dt>2021年</dt>
            <dt>2022年</dt>
            <dt>2023年</dt>
        </dl>
        <dl id="jcMonth" style="display: none;">
            <dt>1月</dt>
            <dt>2月</dt>
            <dt>3月</dt>
            <dt>4月</dt>
            <dt>5月</dt>
            <dt>6月</dt>
            <dt>7月</dt>
            <dt>8月</dt>
            <dt>9月</dt>
            <dt>10月</dt>
            <dt>11月</dt>
            <dt class="visited">12月</dt>
        </dl>
        <div id="jcDayWrap">
            <dl id="jcDayCon">
                <dt>日</dt>
                <dt>一</dt>
                <dt>二</dt>
                <dt>三</dt>
                <dt>四</dt>
                <dt>五</dt>
                <dt>六</dt>
            </dl>
            <div id="jcDateMax" style="left: -1771px;">
                <ul>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li>31</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                </ul>
                <ul>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li>10</li>
                    <li>11</li>
                    <li>12</li>
                    <li>13</li>
                    <li>14</li>
                </ul>
                <ul>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li>10</li>
                    <li>11</li>
                </ul>
                <ul>
                    <li>29</li>
                    <li>30</li>
                    <li>31</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                </ul>
                <ul>
                    <li>26</li>
                    <li>27</li>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                </ul>
                <ul>
                    <li>31</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li>10</li>
                    <li>11</li>
                </ul>
                <ul>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                </ul>
                <ul>
                    <li>26</li>
                    <li>27</li>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li>31</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                </ul>
                <ul>
                    <li>30</li>
                    <li>31</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li>10</li>
                </ul>
                <ul>
                    <li>27</li>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                </ul>
                <ul>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li>10</li>
                    <li>11</li>
                    <li>12</li>
                </ul>
                <ul class="dateV">
                    <li>29</li>
                    <li>30</li>
                    <li class="jcDateColor">1</li>
                    <li class="jcDateColor">2</li>
                    <li class="jcDateColor">3</li>
                    <li class="jcDateColor">4</li>
                    <li class="jcDateColor">5</li>
                    <li class="jcDateColor">6</li>
                    <li class="jcDateColor">7</li>
                    <li class="jcDateColor">8</li>
                    <li class="jcDateColor">9</li>
                    <li class="jcDateColor">10</li>
                    <li class="jcDateColor visited">11</li>
                    <li class="jcDateColor">12</li>
                    <li class="jcDateColor">13</li>
                    <li class="jcDateColor">14</li>
                    <li class="jcDateColor">15</li>
                    <li class="jcDateColor">16</li>
                    <li class="jcDateColor">17</li>
                    <li class="jcDateColor">18</li>
                    <li class="jcDateColor">19</li>
                    <li class="jcDateColor">20</li>
                    <li class="jcDateColor">21</li>
                    <li class="jcDateColor">22</li>
                    <li class="jcDateColor">23</li>
                    <li class="jcDateColor">24</li>
                    <li class="jcDateColor">25</li>
                    <li class="jcDateColor">26</li>
                    <li class="jcDateColor">27</li>
                    <li class="jcDateColor">28</li>
                    <li class="jcDateColor">29</li>
                    <li class="jcDateColor">30</li>
                    <li class="jcDateColor">31</li>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                </ul>
            </div>
            <div id="jcDateBtn"><samp>今天</samp><span>清空</span><a
                    id="d_sub">确定</a>
                <div id="jcTimeWrap"><input type="text" value=""><label>:</label><input
                        type="text" value=""></div>
            </div>
        </div>
    </div>
    <link rel="stylesheet" href="${ctx}/resources/assets/others/jcDate/jcDate.css"/>
    <link rel="stylesheet" href="${ctx}/resources/core/css/myself-date.css"/>
    <link rel="stylesheet" href="${ctx}/resources/core/css/table.css"/>
    <script src="${ctx}/resources/assets/others/jcDate/jQuery-jcDate.js"></script>
</c:if>
<c:if test="${not empty pageJsPath}">
    <script src="${ctx}${pageJsPath}"></script>
</c:if>
<c:if test="${not empty pageCssPath}">
    <link rel="stylesheet" href="${ctx}${pageCssPath}"/>
</c:if>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>