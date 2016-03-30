/**
 * Created by zhangxiaodong on 16-2-26.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        jqzoom: '../core/js/jquery.jqzoom-core',
        radialindicator: '../core/js/radialindicator',
        normalInit: '../core/js/module/normalInit'

    },
    shim: {
        jqzoom: ['jquery'],
        radialindicator: ['jquery']
    }
});

require(['jquery', 'component', 'ajax', 'jqzoom', 'radialindicator', 'normalInit'], function ($, Cpn, Ajax) {
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

        //与他联系弹窗效果
        (function () {
            var pop = $('#popUp');
            var close = $('#popClose, #popReset');
            var popSubmit = $('#popSubmit');
            var sayPhone = $('#sayPhone');
            var sayText = $('#sayText');
            var sayError = $('#sayError');
            var popFormDom = document.getElementById('popForm');
            var popTips = $('#popTips');
            var userId;

            $(document).on('click', '.touch-him', function (event) {
                event.preventDefault();
                popFormDom.reset();
                popFormCleanTips();

                pop.fadeIn(300);
            });
            close.click(function () {
                pop.fadeOut(300);
            });
            pop.click(function (event) {
                if (event.target == this) {
                    pop.fadeOut();
                }
            });
            sayPhone.focus(function () {
                popFormCleanTips()
            });
            popSubmit.click(function () {
                if (/1[0-9]{10}$/.test(sayPhone.val())) {
                    //请在此处写上正确的ajax方法
                    $.ajax({
                        data: {
                            phone: sayPhone.val(),
                            text: sayText.val(),
                            userId: userId
                        }
                    }).done(function (res) {
                        if (res.flag == 1) {
                            popTips.html('提交成功');
                            setTimeout(function () {
                                pop.fadeOut(300);
                            },1000);
                        } else {
                            popTips.html('提交失败');
                        }
                    })
                } else {
                    sayError.html('请输入正确的手机号!')
                }
            });

            //清楚回显和错误提示
            function popFormCleanTips() {
                sayError.html('');
                popTips.html('')
            }
        }());








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
                if ($(target).data('processed') == false) {
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
