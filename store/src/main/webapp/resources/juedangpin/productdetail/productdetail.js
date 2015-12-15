/**
 * Created by supersoup on 15/11/9.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component'
    }
});

require(['jquery', 'component'], function($, Cpn) {
    $(document).ready(function() {
        //tab切换
        Cpn.tab({
            tabArea: $('#info,#question,#talking'),
            tabLi: $('#tab li'),
            tabTarget: $('#tab h2')
        }, 200);

        //图片切换和放大
        var middlePic = $('#middlePic');
        Cpn.pic({
            small: $('#smallPic'),
            middle: middlePic,
            big: $('#bigPic'),
            glass: $('#glass'),
            src: '',
            middleX: middlePic.offset().left,
            middleY: middlePic.offset().top,
            x: 95,
            y: 95
        });

        //横向商品列表点击左右移动
        Cpn.rowList({
            list: $('#rowList'),
            left: $('#moveRight'),
            right: $('#moveLeft')
        });

        //纵向商品列表点击上下移动
        Cpn.verticalList({
            list:  $('#verticalList'),
            top: $('#moveTop'),
            bottom: $('#moveBottom')
        });

        //固定头部
        Cpn.fixedHead({
            head: $('#fixedHead'),
            height: 800,
            time: 200
        });

        //弹出框
        Cpn.pop({
            popUp: $('#popUp'),
            close: $('#popClose, #popReset, #popSubmit')
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