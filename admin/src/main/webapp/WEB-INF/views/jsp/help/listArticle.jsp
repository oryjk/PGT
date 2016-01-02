<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url var="adminStaticPath" value="${adminStaticPath}"/>
<pgt:container id="main">
	<div class="page-content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <a href="#">首页</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li class="active">
                            <a href="#">文章列表</a>
                            <i class="fa fa-circle"></i>
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
                                <a href="add" class="btn green-haze btn-circle"><i class="fa fa-plus"></i> 新增</a>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                                <div class="row">
                                    <div class="col-xs-2">
                                        <div class="dataTables_length">
                                            <label>目录
                                                <select name="sample_3_length" aria-controls="sample_3"
                                                        class="form-control input-small input-inline select2-offscreen"
                                                        tabindex="-1" title="">
                                                    <option value="">全部</option>
                                                    <c:forEach items="${categories }" var="category">
                                                    	 <option value="${category.id}">${category.name }</option>
                                                    </c:forEach>
                                                </select></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-scrollable productlist-box">
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                                           id="list1" role="grid" aria-describedby="sample_3_info">
                                        <thead>
                                        <tr role="row">
                                            <th>
                                                位置
                                            </th>
                                            <th>
                                                所属目录
                                            </th>
                                            <th>
                                                文章id
                                            </th>
                                            <th>
                                                文章标题
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
                                        <c:forEach items="${helpCenters }" var="helpCenter">
                                        	<tr class="gradeX odd" role="row">
	                                            <td>
	                                                <input class="pgt-sort-num" type="text" value="1"/>
	                                            </td>
	                                            <td>${helpCenter.category.name}</td>
	                                            <td>${helpCenter.id }</td>
	                                            <td>${helpCenter.title }</td>
	                                            <td>
	                                                <div class="btn-group">
	                                                    <a class="btn btn-xs default btn-circle" href="javascript:;" data-toggle="dropdown" data-pgt-value="">
	                                                        禁用 <i class="fa fa-angle-down"></i>
	                                                    </a>
	                                                    <ul class="dropdown-menu pull-right">
	                                                        <li>
	                                                            <a href="javascript:;" data-pgt-value="0" data-pgt-dropdown="status-option">
	                                                                禁用 </a>
	                                                        </li>
	                                                        <li>
	                                                            <a href="javascript:;" data-pgt-value="1"  data-pgt-dropdown="status-option">
	                                                                启用 </a>
	                                                        </li>
	                                                    </ul>
	                                                </div>
	                                            </td>
	                                            <td>
	                                                <a href="update/${helpCenter.id}" class="btn btn-xs green btn-circle">修改</a>
                                                	<a href="delete/${helpCenter.id }" class="btn btn-xs red btn-circle">删除</a>
	                                            </td>
	                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="row">
                                    <link rel="stylesheet" href="${adminStaticPath}/core/css/page.css"/>
                                    <div class="col-xs-2">
                                        <div class="dataTables_info pgt-page-count" id="sample_3_info" role="status" aria-live="polite">
                                            第
                                            <span>${paginationBean.currentIndex + 1}</span>
                                            条 到 第
                                            <span>${paginationBean.currentIndex + 1 + fn:length(helpCenters)}</span>
                                            条 共
                                            <span>20</span>
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
                                                    <option value="15" selected>15</option>
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
        </div>
    </div>
</pgt:container>