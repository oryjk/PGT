<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
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
                    <li>
                        <a href="#">手机绝当品</a>
                    </li>
                    <li><a href="#">帮助中心</a></li>
                    <li><a href="#">网站导航</a></li>
                </ul>
                <ul class="have-login">
                    <li>
                        <a href="<spring:url value="${urlConfiguration.myAccountPage}"/>"><span> 欢迎您：</span><span>${currentUser.username}</span></a>
                    </li>
                   	<li><a href="<spring:url value="${urlConfiguration.myAccountPage}"/>">账户管理</a></li>
                    <li><a href="<spring:url value="${urlConfiguration.logoutPage}"/>">退出登陆</a></li>
                </ul>
            </div>
        </div>
        <div class="top-banner">
            <div class="top-banner-box">
                <a href="#"></a>
            </div>
        </div>
    </div>
    <div class="logo-box">
        <h1>
            <a href="<spring:url value="${urlConfiguration.homePage}"/>">
                购物车
            </a>
        </h1>
        <ul id="step" class="step3">
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

<!--正文-->
<div class="content-box">
    <div id="content3" class="content-3" style="display: block">
        <div class="content-title">
            <h2>收银台</h2>
        </div>
        <form action="<c:url value="/payment/gateway"/>" method="post" >
            <fieldset class="success-box">
                <p class="look-order">
                    <a class="link-btn" href="#">查看我的订单</a>
                </p>
                <div class="row">
                    <h3>订单提交成功,请您尽快付款!</h3>
                </div>
                <div class="row">
                    <h4 class="point-p">需付金额: <span class="cost">¥<span>${order.total }</span></span></h4>
                    <h3 >订单号: <span>${order.id }</span></h3>
                </div>
                <div class="row">
                    <p class="point-p">商品在您未付款前随时可能售出,如付款出现问题请及时联系客服,感谢您的支持.</p>
                </div>
                <div class="row">
                    <p class="point-p">请您在订单完成之后<span>30</span>分钟内付款,否则订单会自动取消.</p>
                </div>
                <div class="row row-division">
                    <p>点金子提供在线支付保障,请您放心购买.</p>
                </div>
                <div class="row">
                    <p>支付成功后点金子会尽快为您发货,请您耐心等待.</p>
                </div>
            </fieldset>

            <h3>支付方式 <span>点击确认后支付会跳转到相应的支付界面</span></h3>
            <fieldset class="pay-way">
                <div class="row">
                    <label  class="yeepay" for="">
                        <input type="radio" name="method" value="yeepay"/>
                    </label>
                    <label class="zhifubao" for="">
                        <input type="radio" name="method" value="alipay"/>
                    </label>
                    <input class="d-btn" type="submit" value="确认支付"/>
                </div>

            </fieldset>
        </form>
    </div>
    <div id="recommend" class="recommend">
        <ul id="tab" class="tab">
            <li class="choose"><h2 data-tab="0">商品详情</h2></li>
            <li><h2 data-tab="1">我的收藏</h2></li>
            <li><h2 data-tab="2">最近浏览</h2></li>
        </ul>
        <div class="similar-box">
            <ul class="similar" id="rowList1">
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
            </ul>
            <div class="move-left-box">
                <a href="#"  id="moveLeft1"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight1">></a>
            </div>
        </div>
        <div class="similar-box" style="display: none">
            <ul class="similar" id="rowList2">
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>2698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
            </ul>
            <div class="move-left-box">
                <a href="#"  id="moveLeft2"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight2">></a>
            </div>
        </div>
        <div class="similar-box" style="display: none">
            <ul class="similar" id="rowList3">
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>3698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>698.00</span>
                    </p>
                </li>
            </ul>
            <div class="move-left-box">
                <a href="#"  id="moveLeft3"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight3">></a>
            </div>
        </div>

    </div>

</div>

<!--主脚部-->
    <jsp:include page="../core/footer-main.jsp"/>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" defer async="true" data-main="<spring:url value="${juedangpinStaticPath}/shopping-cart/geteway.js"/>"></script>

</html>