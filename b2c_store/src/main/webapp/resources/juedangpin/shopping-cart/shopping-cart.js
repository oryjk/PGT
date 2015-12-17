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
    });

});