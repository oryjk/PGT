require.config({
    baseUrl: '../../my-account',
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax',
        vue: '../core/js/vue',
        underscore: '../core/js/underscore',
        normalInit: '../core/js/module/normalInit'
    }
});
require(['jquery', 'component', 'ajax', 'vue', 'underscore', 'normalInit'], function ($, Cpn, Ajax, Vue, _) {
    $(document).ready(function () {
        $(".link-dislike").on('click', function (event) {
            var target = event.target;
            if (target) {
                $.ajax({
                    type: 'POST',
                    url: '/myAccount/dislike',
                    data: {
                        'favouriteId': $(target).data('id')
                    },
                    success: function (data) {
                        if (data && data.success === 1) {
                            window.location.reload();
                        }
                    }
                });
            }
        });
    });
});
