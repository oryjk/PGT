<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangxiaodong
  Date: 16-2-23
  Time: 下午12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--footer begin-->
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
        <c:forEach items="${helpCategoryVoList}" var="esHelpCenter">
            <c:set var="helpcategory" value="${esHelpCenter.source}"/>
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
                    <img class="app-icon" src="/resources/core/images/footer/apple.png" alt="苹果客户端下载"/>

                    <h3>苹果客户端</h3>
                    <img class="two-dimension" src="/resources/core/images/footer/erweima.jpg" alt="二维码"/>
                </a>
                <a class="android-dimension" href="#">
                    <img class="app-icon" src="/resources/core/images/footer/android.png" alt="安卓客户端下载"/>

                    <h3>安卓客户端</h3>
                    <img class="two-dimension" src="/resources/core/images/footer/erweima.jpg" alt="二维码"/>
                </a>
                <a class="online-server" href="#">
                    <img class="app-icon" src="/resources/core/images/footer/weixin.png" alt="微信客户端下载"/>

                    <h3>微信公众号</h3>
                    <img class="two-dimension" src="/resources/core/images/footer/erweima.jpg" alt="二维码"/>
                </a>
                <a class="online-server" href="#">
                    <img class="app-icon" src="/resources/core/images/footer/weibo.png" alt="点金子在线投资"/>

                    <h3>点金子投资</h3>
                    <img class="two-dimension" src="/resources/core/images/footer/erweima.jpg" alt="二维码"/>
                </a>
            </div>
            <div class="our-info">
                <h3>点金子联系地址</h3>
                <p>北京市朝阳区朝外大街乙6号0110</p>
                <p></p>
                <p>咨询热线：010-58697606</p>
                <p>企业邮箱：zxhl998@163.com</p>
            </div>
        </div>
    </div>

    <div class="approve-box">
        <div class="approve">
            <a href="#">
                <img src="/resources/core/images/footer/approve-0.png" alt=""/>
            </a>
            <a href="#">
                <img src="/resources/core/images/footer/approve-1.jpg" alt=""/>
            </a>
        </div>
        <p>蜀ICP备15022028号 © 2015 dianjinzi, Inc. All rights reserved.</p>
    </div>
</div>
<!--footer end-->

<script src="/resources/core/js/jquery.min.js"></script>
<script src="/resources/core/js/jquery.jqzoom-core.js"></script>
<script type="text/javascript">

    $(document).ready(function() {
        $('.jqzoom').jqzoom({
            zoomType: 'standard',
            lens:true,
            preloadImages: false,
            alwaysOn:false,
            zoomWidth:400,
            zoomHeight: 400,
            hideEffect: 'fadeout'
        });

    });


</script>