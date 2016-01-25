function getPhoneCom(phoneNumber){
    $.ajax({
        type:'get',
        url:'/mobile/sms/register',
        data: "phoneNumber=" + phoneNumber,
        dataType:'json',
        success:function(data){
            alert("获取验证码成功");
        },
        error:function(){
            alert("获取验证码失败");
        }
    });

}