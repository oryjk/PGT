/**
 * Created by supersoup on 15/12/10.
 */
require.config({
    paths: {
        jquery: '../../core/js/jquery.min',
        component: '../../core/js/module/component',
        product: '../../core/js/module/product'
    }
});

require(['jquery', 'component', 'product'], function($, Cpn, Prd) {

    $(document).ready(function() {
        //固定头部
        Cpn.fixedHead({
            head: $('#fixedHead'),
            height: 200,
            time: 200
        });

        //tab切换
        Cpn.tab({
            tabArea: $('#recommend>div'),
            tabLi: $('#tab>li'),
            tabTarget: $('#tab h2')
        });

        //三个rowList的水平移动
        Cpn.rowList({
            list: $('#rowList1'),
            left: $('#moveRight1'),
            right: $('#moveLeft1')
        });
        Cpn.rowList({
            list: $('#rowList2'),
            left: $('#moveRight2'),
            right: $('#moveLeft2')
        });
        Cpn.rowList({
            list: $('#rowList3'),
            left: $('#moveRight3'),
            right: $('#moveLeft3')
        });

        //加入购物车
        $('.addCart').click(function(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var url = that.attr('data-url');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
        });

        //添加收藏
        $('.addEnjoy').click(function(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var url = that.attr('data-url');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage, that);
        });

        //取消收藏
        $('.disLike').click(function(event) {
            var that = $(this);
            var favouriteId = that.attr('data-favourite-id');
            var url = that.attr('data-url');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.removeFavourite(favouriteId, productMessage, that);
        });

        //菜单折叠
        Cpn.foldToggle($('.menu-level-1'));

        //分页
        var pageObj = {
            currentIndex: 0,
            capacity: 5
        };
        var maxIndex = -1;

        var previousPage = $('#previousPage');
        var nextPage = $('#nextPage');
        var pageCount = $('#pageCount');
        var pageWhich = $('#pageWhich');
        var pageSub = $('#pageSub');
        var pages = $('#pages');

        //载入时
        getPage(pageObj);

        //前页后页
        previousPage.click(function(event) {
            event.preventDefault();

            pageObj.currentIndex --;
            getPage(pageObj);
        });
        nextPage.click(function(event) {
            event.preventDefault();

            pageObj.currentIndex ++;
            getPage(pageObj);
        });

        //填写页码并确定
        pageSub.click(function(event) {
            event.preventDefault();

            var page = pageWhich.val();
            if (page < 0) {
                page = 0;
            }
            if (page > maxIndex-1) {
                page = maxIndex;
            }
            pageObj.currentIndex = page;
            getPage(pageObj);
        });

        //选择页码
        pages.delegate('a', 'click', function(event) {
            event.preventDefault();

            var that = $(event.target);
            pageObj.currentIndex = that.html() - 1;
            getPage(pageObj);
        });

        function getPage(pageObj) {
            $.ajax({
                type: 'get',
                url: '/b2c_store/myAccount/ajaxFavourites',
                data: {
                    currentIndex: pageObj.currentIndex
                },
                success: function(param) {
                    if (param.success == 1) {
                        pageObj.currentIndex = param.data.currentIndex;
                        maxIndex = param.data.maxIndex;

                        //前页后页选择性隐藏
                        if (pageObj.currentIndex <= 0) {
                            previousPage.hide();
                        } else {
                            previousPage.show();
                        }
                        if (pageObj.currentIndex >= maxIndex-1) {
                            nextPage.hide();
                        } else {
                            nextPage.show();
                        }

                        //总页数显示
                        pageCount.html(maxIndex);

                        //创造页码
                        var pageStr = '';
                        for (var i = 1; i <= maxIndex; i ++) {
                            pageStr += '<li><a href="#">' + i + '</a></li>';
                        }
                        pages.html(pageStr);
                        pages.children().children('a').eq(pageObj.currentIndex).addClass('page-focus');

                        //渲染数据
                        //var favouriteStr = '';
                        //$.each($(param.data.result), function() {
                        //    console.log(this);
                        //    favouriteStr =
                        //        '<div class="list-product">'
                        //    +'    <div class="inner">'
                        //    +'    <a class="list-img-box" href="'+this.description+'">'
                        //    +'    <img src="${pageContext.request.contextPath}/resources${fav['snapshotMedia']['path']}"'
                        //    +'alt="${empty fav['snapshotMedia']['title'] ? fav.name : fav['snapshotMedia']['title']}"/></a>'
                        //    +'    <div class="list-price-box"><span>¥</span><span><fmt:formatNumber value="${fav.finalPrice}" pattern="#.00" type="number" /></span></div>'
                        //    +'<p class="product-link"><a href="<spring:url value="${urlConfiguration.pdpPage}/${fav.productId}"/>" data-favourite-id="${fav.id}">${fav.name}</a></p>'
                        //    +'<p><span>${fav.discussCount}</span> 评价</p>'
                        //    +'<div class="product-handle">'
                        //    +'    <a class="disLike" data-favourite-id="${fav.id}" href="#"><i class="foundicon-heart"></i>取消</a>'
                        //    +'<a class="addEnjoy" data-value="${fav.productId}" href="#"><i class="foundicon-heart"></i>收藏</a>'
                        //    +'<a class="addCart" data-vaule="${fav.productId}" href="#"><i class="foundicon-cart"></i>购物车</a>'
                        //    +'</div>'
                        //    +'    <div class="out-of-stock"></div>'
                        //    +'<div class="product-message">添加成功</div>'
                        //    +'    </div>'
                        //    +'    </div>';
                        //})
                    }
                }
            })
        }


    });
});