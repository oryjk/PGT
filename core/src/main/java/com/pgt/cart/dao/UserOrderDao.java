package com.pgt.cart.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.BrowsedProductVO;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 11/8/2015.
 */
@Repository(value = "userOrderDao")
public interface UserOrderDao extends SqlMapper {

	long querySubmittedOrderCount(@Param("userId") int pUserId, @Param("orderStatus") int pOrderStatus, @Param("pagination") InternalPagination pPagination);

	List<Order> querySubmittedOrderPage(@Param("userId") int pUserId, @Param("orderStatus") int pOrderStatus, @Param("pagination") InternalPagination pPagination);

	Order loadOrderHistory(@Param("orderId") int pOrderId);

	List<BrowsedProductVO> queryBrowsedProducts(@Param("userId") int pUserId);

	int resetBrowsedProductUpdateDate(@Param("browsedProductId") int pBrowsedProductId);

	int replaceBrowsedProductRecord(@Param("browsedProductId") int pBrowsedProductId, @Param("productId") int pNewProductId);

	int createBrowsedProductRecord(BrowsedProductVO pBrowsedProduct);

	int resetBrowsedProductsBatchUpdateDate(List<Integer> pBrowsedProductIds);

}
