<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="${juedangpinStaticPath}" var="staticPath"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel = "Shortcut Icon" href="<spring:url value="${juedangpinStaticPath}/common/logo.png"/>">
    <title>点金子典当行绝当品销售平台</title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/person-info/address.css"/>"/>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>"/>
</head>
<body>
<!--主头部-->
<div class="header" id="header">
    <jsp:include page="../../core/header-main.jsp"></jsp:include>
</div>

<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
	   	<jsp:include page="../vertical-my-account-directory.jsp">
               <jsp:param name="step" value="address" />
       	</jsp:include>
        <!-- 详细内容列表-->
       	<jsp:include page="addressBook.jsp"></jsp:include>

        <jsp:include page="../../shopping-cart/horizontal-recommend-bar.jsp">
            <jsp:param name="excludeFavourites" value="true" />
        </jsp:include>
    </div>
</div>
<!--主脚部-->
<jsp:include page="../../core/footer-main.jsp"></jsp:include>
    
<div id="popUp" class="pop-up">
    <div class="inner">
        <h3>
            <span id="popTitle" class="pop-title">填写收货信息</span>
            <span id="popClose" class="close">X</span>
        </h3>
        <form id="popForm" class="pop-content" action="/my-account/person-info/addAddress">

            <div class="row1">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>收货人:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="name" maxlength="50"/>
                </div>
            </div>

            <div class="row2">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>所在区域:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <!-- 仿select-->
                    <div class="province">
                        <a id="province" class="select-view"  href="#">
                        <span class="selected">请选择</span>
                        <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                        <c:forEach items="${provinceList}" var="province">
                            <li><a class="option-view" data-value="${province.id}" href="#">${province.name}</a></li>
                        </c:forEach>
                        </ul>
                        <input class="select-value" name="province" type="hidden" value=""/>
                    </div>省

                    <!-- 仿select-->
                    <div class="city">
                        <a id="city" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">

                        </ul>
                        <input class="select-value" name="city" type="hidden" value=""/>
                    </div>市

                    <!-- 仿select-->
                    <div class="country">
                        <a id="country" class="select-view"  href="#">
                            <span class="selected">请选择</span>
                            <i class="foundicon-down-arrow"></i>
                        </a>
                        <ul class="options">
                        </ul>
                        <input class="select-value" name="district" type="hidden" value=""/>
                    </div>区/县
                </div>
            </div>

            <div class="row3">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>详细地址:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="address" maxlength="100"/>
                </div>
            </div>

            <div class="row4">
                <label for="#">
                    <span class="must-write">*</span><!--
                            --><span>手机号码:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="phone" maxlength="11"/>
                </div>
            </div>
 			
            <div class="row5">
                <label for="#">
                    <span>固定号码:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="telephone" maxlength="20"/>
                </div>
            </div>
			
            <div class="row6">
                <label for="#">
                    <span>邮箱:</span>
                    <span class="pop-tips"></span>
                </label>

                <div class="text">
                    <input type="text" name="email" maxlength="100"/>
                </div>
            </div>

            <div class="row7">
                <input id="popSubmit"  class="d-btn" type="button" value="确定"/>
                <input id="popReset" class="l-btn" type="reset" value="取消"/>
            </div>
        </form>
    </div>
</div>
<jsp:include page="../../core/baidu.jsp"></jsp:include>
</body>
<script src="${staticPath}/core/js/require.js" defer async="true" data-main="${staticPath}/my-account/person-info/address"></script>
</html>