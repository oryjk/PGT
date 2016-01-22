function dislikeButton(favouriteId){
    
    $.ajax({
        type:'get',
        url:'/mobile/myAccount/dislike',
        data:'favouriteId='+favouriteId,
        dataType:'json',
        async:false,
        success: function (data) {
            alert(data.success);
            if(data.success===1){
                location.reload(true);
            }
            if(!data.success===0){
                alert("请求成功，但提交数据有问题");
            }
        },
        error:function(){

        }
    });
}



function addShoppingCart(productId){

    $.ajax({
        type:'get',
        url:'/mobile/shoppingCart/ajaxAddItemToOrder',
        data:'productId='+productId,
        dataType:'json',
        success: function (data) {
            if(data.success===1){
                alert("加入购物车成功！！！")
            }
            if(!data.success===0){
                alert("请求成功，但提交数据有问题");
            }
        },
        error:function(){

        }
    });
}