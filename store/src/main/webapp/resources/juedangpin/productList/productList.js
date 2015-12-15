/**
 * Created by carl on 15/10/30.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        product: '../core/js/module/product'
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

        //横向商品列表点击左右移动
        Cpn.rowList({
            list: $('#rowList'),
            left: $('#moveRight'),
            right: $('#moveLeft')
        });

        //纵向列表高度和右边内容一致
        $('#verticalList').parent().css({
            maxHeight: $('#main').outerHeight(true) - 124 + 'px'
        });
        //纵向商品列表点击上下移动
        Cpn.verticalList({
            list:  $('#verticalList'),
            top: $('#moveTop'),
            bottom: $('#moveBottom')
        });

        //购物车和收藏
        $('.addCart').click(function(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
        });

        $('.addEnjoy').click(function(event) {
            console.log(this);
            var that = $(this);
            var productId = that.attr('data-value');
            //var url = that.attr('data-url');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage);
        });
    });
});