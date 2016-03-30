<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link href="${pageContext.request.contextPath }/resources/static/about-us/contact.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/static/js/right.js"></script>
</head>
<body>

<div class="header">
    <a href="#" class="arrow"></a>
    <div class="font">联系方式</div>
</div>
<div class="content">

    <p>如果您对“点金子”网的产品有任何疑问，或者对我们的服务有任何意见或建议，欢迎您直接与我们联络，我们将竭诚为您服务。
    </p>
    <p>电话服务中心工作时间：周一至周五 09:00-17:30。
    </p>
    <p>客服热线：028-85033350
        销售热线：028-85033350
    </p>
    <p>公司邮箱：kefu@dianjinzi.com

        公司地址：北京市朝阳区朝外大街乙6号0110，北京金地利翔投资顾问有限公司
    </p>
</div>

<%@include file="../common/footer.jsp" %>

</body>
</html>