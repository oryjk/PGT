<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 12/25/15
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<admin:container id="productList" pageJsPath="/resources/category/category-list.js">
	<div class="row">
		<div class="col-xs-12">
			<ul class="page-breadcrumb breadcrumb">
				<li>
					<a href="/">首页</a>
					<i class="fa fa-circle"></i>
				</li>
				<li>
					<a href="">分类列表</a>
					<i class="fa fa-circle"></i>
				</li>
				<li class="active">
					<a href="/category/categoryList">主分类</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- super:把错误内容放在span里面,有两种提示框 alert-danger 和 alert-success 两种.如果不需要显示时把display改为none-->
	<div class="row" style="display: none">
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
						<button class="btn green-haze btn-circle" data-pgt-btn="create" data-url="/category/create"><i class="fa fa-plus"></i> 新增</button>
					</div>
				</div>
				<div class="portlet-body">
					<div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
						<div class="row">
							<div class="col-xs-2">
								<div class="dataTables_length">
									<label>主分类
										<select id="categorySelect" name="sample_3_length" aria-controls="sample_3"
												class="form-control input-small input-inline select2-offscreen" path="rootCategoryId"
												tabindex="-1" title="">
											<option value="all" selected="true">全部</option>
											<c:forEach items="${rootCategory}" var="category">
												<option ${category.id eq categoryId ? "selected='selected'" :''} value="${category.id}">${category.name}</option>
											</c:forEach>
										</select></label>
								</div>

							</div>
							<div class="col-md-2 col-sm-2">
								<div class="dataTables_filter">
									<label>名称:<input id="name" type="search" value="${term}" class="form-control input-small input-inline"
													 placeholder="" aria-controls="sample_3">
									</label>
								</div>
							</div>



							<div class="col-md-2 col-sm-2">
								<button id="searchBtn" class="btn blue">
									搜索
								</button>
							</div>

						</div>


						<c:choose>
							<c:when test="${categoryType eq 'ROOT'}">
								<div class="table-scrollable productlist-box">
									<table class="table table-striped table-bordered table-hover dataTable no-footer"
										   id="list1" role="grid" aria-describedby="sample_3_info">
										<thead>
										<tr role="row">
											<th>
												位置
											</th>
											<th>
												分类id
											</th>
											<th>
												分类名称
											</th>
											<th>
												副标题
											</th>
											<th>
												封面图
											</th>
											<th>
												色调
											</th>
											<th>
												子分类数
											</th>
											<th>
												状态
											</th>
											<th>
												操作
											</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${categories}" var="rootCategory">
											<tr class="gradeX odd" role="row">
												<td>
														${rootCategory.sort}
												</td>
												<td>
														${rootCategory.id}
												</td>
												<td>
														${rootCategory.name}
												</td>
												<td>
														${rootCategory.description}
												</td>
												<td class="face-box">
													<img src="${staticServer}${rootCategory.frontMedia.path}" alt="" style="width: 150px;height: 206.5px;"/>
												</td>
												<td>
														${rootCategory.color}
												</td>
												<td>
														${fn:length(rootCategory.children)}
												</td>
												<td>
													<div class="btn-group">
														<a class="btn btn-xs default btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="">
															<c:choose>
																<c:when test="${rootCategory.status==1}">
																	启用
																</c:when>
																<c:otherwise>
																	禁用
																</c:otherwise>
															</c:choose>
															<i class="fa fa-angle-down"></i>
														</a>

													</div>
												</td>
												<td>
													<button class="btn btn-xs green btn-circle" data-pgt-btn="modify" data-url="/category/update/${rootCategory.id}">修改</button>
													<button class="btn btn-xs red btn-circle" data-pgt-btn="delete" data-url="/category/delete/${rootCategory.id}" data-pgt-type="ROOT">删除</button>
												</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>

							</c:when>
							<c:otherwise>
								<div class="table-scrollable productlist-box">
									<table class="table table-striped table-bordered table-hover dataTable no-footer"
										   id="list" role="grid" aria-describedby="sample_3_info">
										<thead>
										<tr role="row">
											<th>
												位置
											</th>
											<th>
												分类名称
											</th>
											<th>
												分类id
											</th>
											<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1">
												<div class="btn-group">
													<a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="">
														状态 <i class="fa fa-angle-down"></i>
													</a>
													<ul class="dropdown-menu pull-right">
														<li>
															<a href="javascript:;" data-pgt-value="1" data-pgt-dropdown="status-option">
																启用 </a>
														</li>
														<li>
															<a href="javascript:;" data-pgt-value="0" data-pgt-dropdown="status-option">
																禁用 </a>
														</li>
													</ul>
												</div>
											</th>
											<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
												aria-label="Status : activate to sort column ascending">
												操作
											</th>
										</tr>
										</thead>
										<c:forEach items="${categories}" var="rootCategory">
										<tr class="gradeX odd" role="row">
											<td>
													${rootCategory.sort}
											</td>
											<td>
													${rootCategory.name}
											</td>
											<td>
													${rootCategory.id}
											</td>
											<td>
												<div class="btn-group">
													<a class="btn btn-xs blue btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="1">
														<c:choose>
															<c:when test="${rootCategory.status==1}">
																启用
															</c:when>
															<c:otherwise>
																禁用
															</c:otherwise>
														</c:choose>
														<i class="fa fa-angle-down"></i>
													</a>

												</div>
											</td>
											<td>
												<button class="btn btn-xs green btn-circle" data-pgt-btn="modify" data-url="/category/update/${rootCategory.id}">修改</button>
												<button class="btn btn-xs red btn-circle" data-pgt-btn="delete" data-url="/category/delete/${rootCategory.id}" data-pgt-type="HIERARCHY">删除</button>
											</td>
										</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="row">
							<link rel="stylesheet" href="/resources/core/css/page.css"/>
							<div class="col-xs-2">
								<div class="dataTables_info pgt-page-count" id="sample_3_info" role="status"
									 aria-live="polite">
									第
									<span>${paginationBean.sqlStartIndex+1}</span>
									条 到 第
									<span>${paginationBean.sqlStartIndex+fn:length(tenderList)}</span>
									条 共
									<span>${paginationBean.totalAmount}</span>
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
										</select> 条</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-4">
								<div class="dataTables_paginate paging_simple_numbers pgt-page-box">
									<!-- 当前页需要增加active类,首页末页的禁用是增加disabled类-->
									<ul class="pagination" id="pagination">

										<li class="paginate_button"><a
												href="/category/query?currentIndex=0">首页</a></li>
										<c:choose>
											<c:when test="${paginationBean.maxIndex>5}">
												<c:if test="${paginationBean.currentIndex>2 and paginationBean.currentIndex<paginationBean.maxIndex-3}">
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
													<li class="paginate_button ">
														<a href="/category/query?currentIndex=${currentIndex-2}">${currentIndex-1}</a>
													</li>
													<li class="paginate_button ">
														<a href="/category/query?currentIndex=${currentIndex-1}">${currentIndex}</a>
													</li>
													<li class="paginate_button active">
														<a href="/category/query?currentIndex=${currentIndex}">${currentIndex+1}</a>
													</li>
													<li class="paginate_button">
														<a href="/category/query?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
													</li>
													<li class="paginate_button">
														<a href="/category/query?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
													</li>
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
												</c:if>
												<c:if test="${paginationBean.currentIndex<3}">

													<c:forEach var="current" begin="1" end="${currentIndex+1}">
														<li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
															<a href="/category/query?currentIndex=${current-1}">${current}</a>
														</li>
													</c:forEach>
													<li class="paginate_button">
														<a href="/category/query?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
													</li>
													<li class="paginate_button">
														<a href="/category/query?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
													</li>
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
												</c:if>
												<c:if test="${paginationBean.currentIndex+4>paginationBean.maxIndex}">
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
													<li class="paginate_button">
														<a href="/category/query?currentIndex=${currentIndex-2}">${currentIndex-2}</a>
													</li>
													<li class="paginate_button">
														<a href="/category/query?currentIndex=${currentIndex-1}">${currentIndex-1}</a>
													</li>
													<c:forEach var="current" begin="${currentIndex+1}"
															   end="${maxIndex+1}">
														<li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
															<a href="/category/query?currentIndex=${current-1}">${current}</a>
														</li>
													</c:forEach>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:forEach var="current" begin="1" end="${paginationBean.maxIndex+1}">

													<li class="paginate_button <c:if test="${paginationBean.currentIndex+1==current}">active</c:if> ">
														<a href="/category/query?currentIndex=${current-1}">${current}</a>
													</li>
												</c:forEach>
											</c:otherwise>
										</c:choose>


										<li class="paginate_button"><a
												href="/category/query?currentIndex=${paginationBean.maxIndex}">末页</a>
										</li>

									</ul>
								</div>
							</div>
							<div class="col-xs-2">
								<form id="pageSubmit" class="dataTables_filter pgt-goto-page" action="/category/query" method="get">
									<label>
										<input id="term" type="hidden" name="term" value="${term}">
										<input type="search" value="${currentIndex+1}" name="currentIndex"
											   class="form-control input-xsmall input-inline" placeholder="第几页">
										<input type="hidden" id="type" name="type"  value="${categoryType}">
										<input type="hidden" id="category" name="categoryId" value="${categoryId}">
										<input type="submit" class="btn blue pgt-goto-page-btn" value="跳转">
									</label>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</admin:container>

