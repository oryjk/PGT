<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <body>
  <form action="execute" method="post">
      <table>
          <thead>
              <th>项目名</th>
              <th>是否打包</th>
          </thead>
              <c:forEach items="${wars}" var="wars" varStatus="m">
                   <tbody>
                       <th>
                            ${wars.name}
                            <input type="hidden" name="wars[${m.index }].name" value="${wars.name}">
                       </th>
                       <th>
                           <c:choose>
                               <c:when test="${fn:contains(wars.value,'/')}">
                                   <input name="wars[${m.index }].value" value="${wars.value}">
                               </c:when>
                               <c:otherwise>
                                    <select name="wars[${m.index }].value">
                                        <option value="true" <c:if test="${wars.value == true}">selected</c:if>>是</option>
                                        <option value="false" <c:if test="${wars.value == false}">selected</c:if>>否</option>
                                    </select>
                               </c:otherwise>
                           </c:choose>
                       </th>
                  </tbody>
              </c:forEach>
      </table>
      <input type="submit" value="一键部署"/>
  </form>
  </body>
</html>
