/**
 * Created by carlwang on 2/26/16.
 */
/**
 * Created by supersoup on 16/2/17.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        handlebar: '../core/js/handlebars-v4.0.5',
        ajax: '../core/js/module/ajax',
        underscore: '../core/js/underscore',
        vue: '../core/js/vue.min',
        component: '../core/js/module/component'
    }
});

require(['jquery', 'handlebar', 'ajax', 'underscore', 'vue', 'component'], function ($, Handlebar, Ajax, _, Vue, Con) {
    $(document).ready(function () {


        var vm = new Vue({
            el: '#mainContent',
            data: {
                sort: Con.getParamValue(window.location.href, 'sort'),
                page: Con.getParamValue(window.location.href, 'page'),
                keyword: Con.getParamValue(window.location.href, 'keyword'),
                tenderFilter: Con.getParamValue(window.location.href, 'tenderFilter'),
                cid: Con.getParamValue(window.location.href, 'cid')
            },
            methods: {
                sortBy: function (event) {
                    event.preventDefault();
                    this.sort = $(event.target).attr('data-value');
                    sendRequest(vm);
                },
                tenderFilterAction: function (event) {
                    this.tenderFilter = $(event.target).attr('data-value');
                    sendRequest(vm);
                },
                paginationAction: function (event) {
                    this.page = $(event.target).attr('data-value');
                    sendRequest(vm);
                }
            }
        });

        function sendRequest(vm) {

            var urlParam = Con.buildURLParamByJson(JSON.parse(JSON.stringify(vm.$data)));
            window.history.pushState(null, null, '?' + urlParam);
            $.ajax({
                url: '/tender/ajaxTenderList',
                type: 'GET',
                dataType: 'json',
                data: JSON.parse(JSON.stringify(vm.$data)),
            })
                .done(function (response) {
                    console.log("success");
                    console.log(response);
                })
                .fail(function () {
                    console.log("error");
                })
                .always(function () {
                    console.log("complete");
                });

        };

        console.log(vm);
    });
});