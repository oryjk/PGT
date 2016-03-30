<%--
  Created by IntelliJ IDEA.
  User: Yove
  Date: 12/26/2015
  Time: 00:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pagination" value="${internalUserPage}" scope="request" />

<pgt:container id="content">
    <jsp:include page="include/bread-crumb-row.jspf" />
    <div class="row">
        <div class="col-xs-12">
            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-cogs font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase">管理员列表</span>
                    </div>
                    <div class="actions btn-set">
                        <button class="btn green-haze btn-circle"><a href="/internal/register"><i
                                class="fa fa-plus"></i> 新增</a></button>
                        <div class="btn-group">
                            <a class="btn yellow btn-circle" href="javascript:;" data-toggle="dropdown">
                                <i class="fa fa-check-circle"></i> 批量操作 <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu pull-right">
                                <li>
                                    <a href="javascript:;">
                                        启用所选项 </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        禁用所选项 </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                        <div class="row">
                            <form action="/internal/iu-list" method="get">
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
                                        aria-label=" Email : activate to sort column ascending">
                                        级别
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label=" Email : activate to sort column ascending">
                                        昵称
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        账号
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        电话
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        注册时间
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        最后登陆时间
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        最后登陆ip
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        最后更新时间
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        状态
                                    </th>
                                    <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                        aria-label="Status : activate to sort column ascending">
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="list">
                                <c:forEach var="iu" items="${internalUserPage.result}" varStatus="index">
                                    <tr class="gradeX odd" role="row">
                                        <td><input type="checkbox"></td>
                                        <td class="sorting_1">${iu.id}</td>
                                        <td><button class="btn btn-xs blue">${roles[iu.role]}</button></td>
                                        <td><a class="link-name" href="#">${iu.name}</a></td>
                                        <td><a class="link-name" href="#">${iu.login}</a></td>
                                        <td>${iu.phone}</td>
                                        <td><fmt:formatDate value="${iu.creationDate}" pattern="yyyy-MM-dd HH:mm:sss" /></td>
                                        <td><fmt:formatDate value="${iu.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:sss" /></td>
                                        <td>${iu.ip}</td>
                                        <td><fmt:formatDate value="${iu.updateDate}" pattern="yyyy-MM-dd HH:mm:sss" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${iu.available}">
                                                    <button class="btn btn-xs blue btn-circle">启用</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-xs gray btn-circle">禁用</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><a href="/internal/iu-modify?uid=${iu.id}"><button class="btn btn-xs blue btn-circle">修改</button></a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <%-- pagination row --%>
                        <form action="/internal/iu-list" method="get">
                            <div class="row">
                                <jsp:include page="../b2c-order/include/pagination-capacity-selection.jsp">
                                    <jsp:param name="paginationURL" value="/internal/iu-list?keyword=${param.keyword}" />
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