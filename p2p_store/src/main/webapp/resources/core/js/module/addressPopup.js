/**
 * Created by carlwang on 3/16/16.
 */
/**
 * Created by carlwang on 3/13/16.
 */


define(['../core/js/vue'],function (Vue) {
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
            errorMsg: '',
            needRedirect:true
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
                            if(popUp.needRedirect){
                                window.location.href = '/my-account/person-info/address';
                            }
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

    });
    return popUp;

})


