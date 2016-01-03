<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/30
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<pgt:container id="content" loadJsDateInput="true" pageJsPath="/resources/order/order-list.js">
  <div class="page-content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-xs-12">
          <ul class="page-breadcrumb breadcrumb">
            <li>
              <a href="#">首页</a>
              <i class="fa fa-circle"></i>
            </li>
            <li>
              <a href="table_managed.html">商品管理</a>
              <i class="fa fa-circle"></i>
            </li>
            <li class="active">
              <a href="#">订单列表</a>
            </li>
          </ul>
        </div>
      </div>
      <!-- super:把错误内容放在span里面,有两种提示框 alert-danger 和 alert-success 两种.如果不需要显示时把display改为none-->
      <div class="row" style="display: block">
        <div class="col-xs-12">
          <div class="Metronic-alerts alert alert-danger fade in">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
            <p>错误信息</p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <div class="portlet light">
            <div class="portlet-title">
              <div class="caption">
                <i class="fa fa-cogs font-green-sharp"></i>
                <span class="caption-subject font-green-sharp bold uppercase">表格</span>
              </div>
              <div class="actions btn-set">
              </div>
            </div>
            <div class="portlet-body">
              <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                <jsp:include page="include/queryTransForm.jsp"/>
                <jsp:include page="include/queryTransResult.jsp"/>
                <jsp:include page="include/queryTransPagination.jsp"/>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>

  </div>
</pgt:container>