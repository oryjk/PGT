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
<ul class="step step-1">
    <li>订单详情</li>
    <li>订单确认</li>
    <li>支付</li>
    <li>完成</li>
</ul>
<!--step end-->

<!--content begin-->
<div class="main">
    <div class="order-info">
        <h2>订单信息</h2>

        <div class="main-content">

            <c:forEach items="${order.commerceItems}" var="item">
            <div class="info-row-1">
                产品信息 : <a class="info-value" href="#">${item.name}</a>
                <span class="time-tips">( 该产品还剩<span>23</span>天可预定 )</span>
            </div>
            <div class="info-row-2">
                <div class="info-col-1">购买数量: <span class="info-value">${item.quality}</span></div>
                <div class="info-col-2">产品金额: <span class="buy-cost info-value">¥</span><span
                        class="buy-cost">${order.total}</span></div>
                <div class="info-col-3">配送费用: <span class="info-value">免运费</span></div>
            </div>
            </c:forEach>
            <div class="info-row-3">
                发货时间: <span class="info-value">2016年6月22日</span>
                <span class="time-tips">( 该产品还剩<span>123</span>天可能绝当 )</span>
            </div>
            <div class="info-row-4">
                备注信息: <input type="text" placeholder="请填写任何您想告诉我们的信息"/>
            </div>
            <div class="info-row-5">
                <span class="buy-explain-title">购买说明: </span>

                <div class="buy-explain">
                    <p>预定"在当品",可以看做一种投资行为:购买的是一种"可能性",将会出现下面两种情况中的一种:</p>

                    <p>1. 您所预定的商品,如果到期发生绝当,您将获得该商品.我们将在绝当发生后15个工作日内为您发货.</p>

                    <p>2. 如果到期之前,您所预定的商品发生赎当,您不能获得该商品.我们将退还您的全额本金,另外赔付您:<span>¥</span><span>312,00</span>.</p>
                </div>
            </div>
            <div class="info-row-6">
                <span>收获地址:</span>
                <a id="addNewAddress" class="link-btn" href="javascript:void(0);">新增收获地址</a>
            </div>
            <div class="info-row-7">
                <ul>
                    <li class="receive-item">
                        <div class="receive-info">
                            <span class="receive-name receive-choose">核弹</span>
                            <span class="receive-phone">15756306206</span>
                            <span class="receive-province">四川省</span>
                            <span class="receive-city">成都市</span>
                            <span class="receive-country">武侯区</span>
                            <span class="address">佳灵路5号下一站都市A302</span>
                        </div>
                        <div class="receive-handle">
                            <a href="javascript:void(0);" class="set-default link-btn">设为默认</a>
                            <a href="javascript:void(0);" class="receive-modify link-btn">修改</a>
                            <a href="javascript:void(0);" class="receive-delete link-btn">删除</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="info-row-8">
                <input class="receive-ifo-submit" type="button" value="确认订单并提交"/>
            </div>
        </div>
    </div>

    <div class="order-confirm">
        <h2>订单确认</h2>

        <div class="main-content">
            <div class="confirm-row-1">
                <div class="confirm-title">订单号:</div>
                <div class="confirm-value">919281734123</div>
            </div>
            <div class="confirm-row-2">
                <div class="confirm-title">联系人:</div>
                <div class="confirm-value">核弹</div>
            </div>
            <div class="confirm-row-3">
                <div class="confirm-title">联系方式:</div>
                <div class="confirm-value">15983439289</div>
            </div>
            <div class="confirm-row-4">
                <div class="confirm-title">备注信息:</div>
                <div class="confirm-value">请填写任何你想要的信息</div>
            </div>
            <div class="confirm-row-5">
                <div class="confirm-title">收货人信息:</div>
                <div class="confirm-value">
                    <p><span>核弹</span> <span>159388279830</span></p>

                    <p><span>四川省</span><span>成都市</span><span>武侯区</span><span>佳灵路下一站都市A302</span></p>
                </div>
            </div>
            <div class="confirm-row-6">
                <table>
                    <tr>
                        <th class="confirm-name">产品名称</th>
                        <th class="confirm-post-cost">配送费用</th>
                        <th class="confirm-item-cost">产品单价</th>
                        <th class="confirm-item-count">产品数量</th>
                        <th class="confirm-percent">赔付利率(年利率)</th>
                    </tr>
                    <tr>
                        <td><a class="item-link" href="#">什么系列什么产品噢噢噢噢噢噢噢</a></td>
                        <td><span>¥</span><span>0.00</span></td>
                        <td><span class="cost">¥</span><span class="cost">123,000.00</span></td>
                        <td>1</td>
                        <td>10.00%</td>
                    </tr>
                </table>
            </div>
            <div class="confirm-row-7">
                温馨提示:请在提交订单30分钟内付款,否则您的订单将自动关闭.
            </div>
            <div class="confirm-row-8">
                <span class="money-title">应付金额:</span>
                <span class="cost">¥</span>
                <span class="cost">186,000.00</span>
            </div>
            <div class="confirm-row-9">
                <a class="confirm-submit" href="javascript: void(0);">立即支付</a>
                <a class="confirm-reset" href="javascript: void(0);">返回上一步</a>
            </div>
        </div>
    </div>


    <form class="order-pay">
        <h2>最后一步，请尽快付款！</h2>

        <div class="pay-content">
            <div class="pay-title">请在<span>30</span>分钟内完成支付，否则订单将会被取消哦。</div>
            <div class="pay-info">
                <div class="amount-payable">应付金额：186000.00元</div>
                <div class="pay-add">
                    收货人：<span>周杰伦</span> &nbsp; <span>1890000000</span> <br><br>
                    收货地址：<span>四川省成都市武侯区中环武阳大道三段下一站都市A座302</span>
                </div>
            </div>
            <div class="pay-yeepay">
                <input type="radio">使用易宝余额支付（易宝余额为：<span>4.25元</span>）
            </div>
            <div class="payment">
                <div class="pay-check">支付宝/易宝</div>
                <div class="pay-Alipay"><input name="" type="radio"><img src="../core/images/cart/zhifubao.png"></div>
                <div class="pay-Alipay"><input name="" type="radio"><img src="../core/images/cart/yibao.png"></div>
            </div>
            <div class="pay-submit">
                <input class="pay-submit-btn" type="submit" value="立即支付">
            </div>
        </div>
    </form>
    <div class="pay-end">
        <h2>支付成功</h2>
        <div class="pay-end-content">
            <div class="pay-end-font">
                <div class="pay-end-font-num">
                    <h3>支付成功！</h3> <a class="link-btn" href="javascript: void(0);">查看订单详情</a>
                </div>
                <div class="pay-end-font-num">
                    <h3>订单号：<span>3325000045612</span></h3>
                    <div>已支付金额：<span>3560.00元</span></div>
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
                    <a class="link-btn" href="javascript:void(0);">>返回首页</a>
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

