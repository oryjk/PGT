<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/resources/user/forget-reset.css"/>
    <script type="application/javascript" src="/resources/core/js/jquery.min.js"></script>
    <script type="application/javascript" src="/resources/user/resetforget.js"></script>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp" />
<!--header end-->

<!--content begin-->
<div class="content">
    <h2>忘记密码</h2>
    <!-- step begin-->
    <!--super: 下面四步依次加上step-1到step-4-->
    <ul class="step step-1">
        <li class="main-font1">填写用户名</li>
        <li class="main-font2">验证身份</li>
        <li class="main-font3">设置新密码</li>
        <li class="main-font4">完成</li>
    </ul>
    <!--step end-->

    <!-- super: 一下四个步骤,根据不同的步骤进行显示-->
    <!-- input-username begin-->
        <form:form modelAttribute="user"  class="input-username" action="/user/resetPassword" method="post"  style="display: ${step=='CHECK_USER_EXIST' ? 'block' : 'none'}">
            <table>
            <tr class="input-row-1">
                <th>账户名 :</th>
                <td><input name="username" class="input-text" type="text"/></td>
            </tr>
            <tr class="input-row-2">
                <th>验证码 :</th>
                <td>
                    <input class="input-text" type="text" name="authCode"/>
                </td>
                <td>
                    <img id="getAuthCode"  class="confirm-code" src="/code/resetPassword">
                    <p>
                        <span>看不清楚?</span>
                        <a id="changeAuthCode" class="change-confirm-code link-btn" href="javascript:void(0);">换一张</a>
                    </p>
                </td>
            </tr>
            <tr class="input-row-3">
                <th></th>
                <td><span class="error"><form:errors path="loginError"/></span></td>
            </tr>
            <tr class="input-row-4">
                <th></th>
                <td>
                    <input class="input-next" type="submit" value="下一步"/>
                    <input class="input-cancel" type="reset" value="取消"/>
                </td>
            </tr>
        </table>
    </form:form>
    <!-- input-username end-->

    <!-- test-identity begin-->
    <form class="test-identity"  action="/user/resetPassword" method="post" style="display: ${step=='CHECK_PHONE_CODE' ? 'block' : 'none'}">
        <table>
            <thead>
            <tr class="test-row-1">
                <th>验证方式 :</th>
                <td>
                    <select name="" id="">
                        <option value="1">手机验证</option>
                        <option value="2">邮箱验证</option>
                    </select>
                </td>
            </tr>
            </thead>
            <tbody>
                <tr class="test-row-2">
                    <th>手机号码 :</th>
                    <td>
                        <span>手机号码：${userResult.phoneNumber}</span>
                        <input id="getSmsPath" class="get-phone-confirm-code" type="button" value="获取手机验证码"/>
                    </td>
                </tr>
                <tr class="input-row-3">
                    <th>手机验证码 :</th>
                    <td>
                        <input class="input-text" type="text" name="smsCode"/>
                        <input type="hidden" id="smsPath" data-value="/sms/resetPassword?phoneNumber=${userResult.phoneNumber}">
                        <span class="error"><form:errors path="loginError"/></span>
                    </td>
                </tr>
            </tbody>

            <!--
            <tbody>
                <tr class="test-row-2">
                    <th>邮箱号码 :</th>
                    <td>
                        <span>8191****1@qq.com</span>
                        <input class="get-email-confirm-code" type="button" value="获取邮箱验证码"/>
                    </td>
                </tr>
                <tr class="input-row-3">
                    <th>邮箱验证码 :</th>
                    <td>
                        <input class="input-text" type="text"/>
                        <span class="error"></span>
                    </td>
                </tr>
            </tbody>
            -->

            <tr class="input-row-4">
                <th></th>
                <td>
                    <input class="input-next" type="submit" value="下一步"/>
                    <input class="input-cancel" type="reset" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
    <!-- test-identity end-->

    <!-- set-password begin-->
    <form class="set-password"  class="forget"
          action="/user/resetPassword" method="post" style="display: ${step=='SET_NEW_PASSWORD' ? 'block' : 'none'}">
        <table>
            <tr class="input-row-1">
                <th>新密码 :</th>
                <td><input name="password"  class="input-text" type="text"/></td>
            </tr>
            <tr class="input-row-2">
                <th>确认新密码 :</th>
                <td>
                    <input name="password2" class="input-text" type="text"/>
                </td>
            </tr>
            <tr class="input-row-3">
                <th></th>
                <td><span class="error"><form:errors path="loginError"/></span></td>
            </tr>
            <tr class="input-row-4">
                <th></th>
                <td>
                    <input class="input-next" type="submit" value="下一步"/>
                    <input class="input-cancel" type="reset" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
    <!-- set-password end-->

    <!-- reset-success begin-->
    <div class="reset-success" style="display: ${step=='COMPLETE' ? 'block' : 'none'}">
        <div class="success-row-1">恭喜您!重新设置密码成功!</div>
        <div class="success-row-2">请牢记您的新密码</div>
        <div class="success-row-3">
            <a class="link-btn" href="#">返回首页</a>
            <a class="link-btn" href="${pageContext.request.contextPath}/user/login">立即登录</a>
        </div>
    </div>
    <!-- reset-success end-->
</div>
<!--content end-->

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp" />
<!--footer end-->
</body>
</html>