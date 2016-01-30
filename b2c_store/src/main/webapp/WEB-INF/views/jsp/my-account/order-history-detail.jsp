<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>" />
    <link rel="stylesheet"
          href="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/my-orders.css"/>" />
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <jsp:include page="../core/header-main.jsp" />
</div>

<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <jsp:include page="vertical-my-account-directory.jsp">
            <jsp:param name="step" value="orderHistory" />
        </jsp:include>

        <!-- 详细内容列表-->
        <!-- 根据订单的具体情况,添加step-x-->
        <div id="main" class="main-box  step-5">

            <!--面包屑-->
            <div class="bread-nav">
                <p>
                    <a href="#">个人中心</a>
                    >
                    <a href="<spring:url value="/myAccount/orderHistory"/>">订单详情</a>
                </p>
            </div>

            <div class="floor-1">
                <h3>订单状态</h3>

                <div class="floor-content">
                    <div class="detail-status"></div>
                    <ul>
                        <c:choose>
                            <c:when test="${b2cOrder.status eq 10}">
                                <span>待提交</span>
                            </c:when>
                            <c:when test="${b2cOrder.status eq 20}">
                                <span>待付款</span>
                            </c:when>
                            <c:when test="${b2cOrder.status eq 30}">
                                <span>待收货</span>
                            </c:when>
                            <c:when test="${b2cOrder.status eq 100}">
                                <span>已完成</span>
                            </c:when>
                            <c:when test="${b2cOrder.status eq -10}">
                                <span>已取消</span>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>



            <!-- 商品信息-->
            <div>
                <h3 class="form-section">
                    商品信息
                </h3>

                <div class="pgt-scan">
                    <div class="row">
                        <div class="col-xs-12">
                            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                   role="grid" aria-describedby="sample_3_info">
                                <thead>
                                <tr role="row">
                                    <th> 商品ID </th>
                                    <th> 商品名称 </th>
                                    <th> 绝当价 </th>
                                    <th> 略缩图 </th>
                                    <th> 商家 </th>
                                    <th> 发货时间 </th>
                                    <th> 物流公司 </th>
                                    <th> 物流单号 </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="ci" items="${b2cOrder.commerceItems}">
                                    <tr class="gradeX odd" role="row">
                                        <td>${ci.id}</td>
                                        <td>${ci.name}</td>
                                        <td>¥&nbsp;<span><fmt:formatNumber value="${ci.salePrice}" pattern="0.00" type="number" /></span></td>
                                        <td class="productlist-face-box">
                                            <img src="${pageContext.request.contextPath}/resources${ci['snapshotMedia']['path']}"
                                                 alt="${empty ci['snapshotMedia']['title'] ? ci.name : ci['snapshotMedia']['title']}" />
                                        </td>
                                        <td>${ci.merchant}</td>
                                        <td>${ci.delivery.deliveryTime}</td>
                                        <td>${ci.delivery.logistics}</td>
                                        <td>${ci.delivery.trackingNo}</td>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>


            <!-- 收货人信息-->
            <div>
                <h3 class="form-section">
                    收货人信息
                    <%--<button class="btn btn-xs green" id="modifySwitch">修改</button>--%>
                </h3>
                <div class="pgt-scan">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-4">姓名：</label>

                                <div class="col-md-8">
                                    <p class="form-control-static">
                                        ${b2cOrder.shippingVO.shippingAddress.name}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-4">手机：</label>

                                <div class="col-md-8">
                                    <p class="form-control-static">
                                        ${b2cOrder.shippingVO.shippingAddress.telephone}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-4">电话：</label>

                                <div class="col-md-8">
                                    <p class="form-control-static">
                                        ${b2cOrder.shippingVO.shippingAddress.phone}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-4">配送区域：</label>

                                <div class="col-md-8">
                                    <p class="form-control-static">
                                        ${b2cOrder.shippingVO.shippingAddress.province} -
                                        ${b2cOrder.shippingVO.shippingAddress.city} -
                                        ${b2cOrder.shippingVO.shippingAddress.district}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-8">
                            <div class="form-group">
                                <label class="control-label col-md-2">配送地址：</label>

                                <div class="col-md-10">
                                    <p class="form-control-static">
                                        ${b2cOrder.shippingVO.shippingAddress.address}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form class="pgt-modify" style="display: none">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-3">姓名</label>

                                <div class="col-md-9">
                                    <input type="search" class="form-control input-small input-inline" placeholder="" aria-controls="sample_3">
                                </div>
                            </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-3">手机</label>

                                <div class="col-md-9">
                                    <input type="search" class="form-control input-small input-inline" placeholder="" aria-controls="sample_3">
                                </div>
                            </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-3">电话</label>

                                <div class="col-md-9">
                                    <input type="search" class="form-control input-small input-inline" placeholder="" aria-controls="sample_3">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label col-md-3">配送区域</label>

                                <div class="col-md-9">
                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen"
                                            tabindex="-1" title="">
                                        <option value="10">10</option>
                                        <option value="30">30</option>
                                        <option value="100">100</option>
                                        <option value="-1">所有</option>
                                    </select>省
                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen"
                                            tabindex="-1" title="">
                                        <option value="10">10</option>
                                        <option value="30">30</option>
                                        <option value="100">100</option>
                                        <option value="-1">所有</option>
                                    </select>市
                                    <select name="sample_3_length" aria-controls="sample_3" class="form-control input-xsmall input-inline select2-offscreen"
                                            tabindex="-1" title="">
                                        <option value="10">10</option>
                                        <option value="30">30</option>
                                        <option value="100">100</option>
                                        <option value="-1">所有</option>
                                    </select>县
                                </div>

                            </div>
                        </div>
                        <!--/span-->
                        <div class="col-md-8">
                            <div class="form-group">
                                <label class="control-label col-md-2">配送地址</label>

                                <div class="col-md-10">
                                    <input type="search" class="form-control input-large input-inline" placeholder="" aria-controls="sample_3">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-offset-1 col-md-9">
                            <button type="submit" class="btn blue-hoki">确认</button>
                            <button type="button" class="btn default">取消</button>
                        </div>
                    </div>
                </form>
            </div>




            <jsp:include page="../shopping-cart/horizontal-recommend-bar.jsp" />
    </div>
</div>

    <!--主脚部-->
    <jsp:include page="../core/footer-main.jsp" />

</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" defer async="true" data-main="<spring:url value="${juedangpinStaticPath}/my-account/my-orders/my-orders"/>"></script>
</html>