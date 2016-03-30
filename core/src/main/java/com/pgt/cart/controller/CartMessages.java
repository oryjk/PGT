package com.pgt.cart.controller;

/**
 * Created by Yove on 15/12/6.
 */
public interface CartMessages {

	String ERROR_PROD_NOT_AVAILABLE = "Error.shoppingCart.product.notAvailable";

	String ERROR_PROD_OUT_STOCK = "Error.shoppingCart.product.outOfStock";

	String ERROR_PROD_ADDED_TO_CART = "Error.shoppingCart.product.productAddedToCart";

	String ERROR_ITEM_PURCHASE_FAILED = "Error.shoppingCart.item.addItemToCartFailed";

	String ERROR_ITEM_REMOVE_FAILED = "Error.shoppingCart.item.removeItemFromCartFailed";

	String ERROR_ITEM_REACHED_LIMIT = "Error.shoppingCart.item.itemCountReachedLimit";

	String ERROR_CART_EMPTY_FAILED = "Error.shoppingCart.cart.emptyCartFailed";

	String ERROR_INV_CHECK_FAILED = "Error.shoppingCart.cart.inventoryCheckFailed";

	String WARN_REMOVE_ITEM_NOTIFY = "Warn.shoppingCart.item.removeItemNotify";
}
