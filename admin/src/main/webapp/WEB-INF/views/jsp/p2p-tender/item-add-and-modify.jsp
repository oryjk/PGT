<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                            <form action="/tender/${product.productId eq null ? 'createTenderProduct' : 'updateTenderProduct'}" class="form-horizontal" method="post">
                                <div class="tender-info-body">
                                    <h2 class="pgt-part-title" >投资信息</h2>


                                    <input type="hidden" name="tenderId" value="${tender.tenderId}">

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
                                        <label class="col-md-3 control-label">总数量</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                ${tender.totalQuantity}
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

                                </div>


                                <div class="form-body">
                                    <h2 class="pgt-part-title" >产品信息</h2>


                                   <c:if test="${!empty product.productId}">
                                    <!-- 只有在修改时才显示id行-->
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">id</label>
                                        <div class="col-md-4">
                                            <p class="form-control-static">
                                                012
                                            </p>
                                        </div>
                                    </div>
                                   </c:if>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">产品名称:</label>

                                        <div class="col-xs-4">
                                            <input name="name" value="${product.name}" placeholder="不超过30字" class="form-control  pgt-requisite-name"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                            </p>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="control-label col-xs-3">是否热门</label>

                                        <div class="col-xs-9">
                                            <div class="radio-list">
                                                <select name="isHot" class="form-control input-medium">
                                                    <option value="false" ${product.isHot eq true ? "selected='selected'" :''}>不热门</option>
                                                    <option value="true" ${product.isHot eq false ? "selected='selected'" :''}>热门</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">所属商家id</label>

                                        <div class="col-xs-4">
                                            <input name="merchant" type="text" value="${product.merchant}" class="form-control" placeholder="商家id"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">标题</label>

                                        <div class="col-xs-4">
                                            <input name="title" value="${product.title}" type="text" class="form-control" placeholder="不超过20字"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">产品编码</label>

                                        <div class="col-xs-4">
                                            <input name="serialNumber" value="${product.serialNumber}" type="text"  class="form-control" placeholder=""/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">关键字</label>

                                        <div class="col-xs-4">
                                            <input name="keyWord" value="${product.keyWord}" type="text"  class="form-control" placeholder="不超过20字"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">款式</label>

                                        <div class="col-xs-4">
                                            <input name="shortDescription" value="${product.shortDescription}" type="text"  class="form-control" placeholder="不超过20字"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">成色</label>

                                        <div class="col-xs-4">
                                            <input name="isNew" value="${product.isNew}" class="form-control" placeholder="不超过10字"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">库存</label>

                                        <div class="col-xs-4">
                                            <input name="stock"  type="text"  class="form-control" placeholder="不超过20字" value="${product.stock}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">市场价</label>

                                        <div class="col-xs-4">

                                            <input name="listPrice" type="text"  class="form-control pgt-requisite-price1" placeholder="格式为xxxx.xx" value="<fmt:formatNumber value="${product.listPrice}" type="currency" pattern="#0.00"/>"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">绝当价</label>

                                        <div class="col-xs-4">
                                            <input name="salePrice" type="text"  class="form-control pgt-requisite-price2" placeholder="格式为xxxx.xx" value="<fmt:formatNumber value="${product.salePrice}" type="currency" pattern="#0.00"/>"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-xs-3">状态</label>

                                        <div class="col-xs-9">
                                            <div class="radio-list">
                                                <select name="status" class="form-control input-medium">
                                                    <option value="1" ${product.status eq 1 ? "selected='selected'" :''}>启用</option>
                                                    <option value="0" ${product.status eq 0 ? "selected='selected'" :''}>禁用</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>




                                <!-- super:修改产品时才出现下面一个div.form-group-->

                                <div class="img-body">
                                    <h2 class="pgt-part-title">图片列表</h2>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label">列表图</label>
                                        <div class="col-md-2">
                                            <form class="pgt-file-box" action="" enctype="multipart/form-data">
                                                <input class="pgt-file-btn" name="front" type="file"/>
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
                                                <input class="pgt-file-btn" name="main" type="file"/>
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

                                </div>

                                <div class="form-actions top">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button type="submit" class="btn blue-hoki">保存并新增产品</button>
                                            <button type="submit" class="btn blue-hoki">保存</button>
                                            <button type="button" class="btn default">取消</button>
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
