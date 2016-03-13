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
        var statusMyAccount = $('#statusMyAccount');
        var statusMyAccountDetail = $('#statusMyAccountDetail');
        var bannerBox = $('#bannerBox');
        var menu = $('#menu');

        //轮播图
        Cpn.slider({
            next: 1,
            imgBox: $('#bannerBackEnd'),
            navBox: $('#bannerNav'),
            time: 600
        });

        statusMyAccount.mouseenter(function () {
            statusMyAccountDetail.slideDown();
        });
        statusMyAccount.mouseleave(function () {
            statusMyAccountDetail.slideUp();
        });

    });


});