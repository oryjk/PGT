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

require(['jquery', 'handlebar', 'ajax', 'underscore', 'vue', 'component'], function ($, Handlebars, Ajax, _, Vue, Con) {
    $(document).ready(function () {

        Handlebars.registerHelper('isTrue', function (status, exceptStatus) {
            if (status == exceptStatus) {
                return true;
            }
            return false;
        });
        Handlebars.registerHelper('pagination', function (currentPage, exceptPage))
        {
            if (currentPage < exceptPage) {
                return true;
            }
            return false;
        }

        Handlebars.registerHelper("compare", function (v1, v2, options) {
            if (v1 > v2) {
                //满足添加继续执行
                return options.fn(this);
            } else {
                //不满足条件执行{{else}}部分
                return options.inverse(this);
            }
        });
        var vm = new Vue({
            el: '#mainContent',
            data: {
                sort: Con.getParamValue(window.location.href, 'sort') != null ? Con.getParamValue(window.location.href, 'sort') : '',
                page: Con.getParamValue(window.location.href, 'page') != null ? Con.getParamValue(window.location.href, 'page') : '',
                keyword: Con.getParamValue(window.location.href, 'keyword') != null ? Con.getParamValue(window.location.href, 'keyword') : '',
                tenderFilter: Con.getParamValue(window.location.href, 'tenderFilter') != null ? Con.getParamValue(window.location.href, 'tenderFilter') : '',
                cid: Con.getParamValue(window.location.href, 'cid') != null ? Con.getParamValue(window.location.href, 'cid') : ''
            },
            methods: {
                sortBy: function (event) {
                    event.preventDefault();
                    this.sort = $(event.target).attr('data-value') ? $(event.target).attr('data-value') : '';
                    sendRequest(vm);
                },
                tenderFilterAction: function (event) {
                    this.tenderFilter = $(event.target).attr('data-value') ? $(event.target).attr('data-value') : '';
                    sendRequest(vm);
                },
                paginationAction: function (event) {
                    this.page = $(event.target).attr('data-value') ? $(event.target).attr('data-value') : '';
                    $(event.target).addClass('current-page');
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
                    loadTenderList(response)
                })
                .fail(function () {
                    console.log("error");
                })
                .always(function () {
                    console.log("complete");
                });

        };

        function loadTenderList(data) {
            console.log(data);
            if (data != null) {
                $.ajax({
                    url: '/resources/product-list/tenders_template.html',
                    type: 'GET',
                    dataType: 'html',
                })
                    .done(function (response) {
                        console.log("success");
                        console.log(response);

                        var template = Handlebars.compile(response);
                        var html = template(data.data);
                        $('.products').html(html);
                    })
                    .fail(function () {
                        console.log("error");
                    })
                    .always(function () {
                        console.log("complete");
                    });

            }

        }

        console.log(vm);
    });
});