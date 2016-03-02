/**
 * Created by carlwang on 2/26/16.
 */
/**
 * Created by supersoup on 16/2/17.
 */
 require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        handlebar: '../core/js/handlebars-v4.0.5',
        ajax: '../core/js/module/ajax'
    }
});

 require(['jquery', 'handlebar', 'ajax'], function($, Handlebar, Ajax) {
     $(document).ready(function() {
        $('#main').on('click',function(event){
            alert(event);
        })

    });
 });