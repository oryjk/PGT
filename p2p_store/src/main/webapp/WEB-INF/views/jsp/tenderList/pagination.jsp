<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 3/2/16
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="totalPage" value="${paginationBean.totalPage}"/>
<c:set var="currentPage" value="${paginationBean.currentPage}"/>
<template v-if="!ajax">

	<ol v-on:click="paginationAction">
		<c:if test="${currentPage!=1}">
			<li><a href="javascript:void(0);" class="pre">上一页</a></li>
		</c:if>
		<c:choose>
			<c:when test="${paginationBean.currentPage<6}">
				<c:choose>
					<c:when test="${totalPage>7}">
						<c:forEach begin="1" end="7" var="current">
							<li><a href="javascript:void(0);" class="<c:if test="${current==currentPage}">current</c:if>" data-value="${current}">${current}</a></li>
						</c:forEach>
						<li>...</li>
					</c:when>
					<c:otherwise>
						<c:forEach begin="1" end="${totalPage}" var="current">
							<li><a href="javascript:void(0);" class="<c:if test="${current==currentPage}">current</c:if>" data-value="${current}">${current}</a></li>
						</c:forEach>
						<li>...</li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${totalPage-currentPage<3}">
				<li><a href="javascript:void(0);" data-value="1">1</a></li>
				<li><a href="javascript:void(0);" data-value="2">2</a></li>
				<li>...</li>
				<li><a href="javascript:void(0);" data-value="${totalPage-4}">${totalPage-4}</a></li>
				<li><a href="javascript:void(0);" data-value="${totalPage-3}">${totalPage-3}</a></li>
				<li><a href="javascript:void(0);" data-value="${totalPage-2}">${totalPage-2}</a></li>
				<li><a href="javascript:void(0);" data-value="${totalPage-1}">${totalPage-1}</a></li>
				<li><a href="javascript:void(0);" data-value="${totalPage}">${totalPage}</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="javascript:void(0);" data-value="1">1</a></li>
				<li><a href="javascript:void(0);" data-value="2">2</a></li>
				<li>...</li>
				<li><a href="javascript:void(0);" data-value="${currentPage-2}">${currentPage-2}</a></li>
				<li><a href="javascript:void(0);" data-value="${currentPage-1}">${currentPage-1}</a></li>
				<li><a class="current-page" href="javascript:void(0);" data-value="${currentPage}">${currentPage}</a></li>
				<li><a href="javascript:void(0);" data-value="${currentPage+1}">${currentPage+1}</a></li>
				<li><a href="javascript:void(0);" data-action="${currentPage+2}">${currentPage+2}</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${currentPage!=totalPage}">
			<li><a href="javascript:void(0);" class="next">下一页</a></li>
		</c:if>
	</ol>
</template>
<template v-if="ajax">
	<ol v-on:click="paginationAction">
		<template v-if="pagination.currentPage>1">
			<li><a href="javascript:void(0);" class="pre">上一页</a></li>
		</template>

		<template v-if="isPre">
			<li v-for="number in pageList">
				<a href="javascript:void(0);" data-value="{{ $index+1 }}" v-bind:class="{'current-page':$index+1==pagination.currentPage}">
					{{ $index+1}}
				</a>
			</li>
		</template>
		
		<template v-if="isPost">
			<li><a href="javascript:void(0);" data-value="1" v-bind:class="{'current-page':1==pagination.currentPage}">1</a></li>
			<li><a href="javascript:void(0);" data-value="2" v-bind:class="{'current-page':2==pagination.currentPage}">2</a></li>
			<li>...</li>
			<li><a href="javascript:void(0);" data-value="{{pagination.totalPage-4}}" v-bind:class="{'current-page':pagination.totalPage-4==pagination.currentPage}">{{pagination.totalPage-4}}</a>
			</li>
			<li><a href="javascript:void(0);" data-value="{{pagination.totalPage-3}}" v-bind:class="{'current-page':pagination.totalPage-3==pagination.currentPage}">{{pagination.totalPage-3}}</a>
			</li>
			<li><a href="javascript:void(0);" data-value="{{pagination.totalPage-2}}" v-bind:class="{'current-page':pagination.totalPage-2==pagination.currentPage}">{{pagination.totalPage-2}}</a>
			</li>
			<li><a href="javascript:void(0);" data-value="{{pagination.totalPage-1}}" v-bind:class="{'current-page':pagination.totalPage-1==pagination.currentPage}">{{pagination.totalPage-1}}</a>
			</li>
			<li><a href="javascript:void(0);" data-value="{{pagination.totalPage}}" v-bind:class="{'current-page':pagination.totalPage==pagination.currentPage}">{{pagination.totalPage}}</a>
			</li>
		</template>

		<template v-if="isOther">
			<li><a href="javascript:void(0);" data-value="1" v-bind:class="{'current-page':1==pagination.currentPage}">1</a></li>
			<li><a href="javascript:void(0);" data-value="2" v-bind:class="{'current-page':2==pagination.currentPage}">2</a></li>
			<li>...</li>
			<li>
				<a href="javascript:void(0);" data-value="{{pagination.currentPage-2}}">
					{{pagination.currentPage-2}}
				</a>
			</li>
			<li>
				<a href="javascript:void(0);" data-value="{{pagination.currentPage-1}}">
					{{pagination.currentPage-1}}
				</a>
			</li>
			<li>
				<a href="javascript:void(0);" data-value="{{pagination.currentPage}}" class="current-page">
					{{pagination.currentPage}}
				</a>
			</li>
			<li>
				<a href="javascript:void(0);" data-value="{{pagination.currentPage+1}}">
					{{pagination.currentPage+1}}
				</a>
			</li>
			<li>
				<a href="javascript:void(0);" data-value="{{pagination.currentPage+2}}">
					{{pagination.currentPage+2}}
				</a>
			</li>
		</template>
		<template v-if="pagination.currentPage<pagination.totalPage">
			<li><a href="javascript:void(0);" class="next">下一页</a></li>
		</template>
	</ol>
</template>
