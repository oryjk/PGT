package com.pgt.order.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Delivery;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.order.bean.OrderSearchVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 12/24/2015.
 */
@Repository(value = "B2COrderDao")
public interface B2COrderDao extends SqlMapper {

	long queryB2COrderCount(@Param("vo") OrderSearchVO pOrderSearchVO, @Param("pagination") InternalPagination pPagination);

	List<Order> queryB2COrderPage(@Param("vo") OrderSearchVO pOrderSearchVO, @Param("pagination") InternalPagination pPagination);

	Order loadOrder(int pOrderId);

	int updateOrder2Status(@Param("orderId") int pOrderId, @Param("status") int pStatus);

	CommerceItem loadCommerceItem(int pCommerceItemId);

	int createDelivery(Delivery pDelivery);

	int updateCommerceItemAsReceived(int pCommerceItemId);
}
