/**
 * Created by supersoup on 15/12/8.
 */

define(function() {
    var baseUrl = '';
    if (location.hostname == 'localhost') {
        //测试用
        baseUrl = '/b2c_store';
    }

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
                    if (productMessage) {
                        productMessage
                            .html('成功加入购物车!')
                            .fadeIn(1000)
                            .fadeOut(2000);
                    }
                    //购物车显示数量
                    if (cartCount) {
                        getOrderItemCount(cartCount);
                    }
                    if (callbackFunction) {
                        callbackFunction();
                    }

                } else if (param.success === 0) {
                    if (productMessage) {
                        productMessage
                            .html(param.errorMessage.default)
                            .fadeIn(1000)
                            .delay(3000)
                            .fadeOut(2000);
                    }
                }
            }
        });
    };

    //删除购物车 productId:产品id, productMessage:提示标签的jq, cartCount:购物车数字的jq
    var removeItemFromOrder = function(productId, productMessage, cartCount, callbackFunction) {
        $.ajax({
            type: 'get',
            url: baseUrl + '/shoppingCart/ajaxRemoveItemFromOrder',
            data: {
                'productId': productId
            },
            success: function(param) {
                if (param.success === 1) {
                    if (productMessage) {
                        productMessage
                            .html('成功删除购物车!')
                            .fadeIn(1000)
                            .fadeOut(2000);
                    }
                    //购物车显示数量
                    if (cartCount) {
                        getOrderItemCount(cartCount);
                    }

                } else if (param.success === 0) {
                    if (productMessage) {
                        productMessage
                            .html(param.errorMessage.default)
                            .fadeIn(1000)
                            .delay(3000)
                            .fadeOut(2000);
                    }
                }

                if (callbackFunction) {
                    callbackFunction(param);
                }
            }
        });
    };

    //加入收藏 favouriteId:收藏id, productMessage:提示标签的jq, [that]:this的jq填入则可以进行收藏及取消的切换
    var addItemToFavourite = function(productId, productMessage, that, callback) {
        $.ajax({
            type: 'GET',
            url: baseUrl + '/myAccount/favourite',
            data: {
                'productId': productId
            },
            success: function(param) {
                if (param.success === 1) {
                    if (productMessage) {
                        productMessage
                            .html('添加收藏成功!')
                            .fadeIn(1000)
                            .fadeOut(2000);
                    }

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
                    if (productMessage) {
                        productMessage
                            .html(param.errorMessage.default)
                            .fadeIn(1000)
                            .delay(3000)
                            .fadeOut(2000);
                    }
                }

                if (callback) {
                    callback(param);
                }
            }
        });
    };

    //取消收藏,favouriteId:收藏id, productMessage:提示标签的jq, that:this的jq
    var removeFavourite = function(favouriteId, productMessage, that, callback) {
        $.ajax({
            type: 'GET',
            url: baseUrl + '/myAccount/dislike',
            data: {
                'favouriteId': favouriteId
            },
            success: function(param) {
                if (param.success === 1) {
                    if (productMessage) {
                        productMessage
                            .html('取消收藏成功!')
                            .fadeIn(1000)
                            .fadeOut(2000);
                    }

                    //收藏取消切换
                    if (that) {
                        that
                            .hide()
                            .siblings()
                            .filter('.addEnjoy')
                            .show();
                    }


                } else if (param.success === 0) {
                    if (productMessage) {
                        productMessage
                            .html(param.errorMessage.default)
                            .fadeIn(1000)
                            .delay(3000)
                            .fadeOut(2000);
                    }
                }

                if (callback) {
                    callback(param);
                }
            }
        })
    };

    //'/shoppingCart/ajaxRemoveItemFromOrder


    return {
        addItemToOrder: addItemToOrder,
        removeItemFromOrder: removeItemFromOrder,
        addItemToFavourite: addItemToFavourite,
        getOrderItemCount: getOrderItemCount,
        removeFavourite: removeFavourite,
        baseUrl: baseUrl
    }
});