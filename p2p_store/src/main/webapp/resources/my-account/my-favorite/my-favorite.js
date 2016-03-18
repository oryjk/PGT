require.config({
    paths: {
        jquery: '/resources/core/js/jquery.min',
        component: '/resources/core/js/module/component',
        ajax: '/resources/core/js/module/ajax',
        vue: '/resources/core/js/vue',
        underscore: '/resources/core/js/underscore'
    }
});
require(['jquery', 'component', 'ajax', 'vue', 'underscore'], function ($, Cpn, Ajax, Vue, _) {
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
