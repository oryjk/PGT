package com.pgt.cart.controller;

/**
 * Created by Yove on 12/7/2015.
 */
public interface FavouriteMessages {

	String ERROR_PROD_INVALID = "Error.favourite.product.invalid";
	String ERROR_PROD_OUT_STOCK = "Error.shoppingCart.product.outOfStock";

	String ERROR_COMMERCE_ITEM_INVALID = "Error.favourite.item.invalid";

	String ERROR_FAVOURITE_INVALID = "Error.favourite.favourite.invalid";

	String ERROR_USER_LOGIN_REQUIRED = "Error.favourite.user.loginRequired";

	String WARN_FAVOURITE_DUPLICATE = "Warn.favourite.favourite.duplicate";

	String ERROR_GENERAL_FAVOURITE_FAILED = "Error.favourite.general.favouriteFailed";
	String ERROR_GENERAL_DISLIKE_FAILED = "Error.favourite.general.dislikeFailed";
	String ERROR_GENERAL_FAVOURITE_FROM_CART_FAILED = "Error.favourite.general.favouriteFromCartFailed";
	String ERROR_GENERAL_MOVE_FAVOURITE_TO_CART_FAILED = "Error.favourite.general.moveFavouriteToCartFailed";
}
