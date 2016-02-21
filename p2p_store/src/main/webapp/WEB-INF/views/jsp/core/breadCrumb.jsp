<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/6/15
  Time: 11:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${breadCrumb!=null}">
    <div class="bread-nav">
        <div class="row">
            <p>
                <a href="<spring:url value="/"/>">主页</a>
                <c:forEach items="${breadCrumb}" var="item">
                    >
                    <a href="<spring:url value="${item.breadUrl}"/>">${item.breadName}</a>
                </c:forEach>

            </p>

        </div>
    </div>

</c:if>
