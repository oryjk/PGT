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
<admin:container id="productList" pageJsPath="/resources/p2p-tender/tender-add-and-modify.js" pageCssPath="/resources/p2p-tender/tender-add-and-modify.css" >
	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath"/>
	<input type="hidden" value="${staticServer}" id="staticServer"/>
	<input type="hidden" value="${tender.name}" id="tenderName"/>
	<input type="hidden" value="${tender.tenderId}" id="tenderId"/>
	<div class="row">
		<div class="col-xs-12">
			<ul class="page-breadcrumb breadcrumb">
				<li>
					<a href="#">首页</a>
					<i class="fa fa-circle"></i>
				</li>
				<li>
					<a href="/tender/tenderList">投资列表</a>
					<i class="fa fa-circle"></i>
				</li>
				<li class="active">
					<a href="#">新增投资</a>
					<i class="fa fa-circle"></i>
				</li>
				<li class="active">
					<a href="#">投资id:<label id="tenderId"></label></a>
					<i class="fa fa-circle"></i>
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
			<div class="portlet box blue-hoki">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-gift"></i>图片信息
					</div>
				</div>
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<div class="form-horizontal">
						<div class="form-body">
							<div>
								<div>
									<div class="form-group">
										<label class="col-md-2 control-label">首页图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture"  data-pgt-btn="single" type="file"/>
												<input name="mediaType" type="hidden" value="tender_advertisement"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-delete" href="#" data-pgt-btn="delete-single" data-url="/media/delete/${product.advertisementMedia.id}">删除</a>
												</div>
												<img class="pgt-advertisement-img" src="${tender.p2pAdvertisement.path}" alt=""/>
												<p></p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">列表图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture"  data-pgt-btn="single" type="file"/>
												<input name="mediaType" type="hidden" value="tender_front"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-delete" href="#" data-pgt-btn="delete-single" data-url="/media/delete/${product.frontMedia.id}">删除</a>
												</div>
												<img class="pgt-front-img" src="${tender.p2pFrontMedia.path}" alt=""/>
												<p></p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">主图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="multiple" type="file"/>
												<input name="mediaType" type="hidden" value="tender_hero"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<c:forEach items="${tender.p2pHeroMedias}" var="heroMedia">
												<div class="pgt-each-img">
													<div class="pgt-handle-box">
														<a class="pgt-img-delete" href="#" data-pgt-btn="delete-multiple" data-url="/media/delete/${heroMedia.id}">删除</a>
													</div>
													<img class="pgt-hero-img" src="${heroMedia.path}" alt=""/>
													<p class="pgt-img-size"></p>
												</div>
											</c:forEach>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">详情图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="multiple" type="file"/>
												<input name="mediaType" type="hidden" value="tender_main"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<c:forEach items="${tender.p2pMainMedia}" var="mainMedia">
												<div class="pgt-each-img">
													<div class="pgt-handle-box">
														<a class="pgt-img-delete" href="#" data-pgt-btn="delete-multiple" data-url="/media/delete/${mainMedia.id}">删除</a>
													</div>
													<img class="pgt-hero-img" src="${mainMedia.path}" alt=""/>
													<p class="pgt-img-size">100 * 100</p>
												</div>
											</c:forEach>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">专家点评</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
												<input name="mediaType" type="hidden" value="tender_expert"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>

										<div class="col-md-8">
												<div class="pgt-each-img">
													<div class="pgt-handle-box">
														<a class="pgt-img-delete" href="#" data-pgt-btn="delete-single" data-url="/media/delete/${product.expertMedia.id}">删除</a>
													</div>
													<img class="pgt-hero-img" src="${tender.p2pExpertMedia.path}" alt=""/>
													<p class="pgt-img-size"></p>
												</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">手机详情图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
												<input name="mediaType" type="hidden" value="tender_mobileDetail"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>

										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-delete" href="#" data-pgt-btn="delete-single" data-url="/media/delete/${product.mobileDetailMedia.id}">删除</a>
												</div>
												<img class="pgt-hero-img" src="${tender.mobileDetailMedia.path}" alt=""/>
												<p class="pgt-img-size"></p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-8"style="float: right" >
											<a href="/tender/addProductStepBase?tenderId=${tenderId}">
											<button id="searchBtn" style="float: right" class="btn blue">
												添加产品
											</button>
											</a>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>

				</div>
				<!-- END FORM-->
			</div>
		</div>
	</div>
	<div id="testbox">
	</div>
</admin:container>

