<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>点金子绝当淘商城</title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/person-info/update-password.css"/>"/>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>"/>

</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <jsp:include page="../../core/header-main.jsp" />
</div>


<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <jsp:include page="../vertical-my-account-directory.jsp">
            <jsp:param name="step" value="updatePassword" />
        </jsp:include>


        <!-- 详细内容列表-->
        <div class="main-box">

            <!--面包屑-->
            <div class="bread-nav">
                <p>
                    <a >个人中心</a>
                    >
                    <a href="<spring:url value="/user/updatePassword"/>">修改密码</a>
                </p>
            </div>
            <div class="main-right">
                <div class="update-content">

                    <form:form modelAttribute="user" method="post" action="/user/updatePasswordSubmit" id="login">
                    <div class="update-content-top">
                        <div class="box1">
                            <label for="">旧密码 : </label>
                            <input type="password" name="oldPassword">
                        </div>
                        <div class="box2">
                            <label for="">新密码 :</label>
                            <input type="password" name="password">
                        </div>
                        <div class="box4">
                            <label for="">确认密码 : </label>
                            <input type="password" name="password2">
                        </div>
                        <div class="box3">
                            <form:errors path="loginError" /><br><br>
                            6-20个字符，由字母，数字和符号的两种以上组合
                        </div>
                        <div class="box5">
                            <input class="d-btn" type="submit" class="button" value="保存">
                        </div>
                    </div>
                    </form:form>

                    <div class="update-content-bottom">
                        <p><h4>密码设置技巧</h4></p>
                        <p>密码设置至少6位数以上，由数字，字母和符号混合而成，安全性最高。</p>
                        <p>不要和用户名太相似，这样容易被人猜到。</p>
                        <p>不要用手机号，电话号码，生日，学号，身份证等个人信息。</p>
                        <p>在点金子，支付宝和邮箱中设置不同的密码，以免一个人账户被盗造成其他账户同时被盗。</p>
                        <p>请你每隔一段时间更新一次账号的密码。同时，新密码不应包括旧密码的内容，并且不应与旧密码相似。</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="clear-float"></div>

        <!-- 类似商品-->

        <jsp:include page="../../shopping-cart/horizontal-recommend-bar.jsp">
            <jsp:param name="excludeFavourites" value="true" />
        </jsp:include>



    </div>
</div>
<!--主脚部-->
<jsp:include page="../../core/footer-main.jsp"></jsp:include>

</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/person-info/recommend-bar"/>"></script>
</html>


