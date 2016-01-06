package com.pgt.cart.controller;

/**
 * Created by jeniss on 15/12/6.
 */
public interface CartMessages {

	String ERROR_PROD_NOT_AVAILABLE = "Error.shoppingCart.product.notAvailable";

	String ERROR_PROD_OUT_STOCK = "Error.shoppingCart.product.outOfStock";

	String ERROR_ITEM_PURCHASE_FAILED = "Error.shoppingCart.item.addItemToCartFailed";

	String ERROR_ITEM_REMOVE_FAILED = "Error.shoppingCart.item.removeItemFromCartFailed";

	String ERROR_CART_EMPTY_FAILED = "Error.shoppingCart.cart.emptyCartFailed";

	String ERROR_INV_CHECK_FAILED = "Error.shoppingCart.cart.inventoryCheckFailed";

	String WARN_REMOVE_ITEM_NOTIFY = "Warn.shoppingCart.item.removeItemNotify";
}
