<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../core/head.jspf">
    <jsp:param name="css_path" value="/resources/my-account/my_account_base.css"/>
    <jsp:param name="custom_css_path" value="/resources/my-account/my-favorite/my-favorite.css"/>
</jsp:include>
<body>
<!--header begin-->
<jsp:include page="../core/header-main.jsp"/>
<!--header end-->

<div class="content">
    <jsp:include page="vertical-my-account-directory.jsp"/>
    <div class="main-area">
        <h2 class="main-head">最近浏览</h2>


        <div class="area-product">
            <div class="favorite-list">
                <table>
                    <!-- thead begin-->
                    <tr class="favorite-list-head">
                        <th class="col-1">项目信息</th>
                        <th class="col-2"></th>
                        <th class="col-3">库存</th>
                        <th class="col-4">产品金额(元)</th>
                        <th class="col-5">操作</th>
                    </tr>
                    <!-- thead end-->

                    <!-- tbody begin-->
                    <c:forEach var="tender" items="${browsedProducts}">
                        <tr class="favorite-list-item">
                            <td class="favorite-img">
                                <a href="#"><img src="../../core/images/product/hero_2_01.jpg"
                                                 alt="${tender.name}"/></a>
                            </td>
                            <td class="favorite-base-info">
                                <p><a href="#">${tender.name}</a></p>
                            </td>
                            <td>${tender.productResidue}</td>
                            <td><span>¥</span><span><fmt:formatNumber value="${tender.tenderTotal}"
                                                                      pattern="0.00" type="number"/></span></td>
                            <td class="favorite-handle">
                                <p><a class="link-btn" href="#">立即抢购</a></p>
                            </td>
                        </tr>
                    </c:forEach>
                    <!-- tbody end-->
                </table>
            </div>
        </div>

    </div>
</div>

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->
</body>
</html>