<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="../core/head.jspf">
	<jsp:param name="cssPath" value="/productdetail/productdetail.css" />
	<jsp:param name="title" value="${product.name}"/>
	<jsp:param name="keywords" value="点金子,淘在当,绝当淘,文玩销售,珠宝典当,黄金典当,铂金典当,玉石典当,钟表典当,奢侈品典当,钻石典当,翡翠典当,琥珀典当,绝当品销售" />
	<jsp:param name="description" value="点金子，典当行业互联网风控专家，专业销售绝当品、在当品预售，平台销售种当品类繁多，珠宝销售、黄金销售、铂金销售玉石销售、钻石销售、文玩销售、琥珀销售、蜜蜡销售。" />
</jsp:include>
<script
	src="<spring:url value="${juedangpinStaticPath}/productdetail/queryConsulitngAndDiscuss.js"/>"></script>
<body>
	<!--主头部-->
	<div class="header" id="header">

		<c:if test="${ES==true}">
			<jsp:include page="../core/header-main.jsp" />
		</c:if>

		<c:if test="${ES==false}">
			<jsp:include page="../core/header-main-db.jsp" />
		</c:if>

	</div>



	<c:if test="${ES==true}">

		<jsp:include page="productdetail_es.jsp" />

	</c:if>


	<c:if test="${ES==false}">
		<jsp:include page="productdetail_db.jsp" />
	</c:if>


	<div id="question" class="question">
		<jsp:include page="consulting.jsp" />
	</div>
	<div id="talking" class="talking">

		<jsp:include page="discuss.jsp" />

	</div>
	</div>
	</div>
	</div>

	<jsp:include page="../core/footer-main.jsp" >
		<jsp:param name="useside" value="true" />
	</jsp:include>
	<!--弹出框-->
	<div id="popUp" class="pop-up">
		<div class="inner">
			<h3>
				<span id="popTitle" class="pop-title">确认</span> <span id="popClose"
					class="close">X</span>
			</h3>
			<form id="popForm" class="pop-content" action="">

				<div class="row1">
					<div class="text">
						<span id="popContent">咨询已提交!我们会尽快为您解答!</span>
					</div>
				</div>

				<div>
					<input type="hidden" name="" value="" />
				</div>

			</form>
		</div>
	</div>
	<jsp:include page="../core/baidu.jsp"></jsp:include>
</body>
<script
	src="<spring:url value="${juedangpinStaticPath}/core/js/require.js"/>"
	data-main="<spring:url value="${juedangpinStaticPath}/productdetail/productdetail"/>"></script>
<script type="text/javascript" src="<spring:url value="${juedangpinStaticPath}/core/js/jquery.min.js"/>"></script>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
</html>