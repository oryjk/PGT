<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="${juedangpinStaticPath}" var="staticPath"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/person-info/address.css"/>"/>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>"/>
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <jsp:include page="../../core/header-main.jsp"></jsp:include>
</div>

<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <div class="aside">
            <h2>精品推荐</h2>
            <ul>
                <li><a class="menu-level-1" href="">我的订单</a></li>
                <li>
                    <a class="menu-level-1" href="">收藏夹</a>
                    <ul>
                        <li><a class="menu-level-end" href="#">我的收藏</a></li>
                        <li><a class="menu-level-end" href="#">最近浏览</a></li>
                    </ul>
                </li>
                <li><a class="menu-level-1" href="">支付绑定</a></li>
                <li>
                    <a class="menu-level-1" href="#">个人中心</a>
                    <ul>
                        <li><a class="menu-level-end" href="#">个人信息</a></li>
                        <li><a class="menu-level-end" href="#">修改密码</a></li>
                        <li><a class="menu-level-end" href="address">地址管理</a></li>
                    </ul>
                </li>
                <li>
                    <a class="menu-level-1" href="#">帮助中心</a>
                    <ul>
                        <li>
                            <a class="menu-level-2" href="#">购物与支付</a>
                            <ul>
                                <li><a class="menu-level-end" href="#">购物流程</a></li>
                                <li><a class="menu-level-end" href="#">支付方式</a></li>
                                <li><a class="menu-level-end" href="#">发票制度</a></li>
                            </ul>
                        </li>
                        <li>
                            <a class="menu-level-2" href="#">配送说明</a>
                            <ul></ul>
                        </li>
                        <li>
                            <a class="menu-level-2" href="#">关于我们</a>
                            <ul>
                            </ul>
                        </li>
                        <li><a class="menu-level-2" href="#">售后服务</a></li>
                    </ul>
                </li>
            </ul>
        </div>

        <!-- 详细内容列表-->
       	<jsp:include page="addressBook.jsp"></jsp:include>

        <div class="clear-float"></div>

        <!-- 类似商品-->
        <div class="similar-box">
            <h2>类似商品</h2>

            <ul class="similar" id="rowList">
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>2698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>3698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>4098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>5798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>6698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>7698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>8698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>9698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>10698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>11698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>12698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>13698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>14698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="${staticPath}/core/images/productdetail/similar-5.jpg" alt=""/></a>
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
<!--主脚部-->
<jsp:include page="../../core/footer-main.jsp"></jsp:include>
    
<div id="popUp" class="pop-up">
    <div class="inner">
        <h3>
            <span id="popTitle" class="pop-title">填写收货信息</span>
            <span id="popClose" class="close">X</span>
        </h3>
        <form id="popForm" class="pop-content" action="addAddress">

            <div class="row1">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>收货人:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="name" maxlength="50"/>
                </div>
            </div>

            <div class="row2">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>所在区域:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <!-- 仿select-->
                    <div class="province">
                        <a id="province" class="select-view"  href="#">
                        <span class="selected">请选择</span>
                        <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                        <c:forEach items="${provinceList}" var="province">
                            <li><a class="option-view" data-value="${province.id}" href="#">${province.name}</a></li>
                        </c:forEach>
                        </ul>
                        <input class="select-value" name="province" type="hidden" value=""/>
                    </div>省

                    <!-- 仿select-->
                    <div class="city">
                        <a id="city" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">

                        </ul>
                        <input class="select-value" name="city" type="hidden" value=""/>
                    </div>市

                    <!-- 仿select-->
                    <div class="county">
                        <a id="county" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                            <li><a class="option-view" data-value="0" href="#">高新区</a></li>
                            <li><a class="option-view" data-value="1" href="#">姑苏区</a></li>
                            <li><a class="option-view" data-value="2" href="#">宜秀区</a></li>
                        </ul>
                        <input class="select-value" name="district" type="hidden" value=""/>
                    </div>区/县
                </div>
            </div>

            <div class="row3">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>详细地址:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="address" maxlength="100"/>
                </div>
            </div>

            <div class="row4">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>手机号码:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="phone" maxlength="11"/>
                </div>
            </div>
 			
            <div class="row5">
                <label for="#">
                    <span>固定号码:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="telephone" maxlength="20"/>
                </div>
            </div>
			
            <div class="row6">
                <label for="#">
                    <span>邮箱:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="email" maxlength="100"/>
                </div>
            </div>

            <div class="row7">
                <input id="popSubmit"  class="d-btn" type="button" value="确定"/>
                <input id="popReset" class="l-btn" type="reset" value="取消"/>
            </div>
        </form>
    </div>
</div>
</body>
<script src="${staticPath}/core/js/require.js" defer async="true" data-main="${staticPath}/my-account/person-info/address"></script>
</html>