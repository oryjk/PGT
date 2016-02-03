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


  <c:forEach items="${helpCenterList}" var="helpcategory">
  <ul>
    <li><h3>${helpcategory.category.name}</h3></li>

    <c:forEach items="${helpcategory.helpCenterList}" var="helpVo">
    <li><a href="${pageContext.request.contextPath}/helpcenter/${helpVo.id}">${helpVo.title}</a></li>
    </c:forEach>
  </ul>
  </c:forEach>

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
        <img class="two-dimension" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/weixinerweima.jpg"/>" alt="二维码"/>
  </a>
      <a class="online-server" href="#">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/dianjinzi.png"/>" alt="点金子在线投资"/>
        <h3>点金子投资</h3>
      </a>

    </div>
    <div class="our-info">
      <h3>点金子总部地址</h3>
      <p>成都市武侯区下一站都市A座302室</p>
      <p></p>
      <h3>点金子北京总部地址</h3>
      <p>北京市朝阳区朝外大街乙6号0110</p>
      <p></p>
      <p>咨询热线：028-85033350</p>
      <p>企业邮箱：zxhl998@163.com</p>
    </div>
  </div>
</div>

<div class="approve-box">
  <div class="approve">

    <a href="http://webscan.360.cn/index/checkwebsite/url/mp.dianjinzi.com">
      <img border="0" src="http://img.webscan.360.cn/status/pai/hash/4ee18cbbbfc36755fd8f96837e2f700f"/></a>
  </div>
  <p>蜀ICP备15022028号 © 2016 dianjinzi, Inc. All rights reserved.</p>
</div>
</div>


<!--fixed侧边栏-->

<c:if test="${param.useside eq 'true' }">
<div class="side-bar" id="side-bar">
  <div class="right-a">
    <a href="<spring:url value="/"/>" class="right1-2">
      <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/pig.png"/>">
      <div class="right2"><span>主页</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>"></div>
    </a>

    <div class="right-b">
      <div class="right-buy1">0</div>
      <a href="<spring:url value="/myAccount/orderHistory" />" class="right1-2">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-dd.png"/>">

        <div class="right2"><span>订单</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>">
        </div>
      </a>
      <a path="<spring:url value="/product/buy" />" class="right1" title="2">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-buy.png"/>">

        <div class="right2"><span>购物车</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>">
        </div>
      </a>
      <a path="<spring:url value="/product/collection"/>" class="right1" title="3">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-sc.png"/>">

        <div class="right2"><span>收藏</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>">
        </div>
      </a>

      <a path="<spring:url value="/product/history" />" class="right1" title="4">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-ls.png"/>">

        <div class="right2"><span>历史</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>">
        </div>
      </a>

      <a href="/user/yeepayAccountInfo" class="right1-2">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/s-zh.png"/>">

        <div class="right2"><span>账户</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>">
        </div>
      </a>
      <a href="#" class="right1-2">
        <img src="<spring:url value="${juedangpinStaticPath}/core/images/footer/_0005_arrow-top.png"/>">

        <div class="right2"><span>回到顶部</span><img class="img-search" src="<spring:url value="${juedangpinStaticPath}/core/images/footer/search_17.png"/>">
        </div>
      </a>
    </div>
  </div>
  <div class="right-menu" id="right-menu">

  </div>
</div>
</c:if>






