$(function(){
    $("input:text").keyup(function(){
        var tenderTotal = $("#tenderTotal").val();
        var vue = new Vue();
        vue.addCheckElement("tenderTotal", tenderTotal, regex_tenderTotal);
    })

    $(".submit").click(function(){
        //同步
        onSubmit("登录失败，帐号或密码错误！");
        //异步
        //onSubmit("login","登录失败，帐号或密码错误！");
        //异步 + 跳转
        //onSubmit("login","登录失败，帐号或密码错误！", "main");
    })

    var vue = new Vue();
    var error = new Object();
    error.tenderTotal = "";
    vue.addErrorEntity(error);

    var view = new Vue({
        el: '#error',
        data: {
            model : error
        }
    })
})