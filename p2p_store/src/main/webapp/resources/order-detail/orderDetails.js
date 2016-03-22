/**
 * Created by zhangxiaodong on 16-2-26.
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
        var shipping = new Vue({
            data: {},
            methods: {}
        })
    });
})
