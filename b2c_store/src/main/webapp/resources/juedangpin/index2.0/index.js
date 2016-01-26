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

        //调整banner的高度,让他适应menu
        $('#bannerBox').css({
            //height: $('#menuList').height() - 10 + 'px'
            height: $('#menuList').height()  + 'px'
        });

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

        //显示购物车数量
        Prd.getOrderItemCount($('#asideCartCount, #fixedCartCount, #cartCount'));
        
        //content部分点击事件委托
        $('#content').click(function(event) {
            var target = $(event.target);
            var targetType = target.attr('data-btn');

            if (targetType == 'addCart' || targetType == 'addEnjoy') {
                var productId = target.attr('data-value');
                var productMessage = $(target).parent().parent().children().filter('.product-message');

                event.preventDefault();
                event.stopPropagation();

                if (targetType == 'addCart') {
                    //加入购物车
                    Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
                    $('#asideCartCount').parent().css({
                        'transform': 'scale(4)',
                        'opacity': '.5'
                    });
                    setTimeout(function() {
                        $('#asideCartCount').parent().css({
                            'transform': 'scale(1)',
                            'opacity': '1'
                        });
                    }, 500);
                } else if (targetType == 'addEnjoy') {
                    //添加收藏
                    Prd.addItemToFavourite(productId, productMessage);
                }
            }
        })

    });
});