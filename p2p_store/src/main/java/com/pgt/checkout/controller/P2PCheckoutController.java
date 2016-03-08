package com.pgt.checkout.controller;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderType;
import com.pgt.cart.exception.OrderPersistentException;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.configuration.URLConfiguration;
import com.pgt.inventory.LockInventoryException;
import com.pgt.inventory.service.InventoryService;
import com.pgt.order.P2POrderService;
import com.pgt.product.bean.Product;
import com.pgt.session.SessionHelper;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.service.TenderService;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Samli on 2016/1/16.
 */

@RestController
@RequestMapping("/order")
public class P2PCheckoutController {
	private static final int NO_ERROR = 0;

	private static final int NO_PRODUCT_IDS = 1;

	private static final int NO_QUANTITIES = 2;

	private static final int ID_QUANTITY_NOT_MATCH = 3;

	private static final int BLANK_PRODUCT_ID = 4;

	private static final int BLANK_QUANTITY = 5;

	private static final int PRODUCT_NOT_EXIST = 6;

	private static final int INVEST_TOTAL_NOT_ENOUGH = 7;

	private static final Logger LOGGER = LoggerFactory.getLogger(P2PCheckoutController.class);

	@Resource(name = "p2pOrderService")
	private P2POrderService orderService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private TenderService tenderService;

	@Autowired
	private URLConfiguration urlConfiguration;

	@RequestMapping(value = "/create")
	public ModelAndView createOrder (HttpServletRequest pRequest, HttpServletResponse pResponse) {
//		User user = SessionHelper.getUser(pRequest, pResponse);
//		LOGGER.debug("============= P2PCheckoutController#createOrder start =============");
//		if (null == user) {
//			ModelAndView modelAndView = new ModelAndView("redirect:" + getUrlConfiguration().getLoginPage());
//			LOGGER.debug("no user redirect to login page");
//			LOGGER.debug("============= P2PCheckoutController#createOrder end =============");
//			return modelAndView;
//		}
		User user = new User();
		user.setId(1L);
		String tenderIdStr = pRequest.getParameter("tenderId");
		String[] productIds = pRequest.getParameterValues("productIds");
		String[] quantities = pRequest.getParameterValues("quantities");
		if (StringUtils.isBlank(tenderIdStr) || !StringUtils.isNumeric(tenderIdStr)) {
			ModelAndView modelAndView = new ModelAndView("redirect:" + getUrlConfiguration().getHomePage());
			LOGGER.debug("tenderId is not a number, redirect to home page");
			LOGGER.debug("============= P2PCheckoutController#createOrder end =============");
			return modelAndView;
		}
		int tenderId = Integer.valueOf(tenderIdStr);

		// check if has not pay order
		if (getOrderService().hasUncompleteOrder(user.getId().intValue(), OrderType.P2P_ORDER)) {
			LOGGER.debug("user has incomplete order redirect to tender page");
			LOGGER.debug("============= P2PCheckoutController#createOrder end =============");
			// TODO redirect to tendId
		}
		getInventoryService().ensureTransaction();

		Tender tender = getOrderService().queryTenderById(tenderId);
		List<Product> relatedProducts = getTenderService().queryTenderProduct(tenderId);

		// check productIds is valid
		int errorCode = isProductIdsValid(productIds, quantities, relatedProducts, tender);
		if (NO_ERROR != errorCode) {
			LOGGER.debug("product parameter is not correct redirect to tender page");
			LOGGER.debug("============= P2PCheckoutController#createOrder end =============");
			getInventoryService().setAsRollback();
			// TODO redirect to tendId
		}
		// create order
		try {
			Pair<Order, P2PInfo> result = getOrderService().createP2POrder(user, tender, relatedProducts, productIds, quantities);
			Order order = result.getLeft();
			// check inventory
			getInventoryService().lockInventory(order);
		} catch (OrderPersistentException  | LockInventoryException ex ) {
			LOGGER.error(ex.getMessage(), ex);
			getInventoryService().setAsRollback();
			// TODO RETURN TENDER PAGE
		} finally {
			getInventoryService().commit();
		}
		// TODO SHIPPING PAGE
		return null;
	}

