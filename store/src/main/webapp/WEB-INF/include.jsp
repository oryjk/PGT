<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 11/14/15
  Time: 6:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet" href="../index/index.css"/>
</head>
<body>
<!--头部-->
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
        <div class="top-banner">
            <div class="top-banner-box">
                <a href="#"></a>
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

            <ul class="menu-list">
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

<!--正文-->
<div class="content-box">
    <div class="content">
        <div class="banner-box">
            <div class="banner">
                <img src="../core/images/index/banner-0.jpg" alt="banner0"/>
            </div>
            <ol class="banner-nav">
                <li>0</li>
                <li>1</li>
                <li>2</li>
                <li>3</li>
                <li>4</li>
                <li>5</li>
            </ol>
        </div>

        <div class="classify-box">
            <h2 class="classify-head">最新热卖</h2>

            <div class="classify">
                <a class="classify-0" href="#">
                    <img src="../core/images/index/classify-0.jpg" alt=""/>
                </a>
                <a class="classify-1" href="#">
                    <img src="../core/images/index/classify-1.jpg" alt=""/>
                </a>
                <a class="classify-2" href="#">
                    <img src="../core/images/index/classify-2.jpg" alt=""/>
                </a>
                <a class="classify-3" href="#">
                    <img src="../core/images/index/classify-3.jpg" alt=""/>
                </a>
                <a class="classify-4" href="#">
                    <img src="../core/images/index/classify-4.jpg" alt=""/>
                </a>
            </div>
        </div>

        <div class="products-box-2">
            <ul class="products-nav">
                <li><a href="#">千足金</a></li>
                <li><a href="#">K金</a></li>
                <li><a href="#">PT铂金</a></li>
                <li><a href="#">更多 ></a></li>
            </ul>
            <h2 class="products-head">黄铂金</h2>

            <div class="products">
                <a class="product-widther" href="#">
                    <img src="../core/images/index/product-0-1.png" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-0-2.jpg" alt=""/>
                </a>
                <a class="product-main" href="#">
                    <img src="../core/images/index/product-0-0.jpg" alt=""/>
                </a>

                <div class="row-2">

                    <a href="#">
                        <img src="../core/images/index/product-0-3.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img src="../core/images/index/product-0-4.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img src="../core/images/index/product-0-5.jpg" alt=""/>
                    </a>
                </div>
            </div>
        </div>

        <div class="products-box">
            <ul class="products-nav">
                <li><a href="#">玛瑙</a></li>
                <li><a href="#">水晶</a></li>
                <li><a href="#">青金石</a></li>
                <li><a href="#">更多 ></a></li>
            </ul>
            <h2 class="products-head">彩宝</h2>

            <div class="products">
                <a class="product-main" href="#">
                    <img src="../core/images/index/product-1-0.jpg" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-1-2.jpg" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-1-3.jpg" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-1-4.jpg" alt=""/>
                </a>

                <div class="row-2">
                    <a href="#">
                        <img src="../core/images/index/product-1-5.jpg" alt=""/>
                    </a>
                    <a class="product-widther" href="#">
                        <img src="../core/images/index/product-1-1.jpg" alt=""/>
                    </a>
                </div>
            </div>
        </div>

        <div class="products-box-2">
            <ul class="products-nav">
                <li><a href="#">项链</a></li>
                <li><a href="#">挂件</a></li>
                <li><a href="#">戒指</a></li>
                <li><a href="#">更多 ></a></li>
            </ul>
            <h2 class="products-head">翡翠玉石</h2>

            <div class="products">
                <a href="#">
                    <img src="../core/images/index/product-2-2.jpg" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-2-3.jpg" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-2-4.jpg" alt=""/>
                </a>
                <a class="product-main" href="#">
                    <img src="../core/images/index/product-2-0.jpg" alt=""/>
                </a>

                <div class="row-2">
                    <a class="product-widther" href="#">
                        <img src="../core/images/index/product-2-5.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img src="../core/images/index/product-2-1.jpg" alt=""/>
                    </a>
                </div>
            </div>
        </div>

        <div class="products-box">
            <ul class="products-nav">
                <li><a href="#">男表</a></li>
                <li><a href="#">女表</a></li>
                <li><a href="#">怀表</a></li>
                <li><a href="#">更多 ></a></li>
            </ul>
            <h2 class="products-head">钟表</h2>

            <div class="products">
                <a class="product-main" href="#">
                    <img src="../core/images/index/product-3-0.jpg" alt=""/>
                </a>
                <a href="#">
                    <img src="../core/images/index/product-3-2.jpg" alt=""/>
                </a>
                <a class="product-widther" href="#">
                    <img src="../core/images/index/product-2-5.jpg" alt=""/>
                </a>

                <div class="row-2">
                    <a href="#">
                        <img src="../core/images/index/product-3-3.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img src="../core/images/index/product-3-4.jpg" alt=""/>
                    </a>
                    <a href="#">
                        <img src="../core/images/index/product-3-1.jpg" alt=""/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!--脚部-->
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
<script src="../core/js/require.js" data-main="../index/index.js"></script>
</html>
