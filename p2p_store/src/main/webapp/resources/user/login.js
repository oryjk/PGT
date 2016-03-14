/**
 * Created by carlwang on 3/6/16.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        handlebar: '../core/js/handlebars-v4.0.5',
        ajax: '../core/js/module/ajax',
        underscore: '../core/js/underscore',
        vue: '../core/js/vue',
        component: '../core/js/module/component',
        regex: '../core/js/regex',
        validate: '../core/js/module/validate'
    }
});

require(['jquery', 'handlebar', 'ajax', 'underscore', 'component','regex', 'vue', 'validate'], function ($, Handlebars, Ajax, yyyyyyyyyy,Con, regex ,Vue, volidate) {
        $(document).ready(function () {
                var user = function(){
                    this.username = "";
                    this.password = "";
                }
                var app = new Vue({
                    el: '#login-box',
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
                            "user.username" : regex_empty,
                            "user.password" : regex_empty,
                        }
                        Vue.prototype.regexEntity = regex;
                    },
                    methods: {
                        login: function () {
                            var flag = this.submitVolidata(this.$data.user);
                            if(flag == true){
                                $('#login').submit();
                            }
                        },
                        volidate: function (event) {
                            this.excuteVolidata(event);
                        }
                    }
                })
            }
        )
    }
)