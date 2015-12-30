<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<script type="text/javascript" charset="utf-8" src="${adminStaticPath }/core/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${adminStaticPath }/core/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${adminStaticPath }/core/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<pgt:container id="content">
	<div class="page-content">
        <div class="container-fluid pgt-container">
            <div class="row">
                <div class="col-xs-12">
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <a href="#">首页</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <a href="#">文章列表</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">新增分类</a>
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
                                <i class="fa fa-gift"></i>文章
                            </div>
                        </div>
                       
                        <!-- BEGIN FORM-->
                       	<div class="portlet-body form">
                       		 <form action="${actionUrl }" method="post">
                       		 	<input type="hidden" name="id" value="${helpCenter.id}"/>
                       		 	<input type="text" name="title" value="${helpCenter.title}"/>
                       		 	<input type="hidden" name="type" value="${helpCenter.type}"/>
	                            <div class="form-horizontal">
	                                <div class="form-body">
	                                    <div class="row">
	                                        <div class="col-xs-10 col-xs-push-1">
	                                            <script id="editor" type="text/plain" name="content" style="width:100%;height:500px;">
													${helpCenter.content}
												</script>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div class="form-actions top">
	                                    <div class="row">
	                                        <div class="col-md-offset-7 col-xs-2">
	                                            <div class="dataTables_length">
	                                                <label>目录:
	                                                    <select name="category.id" aria-controls="sample_3"
	                                                            class="form-control input-small input-inline select2-offscreen"
	                                                            tabindex="-1" title="">
	                                                        <c:forEach items="${helpCategorVoList}" var="helpCategorVo">
	                                                        	<option value="${helpCategorVo.category.id }" ${helpCenter.category.id eq helpCategorVo.category.id ? 'selected' : ''}>${helpCategorVo.category.name }</option>
	                                                        </c:forEach>
	                                                    </select></label>
	                                            </div>
	                                        </div>
	                                        <div class="col-md-2">
	                                            <button type="submit" class="btn blue-hoki">确认</button>
	                                            <button type="button" class="btn default">取消</button>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
                        	</form>
	                    </div>
                        <!-- END FORM-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</pgt:container>

<script src="${adminStaticPath }/help/artcle-add-and-modify.js"></script>
<!-- END JAVASCRIPTS -->
</body>