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
					<a href="<spring:url value="/"/>">首页</a>
					<i class="fa fa-circle"></i>
				</li>
				<li>
					<a href="">商品管理</a>
					<i class="fa fa-circle"></i>
				</li>
				<li class="active">
					<a href="#">商品列表</a>
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
						<button class="btn green-haze btn-circle"><i class="fa fa-plus"></i> 新增</button>
						<div class="btn-group">
							<a class="btn yellow btn-circle" href="javascript:;" data-toggle="dropdown">
								<i class="fa fa-check-circle"></i> 批量操作 <i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu pull-right">
								<li>
									<a href="javascript:;">
										启用分类 </a>
								</li>
								<li>
									<a href="javascript:;">
										禁用分类 </a>
								</li>
								<li class="divider">
								<li>
									<a href="javascript:;">
										删除所选项 </a>
								</li>
								<li class="divider">
								</li>
								<li>
									<a href="javascript:;">
										打印所选项 </a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="portlet-body">
					<div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
						<div class="row">
							<div class="col-xs-2">
								<div class="dataTables_length">
									<label>主分类
										<select name="sample_3_length" aria-controls="sample_3"
												class="form-control input-small input-inline select2-offscreen" path="rootCategoryId"
												tabindex="-1" title="">
											<option value="" selected="true">全部</option>
											<c:forEach items="${categories}" var="category">
												<option value="${category.id}">${category.name}</option>
											</c:forEach>
										</select></label>
								</div>
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
													<img src="" alt=""/>
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
														<ul class="dropdown-menu pull-right">
															<li>
																<a href="javascript:;" data-pgt-value="0" data-pgt-dropdown="status-option">
																	禁用 </a>
															</li>
															<li>
																<a href="javascript:;" data-pgt-value="1" data-pgt-dropdown="status-option">
																	启用 </a>
															</li>
														</ul>
													</div>
												</td>
												<td>
													<button class="btn btn-xs green btn-circle">修改</button>
													<button class="btn btn-xs red btn-circle">删除</button>
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
													<ul class="dropdown-menu pull-right">
														<li>
															<a href="javascript:;" data-pgt-value="0" data-pgt-dropdown="status-option">
																禁用 </a>
														</li>
														<li>
															<a href="javascript:;" data-pgt-value="1" data-pgt-dropdown="status-option">
																启用 </a>
														</li>
													</ul>
												</div>
											</td>
											<td>
												<button class="btn btn-xs green btn-circle">修改</button>
												<button class="btn btn-xs red btn-circle">删除</button>
											</td>
										</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</c:otherwise>
						</c:choose>


						<div class="row">
							<link href="${ctx}/resources/core/css/page.css" rel="stylesheet" type="text/css">
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
</admin:container>
