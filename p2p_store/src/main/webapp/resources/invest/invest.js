/**
 * Created by supersoup on 16/2/17.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        vue: '../core/js/vue',
        regex: '../core/js/regex',
        validate: '../core/js/module/validate',
        underscore: '../core/js/underscore'
    }
});

require(['jquery', 'component', 'ajax','vue','regex','validate'], function($, Cpn, Ajax, Vue, Regex, Volidate) {

    $(document).ready(function() {
        var user = function(){
            this.username = "";
        }
        var app = new Vue({
            el: '#form',
            data: {
                error : '',
                user: user
            },
            ready:function(){
                var validation = new user();
                this.$data.error = validation;
                //绑定显示错误信息的对象
                Vue.prototype.volidateEntity = validation;
                //绑定正则
                var regex = {
                    "user.username" : regex_empty
                }
                Vue.prototype.regexEntity = regex;
            },
            methods: {
                formSubmit: function () {
                    var flag = this.submitVolidata(this.$data.user);
                    if(flag == true){
                        $('.invest-submit').submit();
                    }
                },
                volidate: function (event) {
                    this.excuteVolidata(event);
                }
            }
        });

        $(".auth-code-btn").click(function(){
          $.ajax({
              type: 'get',
              url: $('#smsPath').attr('data-value'),
              data: {
                  phoneNumber: $('#userPhone').val()
              },
              success: function() {
                  setTimeout(numDown60, 1000);
              }
          });
         });

        //为仿select绑定事件
        var selectBox = $('.invest-gender-select,.invest-type-select');
        Cpn.select(selectBox.find('.select-view'), selectBox.find('.option-view'));

        $(".option-view").click(function(){
            var value =$(this).attr("data-value");
            $(this).parent().parent().next().val(value);
        });

        //定义省市县三级联动.
        (function () {
            var areaObj = {
                province: $('#province'),
                city: $('#city'),
                country: $('#country')
            };
            //选省获市
            Cpn.select2(areaObj.province, function (id) {
                var url = Ajax.baseUrl + '/getCityByProvinceId/' + id;
                $.ajax({
                    url: url,
                    data: null,
                    success: function (param) {
                        var str = '';
                        $.each($.parseJSON(param), function () {
                            str += '<li><a class="option-view" data-value="' + this.id + '" href="#">' + this.name + '</a></li>'
                        });
                        $('#city').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
                        $('#country').children('.selected').html('请选择').end().siblings('.options').html('').next('.select-value').val('');
                    }
                })
            });
            //选市获县
            Cpn.select2(areaObj.city, function (id) {
                var url = Ajax.baseUrl + '/getAreaByCityId/' + id;
                $.ajax({
                    type: 'get',
                    url: url,
                    data: null,
                    success: function (param) {
                        var str = '';
                        $.each($.parseJSON(param), function () {
                            str += '<li><a class="option-view" data-value="' + this.id + '" href="#">' + this.name + '</a></li>'
                        });
                        $('#country').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
                    }
                })
            });
            //县的下拉
            Cpn.select2(areaObj.country);
        }());

    });
});