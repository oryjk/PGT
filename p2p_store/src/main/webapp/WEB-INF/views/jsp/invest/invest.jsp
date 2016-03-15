<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>在线典当</title>
    <link rel="stylesheet" href="/resources/invest/invest.css"/>


</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->
<!--banner begin-->
<div class="banner">
    <div class="banner-content">
        <form id="form" action="/pawnPersonInfo/createPawnPersonInfo" method="post">
            <h2>典当个人信息</h2>


            <table>
                <tr>
                    <th>姓名</th>
                    <td><input class="invest-text" type="text" name="name" value="${pawnPersonInfo.name}"
                               id="username"
                               v-model="user.username"
                               v-on:keyup="volidate"/> ${errors['name']}</td>
                </tr>
                <tr>
                    <th>性别</th>
                    <td>
                        <!-- 仿select组件begin-->
                        <div class="invest-gender-select">
                            <a class="select-view"  href="#">
                                <span class="selected">请选择</span>
                                <i class="foundicon-down-arrow"></i>
                            </a>
                            <ul>
                                <li><a class="option-view" data-value="0" href="#">男</a></li>
                                <li><a class="option-view" data-value="1" href="#">女</a></li>
                            </ul>
                            <input  name="gender" type="hidden" value="${pawnPersonInfo.gender}"/>
                        </div>
                        <!-- 仿select组件end-->
                    </td>
                </tr>
                <tr>
                    <th>手机号</th>
                    <td><input id="userPhone" class="invest-text" type="text" name="phoneNumber" value="${pawnPersonInfo.phoneNumber}"/>${errors['phoneNumber']}</td>

                </tr>
                <tr>
                    <th>地址</th>
                    <td>
                        <!-- 仿select组件begin-->
                        <div class="invest-province-select">
                            <a id="province" class="select-view"  href="#">
                                <span class="selected">请选择</span>
                                <i class="foundicon-down-arrow"></i>
                            </a>
                            <ul class="options">
                                <c:forEach items="${provinceList}" var="province">
                                    <li><a class="option-view" data-value="${province.id}" href="#">${province.name}</a></li>
                                </c:forEach>
                            </ul>
                            <input class="select-value" name="province" type="hidden" value="${pawnPersonInfo.province}"/>
                        </div>
                        <!-- 仿select组件end-->
                        <!-- 仿select组件begin-->
                        <div class="invest-city-select">
                            <a id="city" class="select-view"  href="#">
                                <span class="selected">请选择</span>
                                <i class="foundicon-down-arrow"></i>
                            </a>
                            <ul class="options">
                            </ul>
                            <input class="select-value" name="city" type="hidden" value="${pawnPersonInfo.city}"/>
                        </div>
                        <!-- 仿select组件begin-->
                        <!-- 仿select组件end-->
                        <div class="invest-country-select">
                            <a id="country" class="select-view"  href="#">
                                <span class="selected">请选择</span>
                                <i class="foundicon-down-arrow"></i>
                            </a>
                            <ul class="options">
                            </ul>
                            <input class="select-value" name="district" type="hidden" value="${pawnPersonInfo.district}"/>
                        </div>
                        <!-- 仿select组件end-->
                    </td>
                </tr>
                <tr>
                    <th>详细地址</th>
                    <td><input class="invest-address-text" type="text" name="detailAddress" value="${pawnPersonInfo.detailAddress}"/>${errors['detailAddress']}</td>

                </tr>
                <tr>
                    <th>典当类型</th>
                    <td>
                        <!-- 仿select组件begin-->
                        <div class="invest-type-select">
                            <a class="select-view"  href="#">
                                <span class="selected">请选择</span>
                                <i class="foundicon-down-arrow"></i>
                            </a>
                            <ul>
                                <c:forEach items="${pawnTypes}" var="pawnType">
                                    <li><a class="option-view" data-value="${pawnType.key}" href="#">${pawnType.value}</a></li>
                                </c:forEach>
                            </ul>
                            <input name="pawnType" type="hidden" value="${pawnPersonInfo.pawnType}"/>
                        </div>
                        <!-- 仿select组件end-->
                    </td>
                </tr>
                <tr>
                    <th>典当金额</th>
                    <td><input name="price" class="invest-text" type="text" value="${pawnPersonInfo.price}"/>${errors['price']}</td>

                </tr>
                <tr>
                    <th>手机验证码</th>
                    <input type="hidden" id="smsPath" data-value="/sms/onlinePawn?phoneNumber=">
                    <td>
                        <input name="smsCode" class="auth-code-text" type="text"/>
                        <input class="auth-code-btn" type="button" value="获取"/>
                        ${errors['smsCode']}
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td><input @click.prevent="formSubmit" class="invest-submit" type="submit" value="立即申请"/></td>
                </tr>
            </table>

        </form>
    </div>
</div>
<!--banner end-->
<!--condition begin-->
<div class="condition">
    <h2>典当条件</h2>
    <ul class="inner">
        <li>个人有效身份证(身份证, 暂住证, 驾驶证, 护照, 军官证)</li>
        <li>当物的合法来源证明</li>
        <li>保卡等附件</li>
    </ul>
</div>
<!--condition end-->
<!--process begin-->
<div class="process">
    <h2>典当流程</h2>
    <div class="inner"></div>
</div>
<!--process end-->
<!--footer begin-->
<div class="footer"></div>
<!--footer end-->
<jsp:include page="../core/baidu.jsp" />
</body>
<script src="/resources/core/js/require.js" data-main="/resources/invest/invest"></script>
</html>