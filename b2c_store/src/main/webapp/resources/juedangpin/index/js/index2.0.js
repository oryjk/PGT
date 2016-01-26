

function addCart(productId){

    $.get( $("#path").val()+"/shoppingCart/ajaxAddItemToOrder",
        {
            'productId': productId
        },
        function(param){
            if(param.success === 1){
                alert(productId+":添加购物车成功！")
            }
        });

}


function addEnjoy(productId){

    $.get( $("#path").val()+"/myAccount/favourite",
        {
            'productId': productId
        },
        function(param){
            if(param.success === 1){
                alert(productId+":添加收藏成功！")
            }
        });

}





