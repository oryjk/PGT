/**
 * liqiang
 */

//实例一个对象保存（数组参数）
var model = new Array();

//保存验证 结果 标识
function form(){
	var flag = "";
	return{
		"setFlag":function(val){
			flag += val;
		},
		"getFlag":function(){
			return flag;
		}
	}
}

//提交事件
function onSubmit(url, error_info , success_url){
	//获取model表单数组长度
	var i = 0;
	for(var key in model){
		i++;
	}
	
	//错误情况则 字符串包含 0
	var str = f.getFlag().toString();
	str = str.substring(str.length - i,str.length);
	
	//如果 str包含 0 则存在表单错误，否则提交
	var v = str.match("0");
	if(v == null){
		if(url == undefined){
			//没有url时同步提交
			$(this).submit();
		}else{
			//有url时异步提交
			ajaxSubmit(url, error_info , success_url);
		}
	}else{
		err_input(str);
	}
}

//添加验证元素
function addCheckElement(paramName, paramValue, regex_value, min_length, max_length , error_info){
	model[paramName] = paramValue;
	var regex = new Object();
	regex[paramName] = regex_value;
	check(model, regex, min_length, max_length, error_info);
}

//js 对象 + regex 验证表单元素
function model_regex(model, key, regex, min, max, info){
	if(model[key].length > min){
		var v = model[key].match(regex);
		if(v == null){
			$("#"+ key).css("outline","1px solid red");
			error_entity[key] = info;
			if(model[key].length > max){
				error_entity[key] = "你多输入了" + (model[key].length - max) + "个字符";
			}
			return "0";
		}
		else{
			$("#"+ key).css("outline","1px solid green");
			error_entity[key] = "";
			return "1";
		}
	}
	else{
		return "0";
	}
}

//初始化验证 标识
var f = new form();
//flag 1表示验证通过，0表示验证不通过
function check(o, r, min, max, info){
	for(var key in r){
		flag = model_regex(o, key, r[key], min, max, info);
		f.setFlag(flag);
	}
}

//自定义vue-验证
Vue.prototype.addCheckElement = function(paramName, paramValue,regex_params){
     addCheckElement(paramName, paramValue, regex_params.regex, regex_params.min, regex_params.max , regex_params.error);
}

//自定义vue-error错误信息实体，构造器
var error_entity;
Vue.prototype.addErrorEntity = function(o){
	//动态创建对象
	error_entity = Object.create(o);
}

//验证元素是否存在空值
function volidataNUll(){
	for(var m in error_entity){
		$("#"+ m).css("outline", "1px solid red");
		$("#"+ m).focus();
	}
}


//异步提交数据
function ajaxSubmit(url, error_info , success_url){
	//准备参数
	var json_data = "";
	json_data += "[{";
	for(var key in model){
		json_data += "\"" + key +"\":\"" + model[key] + "\",";
	}
	json_data = json_data.substring(0, json_data.length - 1) + "}]";
	//表单非空验证
	if(json_data != "[}]"){
		alert(json_data);
		$.post(
			url,
			{ 'json_data': json_data },
			function(data){
				if(data == "error"){
					alert(error_info);
				}
				if(data == "success" && success_url != undefined){
					window.location.href = success_url;
				}
			}
		);
	}else{
		volidataNUll();
	}
}

//提交时参数错误时修改样式
function err_input(str){
	for(var i = 0; i < str.length; i++){
		if(str[i] == 0){
			$("input[type=text][id]").eq(i).css("outline", "1px solid red");
			$("input[type=text][id]").focus();
		}
	}
}

