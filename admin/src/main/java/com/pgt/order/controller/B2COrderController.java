package com.pgt.order.controller;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.order.bean.B2COrderSearchVO;
import com.pgt.order.service.B2COrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 12/21/2015.
 */
@RequestMapping("/order")
@RestController
public class B2COrderController extends InternalTransactionBaseController implements OrderStatus {

	private static final Logger LOGGER = LoggerFactory.getLogger(B2COrderController.class);

	@Resource(name = "B2COrderService")
	private B2COrderService mB2COrderService;

	@RequestMapping(value = "/order-list")//, method = RequestMethod.GET)
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
			@RequestParam(value = "submitTimeEnd", required = false) String submitTimeEnd
	) {
		long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
		long caLong = RepositoryUtils.safeParse2LongId(capacity);
		LOGGER.debug("Query b2c-orders at index: {} with capacity: {} by sort filed: {} and asc: {}", ciLong, caLong, sortFieldName, asc);
		B2COrderSearchVO searchVO = B2COrderSearchVO.getInstance().setOrderId(id).setUserName(userName);
		searchVO.setPriceBeg(priceBeg).setPriceEnd(priceEnd);
		searchVO.setSubmitTimeBeg(submitTimeBeg).setSubmitTimeEnd(submitTimeEnd);
		LOGGER.debug("Query b2c-orders with search conditions: {}", searchVO);
		InternalPaginationBuilder ipb = new InternalPaginationBuilder();
		InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setSortFieldName(sortFieldName).setAsc(asc).createInternalPagination();
		getB2COrderService().queryB2COrderPage(searchVO, pagination);
		ModelAndView mav = new ModelAndView("/b2c-order/b2c-order-list");
		mav.addObject(ResponseConstant.B2C_ORDER_PAGE, pagination);
		return mav;
	}

	@RequestMapping(value = "/order-info")//, method = RequestMethod.GET)
	public ModelAndView loadOrderHistoryDetails(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "id") String orderId) {
		int orderIdInt = RepositoryUtils.safeParseId(orderId);
		Order order = getB2COrderService().loadOrder(orderIdInt);
		ModelAndView mav = new ModelAndView("/b2c-order/b2c-order");
		mav.addObject(ResponseConstant.B2C_ORDER, order);
		return mav;
	}

	@RequestMapping(value = "/change-order-status")
	public ModelAndView changeOrderStatus(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "status", required = true) String status) {
		ModelAndView mav = new ModelAndView("redirect:/b2c-order/b2c-orders");
		int idInt = RepositoryUtils.safeParseId(id);
		Order order = getB2COrderService().loadOrder(idInt);
		if (order == null) {
			mav.addObject(ResponseConstant.ERROR_MSG, "Cannot find order with id: " + id);
			return mav;
		}
		int statusInt = RepositoryUtils.safeParseId(status);
		LOGGER.debug("Try to change status from: {} to: {} for order: {}", order.getStatus(), status, id);
		TransactionStatus ts = ensureTransaction();
		try {
			boolean result = false;
			if (statusInt == getB2COrderService().getUnpaidStatus()) {
				result = getB2COrderService().updateOrder2UnpaidStatus(order);
			} else if (statusInt == getB2COrderService().getCompleteStatus()) {
				result = getB2COrderService().updateOrder2CompleteStatus(order);
			} else if (statusInt == getB2COrderService().getCancelStatus()) {
				result = getB2COrderService().updateOrder2CancelStatus(order);
			}
			if (!result) {
				ts.setRollbackOnly();
			}
		} catch (Exception e) {
			LOGGER.error("Cannot change status to: {} for order: {}", status, id, e);
			ts.setRollbackOnly();
		} finally {
			getTransactionManager().commit(ts);
		}
		return mav;
	}

	public B2COrderService getB2COrderService() {
		return mB2COrderService;
	}

	public void setB2COrderService(final B2COrderService pB2COrderService) {
		mB2COrderService = pB2COrderService;
	}
}
