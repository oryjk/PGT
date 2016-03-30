package com.pgt.myaccount.order.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 16/02/24.
 */
@Repository(value = "p2pUserOrderDao")
public interface P2PUserOrderDao extends SqlMapper {

	long queryOrderCount(@Param("userId") int pUserId, @Param("pagination") InternalPagination pPagination);

	List<Order> queryOrderPage(@Param("userId") int pUserId, @Param("pagination") InternalPagination pPagination);

	long queryDuringPawningOrderCount(@Param("userId") int pUserId, @Param("status") int pStatus, @Param("pagination") InternalPagination pPagination);

	List<Order> queryDuringPawningOrderPage(@Param("userId") int pUserId, @Param("status") int pStatus, @Param("pagination") InternalPagination pPagination);

	long queryEndPawningOrderCount(@Param("userId") int pUserId, @Param("status") int pStatus, @Param("pagination") InternalPagination pPagination);

	List<Order> queryEndPawningOrderPage(@Param("userId") int pUserId, @Param("status") int pStatus, @Param("pagination") InternalPagination pPagination);

	long queryRedeemPawningOrderCount(@Param("userId") int pUserId, @Param("status") int pStatus, @Param("pagination") InternalPagination pPagination);

	List<Order> queryRedeemPawningOrderPage(@Param("userId") int pUserId, @Param("status") int pStatus, @Param("pagination") InternalPagination pPagination);

	Order loadOrderInformation(@Param("orderId") int pOrderId);
}
