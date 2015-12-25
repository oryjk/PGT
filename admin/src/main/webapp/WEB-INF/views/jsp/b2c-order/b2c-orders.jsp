<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/25/2015
  Time: 00:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="<spring:url value="/order/order-list"/>">
    <ul>
        <li><label>currentIndex </label> <input type="text" name="currentIndex"  value="${param.currentIndex}" /></li>
        <li><label>capacity     </label> <input type="text" name="capacity"      value="${param.capacity}" /></li>
        <li><label>sortFieldName</label> <input type="text" name="sortFieldName" value="${param.sortFieldName}" /></li>
        <li><label>asc          </label> <input type="text" name="asc"           value="${param.asc}" /></li>
        <li><label>id           </label> <input type="text" name="id"            value="${param.id}" /></li>
        <li><label>userName     </label> <input type="text" name="userName"      value="${param.userName}" /></li>
        <li><label>priceBeg     </label> <input type="text" name="priceBeg"      value="${param.priceBeg}" /></li>
        <li><label>priceEnd     </label> <input type="text" name="priceEnd"      value="${param.priceEnd}" /></li>
        <li><label>submitTimeBeg</label> <input type="text" name="submitTimeBeg" value="${param.submitTimeBeg}" /></li>
        <li><label>submitTimeEnd</label> <input type="text" name="submitTimeEnd" value="${param.submitTimeEnd}" /></li>
    </ul>
    <input type="submit" value="Query" />
</form>
<br />
<ul>
    <li>Count: ${b2cOrderPage}</li>
    <c:forEach var="order" items="${b2cOrderPage.result}">
    <li>${order}</li>
    </c:forEach>
</ul>
<br />
<hr />
<br />
<form action="<spring:url value="/order/change-order-status"/>">
    <p>${error_message}</p>
    <ul>
        <li><label>id           </label> <input type="text" name="id"            value="${param.id}" /></li>
        <li><label>status       </label> <input type="text" name="status"        value="${param.status}" /></li>
    </ul>
    <input type="submit" value="Change" />
</form>
</body>
</html>
