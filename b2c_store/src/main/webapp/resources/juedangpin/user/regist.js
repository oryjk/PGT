/**
 * Created by carl on 15/10/30.
 */



require.config({
    paths: {
        jquery: '../core/js/jquery.min'
    }
});

require(['jquery'], function($) {

    $('#registSub').addClass('disable');

    //注册状态对象

    var registInfo = {
        num: 60,
        userphoneFlg: true,
        passwordFlg: true,
        confirmFlg: true,
        phoneComFlg: true,
        agreeFlg: true,
        codeFlg: true,
        phoneCom: '',
        code: ''
    };

    testAgree();
    //$('#username').on('blur', testUsername);
    $('#userPhone').on('blur', testUserPhone);
    $('#confirm').on('blur', testConfirm);
    $('#password').on('blur', testPassword);
    $('#getPhoneCom').on('click', getPhoneCom);
    $('#agree').on('change', testAgree);
    $('#registSub').on('click', registSubmit);
    $('#authImg').on('click', getCode);



    function registSubmit(event) {
        event.preventDefault();

        testUserPhone();
        testConfirm();
        testPassword();
        testAgree();

        if (registInfo.userphoneFlg
            && registInfo.passwordFlg
            && registInfo.confirmFlg
            && registInfo.phoneComFlg
            && registInfo.agreeFlg)
        {
            $('#regist').get(0).submit();
        }
    }

    function testUserPhone() {
        $('#userPhonePrompt').text('');
        registInfo.usernameFlg = false;

        var str = $('#userPhone').val();
        if (/1\d{10}/.test(str) && str.length == 11) {
            registInfo.usernameFlg = true;
        } else {
            $('#userPhonePrompt').text('格式不正确');
        }
    }

    function testPassword() {
        $('#passwordPrompt').text('');
        registInfo.passwordFlg = false;

        var str = $('#password').val();
        if (str == '') {
            //空
            $('#passwordPrompt').text('密码不能为空！');
        } else if (str.length < 6) {
            //密码太短
            $('#passwordPrompt').text('密码太短！');
        } else if (/[^\d\w$]/.test(str)) {
            //特殊字符
            $('#passwordPrompt').text('不能包含标点！');
        } else {
            //密码可用
            $('#passwordPrompt').text('密码可用！');
            registInfo.passwordFlg = true;
        }
    }

    function testConfirm() {
        $('#confirmPrompt').text('');
        registInfo.confirmFlg = false;

        var pwdStr = $('#password').val();
        var conStr = $('#confirm').val();
        if (pwdStr == '') {
            //pwdStr wei kong
            $('#confirmPrompt').text('密码为空');
        } else if (pwdStr != conStr) {
            $('#confirmPrompt').text('密码不一致');
        } else if (pwdStr == conStr) {
            $('#confirmPrompt').text('输入正确！');
            registInfo.confirmFlg = true;
        }
    }

    function testAgree() {
        registInfo.agreeFlg = false;
        $('#registSub').prop('disabled', true);
        if ($('#agree').prop('checked')) {
            registInfo.agreeFlg = true;
            $('#registSub').prop('disabled', false)
                .removeClass('disable');
        } else {
            registInfo.agreeFlg = false;
            $('#registSub').prop('disabled', true)
                .addClass('disable');
        }
    }

    function getCode() {
        $('#loginCode').attr('src','');
        $('#loginCode').attr('src','/dianjinzi/code');
    }

    function getPhoneCom() {
        $('#getPhoneCom').val('请稍等...')
            .addClass('disable')
            .prop('disabled', true);

        //发送ajax请求，在成功后调用以下setTimeout()
        $.ajax({
            type: 'get',
            url: $('#smsPath').attr('data-value'),
            data: {
                phoneNumber: $('#userPhone').val()
            },
            success: function() {
                setTimeout(numDown, 1000);
            }
        });
    }

    function numDown() {
        registInfo.num--;
        $('#getPhoneCom').val(registInfo.num + '秒');
        if (registInfo.num > 0) {
            setTimeout(numDown, 1000);
        } else {
            //结束循环.
            registInfo.num = 60;
            $('#getPhoneCom').val('获取')
                .removeClass('disable')
                .prop('disabled', false);
        }
    }

});