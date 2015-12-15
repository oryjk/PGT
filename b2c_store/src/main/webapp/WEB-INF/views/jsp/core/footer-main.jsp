<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 11/16/15
  Time: 12:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/apple.png"/>" alt="苹果客户端下载"/>
        <h3>苹果客户端</h3>
        <img class="two-dimension" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/erweima.jpg"/>" alt="二维码"/>
  </a>
      <a class="android-dimension" href="#">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/android.png"/>" alt="安卓客户端下载"/>
        <h3>安卓客户端</h3>
        <img class="two-dimension" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/erweima.jpg"/>" alt="二维码"/>
  </a>
      <a class="online-server" href="#">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/weixin.png"/>" alt="微信公众号"/>
        <h3>微信公众号</h3>
        <img class="two-dimension" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/erweima.jpg"/>" alt="二维码"/>
  </a>
      <a class="online-server" href="#">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/weibo.png"/>" alt="新浪官方微博"/>
        <h3>新浪官方微博</h3>
        <img class="two-dimension" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/erweima.jpg"/>" alt="二维码"/>
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
      <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/approve-0.png"/>" alt=""/>
    </a>
    <a href="#">
      <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/approve-1.jpg"/>" alt=""/>
    </a>
    <a href="#">
      <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/approve-2.png"/>" alt=""/>
    </a>
    <a href="#">
      <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/approve-3.png"/>" alt=""/>
    </a>
    <a href="#">
      <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/approve-4.jpg"/>" alt=""/>
    </a>
  </div>
  <p>蜀ICP备15022028号 © 2015 dianjinzi, Inc. All rights reserved.</p>
</div>
</div>