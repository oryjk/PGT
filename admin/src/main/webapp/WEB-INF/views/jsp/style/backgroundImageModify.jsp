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
<admin:container id="productList" pageJsPath="/resources/others/background-add-and-modify.js" pageCssPath="/resources/banner/banner-add-and-modify.css" >
	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath"/>
	<input type="hidden" value="${pageBackground.pageBackgroundId}" id="pageBackgroundId"/>
	<div class="row">
		<div class="col-xs-12">
			<ul class="page-breadcrumb breadcrumb">
				<li>
					<a href="#">首页</a>
					<i class="fa fa-circle"></i>
				</li>
				<li>
					<a href="#">背景操作</a>
					<i class="fa fa-circle"></i>
				</li>
				<li class="active">
					<a href="#">新增背景图片</a>
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

					<div id="imgUpload" class="pgt-img-upload">
						<div class="row">
							<div class="col-md-8">
								<div class="pgt-each-img">
									<div class="pgt-handle-box">
										<a id="pgt-header-delete" class="pgt-img-delete" data-url="/media/delete/${pageBackground.headerMedia.id}">删除</a>
									</div>
									<img id="pgt-header-img" class="pgt-category-img" src="${pageBackground.headerMedia.path}" alt=""/>
									<p>200 * 200</p>
								</div>
							</div>
						</div>
						<div class="row">
							<label class="col-md-12 control-label">头部图片</label>
							<div class="col-md-12">
								<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
									<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
									<input name="mediaType" type="hidden" value="header"/>
									<button type="button" class="btn blue">选择图片</button>
								</form>
								<p></p>
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-md-8">
							<div class="pgt-each-img">
								<div class="pgt-handle-box">
									<a id="pgt-middle-delete" class="pgt-img-delete" data-url="/media/delete/${pageBackground.middleMedia.id}">删除</a>
								</div>
								<img id="pgt-middle-img" class="pgt-category-img" src="${pageBackground.middleMedia.path}" alt=""/>
								<p>200 * 200</p>
							</div>
						</div>
					</div>
					<div class="row">
						<label class="col-md-12 control-label">中部图片</label>
						<div class="col-md-12">
							<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
								<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
								<input name="mediaType" type="hidden" value="middle"/>
								<button type="button" class="btn blue">选择图片</button>
							</form>
							<p></p>
						</div>
					</div>

					<div class="row">
						<div class="col-md-8">
							<div class="pgt-each-img">
								<div class="pgt-handle-box">
									<a id="id="pgt-footer-delete" class="pgt-img-delete" data-url="/media/delete/${pageBackground.footerMedia.id}">删除</a>
								</div>
								<img id="pgt-footer-img" class="pgt-category-img" src="${pageBackground.footerMedia.path}" alt=""/>
								<p>200 * 200</p>
							</div>
						</div>
					</div>
					<div class="row">
						<label class="col-md-12 control-label">底部图片</label>
						<div class="col-md-12">
							<form class="pgt-file-box" action="/upload/image" enctype="multipart/form-data">
								<input class="pgt-file-btn" name="uploadPicture" data-pgt-btn="single" type="file"/>
								<input name="mediaType" type="hidden" value="footer"/>
								<button type="button" class="btn blue">选择图片</button>
							</form>
							<p></p>
						</div>
					</div>

				</div>


				<button class="btn green-haze btn-circle" onclick="javascript:window.location.href='/pageBackground/queryPageBackground';"><i class="fa fa-plus"></i>完成</button>

				</div>
				<!-- END FORM-->
			</div>
		</div>
	</div>
	<div id="testbox">
	</div>
</admin:container>