	private int isProductIdsValid(String[] productIds, String[] quantities, List<Product> relatedProducts, Tender tender) {
		if (productIds == null || productIds.length == 0) {
			LOGGER.error("productIds is null or length == 0");
			return NO_PRODUCT_IDS;
		}

		if (quantities == null || quantities.length == 0) {
			LOGGER.error("quantities is null or length == 0");
			return NO_QUANTITIES;
		}

		for (String productId : productIds) {
			if (StringUtils.isBlank(productId)) {
				LOGGER.error("productIds has blank data");
				return BLANK_PRODUCT_ID;
			}
		}

		for (String quantity : quantities) {
			if (StringUtils.isBlank(quantity)) {
				LOGGER.error("quantities has blank data");
				return BLANK_QUANTITY;
			}
		}
		if (productIds.length != quantities.length) {
			LOGGER.error("productIds length and quantities length is not match");
			return ID_QUANTITY_NOT_MATCH;
		}


		if (null != productIds) {
			// check relatedProducts need contains productIds
			if (null == relatedProducts || relatedProducts.isEmpty()) {
				LOGGER.error("no relate product");
				return PRODUCT_NOT_EXIST;
			}
			for (String productId : productIds) {
				if (null == productId) {
					continue;
				}
				int id = 0;
				try {
					id = Integer.valueOf(productId);
				} catch (Exception e) {
					LOGGER.error("productId is not number");
					return PRODUCT_NOT_EXIST;
				}
				boolean match = false;
				for (Product relatedProduct : relatedProducts) {
					if (null == relatedProduct || null == relatedProduct.getProductId()) {
						continue;
					}
					if (id == relatedProduct.getProductId()) {
						match = true;
						break;
					}
				}
				if (!match) {
					LOGGER.error("productId[" + productId + "] is match.");
					return PRODUCT_NOT_EXIST;
				}
			}
		}
		return NO_ERROR;
	}


	public ModelAndView createOrderAbondon (HttpServletRequest pRequest, HttpServletResponse pResponse) {
		User user = SessionHelper.getUser(pRequest, pResponse);
		if (null == user) {
			//  TODOo REDIRECT TO LOGIN
		}
		String tenderIdStr = pRequest.getParameter("tenderId");
		String placeQuantityStr = pRequest.getParameter("placeQuantity");
		String[] productIds = pRequest.getParameterValues("productIds");
		if (StringUtils.isBlank(tenderIdStr) || !StringUtils.isNumeric(tenderIdStr)) {
			// TODOo
		}
		if (StringUtils.isBlank(placeQuantityStr) || !StringUtils.isNumeric(placeQuantityStr)) {
			// TODOo
		}
		int tenderId = Integer.valueOf(tenderIdStr);
		int placeQuantity = Integer.valueOf(placeQuantityStr);

		// check if has not pay order
		if (getOrderService().hasUncompleteOrder(user.getId().intValue(), OrderType.P2P_ORDER)) {
			// TODOo redirect to tendId
		}
		// TODOo QUERY TENDER BY ID
		Tender tender = null;
		// TODOo QUERY related product
		List<Product> relatedProducts = null;

		// check productIds is valid
		int errorCode = isProductIdsValid(productIds, relatedProducts, tender, placeQuantity);
		if (NO_ERROR != errorCode) {
			// TODOo redirect to tendId
		}
		// check inventory

		// create order
		// TODOo EXCEIPTOIN
		try {
			getOrderService().createP2POrder(user, tender, relatedProducts, productIds, placeQuantity);
		} catch (OrderPersistentException e) {
			e.printStackTrace();
		}


		return null;
	}

	/**
	 * @param productIds
	 * @param relatedProducts
	 * @param tender
	 * @param placeQuantity
	 * @return error code
	 */
	private int isProductIdsValid (String[] productIds, List<Product> relatedProducts, Tender tender, int placeQuantity) {
		if (null != productIds) {
			// check relatedProducts need contains productIds
			if (null == relatedProducts || relatedProducts.isEmpty()) {
				// TODOo LOG
				return PRODUCT_NOT_EXIST;
			}
			double total = 0D;
			for (String productId : productIds) {
				if (null == productId) {
					continue;
				}
				int id = 0;
				try {
					id = Integer.valueOf(productId);
				} catch (Exception e) {
					// TODOo log
					return PRODUCT_NOT_EXIST;
				}
				boolean match = false;
				for (Product relatedProduct : relatedProducts) {
					if (null == relatedProduct || null == relatedProduct.getProductId()) {
						continue;
					}
					if (id == relatedProduct.getProductId()) {
						match = true;
						total += relatedProduct.getSalePrice();
						// TODOo ROUND
						break;
					}
				}
				if (!match) {
					// TODOo log
					return PRODUCT_NOT_EXIST;
				}
			}
			double investTotal = tender.getUnitPrice() * placeQuantity;
			// TODOo ROUND;
			if (total > investTotal) {
				// TODOo log
				return INVEST_TOTAL_NOT_ENOUGH;
			}
		}
		return NO_ERROR;
	}

	public P2POrderService getOrderService () {
		return orderService;
	}

	public void setOrderService (P2POrderService orderService) {
		this.orderService = orderService;
	}

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public URLConfiguration getUrlConfiguration() {
		return urlConfiguration;
	}

	public void setUrlConfiguration(URLConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}

	public TenderService getTenderService() {
		return tenderService;
	}

	public void setTenderService(TenderService tenderService) {
		this.tenderService = tenderService;
	}
}
