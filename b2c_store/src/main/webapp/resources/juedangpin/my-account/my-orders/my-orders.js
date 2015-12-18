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

        //菜单折叠
        Cpn.foldToggle($('.menu-level-1'));

        //事件委托:加入购物车, 添加收藏
        $(document).on('click', '.addCart', addCart);
        $(document).on('click', '.addEnjoy', addEnjoy);
        $(document).on('click', '.order-history-search', orderSearch);

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

        Cpn.page($('#orderList'), $('#previousPage'), $('#nextPage'), $('#pageCount'), $('#pageWhich'), $('#pageSub'), $('#pages'), getOrderListUrl, pageObj, rendering);

        function rendering(param, list) {
            var orderArr = param.data.result;
            var str = '';
            for (var i = 0; i < orderArr.length; i ++) {
                var productArr = orderArr[i].commerceItems;

                str += '<div class="each-order"> <div class="order-info"> <div class="operate"> </div> 下单时间: <span class="order-time">'
                    + orderArr[i].submitDate
                    + '</span> 订单号: <span class="order-number">'
                    + orderArr[i].id
                    + '</span> 物流: <span>'
                    + orderArr[i].status
                    + '</span> <a class="link-btn" href="#"></a></div><table> ';

                for (var j = 0; j < productArr.length; j ++) {
                    str += '<tr> <td class="col1"><img src="'
                        + Prd.baseUrl + '/resources' + productArr[j].snapshotMedia.path
                        + '" alt="'
                        + productArr[j].name
                        + '"/> </td> <td class="col2"><a class="product-name" href="'
                        + Prd.baseUrl + '/product/' + productArr[j].referenceId
                        +'">'
                        + productArr[j].name
                        + '</a> </td> <td class="col3"> <span>'
                        + productArr[j].quality
                        + '</span></td> <td class="col4"> <a class="link-btn" href="#">申请退换货</a> </td>';

                    if ( j == 0) {
                        str += '<td class="col5" rowspan="100">'
                            + orderArr[i].shippingVO.shippingAddress.name
                            + '</td> <td class="col6" rowspan="100"><span>¥<span>'
                            + orderArr[i].subtotal
                            + '</span></span></td> <td class="col7" rowspan="100"> <span>';

                        if (orderArr[i].status == 1) {
                            str += '待结算</span><a class="link-btn" href="#">结算</a> </td>';
                        } else if (orderArr[i].status == 2) {
                            str += '待付款</span><a class="link-btn" href="#">付款</a> </td>';
                        } else if (orderArr[i].status == 3) {
                            str += '已付款</span> </td>';
                        } else if (orderArr[i].status == 4) {
                            str += '待确认完成</span> </td>';
                        } else if (orderArr[i].status == 5) {
                            str += '已完成</span> </td>';
                        }
                    }
                }

                str +='</tr> </table> </div>';
            }

            list.html(str);
        }

        // search order with order id or keyword
        function orderSearch(event) {
            var that = $(this);
            var redirectURL = that.attr('data-url');
            var keyword = $(":text.content-search").val();
            window.location.href = redirectURL + keyword;
        }
    });
});