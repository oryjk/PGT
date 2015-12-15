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



    });
});