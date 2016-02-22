/**
 * Created by supersoup on 16/2/23.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax'
    }
});

require(['jquery', 'component', 'ajax'], function($, Cpn, Ajax) {

    $(document).ready(function() {
        //轮播图
        Cpn.slider({
            next: 1,
            imgBox: $('#bannerFrontEnd, #bannerBackEnd'),
            navBox: $('#bannerNav'),
            time: 600
        });
    });
});