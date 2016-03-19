<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangxiaodong
  Date: 16-2-23
  Time: 下午12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header">
    <a class="top-banner" href="#" style="background: #9e150e url('/resources/core/images/data/top-banner.jpg') no-repeat center center"></a>
    <div class="top">
        <div class="top-content">
            <div class="top-welcome">
                <span class="top-welcome-text">欢迎光临点金子绝当淘!</span>
                <span class="top-welcome-phone-title">客服电话:</span>
                <span class="top-welcome-phone-value main">028-88888888</span>
            </div>
            <div class="top-user">
                <div class="top-before-login" style="display: block">
                    <a class="top-user-login hover-main" href="#">登录</a>
                    <a class="top-user-register hover-main" href="#">注册</a>
                </div>
                <div class="top-after-login" style="display: none">
                    <span>欢迎您:</span>
                    <a href="#">小陈</a>
                    <a class="top-user-logout hover-main" href="javascript:void(0);">退出</a>
                </div>
            </div>
        </div>
    </div>
    <div class="head">
        <div class="head-content">
            <h1 class="logo">
                <a class="logo-link" href="#"></a>
            </h1>
            <a href="#" class="hot-left"  style="background: url('/resources/core/images/data/hot-left.gif') no-repeat center center"></a>
            <form class="search">
                <div class="search-content">
                    <a class="search-input-submit" href="javascript:void(0);"></a>
                    <input class="search-input-text" type="text" placeholder="请输入要搜索的关键字"/>
                </div>
            </form>
            <a href="#" class="hot-right" style="background: url('/resources/core/images/data/hot-right.gif') no-repeat center center"></a>
        </div>
    </div>
    <div class="nav">
        <div class="nav-content">
            <div class="menu">
                <h2 class="menu-title bg-main">开启购物新体验</h2>
                <ul class="menu-list">
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                            <a class="menu-item-sub hover-main" href="#">玛瑙</a>
                        </div>
                    </li>
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                        </div>
                    </li>
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                        </div>
                    </li>
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                        </div>
                    </li>
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                        </div>
                    </li>
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                            <a class="menu-item-sub hover-main" href="#">玛瑙</a>
                        </div>
                    </li>
                    <li class="menu-item" style="background:url('') no-repeat center center">
                        <h3 class="menu-item-title">文玩杂项</h3>
                        <div class="menu-item-sub-box">
                            <a class="menu-item-sub hover-main" href="#">橄榄核雕</a>
                            <a class="menu-item-sub hover-main" href="#">骆驼骨</a>
                            <a class="menu-item-sub hover-main" href="#">牦牛角</a>
                            <a class="menu-item-sub hover-main" href="#">玛瑙</a>
                        </div>
                    </li>
                </ul>
            </div>
            <ul class="nav-list">
                <li id="navHover" class="nav-item-hover" style="width: 96px; height: 54px; top: 0; left:0;"></li>
                <li class="nav-item"><a class="nav-item-link" href="#">首页</a></li>
                <li class="nav-item"><a class="nav-item-link" href="#">在当大厅</a></li>
                <li class="nav-item"><a class="nav-item-link" href="#">在线典当</a></li>
                <li class="nav-item"><a class="nav-item-link" href="#">帮助中心</a></li>
                <li class="nav-item"><a class="nav-item-link" href="#">绝当品商城</a></li>
            </ul>
        </div>
    </div>
</div>

