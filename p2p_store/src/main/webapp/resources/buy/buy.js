/**
 * Created by supersoup on 16/3/15.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        addressPopup: '../core/js/module/addressPopup',
        vue: '/resources/core/js/vue'
    }
});

require(['jquery', 'component', 'ajax', 'addressPopup', 'vue'], function ($, Cpn, Ajax, addressPopup, Vue) {
    var pop = $('#popUp');
    Cpn.pop({
        popUp: pop,
        close: $('#popReset, #popClose')
    });

    $(document).ready(function () {
            Vue.config.debug = true;
            addressPopup.redirectUrl = '/checkout/shipping?orderId=' + addressPopup.orderId;
            new Vue({
                el: '#order-info',
                data: {
                    defaultAddressId: ''
                },
                methods: {
                    setOrderAddress: function (shippingId, orderId, event) {
                        var orderInfoObj = this;
                        $.ajax({
                            url: '/checkout/addAddressToOrder/',
                            data: {
                                "addressInfoId": shippingId,
                                "orderId": orderId
                            },
                            type: 'POST',
                            success: function (response) {
                                orderInfoObj.receive_choose = '';
                                orderInfoObj.defaultAddressId = shippingId;
                            }
                        })
                    },
                    edit: function (addressId, event) {
                        addressPopup.isUpdate = true;
                        $.ajax({
                            url: '/my-account/person-info/findAddress/' + addressId,
                            type: 'GET',
                            dataType: 'json'
                        })
                            .done(function (response) {
                                addressPopup.currentAddress = JSON.parse(response.data);
                                addressPopup.display = 'block';
                            })
                            .fail(function () {
                                console.log("error");
                            })
                            .always(function () {
                                console.log("complete");
                            });
                    },
                    deleteAddress: function (addressId) {
                        if (window.confirm('你确定要删除本地址吗？')) {
                            $.ajax({
                                url: '/my-account/person-info/deleteAddress/' + addressId,
                                type: 'POST',
                                dataType: 'json'
                            })
                                .done(function (response) {
                                    window.location.href = '/checkout/shipping?orderId=' + addressPopup.orderId;
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
            });
            $('#addNewAddress,.address-have-no-add').click(function () {
                addressPopup.currentAddress = {
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
                addressPopup.needRedirect = false;
                addressPopup.display = 'block';
            });
        }
    )

});