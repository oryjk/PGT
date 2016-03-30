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
                    demo.$data.gridData = arr;
                },"json");
                //arr.pop(index);
            },
            detail: function(event){
                var index  =  event.target.value;
                $.post("findfile",{"filename" : arr[index].name},function(data){
                    $("#content").html(data);
                },"json");
            }
        }
    });

    $("button[title=delbyday]").click(function(){
        var day = $("#day").val();
        $.post("delbyday",{"day" : day},function(data){
            arr = data;
            demo.$data.gridData = arr;
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
