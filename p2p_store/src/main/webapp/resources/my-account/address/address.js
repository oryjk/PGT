/**
 * Created by carlwang on 3/13/16.
 */

require.config({
    paths: {
        jquery: '/resources/core/js/jquery.min',
        component: '/resources/core/js/module/component',
        ajax: '/resources/core/js/module/ajax',
        vue: '/resources/core/js/vue',
        underscore: '/resources/core/js/underscore'
    }
});
require(['jquery', 'component', 'ajax', 'vue', 'underscore'], function ($, Cpn, Ajax, Vue, _) {
    $(document).ready(function () {
        var popUp = new Vue({
            el: '#popUp',
            data: {
                currentAddress: {
                    province: '请选择',
                    city: '请选择',
                    district: '请选择',
                    address: '',
                    phone: '',
                    telephone: ''
                },
                provinces: '',
                cities: '',
                district: '',
                display: 'none',
                province_display: 'none'
            },

            methods: {
                closePop: function (event) {
                    this.display = 'none';
                    this.currentAddress = '';
                },
                queryCityByProvinceId: function (provinceId, event) {
                    var url = '/getCityByProvinceId/' + provinceId;
                    $.ajax({
                        url: url,
                        data: null,
                        success: function (response) {
                            console.log(response);
                        }
                    })
                },
                showProvince: function () {
                    this.province_display='block';
                }
            }

        })


        var main = new Vue({
            el: '#main',
            data: {},
            methods: {
                setDefault: function (addressId, event) {
                    $.ajax({
                        url: '/my-account/person-info/setDefaultAddress/' + addressId,
                        type: 'POST',
                        dataType: 'json'
                    })
                        .done(function (response) {
                            window.location.href = response.redirectUrl;
                        })
                        .fail(function () {
                            console.log("error");
                        })
                        .always(function () {
                            console.log("complete");
                        });

                },
                edit: function (addressId, event) {
                    $.ajax({
                        url: '/my-account/person-info/findAddress/' + addressId,
                        type: 'GET',
                        dataType: 'json'
                    })
                        .done(function (response) {
                            popUp.currentAddress = JSON.parse(response.data);
                            popUp.display = 'block';
                        })
                        .fail(function () {
                            console.log("error");
                        })
                        .always(function () {
                            console.log("complete");
                        });
                }
            }
        })

    });
})
