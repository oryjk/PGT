/**
 * Created by supersoup on 15/12/8.
 */

define(function() {
    var baseUrl = '';

    //重新请求购物车数量 cartCount:购物车数字的jq
    var getOrderItemCount = function(cartCount) {
        $.ajax({
            type: 'get',
            url: baseUrl + '/shoppingCart/getOrderItemCount',
            success: function(param) {
                cartCount.html(param.data.itemCount);
            }
        })
    };

    //加入购物车 productId:产品id, productMessage:提示标签的jq, cartCount:购物车数字的jq
    var addItemToOrder = function(productId, productMessage, cartCount, callbackFunction) {
        $.ajax({
            type: 'get',
            url: baseUrl + '/shoppingCart/ajaxAddItemToOrder',
            data: {
                'productId': productId
            },
            success: function(param) {
                if (param.success === 1) {
                    productMessage
                        .html('成功加入购物车!')
                        .fadeIn(1000)
                        .fadeOut(2000);
                    //购物车显示数量
                    if (cartCount) {
                        getOrderItemCount(cartCount);
                    }
                    if (callbackFunction) {
                        callbackFunction();
                    }

                } else if (param.success === 0) {
                    productMessage
                        .html('对不起,该商品已卖完!')
                        .fadeIn(1000)
                        .delay(3000)
                        .fadeOut(2000);
                }
            }
        });
    };

    //加入收藏 favouriteId:收藏id, productMessage:提示标签的jq, [that]:this的jq填入则可以进行收藏及取消的切换
    var addItemToFavourite = function(productId, productMessage, that) {
        $.ajax({
            type: 'GET',
            url: baseUrl + '/myAccount/favourite',
            data: {
                'productId': productId
            },
            success: function(param) {
                if (param.success === 1) {
                    productMessage
                        .html('添加收藏成功!')
                        .fadeIn(1000)
                        .fadeOut(2000);
                    //收藏取消切换
                    if (that) {
                        that
                            .hide().
                            siblings().
                            filter('.disLike')
                            .show()
                            .attr('data-favourite-id', param.data.id);
                    }

                } else if (param.success === 0) {
                    productMessage
                        .html(param.errorMessage.default)
                        .fadeIn(1000)
                        .delay(3000)
                        .fadeOut(2000);
                }
            }
        });
    };

    //取消收藏,favouriteId:收藏id, productMessage:提示标签的jq, that:this的jq
    var removeFavourite = function(favouriteId, productMessage, that) {
        $.ajax({
            type: 'GET',
            url: baseUrl + '/myAccount/dislike',
            data: {
                'favouriteId': favouriteId
            },
            success: function(param) {
                if (param.success === 1) {
                    productMessage
                        .html('取消收藏成功!')
                        .fadeIn(1000)
                        .fadeOut(2000);
                    //收藏取消切换
                    that
                        .hide()
                        .siblings()
                        .filter('.addEnjoy')
                        .show();


                } else if (param.success === 0) {
                    productMessage
                        .html(param.errorMessage.default)
                        .fadeIn(1000)
                        .delay(3000)
                        .fadeOut(2000);
                }
            }
        })
    };


    return {
        addItemToOrder: addItemToOrder,
        addItemToFavourite: addItemToFavourite,
        getOrderItemCount: getOrderItemCount,
        removeFavourite: removeFavourite,
        baseUrl: baseUrl
    }
});