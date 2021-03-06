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

//$('#side-bar').scroll(function () {
//    alert("meeage");
//})
        //调整banner的高度,让他适应menu
        //$('#bannerBox').css({
        //    //height: $('#menuList').height() - 10 + 'px'
        //    height: $('#menuList').height()  + 'px'
        //});
        //right全屏幕高
        //var height = window.screen.height;
        //$(".side-bar").css("height", height);

        //轮播图
        Cpn.slider({
            next: 1,
            imgBox: $('#banner'),
            navBox: $('#bannerNav'),
            time: 600
        });

        //固定顶部
        Cpn.fixedHead({
            head: $('#fixedHead'),
            height: 800,
            time: 200
        });


        //弹出框
        Cpn.pop({
            popUp: $('#classifyPop'),
            close: $('#closeClassifyPop')
        });
        
        //content部分点击事件委托
        $('#content,#classifyPop').click(function(event) {
            var target = $(event.target);
            var targetType = target.attr('data-btn');

            if (targetType == 'addCart' || targetType == 'addEnjoy') {
                var productId = target.attr('data-value');
                var productMessage = $(target).parent().parent().children().filter('.product-message');

                event.preventDefault();
                event.stopPropagation();

                if (targetType == 'addCart') {
                    //加入购物车
                    Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'), function() {
                        $("#right-menu").load('/product/buy');
                    });

                    $('.right-buy1').css({
                        'transform': 'scale(4)',
                        'opacity': '.5'
                    });
                    setTimeout(function() {
                        $('.right-buy1').css({
                            'transform': 'scale(1)',
                            'opacity': '1'
                        });
                    }, 500);
                } else if (targetType == 'addEnjoy') {
                    //添加收藏
                    Prd.addItemToFavourite(productId, productMessage);
                }
            }
        });

        $(document).on('click', '.each-classify', function(event) {

            var target = $(event.target);
            var href=target.attr("data-value");
            event.preventDefault();
            $('#classifyPopCentent').load(href, function() {
                $('#classifyPop').fadeIn(300);
            })
        });

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

        $('.logo-box').on('mouseover',function(){
            window.history.pushState(null,null,'/baidu.html');
            window.location.href='#!sdasdada';
        })

    })

});

