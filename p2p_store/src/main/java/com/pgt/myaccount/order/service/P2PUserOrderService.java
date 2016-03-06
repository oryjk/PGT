package com.pgt.myaccount.order.service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.myaccount.order.dao.P2PUserOrderDao;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yove on 16/02/24.
 */
@Service(value = "p2pUserOrderService")
public class P2PUserOrderService implements P2POrderSearchStatus {

	@Resource(name = "p2pUserOrderDao")
	private P2PUserOrderDao mUserOrderDao;

	public boolean isValidStatus(int pStatus) {
		return ArrayUtils.contains(STATUS_COLLECTION, pStatus);
	}

	public void queryP2POrderPage(final int pUserId, final int pStatus, final InternalPagination pPagination) {
		// check status firstly
		if (isValidStatus(pStatus)) {
			long count = queryP2POrderCountWithStatus(pUserId, pStatus, pPagination);
			pPagination.setCount(count);
			if (count > 0) {
				List<Order> orders = queryP2POrderPageWithStatus(pUserId, pStatus, pPagination);
				pPagination.setResult(orders);
			} else {
				pPagination.setResult(Collections.EMPTY_LIST);
			}
		} else {
			pPagination.setResult(Collections.EMPTY_LIST);
		}
	}

	private List<Order> queryP2POrderPageWithStatus(final int pUserId, final int pStatus, final InternalPagination pPagination) {
		if (pStatus >= DURING_PAWNING && pStatus < END_PAWNING) {
			return getUserOrderDao().queryDuringPawningOrderPage(pUserId, pStatus, pPagination);
		} else if (pStatus >= END_PAWNING && pStatus < REDEEM_PAWNING) {
			return getUserOrderDao().queryEndPawningOrderPage(pUserId, pStatus, pPagination);
		} else if (pStatus >= REDEEM_PAWNING) {
			return getUserOrderDao().queryRedeemPawningOrderPage(pUserId, pStatus, pPagination);
		}
		// else query all p2p orders
		return getUserOrderDao().queryOrderPage(pUserId, pPagination);
	}

	private long queryP2POrderCountWithStatus(final int pUserId, final int pStatus, final InternalPagination pPagination) {
		if (pStatus >= DURING_PAWNING && pStatus < END_PAWNING) {
			return getUserOrderDao().queryDuringPawningOrderCount(pUserId, pStatus, pPagination);
		} else if (pStatus >= END_PAWNING && pStatus < REDEEM_PAWNING) {
			return getUserOrderDao().queryEndPawningOrderCount(pUserId, pStatus, pPagination);
		} else if (pStatus >= REDEEM_PAWNING) {
			return getUserOrderDao().queryRedeemPawningOrderCount(pUserId, pStatus, pPagination);
		}
		// else query all p2p order count
		return getUserOrderDao().queryOrderCount(pUserId, pPagination);
	}

	public Order loadOrderInformation(final int pOrderId) {
		return getUserOrderDao().loadOrderInformation(pOrderId);
	}


	public P2PUserOrderDao getUserOrderDao() {
		return mUserOrderDao;
	}

	public void setUserOrderDao(final P2PUserOrderDao pUserOrderDao) {
		mUserOrderDao = pUserOrderDao;
	}

}
