/**
 * Created by supersoup on 16/3/22.
 */
define(['jquery'], function ($) {
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
    }).mouseleave(function () {
        var $this =$('.nav-item').eq(0),
            w = $this.innerWidth(),
            h = $this.innerHeight();
        $('#navHover').css({
            top:0,
            left:0,
            width: w + 'px',
            height: h + 'px'
        });
    });

    var menuList = $('.header .menu-list');
    $('.header .menu').mouseenter(function () {
        menuList.slideDown(300);
    }).mouseleave(function () {
        menuList.slideUp(300);
    })
});