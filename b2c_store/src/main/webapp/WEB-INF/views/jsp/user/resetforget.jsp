<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet"
    href="<spring:url value="${juedangpinStaticPath}/user/forget.css"/>" />
</head>
<body>
    <div class="header" id="header">
    <div class="head-box">
        <div class="logo-box">
            <h1>
                <a href="<spring:url value="/"/>">点金子绝当品——欢迎登录
                    <img src="<spring:url value="${juedangpinStaticPath}/core/images/header/images/big-logo_pig.jpg"/>" alt=""/>
                    <div class="golds"></div>
                    <div class="light"></div>
                </a>
            </h1>
        </div>
        <div class="found">
            找回密码
        </div>
    </div>
    <div id="fixedHead" class="fixed-head-box">
        <div class="fixed-head">
            <div class="logo-box">
                <h1>
                    <a href="<spring:url value="/"/>">
                        <img src="../core/images/header/logo.png" alt="点金子名品"/>
                    </a>
                </h1>
            </div>
        </div>
    </div>
</div>
    <div class="main1" style="display: <c:choose>
        <c:when test="${step=='CHECK_USER_EXIST'}"> 
                block  
              </c:when>
        <c:otherwise>   
             none
             </c:otherwise>
    </c:choose> ">
        <ul class="forget-img">
            <li class="main-font1">填写用户名</li>
            <li class="main-font2">验证身份</li>
            <li class="main-font3">设置新密码</li>
            <li class="main-font4">完成</li>
        </ul>
        <form:form modelAttribute="user" class="forget" action="${pageContext.request.contextPath}/user/resetPassword" method="post">
            <div class="admin">
                账户名：<input type="text" class="text" name="username">
            </div>
            <form:errors path="loginError"/>
            <div class="validate">
                验证码：<input type="text" class="text" name="authCode">	
                <div class="validate-img">
                    <img src="<spring:url value="/code/resetPassword"/>"> 看不清楚？ <a
                        class="link-btn" href="#">换一张</a>
                </div>
            </div>
            <div class="error-box-1"></div>
            <div class="sure-btn">
                <input class="d-btn" type="submit" value="提交"> <input
                    class="l-btn" type="reset" value="取消" />
            </div>
        </form:form>
    </div>
    <!--check phone code-->
    <div class="main2" style="display:<c:choose>
        <c:when test="${step=='CHECK_PHONE_CODE'}"> 
                block  
              </c:when>
        <c:otherwise>   
             none
             </c:otherwise>
    </c:choose>">
        <ul class="forget-img">
            <li class="main-font1">填写用户名</li>
            <li class="main-font2">验证身份</li>
            <li class="main-font3">设置新密码</li>
            <li class="main-font4">完成</li>
        </ul>
        <form class="forget"
            action="${pageContext.request.contextPath}/user/resetPassword" method="post">
            <div class="admin">
                <div class="form">
                    <label>验证方式：</label> <select class="select1">
                        <option value="看书">手机验证</option>
                        <option value="运动">邮箱验证</option>
                    </select> 下拉菜单选择其他方式
                </div>
            <div class="validate">
                <div class="phone">手机号码：${userResult.phoneNumber}</div>
                <div class="validate-img">
                    <input  id="getSmsPath" class="l-btn" type="button" value="获取短信验证码">
                </div>
            </div>
            <div class="phone-validate">
                短信验证码：<input type="text" class="text" name="smsCode">
                 <input type="hidden" id="smsPath" data-value="<spring:url value="/sms/resetPassword?phoneNumber=${userResult.phoneNumber}"/>">
                    <span class="phone-code-wrong">验证码错误!</span>
            </div>      
            <div class="error-box-2"></div>
            
            <div class="sure-btn">
                <input class="d-btn" type="submit" value="提交"> <input
                    class="l-btn" type="reset" value="取消" />
        	</div>
    	</div>
        </form>
    </div>

    <!--reset password-->
    <div class="main3" style="display:<c:choose>
        <c:when test="${step=='SET_NEW_PASSWORD'}"> 
                block  
              </c:when>
        <c:otherwise>   
             none
             </c:otherwise>
    </c:choose>">
        <ul class="forget-img">
            <li class="main-font1">填写用户名</li>
            <li class="main-font2">验证身份</li>
            <li class="main-font3">设置新密码</li>
            <li class="main-font4">完成</li>
        </ul>
        <form class="forget"
            action="${pageContext.request.contextPath}/user/resetPassword" method="post">
            <div class="admin">
                新登录密码：<input  name="password" type="password" class="text" placeholder="6位以上数字或字母">
                <span class="tips">密码格式不正确</span>
            </div>
            <div class="validate">
                确认新密码：<input name="password2" type="password" class="text"> <span class="tips">两次密码不一致</span>
            </div>
            <div class="error-box-3"></div>
            <div class="sure-btn">
                <input class="d-btn" type="submit" value="提交"> <input
                    class="l-btn" type="reset" value="取消" />
            </div>
        </form>
    </div>
    <!--complete-->
    <div class="main4" style="display:<c:choose>
        <c:when test="${step=='COMPLETE'}">   
                block  
              </c:when>
        <c:otherwise>   
             none
             </c:otherwise>
    </c:choose>">
        <ul class="forget-img">
            <li class="main-font1">填写用户名</li>
            <li class="main-font2">验证身份</li>
            <li class="main-font3">设置新密码</li>
            <li class="main-font4">完成</li>
        </ul>
        <form class="forget"
            action="${pageContext.request.contextPath}/user/resetPassword" method="post">
            <div class="forget-main">
                <div class="ture-img">
                    <img src="<spring:url value="${juedangpinStaticPath}/core/images/user/ture-img.png"/>">
                </div>
                <div class="forget-font1">恭喜你，重置密码成功</div>
                <div class="forget-font2">
                    请牢记您设置的密码。<a class="link-btn" href="${pageContext.request.contextPath}/user/login">立即登录</a>
                </div>
            </div>
        </form>   
    </div>
    <jsp:include page="../core/footer-main.jsp" />
</body>
<script
    src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>" data-main="<spring:url value="${juedangpinStaticPath}/user/resetforget.js"/>"></script>
</html>