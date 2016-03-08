/**
 * Created by carlwang on 3/8/16.
 */

define(['vue','underscore'], function(Vue,_) {
    Vue.prototype.sayHello = function () {
        console.log('hello world');
    }
    return Vue;
});
