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
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<admin:container id="productList">
	productList:${productList}
	categoryHierarchy:${categoryHierarchy}
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
					<a href="#">商品列表</a>
				</li>
			</ul>
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
										改为待售 </a>
								</li>
								<li>
									<a href="javascript:;">
										改为正售 </a>
								</li>
								<li>
									<a href="javascript:;">
										改为下架 </a>
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
							<div class="col-md-2 col-sm-2">
								<div class="dataTables_length" id="sample_3_length">
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
							<div class="col-md-2 col-sm-2">
								<div class="dataTables_filter">
									<label>持有人:<input type="search" class="form-control input-small input-inline"
													  placeholder="" aria-controls="sample_3">
									</label>
								</div>
							</div>
							<div class="col-md-2 col-sm-2">
								<div class="dataTables_filter">
									<label>名称:<input type="search" class="form-control input-small input-inline"
													 placeholder="" aria-controls="sample_3">
									</label>
								</div>
							</div>
							<div class="col-md-2 col-sm-2">
								<button class="btn blue">
									搜索
								</button>
							</div>
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
										<div class="btn-group">
											<a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown">
												分类 <i class="fa fa-angle-down"></i>
											</a>
											<ul class="dropdown-menu pull-right">
												<li>
													<a href="javascript:;">
														分类1 </a>
												</li>
												<li>
													<a href="javascript:;">
														分类2 </a>
												</li>
												<li>
													<a href="javascript:;">
														分类3 </a>
												</li>
											</ul>
										</div>
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label=" Email : activate to sort column ascending">
										<div class="btn-group">
											<a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown">
												子分类 <i class="fa fa-angle-down"></i>
											</a>
											<ul class="dropdown-menu pull-right">
												<li>
													<a href="javascript:;">
														子分类1 </a>
												</li>
												<li>
													<a href="javascript:;">
														子分类2 </a>
												</li>
												<li>
													<a href="javascript:;">
														子分类3 </a>
												</li>
											</ul>
										</div>
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label=" Email : activate to sort column ascending">
										持有人
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label="Status : activate to sort column ascending">
										产品名称
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label="Status : activate to sort column ascending">
										市场价(元)
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label="Status : activate to sort column ascending">
										绝当价(元)
									</th>
									<th class="sorting product-face-box-th" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label="Status : activate to sort column ascending">
										略缩图
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
										aria-label="Status : activate to sort column ascending">
										库存
									</th>
									<th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1">
										<div class="btn-group">
											<a class="btn btn-xs  btn-circle" href="javascript:;" data-toggle="dropdown">
												状态 <i class="fa fa-angle-down"></i>
											</a>
											<ul class="dropdown-menu pull-right">
												<li>
													<a href="javascript:;">
														待售 </a>
												</li>
												<li>
													<a href="javascript:;">
														正售 </a>
												</li>
												<li>
													<a href="javascript:;">
														下架 </a>
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
								<tbody>
								<c:forEach items="${productList}" var="product">
									<tr class="gradeX odd" role="row">
										<td>
											<input type="checkbox">
										</td>
										<td class="sorting_1">
											${product.productId}
										</td>
										<td>
											${categoryHierarchy.parentCategory.name}
										</td>
										<td>
											${categoryHierarchy.name}
										</td>
										<td>
											<a class="link-name" href="#">
												鑫鑫珠宝行
											</a>
										</td>
										<td>
											<a class="link-name" href="#">
												${product.name}
											</a>
										</td>
										<td>
											${product.listPrice}
										</td>
										<td>
											${product.salePrice}
										</td>
										<td class="productlist-face-box">
											<img src="${product.thumbnailMedias[0].path}" alt=""/>
										</td>
										<td>
											1
										</td>
										<td>
											<div class="btn-group">
												<a class="btn btn-xs blue btn-circle" href="javascript:;" data-toggle="dropdown">
													<c:choose>
														<c:when test="${product.status==1}">
															正售
														</c:when>
														<c:otherwise>
															下架
														</c:otherwise>
													</c:choose>
													<i class="fa fa-angle-down"></i>
												</a>

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
						<div class="row">
							<div class="col-md-5 col-sm-5">
								<div class="dataTables_info" id="sample_3_info" role="status" aria-live="polite">
									第
									<span>1</span>
									条 到 第
									<span>15</span>
									条 共
									<span>100</span>
									条
								</div>
							</div>
							<div class="col-md-7 col-sm-7">
								<div class="dataTables_paginate paging_simple_numbers" id="sample_3_paginate">
									<ul class="pagination">
										<li class="paginate_button previous disabled" aria-controls="sample_3"
											tabindex="0" id="sample_3_previous"><a href="#"><i
												class="fa fa-angle-left"></i></a></li>
										<li class="paginate_button active" aria-controls="sample_3" tabindex="0"><a
												href="#">1</a></li>
										<li class="paginate_button " aria-controls="sample_3" tabindex="0"><a
												href="#">2</a></li>
										<li class="paginate_button " aria-controls="sample_3" tabindex="0"><a
												href="#">3</a></li>
										<li class="paginate_button next" aria-controls="sample_3" tabindex="0"
											id="sample_3_next"><a href="#"><i class="fa fa-angle-right"></i></a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</admin:container>

