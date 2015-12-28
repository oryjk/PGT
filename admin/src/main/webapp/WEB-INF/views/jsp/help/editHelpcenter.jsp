<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%-- <admin:container id="editHelp"></admin:container> --%>
<c:set var="staticPath"><spring:url value="${juedangpinStaticPath}"/></c:set>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet" href="/resources/help/helpcenter.css"/>
    <link rel="stylesheet" href="/admin/resources/ueditor/themes/default/css/ueditor.css"/>
</head>
<body>


<!--正文-->
<div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <div class="aside">
            <div class="aside1"><h2>帮助中心</h2></div>
        <ul>
            <c:forEach items="${helpCategorVoList}" var="categoryVo">
                <li><a class="menu-level-1" >${categoryVo.category.name}</a></li>
                <li>
                    <ul>
                        <c:forEach items="${categoryVo.helpCenterList}" var="helpVo">

                            <c:if test="${helpVo.id == helpCenter.id}">
                                <li><a    class="menu-level-end current-page" href="${pageContext.request.contextPath}/helpcenter/${helpVo.id}">${helpVo.title}</></li>

                            </c:if>

                            <c:if test="${helpVo.id != helpCenter.id}">
                                <li><a  class="menu-level-end"   href="${pageContext.request.contextPath}/helpcenter/${helpVo.id}">${helpVo.title}</></li>
                            </c:if>

                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>


         </ul>
        </div>
${helpCenter.content}+++
        <div id="main-box" class="main-box">

            <div class="main-right">
                <!--面包屑-->
                <div class="bread-nav">
                    <p>
                        <a href="#">帮助中心</a>
                        >
                        <a href="#">${helpCenter.category.name}</a>
                        >
                        <a href="#">${helpCenter.title}</a>
                    </p>
                </div>
                <script type="text/javascript" src="/admin/resources/ueditor/ueditor.parse.js"></script>
 				<form action="../update" method="post">
			    	<input name="id" type="text" value="${helpCenter.id}">
			    	<input name="title" type="text" value="${helpCenter.title}">
				   
				  
				     <!-- 加载编辑器的容器 -->
				    <script id="container" name="content" type="text/plain">
  	 					${helpCenter.content}
   					</script>
				   	<input type="submit" value="保存">
			    </form>
		      	<!-- 配置文件 -->
			    <script type="text/javascript" src="/admin/resources/ueditor/ueditor.config.js"></script>
			    <!-- 编辑器源码文件 -->
			    <script type="text/javascript" src="/admin/resources/ueditor/ueditor.all.js"></script>
			     <!-- 实例化编辑器 -->
			    <script type="text/javascript">
			    alert(URL)
			        var ue = UE.getEditor('container');
			    </script>
				    
               <%--  <img src="${pageContext.request.contextPath}/resources/${helpCenter.frontMedia.path}" alt="${helpCenter.title}"> --%>
            </div>

        </div>

    <div class="clear-float"></div>

    <!-- 类似商品-->
    <div class="similar-box">




        <div class="move-left-box">
            <a href="#"  id="moveLeft"><</a>
        </div>
        <div class="move-right-box">
            <a href="#"  id="moveRight">></a>
        </div>
    </div>

    <div class="clear-float"></div>

      </div>
    </div>
</body>

</html>