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
        <h2 class="main-head">我的收藏</h2>
        <ul class="filter-favorite-status">
            <c:choose>
                <c:when test="${param.type eq 6}">
                    <li class="filter-favorite-choose"><a href="javascript:void(0);">收藏项目</a></li>
                    <li class="filter-favorite-end"><a href="/myAccount/favourites?type=5">收藏产品</a></li>
                </c:when>
                <c:otherwise>
                    <li class="filter-favorite-end"><a href="/myAccount/favourites?type=6">收藏项目</a></li>
                    <li class="filter-favorite-choose"><a href="javascript:void(0);">收藏产品</a></li>
                </c:otherwise>
            </c:choose>
        </ul>

        <div class="area-product">
            <%--
                <ul class="filter-order-status">
                    <li><a href="javascript:void(0);">全部</a></li>
                    <li><a href="javascript:void(0);">有货</a></li>
                    <li class="filter-order-end"><a href="javascript:void(0);">无货</a></li>
                </ul>
            --%>
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
                    <c:forEach var="fav" items="${favourites.result}">
                        <c:choose>
                            <c:when test="${param.type eq 6}">
                                <tr class="favorite-list-item">
                                    <td class="favorite-img">
                                        <a href="#"><img src="../../core/images/product/hero_2_01.jpg"
                                                         alt="${fav.tender.name}"/></a>
                                    </td>
                                    <td class="favorite-base-info">
                                        <p><a href="#">${fav.tender.name}</a></p>
                                    </td>
                                    <td>${fav.tender.productResidue}</td>
                                    <td><span>¥</span><span><fmt:formatNumber value="${fav.tender.tenderTotal}"
                                                                              pattern="0.00" type="number"/></span></td>
                                    <td class="favorite-handle">
                                        <p><a class="link-btn" href="#">立即抢购</a></p>
                                        <p><a class="link-btn link-dislike" href="javascript:void(0)"
                                              data-id="${fav.id}">取消收藏</a></p>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr class="favorite-list-item">
                                    <td class="favorite-img">
                                        <a href="#"><img src="../../core/images/product/hero_2_01.jpg" alt="商品名"/></a>
                                    </td>
                                    <td class="favorite-base-info">
                                        <p><a href="#">${fav.name}</a></p>
                                    </td>
                                    <td>${fav.productStock}</td>
                                    <td><span>¥</span><span><fmt:formatNumber value="${fav.finalPrice}" pattern="0.00"
                                                                              type="number"/></span></td>
                                    <td class="favorite-handle">
                                        <p><a class="link-btn" href="#">立即抢购</a></p>
                                        <p><a class="link-btn link-dislike" href="javascript:void(0)"
                                              data-id="${fav.id}">取消收藏</a></p>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <!-- tbody end-->
                </table>
            </div>
            <div class="page-box">
                <ol>
                    <li class="page-list">
                        <c:if test="${favourites.maxIndex gt 0}">
                            <ol id="pages">
                                <c:forEach var="pageNum" items="${favourites.pageNumbers}">
                                    <c:choose>
                                        <c:when test="${pageNum gt 0}">
                                            <li>
                                                <a href="/myAccount/favourites?type=${param.type}&currentIndex=${pageNum}">${pageNum + 1}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="/myAccount/favourites?type=${param.type}">${pageNum + 1}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ol>
                        </c:if>
                    </li>
                </ol>
            </div>
        </div>

    </div>
</div>

<!--footer begin-->
<jsp:include page="../core/footer-main.jsp"/>
<!--footer end-->

<script src="/resources/core/js/require.js" data-main="/resources/my-account/my-favorite/my-favorite"></script>
</body>
</html>