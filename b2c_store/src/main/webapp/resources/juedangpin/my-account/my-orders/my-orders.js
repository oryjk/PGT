/**
 * Created by supersoup on 15/12/14.
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

        //事件委托:加入购物车, 添加收藏
        $(document).on('click', '.addCart', addCart);
        $(document).on('click', '.addEnjoy', addEnjoy);

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

            Prd.addItemToFavourite(productId, productMessage);
        }


        //分页
        var pageObj = {
            currentIndex: 0,
            capacity: 5
        };
        var getOrderListUrl = Prd.baseUrl + '/myAccount/ajaxOrderHistory';

        Cpn.page($('.product-list'), $('#previousPage'), $('#nextPage'), $('#pageCount'), $('#pageWhich'), $('#pageSub'), $('#pages'), getOrderListUrl, pageObj, rendering);

        function rendering($arr, list) {
            console.log($arr)
        }

    });
});