$(function(){
    var arr = new Array();

    var demo = new Vue({
        el: '#demo',
        data: {
            gridData:[
                {name: '无'}
            ]
        },
        ready:function(){
            $.post("findall",{},function(data){
                arr = data;
                demo.$data.gridData = arr;
            },"json");
        },
        methods: {
            del: function(event){
                var index  =  event.target.value;
                $.post("del",{"filename" : arr[index].name},function(data){
                    arr = data;
                },"json");
                arr.pop(index);
            },
            recover: function(event){
                var index  =  event.target.value;
                $.post("recover",{"filename" : arr[index].name},function(data){
                    arr = data;
                },"json");
            }
        }
    });

    $("button[title=add]").click(function(){
        $.post("add",{},function(data){
            arr = data;
        },"json");
    });
});






//js面向对象
//var entity = function() {
//    var obj = new Object();
//    obj.json = "";
//    obj.set = function(o) {
//        obj.json = o;
//    }
//    obj.get = function() {
//        return obj.json;
//    }
//    return obj;
//}
//
//var obj = new entity();
