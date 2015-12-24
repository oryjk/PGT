package com.pgt.order.service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.order.bean.B2COrderSearchVO;
import com.pgt.order.dao.B2COrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yove on 12/21/2015.
 */
@Service("B2COrderService")
public class B2COrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(B2COrderService.class);

	@Resource(name = "B2COrderDao")
	private B2COrderDao mB2COrderDao;

	public List<Order> queryB2COrderPage(final B2COrderSearchVO pB2COrderSearchVO, final InternalPagination pPagination) {
		long count = getB2COrderDao().queryB2COrderCount(pB2COrderSearchVO, pPagination);
		pPagination.setCount(count);
		LOGGER.debug("Get b2c-orders count: {}", count);
		if (count > 0) {
			List<Order> orders = getB2COrderDao().queryB2COrderPage(pB2COrderSearchVO, pPagination);
			pPagination.setResult(orders);
		} else {
			pPagination.setResult(Collections.emptyList());
		}
		return (List<Order>) pPagination.getResult();
	}

	public B2COrderDao getB2COrderDao() {
		return mB2COrderDao;
	}

	public void setB2COrderDao(final B2COrderDao pB2COrderDao) {
		mB2COrderDao = pB2COrderDao;
	}
}
