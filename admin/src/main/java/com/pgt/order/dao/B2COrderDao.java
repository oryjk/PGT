package com.pgt.order.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.order.bean.B2COrderSearchVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 12/24/2015.
 */
@Repository(value = "B2COrderDao")
public interface B2COrderDao extends SqlMapper {

	long queryB2COrderCount(@Param("vo") B2COrderSearchVO pB2COrderSearchVO, @Param("pagination") InternalPagination pPagination);

	List<Order> queryB2COrderPage(@Param("vo") B2COrderSearchVO pB2COrderSearchVO, @Param("pagination") InternalPagination pPagination);
}
