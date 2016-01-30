package com.pgt.cart.filter;

import com.pgt.cart.bean.Order;
import com.pgt.cart.service.PriceOrderService;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		// get the persist order in initial status
		List<Order> initialOrders = getShoppingCartService().loadInitialOrders(userId);
		return false;
	}

	@Override
	public void postHandle (final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion (final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {

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
