<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
     <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/help/help.css"/>" />
</head>
<!--head-->
<body>
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
                <ul class="will-login">
                    <li><a href="../user/login.html"></i> 立即登陆</a></li>
                    <li><a href="../user/regist.html">免费注册</a></li>
                </ul>
                <ul class="have-login">
                    <li><a href="../user/regist.html"><span> 欢迎您：</span><span>游客</span></a></li>
                    <li><a href="#">账户管理</a></li>
                    <li><a href="#">退出登陆</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="head-box">
        <div class="logo-box">
            <h1><a href="../index/index.html">点金子绝当品——欢迎登陆</a></h1>
        </div>
        <div class="search-box">
            <form action="/search">
                <input class="search-text" type="text"/><!--
             --><input class="search-sub" type="submit" value="搜索"/>
            </form>
            <ul class="search-tips" id="searchTips">
                <li>小万</li>
                <li>王霸</li>
                <li>厉害</li>
                <li>搜索</li>
                <li>提示</li>
            </ul>
            <div class="hot-words">
                <a href="#">双11</a>
                <a href="#">5折秒杀</a>
                <a href="#">立减100</a>
                <a href="#">珍贵绝当</a>
                <a href="#">玛瑙</a>
                <a href="#">红木</a>
                <a href="#">独一件</a>
            </div>
        </div>
        <div class="cart-box">
            <div class="cart">
                <i class="foundicon-cart"></i>
                <span>我的购物车</span>
                <span class="cart-count">0</span>
            </div>
        </div>
    </div>

    <div class="nav-box">

        <div class="menu">
            <a class="menu-head" href="#">
                <span>全部商品分类</span>
            </a>

            <ul id="menuList" class="menu-list">
                <li>
                    <a class="menu-list-tittle" href="#">黄铂金</a>
                    <div class="menu-list-detail">
                        <a href="#">千足金</a>
                        <a href="#">k金</a>
                        <a href="#">PT铂金</a>
                    </div>
                </li>
                <li>
                    <a class="menu-list-tittle" href="#">钻石</a>
                    <div class="menu-list-detail">
                        <a href="#">钻戒</a>
                        <a href="#">裸钻</a>
                        <a href="#">特价钻石</a>
                    </div>
                </li>
                <li>
                    <a class="menu-list-tittle" href="#">彩宝</a>
                    <div class="menu-list-detail">
                        <a href="#">玛瑙</a>
                        <a href="#">欧泊</a>
                        <a href="#">青金石</a>
                    </div>
                </li>
                <li>
                    <a class="menu-list-tittle" href="#">玉翠</a>
                    <div class="menu-list-detail">
                        <a href="#">绿翠</a>
                        <a href="#">红翡</a>
                        <a href="#">黄翡</a>
                    </div>
                </li>
                <li>
                    <a class="menu-list-tittle" href="#">琥珀蜜蜡</a>
                    <div class="menu-list-detail">
                        <a href="#">黄蜜蜡</a>
                        <a href="#">白蜜蜡</a>
                        <a href="#">金包蜜</a>
                    </div>
                </li>
                <li>
                    <a class="menu-list-tittle" href="#">钟表</a>
                    <div class="menu-list-detail">
                        <a href="#">男表</a>
                        <a href="#">女表</a>
                        <a href="#">怀表</a>
                    </div>
                </li>
                <li>
                    <a class="menu-list-tittle" href="#">更多</a>
                </li>
            </ul>
        </div>

        <a href="#" class="hottest">
            <h1>点金子绝当品震撼上线！</h1>
        </a>

        <div class="nav">
            <ul>
                <li><a href="#">金银首饰</a></li>
                <li><a href="#">钻石彩宝</a></li>
                <li><a href="#">玉翠琥珀</a></li>
                <li><a href="#">名贵钟表</a></li>
                <li><a href="#">点金子投资</a></li>
            </ul>
        </div>
    </div>

</div>
<!--main-->

<div class="main1">
    <div class="main1-content">
        <div class="main1-left">
            <ul>
                <li class="menu-top">用户中心</li>
                <c:forEach items="${bigList }" var="bigType">
                <li><h3>${bigType.title }</h3></li>
                  <c:forEach  items="${bigType.smallTypeList }" var="smallType">
                  <li><a href="<spring:url value="${smallType.id}"/>">${smallType.title}</a> </li>

                  </c:forEach>
                </c:forEach>
            
             
                
              

            </ul>
        </div>

        <div class="main1-right">
        ${smallType.content}
        </div>
        <div class="main1-bottom">
            <div class="main1-menu">
                <h2>&nbsp;&nbsp;&nbsp; 猜我喜欢</h2>
            </div>
            <div class="menu-left"></div>
            <div class="menu-mid"></div>
            <div class="menu-right">
            </div>
        </div>
    </div>
</div>

<!-- foot -->
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
                    <img src="../dianjinzi(1)/img/footer/apple.png" alt="苹果客户端下载"/>
                    <h3>苹果客户端</h3>
                </a>
                <a class="android-dimension" href="#">
                    <img src="../dianjinzi(1)/img/footer/android.png" alt="安卓客户端下载"/>
                    <h3>安卓客户端</h3>
                </a>
                <a class="online-server" href="#">
                    <img src="../dianjinzi(1)/img/footer/weixin.jpeg" alt="微信客户端下载"/>
                    <h3>微信公众号</h3>
                </a>
                <a class="online-server" href="#">
                    <img src="../help-img/apple.png" alt="点金子在线投资"/>
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
                <img src="H:\help\help-img\footer\approve-0" alt=""/>
            </a>
            <a href="#">
                <img src="../dianjinzi(1)/img/footer/approve-1.jpg" alt=""/>
            </a>
            <a href="#">
                <img src="../dianjinzi(1)/img/footer/approve-2.html" alt=""/>
            </a>
            <a href="#">
                <img src="../dianjinzi(1)/img/footer/approve-3.png" alt=""/>
            </a>
            <a href="#">
                <img src="../dianjinzi(1)/img/footer/approve-4.jpg" alt=""/>
            </a>
        </div>
        <p>蜀ICP备15022028号 © 2015 dianjinzi, Inc. All rights reserved.</p>
    </div>
</div>
</body>
</html>