/**
 * Created by supersoup on 15/11/9.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        product: '../core/js/module/product'
    }
});

require(['jquery', 'component', 'product'], function ($, Cpn, Prd) {
    $(document).ready(function () {
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
            list: $('#verticalList'),
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


        //事件委托:加入购物车, 添加收藏
        $(document).on('click', '.addCart', addCart);
        $(document).on('click', '.addEnjoy', addEnjoy);
        if ($('#outOfStock').size() <= 0) {
            $(document).on('click', '.join-cart', mainAddCart);
        } else {
            $('.join-cart').css({
                background: 'grey',
                cursor: 'default'
            });
            $('.buy-now').css({
                background: 'grey',
                cursor: 'default'
            });
            $('.buy-now')[0].setAttribute('href','#');
        }


        //加入购物车
        function addCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'), function () {
                $("#right-menu").load('/product/buy');
            });
        }

        //添加收藏
        function addEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage);
        }

        //主商品加入购物车
        //加入购物车
        function mainAddCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'), function () {
                that.html('<span><i class="foundicon-checkmark"></i> 已加入</span>');
                $("#right-menu").load('/product/buy');
            });
        }

        //显示购物车数量
        Prd.getOrderItemCount($('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'));

        //侧边栏的功能些
        $(document).on('click', '.sideAddCart', sideAddCart);
        $(document).on('click', '.sideRemoveCart', sideRemoveCart);
        $(document).on('click', '.sideAddEnjoy', sideAddEnjoy);
        $(document).on('click', '.sideDisEnjoy', sideDisEnjoy);

        $(function(){
            var h = $(document).scrollTop();
            $(".right-menu").scroll(function() {
                var scroll_position = $(".right-menu").scrollTop();
                var height = $(".n-all").height() - $(".right-menu").height();
                if(scroll_position >= height){
                    $(".right-menu").scrollTop(height -1);
                    $(document).scrollTop(h);
                }
                if(scroll_position <= 0){
                    $(".right-menu").scrollTop(1);
                    $(document).scrollTop(h);
                }
            });
            $(document).scroll(function() {
                h = $(document).scrollTop();
            })
        })

        //right-box
        $(function () {
            var flag = 0;
            var status = "";
            $(".right1").click(function () {
                var m_status = $(this).attr("title");
                if (status != m_status || flag == 0) {
                    $("#side-bar").animate({right: "0"});
                    var url = $(this).attr("path");
                    status = $(this).attr("title");
                    $("#side-bar").show();
                    $("#right-menu").load(url);
                    flag = 1;
                } else if (flag == 1 && status == m_status) {
                    $("#side-bar").animate({right: "-300px"});
                    flag = 0;
                }
            });

            $('#header, #content, #footer').click(function (event) {
                if ($(event.target).attr('data-btn') != 'addCart')
                    $("#side-bar").animate({right: "-300px"});
                flag = 0;
            })
        });

        //加入购物车
        function sideAddCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.siblings().children('.product-message');


            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'));
        }

        //删除购物车
        function sideRemoveCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');

            event.preventDefault();
            Prd.removeItemFromOrder(productId, null, $('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'), function (param) {
                if (param.success == 1) {
                    $("#right-menu").load('/product/buy');
                }
            });
        }

        //添加收藏
        function sideAddEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.siblings().children('.product-message');
            ;


            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage);
        }

        //取消收藏
        function sideDisEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');

            event.preventDefault();

            Prd.removeFavourite(productId, null, null, function (param) {
                if (param.success == 1) {
                    that.parent().hide();
                }
            });
        }
    });
});