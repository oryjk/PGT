<%--
  Created by IntelliJ IDEA.
  User: fei
  Date: 2016/3/17
  Time: 23:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
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
<input type="file" value="上传图片"/>
</body>
</html>
