/**
 * Created by supersoup on 16/3/24.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        vue: '../core/js/vue',
        normalInit: '../core/js/module/normalInit'
    }
});

require(['jquery', 'component', 'ajax', 'vue', 'normalInit'], function ($, Cpn, Ajax, Vue) {
    $(document).ready(function () {
        $('.filter-item').mouseenter(function () {
            $(this).addClass('filter-item-hover');
        }).mouseleave(function () {
            $(this).removeClass('filter-item-hover');
        })

        $(".page-link").click(function () {
            var currenthref =  $(this).attr("href");
            $(this).attr("href",currenthref + filter_obj);
        })
    });
});