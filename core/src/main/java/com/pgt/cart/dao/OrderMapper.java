package com.pgt.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;

@Repository
public interface OrderMapper extends SqlMapper {

	Order loadOrder(Integer orderId);

	Order loadEasyBuyOrderByUserId(String userId);

	List<CommerceItem> selectCommerceItemByOrderId(Integer orderId);

	void deleteAllCommerceItems(Integer orderId);

	void updateOrder(Order pOrder);

	void updateOrderStatus(Order order);

	int getUncompleteOrderAmount(@Param("userId") int userId, @Param("type")  int type, @Param("orderId") int orderId);
}
