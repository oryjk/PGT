//绑定错误对象并取别名
Vue.prototype.regexEntity;

//绑定错误信息
Vue.prototype.volidateEntity = new Object();

//扩展验证
Vue.prototype.excuteVolidata = function(event) {
	var key = event.srcElement.__v_model.expression;
	var val = event.target.value;
	var regex_entity = this.regexEntity;
	for(var o in regex_entity){
		if(o == key){
			if(val.match(regex_entity[o].regex) == null){//不匹配正则
				event.target.style.outline = "1px solid red";
				for(var key in this.volidateEntity){
					if(o.match(key)){
						this.volidateEntity[key] = regex_entity[o].error;
					}
				}
			}
			else{
				event.target.style.outline = "1px solid green";
				for(var key in this.volidateEntity){
					if(o.match(key)){
						this.volidateEntity[key] = true;
					}
				}
			}
		}
	}
};

//提交事件
Vue.prototype.submitVolidata = function(funData, fun){
	var model = fun;
	var data = new model();
	for(var key in data){
		for(var d in funData){
			if(key == d){
				data[key] = funData[d];
			}
		}
	}
	//输入框正确性验证
	var i,j,i=0,j=0;
	for(var info in this.volidateEntity){
		i++;
		if(this.volidateEntity[info] == true){
			j++;
		}
	}
	//jquery异步提交
	if(i == j){
		console.log(this);
		//同步提交表单（单个） -- 完善中（自写js异步，这里通过参数传方法-钩子）
		document.forms[0].submit();
	}
	//为空时处理
	else{
		var regex_entity = this.regexEntity;
		for(var o in regex_entity){
			for(var key in this.volidateEntity){
				if(o.match(key)){
					for(var m in data){
						if(o.match(m)){
							if(data[m].match(regex_entity[o].regex) == null){
								for(var i=0;i < this.$el.children.error.children.form.length;i++){
									if(this.$el.children.error.children.form[i].__v_model != undefined){
										var input = this.$el.children.error.children.form[i].__v_model.expression;
										if(input.match(o)){
											this.$el.children.error.children.form[i].style.outline = "1px solid red"
											this.volidateEntity[key] = regex_entity[o].error;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
};


/**
 		vue 以上表单验证 div使用格式 - 多个表单验证正在完善中，边框红色显示位置bug（单个没问题）
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