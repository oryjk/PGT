<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 1/17/2016
  Time: 2:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<admin:container id="productList" pageJsPath="/resources/product/product-add-and-modify.js">
	<h2 style="margin: 20px auto 20px;text-align: center">对不起，您暂时没有权限，请先登录或者切换成有权限的账号进行操作。</h2>

	<p style="margin: 20px auto 20px;text-align: center">点击这里<a href="/logout"> 重新登录</a> 具有权限的账号</p>
</admin:container>

