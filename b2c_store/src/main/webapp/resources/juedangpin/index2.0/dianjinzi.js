/**
 * Created by zhoujialiang on 16/1/25.
 */
$(function(){
    $(".unit-hover").hide();
    $(".unit").mouseover(function(){
        $(this).attr("title","none")
        $(this).parent().children(".unit[title=box]").css("zoom","1.2");
        $(this).children(".unit-hover").show();
    });
    $(".unit").mouseout(function(){
        $(".unit").attr("title","box")
        $(".unit").css("zoom","1");
        $(this).children(".unit-hover").hide();
    });
});

$(function () {
    var height = $('body').height();
    $(".right").css("height", height);
});

$(function(){
    $(".content-top-f1").hide();
    $('.content').bind('mousewheel', function(event, delta) {
        $(".content-top-f1").show(1);
    });

    $('.banner-all').bind('mousewheel', function(event, delta) {
        $(".content-top-f1").hide(1);
    });
});