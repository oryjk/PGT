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


<admin:container id="productList" pageJsPath="/resources/product/product-list.js">
	<c:set value="${searchPaginationBean.currentIndex}" var="currentIndex"/>
	<c:set value="${searchPaginationBean.maxIndex}" var="maxIndex"/>
	<div class="row">
		<div class="col-xs-12">
			<ul class="page-breadcrumb breadcrumb">
				<li>
					<a href="/">首页</a>
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
	<div class="row">
		<div class="col-xs-12">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-cogs font-green-sharp"></i>
						<span class="caption-subject font-green-sharp bold uppercase">表格</span>
					</div>
					<div class="actions btn-set">
						<button class="btn green-haze btn-circle" data-pgt-btn="create" data-url="/product/create"><i class="fa fa-plus"></i> 新增</button>
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
							<div class="col-xs-2">
								<div class="dataTables_length">
									<label>主分类
										<c:set var="rootCategoryId" value="${categoryHierarchy.parentCategory.categoryId}"/>
										<select name="sample_3_length" aria-controls="sample_3"
												class="form-control input-small input-inline select2-offscreen"
												id="mainCategory" tabindex="-1" title="">
											<option value="">全部</option>
											<c:forEach items="${categories}" var="category">
												<c:choose>
													<c:when test="${rootCategoryId == category.id}">
														<option value="${category.id}" selected="true">${category.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${category.id}">${category.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select></label>
								</div>
							</div>
							<div class="col-xs-2">
								<div class="dataTables_length">
									<label>次分类
										<c:set var="categoryId" value="${categoryHierarchy.categoryId}"/>
										<select name="sample_3_length" aria-controls="sample_3"
												class="form-control input-small input-inline select2-offscreen"
												id="viceCategory" tabindex="-1" title="">
											<option value="5">全部</option>
											<c:forEach items="${subCategories}" var="subCategory">
												<c:choose>
													<c:when test="${subCategory.id == categoryId}">
														<option value="${subCategory.id}" selected="true">${subCategory.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${subCategory.id}">${subCategory.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select> </label>
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

						<div class="table-scrollable list-box">
							<table class="table table-striped table-bordered table-hover dataTable no-footer"
								   id="list" role="grid" aria-describedby="sample_3_info">
								<thead>
								<tr role="row">
									<th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" aria-label="">
										<input id="checkAll" type="checkbox">
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
												<c:forEach items="${categories}" var="category">
													<li>
														<a href="javascript:;" data-value="${category.id}">
																${category.name}
														</a>
													</li>
												</c:forEach>
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
									<th class="sorting face-box-th" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
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
										<td class="face-box">
											<img src="${staticServer}${product.thumbnailMedia.path}" alt=""/>
										</td>
										<td>
											1
										</td>
										<td>
											<c:choose>
												<c:when test="${product.status==1}">
													正售
												</c:when>
												<c:otherwise>
													下架
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<button class="btn btn-xs green btn-circle" data-pgt-btn="modify" data-url="/product/update/${product.productId}">
												修改
											</button>
											<button class="btn btn-xs red btn-circle" data-pgt-btn="delete" data-url="/product/delete/${product.productId}">
												删除
											</button>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="row">
							<link rel="stylesheet" href="/resources/core/css/page.css"/>
							<div class="col-xs-2">
								<div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
									第
									<span>${searchPaginationBean.sqlStartIndex+1}</span>
									条 到 第
									<span>${searchPaginationBean.sqlStartIndex+searchPaginationBean.capacity}</span>
									条 共
									<span>${searchPaginationBean.totalAmount}</span>
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
												href="/product/productList?currentIndex=0">首页</a></li>
										<c:choose>
											<c:when test="${searchPaginationBean.maxIndex>5}">
												<c:if test="${searchPaginationBean.currentIndex>2 and searchPaginationBean.currentIndex<searchPaginationBean.maxIndex-3}">
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
													<li class="paginate_button ">
														<a href="/product/productList?currentIndex=${currentIndex-2}">${currentIndex-1}</a>
													</li>
													<li class="paginate_button ">
														<a href="/product/productList?currentIndex=${currentIndex-1}">${currentIndex}</a>
													</li>
													<li class="paginate_button active">
														<a href="/product/productList?currentIndex=${currentIndex}">${currentIndex+1}</a>
													</li>
													<li class="paginate_button">
														<a href="/product/productList?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
													</li>
													<li class="paginate_button">
														<a href="/product/productList?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
													</li>
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
												</c:if>
												<c:if test="${searchPaginationBean.currentIndex<3}">

													<c:forEach var="current" begin="1" end="${currentIndex+1}">
														<li class="paginate_button <c:if test="${searchPaginationBean.currentIndex+1==current}">active</c:if> ">
															<a href="/product/productList?currentIndex=${current-1}">${current}</a>
														</li>
													</c:forEach>
													<li class="paginate_button">
														<a href="/product/productList?currentIndex=${currentIndex+1}">${currentIndex+2}</a>
													</li>
													<li class="paginate_button">
														<a href="/product/productList?currentIndex=${currentIndex+2}">${currentIndex+3}</a>
													</li>
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
												</c:if>
												<c:if test="${searchPaginationBean.currentIndex+4>searchPaginationBean.maxIndex}">
													<li class="paginate_button disabled">
														<a href="javascript:;">...</a>
													</li>
													<li class="paginate_button">
														<a href="/product/productList?currentIndex=${currentIndex-2}">${currentIndex-2}</a>
													</li>
													<li class="paginate_button">
														<a href="/product/productList?currentIndex=${currentIndex-1}">${currentIndex-1}</a>
													</li>
													<c:forEach var="current" begin="${currentIndex+1}" end="${maxIndex+1}">
														<li class="paginate_button <c:if test="${searchPaginationBean.currentIndex+1==current}">active</c:if> ">
															<a href="/product/productList?currentIndex=${current-1}">${current}</a>
														</li>
													</c:forEach>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:forEach var="current" begin="1" end="${searchPaginationBean.maxIndex+1}">

													<li class="paginate_button <c:if test="${searchPaginationBean.currentIndex+1==current}">active</c:if> ">
														<a href="/product/productList?currentIndex=${current-1}">${current}</a>
													</li>
												</c:forEach>
											</c:otherwise>
										</c:choose>


										<li class="paginate_button"><a
												href="/product/productList?currentIndex=${searchPaginationBean.maxIndex}">末页</a></li>

									</ul>
								</div>
							</div>
							<div class="col-xs-2">
								<form class="dataTables_filter pgt-goto-page" action="/product/productList" method="get">
									<label>
										<input type="search" value="${currentIndex+1}" name="currentIndex" class="form-control input-xsmall input-inline" placeholder="第几页">
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

