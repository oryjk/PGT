<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../core/head.jspf">
    <jsp:param name="css_path" value="/resources/my-account/my_account_base.css"/>
    <jsp:param name="custom_css_path" value="/resources/my-account/my-favorite/my-favorite.css"/>
</jsp:include>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<div class="content">
    <jsp:include page="vertical-my-account-directory.jsp"/>
    <div class="main">
        <h2 class="main-head">我的交易订单</h2>
        <ul class="filter-favorite-status">
            <li class="filter-favorite-choose"><a href="javascript:void(0);">收藏的项目</a></li>
            <li class="filter-favorite-end"><a href="javascript:void(0);">收藏的产品</a></li>
        </ul>

        <div class="area-product">
            <ul class="filter-order-status">
                <li><a href="javascript:void(0);">全部</a></li>
                <li><a href="javascript:void(0);">有货</a></li>
                <li class="filter-order-end"><a href="javascript:void(0);">无货</a></li>
            </ul>
            <div class="favorite-list">
                <table>
                    <!-- thead begin-->
                    <tr class="favorite-list-head">
                        <th class="col-1">项目信息</th>
                        <th class="col-2"></th>
                        <th class="col-3">库存</th>
                        <th class="col-4">产品金额(元)</th>
                        <th class="col-5">操作</th>
                    </tr>
                    <!-- thead end-->

                    <!-- tbody begin-->
                    <tr class="favorite-list-item">
                        <td class="favorite-img">
                            <a href="#"><img src="../../core/images/product/hero_2_01.jpg" alt="商品名"/></a>
                        </td>
                        <td class="favorite-base-info">
                            <p><a href="#">名字自己自 i 自 ii 仔仔 i 自 i 字段发达省份 是短发短发教科书的番外篇</a></p>
                        </td>
                        <td>12</td>
                        <td><span>¥</span><span>134,000.00</span></td>
                        <td class="favorite-handle">
                            <p><a class="link-btn" href="#">立即抢购</a></p>
                            <p><a class="link-btn" href="#">取消收藏</a></p>
                        </td>
                    </tr>
                    <!-- tbody end-->
                </table>
            </div>
            <div class="page-box">
                <ol>
                    <li><a href="javascript:void(0);">1</a></li>
                    <li>...</li>
                    <li><a href="javascript:void(0);">3</a></li>
                    <li><a href="javascript:void(0);">4</a></li>
                    <li><a href="javascript:void(0);">5</a></li>
                    <li>...</li>
                    <li><a href="javascript:void(0);">7</a></li>
                </ol>
            </div>
        </div>

    </div>
</div>

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/my-account/address/address"></script>
</body>
</html>