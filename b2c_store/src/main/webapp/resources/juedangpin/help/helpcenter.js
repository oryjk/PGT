require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component'
    }
});

require(['jquery', 'component'], function($, Cpn) {

    $(document).ready(function() {

        //菜单折叠
        Cpn.foldToggle($('.menu-level-1'));



    });

});