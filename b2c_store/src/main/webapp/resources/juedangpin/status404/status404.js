/**
 * Created by supersoup on 15/12/12.
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

        //content部分点击事件委托
        $('.addCart').click(function(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var url = that.attr('data-url');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(url, productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
        });

        $('.addEnjoy').click(function(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var url = that.attr('data-url');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage);
        });

        //count-down
        var statusObj = {
            time: 5,
            text: $('#countDown')
        };
        $('#stopCountDown').click(function(event) {
            event.preventDefault();
            statusObj.time = -1;
        });

        setTimeout(countDown, 1000);

        function countDown() {
            if (statusObj.time != -1) {
                statusObj.time --;
                statusObj.text.html(statusObj.time);
                setTimeout(countDown, 1000);
                if (statusObj.time == 0) {
                    window.location=$("#redirectUrl").attr("value");
                }
            }
        }



    });
});