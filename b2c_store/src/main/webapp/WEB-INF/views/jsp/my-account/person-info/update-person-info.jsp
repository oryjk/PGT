<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <title>绝当品</title>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/person-info/update-person-info.css"/>"/>
    <link rel="stylesheet" href="<spring:url value="${juedangpinStaticPath}/my-account/other-part.css"/>"/>




</head>
<body>

    <!--主头部-->
    <div class="header" id="header">
        <jsp:include page="../../core/header-main.jsp" />
    </div>


    <!--正文-->
    <div class="content-box">

    <div class="content">

        <!-- 侧边栏-->
        <jsp:include page="../vertical-my-account-directory.jsp">
            <jsp:param name="step" value="address" />
        </jsp:include>


        <!-- 详细内容列表-->
        <div id="main" class="main-box">

            <!--面包屑-->
            <div class="bread-nav">
                <p>
                    <a href="#">个人中心</a>
                    >
                    <a href="#">个人管理</a>
                    >
                    <a href="#">地址管理</a>
                </p>
            </div>

            <div class="main-right">
                <div class="personal-content">
                    <div class="personal-left">


                        <form id="jvForm" action="/add" method="post" enctype="multipart/form-data">

                        <div class="personal-img">
                            <img id="allImgUrl"  <c:if test="${!empty userInformation.path}">src="${pageContext.request.contextPath}/resources/${userInformation.path}"</c:if>  <c:if test="${empty userInformation.path}"> src=${pageContext.request.contextPath}/resources/juedangpin/core/images/myaccount/no-head.jpg</c:if>/>
                        </div>
                        <!--仿file-->
                        <div class="new-input-file">
                            点击上传头像
                            <input id="upload"  name="uploadPic"  class="oriinal-input-file" type="file" value="上传" >
                        </div>
                            </form>


                    </div>
                   <form action="${pageContext.request.contextPath}/userinformation/create" method="post">
                      ${error}
                       <input type="hidden" name="path" id="path" value="${userInformation.path}"/>
                    <div class="personal-right">
                        <div class="text1">
                            <span style="color: red"> *</span>昵称
                            <input type="text" class="input-text1" name="nickname" value="${userInformation.nickname}">
                        </div>
                        <div class="text2">
                            <span style="color: red"> *</span>手机
                            <input type="text" class="input-text1" name="phoneNumber" value="${userInformation.phoneNumber}">
                        </div>
                        <div class="text2">
                            <div class="input-text5">
                                性别
                            </div>
                            <div class="input-text4">
                                <label><input name="gender" type="radio" value="男" <c:if test="${userInformation.gender eq '男'}">checked=""</c:if>>男</label>
                                <label><input name="gender"  type="radio" value="女" <c:if test="${userInformation.gender eq '女'}">checked=""</c:if>>女</label>
                            </div>
                        </div>
                        <div class="text3">
                            邮箱
                            <input type="text" class="input-text1" name="personEmail" value="${userInformation.personEmail}">
                        </div>
                        <div class="text3">
                            身份证号
                            <input type="text" class="input-text2" name="idCard" value="${userInformation.idCard}" >
                        </div>
                        <div class="text3">
                            <div class="input-text5">
                                婚姻状况
                            </div>
                            <div class="input-text4">
                                <input type="radio" class="" name="marrage" value="已婚"  <c:if test="${userInformation.marrage eq '已婚'}">checked=""</c:if>>已婚
                                <input type="radio" name="marrage" value="保密"  <c:if test="${userInformation.marrage eq '未婚'}">checked=""</c:if>>未婚
                                <input type="radio" name="marrage" value="保密"  <c:if test="${userInformation.marrage eq '保密'}">checked=""</c:if>>保密
                            </div>
                        </div>
                        <div class="text3">
                            所在行业
                            <input type="text" class="input-text2" name="industry" value="${userInformation.industry}">
                        </div>
                        <div class="text3">
                            月收入
                            <input type="text" class="input-text3" name="income" value="${userInformation.income}">
                        </div>
                        <div class="text3">
                            <div class="text3-font1" >支付绑定</div>
                            <div class="text3-font2">亲，您还没有绑定哦 <a class="link-btn" href="#">点击绑定</a></div>
                        </div>
                        <div class="input-btn">
                            <input class="d-btn" type="submit" value="保存">
                        </div>
                    </div>

                </div>
            </div>

        </div>
        </form>


        <div class="clear-float"></div>

        <!-- 类似商品-->
        <div class="similar-box">
            <h2>类似商品</h2>

            <ul class="similar" id="rowList">
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>1698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-1.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针竖刻表盘日历男表1501A 双竖刻度黑盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>2698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-2.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)男士手表男士机械表 进口全自动日历数字表盘男表 防水精钢表带1501B 数字表盘白盘钢带
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>3698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-3.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)24钻机芯原装进口防水全自动机械表 太阳纹日历夜光大表盘男表1518 竖刻夜光-银
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>4098.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-4.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动夜光三针大表盘日历 男表1503 罗马刻度黑盘皮带(人气爆款_送礼佳品)
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>5798.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>6698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>7698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>8698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>9698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>10698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>11698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>12698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>13698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>14698.00</span>
                    </p>
                </li>
                <li>
                    <a class="similar-pic-box" href="#"><img src="../../core/images/productdetail/similar-5.jpg" alt=""/></a>
                    <a class="similar-name" href="#">
                        丽声(RHYTHM)原装进口男士手表男士机械表 防水全自动细三针
                    </a>
                    <p class="similar-cost">
                        ¥
                        <span>15698.00</span>
                    </p>
                </li>
            </ul>

            <div class="move-left-box">
                <a href="#"  id="moveLeft"><</a>
            </div>
            <div class="move-right-box">
                <a href="#"  id="moveRight">></a>
            </div>
        </div>
    </div>
</div>

<!--主脚部-->
    <jsp:include page="../../core/footer-main.jsp"></jsp:include>
</body>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/jquery.min.js"/>"></script>
<script src="<spring:url value="${juedangpinStaticPath}/core/js/jquery.form.js"/>"></script>


<script type="text/javascript">
    $("#upload").change(function(){
        //定义参数
        var options = {
            url : "${pageContext.request.contextPath}/upload/uploadPic",
            dataType : "json",
            type :  "post",
            success : function(data){
                //回调 二个路径
                //url
                //path
                $("#allImgUrl").attr("src","${pageContext.request.contextPath}/resources/"+data.url);
                $("#path").val(data.url);
            }
        };

        //jquery.form使用方式
        $("#jvForm").ajaxSubmit(options);

    });

</script>

</html>