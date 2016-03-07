<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>ueditor demo</title>
</head>
<body>

<div style="width:800px;height:400px"> 
  <!-- 加载编辑器的容器 --> 
  <script id="container" name="content" type="text/plain">
       
    </script> 
  <!-- 配置文件 --> 
  <script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/admin/ueditor.config.js"/> "></script> 
  <!-- js文件--> 
  <script  type="text/javascript"  src="<spring:url value="${juedangpinStaticPath}/admin/jquery-2.1.1.js"/> "></script> 

    <script  type="text/javascript"  src="<spring:url value="${juedangpinStaticPath}/admin/ueditor.parse.js"/> "></script> 

  <!-- 编辑器源码文件 --> 
  <script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/admin/ueditor.all.js"/> "></script> 
  <!-- 实例化编辑器 --> 
  <script type="text/javascript">
  
    var ue = UE.getEditor('container');
	// ue.ready(function() {
$(document).ready(function(){	
     $("#root").change(function(){
    	
     
    	var bigTypeId=$("#bigTypeId").val();
     
      
    	 $.ajax({
    	  	   url:"http://localhost:8080/dianjinzi/admin/ajax",
    	  	   type:"post",
    	  	   data:{"bigType.id":bigTypeId},
    	    success:function(data){
    	  	  alert(data);
        	  }
    	    });
     });
    	 });
  
       </script> 

       
    
</div>


<form  action="" method="post" >

根元素<select id="root" >
  <option> 请选择</option>
	<c:forEach items="${bigList }" var="bigType">
      <option id="bigTypeId" value="${bigType.id }">  ${bigType.title }</option>
    </c:forEach>
</select>
<input type="button" value="添加根元素" id="addRoot"> 
<br/>
层元素<select id="ceng">


</select>

<input type="hidden" id="hidden"/>
<input  type="submit" value="提交"/>
</form>

<jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
</html>
