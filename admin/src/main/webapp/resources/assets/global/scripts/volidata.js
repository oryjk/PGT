//绑定错误对象并取别名
Vue.prototype.regexEntity;

//绑定错误信息
Vue.prototype.volidateEntity = new Object();

//扩展验证
Vue.prototype.excuteVolidata = function (event) {
    var key = event.target.__v_model.expression;
    var val = event.target.value;
    var regex_entity = this.regexEntity;
    for (var o in regex_entity) {
        if (o == key) {
            if (val.match(regex_entity[o].regex) == null || val.length <= 0) {//不匹配正则
                event.target.style.outline = "1px solid red";
                for (var key in this.volidateEntity) {
                    if (o.match(key)) {
                        this.volidateEntity[key] = regex_entity[o].error;
                    }
                }
            }
            else {
                event.target.style.outline = "1px solid green";
                for (var key in this.volidateEntity) {
                    if (o.match(key)) {
                        this.volidateEntity[key] = true;
                    }
                }
            }
        }
    }
};

Vue.prototype.alterCss = function (o, border) {
    var obj = this.$el.children.error.children;
    for (var c = 0; c < obj.length; c++) {
        if (obj[c].nodeName == "FORM") {
            var sub_obj = obj[c];
            for (var s = 0; s < sub_obj.length; s++) {
                if (sub_obj[s].__v_model != undefined) {
                    var expression = sub_obj[s].__v_model.expression;
                    if (expression == o) {
                        sub_obj[s].__v_model.el.style.outline = border;
                    }
                }
            }
        }
    }
}

//提交事件
Vue.prototype.submitVolidata = function (funData, fun) {
    var model = fun;
    data = new model();
    var length = 0;
    for (var key in data) {
        for (var o in funData) {
            if (key == o) {
                data[key] = funData[o];
            }
        }
        length++;//获取对象属性个数
    }
    //输入框正确性验证
    var i, j, i = 0, j = 0;
    for (var info in this.volidateEntity) {
        i++;
        if (this.volidateEntity[info] == true) {
            j++;
        }
    }
    //jquery异步提交
    if (i == j) {
        document.forms[0].submit();
    }
    //为空时处理
    else {
        var regex_entity = this.regexEntity;
        var i = 0;
        for (var o in regex_entity) {
            var flag = o.split(".")[1];
            for (var key in data) {
                if (flag == key) {
                    if (data[key].match(regex_entity[o].regex) == null || data[key].length <= 0) {
                        for (var v in this.volidateEntity) {
                            if (flag == v) {
                                this.volidateEntity[v] = regex_entity[o].error;
                                this.alterCss(o, "1px solid red");
                            }
                        }
                    }
                    else {
                        this.alterCss(o, "1px solid green");
                        i++;
                        if (i == length) {
                            document.forms[0].submit();
                        }
                    }
                }
            }
        }
    }
};

//定时器
Vue.prototype.time = new Object();


/**
 vue 扩展 - 支持多个表单验证（绑定的div id和 对象名 不能一样）
 <div id="绑定的对象id-例如app">
 <div id="error" v-model="error">
 <form id="form" v-on:submit.prevent="ajaxSubmit">
 <input id="tenderTotal" type="text" v-model="newUser.name" v-on:keyup="volidate"/>
 <div v-show="error.name != true">{{error.name}}</div>
 <button type="submit">确认</button>
 </form>
 </div>
 </div>

 <div id="绑定的对象id-例如app">
 <div id="error" v-model="error">
 <form id="form" v-on:submit.prevent="ajaxSubmit">
 <input id="tenderTotal" type="text" v-model="newUser.name" v-on:keyup="volidate"/>
 <div v-show="error.name != true">{{error.name}}</div>
 <button type="submit">确认</button>
 </form>
 </div>
 </div>
 **/

