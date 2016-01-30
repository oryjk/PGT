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

//right-box
$(function(){

    var flag = 0;
    var status = "";
    $(".right1").click(function(event){
        event.preventDefault();

        var m_status = $(this).attr("title");
        if(status != m_status || flag == 0){
            $("#right").animate({right:"0"});
            var url = $(this).attr("path");
            status = $(this).attr("title");
            $("#right").show();
            $("#right-menu").load(url);
            flag = 1;
        }else if(flag == 1 && status == m_status){
            $("#right").animate({right:"-300px"});
            flag = 0;
        }

        return false;
    });

    $('#header, #content, #footer').click(function() {
        $("#right").animate({right:"-300px"});
        flag = 0;
    })
});


//$(function () {
//    var height = window.screen.height;
//    $(".right").css("height", height);
//});

$(function(){
    $(".content-top-f1").hide();
    $('.content').bind('mousewheel', function(event, delta) {
        $(".content-top-f1").show(1);
    });

    $('.banner-all').bind('mousewheel', function(event, delta) {
        $(".content-top-f1").hide(1);
    });
});