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
        normalInit: '../core/js/module/normalInit'
    }
});

require(['jquery', 'handlebar', 'ajax', 'underscore', 'vue', 'component', 'normalInit'], function ($, Handlebars, Ajax, _, Vue, Con) {
        $(document).ready(function () {
                var vm = new Vue({
                    el: '#login-box',
                    data: {
                        username: '',
                        password: '',
                        authNum: '',
                        needAuth: false,
                        showUsernameError: '',
                        showPasswordError: '',
                        showAuthError: '',
                        usernameFocus: '',
                        passwordFocus: '',
                        authFocus: '',
                        errorMsg: '',
                        errorDisplay:'none'
                    },
                    methods: {
                        login: function (event) {
                            if (this.username == '') {
                                this.errorMsg='请输入用户名';
                                this.errorDisplay='block';
                                return;
                            }
                            if (this.password == '') {
                                this.errorMsg='请输入密码';
                                this.errorDisplay='block';
                                return;
                            }
                            if (this.needAuth) {
                                //need focus on password input.
                                this.errorDisplay='block';
                            }
                            $('#login').submit();
                        },
                        userOnFocus: function (event) {
                            this.usernameFocus = 'input-focus';
                            this.showUsernameError = '';
                            this.showPasswordError = '';
                            this.showAuthError = '';
                            this.passwordFocus = '';
                            this.authFocus = '';
                        },
                        passwordOnFocus: function (event) {
                            this.usernameFocus = '';
                            this.showUsernameError = '';
                            this.showPasswordError = 'input-focus';
                            this.showAuthError = '';
                            this.passwordFocus = '';
                            this.authFocus = '';
                        },
                        authOnFocus: function (event) {
                            this.usernameFocus = '';
                            this.showUsernameError = '';
                            this.showPasswordError = '';
                            this.showAuthError = '';
                            this.passwordFocus = '';
                            this.authFocus = 'input-focus';
                        }

                    }

                });
                $('#loading').hide();


            }
        )
    }
)