<!--pop begin-->
<div id="popUp" class="pop-up" style="display: block;">
    <div class="wrap">
        <div class="inner">
            <h3>
                <span id="popTitle" class="pop-title">新增收获地址</span>
                <span id="popClose" class="close">X</span>
            </h3>
            <form id="popForm" class="pop-content" action="">

                <div class="pop-address-title">收货人: <span class="pop-error">错误提示</span></div>
                <div class="pop-address-value"><input class="pop-address-name" type="text"/></div>

                <div class="pop-address-title">所在地区: <span class="pop-error">错误提示</span></div>
                <div class="pop-address-value">
                    <!-- 仿select组件begin-->
                    <div class="invest-province-select">
                        <a id="province" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                            <li><a class="option-view" data-value="0" href="#">四川</a></li>
                            <li><a class="option-view" data-value="1" href="#">湖南</a></li>
                            <li><a class="option-view" data-value="2" href="#">江西</a></li>
                        </ul>
                        <input class="select-value" name="" type="hidden" value=""/>
                    </div>
                    <!-- 仿select组件end-->
                    <!-- 仿select组件begin-->
                    <div class="invest-city-select">
                        <a id="city" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                            <li><a class="option-view" data-value="0" href="#">四川</a></li>
                            <li><a class="option-view" data-value="1" href="#">湖南</a></li>
                            <li><a class="option-view" data-value="2" href="#">江西</a></li>
                        </ul>
                        <input class="select-value" name="" type="hidden" value=""/>
                    </div>
                    <!-- 仿select组件begin-->
                    <!-- 仿select组件end-->
                    <div class="invest-country-select">
                        <a id="country" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                            <li><a class="option-view" data-value="0" href="#">四川</a></li>
                            <li><a class="option-view" data-value="1" href="#">湖南</a></li>
                            <li><a class="option-view" data-value="2" href="#">江西</a></li>
                        </ul>
                        <input class="select-value" name="" type="hidden" value=""/>
                    </div>
                    <!-- 仿select组件end-->
                </div>

                <div class="pop-address-title">详细地址: <span class="pop-error">错误提示</span></div>
                <div class="pop-address-value"><input class="pop-address-address" type="text"/></div>

                <div class="pop-address-title">手机号码: <span class="pop-error">错误提示</span></div>
                <div class="pop-address-value"><input class="pop-address-phone" type="text"/></div>

                <div class="pop-btn">
                    <input id="popSubmit" class="pop-confirm" type="button" value="确认"/>
                    <input id="popReset" class="pop-cancel" type="reset" value="取消"/>
                </div>
            </form>
        </div>
    </div>
</div>
<!--pop end-->

</body>
</html>