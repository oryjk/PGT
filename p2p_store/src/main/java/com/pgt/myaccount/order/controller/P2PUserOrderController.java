package com.pgt.myaccount.order.controller;

import com.pgt.base.bean.MyAccountNavigationEnum;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.controller.TransactionBaseController;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.constant.Constants;
import com.pgt.myaccount.order.service.P2POrderSearchStatus;
import com.pgt.myaccount.order.service.P2PUserOrderService;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.service.TenderService;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 16/02/24.
 */
@RequestMapping("/myAccount")
@RestController
public class P2PUserOrderController extends TransactionBaseController implements P2POrderSearchStatus {

	private static final Logger LOGGER = LoggerFactory.getLogger(P2PUserOrderController.class);

	@Resource(name = "p2pUserOrderService")
	private P2PUserOrderService mUserOrderService;

	@Autowired
	private TenderService mTenderService;

	@RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
	public ModelAndView loadOrderHistory(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                     @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
	                                     @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
	                                     @RequestParam(value = "keyword", required = false) String keyword,
	                                     @RequestParam(value = "asc", required = false, defaultValue = "false") String asc,
	                                     @RequestParam(value = "status", required = false, defaultValue = "0") int status) {
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load order history.");
			return new ModelAndView(REDIRECT_LOGIN + "/myAccount/orderHistory");
		}
		LOGGER.debug("Query order history of user: {} for status: {}", currentUser.getId(), status);
		long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
		long caLong = RepositoryUtils.safeParse2LongId(capacity);
		boolean ascBoolean = Boolean.valueOf(asc);
		LOGGER.debug("Query order history with index: {}, capacity: {} and keyword: {} with order asc: {}", ciLong, caLong, keyword, ascBoolean);
		InternalPaginationBuilder ipb = new InternalPaginationBuilder().setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword);
		InternalPagination pagination = ipb.setSortFieldName("update_date").setAsc(ascBoolean).createInternalPagination();
		getUserOrderService().queryP2POrderPage(currentUser.getId().intValue(), status, pagination);
		ModelAndView mav = new ModelAndView("/my-account/order-history");
		mav.addObject(CartConstant.ORDER_HISTORY, pagination);
		return mav;
	}

	@RequestMapping(value = "/orderHistoryDetails", method = RequestMethod.GET)
	public ModelAndView loadOrderHistoryDetails(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                            @RequestParam(value = "orderId") String orderId) {
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load order history details.");
			return new ModelAndView(REDIRECT_LOGIN);
		}
		int orderIdInt = RepositoryUtils.safeParseId(orderId);
		if (!RepositoryUtils.idIsValid(orderIdInt)) {
			LOGGER.debug("Cannot load order with an invalid order id: {}", orderId);
			return new ModelAndView(REDIRECT_LOGIN);
		}
		Order order = getUserOrderService().loadOrderInformation(orderIdInt);
		Tender tender = getTenderService().queryTenderById(order.getP2pInfo().getTenderId(), Boolean.TRUE);
		ModelAndView mav = new ModelAndView("/my-account/order-history-detail");
		mav.addObject(CartConstant.ORDER_HISTORY_DETAIL, order);
		mav.addObject(CartConstant.ORDER_TENDER, tender);
		return mav;
	}

	@RequestMapping(value = "/browsedProducts")//, method = RequestMethod.GET)
	public ModelAndView recentlyBrowsedProducts(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		// check user login state
		User currentUser = getCurrentUser(pRequest);
		if (currentUser == null) {
			LOGGER.debug("Current session user has not sign, skip load recent browsed products.");
			return new ModelAndView("redirect:/user/login");
		}
		ModelAndView mav = new ModelAndView("/my-account/browsed-products");
		// left navigation for p2p
		mav.addObject(Constants.CURRENT_ACCOUNT_ITEM, MyAccountNavigationEnum.MY_RECENT_VIEW);
		return mav;
	}

	public P2PUserOrderService getUserOrderService() {
		return mUserOrderService;
	}

	public void setUserOrderService(final P2PUserOrderService pUserOrderService) {
		mUserOrderService = pUserOrderService;
	}

	public TenderService getTenderService() {
		return mTenderService;
	}

	public void setTenderService(final TenderService pTenderService) {
		mTenderService = pTenderService;
	}
}
