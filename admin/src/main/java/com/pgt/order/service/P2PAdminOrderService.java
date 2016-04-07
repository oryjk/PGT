package com.pgt.order.service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.order.bean.OrderSearchVO;
import com.pgt.order.dao.P2POrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yove on 4/7/2016.
 */
@Service(value = "P2PAdminOrderService")
public class P2PAdminOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(P2PAdminOrderService.class);

	@Resource(name = "P2POrderDao")
	private P2POrderDao mP2POrderDao;

	public List<Order> queryP2POrderPage(final OrderSearchVO pOrderSearchVO, final InternalPagination pPagination) {
		long count = getP2POrderDao().queryP2POrderCount(pOrderSearchVO, pPagination);
		pPagination.setCount(count);
		LOGGER.debug("Get b2c-orders count: {}", count);
		if (count > 0) {
			List<Order> orders = getP2POrderDao().queryP2POrderPage(pOrderSearchVO, pPagination);
			pPagination.setResult(orders);
		} else {
			pPagination.setResult(Collections.emptyList());
		}
		return (List<Order>) pPagination.getResult();
	}

	public P2POrderDao getP2POrderDao() {
		return mP2POrderDao;
	}

	public void setP2POrderDao(final P2POrderDao pP2POrderDao) {
		mP2POrderDao = pP2POrderDao;
	}
}
