package com.pgt.cart.controller;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.ResponseBean;
import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.exception.OrderPersistentException;
import com.pgt.cart.exception.PriceOrderException;
import com.pgt.cart.service.PriceOrderService;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.internal.util.RepositoryUtils;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.utils.URLMapping;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Yove on 10/28/2015.
 */
@RequestMapping("/shoppingCart")
@RestController
public class ShoppingCartModifierController extends TransactionBaseController implements CartMessages {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartModifierController.class);

	@Resource(name = "shoppingCartService")
	private ShoppingCartService mShoppingCartService;

	@Resource(name = "priceOrderService")
	private PriceOrderService mPriceOrderService;

	@Autowired
	private ProductService mProductService;

	@Resource(name = "responseBuilderFactory")
	private ResponseBuilderFactory mResponseBuilderFactory;

	@Autowired
	private URLMapping mURLMapping;

	private static final String CART = "/shoppingCart/cart";

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public ModelAndView redirect2Cart(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		ModelAndView modelAndView = new ModelAndView("shopping-cart/cart");
		return modelAndView;
	}


	@RequestMapping(value = "/addItemToOrder")//, method = RequestMethod.POST)
	public ModelAndView addItemToOrderRedirect2Cart(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "productId", required = true) int productId,
			@RequestParam(value = "easyBuy", required = false, defaultValue = "0") int easyBuy) {
		ModelAndView mav = new ModelAndView(getRedirectView(getURLMapping().getPDPUrl(String.valueOf(productId))));
		if (easyBuy > 0) {
			// TODO add shipping view name here!!
			mav.setViewName(getRedirectView(""));
		}
		// check product
		Product product = getProductService().queryProduct(productId);
		if (!mShoppingCartService.checkProductValidity(product)) {
			LOGGER.debug("Stop add item to cart for product: {} not available", productId);
			mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_PROD_NOT_AVAILABLE, StringUtils.EMPTY));
			return mav;
		}
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		synchronized (order) {
			if (!getShoppingCartService().purchaseProduct(order, product)) {
				LOGGER.debug("Stop add item to cart for product: {} out of stock", productId);
				mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_PROD_OUT_STOCK, StringUtils.EMPTY));
				return mav;
			}
			// persist changes to database
			TransactionStatus status = ensureTransaction();
			try {
				LOGGER.debug("Synchronized order to price and update order");
				getPriceOrderService().priceOrder(order);
				// anonymous user could only add item to cart but not persisted
				if (RepositoryUtils.idIsValid(order.getUserId())) {
					boolean result = getShoppingCartService().persistInitialOrder(order);
					LOGGER.debug("Persist order with result: {}", result ? "success" : "failed");
					if (!result) {
						status.setRollbackOnly();
					}
				}
			} catch (PriceOrderException e) {
				LOGGER.error(String.format("Add item to cart with product id: %d failed for price order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (OrderPersistentException e) {
				LOGGER.error(String.format("Add item to cart with product id: %d failed for persist order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (Exception e) {
				LOGGER.error(String.format("Add item to cart with product id: %d failed.", productId), e);
				status.setRollbackOnly();
			} finally {
				if (status.isRollbackOnly()) {
					LOGGER.debug("Transaction had been set as roll back during add item to cart for product: {}",
							productId);
					mav.addObject(CartConstant.ERROR_MSG,
							getMessageValue(ERROR_ITEM_PURCHASE_FAILED, StringUtils.EMPTY));
				} else {
					// reset redirect url to shopping car
					mav.setViewName(getRedirectView(CART));
				}
				getTransactionManager().commit(status);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/ajaxAddItemToOrder")//, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity ajaxAddItemToOrder(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "productId", required = true) int productId) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
		// check product
		Product product = getProductService().queryProduct(productId);
		if (!mShoppingCartService.checkProductValidity(product)) {
			LOGGER.debug("Stop add item to cart for product: {} not available", productId);
			rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_PROD_NOT_AVAILABLE);
			return new ResponseEntity(rb, HttpStatus.OK);
		}
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		synchronized (order) {
			if (!getShoppingCartService().purchaseProduct(order, product)) {
				LOGGER.debug("Stop add item to cart for product: {} out of stock", productId);
				rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_PROD_OUT_STOCK);
				return new ResponseEntity(rb, HttpStatus.OK);
			}
			// persist changes to database
			TransactionStatus status = ensureTransaction();
			try {
				LOGGER.debug("Synchronized order to price and update order");
				getPriceOrderService().priceOrder(order);
				// anonymous user could only add item to cart but not persisted
				if (RepositoryUtils.idIsValid(order.getUserId())) {
					boolean result = getShoppingCartService().persistInitialOrder(order);
					LOGGER.debug("Persist order with result: {}", result ? "success" : "failed");
					if (!result) {
						status.setRollbackOnly();
					}
				}
			} catch (PriceOrderException e) {
				LOGGER.error(String.format("Add item to cart with product id: %d failed for price order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (OrderPersistentException e) {
				LOGGER.error(String.format("Add item to cart with product id: %d failed for persist order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (Exception e) {
				LOGGER.error(String.format("Add item to cart with product id: %d failed.", productId), e);
				status.setRollbackOnly();
			} finally {
				if (status.isRollbackOnly()) {
					LOGGER.debug("Transaction had been set as roll back during add item to cart for product: {}",
							productId);
					rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_ITEM_PURCHASE_FAILED);
				} else {
					rb.setSuccess(true);
				}
				getTransactionManager().commit(status);
			}
		}
		return new ResponseEntity(rb, HttpStatus.OK);
	}

	@RequestMapping(value = "/removeItemFromOrder")//, method = RequestMethod.POST)
	public ModelAndView removeItemFromOrder(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "productId", required = true) int productId) {
		ModelAndView mav = new ModelAndView(getRedirectView(CART));
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		synchronized (order) {
			LOGGER.debug("Synchronized order to price and update order");
			// check order contains commerce item
			CommerceItem purchasedCommerceItem = order.getCommerceItemByProduct(productId);
			if (purchasedCommerceItem == null) {
				LOGGER.debug("Cannot find commerce item with product id: {}", productId);
				return mav;
			}
			// remove commerce item
			purchasedCommerceItem = order.removeCommerceItemByProduct(productId);
			// persist changes to database
			TransactionStatus status = ensureTransaction();
			try {
				getPriceOrderService().priceOrder(order);
				// anonymous user could only add item to cart but not persisted
				if (RepositoryUtils.idIsValid(order.getUserId())) {
					boolean result = true;
					// remove record from commerce item table
					if (purchasedCommerceItem != null && RepositoryUtils.idIsValid(purchasedCommerceItem.getId())) {
						result = getShoppingCartService().deleteCommerceItem(purchasedCommerceItem.getId());
					}
					if (result) {
						result = getShoppingCartService().persistInitialOrder(order);
						LOGGER.debug("Persist order with result: {}", result ? "success" : "failed");
						if (!result) {
							status.setRollbackOnly();
						}
					}
				}
			} catch (PriceOrderException e) {
				LOGGER.error(String.format("Remove item from cart with product id: %d failed for price order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (OrderPersistentException e) {
				LOGGER.error(String.format("Remove item from with product id: %d failed for persist order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (Exception e) {
				LOGGER.error(String.format("Remove item from with product id: %d failed.", productId), e);
				status.setRollbackOnly();
			} finally {
				if (status.isRollbackOnly()) {
					LOGGER.debug("Transaction had been set as roll back during remove item from cart for product: {}",
							productId);
					mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_ITEM_REMOVE_FAILED, StringUtils.EMPTY));
				}
				getTransactionManager().commit(status);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/ajaxRemoveItemFromOrder")//, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity ajaxRemoveItemFromOrder(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "productId", required = true) int productId) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean();
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		synchronized (order) {
			LOGGER.debug("Synchronized order to price and update order");
			// check order contains commerce item
			CommerceItem purchasedCommerceItem = order.getCommerceItemByProduct(productId);
			if (purchasedCommerceItem == null) {
				LOGGER.debug("Cannot find commerce item with product id: {}", productId);
				return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
			}
			// remove commerce item
			purchasedCommerceItem = order.removeCommerceItemByProduct(productId);
			// persist changes to database
			TransactionStatus status = ensureTransaction();
			try {
				getPriceOrderService().priceOrder(order);
				// anonymous user could only add item to cart but not persisted
				if (RepositoryUtils.idIsValid(order.getUserId())) {
					boolean result = true;
					// remove record from commerce item table
					if (purchasedCommerceItem != null && RepositoryUtils.idIsValid(purchasedCommerceItem.getId())) {
						result = getShoppingCartService().deleteCommerceItem(purchasedCommerceItem.getId());
					}
					if (result) {
						result = getShoppingCartService().persistInitialOrder(order);
						LOGGER.debug("Persist order with result: {}", result ? "success" : "failed");
						if (!result) {
							status.setRollbackOnly();
						}
					}
				}
			} catch (PriceOrderException e) {
				LOGGER.error(String.format("Remove item from cart with product id: %d failed for price order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (OrderPersistentException e) {
				LOGGER.error(String.format("Remove item from with product id: %d failed for persist order exception.",
						productId), e);
				status.setRollbackOnly();
			} catch (Exception e) {
				LOGGER.error(String.format("Remove item from with product id: %d failed.", productId), e);
				status.setRollbackOnly();
			} finally {
				if (status.isRollbackOnly()) {
					LOGGER.debug("Transaction had been set as roll back during remove item from cart for product: {}",
							productId);
					rb.setSuccess(false).addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_ITEM_REMOVE_FAILED);
				}
				getTransactionManager().commit(status);
			}
		}
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/ajaxRemoveItemsFromOrder")//, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity ajaxRemoveItemsFromOrder(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "productIds", required = true) int[] productIds) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean();
		if (ArrayUtils.isEmpty(productIds)) {
			LOGGER.debug("Empty product ids to remove from order.");
			return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
		}
		List<Integer> commerceItemIds = new ArrayList(productIds.length);
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		synchronized (order) {
			LOGGER.debug("Synchronized order to price and update order");
			for (int i = 0; i < productIds.length; i++) {
				int productId = productIds[i];
				// check order contains commerce item
				CommerceItem purchasedCommerceItem = order.getCommerceItemByProduct(productId);
				if (purchasedCommerceItem == null) {
					LOGGER.debug("Cannot find commerce item with product id: {}", productId);
					return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
				}
				// remove commerce item
				purchasedCommerceItem = order.removeCommerceItemByProduct(productId);
				if (purchasedCommerceItem != null && RepositoryUtils.idIsValid(purchasedCommerceItem.getId())) {
					commerceItemIds.add(purchasedCommerceItem.getId());
				}
			}
			// persist changes to database
			if (CollectionUtils.isNotEmpty(commerceItemIds)) {
				TransactionStatus status = ensureTransaction();
				try {
					getPriceOrderService().priceOrder(order);
					// anonymous user could only add item to cart but not persisted
					if (RepositoryUtils.idIsValid(order.getUserId())) {
						boolean result = getShoppingCartService().deleteCommerceItems(commerceItemIds);
						if (result) {
							result = getShoppingCartService().persistInitialOrder(order);
							LOGGER.debug("Persist order with result: {}", result ? "success" : "failed");
							if (!result) {
								status.setRollbackOnly();
							}
						}
					}
				} catch (PriceOrderException e) {
					LOGGER.error("Remove items from cart with product ids: " + Arrays.toString(productIds) + " failed for price order exception.", e);
					status.setRollbackOnly();
				} catch (OrderPersistentException e) {
					LOGGER.error("Remove items from cart with product ids: " + Arrays.toString(productIds) + " failed for persist order " +
							"exception.", e);
					status.setRollbackOnly();
				} catch (Exception e) {
					LOGGER.error("Remove items from cart with product ids: " + Arrays.toString(productIds) + " failed.", e);
					status.setRollbackOnly();
				} finally {
					if (status.isRollbackOnly()) {
						LOGGER.debug("Transaction had been set as roll back during remove items from cart for product: "
								+ Arrays.toString(productIds));
						rb.setSuccess(false).addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_ITEM_REMOVE_FAILED);
					}
					getTransactionManager().commit(status);
				}
			}
		}
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getOrderItemCount", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getOrderItemCount(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean();
		int itemCount = 0;
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		if (order != null && !order.emptyOrder()) {
			itemCount = order.getCommerceItemCount();
		}
		LOGGER.debug("Get commerce item count: {} from current order.", itemCount);
		Map<String, Object> result = new HashMap(1);
		result.put(CartConstant.CURRENT_ORDER_ITEM_COUNT, itemCount);
		return new ResponseEntity(rb.setData(result).createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/emptyCart", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity emptyCart(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean();
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		if (order == null || order.emptyOrder()) {
			LOGGER.debug("No need to empty items for empty order.");
			return new ResponseEntity(rb.setData(order).createResponse(), HttpStatus.OK);
		}
		synchronized (order) {
			LOGGER.debug("Synchronized order to price and update order");
			// remove all commerce items in order
			order.emptyCommerceItems();
			TransactionStatus status = ensureTransaction();
			try {
				getPriceOrderService().priceOrder(order);
				// anonymous user could only add item to cart but not persisted
				if (RepositoryUtils.idIsValid(order.getUserId())) {
					getShoppingCartService().deleteAllCommerceItems(order.getId());
					boolean result = getShoppingCartService().persistInitialOrder(order);
					LOGGER.debug("Persist order with result: {}", result ? "success" : "failed");
					if (!result) {
						status.setRollbackOnly();
					}
				}
			} catch (PriceOrderException e) {
				LOGGER.error("Empty order failed for price order exception.", e);
				status.setRollbackOnly();
			} catch (OrderPersistentException e) {
				LOGGER.error("Empty order failed for persist order exception.", e);
				status.setRollbackOnly();
			} catch (Exception e) {
				LOGGER.error("Empty order failed.", e);
				status.setRollbackOnly();
			} finally {
				if (status.isRollbackOnly()) {
					LOGGER.debug("Transaction had been set as roll back during empty order");
					rb.setSuccess(false).addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_CART_EMPTY_FAILED);
				}
				getTransactionManager().commit(status);
			}
		}
		return new ResponseEntity(rb.setData(order).createResponse(), HttpStatus.OK);
	}

	public ShoppingCartService getShoppingCartService() {
		return mShoppingCartService;
	}

	public void setShoppingCartService(final ShoppingCartService pShoppingCartService) {
		mShoppingCartService = pShoppingCartService;
	}

	public PriceOrderService getPriceOrderService() {
		return mPriceOrderService;
	}

	public void setPriceOrderService(final PriceOrderService pPriceOrderService) {
		mPriceOrderService = pPriceOrderService;
	}

	public ProductService getProductService() {
		return mProductService;
	}

	public void setProductService(final ProductService pProductService) {
		mProductService = pProductService;
	}

	public URLMapping getURLMapping() {
		return mURLMapping;
	}

	public void setURLMapping(URLMapping pURLMapping) {
		mURLMapping = pURLMapping;
	}

	public ResponseBuilderFactory getResponseBuilderFactory() {
		return mResponseBuilderFactory;
	}

	public void setResponseBuilderFactory(ResponseBuilderFactory pResponseBuilderFactory) {
		mResponseBuilderFactory = pResponseBuilderFactory;
	}

}
