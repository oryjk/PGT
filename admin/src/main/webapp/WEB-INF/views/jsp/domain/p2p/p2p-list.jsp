<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pgt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<pgt:container id="content" dashboard="true">
    <div class="page-container" id="main">
        <div class="page-content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12">
                        <ul class="page-breadcrumb breadcrumb">
                            <li>
                                <a href="#">首页</a>
                                <i class="fa fa-circle"></i>
                            </li>
                            <li>
                                <a href="table_managed.html">主域名内容</a>
                                <i class="fa fa-circle"></i>
                            </li>
                            <li class="active">
                                <a href="#">在当淘推荐</a>
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
                                    <span class="caption-subject font-green-sharp bold uppercase">在当淘推荐</span>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div id="sample_3_wrapper" class="dataTables_wrapper no-footer">
                                    <div class="row">
                                        <div class="col-md-2 col-sm-2">
                                            <div class="dataTables_filter">
                                                <label>id:<input type="search" class="form-control input-small input-inline"
                                                                 placeholder="订单id" aria-controls="sample_3">
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-xs-3">
                                            <div class="dataTables_filter">
                                                <label>金额:
                                                    <input type="search" class="form-control input-xsmall input-inline"
                                                           placeholder="最低" aria-controls="sample_3">
                                                    -
                                                    <input type="search" class="form-control input-xsmall input-inline"
                                                           placeholder="最高" aria-controls="sample_3">
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-10">
                                            <div class="dataTables_filter">
                                                <div class="row">
                                                    <div class="col-xs-1 pgt-time-tittle">
                                                        <span>时间: </span>
                                                    </div>
                                                    <div class="col-xs-2 pgt-begin-date" style="position: relative">
                                                        <input class="jcDate jcDateIco form-control input-small input-inline" >
                                                    </div>
                                                    <div class="col-xs-2 pgt-time" style="position: relative">
                                                        <input type="search" class="form-control input-mini input-inline"
                                                               placeholder="时" aria-controls="sample_3">
                                                        :
                                                        <input type="search" class="form-control input-mini input-inline"
                                                               placeholder="分" aria-controls="sample_3">
                                                    </div>

                                                    <div class="col-xs-1 pgt-date-divide">
                                                        <span>至</span>
                                                    </div>
                                                    <div class="col-xs-2">
                                                        <input class="jcDate jcDateIco form-control input-small input-inline">
                                                    </div>
                                                    <div class="col-xs-2 pgt-time">
                                                        <input type="search" class="form-control input-mini input-inline"
                                                               placeholder="时" aria-controls="sample_3">
                                                        :
                                                        <input type="search" class="form-control input-mini input-inline"
                                                               placeholder="分" aria-controls="sample_3">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-1">
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
                                                    在当品名称
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                    aria-label=" Email : activate to sort column ascending">
                                                    在当品价格
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                    aria-label=" Email : activate to sort column ascending">
                                                    在当品链接
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                    aria-label=" Email : activate to sort column ascending">
                                                    图片
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="sample_3" rowspan="1" colspan="1"
                                                    aria-label="Status : activate to sort column ascending">
                                                    描述
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="gradeX odd" role="row">
                                                <td>
                                                    <input type="checkbox">
                                                </td>
                                                <td class="sorting_1">
                                                    0000000000001
                                                </td>
                                                <td>
                                                    2015年12月12日
                                                </td>
                                                <td>
                                                    <a href="#" class="btn btn-xs btn-circle">查看</a>
                                                </td>
                                                <td class="productlist-face-box">
                                                    <img src="../core/images/logo.jpg" alt=""/>
                                                </td>
                                                <td>
                                                    123
                                                </td>
                                            </tr>
                                            <tr class="gradeX odd" role="row">
                                                <td>
                                                    <input type="checkbox">
                                                </td>
                                                <td class="sorting_1">
                                                    0000000000001
                                                </td>
                                                <td>
                                                    2015年12月12日
                                                </td>
                                                <td>
                                                    <a href="#" class="btn btn-xs btn-circle">查看</a>
                                                </td>
                                                <td class="productlist-face-box">
                                                    <img src="" alt=""/>
                                                </td>
                                                <td>
                                                    123
                                                </td>
                                            </tr>
                                            <tr class="gradeX odd" role="row">
                                                <td>
                                                    <input type="checkbox">
                                                </td>
                                                <td class="sorting_1">
                                                    0000000000001
                                                </td>
                                                <td>
                                                    2015年12月12日
                                                </td>
                                                <td>
                                                    <a href="#" class="btn btn-xs btn-circle">查看</a>
                                                </td>
                                                <td>
                                                    3210.00
                                                </td>
                                                <td>
                                                    123
                                                </td>
                                            </tr>

                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="row">
                                        <link rel="stylesheet" href="../core/css/page.css"/>
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
            </div>

        </div>

    </div>
</pgt:container>