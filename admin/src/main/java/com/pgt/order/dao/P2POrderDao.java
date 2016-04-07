package com.pgt.order.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.order.bean.OrderSearchVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 4/7/2016.
 */
@Repository(value = "P2POrderDao")
public interface P2POrderDao extends SqlMapper {

	long queryP2POrderCount(@Param("vo") OrderSearchVO pOrderSearchVO, @Param("pagination") InternalPagination pPagination);

	List<Order> queryP2POrderPage(@Param("vo") OrderSearchVO pOrderSearchVO, @Param("pagination") InternalPagination pPagination);
}
