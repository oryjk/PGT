/**
 * Created by supersoup on 16/3/16.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        ajax: '../core/js/module/ajax'
    }
});

require(['jquery', 'component', 'ajax'], function($, Cpn, Ajax) {

    $(document).ready(function() {
        //轮播图
        Cpn.slider({
            next: 1,
            imgBox: $('#banner'),
            navBox: $('#bannerNav'),
            time: 600
        });

        //nav的hover效果
        $('.nav-item').mouseenter(function () {
            var $this =$(this),
            position = $this.position(),
            w = $this.innerWidth(),
            h = $this.innerHeight();
            $('#navHover').css({
                top: position.top + 'px',
                left: position.left +  'px',
                width: w + 'px',
                height: h + 'px'
            });
        });
        $('.nav-item').mouseout(function () {
            var $this =$('.nav-item').eq(0),
            w = $this.innerWidth(),
            h = $this.innerHeight();
            $('#navHover').css({
                top:0,
                left:0,
                width: w + 'px',
                height: h + 'px'
            });
        })
    });


});