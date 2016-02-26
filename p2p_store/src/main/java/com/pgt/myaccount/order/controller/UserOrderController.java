package com.pgt.myaccount.order.controller;

import com.pgt.cart.controller.TransactionBaseController;
import com.pgt.cart.service.UserOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Yove on 16/02/24.
 */
@RequestMapping("/myAccount")
//@RestController("UserOrderController")
public class UserOrderController extends TransactionBaseController {

	@Resource(name = "userOrderService")
	private UserOrderService mUserOrderService;

	public UserOrderService getUserOrderService() {
		return mUserOrderService;
	}

	public void setUserOrderService(final UserOrderService pUserOrderService) {
		mUserOrderService = pUserOrderService;
	}
}
