
$("#upload").change(function(){
//定义参数
    var options = {
        url : "/upload/uploadPic",
        dataType : "json",
        type :  "post",
        success : function(data){
            //回调 二个路径
            //url
            //path
            $("#allImgUrl").attr("src",data.url);
            $("#path").val(data.path);
        }
    };

    //jquery.form使用方式
    $("#jvForm").ajaxSubmit(options);


});




