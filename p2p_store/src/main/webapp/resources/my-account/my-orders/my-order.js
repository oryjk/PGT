/**
 * Created by supersoup on 16/3/29.
 */
require.config({
    baseUrl: '../../my-account',
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        vue: '../core/js/vue',
        underscore: '../core/js/underscore',
        normalInit: '../core/js/module/normalInit'
    }
});

require(['jquery', 'component', 'ajax', 'vue', 'underscore', 'normalInit'], function ($, Cpn, Ajax, Vue, _) {
    $(document).on('click', '.watch-detail', function (event) {
        event.preventDefault();
        $('.order-detail-pop').fadeIn(300);
        $('.order-detail-pop-content').load('./order-detail.html');
    });

    $(document).on('click', '.close', function() {
        $('.order-detail-pop').fadeOut(300);
    });

    $(function(){
        var h = $(document).scrollTop();
        var wrap = $(".order-detail-wrap");
        var ctn = $('.order-detail-pop-content');
        wrap.scroll(function() {
            var scroll_position = wrap.scrollTop();
            var height = ctn.height() - wrap.height();
            if(scroll_position >= height){
                wrap.scrollTop(height -1);
                $(document).scrollTop(h);
            }
            if(scroll_position <= 0){
                wrap.scrollTop(1);
                $(document).scrollTop(h);
            }
        });
        $(document).scroll(function() {
            h = $(document).scrollTop();
        })
    });

});