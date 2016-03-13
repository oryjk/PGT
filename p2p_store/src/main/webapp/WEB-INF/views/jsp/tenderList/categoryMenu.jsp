<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/5/16
  Time: 12:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="menu">

	<li class="menu-level-1">
		<a href="/tender/tenderList" class="list">
			<img src="../core/images/icon/list1.png">
			<h4>全部</h4>
		</a>
	</li>
	<template v-if="!ajax">
		<c:forEach var="item" items="${rootCategoryList}">
			<li class="menu-level-1">
				<a href="#" class="list" @click.prevent="chooseCategoryAction('TENDER_ROOT',$event)" data-value="${item.id}">
					<img src="../core/images/icon/list2.png">
					<h4>${item.name}</h4>
				</a>
				<c:if test="${item.children!=null}">
					<ul class="menu-2">
						<c:forEach var="sub" items="${item.children}">
							<li class="menu-level-2" data-value="${sub.id}" @click.prevent="chooseCategoryAction('TENDER_HIERARCHY',$event)"><a href="#">${sub.name}</a></li>
						</c:forEach>
					</ul>
				</c:if>
			</li>
		</c:forEach>
	</template>
	<template v-if="ajax">
		<template v-for="item in categoryMenu">
			<li class="menu-level-1">
				<a href="#" class="list" @click.prevent="chooseCategoryAction('TENDER_ROOT',$event)">
					<img src="../core/images/icon/list2.png">
					<h4>{{item.name}}</h4>
				</a>
				<template v-if="item.children!=null">
					<ul class="menu-2">
						<li class="menu-level-2" data-value="{{sub.id}}" v-for="sub in item.children" @click.prevent="chooseCategoryAction('TENDER_HIERARCHY',$event)"><a href="#">{{sub.name}}</a>
						</li>
					</ul>
				</template>
			</li>
		</template>
	</template>
	<div class="list-end"></div>

</ul>
