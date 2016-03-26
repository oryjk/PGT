<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<script type="text/javascript" src="/resources/core/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/core/js/jquery.form.js"></script>
<body>
<form action="create" method="post">
    <input type="text" name="name" placeholder="名称"/>
    <label>类型</label>
    <select name="type">
        <option value="1">绝当淘</option>
        <option value="2">在当淘</option>
    </select>
    <input type="text" name="price" placeholder="价格"/>
    <input type="text" name="url" placeholder="链接"/>
    <label>图片id</label>
    <input type="text" name="media.id"/>
    <input type="submit" value="提交"/>
</form>

<form id="uploadForm" method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="file"/>
    <input id="image_submit" value="上传" type="button"/>
    <div id="show"></div>
</form>
<script>
    $(function(){
        //这里可以用js获取项目根网址 www.pgt_admin.com/
        var base = "http://www.pgt_admin.com/upload/image/";
        alert(base);
        var path = "";
        var options={
            url: base,
            type:"post",
            success:function(url){
                $("#show").empty();
                $("#show").append("<img src=" + url + "/>");
                path = url;
                $("#imgs").val(path);
            }
        };
        $("#uploadForm").submit(function() {
            $(this).ajaxSubmit(options);
            return false;
        });
        $("#image_submit").click(function(){
            $("#uploadForm").submit();
        });
    });
</script>
</body>
</html>
