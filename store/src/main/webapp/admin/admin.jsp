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

<div style="width:800px;height:400px;border:solid red 1px;"> 
  <!-- 加载编辑器的容器 --> 
  <script id="container" name="content" type="text/plain">
       jk;hkjlhl
    </script> 
  <!-- 配置文件 --> 
  <script type="text/javascript" src="ueditor.config.js"></script> 
  <!-- js文件--> 
  <script  type="text/javascript"  src="jquery-2.1.1.js"></script> 

    <script  type="text/javascript"  src="ueditor.parse.js"></script> 

  <!-- 编辑器源码文件 --> 
  <script type="text/javascript" src="ueditor.all.js"></script> 
  <!-- 实例化编辑器 --> 
  <script type="text/javascript">
	

    var ue = UE.getEditor('container');
  
ue.ready(function() {
	
    $("form").submit(function(){
	
	 var html = ue.getContent();
	
		$("#content").val(html);
	$("#hidden").val(html);
	$("#content"
			).val(html);
	alert("dd");	
	})
	
   
	//$("#id").click(function(){
	

});

       </script> 
    
</div>
<button id="id" >按钮 </button>
<textarea id="content"></textarea>


<form  action="" method="post" >
<input type="hidden" id="hidden"/>
<input  type="submit" value="提交"/>
</form>
<img src="${pageContext.request.contextPath}/admin/logo.png"/>   

</body>
</html>