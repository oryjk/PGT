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
<pgt:container id="content">
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
                <div class="row">
                  <link rel="stylesheet" href="../core/css/page.css"/>
                  <div class="col-xs-2">
                    <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
                      第
                      <span>1</span>
                      条 到 第
                      <span>15</span>
                      条 共
                      <span>100</span>
                      条
                    </div>
                  </div>
                  <div class="col-xs-2">
                    <div class="dataTables_length pgt-each-page">
                      <label>每页显示
                        <select name="sample_3_length" aria-controls="sample_3"
                                class="form-control input-xsmall input-inline select2-offscreen"
                                tabindex="-1" title="">
                          <option value="5">5</option>
                          <option value="15">15</option>
                          <option value="20">20</option>
                          <option value="-1">所有</option>
                        </select> 条</label>
                    </div>
                  </div>
                  <div class="col-md-4 col-sm-4">
                    <div class="dataTables_paginate paging_simple_numbers pgt-page-box">
                      <!-- 当前页需要增加active类,首页末页的禁用是增加disabled类-->
                      <ul class="pagination">
                        <li class="paginate_button previous disabled"><a href="#"><i
                                class="fa fa-angle-left"></i></a></li>
                        <li class="paginate_button"><a
                                href="#">首页</a></li>
                        <li class="paginate_button disabled"><a
                                href="javascript:;">...</a></li>
                        <li class="paginate_button "><a
                                href="#">3</a></li>
                        <li class="paginate_button "><a
                                href="#">4</a></li>
                        <li class="paginate_button active"><a
                                href="#">5</a></li>
                        <li class="paginate_button"><a
                                href="#">6</a></li>
                        <li class="paginate_button"><a
                                href="#">7</a></li>
                        <li class="paginate_button disabled"><a
                                href="javascript:;">...</a></li>
                        <li class="paginate_button"><a
                                href="#">末页</a></li>
                        <li class="paginate_button next"><a href="#"><i class="fa fa-angle-right"></i></a></li>
                      </ul>
                    </div>
                  </div>
                  <div class="col-xs-2">
                    <form class="dataTables_filter pgt-goto-page">
                      <label>
                        <input type="search" class="form-control input-xsmall input-inline" placeholder="第几页">
                        <input type="submit" class="btn blue" value="跳转">
                      </label>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>

  </div>
</pgt:container>