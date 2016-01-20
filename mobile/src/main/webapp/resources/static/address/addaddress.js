$(document).ready(function(){

    $("#province").change(function(){
        var provinceId = $("#province option:selected").attr("data-value");
        $.ajax({
            type:'get',
            url:'/mobile/getCityByProvinceId/'+provinceId,
            /*url:'/mobile-1.0-SNAPSHOT/getCityByProvinceId/'+provinceId,*/


            dataType:'json',
            success:function(city){
                $("#city").html("");
                $("#city").append('<option value="volvo">请选择城市</option>');
                for(var c=0;c < city.length;c++){
                    $("#city").append('<option id="city_op" name="city_op" data-value="'+city[c].id+'" value="'+city[c].name+'">'+city[c].name+'</option>');


                }
            }

        });


    });

    $("#city").change(function(){
        var cityId = $("#city option:selected").attr("data-value");
        $.ajax({
            type:'get',
            url:'/mobile/getAreaByCityId/'+cityId,
           /* url:'/mobile-1.0-SNAPSHOT/getAreaByCityId/'+cityId,*/

            dataType:'json',
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