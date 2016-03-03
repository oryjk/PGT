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
        vue: '../core/js/vue',
        component: '../core/js/module/component'
    }
});

require(['jquery', 'handlebar', 'ajax', 'underscore', 'vue', 'component'], function ($, Handlebars, Ajax, _, Vue, Con) {
    $(document).ready(function () {

        $(document).on('mouseenter', '.menu-level-1', function() {
            $(this).children('.menu-2').slideDown(300);
        });

        $(document).on('mouseleave', '.menu-level-1', function() {
            $(this).children('.menu-2').slideUp(300);
        });

        Handlebars.registerHelper('isTrue', function (status, exceptStatus) {
            if (status == exceptStatus) {
                return true;
            }
            return false;
        });
        Handlebars.registerHelper('pagination', function (currentPage, exceptPage) {
            if (currentPage < exceptPage) {
                return true;
            }
            return false;
        });

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
                queryRequest: {
                    sort: Con.getParamValue(window.location.href, 'sort') != null ? Con.getParamValue(window.location.href, 'sort') : '',
                    page: Con.getParamValue(window.location.href, 'page') != null ? Con.getParamValue(window.location.href, 'page') : '',
                    keyword: Con.getParamValue(window.location.href, 'keyword') != null ? Con.getParamValue(window.location.href, 'keyword') : '',
                    tenderFilter: Con.getParamValue(window.location.href, 'tenderFilter') != null ? Con.getParamValue(window.location.href, 'tenderFilter') : '',
                    cid: Con.getParamValue(window.location.href, 'cid') != null ? Con.getParamValue(window.location.href, 'cid') : '',
                },
                ajax: false,
                isPre: false,
                isPost: false,
                isOther: false,
                pageList: '',
                pagination: ''
            },
            methods: {
                sortBy: function (event) {
                    event.preventDefault();
                    this.sort = $(event.target).attr('data-value') ? $(event.target).attr('data-value') : '';
                    vm.sendRequest(vm);
                },
                tenderFilterAction: function (event) {
                    this.tenderFilter = $(event.target).attr('data-value') ? $(event.target).attr('data-value') : '';
                    vm.sendRequest(vm);
                },
                paginationAction: function (event) {
                    if ($(event.target).attr('class') == 'pre') {
                        this.queryRequest.page = this.queryRequest.page == '' ? 1 : this.queryRequest.page - 1;
                        this.sendRequest(vm);
                        return;
                    }
                    if ($(event.target).attr('class') == 'next') {
                        this.queryRequest.page = this.queryRequest.page == '' ? 2 : this.queryRequest.page + 1;
                        this.sendRequest(vm);
                        return;
                    }
                    this.queryRequest.page = $(event.target).attr('data-value') ? $(event.target).attr('data-value') : '';
                    this.sendRequest(vm);

                },
                sendRequest: function () {
                    this.ajax = true;
                    var urlParam = Con.buildURLParamByJson(JSON.parse(JSON.stringify(vm.$data.queryRequest)));
                    window.history.pushState(null, null, '?' + urlParam);
                    $.ajax({
                        url: '/tender/ajaxTenderList',
                        type: 'GET',
                        dataType: 'json',
                        data: JSON.parse(JSON.stringify(vm.$data.queryRequest)),
                    })
                        .done(function (response) {
                            vm.loadTenderList(response)
                        })
                        .fail(function () {
                            console.log("error");
                        })
                        .always(function () {
                            console.log("complete");
                        });
                },
                loadTenderList: function (data) {
                    console.log(data);
                    if (data != null) {
                        this.renderTenders(data);
                        this.renderPagination(data);
                    }
                },
                renderTenders: function (data) {
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
                },
                renderPagination: function (data) {
                    var pagination = data.data.paginationBean;
                    this.pagination = pagination;
                    if (pagination.currentPage < 6) {
                        this.isPre = true;
                        this.isPost = false;
                        this.isOther = false;
                        if (pagination.totalPage > 7) {
                            this.pageList = 7;
                            return;
                        }
                        this.pageList = pagination.totalPage;
                    }
                    if (pagination.totalPage - pagination.currentPage < 3) {
                        this.isPost = true;
                        this.isPre = false;
                        this.isOther = false;
                        this.pageList = pagination.totalPage;
                        return;
                    }
                    this.isOther = true;
                    this.pageList = pagination.totalPage;
                    console.log(pagination);
                }

            }
        });


        console.log(vm);
    });
});