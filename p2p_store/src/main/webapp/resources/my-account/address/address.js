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
                    name: '',
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
                    id: ''
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
                isUpdate: false,
                isValid: true,
                errorMsg: ''
            },

            methods: {
                closePop: function (event) {
                    this.display = 'none';
                    this.currentAddress = '';
                },
                queryCityByProvinceId: function (provinceId, name, event) {
                    this.currentAddress.province = name;
                    this.province_display = 'none';

                    $.ajax({
                        url: '/getCityByProvinceId/' + provinceId,
                        success: function (response) {
                            popUp.cities = JSON.parse(response);
                        }
                    })
                },
                getAreaByCityId: function (cityId, name, event) {
                    this.currentAddress.city = name;
                    this.city_display = 'none';
                    $.ajax({
                        url: '/getAreaByCityId/' + cityId,
                        success: function (response) {
                            popUp.districts = JSON.parse(response);
                        }
                    })
                },
                saveDistrict: function (districtId, name, event) {
                    this.currentAddress.district = name;
                    this.district_display = 'none';
                },
                validate: function () {
                    if (this.currentAddress.name == '') {
                        this.errorMsg = '收货人不能为空';
                        this.isValid = false;
                        return;
                    }
                    if (this.currentAddress.province == '' || this.currentAddress.province == '请选择') {
                        this.errorMsg = '请选择省份';
                        this.isValid = false;
                        return;
                    }
                    if (this.currentAddress.city == '' || this.currentAddress.city == '请选择') {
                        this.errorMsg = '请选择所属城市';
                        this.isValid = false;
                        return;
                    }
                    if (this.currentAddress.district == '' || this.currentAddress.district == '请选择') {
                        this.errorMsg = '请选择所属区县';
                        this.isValid = false;
                        return;
                    }
                    if (this.currentAddress.address == '') {
                        this.errorMsg = '请输入详细地址';
                        this.isValid = false;
                        return;
                    }
                    if (this.currentAddress.phone == '') {
                        this.errorMsg = '请输入手机号码';
                        this.isValid = false;
                        return;
                    }
                    if (this.currentAddress.phone.match('^[0-9]{11}$') == null) {
                        this.errorMsg = '请输入合法的手机号码';
                        this.isValid = false;
                        return;
                    }

                    this.isValid = true;
                },
                saveAddress: function () {
                    this.validate();
                    if (this.isValid) {
                        $.ajax({
                            url: this.saveUrl,
                            data: this.currentAddress,
                            type: 'POST',
                            success: function (response) {
                                console.log('success:' + response.success);
                                popUp.display = 'none';
                                window.location.href = '/my-account/person-info/address';
                            }
                        })
                    }

                }
            },
            computed: {
                saveUrl: function () {
                    if (this.isUpdate) {
                        return '/my-account/person-info/updateAddress/' + this.currentAddress.id;
                    }

                    return '/my-account/person-info/addAddress';
                },
                title: function () {
                    if (this.isUpdate) {
                        return "更新收货地址";
                    }
                    return "新增收货地址";

                }
            }

        })
        //in debug mode.
        window.popUp = popUp;

        var main = new Vue({
            el: '#main',
            data: {},
            methods: {
                setDefault: function (id, event) {
                    $.ajax({
                        url: '/my-account/person-info/setDefaultAddress/' + id,
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
                edit: function (id, event) {
                    popUp.isUpdate = true;
                    $.ajax({
                        url: '/my-account/person-info/findAddress/' + id,
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
                },
                createAddress: function () {
                    popUp.isUpdate = false;
                    popUp.display = 'block';
                    popUp.currentAddress = {
                        'name': '',
                        'province': '请选择',
                        'city': '请选择',
                        'district': '请选择',
                        'address': '',
                        'phone': '',
                        'telephone': '',
                        'email': '',
                        'id': ''
                    };
                },
                deleteAddress: function (addressId) {
                    if (window.confirm('你确定要删除本地址吗？')) {
                        $.ajax({
                            url: '/my-account/person-info/deleteAddress/' + addressId,
                            type: 'POST',
                            dataType: 'json'
                        })
                            .done(function (response) {
                                window.location.href = '/my-account/person-info/address';
                            })
                            .fail(function () {
                                console.log("error");
                            })
                            .always(function () {
                                console.log("complete");
                            });
                        return true;
                    } else {
                        return false;
                    }


                }
            }
        })

    });
})
