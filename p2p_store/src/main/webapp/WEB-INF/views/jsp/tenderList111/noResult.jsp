<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="/resources/product-list/product-list.css"/>
	<script src="/resources/core/js/jquery.min.js" data-main="invest"></script>
</head>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->
<!--content begin-->
<div class="content">
	没有找到你想要的
</div>
<!--content end-->

<jsp:include page="../core/recommend-horizontal.jsp"/>

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/product-list/productList"></script>
<jsp:include page="../core/baidu.jsp" />
</body>
</html>
