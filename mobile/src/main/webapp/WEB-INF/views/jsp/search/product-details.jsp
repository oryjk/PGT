<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/jquery1.8.3/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/product-details/js/right.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/static/product-details/js.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/resources/static/product-details/product-details.css" rel="stylesheet">
</head>
<!DOCTYPE html>
<body>

        <div class="content">
            <div class="banner">
                <div class="WSCSlideWrapper" id="WSCSlideWrapper">
                    <div>
                        <c:forEach items="${product.heroMedias}" var="heroMedias">
                            <a href="#"><img src="/resources/${heroMedias.path}"/></a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="content1">
                <div class="content1-1">
                    ${product.salePrice}
                </div>
                <div class="content1-2">
                    市场价：<span>${product.listPrice}</span>
                </div>
                <div class="content1-3">
                    下载客户端
                </div>
            </div>
            <div class="content2">
                <div class="content2-1">
                    <div class="content2-1-1">
                        ${product.name}
                    </div>
                    <div class="content2-1-2">
                        ${product.shortDescription}
                    </div>
                </div>
                <div class="content2-2"></div>
            </div>
            <div class="content3">
                <div class="content3-1">
                    <div class="content3-1-1">已选</div>
                    <div class="content3-1-2">数量</div>
                </div>
                <div class="content3-2">
                    <div class="content3-2-1">${product.name}</div>
                    <div class="content3-2-2">
                        <a href="#" class="minus">－</a>

                        <div class="num">1</div>
                        <a href="#" class="plus">+</a>

                        <div class="kong-num"></div>
                    </div>
                </div>
                <div class="content3-3"></div>
            </div>
            <div class="content3">
                <div class="content3-1">
                    <div class="content3-1-1">送至</div>
                    <div class="content4-1-2">提示</div>
                </div>
                <div class="content3-2">
                    <div class="content3-2-1">四川 成都 锦江区</div>
                    <div class="content4-2-2">
                        现货 免运费
                    </div>
                    <div class="content4-2-3">
                        支持7天无理由退货
                    </div>
                </div>
                <a href="#" class="content4-3"></a>
            </div>
        </div>

</body>
<script type="text/javascript">
    $(document).ready(function () {
        $('.WSCSlideWrapper').height($('.WSCSlideWrapper').width() * 1);
        $('#WSCSlideWrapperTwo').height($('#WSCSlideWrapperTwo').width() * 1.5);
        $('.WSCSlideWrapper').touchslide({
            timecontrol: 3000,
            animatetime: 300,
            direction: 'left',
            navshow: true,
            canvassuport: true
        });
    });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/product-details/js/banner.js"></script>
</html>