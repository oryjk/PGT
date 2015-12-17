<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
     <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/pay-binding/pay-binding.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>" />
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
			<jsp:include page="../vertical-my-account-directory.jsp"/>
            <!-- 详细内容列表-->
            <div id="main" class="main-box">

	            <!--面包屑-->
	            <div class="bread-nav">
	                <p>
	                    <a href="#">个人中心</a>
	                    &gt;
	                    <a href="#">支付绑定</a>
	                </p>
	            </div>
	
	            <!-- 未绑定-->
	            <div class="no-binding" style="display: block">
	                <div class="message-box">
	                    <p>读取易宝账户信息错误！请重试。</p>
	                </div>
	            </div>
	        </div>


            <div class="clear-float"></div>

            <!-- 类似商品-->
            <div class="similar-box">
                <h2>类似商品</h2>

                <ul class="similar" id="rowList">
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-1.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>1698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-1.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>2698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-2.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>3698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-3.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>4098.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-4.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>5798.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>6698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>7698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>8698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>9698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>10698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>11698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>12698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>13698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>14698.00</span>
                        </p>
                    </li>
                    <li>
                        <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                        <a class="similar-name" href="#">
                            丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                        </a>
                        <p class="similar-cost">
                            ¥
                            <span>15698.00</span>
                        </p>
                    </li>
                </ul>

                <div class="move-left-box">
                    <a href="#"  id="moveLeft"><</a>
                </div>
                <div class="move-right-box">
                    <a href="#"  id="moveRight">></a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../../core/footer-main.jsp" />
</body>
<script
        src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
        data-main="<spring:url value="${juedangpinStaticPath}/my-account/collection/collection.js"/>"></script>
</html>
