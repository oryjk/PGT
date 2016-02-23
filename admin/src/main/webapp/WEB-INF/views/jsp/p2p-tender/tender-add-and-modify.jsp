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
                            <a href="#">新增投资</a>
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
                        <div id="app" class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <div id="error" v-model="error">
                                <form  id = "form" action="/tender/${tender.tenderId eq null ? 'create' : 'update'} " class="form-horizontal" method="post" v-on:submit.prevent="ajaxSubmit">
                                <div class="form-body">
                                    <!-- 只有在修改时才显示id行-->

                                    <c:if test="${!empty tender.tenderId}">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">id</label>
                                            <input type="hidden" name="tenderId" value="${tender.tenderId}"/>

                                            <div class="col-xs-4">
                                                <p class="form-control-static">
                                                        ${tender.tenderId}
                                                </p>
                                            </div>
                                        </div>
                                    </c:if>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">当铺ID:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   name="pawnShopId" value="${tender.pawnShopId}" class="form-control" placeholder="不超过30字"
                                                   v-model="tender.pawnShopId"
                                                   v-on:keyup="volidate">
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.pawnShopId != true">{{error.pawnShopId}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">当铺所有者的ID:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   name="pawnShopOwnerId" value="${tender.pawnShopOwnerId}" class="form-control" placeholder="不超过30字"
                                                   v-model="tender.pawnShopOwnerId"
                                                   v-on:keyup="volidate">
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.pawnShopOwnerId != true">{{error.pawnShopOwnerId}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3">所属分类</label>
                                        <div class="col-md-9">
                                            <div class="radio-list">
                                                <select class="form-control input-medium" name="categoryId">
                                                    <c:forEach items="${categories}" var="category">
                                                        <option value="${category.id}">${category.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">当铺编号:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   class="form-control" name="pawnTicketId" value="${tender.pawnTicketId}" placeholder="当铺编号:"
                                                   v-model="tender.pawnTicketId"
                                                   v-on:keyup="volidate">
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.pawnTicketId != true">{{error.pawnTicketId}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">投资名称:</label>
                                        <div class="col-xs-4">
                                            <input ms-duplex="name" name="name" value="${tender.name}"  class="form-control"
                                                   placeholder="不超过20字"
                                                   v-model="tender.name"
                                                   v-on:keyup="volidate"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.name != true">{{error.name}}</span>
                                            </p>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">开标时间:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   class="form-control" name="publishDate" value="<fmt:formatDate value="${tender.publishDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>" placeholder="开始时间" onfocus="$(this).calendar()"
                                                   v-model="tender.publishDate"
                                                   v-on:click="setDate">
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.publishDate != true">{{error.publishDate}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">截止时间:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   class="form-control" name="dueDate" value="<fmt:formatDate value="${tender.dueDate}" type="both" pattern="yyyy-MM-dd hh:mm"/>" placeholder="截止时间" onfocus="$(this).calendar()"
                                                   v-model="tender.dueDate"
                                                   v-on:click="setDate">
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.dueDate != true">{{error.dueDate}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">违约赔付利率:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   class="form-control" name="interestRate" value="${tender.interestRate}" placeholder="违约赔付利率"
                                                   v-model="tender.interestRate"
                                                   v-on:click="volidate"><div>%</div>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.interestRate != true">{{error.interestRate}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">投资多少天开始算收益:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   class="form-control" name="prePeriod" value="${tender.prePeriod}" placeholder="投资多少天开始算收益"
                                                   v-model="tender.prePeriod"
                                                   v-on:click="volidate">
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.prePeriod != true">{{error.prePeriod}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">无息天数:</label>
                                        <div class="col-xs-4">
                                            <input ms-duplex="postPeriod" name="postPeriod" value="${tender.postPeriod}"
                                                   class="form-control" placeholder="不超过10字"
                                                   v-model="tender.postPeriod"
                                                   v-on:click="volidate"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.postPeriod != true">{{error.postPeriod}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">手续费费率:</label>
                                        <div class="col-md-4">
                                            <input type="text"
                                                   class="form-control" name="handlingFeeRate" value="${tender.handlingFeeRate}" placeholder="手续费费率"
                                                   v-model="tender.handlingFeeRate"
                                                   v-on:click="volidate"><div>%</div>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.handlingFeeRate != true">{{error.handlingFeeRate}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">投资的详情:</label>
                                        <div class="col-xs-4">
                                            <input ms-duplex="description" name="description" value="${tender.description}"
                                                   class="form-control" placeholder="不超过20字"
                                                   v-model="tender.description"
                                                   v-on:click="volidate"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <p class="form-control-static pgt-error">
                                                <span v-show="error.description != true">{{error.description}}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-xs-3">是否分类热门</label>

                                        <div class="col-xs-9">
                                            <div class="radio-list">
                                                <select ms-duplex="categoryHot" name="categoryHot"
                                                        class="form-control input-medium">
                                                    <option value="1">是</option>
                                                    <option value="0">否</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-xs-3">是否网站热门</label>

                                        <div class="col-xs-9">
                                            <div class="radio-list">
                                                <select ms-duplex="status" name="siteHot" class="form-control input-medium">
                                                    <option value="1">是</option>
                                                    <option value="0">否</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-xs-3">状态</label>

                                        <div class="col-xs-9">
                                            <div class="radio-list">
                                                <select ms-duplex="status" name="status" class="form-control input-medium">
                                                    <option value="1">启用</option>
                                                    <option value="0">禁用</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-actions top">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button type="submit" class="btn blue-hoki">下一步</button>
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
<script src="/resources/assets/others/Jquery-date-and-time/jquery-calendar.js"></script>
<script src="/resources/tender/tender.js"></script>
