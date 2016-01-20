$(document).ready(function(){

    $("#getPhoneCom").click(function(){
        var code = $("#phoneNumber").val();
        $.ajax({
            type:'get',
            url:'/mobile/sms/register',
            data: "phoneNumber=" + code,
            dataType:'json',
            success:function(data){
                alert("获取验证码成功");
            },
            error:function(){
                alert("获取验证码失败");
            }
         });

});
});