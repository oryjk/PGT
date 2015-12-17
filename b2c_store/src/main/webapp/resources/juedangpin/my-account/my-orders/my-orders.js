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

        function rendering(param, list) {
            var orderArr = param.data.result;
            var str = '';
            for (var i = 0; i < p.length; i ++) {
                str += '<div class="each-order"> <div class="order-info"> <div class="operate"> </div> 下单时间: <span class="order-time">'
                    + orderArr.submitDate
                    + '</span> 订单号: <span class="order-number">'
                    + orderArr.id
                    + '</span> 物流: < span>'
                    + orderArr.status
                    + '</span> <a class="link-btn" href="#"></a> </div>';

                    str += '<table> <tr> <td class="col1"> <img src="'
                    + ../../core/images/cart/product-small-0.jpg
                    +'" alt=""/> </td> <td class=" col2"> <a class=" product - name" href="#">'
                    + swarovski施华洛世奇水晶时尚水晶结婚礼品 高端大气上档次 低调奢华有内涵 啪啪啪啪啪啪啪啪啪啪啪啪
                    + '</a> </td> <td class="col3"> <span>'
                    + 九五成新
                    + '</span> <span>宝蓝色</span> </td> <td class="col4"> <a class="link-btn" href="#">申请退换货</a> </td>';

                if ( i == 0) {
                str += '<td class="col5" rowspan="100">'
                    + 关羽
                    + '</td> <td class="col6" rowspan="100"><span>¥<span>'
                    +2000.00
                    + '</span></span></td> <td class="col7" rowspan="100"> <span>'
                    + 正在出库
                    + '</span> </td>';
                }

                    str +='</tr> < /table> </div>';
            }

        }

    });
});