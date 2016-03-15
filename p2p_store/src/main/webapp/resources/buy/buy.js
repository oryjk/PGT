/**
 * Created by supersoup on 16/3/15.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax'
    }
});

require(['jquery', 'component', 'ajax'], function ($, Cpn, Ajax) {
    var pop = $('#popUp');
    Cpn.pop({
        popUp: pop,
        close: $('#popReset, #popClose')
    });
    $('#addNewAddress,.address-have-no-add').click(function () {
        pop.fadeIn(300);
    });
    $(document).on('click', '.receive-modify', function () {
        pop.fadeIn(300);
    });


    var areaObj = {
        province: $('#province'),
        city: $('#city'),
        country: $('#country')
    };
    //省->市
    Cpn.select2(areaObj.province, function(id) {
        var url = Prd.baseUrl + '/getCityByProvinceId/' + id;
        $.ajax({
            url: url,
            data: null,
            success: function(param) {
                var str = '';
                $.each($.parseJSON(param), function() {
                    str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
                });
                $('#city').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
                $('#country').children('.selected').html('请选择').end().siblings('.options').html('').next('.select-value').val('');
            }
        })
    });

    //市->区
    Cpn.select2(areaObj.city, function(id) {
        var url = Prd.baseUrl + '/getAreaByCityId/' + id;
        $.ajax({
            type: 'get',
            url: url,
            data: null,
            success: function(param) {
                var str = '';
                $.each($.parseJSON(param), function() {
                    str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
                });
                $('#country').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
            }
        })
    });

    //区
    Cpn.select2(areaObj.country);
});