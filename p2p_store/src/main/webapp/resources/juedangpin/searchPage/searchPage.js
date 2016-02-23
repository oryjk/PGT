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

        Cpn.tab({
            tabArea: $('#recommend>div'),
            tabLi: $('#tab>li'),
            tabTarget: $('#tab h2')
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

        //事件委托:加入购物车, 添加收藏
        $(document).on('click', '.addCart', addCart);
        $(document).on('click', '.addEnjoy', addEnjoy);

        //加入购物车
        function addCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount,.right-buy1'), function() {
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

        //checkbox
        Cpn.checkbox($('.only-availble'));


        //显示购物车数量
        Prd.getOrderItemCount($('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'));

        //侧边栏的功能些
        $(document).on('click', '.sideAddCart', sideAddCart);
        $(document).on('click', '.sideRemoveCart', sideRemoveCart);
        $(document).on('click', '.sideAddEnjoy', sideAddEnjoy);
        $(document).on('click', '.sideDisEnjoy', sideDisEnjoy);

        //right-box
        $(function(){
            var flag = 0;
            var status = "";
            $(".right1").click(function(){
                var m_status = $(this).attr("title");
                if(status != m_status || flag == 0){
                    $("#side-bar").animate({right:"0"});
                    var url = $(this).attr("path");
                    status = $(this).attr("title");
                    $("#side-bar").show();
                    $("#right-menu").load(url);
                    flag = 1;
                }else if(flag == 1 && status == m_status){
                    $("#side-bar").animate({right:"-300px"});
                    flag = 0;
                }
            });

            $('#header, #content, #footer').click(function(event) {
                if ($(event.target).attr('data-btn') != 'addCart')
                    $("#side-bar").animate({right:"-300px"});
                flag = 0;
            })
        });

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
            Prd.removeItemFromOrder(productId, null, $('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'), function(param) {
                if (param.success == 1) {
                    $("#right-menu").load('/product/buy');
                }
            });
        }

        //添加收藏
        function sideAddEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.siblings().children('.product-message');;


            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage);
        }

        //取消收藏
        function sideDisEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');

            event.preventDefault();

            Prd.removeFavourite(productId, null, null, function(param) {
                if (param.success == 1) {
                    that.parent().hide();
                }
            });
        }

    });
});