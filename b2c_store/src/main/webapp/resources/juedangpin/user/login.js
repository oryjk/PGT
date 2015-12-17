/**
 * Created by carl on 15/10/30.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        product: '../core/js/module/product'
    }
});

require(['jquery', 'product'], function($, Prd) {
    //登陆信息
    var loginInfo = {
        times: $('#loginCount').val(),
        needAuth: false
    };

    if(loginInfo.times >= 3) {
        $('#authBox').removeClass('hide');
        loginInfo.needAuth = true;
    }


    //username获得、失去焦点
    $('#username')
        .focus(function() {
            $(this).parent().addClass('username-focus');
        })
        .blur(function() {
            $(this).parent().removeClass('username-focus');
        });
    //password获得、失去焦点
    $('#password')
        .focus(function() {
            $(this).parent().removeClass('password-wrong');
            $(this).parent().addClass('password-focus');
        })
        .blur(function() {
            $(this).parent().removeClass('password-focus');
        });

    //提交登陆信息
    $('#loginSub').click(function(event) {
        event.preventDefault();

        if (loginInfo.needAuth) {
            if ($('#authNum').val() != '') {
                $('#login').get(0).submit();
            } else {
                $('#loginPrompt').removeClass('hide');
            }
        } else {
            $('#login').get(0).submit();
        }
    });

    $(document).on('click', '#loginCode', function() {
        $(this).attr('src','');
        $(this).attr('src', Prd.baseUrl + '/code/login');
        //$('#authBox').html(' ')
        //    .html('<input id="authNum" name="authNum" type="text"/> <img id="loginCode" src="'+Prd.baseUrl+'/code/login" alt=""/> <a href="#">看不清楚？</a>')
    });
    //
    ////登陆提交
    //function login() {
    //
    //    $({
    //        type: 'post',
    //        url: 'login',
    //        data: $('form').serialize(),
    //        success: function(param) {
    //            if (param.message == 1) {
    //                alert('登陆成功');
    //                window.location = '../index/index.html';
    //            } else if (param.message == 0) {
    //                alert('登陆失败');
    //                $(this).parent().addClass('password-wrong');
    //                $('#loginPrompt').text('账号或密码错误！');
    //            }
    //
    //        }
    //    })
    //}
    //
    ////获取验证码
    //function getAuthNumber() {
    //    $({
    //        type: 'get',
    //        url: '/getAuthNumber',
    //        success: function(param) {
    //            //...
    //        }
    //    })
    //}
});