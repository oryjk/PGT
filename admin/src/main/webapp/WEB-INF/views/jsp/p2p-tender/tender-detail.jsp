<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<admin:container id="tenderList" pageJsPath="/resources/p2p-tender/tender-add-and-modify.js">

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
                                <i class="fa fa-gift"></i>基本信息
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <form action="javascript:;" class="form-horizontal">
                                <div class="tender-info-body">
                                    <h2 class="pgt-part-title" >投资信息</h2>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">投资id</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${tender. tenderId}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">投资名字</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${tender.name}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">卖点描述</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${tender.description}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">是否热门</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">

                                                <c:if test="${tender.siteHot==true}">是</c:if>
                                                <c:if test="${tender.siteHot==true}">否</c:if>

                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">所属商家id</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${tender.pawnShopId}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">创建时间</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                    <fmt:formatDate value="${tender.creationDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">开始时间</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                        <fmt:formatDate value="${tender.publishDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">结束时间</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                        <fmt:formatDate value="${tender.dueDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>
                                            </p>
                                        </div>
                                    </div>



                                    <div class="form-group">
                                        <label class="col-md-3 control-label">总金额</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${tender.tenderTotal}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">状态</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">

                                                <c:if test="${tender. state==1}">

                                                </c:if>

                                                <c:if test="${tender. state==0}">

                                                </c:if>

                                                <c:if test="${tender. state==-1}">

                                                </c:if>


                                            </p>
                                        </div>
                                    </div>

                                </div>

                                <div class="img-body">
                                    <h2 class="pgt-part-title">图片列表</h2>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">首页图</label>
                                        <div class="col-md-8">
                                            <div class="pgt-each-img">
                                                <img class="pgt-advertisement-img" src="${tender.p2pAdvertisement.path}" alt=""/>
                                                <p>60 * 60</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">列表图</label>
                                        <div class="col-md-8">
                                            <div class="pgt-each-img">
                                                <img class="pgt-advertisement-img" src="${tender.p2pFrontMedia.path}" alt=""/>
                                                <p>60 * 60</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">主图</label>
                                        <div class="col-md-8">
                                            <c:forEach items="${p2pHeroMedias}" var="media">
                                            <div class="pgt-each-img">
                                                <img class="pgt-advertisement-img" src="${media.path}" alt=""/>
                                                <p>60 * 60</p>
                                            </div>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">详情图</label>
                                        <div class="col-md-8">

                                            <c:forEach items="${tender.p2pMainMedia}" var="media">
                                            <div class="pgt-each-img">
                                                <img class="pgt-advertisement-img" src="${media.path}" alt=""/>
                                                <p>60 * 60</p>
                                            </div>
                                            </c:forEach>

                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">专家点评</label>
                                        <div class="col-md-8">

                                            <c:forEach items="${tender.p2pExpertMedia}" var="media">
                                            <div class="pgt-each-img">
                                                <img class="pgt-advertisement-img" src="${media.path}" alt=""/>
                                                <p>60 * 60</p>
                                            </div>
                                            </c:forEach>

                                    </div>

                                </div>

                                <div class="form-body">
                                    <h2 class="pgt-part-title" >产品信息</h2>

                                    <div class="table-scrollable list-box">
                                        <table class="table table-striped table-bordered table-hover dataTable no-footer">
                                            <thead>
                                            <tr role="row">
                                                <th>
                                                    顺序
                                                </th>
                                                <th>
                                                    序号
                                                </th>
                                                <th>
                                                    名字
                                                </th>
                                                <th>
                                                    金额
                                                </th>
                                                <th>
                                                    数量
                                                </th>
                                                <th>
                                                    图片
                                                </th>
                                                <th>
                                                    操作
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody id="list">


                                            <c:forEach items="${tender.products}" var="product" varStatus="status">
                                            <tr class="gradeX odd" role="row">
                                                <td>
                                                    <input style="width: 30px;" type="text"/>
                                                </td>
                                                <td>
                                                    ${product.productId}
                                                </td>
                                                <td>
                                                     ${product.name}
                                                </td>
                                                <td>
                                                    ${product.salePrice * product.stock}
                                                </td>
                                                <td>
                                                    ${product.stock}
                                                </td>
                                                <td class="face-box">
                                                    <img style="width: 40px;height: 40px;" src="${product.advertisementMedia.path}" alt=""/>
                                                </td>
                                                <td>
                                                    <button class="btn btn-xs green btn-circle" onclick="window.location.href='/tender/updateTenderProduct/${tender.tenderId}/${product.productId}'">修改</button>
                                                    <button class="btn btn-xs red btn-circle" onclick="window.location.href='/product/delete/${product.productId}'">删除</button>
                                                    <button class="btn btn-xs blue btn-circle" onclick="window.location.href='/tender/queryTenderByProductId/${tender.tenderId}/${product.productId}'">查看</button>
                                                </td>
                                            </tr>
                                            </c:forEach>


                                            </tbody>
                                        </table>
                                    </div>
                                </div>


                                <div class="form-actions top">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <!--新增产品必须拥有权限时才显示-->
                                            <button type="submit" class="btn blue-hoki" onclick="window.location.href='/tender/createTenderProduct/${tender.tenderId}'">新增产品</button>
                                            <button type="button" class="btn default" onclick="window.location.href='/tender/tenderList'">返回</button>
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
</div>
</admin:container>


