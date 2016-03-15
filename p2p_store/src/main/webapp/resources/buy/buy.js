/**
 * Created by supersoup on 16/3/15.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax'
    }
});

require(['jquery', 'component', 'ajax'], function ($, Cpn, Ajax) {
    var pop = $('#popUp');
    Cpn.pop({
        popUp: pop,
        close: $('#popReset, #popClose')
    });
    $('#addNewAddress,.address-have-no-add').click(function () {
        pop.fadeIn(300);
    });
    $(document).on('click', '.receive-modify', function () {
        pop.fadeIn(300);
    })
});