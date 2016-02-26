<%--
  Created by IntelliJ IDEA.
  User: carlwang
  Date: 2/26/16
  Time: 11:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
	<link href="/resources/product-list/product-list.css" rel="stylesheet"/>
	<link href="/resources/product-list/list.css" rel="stylesheet">
	<script src="/resources/core/js/jquery.min.js" data-main="invest"></script>
	<script type="text/javascript" src="/resources/product-list/productList.js"></script>
</head>
<body>
<!--header begin-->
<div class="header">
	<div class="header-inner">
		<h1 class="logo">
			<a href="#"></a>
		</h1>
		<ul class="nav">
			<li><a href="#">在当品大厅</a></li>
			<li><a href="#">在线典当</a></li>
			<li><a href="#">绝当品</a></li>
		</ul>
		<form class="search" action="">
			<input type="text" placeholder="点击搜索"/>
		</form>
		<div class="status">
			<div class="status-login"></div>
			<div class="status-logout"></div>
		</div>
	</div>
</div>
<!--header end-->
<!--content begin-->
<div class="content">
	<div class="title">
		<div class="title-left">
			<img src="../core/images/icon/tenderlist1_03.png">

			<h2>项目列表</h2>
		</div>
	</div>
	<div class="main">
		<div class="menu">
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list1.png">
				<h4>全部</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list2.png">
				<h4>文玩杂项</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list3.png">
				<h4>翡翠玉石</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list4.png">
				<h4>琥珀蜜蜡</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list5.png">
				<h4>木制品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list6.png">
				<h4>钻石饰品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list7.png">
				<h4>精品钟表</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list8.png">
				<h4>科技产品</h4>
			</a>
			<a href="javascript:" path="list-all.html" class="list">
				<img src="../core/images/icon/list9.png">
				<h4>其他</h4>
			</a>

			<div class="list-end"></div>

		</div>

		<!--product list-->
		<div class="products">

			<div class="all-list">
				<div class="title1">当前位置：文玩杂项<span>查询结果共1000个相关项目</span></div>
				<ul class="title2">
					<li><input type="radio" name="sex" value="n"/>全部</li>
					<li><input type="radio" name="sex" value="v"/>即将开始</li>
					<li><input type="radio" name="sex" value="n"/>进行中</li>
					<li><input type="radio" name="sex" value="v"/>已结束</li>
				</ul>
				<div class="title3">
					<a href="#">综合推荐</a>
					<a href="#">最新上线</a>
					<a href="#">金额最多</a>
					<a href="#">即将结束</a>
				</div>
				<div class="box-all">

					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>product-list.js
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>

					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
					<a href="#" class="box1">
						<div class="box1-img">
							<img src="../core/images/product/280X220.jpg" alt=""/>
						</div>
						<div class="box1-name">
							产品名称啊啊啊啊啊
						</div>
						<div class="progress-bar">
							<div class="inner" style="width: 120px;" data-value=""></div>
						</div>
						<div class="info-box">
							<div class="surplus-count">
								<p class="info-value">70%</p>

								<p class="info-title">剩余产品</p>
							</div>
							<div class="cost-sum">
								<p class="info-value"><span>¥</span><span>18,000.00</span></p>

								<p class="info-title">产品认购总额</p>
							</div>
							<div class="surplus-time">
								<p class="info-value"><span>17</span>天</p>

								<p class="info-title">截止时间</p>
							</div>
						</div>
						<div class="invest-status">
							进行中
						</div>
						<a class="invest-join-favorite" href="javascript:void(0);"></a>
					</a>
				</div>
				<div class="page-box">
					<ol>
						<li><a href="javascript:void(0);">1</a></li>
						<li>...</li>
						<li><a href="javascript:void(0);">3</a></li>
						<li><a href="javascript:void(0);">4</a></li>
						<li><a href="javascript:void(0);">5</a></li>
						<li>...</li>
						<li><a href="javascript:void(0);">7</a></li>
					</ol>
				</div>
			</div>

		</div>
	</div>
</div>
<!--content end-->

.<!-- recommend begin-->
<div class="recommend-box">
	<div class="recommend">
		<div class="recommend-head">
			<h3>猜你喜欢</h3>
			<a href="javascript:void(0);" class="recommend-change">换一批</a>
		</div>
		<div class="recommend-list">
			<ul>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>
				<li>
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>
				<li class="recommend-item-end">
					<a href="#" class="recommend-img-box"><img src="../core/images/product/invest_hero_1.png" alt=""/></a>

					<div class="recommend-info">
						<h4><a href="#">鑫鑫典当行纯金镶钻生肖戒指</a></h4>

						<div class="recommend-progress">
							<div class="recommend-progress-bar">
								<div class="inner" style="width: 100px;" date-value=""></div>
							</div>
							<div class="recommend-progress-text">
								<p>剩余</p>

								<p><span>35</span>%</p>
							</div>
						</div>
						<div class="recommend-join">
							<div class="recommend-time">剩余时间 <span>15</span>天</div>
							<div class="recommend-people"><span>18</span>名参与者</div>
						</div>
					</div>
				</li>

			</ul>
		</div>
	</div>
