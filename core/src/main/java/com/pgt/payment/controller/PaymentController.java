package com.pgt.payment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
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

	@Resource(name = "transactionLogService")
	private TransactionLogService transactionLogService;

	@Autowired
	private URLConfiguration urlConfiguration;

	@Resource(name = "paymentService")
	private PaymentService paymentService;

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
		Order order = (Order) pRequest.getSession().getAttribute(CartConstant.CURRENT_ORDER);
		if (null == order) {
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
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
		Order order = (Order) pRequest.getSession().getAttribute(CartConstant.CURRENT_ORDER);
		if (null == order) {
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
			return modelAndView;
		}

		// check the order is paid
		PaymentGroup paymentGroup = getPaymentService().findPaymentGroupByOrderId(order.getId());
		if (null != paymentGroup && PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
			ModelAndView modelAndView = new ModelAndView("redirect:/payment/complete");
			return modelAndView;
		}

		String method = pRequest.getParameter("method");
		if (PaymentConstants.METHOD_YEEPAY.equals(method)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/yeepay/yeepayB2cPay");
			return modelAndView;
		} else if (PaymentConstants.METHOD_ALIPAY.equals(method)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/alipay/request");
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
			// TODO: REDIRECT TO LOGIN
			ModelAndView modelAndView = new ModelAndView("redirect:" + getUrlConfiguration().getLoginPage());
			return modelAndView;
		}
		Order order = (Order) pRequest.getSession().getAttribute(CartConstant.CURRENT_ORDER);
		if (null == order) {
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
			return modelAndView;
		}
		// check the order is paid
		ModelAndView modelAndView = null;
		PaymentGroup paymentGroup = getPaymentService().findPaymentGroupByOrderId(order.getId());
		if (null != paymentGroup && PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
			modelAndView = new ModelAndView("/payment/complete");
			int orderId = order.getId();
			double total = order.getTotal();
			modelAndView.addObject("orderId", orderId);
			modelAndView.addObject("orderTotal", total);
			//TODO: switch order
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

}