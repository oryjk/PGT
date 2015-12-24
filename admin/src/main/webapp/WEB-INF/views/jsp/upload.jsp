<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/23/15
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title></title>
	<script type="application/javascript" src="<spring:url value="${adminStaticPath}/core/js/jquery.min.js"/>"></script>
	<script type="application/javascript" src="<spring:url value="${adminStaticPath}/core/js/jquery.form.js"/>"></script>
</head>
<body>

<form id="uploadForm" action="/upload/image" enctype="multipart/form-data" method="post">
	<input type="file" name="uploadPicture" class="image"/>
	<img src="" class="imageShow"/>
	<input type="submit" value="提交" class="submit"/>
</form>
<script type="application/javascript">

	$('.image').change(function () {
		$('#uploadForm').ajaxSubmit({
			url: '${pageContext.request.contextPath}/upload/image',
			dataType: 'json',
			type: 'POST',
			success: function (responseBody) {
				$('.imageShow').attr('src',responseBody.imagePath);
			}
		})
	})
</script>
</body>
</html>
