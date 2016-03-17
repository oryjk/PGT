/**
 * Created by zhangxiaodong on 16-2-26.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        jqzoom: '../core/js/jquery.jqzoom-core',
        radialindicator: '../core/js/radialindicator'
    },
    shim: {
        jqzoom: ['jquery'],
        radialindicator: ['jquery']
    }
});

require(['jquery', 'component', 'ajax', 'jqzoom', 'radialindicator'], function ($, Cpn, Ajax) {
    $(document).ready(function () {

        $('.jqzoom').jqzoom({
            zoomType: 'standard',
            lens: true,
            preloadImages: false,
            alwaysOn: false,
            zoomWidth: 400,
            zoomHeight: 400,
            hideEffect: 'fadeout'
        });

        //左右移动.item-nav下的ul位置:
        var itemNavBox = $('#itemNavBox');
        $('#itemNavRight').click(function () {
            var oLeft = parseInt(itemNavBox.css('left'));
            if (0 - oLeft < itemNavBox.children().size() * 265 - 1060) {
                var newLeft = oLeft - 265;
                itemNavBox.css('left', newLeft + 'px');
            }
        });
        $('#itemNavLeft').click(function () {
            var oLeft = parseInt(itemNavBox.css('left'));
            if (oLeft < 0) {
                var newLeft = parseInt(itemNavBox.css('left')) + 265;
                itemNavBox.css('left', newLeft + 'px');
            }
        });

        $(".detail-content>ul>li").click(function (event) {
            event.preventDefault();

            $(".detail-content>ul>li").each(function () {
                $(this).removeAttr("class");
            });

            $(this).attr("class", "tab-choose");

            var tabindex = $(this).index();

            $(".content-box > div").each(function () {

                $(this).removeClass("content-choose");

                if (tabindex == $(this).index()) {
                    $(this).addClass("content-choose");
                }

            });
        });

        var setQuantity = function (obj, num) {
            $(obj).parent('.each-item').find('.quantity').val(num);
        };
        //add product quantity
        $(".item-buy-plus").click(function () {

            //get limit quantity
            var limitQuantity = $(this).parent().next().text();

            //get buy quantity
            var num = $(this).parent().prev().text();

            num++;
            if (num > limitQuantity) {
                alert("此商品购买的数量不能超过" + limitQuantity + "件");
                return;
            }
            //set buy quantity
            $(this).parent().prev().text(num);
            setQuantity(this, num);
        });


        //reduce product quantity


        $(".item-buy-minus").click(function () {

            var num = $(this).parent().prev().text();

            num--;

            if (num == 0) {
                return;
            }

            $(this).parent().prev().text(num);
            setQuantity(this, num);

        });

        $('.item-buy-now').on('click', function (event) {
            event.preventDefault();
            $(this).parent('.col-content').find('.addToCart').submit();
        });

        $('.invest-add-favorite, .item-join-favorite').on('click', function (event) {
            var target = event.target;
            if (target) {
                if ($(target).data('processed') != 'true') {
                    $.ajax({
                        type: 'POST',
                        url: '/myAccount/favourite',
                        data: {
                            'productId': $(target).data('pid'),
                            'type': $(target).data('type')
                        },
                        success: function (data) {
                            if (data && data.success === 1) {
                                $(target).data('id', data.data.id).data('processed', 'true').text('已收藏');
                            }
                        }
                    });
                } else {
                    $.ajax({
                        type: 'POST',
                        url: '/myAccount/dislike',
                        data: {
                            'favouriteId': $(target).data('id')
                        },
                        success: function (data) {
                            if (data && data.success === 1) {
                                $(target).data('id', '').data('processed', 'false').text('添加收藏');
                            }
                        }
                    });
                }
            }
        });
    });
});
