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
	product:${product}
	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath"/>
	<div class="row">
		<div class="col-xs-12">
			<ul class="page-breadcrumb breadcrumb">
				<li>
					<a href="#">首页</a>
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
										<label class="col-md-2 control-label">略缩图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" type="file"/>
												<input name="mediaType" type="hidden" value="thumbnail"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-thumbnail-img" src="" alt=""/>
												<p>60 * 60</p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">首页图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" type="file"/>
												<input name="mediaType" type="hidden"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-advertisement-img" src="" alt=""/>
												<p>60 * 60</p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">列表图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" type="file"/>
												<input name="mediaType" type="hidden" value="front"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-front-img" src="" alt=""/>
												<p>60 * 60</p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">主图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" type="file"/>
												<input name="mediaType" type="hidden" value="hero"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-hero-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-hero-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-hero-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">详情图</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" type="file"/>
												<input name="mediaType" type="hidden" value="main"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-main-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-main-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-main-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-2 control-label">专家点评</label>
										<div class="col-md-2">
											<form class="pgt-file-box" action="" enctype="multipart/form-data">
												<input class="pgt-file-btn" name="uploadPicture" type="file"/>
												<input name="mediaType" type="hidden" value="expert"/>
												<button type="button" class="btn blue">选择图片</button>
											</form>
											<p></p>
										</div>
										<div class="col-md-8">
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-expert-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-expert-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
											<div class="pgt-each-img">
												<div class="pgt-handle-box">
													<a class="pgt-img-pre" href="#">前移</a>
													<a class="pgt-img-delete" href="#">删除</a>
												</div>
												<img class="pgt-expert-img" src="" alt=""/>
												<p class="pgt-img-size">100 * 100</p>
											</div>
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
</admin:container>

