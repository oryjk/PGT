package com.pgt.cart.controller;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.UserOrderService;
import com.pgt.internal.bean.pagination.InternalPagination;
import com.pgt.internal.bean.pagination.InternalPaginationBuilder;
import com.pgt.internal.util.RepositoryUtils;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 11/8/2015.
 */
@RequestMapping("/myAccount")
@RestController
public class UserOrderController extends TransactionBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderController.class);

	@Resource(name = "userOrderService")
	private UserOrderService mUserOrderService;

	@RequestMapping(value = "/orderHistory")//, method = RequestMethod.GET)
	public ModelAndView loadOrderHistory(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
			@RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "asc", required = false, defaultValue = "true") String asc) {
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load order history.");
			return new ModelAndView("redirect:/user/login");
		}
		LOGGER.debug("Query order history of user: {}", currentUser.getId());
		long ciLong = RepositoryUtils.safeParse2Long(currentIndex);
		long caLong = RepositoryUtils.safeParse2Long(capacity);
		boolean ascBoolean = Boolean.valueOf(asc);
		LOGGER.debug("Query order history with index: {}, capacity: {} and keyword: {} with order asc: {}", ciLong, caLong, keyword, ascBoolean);
		InternalPaginationBuilder ipb = new InternalPaginationBuilder().setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword);
		InternalPagination pagination = ipb.setSortFieldName("update_date").setAsc(ascBoolean).createInternalPagination();
		getUserOrderService().queryOrderPage(currentUser.getId().intValue(), pagination);
		ModelAndView mav = new ModelAndView("/my-account/order-history");
		mav.addObject(CartConstant.ORDER_HISTORY, pagination);
		return mav;
	}

	@RequestMapping(value = "/orderHistoryDetails")//, method = RequestMethod.GET)
	public ModelAndView loadOrderHistoryDetails(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "orderId") String orderId) {
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load order history details.");
			return new ModelAndView("redirect:/user/login");
		}
		int orderIdInt = RepositoryUtils.safeParseId(orderId);
		if (!RepositoryUtils.idIsValid(orderIdInt)) {
			LOGGER.debug("Cannot load order with an invalid order id: {}", orderId);
			return new ModelAndView("redirect:/user/login");
		}
		Order order = getUserOrderService().loadOrderHistory(orderIdInt);
		ModelAndView mav = new ModelAndView("/my-account/order-history-detail");
		mav.addObject(CartConstant.ORDER_HISTORY_DETAIL, order);
		return mav;
	}


	public UserOrderService getUserOrderService() {
		return mUserOrderService;
	}

	public void setUserOrderService(final UserOrderService pUserOrderService) {
		mUserOrderService = pUserOrderService;
	}
}
