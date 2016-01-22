$(document).ready(function(){

    $("#city").mousedown(function(){
        var provinceId = $("#province option:selected").attr("data-value");
        $.ajax({
            type:'get',
            url:'/mobile/getCityByProvinceId/'+provinceId,
            dataType:'json',
            async:false,
            success:function(city){
                $("#city").html("");
                $("#city").append('<option value="volvo">请选择城市</option>');
                for(var c=0;c < city.length;c++){
                    $("#city").append('<option id="city_op" name="city_op" data-value="'+city[c].id+'" value="'+city[c].name+'">'+city[c].name+'</option>');


                }
            }

        });


    });

    $("#district").mousedown(function(){
        var cityId = $("#city option:selected").attr("data-value");
        $.ajax({
            type:'get',
            url:'/mobile/getAreaByCityId/'+cityId,
            dataType:'json',
            async:false,
            success:function(area){
                $("#district").html("");
                $("#district").append('<option value="volvo">请选择区县</option>');
                for(var a=0;a < area.length;a++){
                    $("#district").append('<option id="city_op" name="city_op" data-value="'+area[a].id+'" value="'+area[a].name+'">'+area[a].name+'</option>');

                }
            }

        });


    });
});



function addAddressSubmit(){
    var name = $("#name").val();
    var phone = $("#phone").val();
    var province = $("#province").val();
    var city = $("#city").val();
    var district = $("#district").val();
    var address = $("#address").val();
    var redirectUrl;
    $.ajax({
        type:'post',
        url:'/mobile/my-account/person-info/addAddress',
        data:{'name':name,'phone':phone,'province':province,'city':city,'district':district,'address':address},
        dataType:'json',
        success:function(data){

            if(data.success=="true"){
                location.href="/mobile/my-account/person-info/address";
            }
            if(data.success=="false"){
                alert("填写信息有误");
                redirectUrl = data.redirectUrl;
            }


        },
        error:function(){
            alert("提交失败！，请重试");
        }


    })
}