<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/14/16
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-body" v-bind:style="{display:allProductDisplay}">
  <h2 class="pgt-part-title" >产品信息</h2>

  <div class="table-scrollable list-box">
    <table class="table table-striped table-bordered table-hover dataTable no-footer">
      <thead>
      <tr role="row">
        <th>
          典当状态
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
            <c:if test="${product.pawnageStatus==10}">在当期状态</c:if>
            <c:if test="${product.pawnageStatus==20}">绝当状态</c:if>
            <c:if test="${product.pawnageStatus==30}">赎当状态</c:if>
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
            <button class="btn btn-xs red btn-circle" onclick="window.location.href='/tender/deleteTenderProduct/${product.productId}'">删除</button>
            <button class="btn btn-xs blue btn-circle" onclick="window.location.href='/tender/queryTenderByProductId/${tender.tenderId}/${product.productId}'">查看</button>
          </td>
        </tr>
      </c:forEach>


      </tbody>
    </table>
  </div>
</div>