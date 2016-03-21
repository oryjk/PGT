<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title></title>
  <link rel="stylesheet" href="/resources/user/login.css"/>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-myaccount.jsp"/>
<!--header end-->


<div class="content">
  <div class="content-box">
    <div class="content-img"></div>
    <div class="login">
      <div class="login-success">
        <div class="login-success-title">
          操作提示
        </div>
        <h2>注 册 成 功 !</h2>
        <div class="login-success-timeout">
          正在为您跳转,等待 <span id="time" class="link-btn">5</span> 秒
        </div>
        <div class="go-to-home-box">
          <a class="go-to-home" href="#">前往主页</a>
          <a class="go-to-home" href="#">前往个人中心</a>
        </div>
      </div>
    </div>
  </div>
</div>

<!--content end-->
<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->
<script src="/resources/core/js/require.js" data-main="/resources/user/login"></script>
<jsp:include page="../core/baidu.jsp"/>
</body>
</html>

<script>
  var time = document.getElementById('time');
  var timeout = function () {
    time.innerHTML --;
    if (time.innerHTML == 0) {
      window.location = '';
    }
    window.setTimeout(timeout, 1000);
  };
  timeout();

</script>