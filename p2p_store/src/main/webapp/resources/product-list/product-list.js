/**
 * Created by zhoujialiang on 16/2/22.
 */
$(function(){
    $(".list").click(function() {
    var url = $(this).attr('path');
    $(".products").load(url);
        $(".list").css("background",'#f2f2f2');
        $(this).css("background",'white');
    });



});