<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/2
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="table-scrollable list-box">
  <table class="table table-striped table-bordered table-hover dataTable no-footer"
         id="list" role="grid" aria-describedby="sample_3_info">
    <thead>
    <tr role="row">
      <th>
        订单号
      </th>
      <th>
        交易类型
      </th>
      <th>
        金额
      </th>
      <th>
        交易时间
      </th>
      <th>
        交易单号
      </th>
      <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1">
        <div class="btn-group">
          <a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown">
            状态 <i class="fa fa-angle-down"></i>
          </a>
          <ul class="dropdown-menu pull-right">
            <li>
              <a href="javascript:;">
                状态1 </a>
            </li>
            <li>
              <a href="javascript:;">
                状态2 </a>
            </li>
          </ul>
        </div>
      </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="trans" items="${result}">
      <tr class="gradeX odd" role="row">
        <td>
            ${trans.orderId}
        </td>
        <td>
          <c:if test="${trans.paymentType ==  1}">
            易宝
          </c:if>
          <c:if test="${trans.paymentType == 2}">
            支付宝
          </c:if>
        </td>
        <td>
          <fmt:formatNumber value="${trans.amount}" type="currency" pattern="￥.00"/>
        </td>
        <td>
          <fmt:formatDate  value="${trans.transactionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </td>
        <td>
            ${trans.trackingNo}
        </td>
        <td>
          <c:if test="${trans.status ==  1}">
            支付成功
          </c:if>
          <c:if test="${trans.status == 0}">
            支付中
          </c:if>
          <c:if test="${trans.status == -1}">
            支付失败
          </c:if>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
