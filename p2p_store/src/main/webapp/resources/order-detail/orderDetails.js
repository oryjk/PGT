/**
 * Created by zhangxiaodong on 16-2-26.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        vue: '../core/js/vue'
    }
});

require(['jquery', 'component', 'ajax', 'vue'], function ($, Cpn, Ajax, Vue) {
    $(document).ready(function () {
        var shipping = new Vue({
            data: {},
            methods: {}
        })
    });
})
