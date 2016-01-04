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
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<admin:container id="productList">
  <div class="row">
    <div class="col-xs-12">
      <ul class="page-breadcrumb breadcrumb">
        <li>
          <a href="#">首页</a>
          <i class="fa fa-circle"></i>
        </li>
        <li>
          <a href="#">分类列表</a>
          <i class="fa fa-circle"></i>
        </li>
        <li class="active">
          <a href="#">新增分类</a>
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
            <i class="fa fa-gift"></i>基本信息
          </div>
        </div>
        <div class="portlet-body form">
          <!-- BEGIN FORM-->
          <form:form action="create" class="form-horizontal">
            <div class="form-body">
              <!-- 只有在修改时才出现id行-->
              <c:if test="${category.id!=null}">
              <div class="form-group">
                <label class="col-md-3 control-label">id</label>
                <div class="col-md-4">
                  <p class="form-control-static">
                    ${category.id}
                  </p>
                </div>
              </div>
              </c:if>
              <div class="form-group">
                <label class="col-md-3 control-label">分类名称:</label>
                <div class="col-md-4">
                  <form:input type="text" class="form-control" placeholder="6个字以下" path="name"/>
                  </p>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-md-3">分类等级</label>
                <div class="col-md-9">
                  <div class="radio-list">
                    <form:select class="form-control input-medium" id="levelSelect" path="type">
                      <form:option value="ROOT">主分类</form:option>
                      <form:option value="HIERARCHY">子分类</form:option>
                    </form:select>
                  </div>
                </div>
              </div>

              <div id="categoryMain">
                <div class="form-group">
                  <label class="col-md-3 control-label">副标题</label>
                  <div class="col-md-4">
                    <form:input type="text" class="form-control" placeholder="不超过10字" path="description"/>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">分类色调</label>
                  <div class="col-md-4">
                    <form:input type="text" class="form-control" placeholder="#xxxxxx形式" path="color"/>
                  </div>
                </div>
              </div>

              <div id="categorySon" style="display: none">
                <div class="form-group">
                  <label class="control-label col-md-3">所属大类</label>
                  <div class="col-md-9">
                    <div class="radio-list">
                      <form:select class="form-control input-medium" path="parent.id" items="${categories}" itemLabel="name" itemValue="id">
                        <option value="">请选择</option>
                      </form:select>
                    </div>
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label class="control-label col-md-3">状态</label>
                <div class="col-md-9">
                  <div class="radio-list">
                    <form:select class="form-control input-medium" path="status">
                      <form:option value="1">启用</form:option>
                      <form:option value="0">禁用</form:option>
                    </form:select>
                  </div>
                </div>
              </div>
            </div>
            <!-- super:修改产品时才出现下面一个div.form-group-->

            <div class="form-actions top">
              <div class="row">
                <div class="col-md-offset-3 col-md-9">
                  <button type="submit" class="btn blue-hoki">确认</button>
                  <button type="button" class="btn default">取消</button>
                </div>
              </div>
            </div>
          </form:form>
        </div>
        <!-- END FORM-->
      </div>
    </div>
  </div>
  <div id="imgUpload" class="pgt-img-upload">
    <div class="row">
      <div class="col-md-8">
        <div class="pgt-each-img">
          <div class="pgt-handle-box">
            <a class="pgt-img-delete" href="#">删除</a>
          </div>
          <img class="pgt-category-img" src="" alt=""/>
          <p>200 * 200</p>
        </div>
      </div>
    </div>
    <div class="row">
      <label class="col-md-12 control-label">分类主图</label>
      <div class="col-md-12">
        <form class="pgt-file-box" action="/test">
          <input class="pgt-file-btn" name="thumbnail" type="file"/>
          <button type="button" class="btn blue">选择图片</button>
        </form>
        <p></p>
      </div>
    </div>
  </div>
</admin:container>

