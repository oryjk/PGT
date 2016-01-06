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
					<a href="#">主分类</a>
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
													<img src="${staticServer}${rootCategory.frontMedia.path}" alt="" style="width: 150px;height: 200px;d"/>
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
													<button class="btn btn-xs red btn-circle" data-pgt-btn="delete" data-url="/category/delete/${rootCategory.id}">删除</button>
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
												<button class="btn btn-xs green btn-circle" data-pgt-btn="modify" data-url="/category/update/${rootCategory.id}">修改</button>
												<button class="btn btn-xs red btn-circle" data-pgt-btn="delete" data-url="/category/delete/${rootCategory.id}">删除</button>
											</td>
										</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

		</div>
	</div>
</admin:container>

