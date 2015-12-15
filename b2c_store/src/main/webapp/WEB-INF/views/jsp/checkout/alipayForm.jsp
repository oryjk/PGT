<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="alipaysubmit" action="${formAction }?_input_charset=utf-8" method="POST">
	<c:forEach items="${alipayParams }" var="entry">
		<input type="hidden" name="${entry.key }" value="${entry.value}"/>
	</c:forEach>
	<input type="submit" value="pay" style="display:none"/>
</form>
 <script>document.forms['alipaysubmit'].submit();</script>