

$('#modifySwitch').click(function() {
    var that = $(this);

    that.parent().siblings('.pgt-scan').toggle();
    that.parent().siblings('.pgt-modify').toggle();
});



$(".link-name").click(function(){


    var id=$(this).text();

    $.ajax({
        type: "POST",
        url: "/pawn/queryUser",
        data: {
            userId: id,
        },
        success: function(data){


            if(data.status=="success"){

                //alert(data.internalUser.name);

                var name=data.internalUser.name;

                var id=data.internalUser.id;

                var phone=data.internalUser.phone;

                var date=data.internalUser.creationDate;

                $(".modal-title").text("用户："+name);
                $(".modal-body").text("").append('用户的id:'+id+'<br>'+'用户名:'+name+'<br>'+'用户的电话:'+phone+'<br>'+'用户创建的时间:'+date+'<br>');


            }


        }
    });


    $('#confirmModal').modal('show');


});