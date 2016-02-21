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
                                                ${tender.tenderId}
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
                                                <c:if test="${tender.siteHot==false}">否</c:if>
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
                                        <label class="col-md-3 control-label">总数量</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${product.stock}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">总金额</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">

                                                 ${product.stock * product.salePrice}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">状态</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">

                                                <c:if test="${product.status==1}">
                                                               启用
                                                </c:if>

                                                <c:if test="${product.status==0}">
                                                              禁用
                                                </c:if>

                                            </p>
                                        </div>
                                    </div>

                                </div>

                                <div class="img-body">
                                    <h2 class="pgt-part-title">图片列表</h2>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">主图</label>
                                        <div class="col-md-8">

                                            <c:forEach items="${product.mainMedias}" var="media">
                                                <div class="pgt-each-img">
                                                    <img class="pgt-advertisement-img" src="${media.path}" alt=""/>
                                                    <p>60 * 60</p>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>



                                    <div class="form-actions top">
                                        <div class="row">
                                            <div class="col-md-offset-3 col-md-9">
                                                <!--新增产品必须拥有权限时才显示-->
                                                <button type="submit" class="btn blue-hoki" onclick="window.location.href='/tender/createTenderProduct/${tender.tenderId}'">新增产品</button>
                                                <button type="button" class="btn default" onclick="window.location.href='/tender/queryTenderById/${tender.tenderId}'">返回</button>

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
