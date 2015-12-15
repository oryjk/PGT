<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 10/23/2015
  Time: 11:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table>
    <tr>
        <th>#</th>
        <th>login</th>
        <th>name</th>
        <th>available</th>
        <th>role</th>
        <th>invest type</th>
        <th>phone</th>
        <th>email</th>
        <th>ip</th>
        <th>creation</th>
        <th>last login</th>
        <th>last update</th>
    </tr>
    <c:forEach var="iu" items="${internalUserPage.result}">
        <tr>
            <td>${iu.id}</td>
            <td>${iu.login}</td>
            <td>${iu.name}</td>
            <td>${iu.available}</td>
            <td>${iu.role}</td>
            <td>${iu.investType}</td>
            <td>${iu.phone}</td>
            <td>${iu.email}</td>
            <td>${iu.ip}</td>
            <td>${iu.creationDate}</td>
            <td>${iu.lastLoginDate}</td>
            <td>${iu.updateDate}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
