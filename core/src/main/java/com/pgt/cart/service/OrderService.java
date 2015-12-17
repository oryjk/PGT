package com.pgt.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.dao.OrderMapper;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

	public Order loadOrder(Integer orderId) {
		if (orderId == null) {
			return null;
		}
		return getOrderMapper().loadOrder(orderId);
	}

	public Order loadEasyBuyOrderByUserId(String userId) {
		return getOrderMapper().loadEasyBuyOrderByUserId(userId);
	}

	public boolean updateOrder(Order order) {
		if (order == null) {
			return false;
		}
		getOrderMapper().updateOrder(order);
		return true;
	}

	public OrderMapper getOrderMapper() {
		return orderMapper;
	}

	public void setOrderMapper(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

}