</div>
<!-- recommend end-->

<!--footer begin-->
<div class="footer" id="footer">
	<div class="goods-box">
		<div class="our-goods">
			<div class="our-good">
				<div class="icon-box">
					<i class="foundicon-people"></i>
				</div>
				<div class="good-text">
					<h4>专家鉴定</h4>

					<p>专业鉴定团队</p>

					<p>安全可靠</p>
				</div>
			</div>
			<div class="our-good">
				<div class="icon-box">
					<i class="foundicon-heart"></i>
				</div>
				<div class="good-text">
					<h4>用心推荐</h4>

					<p>我们的专心和用心</p>

					<p>您的放心</p>
				</div>
			</div>
			<div class="our-good">
				<div class="icon-box">
					<i class="foundicon-star"></i>
				</div>
				<div class="good-text">
					<h4>五星级服务</h4>

					<p>线上线下服务同步</p>

					<p>广受好评</p>
				</div>
			</div>
			<div class="our-good">
				<div class="icon-box">
					<i class="foundicon-clock"></i>
				</div>
				<div class="good-text">
					<h4>发货及时</h4>

					<p>每天四次发货</p>

					<p>便捷高效</p>
				</div>
			</div>
			<div class="our-good">
				<div class="icon-box">
					<i class="foundicon-smiley"></i>
				</div>
				<div class="good-text">
					<h4>快乐购物</h4>

					<p>体会快乐的购物</p>

					<p>期待您的笑脸</p>
				</div>
			</div>
		</div>
	</div>

	<div class="about-us">
		<ul>
			<li><h3>购物与支付</h3></li>
			<li><a href="">购物流程</a></li>
			<li><a href="">订单查询</a></li>
			<li><a href="">支付方式</a></li>
			<li><a href="">发票领取</a></li>
		</ul>
		<ul>
			<li><h3>配送说明</h3></li>
			<li><a href="">配送方式和时间</a></li>
			<li><a href="">配送费用和签收</a></li>
			<li><a href=""></a></li>
		</ul>
		<ul>
			<li><h3>关于我们</h3></li>
			<li><a href="">点金子</a></li>
			<li><a href="">点金子绝当品</a></li>
			<li><a href="">联系我们</a></li>
			<li><a href="">招贤纳士</a></li>
			<li><a href="">业务范围</a></li>
			<li><a href="">隐私声明</a></li>
		</ul>
		<ul>
			<li><h3>售后服务</h3></li>
			<li><a href="">退换货政策</a></li>
			<li><a href="">退款说明</a></li>
		</ul>
		<div class="us">
			<div class="our-app">
				<a class="apple-dimension" href="#">
					<img class="app-icon" src="../core/images/footer/apple.png" alt="苹果客户端下载"/>

					<h3>苹果客户端</h3>
					<img class="two-dimension" src="../core/images/footer/erweima.jpg" alt="二维码"/>
				</a>
				<a class="android-dimension" href="#">
					<img class="app-icon" src="../core/images/footer/android.png" alt="安卓客户端下载"/>

					<h3>安卓客户端</h3>
					<img class="two-dimension" src="../core/images/footer/erweima.jpg" alt="二维码"/>
				</a>
				<a class="online-server" href="#">
					<img class="app-icon" src="../core/images/footer/weixin.png" alt="微信客户端下载"/>

					<h3>微信公众号</h3>
					<img class="two-dimension" src="../core/images/footer/erweima.jpg" alt="二维码"/>
				</a>
				<a class="online-server" href="#">
					<img class="app-icon" src="../core/images/footer/weibo.png" alt="点金子在线投资"/>

					<h3>点金子投资</h3>
					<img class="two-dimension" src="../core/images/footer/erweima.jpg" alt="二维码"/>
				</a>
			</div>
			<div class="our-info">
				<h3>点金子总部地址</h3>

				<p>成都市武侯区某某某某某某某某</p>

				<p>咨询热线：028-85033350</p>

				<p>企业邮箱：zxhl998@163.com</p>
			</div>
		</div>
	</div>

	<div class="approve-box">
		<div class="approve">
			<a href="#">
				<img src="../core/images/footer/approve-0.png" alt=""/>
			</a>
			<a href="#">
				<img src="../core/images/footer/approve-1.jpg" alt=""/>
			</a>
		</div>
		<p>蜀ICP备15022028号 © 2015 dianjinzi, Inc. All rights reserved.</p>
	</div>
</div>
<!--footer end-->
</body>
</html>