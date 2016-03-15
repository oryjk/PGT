/**
 * Created by carlwang on 3/8/16.
 */

define(['vue','underscore','jquery'], function(Vue,_,$) {
    //绑定错误对象并取别名
    Vue.prototype.regexEntity = new Object();

    //绑定错误信息
    Vue.prototype.volidateEntity = new Object();

    //扩展验证
    Vue.prototype.excuteVolidata = function (event) {
        console.log(event);
        console.log(Vue.prototype.regexEntity);
        console.log(Vue.prototype.volidateEntity);

        var regexEntity = Vue.prototype.regexEntity;
        var volidateEntity = Vue.prototype.volidateEntity;

        var val = event.target.value;
        var v_model = event.target.__v_model.expression;

        for(var regex_key in regexEntity){
            if(regex_key == v_model){
                if (val.match(regexEntity[regex_key].regex) == null || val.length <= 0) {
                    event.target.style.border = "1px solid red";
                    for (var key in this.volidateEntity) {
                        if (v_model.match(key)) {
                            Vue.prototype.volidateEntity[key] = regexEntity[regex_key].error;
                        }
                    }
                }
                else{
                    event.target.style.border = "1px solid green";
                    for (var key in this.volidateEntity) {
                        if (v_model.match(key)) {
                            Vue.prototype.volidateEntity[key] = true;
                        }
                    }
                }
            }
        }
    };

    Vue.prototype.submitVolidata = function(funData){
        var regexEntity = Vue.prototype.regexEntity;
        console.log(funData);
        var i = 0;
        var length = 0;
        for(var v_model in funData){
            for(var regexEntityKey in regexEntity){
                if(regexEntityKey.split(".")[1] == v_model){
                    if(funData[v_model].match(regexEntity[regexEntityKey].regex) && funData[v_model].length > 0){
                        //样式为绿色边框
                        $("#" + v_model).css("border","1px solid green");
                        i++;
                    }
                    else{
                        //样式为红色边框
                        $("#" + v_model).css("border","1px solid red");
                        for (var key in this.volidateEntity) {
                            if (v_model == key) {
                                Vue.prototype.volidateEntity[key] = regexEntity[regexEntityKey].error;
                            }
                        }
                    }
                }
            }
            length ++;
        }
        if(i == length){
            //alert("提交表单");
            return true;
        }
    }
});
