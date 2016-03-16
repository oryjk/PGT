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
        pop.fadeIn(300);

    });
    $(document).on('click', '.receive-modify', function () {
        pop.fadeIn(300);
    });

    $(document).ready(function () {
            Vue.config.debug = true;
            new Vue({
                el: '#order-info',
                data: {
                    'defaultAddressId': '',
                    'ok':true

                },
                methods: {
                    setOrderAddress: function (shippingId, orderId, event) {
                        var orderInfoObj=this;
                        $.ajax({
                            url: '/checkout/addAddressToOrder/',
                            data: {
                                "addressInfoId": shippingId,
                                "orderId": orderId
                            },
                            type: 'POST',
                            success: function (response) {
                                orderInfoObj.receive_choose='';
                                orderInfoObj.defaultAddressId=shippingId;
                            }
                        })
                    }
                }
            })
        }
    )

});