/**
 * Created by supersoup on 15/12/17.
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

        //tab切换
        Cpn.tab({
            tabArea: $('#recommend>div'),
            tabLi: $('#tab>li'),
            tabTarget: $('#tab h2')
        });

        //弹出框
        Cpn.pop({
            popUp: $('#popUp'),
            close: $('#popClose, #popReset')
        });
        $('.link-btn').click(function() {
            $('#popUp').fadeIn(300);
        });

        var areaObj = {
            province: $('#province'),
            city: $('#city'),
            country: $('#country')
        };


        //省->市
        Cpn.select2(areaObj.province, function(id) {
            var url = Prd.baseUrl + '/getCityByProvinceId/' + id;
            $.ajax({
                url: url,
                data: null,
                success: function(param) {
                    var str = '';
                    $.each($.parseJSON(param), function() {
                        str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
                    });
                    $('#city').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
                    $('#country').children('.selected').html('请选择').end().siblings('.options').html('').next('.select-value').val('');
                }
            })
        });

        //市->区
        Cpn.select2(areaObj.city, function(id) {
            var url = Prd.baseUrl + '/getAreaByCityId/' + id;
            $.ajax({
                type: 'get',
                url: url,
                data: null,
                success: function(param) {
                    var str = '';
                    $.each($.parseJSON(param), function() {
                        str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
                    });
                    $('#country').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
                }
            })
        });

        //区
        Cpn.select2(areaObj.country);

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

        $(document).on('click', '.removeCart', removeCart);
        $(document).on('click', '.cartToFavourite', cartToFavourite);

        //删除该条
        function removeCart(event) {
            event.preventDefault();
            var that = $(this);
            var tr = that.parents('tr');

            $.ajax({
                type: 'get',
                url: Prd.baseUrl + '/shoppingCart/ajaxRemoveItemFromOrder',
                data: {
                    productId: tr.attr('data-value')
                },
                success: function(param) {
                    if (param.success == 1) {
                        tr.fadeOut(200).remove();
                        $(".order-sub-total").text(param.data.subtotalDisplay);
                        $(".order-total").text(param.data.totalDisplay);
                    }
                }
            });
        }
        //移入收藏
        function cartToFavourite(event) {
            event.preventDefault();
            var that = $(this);
            var tr = that.parents('tr');

            $.ajax({
                type: 'get',
                url: Prd.baseUrl + '/myAccount/favouriteItemFromCart',
                data: {
                    productId: tr.attr('data-value')
                },
                success: function(param) {
                    if (param.success == 1) {
                        tr.fadeOut(200).remove();
                        $(".order-sub-total").text(param.data.subtotalDisplay);
                        $(".order-total").text(param.data.totalDisplay);
                    }
                }
            });
        }
    });
});

