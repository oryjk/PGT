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
        component: '../core/js/module/component'
    }
});

require(['jquery', 'handlebar', 'ajax', 'underscore', 'vue', 'component'], function ($, Handlebars, Ajax, _, Vue, Con) {
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
                        error: ''
                    },
                    methods: {
                        login: function (event) {
                            if (this.username == '') {
                                console.log('The user name is empty.');
                                //need focus on user name input.
                                return;
                            }
                            if (this.password == '') {
                                console.log('The password is empty.');
                                //need focus on password input.
                                return;
                            }
                            if (this.needAuth) {
                                console.log('The auth number is empty.');
                                //need focus on password input.
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
                        },
                        qqLogin: function () {
                            QC.Login({'btnId': 'qqLoginBtn'});
                        }
                    }

                });


            }
        )
    }
)