/**
 * Created by carlwang on 3/8/16.
 */

define(['vue','underscore'], function(Vue,_) {
    //绑定错误对象并取别名
    Vue.prototype.regexEntity = new Object();

    //绑定错误信息
    Vue.prototype.volidateEntity = new Object();

    //扩展验证
    Vue.prototype.excuteVolidata = function (event) {
        console.log(event)
        event.target.style.border = "1px solid red";
        console.log(Vue.prototype.regexEntity)
        console.log(Vue.prototype.volidateEntity)
    };
});
