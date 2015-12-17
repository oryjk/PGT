package com.pgt.cart.controller;

import com.pgt.cart.bean.ResponseBean;
import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.cart.service.UserOrderService;
import com.pgt.internal.bean.pagination.InternalPagination;
import com.pgt.internal.bean.pagination.InternalPaginationBuilder;
import com.pgt.internal.util.RepositoryUtils;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class UserOrderController extends TransactionBaseController implements UserOrderMessages {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderController.class);

	@Resource(name = "userOrderService")
	private UserOrderService mUserOrderService;

	@Resource(name = "responseBuilderFactory")
	private ResponseBuilderFactory mResponseBuilderFactory;

	@RequestMapping(value = "/orderHistory")//, method = RequestMethod.GET)
	public ModelAndView loadOrderHistory(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
			@RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "asc", required = false, defaultValue = "true") String asc,
			@RequestParam(value = "status", required = false, defaultValue = "0") int status) {
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
		getUserOrderService().querySubmittedOrderPage(currentUser.getId().intValue(), status, pagination);
		ModelAndView mav = new ModelAndView("/my-account/order-history");
		mav.addObject(CartConstant.ORDER_HISTORY, pagination);
		return mav;
	}

	@RequestMapping(value = "/ajaxOrderHistory")//, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity ajaxLoadOrderHistory(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
			@RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "asc", required = false, defaultValue = "true") String asc,
			@RequestParam(value = "status", required = false, defaultValue = "0") int status) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load order history.");
			rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_USER_LOGIN_REQUIRED);
			return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
		}
		LOGGER.debug("Query order history of user: {}", currentUser.getId());
		long ciLong = RepositoryUtils.safeParse2Long(currentIndex);
		long caLong = RepositoryUtils.safeParse2Long(capacity);
		boolean ascBoolean = Boolean.valueOf(asc);
		LOGGER.debug("Query order history with index: {}, capacity: {} and keyword: {} with order asc: {}", ciLong, caLong, keyword, ascBoolean);
		InternalPaginationBuilder ipb = new InternalPaginationBuilder().setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword);
		InternalPagination pagination = ipb.setSortFieldName("update_date").setAsc(ascBoolean).createInternalPagination();
		getUserOrderService().querySubmittedOrderPage(currentUser.getId().intValue(), status, pagination);
		rb.setSuccess(true).setData(pagination);
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}

	@RequestMapping(value = "/browsedProducts")//, method = RequestMethod.GET)
	public ModelAndView RecentlyBrowsedProducts(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load recent browsed products.");
			return new ModelAndView("redirect:/user/login");
		}
		return new ModelAndView("/my-account/browsed-products");
	}

	public UserOrderService getUserOrderService() {
		return mUserOrderService;
	}

	public void setUserOrderService(final UserOrderService pUserOrderService) {
		mUserOrderService = pUserOrderService;
	}

	public ResponseBuilderFactory getResponseBuilderFactory() {
		return mResponseBuilderFactory;
	}

	public void setResponseBuilderFactory(final ResponseBuilderFactory pResponseBuilderFactory) {
		mResponseBuilderFactory = pResponseBuilderFactory;
	}
}
