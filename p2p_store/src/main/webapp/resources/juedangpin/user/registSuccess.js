/**
 * Created by supersoup on 15/12/21.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min'
    }
});

require(['jquery'], function($) {

    $(document).ready(function() {
        //注册状态对象
        var success = {
            time: 5
        };

        numDown5();

        function numDown5() {
            success.time--;
            $('#waitTime').html(success.time);
            if (success.time > 0) {
                setTimeout(numDown5, 1000);
            } else {
                //结束循环.
                window.location = ''
            }
        }
    });

});