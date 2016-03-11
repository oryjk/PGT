<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctx = request.getContextPath();
    request.setAttribute("ctx", ctx);
%>
<c:set var="ctx" value="${ctx}" scope="request"/>
<c:choose>
    <c:when test="${configuration.useProxy}">
        <c:set var="ctx" value="" scope="request"/>
    </c:when>
    <c:otherwise>
        <c:set var="ctx" value="${ctx}" scope="request"/>
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8" />
    <title>点金子后台管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta content="" name="description" />
    <meta content="" name="author" />
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <%--<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />--%>
    <link href="${ctx}/resources/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${ctx}/resources/assets/admin/pages/css/login.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL SCRIPTS -->
    <!-- BEGIN THEME STYLES -->
    <link href="${ctx}/resources/assets/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/assets/global/css/plugins.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color" />
    <link href="${ctx}/resources/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css" />
    <!-- END THEME STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form class="login-form" action="/login" method="post">
        <h3 class="form-title">登陆</h3>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" style="display: block">
                <button class="close" data-close="alert"></button>
                <span>${errorMessage}</span>
            </div>
        </c:if>
        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">用户名</label>
            <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="login" value="${param.login}" />
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密码</label>
            <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" value="" />
        </div>
        <%--
        <div class="row pgt-confirm-box">
            <div class="col-xs-6">
                <a class="pgt-confirm-code" href="#">
                    <img src="" alt="" />
                </a>
            </div>
            <div class="col-xs-4">
                <a href="#">
                    看不清楚
                </a>
            </div>
        </div>
        --%>
        <div class="form-actions">
            <button type="submit" class="btn btn-success uppercase">登陆</button>
            <%--<label class="rememberme check">--%>
            <%--<input type="checkbox" name="remember" value="true" />记住我 </label>--%>
            <%--<a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a>--%>
        </div>
    </form>
    <!-- END LOGIN FORM -->
    <!-- BEGIN FORGOT PASSWORD FORM -->
    <form class="forget-form" action="index.html" method="post">
        <h3>忘记密码 ?</h3>

        <p>
            请留下您的联系方式,我们将尽快与您联系.
        </p>

        <p>
            或联系客服028-85033350
        </p>

        <div class="form-group">
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="手机号码" name="forget" />
        </div>
        <div class="form-actions">
            <button type="button" id="back-btn" class="btn btn-default">取消</button>
            <button type="submit" class="btn btn-success uppercase pull-right">确认</button>
        </div>
    </form>
    <!-- END FORGOT PASSWORD FORM -->
    <!-- BEGIN REGISTRATION FORM -->
    <!-- END REGISTRATION FORM -->
</div>
<div class="copyright">
    2015 © 成都众鑫惠联科技有限公司
</div>
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${ctx}/resources/assets/global/plugins/respond.min.js"></script>
<script src="${ctx}/resources/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${ctx}/resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${ctx}/resources/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${ctx}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/admin/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
    jQuery(document).ready(function () {
        Metronic.init(); // init metronic core components
        Layout.init(); // init current layout
        Login.init();
        Demo.init();
    });
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>