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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<admin:container id="productList" pageJsPath="/resources/product/product-add-and-modify.js">
	<div class="row">
		<div class="col-xs-12">
			<ul class="page-breadcrumb breadcrumb">
				<li>
					<a href="<spring:url value="/"/>">首页</a>
					<i class="fa fa-circle"></i>
				</li>
				<li>
					<a href="#">产品列表</a>
					<i class="fa fa-circle"></i>
				</li>
				<li class="active">
					<a href="#">新增产品</a>
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
			<div class="portlet box blue-hoki">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-gift"></i>基本信息
					</div>
				</div>
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form:form modelAttribute="product" method="post" action="create/stepBase" class="form-horizontal">
						<div class="form-body">
							<!-- 只有在修改时才显示id行-->
							<c:if test="${!empty product.productId}">
							<div class="form-group">
								<label class="col-md-3 control-label">id</label>
								<div class="col-md-4">
									<p class="form-control-static">
										${product.productId}
									</p>
								</div>
							</div>
							</c:if>
							<div class="form-group">
								<label class="col-md-3 control-label">产品名称:</label>
								<div class="col-md-4">
									<form:input path="name" placeholder="不超过30字" class="form-control"/>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">所属分类</label>
								<div class="col-md-9">
									<div class="radio-list">
										<form:select path="relatedCategoryId" class="form-control input-medium">
											<c:forEach items="${categories}" var="category">
												<form:option value="${category.id}">${category.name}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">是否热门</label>
								<div class="col-md-9">
									<div class="radio-list">
										<form:select path="isHot" class="form-control input-medium">
											<form:option value="false">不热门</form:option>
											<form:option value="true">热门</form:option>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">所属商家id</label>
								<div class="col-md-4">
									<input type="" class="form-control" placeholder="商家id">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">卖点描述</label>
								<div class="col-md-4">
									<form:input  path="title" class="form-control" placeholder="不超过20字"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">款式</label>
								<div class="col-md-4">
									<form:input path="shortDescription" class="form-control" placeholder="不超过20字"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">成色</label>
								<div class="col-md-4">
									<form:input path="isNew" class="form-control" placeholder="不超过10字"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">库存</label>
								<div class="col-md-4">
									<form:input path="stock" class="form-control" placeholder="不超过20字" value="1"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">市场价</label>
								<div class="col-md-4">
									<form:input path="listPrice" class="form-control" placeholder="格式为xxxx.xx"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">绝当价</label>
								<div class="col-md-4">
									<form:input path="salePrice" class="form-control" placeholder="格式为xxxx.xx"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">状态</label>
								<div class="col-md-9">
									<div class="radio-list">
										<form:select path="status" class="form-control input-medium">
											<form:option value="1">启用</form:option>
											<form:option value="0">禁用</form:option>
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<!-- super:修改产品时才出现下面一个div.form-group-->

						<div class="form-actions top">
							<div class="row">
								<div class="col-md-offset-3 col-md-9">
									<button type="submit" class="btn blue-hoki">下一步</button>
									<button type="button" class="btn default">取消</button>
								</div>
							</div>
						</div>
					</form:form>
				</div>
				<!-- END FORM-->
			</div>
		</div>
	</div>
</admin:container>
