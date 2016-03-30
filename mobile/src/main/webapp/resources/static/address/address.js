function deleteById(addressId){
    $.ajax({
        type:'post',
        url:'/mobile/my-account/person-info/deleteAddress/'+addressId,
        dataType:"json",
        async:false,
        success: function () {
            location.reload(true);
        },
        error: function () {
            alert("亲，请求失败，请重试哦 0_0！");
        }

    });
}

function setDefaultById(addressId){
    $.ajax({
        type:'post',
        url:'/mobile/my-account/person-info/setDefaultAddress/'+addressId,
        dataType:"json",
        async:false,
        success: function (data) {
            if(data.success=="true"){
                location.reload(true);
            }
            if(data.success=="false"){
                alert("请求失败！");
                redirectUrl = data.redirectUrl;
                location.href=redirectUrl;
            }
        },
        error: function () {
            alert("亲，请求失败，请重试哦 0_0！");
        }

    });
}

