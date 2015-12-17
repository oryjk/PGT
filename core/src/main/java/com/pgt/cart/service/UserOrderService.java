package com.pgt.cart.service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.dao.UserOrderDao;
import com.pgt.internal.bean.pagination.InternalPagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yove on 11/8/2015.
 */
@Service(value = "userOrderService")
public class UserOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderService.class);

	@Resource(name = "userOrderDao")
	private UserOrderDao mUserOrderDao;

	public List<Order> queryOrderPage(final int pUserId, final int pOrderStatus, final InternalPagination pPagination) {
		long count = getUserOrderDao().queryOrderCount(pUserId, pOrderStatus, pPagination);
		LOGGER.debug("Get order count: {} with status: {}, keyword: {}", count, pOrderStatus, pPagination.getKeyword());
		pPagination.setCount(count);
		if (count > 0) {
			List<Order> orders = getUserOrderDao().queryOrderPage(pUserId, pOrderStatus, pPagination);
			pPagination.setResult(orders);
		} else {
			pPagination.setResult(Collections.EMPTY_LIST);
		}
		return (List<Order>) pPagination.getResult();
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
