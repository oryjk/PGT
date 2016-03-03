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
        component: '../core/js/module/component'
    }
});

require(['jquery', 'handlebar', 'ajax', 'underscore', 'component'], function ($, Handlebar, Ajax, _, Con) {
    $(document).ready(function () {
       var requestParam={}
        $('.page-box ol li').on('click', function (event) {
            event.preventDefault();
            var tenderListParamObj = new TenderListParam();
            tenderListParamObj.sort=$('.title3 .current').attr('sort-value');
            tenderListParamObj.sort=$('.title3 .current').attr('sort-value');
            requestParam.page=$(this).children('a').html();
            requestParam.keyword=$('.search input').val();
            $('.statusFilter input')
        });
    });
});