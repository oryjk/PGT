/**
 * Created by supersoup on 15/12/2.
 */
require.config({
    paths: {
        jquery: '../../core/js/jquery.min',
        component: '../../core/js/module/component'
    }
});

require(['jquery', 'component'], function($, Cpn) {
    $(document).ready(function() {

        //横向商品列表点击左右移动
        Cpn.rowList({
            list: $('#rowList'),
            left: $('#moveRight'),
            right: $('#moveLeft')
        });

        //为仿select绑定事件
        Cpn.select($('.select-view'), $('.option-view'));

    });
});