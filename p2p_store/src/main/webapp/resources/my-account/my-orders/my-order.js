/**
 * Created by supersoup on 16/3/29.
 */
require.config({
    paths: {
        jquery: '/resources/core/js/jquery.min',
        component: '/resources/core/js/module/component',
        ajax: '/resources/core/js/module/ajax',
        vue: '/resources/core/js/vue',
        underscore: '/resources/core/js/underscore',
        normalInit: '/resources/core/js/module/normalInit'
    }
});

require(['jquery', 'component', 'ajax', 'vue', 'underscore', 'normalInit'], function ($, Cpn, Ajax, Vue, _) {
    $(document).on('click', '.watch-detail', function (event) {
        event.preventDefault();
        $('.order-detail-pop-content').load('/myAccount/orderHistoryDetails?orderId=' + $(this).data("order-id"));
        $('.order-detail-pop').fadeIn(300);
    });

    $(document).on('click', '.close', function () {
        $('.order-detail-pop').fadeOut(300);
    });

    $(function () {
        var h = $(document).scrollTop();
        var wrap = $(".order-detail-wrap");
        var ctn = $('.order-detail-pop-content');
        wrap.scroll(function () {
            var scroll_position = wrap.scrollTop();
            var height = ctn.height() - wrap.height();
            if (scroll_position >= height) {
                wrap.scrollTop(height - 1);
                $(document).scrollTop(h);
            }
            if (scroll_position <= 0) {
                wrap.scrollTop(1);
                $(document).scrollTop(h);
            }
        });
        $(document).scroll(function () {
            h = $(document).scrollTop();
        })
    });

});