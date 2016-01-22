<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath }/resources/static/Modify-password/Modify-password.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
</head>
<body>
<div class="header">
    <a href="#" class="arrow"></a>
    <div class="font">修改密码</div>
    <a href="#" class="dian">
        <ul class="menu">
            <li class="menu2"><a href="#">首页</a></li>
            <li class="menu3"><a href="#">分类</a></li>
            <li class="menu4"><a href="#">搜索</a></li>
            <li class="menu5"><a href="#">购物车</a></li>
            <li class="menu1"><a href="#">我的账户</a></li>
        </ul>
    </a>
</div>

<form action="${pageContext.request.contextPath }/user/updatePasswordSubmit" method="post">
<div class="name">
    <div class="kong2"></div>
    <span>原密码：</span><input name="oldpassword" class="text" type="password" >
    <div class="kong2"></div>
</div>

<div class="name1">
    <div class="kong2"></div>
    <span>新密码：</span><input name="password" class="text" type="password" >
    <div class="kong2"></div>
</div>

<div class="name1">
    <div class="kong3"></div>
    <span>确认密码：</span><input name="password2" class="text" type="password" >
    <div class="kong2"></div>
</div>
<div class="font0">
    <p><h2>密码设置技巧</h2></p>

    <p>密码设置至少6位数以上，由数字，字母和符号混合而成，安全性最高。</p>

    <p>不要和用户名太相似，这样容易被人猜到。</p>

    <p>不要用手机号，电话号码，生日，学号，身份证等个人信息。</p>

    <p>在点金子，支付宝和邮箱中设置不同的密码，以免一个人账户被盗造成其他账户同时
    被盗。</p>

    <p>请你每隔一段时间更新一次账号的密码。同时，新密码不应包括旧密码的内容，并且
    不应与旧密码相似。</p>
</div>


    <input type="submit" class="btn-clean" value="保存">

</form>
<%@include file="../../common/footer.jsp" %>


</body>
</html>