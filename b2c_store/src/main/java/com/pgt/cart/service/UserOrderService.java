package com.pgt.cart.service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.dao.UserOrderDao;
import com.pgt.internal.bean.pagination.InternalPagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yove on 11/8/2015.
 */
@Service(value = "userOrderService")
public class UserOrderService {

	@Resource(name = "userOrderDao")
	private UserOrderDao mUserOrderDao;

	public List<Order> queryOrderPage(final int pUserId, final InternalPagination pPagination) {
		return getUserOrderDao().queryOrderPage(pUserId, pPagination);
	}

	public Order loadOrderHistory(final int pOrderId) {
		return getUserOrderDao().loadOrderHistory(pOrderId);
	}


	public UserOrderDao getUserOrderDao() {
		return mUserOrderDao;
	}

	public void setUserOrderDao(final UserOrderDao pUserOrderDao) {
		mUserOrderDao = pUserOrderDao;
	}
}
