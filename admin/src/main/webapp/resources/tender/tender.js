//表单对象模型 - 经个人实践得出- 必须给初值
var newUser = function(){
    this.name = "";
}


//modelandview
var app = new Vue({
    el: '#app',
    data: {
        error : '',
        newUser: newUser
    },
    ready:function(){
        //定义错误实体
        var validation = new newUser();
        this.$data.error = validation;
        //绑定显示错误信息的对象
        this.volidateEntity = validation;
        //绑定正则
        var regex = {
            "newUser.name" : regex_username
        }
        this.regexEntity = regex;
    },
    methods: {
        ajaxSubmit: function () {
            //第一个参数是对象（值），第二个参数是方法
            app.submitVolidata(this.$data.newUser, newUser);
        },
        volidate: function (event) {
            app.excuteVolidata(event);
        }
    }
})
