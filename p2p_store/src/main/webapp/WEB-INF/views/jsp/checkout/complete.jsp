<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/buy/buy.css"/>
    <script type="application/javascript" src="/resources/detail/detail.js"></script>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp" />
<!--header end-->

<!--step begin-->
<!--super: 下面四步依次加上step-1到step-4-->
<ul class="step step-4">
    <li>订单详情</li>
    <li>订单确认</li>
    <li>支付</li>
    <li>完成</li>
</ul>
<!--step end-->

<!--content begin-->
<div class="main-all">

    <div class="pay-end">
        <h2>支付成功</h2>
        <div class="pay-end-content">
            <div class="pay-end-font">
                <div class="pay-end-font-num">
                    <h3>支付成功！</h3> <a class="link-btn" href="">查看订单详情</a>
                </div>
                <div class="pay-end-font-num">
                    <h3>订单号：<span>${orderId}</span></h3>
                    <div>已支付金额：<span>${orderTotal}元</span></div>
                </div>
                <div class="pay-end-font-num">
                    <h3>如果您有任何疑问请及时联系客服，感谢您的支持。</h3>
                </div>
                <div class="pay-end-font-help">
                    点金子网提供在线交易保障，请您放心购买。<br>
                    支付成功后，如果该产品成为绝当品，我们将第一时间给您发货，请您耐心等待。<br>
                    如果该产品已被赎当，我们将根据每个产品的赔付率给您进行赔付，祝您购物愉快。<br>
                </div>
                <div class="pay-end-font-num">
                    <a class="link-btn" href="/">>返回首页</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp" />
<jsp:include page="../core/baidu.jsp" />
<!--footer end-->
</body>
</html>