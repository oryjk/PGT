package com.pgt.cart.controller;

import com.pgt.cart.bean.*;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.exception.OrderPersistentException;
import com.pgt.cart.exception.PriceOrderException;
import com.pgt.cart.service.OrderService;
import com.pgt.cart.service.PriceOrderService;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.user.bean.User;
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
import org.springframework.util.ObjectUtils;
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
public class ShoppingCartModifierController extends TransactionBaseController implements CartMessages, CartProperties {

	public static final  String CART   = "/shoppingCart/cart";
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartModifierController.class);
	@Resource(name = "shoppingCartService")
	private ShoppingCartService    mShoppingCartService;
	@Resource(name = "priceOrderService")
	private PriceOrderService      mPriceOrderService;
	@Autowired
	private ProductService         mProductService;
	@Autowired
	private OrderService           orderService;
	@Autowired
	private URLConfiguration       urlConfiguration;
	@Resource(name = "responseBuilderFactory")
	private ResponseBuilderFactory mResponseBuilderFactory;
	@Autowired
	private URLMapping             mURLMapping;

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public ModelAndView redirect2Cart (HttpServletRequest pRequest, HttpServletResponse pResponse) {
		ModelAndView mav = new ModelAndView("shopping-cart/cart");
		mav.addObject(CartConstant.ORDER_ITEM_LIMIT, getShoppingCartService().getMaxItemCount4Cart());
		Order order = getCurrentOrder(pRequest);
		if (!ObjectUtils.isEmpty(order)) {
			synchronized (order) {
				getShoppingCartService().checkInventory(order);
				if (!getShoppingCartService().checkCartItemCount(order)) {
					LOGGER.debug("redirect to cart page because cart item count had been reached limit");
					mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_ITEM_REACHED_LIMIT, StringUtils.EMPTY));
					return mav;
				}
			}
			if (StringUtils.equals(pRequest.getParameter("error"), ERROR_INV_CHECK_FAILED)) {
				String error = getMessageValue(ERROR_INV_CHECK_FAILED, StringUtils.EMPTY);
				mav.addObject(CartConstant.ERROR_MSG, error);
				return mav;
			}
			String oosProductIds = pRequest.getParameter("oosProdId");
			if (StringUtils.isBlank(oosProductIds)) {
				return mav;
			}
			String oosProductNames = getShoppingCartService().convertProductIdsToProductNames(oosProductIds, "_");
			if (StringUtils.isNotBlank(oosProductNames)) {
				String error = oosProductNames.concat(getMessageValue(WARN_REMOVE_ITEM_NOTIFY, StringUtils.EMPTY));
				mav.addObject(CartConstant.ERROR_MSG, error);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/ajaxCart", method = RequestMethod.GET)
	public ResponseEntity ajaxCart (HttpServletRequest pRequest, HttpServletResponse pResponse) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(true);
		Map<String, Object> data = new HashMap<>();
		data.put(CartConstant.ORDER_ITEM_LIMIT, getShoppingCartService().getMaxItemCount4Cart());
		Order order = getCurrentOrder(pRequest);
		data.put(CartConstant.CURRENT_ORDER, order);
		if (!ObjectUtils.isEmpty(order)) {
			synchronized (order) {
				getShoppingCartService().checkInventory(order);
				if (!getShoppingCartService().checkCartItemCount(order)) {
					LOGGER.debug("Cart item count had been reached limit");
					rb.addErrorMessage(ITEM_COUNT_LIMIE, getMessageValue(ERROR_ITEM_REACHED_LIMIT, StringUtils.EMPTY));
				}
			}
			if (StringUtils.equals(pRequest.getParameter("error"), ERROR_INV_CHECK_FAILED)) {
				String error = getMessageValue(ERROR_INV_CHECK_FAILED, StringUtils.EMPTY);
				rb.addErrorMessage(ITEM_INV_CHECK, error);
			}
			String oosProductIds = pRequest.getParameter("oosProdId");
			if (StringUtils.isNotBlank(oosProductIds)) {
				String oosProductNames = getShoppingCartService().convertProductIdsToProductNames(oosProductIds, "_");
				if (StringUtils.isNotBlank(oosProductNames)) {
					String error = oosProductNames.concat(getMessageValue(WARN_REMOVE_ITEM_NOTIFY, StringUtils.EMPTY));
					rb.addErrorMessage(ITEM_INV_CHECK, error);
				}
			}
		}
		rb.setData(data);
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}


	@RequestMapping(value = "/addItemToOrder")//, method = RequestMethod.POST)
	public ModelAndView addItemToOrderRedirect2Cart (HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                                 @RequestParam(value = "productId", required = true) int productId,
	                                                 @RequestParam(value = "easyBuy", required = false, defaultValue = "0") int easyBuy) {
		ModelAndView mav = new ModelAndView(getRedirectView(getURLMapping().getPDPUrl(String.valueOf(productId))));
		// check product
		Product product = getProductService().queryProduct(productId);
		if (!mShoppingCartService.checkProductValidity(product)) {
			LOGGER.debug("Stop add item to cart for product: {} not available", productId);
			mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_PROD_NOT_AVAILABLE, StringUtils.EMPTY));
			return mav;
		}
		Order order;
		if (easyBuy > 0) {
			User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
			if (user == null) {
				String redirectUrl = getUrlConfiguration().getLoginPage() + "?redirect="
						+ getUrlConfiguration().getPdpPage() + "/" + productId;
				mav.setViewName("redirect:" + redirectUrl);
				return mav;
			}
			order = getEasyBuyOrder(pRequest);
		} else {
			// check and generate order
			order = getCurrentOrder(pRequest, true);
		}
		if (order == null) {
			return mav;
		}
		synchronized (order) {
			if (!getShoppingCartService().ensureCartItemCapacity(order)) {
				LOGGER.debug("Stop add item to cart for product: {} because cart item count had been reached limit", productId);
				mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_ITEM_REACHED_LIMIT, StringUtils.EMPTY));
				return mav;
			}
			CommerceItem purchaseCommerceItem = order.getCommerceItemByProduct(productId);
			if (purchaseCommerceItem != null) {
				LOGGER.debug("Stop add item to cart for product: {} because cart item had been added to cart", productId);
				mav.addObject(CartConstant.ERROR_MSG, getMessageValue(ERROR_PROD_ADDED_TO_CART, StringUtils.EMPTY));
				return mav;
			}
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
					if (easyBuy > 0) {
						pRequest.getSession().setAttribute(CartConstant.ORDER_KEY_PREFIX + order.getId(), order);
						mav.setViewName(getRedirectView("/checkout/shipping"));
						mav.addObject(CartConstant.ORDER_ID, order.getId());
					}
				}
				getTransactionManager().commit(status);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/ajaxAddItemToOrder")//, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity ajaxAddItemToOrder (HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                          @RequestParam(value = "productId", required = true) int productId) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
		// check product
		Product product = getProductService().queryProduct(productId);
		if (!mShoppingCartService.checkProductValidity(product)) {
			LOGGER.debug("Stop add item to cart for product: {} not available", productId);
			rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_PROD_NOT_AVAILABLE);
			return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
		}
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		synchronized (order) {
			if (!getShoppingCartService().ensureCartItemCapacity(order)) {
				LOGGER.debug("Stop add item to cart for product: {} because cart item count had been reached limit", productId);
				rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_ITEM_REACHED_LIMIT);
				return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
			}
			CommerceItem purchaseCommerceItem = order.getCommerceItemByProduct(productId);
			if (purchaseCommerceItem != null) {
				LOGGER.debug("Stop add item to cart for product: {} because cart item had been added to cart", productId);
				rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_PROD_ADDED_TO_CART);
				return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
			}
			if (!getShoppingCartService().purchaseProduct(order, product)) {
				LOGGER.debug("Stop add item to cart for product: {} out of stock", productId);
				rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_PROD_OUT_STOCK);
				return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
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
					rb.setSuccess(true).setData(order);
				}
				getTransactionManager().commit(status);
			}
		}
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/removeItemFromOrder")//, method = RequestMethod.POST)
	public ModelAndView removeItemFromOrder (HttpServletRequest pRequest, HttpServletResponse pResponse,
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
	public ResponseEntity ajaxRemoveItemFromOrder (HttpServletRequest pRequest, HttpServletResponse pResponse,
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
		return new ResponseEntity(rb.setData(order).createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/ajaxRemoveItemsFromOrder")//, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity ajaxRemoveItemsFromOrder (HttpServletRequest pRequest, HttpServletResponse pResponse,
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
					rb.setData(order);
					getTransactionManager().commit(status);
				}
			}
		}
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getOrderItemCount", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getOrderItemCount (HttpServletRequest pRequest, HttpServletResponse pResponse) {
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
	public ModelAndView emptyCart (HttpServletRequest pRequest, HttpServletResponse pResponse) {
		ModelAndView mav = new ModelAndView(getRedirectView(CART));
		// check and generate order
		Order order = getCurrentOrder(pRequest, true);
		if (order == null || order.emptyOrder()) {
			LOGGER.debug("No need to empty items for empty order.");
			return mav;
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
				}
				getTransactionManager().commit(status);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/ajaxEmptyCart", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity ajaxEmptyCart (HttpServletRequest pRequest, HttpServletResponse pResponse) {
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

	protected Order getEasyBuyOrder (HttpServletRequest pRequest) {
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.error("Cannot get current user, please login firstly.");
			return null;
		}
		Order order = getOrderService().loadEasyBuyOrderWithoutItem(String.valueOf(currentUser.getId()));
		if (order == null) {
			LOGGER.debug("Get empty easy buy order by user id.");
			order = new Order(currentUser.getId().intValue(), OrderType.B2C_ORDER);
			order.setEasyBuy(true);
		}
		return order;
	}

	public ShoppingCartService getShoppingCartService () {
		return mShoppingCartService;
	}

	public void setShoppingCartService (final ShoppingCartService pShoppingCartService) {
		mShoppingCartService = pShoppingCartService;
	}

	public PriceOrderService getPriceOrderService () {
		return mPriceOrderService;
	}

	public void setPriceOrderService (final PriceOrderService pPriceOrderService) {
		mPriceOrderService = pPriceOrderService;
	}

	public ProductService getProductService () {
		return mProductService;
	}

	public void setProductService (final ProductService pProductService) {
		mProductService = pProductService;
	}

	public URLMapping getURLMapping () {
		return mURLMapping;
	}

	public void setURLMapping (URLMapping pURLMapping) {
		mURLMapping = pURLMapping;
	}

	public ResponseBuilderFactory getResponseBuilderFactory () {
		return mResponseBuilderFactory;
	}

	public void setResponseBuilderFactory (ResponseBuilderFactory pResponseBuilderFactory) {
		mResponseBuilderFactory = pResponseBuilderFactory;
	}

	public OrderService getOrderService () {
		return orderService;
	}

	public void setOrderService (OrderService orderService) {
		this.orderService = orderService;
	}

	public URLConfiguration getUrlConfiguration () {
		return urlConfiguration;
	}

	public void setUrlConfiguration (URLConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}

}
