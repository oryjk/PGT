package com.pgt.payment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.OrderService;
import com.pgt.cart.service.UserOrderService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.service.PaymentService;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.user.bean.User;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

	@Resource(name = "transactionLogService")
	private TransactionLogService transactionLogService;

	@Autowired
	private URLConfiguration urlConfiguration;

	@Resource(name = "paymentService")
	private PaymentService paymentService;

	@Resource(name = "userOrderService")
	private UserOrderService userOrderService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/gateway", method = RequestMethod.GET)
	public ModelAndView gatewayPage(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		// This code is for test ---- start ----
//		Order mockOrder = new Order();
//		mockOrder.setTotal(0.01);
//		List<CommerceItem> commerceItems = new ArrayList<CommerceItem>();
//		CommerceItem item = new CommerceItem();
//		item.setName("蜜蜡");
//		commerceItems.add(item);
//		item = new CommerceItem();
//		item.setName("南红");
//		commerceItems.add(item);
//		mockOrder.setCommerceItems(commerceItems);
//		mockOrder.setUserId(1);
//		pRequest.getSession().setAttribute(CartConstant.CURRENT_ORDER, mockOrder);
		// This code is for test ---- end ----
		
		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (null == user) {
			// TODO: REDIRECT TO LOGIN
			ModelAndView modelAndView = new ModelAndView("redirect:" + getUrlConfiguration().getLoginPage());
			return modelAndView;
		}
		Order order = getOrderService().getSessionOrder(pRequest);
		if (getOrderService().isInvalidOrder(user, order)) {
			ModelAndView modelAndView = new ModelAndView("redirect:"+getUrlConfiguration().getShoppingCartPage());
			return modelAndView;
		}
		// check the order is paid
		ModelAndView modelAndView = null;
		PaymentGroup paymentGroup = getPaymentService().findPaymentGroupByOrderId(order.getId());
		if (null != paymentGroup && PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
			modelAndView = new ModelAndView("redirect:/payment/complete");
		} else {
			modelAndView = new ModelAndView("/payment/gateway");
		}
		modelAndView.addObject("order", order);
		return modelAndView;
	}

	// TODO CHANGE TO POST
	@RequestMapping(value = "/gateway", method = RequestMethod.POST)
	public ModelAndView gateway(HttpServletRequest pRequest, HttpServletResponse pResponse) {

		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (null == user) {
			// TODO: REDIRECT TO LOGIN
			ModelAndView modelAndView = new ModelAndView("redirect:" + getUrlConfiguration().getLoginPage());
			return modelAndView;
		}
		
		Order order = getOrderService().getSessionOrder(pRequest);
		if (getOrderService().isInvalidOrder(user, order)) {
			ModelAndView modelAndView = new ModelAndView("redirect:"+getUrlConfiguration().getShoppingCartPage());
			return modelAndView;
		}
		String method = pRequest.getParameter("method");
		if(!PaymentConstants.METHOD_YEEPAY.equals(method) && !PaymentConstants.METHOD_ALIPAY.equals(method) ){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/payment/gateway");
			modelAndView.addObject(CartConstant.ORDER_ID, order.getId());
			return modelAndView;
		}
		getOrderService().removeOrderFromSession(String.valueOf(order.getId()), pRequest);
		// check the order is paid
		PaymentGroup paymentGroup = getPaymentService().findPaymentGroupByOrderId(order.getId());
		if (null != paymentGroup && PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
			ModelAndView modelAndView = new ModelAndView("redirect:/payment/complete");
			return modelAndView;
		}

	
		if (PaymentConstants.METHOD_YEEPAY.equals(method)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/yeepay/yeepayB2cPay?orderId=" + order.getId());
			return modelAndView;
		} else if (PaymentConstants.METHOD_ALIPAY.equals(method)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/alipay/request?orderId=" + order.getId());
			return modelAndView;
		}

		String jspPath = null;
		ModelAndView mav = null;
		return mav;
	}
	
	
	
	@RequestMapping(value = "/complete")
	public ModelAndView complete(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (null == user) {
			LOGGER.debug("no user in session redrict to login page.");
			ModelAndView modelAndView = new ModelAndView("redirect:" + getUrlConfiguration().getLoginPage());
			return modelAndView;
		}
		String orderIdStr = pRequest.getParameter("orderId");
		LOGGER.debug("orderIdStr: " + orderIdStr);
		if (StringUtils.isBlank(orderIdStr)) {
			throw new IllegalArgumentException("orderId is blank");
		}
		int orderId = 0;
		try {
			orderId = Integer.valueOf(orderIdStr);
		} catch (Exception e) {
			throw new IllegalArgumentException("orderId is not integer.");
		}
		LOGGER.debug("orderId: " + orderId);
		Order order = getUserOrderService().loadOrderHistory(orderId);
		if (null == order) {
			LOGGER.debug("no order found by id: " + orderId + " redirect to shopping cart page");
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
			return modelAndView;
		}
		if (order.getUserId() != user.getId().intValue()) {
			LOGGER.debug("user on order not match user in session redriect to shopping cart. user on order: " + order.getUserId() + " user in session" + user.getId().intValue() );
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
			return modelAndView;
		}

		// check the order is paid
		ModelAndView modelAndView = null;
		PaymentGroup paymentGroup = getPaymentService().findPaymentGroupByOrderId(order.getId());
		if (null != paymentGroup && PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
			modelAndView = new ModelAndView("/payment/complete");
			double total = order.getTotal();
			modelAndView.addObject("orderId", orderId);
			modelAndView.addObject("orderTotal", total);
			pRequest.getSession().setAttribute(CartConstant.CURRENT_ORDER, null);
		} else {
			modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
		}
		modelAndView.addObject("order", order);
		return modelAndView;
	}

	public TransactionLogService getTransactionLogService() {
		return transactionLogService;
	}

	public void setTransactionLogService(TransactionLogService transactionLogService) {
		this.transactionLogService = transactionLogService;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public URLConfiguration getUrlConfiguration() {
		return urlConfiguration;
	}

	public void setUrlConfiguration(URLConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}

	public UserOrderService getUserOrderService() {
		return userOrderService;
	}

	public void setUserOrderService(UserOrderService userOrderService) {
		this.userOrderService = userOrderService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
}
