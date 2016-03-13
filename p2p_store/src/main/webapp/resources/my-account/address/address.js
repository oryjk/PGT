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
                    telephone: '',
                    userId: '',
                    phone: '',
                    telephone: '',
                    address: '',
                    email: '',
                    addressId: ''
                },
                newAddress: {
                    province: '',
                    city: '',
                    district: '',
                    address: '',
                    phone: '',
                    telephone: '',
                    userId: '',
                    phone: '',
                    telephone: '',
                    address: '',
                    email: ''
                },
                provinces: '',
                cities: '',
                districts: '',
                display: 'none',
                province_display: 'none',
                city_display: 'none',
                district_display: 'none',
                isUpdate: false
            },

            methods: {
                closePop: function (event) {
                    this.display = 'none';
                    this.currentAddress = '';
                },
                queryCityByProvinceId: function (provinceId, event) {
                    this.currentAddress.province = event.currentTarget.text;
                    this.province_display = 'none';

                    $.ajax({
                        url: '/getCityByProvinceId/' + provinceId,
                        success: function (response) {
                            popUp.cities = JSON.parse(response);
                        }
                    })
                },
                getAreaByCityId: function (cityId, event) {
                    this.currentAddress.city = event.currentTarget.text;
                    this.city_display = 'none';
                    $.ajax({
                        url: '/getAreaByCityId/' + cityId,
                        success: function (response) {
                            popUp.districts = JSON.parse(response);
                        }
                    })
                },
                saveDistrict: function (districtId, event) {
                    this.currentAddress.district = event.currentTarget.text;
                    this.district_display = 'none';
                },
                saveAddress: function () {
                    $.ajax({
                        url: this.saveUrl,
                        success: function (response) {
                            var response = JSON.parse(response);
                            console.log('success:' + response.success);
                            console.log('addedAddressId:' + response.addedAddressId);
                        }
                    })
                }
            },
            computed: {
                saveUrl: function () {
                    if (this.isUpdate) {
                        return '/my-account/person-info/updateAddress/' + this.currentAddress.currentAddress;
                    }

                    return '/my-account/person-info/addAddress';
                }
            }

        })
        //in debug mode.
        window.popUp = popUp;

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
