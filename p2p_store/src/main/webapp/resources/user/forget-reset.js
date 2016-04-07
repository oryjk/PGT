/**
 * Created by zhangxiaodong on 16-3-1.
 */

require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        normalInit: '../core/js/module/normalInit'
    }
});

require(['jquery', 'normalInit'], function ($, normalInit) {
    var time;

    $('#phone-code').click(function () {
        var flag = 60;
        $('.input-row-3-1-td').css('display','block');
        $(this).val(flag + "秒后重新发送");
        $(this).addClass("get-phone-confirm-code-new");
        time = window.setInterval(function () {
            if (flag > 0) {
                flag-=1;
                $('#phone-code').val(flag + "秒后重新发送");
            }
            else {
                if (flag == 0) {
                    $('#phone-code').removeClass("get-phone-confirm-code-new");
                    $('.input-row-3-1-td').css('display','none');
                    $('#phone-code').val("获取手机验证码");
                    window.clearInterval(time);
                }
            }
        }, 1000);

    })
});