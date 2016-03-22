/**
 * Created by supersoup on 16/3/16.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        normalInit: '../core/js/module/normalInit'
    }
});

require(['jquery', 'component', 'ajax', 'normalInit', 'index'], function($, Cpn, Ajax) {

    $(document).ready(function() {
        //轮播图
        Cpn.slider({
            next: 1,
            imgBox: $('#banner'),
            navBox: $('#bannerNav'),
            time: 600
        });

        $('.category-nav-item').mouseenter(function () {
            var $this = $(this);
            var eachCategory = $this.parents('.each-category');
            eachCategory.find('.category-nav-item').removeClass('category-nav-choose');
            $this.addClass('category-nav-choose');
            //输入具体的url
            eachCategory.find('.invest-list').load('/');
        });

        var pawnPointList = $('.pawn-point-item');
        var pawnNavList = $('.pawn-nav-item');
        pawnNavList.mouseenter(function () {
            var $this = $(this);
            var i = $this.attr('data-value');
            pawnNavList.removeClass('pawn-nav-current');
            $this.addClass('pawn-nav-current');
            pawnPointList.hide().eq(i).fadeIn(1000);
        })
    });
});