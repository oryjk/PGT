package com.pgt.order.controller;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.order.P2POrderService;
import com.pgt.order.bean.OrderSearchVO;
import com.pgt.order.service.B2COrderService;
import com.pgt.order.service.P2PAdminOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Yove on 16/2/7.
 */
@RequestMapping("/order-p2p")
@RestController
public class P2POrderController extends InternalTransactionBaseController implements OrderStatus {

	private static final Logger LOGGER = LoggerFactory.getLogger(P2POrderController.class);

	@Resource(name = "B2COrderService")
	private B2COrderService mB2COrderService;

	@Resource(name = "p2pOrderService")
	private P2POrderService p2pOrderService;

	@Resource(name = "P2PAdminOrderService")
	private P2PAdminOrderService mP2PAdminOrderService;


	@RequestMapping(value = "/order-list", method = RequestMethod.GET)
	public ModelAndView listB2COrders(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                  @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
	                                  @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
	                                  @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
	                                  @RequestParam(value = "asc", required = false, defaultValue = "true") boolean asc,
	                                  @RequestParam(value = "id", required = false) String id,
	                                  @RequestParam(value = "userName", required = false) String userName,
	                                  @RequestParam(value = "priceBeg", required = false) String priceBeg,
	                                  @RequestParam(value = "priceEnd", required = false) String priceEnd,
	                                  @RequestParam(value = "submitTimeBeg", required = false) String submitTimeBeg,
	                                  @RequestParam(value = "submitTimeEnd", required = false) String submitTimeEnd) {
		// permission verify
		boolean pass = verifyPermission(pRequest);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		// main logic
		long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
		long caLong = RepositoryUtils.safeParse2LongId(capacity);
		LOGGER.debug("Query p2p-orders at index: {} with capacity: {} by sort filed: {} and asc: {}", ciLong, caLong, sortFieldName, asc);
		OrderSearchVO searchVO = OrderSearchVO.getInstance().setOrderId(id).setUserName(userName);
		searchVO.setPriceBeg(priceBeg).setPriceEnd(priceEnd);
		searchVO.setSubmitTimeBeg(submitTimeBeg).setSubmitTimeEnd(submitTimeEnd);
		LOGGER.debug("Query p2p-orders with search conditions: {}", searchVO);
		InternalPaginationBuilder ipb = new InternalPaginationBuilder();
		InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setSortFieldName(sortFieldName).setAsc(asc)
				.createInternalPagination();
		getP2PAdminOrderService().queryP2POrderPage(searchVO, pagination);
		ModelAndView mav = new ModelAndView("/p2p-order/p2p-order-list");
		mav.addObject(ResponseConstant.P2P_ORDER_PAGE, pagination);
		return mav;
	}

	@RequestMapping(value = "/order-info", method = RequestMethod.GET)
	public ModelAndView loadOrderHistoryDetails(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                            @RequestParam(value = "id") String orderId) {
		// permission verify
		boolean pass = verifyPermission(pRequest);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		// main logic
		int orderIdInt = RepositoryUtils.safeParseId(orderId);
		Order order = getB2COrderService().loadOrder(orderIdInt);
		P2PInfo info = getP2pOrderService().queryP2PInfoByOrderId(orderIdInt);
		ModelAndView mav = new ModelAndView("/p2p-order/order-detail");
		mav.addObject(ResponseConstant.P2P_ORDER, order);
		mav.addObject(ResponseConstant.P2P_INFO, info);
		return mav;
	}

	@RequestMapping(value = "/complete-item", method = RequestMethod.GET)
	public ModelAndView completeItem(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		String orderIdStr = pRequest.getParameter("orderId");
		String occupyStr = pRequest.getParameter("occupy");
		int orderId = Integer.valueOf(orderIdStr);
		Order order = getB2COrderService().loadOrder(orderId);
		boolean occupy = Boolean.valueOf(occupyStr);
		getP2pOrderService().completeOrder(order, occupy, new Date());
		order = getB2COrderService().loadOrder(orderId);
		P2PInfo info = getP2pOrderService().queryP2PInfoByOrderId(orderId);
		ModelAndView mav = new ModelAndView("/p2p-order/order-detail");
		mav.addObject(ResponseConstant.P2P_ORDER, order);
		mav.addObject(ResponseConstant.P2P_INFO, info);
		return mav;
	}


	@RequestMapping(value = "/change-order-status", method = RequestMethod.GET)
	public ModelAndView changeOrderStatus(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		String orderIdStr = pRequest.getParameter("orderId");
		String statusStr = pRequest.getParameter("status");
		int orderId = Integer.valueOf(orderIdStr);
		Order order = getB2COrderService().loadOrder(orderId);
		int status = Integer.valueOf(statusStr);
		getB2COrderService().updateOrderStatus(orderId, status);
		P2PInfo info = getP2pOrderService().queryP2PInfoByOrderId(orderId);
		ModelAndView mav = new ModelAndView("/p2p-order/order-detail");
		mav.addObject(ResponseConstant.P2P_ORDER, order);
		mav.addObject(ResponseConstant.P2P_INFO, info);
		return mav;
	}


	public B2COrderService getB2COrderService() {
		return mB2COrderService;
	}

	public void setB2COrderService(B2COrderService mB2COrderService) {
		this.mB2COrderService = mB2COrderService;
	}

	public P2POrderService getP2pOrderService() {
		return p2pOrderService;
	}

	public void setP2pOrderService(P2POrderService p2pOrderService) {
		this.p2pOrderService = p2pOrderService;
	}

	public P2PAdminOrderService getP2PAdminOrderService() {
		return mP2PAdminOrderService;
	}

	public void setP2PAdminOrderService(final P2PAdminOrderService pP2PAdminOrderService) {
		mP2PAdminOrderService = pP2PAdminOrderService;
	}
}
