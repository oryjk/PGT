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

        //菜单折叠
        Cpn.foldToggle($('.menu-level-1'));

        //分页
        var pageObj = {
            currentIndex: 0,
            capacity: 20
        };
        var getFavouriteLIstUrl = Prd.baseUrl + '/myAccount/ajaxFavourites';

        Cpn.page($('.product-list'), $('#previousPage'), $('#nextPage'), $('#pageCount'), $('#pageWhich'), $('#pageSub'), $('#pages'), getFavouriteLIstUrl, pageObj, rendering);

        //事件委托:加入购物车, 添加收藏, 取消收藏
        $(document).on('click', '.addCart', addCart);
        $(document).on('click', '.addEnjoy', addEnjoy);
        $(document).on('click', '.disLike', disLike);

        //加入购物车
        function addCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
        }

        //添加收藏
        function addEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage, that);
        }

        //取消收藏
        function disLike(event) {
            var that = $(this);
            var favouriteId = that.attr('data-favourite-id');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.removeFavourite(favouriteId, productMessage, that);
        }

        //渲染页面
        function rendering(data, list) {
            var favouriteStr = '';
            $.each(data, function() {
                favouriteStr +=
                    '<div class="list-product">'
                    +'    <div class="inner">'
                    +'    <a class="list-img-box" href="'+Prd.baseUrl + '/product/' + this.productId+'">';

                if (this.snapshotMedia) {
                    favouriteStr += '    <img src="'+Prd.baseUrl +'/resources'+ this.snapshotMedia.path +'"';
                } else {
                    favouriteStr += '    <img src="'+Prd.baseUrl +'/resources' +'"';
                }

                favouriteStr += 'alt=" '+this.name+' "/></a>'
                    +'    <div class="list-price-box"><span>¥</span><span>' +this.finalPrice+ '</span></div>'
                    +'<p class="product-link"><a href=" ' +Prd.baseUrl + '/product/' + this.productId +' ">'+this.name+'</a></p>'
                    +'<p><span></span> 评价</p>'
                    +'<div class="product-handle">'
                    +'    <a class="disLike" data-favourite-id="'+this.id+'" href="#"><i class="foundicon-heart"></i>取消</a>'
                    +' <a class="addEnjoy" data-value="'+this.productId+'" href="#"><i class="foundicon-heart"></i>收藏</a>'
                    +' <a class="addCart" data-value="'+this.productId+'" href="#"><i class="foundicon-cart"></i>购物车</a>'
                    +'</div>';

                    if (this.productStock < 1 || this.productStock == '0') {
                        favouriteStr += '<div class="out-of-stock"></div>';
                    }

                    favouriteStr +='<div class="product-message">添加成功</div>'
                    +'</div>'
                    +'</div>';
            });

            list.html(favouriteStr);
        }

    });
});