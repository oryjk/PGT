<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="main-right">
    <!--面包屑-->
    <div class="bread-nav">
        <p>
            <a href="#">帮助中心</a>
            >
            <a href="#">购物与支付</a>
            >
            <a href="#">${title}</a>
        </p>
    </div>
    <img src="${pageContext.request.contextPath}/resources/${frontMedia.path}" alt="${title}">
</div>