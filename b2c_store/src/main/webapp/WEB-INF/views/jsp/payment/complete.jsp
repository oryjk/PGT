<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/shopping-cart/cart.css"/>" />
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <div class="top-box">
        <div class="top-status">
            <div class="status-box">
                <ul class="top-nav">
                    <!--<li>
                        <a href="#">手机绝当品</a>
                    </li>-->
                    <li><a href="<spring:url value="/helpcenter/query"/>">帮助中心</a></li>
                    <!--<li><a href="#">网站导航</a></li>-->
                </ul>
                <ul class="have-login">
                    <li>
                        <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"><span> 欢迎您：</span><span>${currentUser.username}</span></a>
                    </li>
                    <li><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>">账户管理</a></li>
                    <li><a href="<spring:url value="${urlConfiguration.logoutPage}"/>">退出登录</a></li>
                </ul>
            </div>
        </div>
        <div class="top-banner">
            <div class="top-banner-box">
                <a href="#"></a>
            </div>
        </div>
    </div>
    <div class="head-box">
        <div class="logo-box">
            <h1>
                <a href="#">
                    购物车
                </a>
            </h1>
            <ul id="step" class="step4">
                <li>
                    <h3 class="step1-text">我的购物车</h3>
                </li>
                <li>
                    <h3 class="step2-text">确认订单</h3>
                </li>
                <li>
                    <h3 class="step3-text">选择支付方式</h3>
                </li>
                <li>
                    <h3 class="step4-text">支付成功</h3>
                </li>
            </ul>
        </div>
    </div>
</div>

<!--正文-->
<div class="content-box">
    <div id="content4" class="content-4" style="display: block">
        <div class="content-title">
            <h2>支付成功</h2>
        </div>

        <div class="error"></div>

        <form>
            <fieldset class="success-box">
                <p class="look-order">
                    <a class="link-btn" href="<c:url value="/myAccount/orderHistory"/>">查看我的订单</a>
                </p>
                <div class="row">
                    <h3>支付已成功</h3>
                </div>
                <div class="row">
                    <h4 class="point-p">支付金额: <span class="cost">¥<span>${orderTotal }</span></span></h4>
                    <h3 >订单号: <span>${orderId }</span></h3>
                </div>
                <div class="row">
                    <p class="point-p">如果您有任何疑问请及时联系客服,感谢您的支持!</p>
                </div>
                <div class="row row-division">
                    <p>点金子提供在线支付保障,请您放心购买.</p>
                </div>
                <div class="row">
                    <p>支付成功后点金子会尽快为您发货,请您耐心等待.</p>
                </div>
                <div class="row">
                    <a class="right-a link-btn" href="<c:url value="/"/>"> > 返回首页</a>
                </div>
            </fieldset>
        </form>
    </div>

    <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
</div>

<!--主脚部-->
<div class="footer" id="footer">
    <div class="goods-box">
        <div class="our-goods">
            <div class="our-good">
                <div class="icon-box">
                    <i class="foundicon-people"></i>
                </div>
                <div class="good-text">
                    <h4>专家鉴定</h4>
                    <p>专业鉴定团队</p>
                    <p>安全可靠</p>
                </div>
            </div>
            <div class="our-good">
                <div class="icon-box">
                    <i class="foundicon-heart"></i>
                </div>
                <div class="good-text">
                    <h4>用心推荐</h4>
                    <p>我们的专心和用心</p>
                    <p>您的放心</p>
                </div>
            </div>
            <div class="our-good">
                <div class="icon-box">
                    <i class="foundicon-star"></i>
                </div>
                <div class="good-text">
                    <h4>五星级服务</h4>
                    <p>线上线下服务同步</p>
                    <p>广受好评</p>
                </div>
            </div>
            <div class="our-good">
                <div class="icon-box">
                    <i class="foundicon-clock"></i>
                </div>
                <div class="good-text">
                    <h4>发货及时</h4>
                    <p>每天四次发货</p>
                    <p>便捷高效</p>
                </div>
            </div>
            <div class="our-good">
                <div class="icon-box">
                    <i class="foundicon-smiley"></i>
                </div>
                <div class="good-text">
                    <h4>快乐购物</h4>
                    <p>体会快乐的购物</p>
                    <p>期待您的笑脸</p>
                </div>
            </div>
        </div>
    </div>

    <div class="about-us">
        <ul>
            <li><h3>购物与支付</h3></li>
            <li><a href="">购物流程</a></li>
            <li><a href="">订单查询</a></li>
            <li><a href="">支付方式</a></li>
            <li><a href="">发票领取</a></li>
        </ul>
        <ul>
            <li><h3>配送说明</h3></li>
            <li><a href="">配送方式和时间</a></li>
            <li><a href="">配送费用和签收</a></li>
            <li><a href=""></a></li>
        </ul>
        <ul>
            <li><h3>关于我们</h3></li>
            <li><a href="">点金子</a></li>
            <li><a href="">点金子绝当品</a></li>
            <li><a href="">联系我们</a></li>
            <li><a href="">招贤纳士</a></li>
            <li><a href="">业务范围</a></li>
            <li><a href="">隐私声明</a></li>
        </ul>
        <ul>
            <li><h3>售后服务</h3></li>
            <li><a href="">退换货政策</a></li>
            <li><a href="">退款说明</a></li>
        </ul>
        <ul>
            <li><h3>会员中心</h3></li>
            <li><a href="">会员等级</a></li>
            <li><a href="">账号管理</a></li>
            <li><a href="">投诉建议</a></li>
        </ul>
        <div class="us">
            <div class="our-app">
                <a class="apple-dimension" href="#">
                    <img src="../core/images/footer/apple.png" alt="苹果客户端下载"/>
                    <h3>苹果客户端</h3>
                </a>
                <a class="android-dimension" href="#">
                    <img src="../core/images/footer/android.png" alt="安卓客户端下载"/>
                    <h3>安卓客户端</h3>
                </a>
                <a class="online-server" href="#">
                    <img src="../core/images/footer/weixin.jpeg" alt="微信客户端下载"/>
                    <h3>微信公众号</h3>
                </a>
                <a class="online-server" href="#">
                    <img src="../core/images/footer/apple.png" alt="点金子在线投资"/>
                    <h3>点金子投资</h3>
                </a>
            </div>
            <div class="our-info">
                <h3>点金子总部地址</h3>
                <p>成都市武侯区某某某某某某某某</p>
                <p>咨询热线：028-85033350</p>
                <p>企业邮箱：zxhl998@163.com</p>
            </div>
        </div>
    </div>

    <div class="approve-box">
        <div class="approve">
            <a href="#">
                <img src="../core/images/footer/approve-0.png" alt=""/>
            </a>
            <a href="#">
                <img src="../core/images/footer/approve-1.jpg" alt=""/>
            </a>
            <a href="#">
                <img src="../core/images/footer/approve-2.html" alt=""/>
            </a>
            <a href="#">
                <img src="../core/images/footer/approve-3.png" alt=""/>
            </a>
            <a href="#">
                <img src="../core/images/footer/approve-4.jpg" alt=""/>
            </a>
        </div>
        <p>蜀ICP备15022028号 © 2015 dianjinzi, Inc. All rights reserved.</p>
    </div>
</div>
</body>
<script src="../core/js/require.js" defer async="true" data-main="../shopping-cart/cart"></script>

</html>