package com.pgt.cart.filter;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.PriceOrderService;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yove on 1/28/2016.
 */
public class ShoppingCartResolveInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartResolveInterceptor.class);

	@Resource(name = "shoppingCartService")
	private ShoppingCartService mShoppingCartService;

	@Resource(name = "priceOrderService")
	private PriceOrderService mPriceOrderService;


	@Override
	public boolean preHandle (final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		// check session user existence
		User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
		// no order for user without login state
		if (user == null) {
			LOGGER.debug("No user found from session, skip merge order.");
			return true;
		}

		int userId = user.getId().intValue();
		LOGGER.debug("User: {} found from session.", userId);
		Order sessionOrder = (Order) request.getSession().getAttribute(CartConstant.CURRENT_ORDER);

		// get the persist order in initial status
		List<Order> initialOrders = getShoppingCartService().loadInitialOrders(userId);
		Order finalInitialOrder = null;

		if (CollectionUtils.isEmpty(initialOrders)) {
			// if no initial orders persisted, new order and persist
			if (sessionOrder != null) {
				finalInitialOrder = sessionOrder;
			} else {
				finalInitialOrder = new Order(userId, getShoppingCartService().getShoppingCartConfiguration().getDefaultOrderType());
			}
			finalInitialOrder.setUserId(userId);
			getPriceOrderService().priceOrder(finalInitialOrder);
			// persist new order
			getShoppingCartService().persistInitialOrder(finalInitialOrder);

		} else if (initialOrders.size() == 1) {
			// common scenario, only one initial order
			finalInitialOrder = initialOrders.get(0);
			if (sessionOrder != null && !sessionOrder.emptyOrder()) {
				getShoppingCartService().mergeOrder(finalInitialOrder, sessionOrder);
				// persist order changes
				getPriceOrderService().priceOrder(finalInitialOrder);
				getShoppingCartService().persistInitialOrder(finalInitialOrder);
			} else {
				getPriceOrderService().priceOrder(finalInitialOrder);
			}
		} else {
			// initial orders count great than 1 need merge, find order which has the minimum id
			finalInitialOrder = initialOrders.remove(0);
			// add session order to pending merge order list if it's not empty
			if (sessionOrder != null && !sessionOrder.emptyOrder()) {
				initialOrders.add(sessionOrder);
			}
			// merge other orders to order which has minimum id
			getShoppingCartService().mergeOrders(finalInitialOrder, initialOrders);
			getPriceOrderService().priceOrder(finalInitialOrder);
			// persist order changes
			getShoppingCartService().persistInitialOrder(finalInitialOrder);
			// remove the other initial order
			List<Integer> pendingRemovedOrderIds = extraOrderIds(initialOrders);
			if (CollectionUtils.isNotEmpty(pendingRemovedOrderIds)) {
				getShoppingCartService().deleteOrders(pendingRemovedOrderIds);
			}
		}

		// set to request
		request.setAttribute(CartConstant.CURRENT_ORDER, finalInitialOrder);
		// clean session order
		if (sessionOrder != null) {
			request.getSession().removeAttribute(CartConstant.CURRENT_ORDER);
		}
		return true;
	}

	@Override
	public void postHandle (final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion (final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {

	}

	public List<Integer> extraOrderIds (List<Order> pOrders) {
		List<Integer> orderIds = new ArrayList<>(pOrders.size());
		pOrders.forEach(order -> {
			orderIds.add(order.getId());
		});
		return orderIds;
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
}
