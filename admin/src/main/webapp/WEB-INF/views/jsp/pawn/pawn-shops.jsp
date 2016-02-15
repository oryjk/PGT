<%--
  Created by IntelliJ IDEA.
  User: jeniss
  Date: 16/2/15
  Time: 上午11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pagination" value="${pawnShopPage}" scope="request" />
<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf" />
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-cogs font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase">当铺列表</span>
                    </div>
                    <div class="actions btn-set">
                        <button class="btn green-haze btn-circle">
                            <a href="/pawn/update-pawn-shop"><i
                                    class="fa fa-plus"></i> 新增
                            </a>
                        </button>
                        <div class="btn-group">
                                <%--
                                <a class="btn yellow btn-circle" href="javascript:;" data-toggle="dropdown">
                                    <i class="fa fa-check-circle"></i> 批量操作 <i class="fa fa-angle-down"></i>
                                </a>
                                <ul class="dropdown-menu pull-right">
                                    <li>
                                        <a href="javascript:;">
                                            启用所选项
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:;">
                                            禁用所选项
                                        </a>
                                    </li>
                                </ul>
                                --%>
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                        <div class="row">
                            <form action="/pawn/pawn-shop-list" method="get">
                                <input type="hidden" name="currentIndex" value="${param.currentIndex}" />
                                <input type="hidden" name="capacity" value="${param.capacity}" />
                                <div class="col-md-2 col-sm-2">
                                    <div class="dataTables_filter">
                                        <label>
                                            <input type="search" class="form-control input-small input-inline" name="keyword"
                                                   value="${param.keyword}" placeholder="" aria-controls="sample_3">
                                        </label>
                                    </div>
                                </div>
                                <div class="col-xs-2">
                                    <input type="submit" class="btn blue" value="搜索" />
                                </div>
                            </form>
                        </div>
                        <div class="table-scrollable productlist-box">
                            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                   id="sample_3" role="grid" aria-describedby="sample_3_info">
                                <thead>
                                <tr role="row">
                                    <th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                        <input type="checkbox">
                                    </th>
                                    <th class="sorting_asc" tabindex="0" aria-controls="sample_3" rowspan="1"
                                        colspan="1" aria-sort="ascending" aria-label="Username : activate to sort column ascending">
                                        序号
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        名称
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label=" Email : activate to sort column ascending">
                                        所有者id
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label=" Email : activate to sort column ascending">
                                        管理员id
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        地址
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        创建时间
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        最后更新时间
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="list">
                                <c:forEach var="shop" items="${pawnShopPage.result}" varStatus="index">
                                    <tr class="gradeX odd" role="row">
                                        <td>
                                            <input type="checkbox">
                                        </td>
                                        <td class="sorting_1">${shop.pawnshopId}</td>
                                        <td>
                                            <button class="btn btn-xs blue">${shop.name}</button>
                                        </td>
                                        <td>
                                            <a class="link-name" href="#">${shop.ownerId}</a>
                                        </td>
                                        <td>
                                            <a class="link-name" href="#">${shop.managerId}</a>
                                        </td>
                                        <td>${shop.address}</td>
                                        <td><fmt:formatDate value="${shop.creationDate}" pattern="yyyy-MM-dd HH:mm:sss" /></td>
                                        <td><fmt:formatDate value="${shop.updateDate}" pattern="yyyy-MM-dd HH:mm:sss" /></td>
                                        <td>
                                            <a href="/pawn/update-pawn-shop?shopId=${shop.pawnshopId}">
                                                <button class="btn btn-xs blue btn-circle">修改</button>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                            <%-- pagination row --%>
                        <form action="/pawn/pawn-shop-list" method="get">
                            <div class="row">
                                <jsp:include page="../b2c-order/include/pagination-capacity-selection.jsp">
                                    <jsp:param name="paginationURL" value="/pawn/pawn-shop-list?keyword=${param.keyword}" />
                                </jsp:include>
                            </div>
                            <input type="hidden" name="keyword" value="${param.keyword}" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</pgt:container>
