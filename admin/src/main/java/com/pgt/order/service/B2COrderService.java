package com.pgt.order.service;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Delivery;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.order.bean.B2COrderSearchVO;
import com.pgt.order.dao.B2COrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 12/21/2015.
 */
public class B2COrderService implements OrderStatus {

	private static final Logger LOGGER = LoggerFactory.getLogger(B2COrderService.class);

	@Resource(name = "B2COrderDao")
	private B2COrderDao mB2COrderDao;

	private Map<Integer, String> mOrderStatusMapping;

	private int[] mPreStatus4UnpaidOrder = new int[] { PAID };
	private int mUnpaidStatus = FILLED_SHIPPING;
	private int[] mPreStatus4CompleteOrder = new int[] { INITIAL, FILLED_SHIPPING, PAID };
	private int mCompleteStatus = NO_PENDING_ACTION;
	private int[] mPreStatus4CancelOrder = new int[] { INITIAL, FILLED_SHIPPING, PAID };
	private int mCancelStatus = CANCEL;
	private int mTransitStatus = TRANSIT;

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

	public Order loadOrder(final int pOrderId) {
		return getB2COrderDao().loadOrder(pOrderId);
	}

	public boolean updateOrder2UnpaidStatus(Order pOrder) {
		for (int status : getPreStatus4UnpaidOrder()) {
			if (status == pOrder.getStatus()) {
				return getB2COrderDao().updateOrder2Status(pOrder.getId(), getUnpaidStatus()) > 0;
			}
		}
		LOGGER.debug("Failed to change order: {} to unpaid status for current status: {}", pOrder.getId(), pOrder.getStatus());
		return false;
	}

	public boolean updateOrder2CompleteStatus(Order pOrder) {
		for (int status : getPreStatus4CompleteOrder()) {
			if (status == pOrder.getStatus()) {
				return getB2COrderDao().updateOrder2Status(pOrder.getId(), getCompleteStatus()) > 0;
			}
		}
		LOGGER.debug("Failed to change order: {} to complete status for current status: {}", pOrder.getId(), pOrder.getStatus());
		return false;
	}

	public boolean updateOrder2CancelStatus(Order pOrder) {
		for (int status : getPreStatus4CancelOrder()) {
			if (status == pOrder.getStatus()) {
				return getB2COrderDao().updateOrder2Status(pOrder.getId(), getCancelStatus()) > 0;
			}
		}
		LOGGER.debug("Failed to change order: {} to cancel status for current status: {}", pOrder.getId(), pOrder.getStatus());
		return false;
	}



	public boolean updateOrder2TransitStatus(Order pOrder) {
		for (int status : getPreStatus4CancelOrder()) {
			if (status == pOrder.getStatus()) {
				return getB2COrderDao().updateOrder2Status(pOrder.getId(), getTransitStatus()) > 0;
			}
		}
		LOGGER.debug("Failed to change order: {} to cancel status for current status: {}", pOrder.getId(), pOrder.getStatus());
		return false;
	}

	public boolean updateOrderStatus(int orderId, int status) {
		return getB2COrderDao().updateOrder2Status(orderId, status) > 0;
	}

	public boolean createDelivery(final Delivery pDelivery) {
		return getB2COrderDao().createDelivery(pDelivery) > 0;
	}

	public boolean markCommerceItemReceived(final int pCommerceItemId) {
		return getB2COrderDao().updateCommerceItemAsReceived(pCommerceItemId) > 0;
	}

	public CommerceItem loadCommerceItem(final int pCidInt) {
		return getB2COrderDao().loadCommerceItem(pCidInt);
	}

	public B2COrderDao getB2COrderDao() {
		return mB2COrderDao;
	}

	public void setB2COrderDao(final B2COrderDao pB2COrderDao) {
		mB2COrderDao = pB2COrderDao;
	}

	public Map<Integer, String> getOrderStatusMapping() {
		return mOrderStatusMapping;
	}

	public void setOrderStatusMapping(final Map<Integer, String> pOrderStatusMapping) {
		mOrderStatusMapping = pOrderStatusMapping;
	}

	public int[] getPreStatus4UnpaidOrder() {
		return mPreStatus4UnpaidOrder;
	}

	public void setPreStatus4UnpaidOrder(final int[] pPreStatus4UnpaidOrder) {
		mPreStatus4UnpaidOrder = pPreStatus4UnpaidOrder;
	}

	public int getUnpaidStatus() {
		return mUnpaidStatus;
	}

	public void setUnpaidStatus(final int pUnpaidStatus) {
		mUnpaidStatus = pUnpaidStatus;
	}

	public int[] getPreStatus4CompleteOrder() {
		return mPreStatus4CompleteOrder;
	}

	public void setPreStatus4CompleteOrder(final int[] pPreStatus4CompleteOrder) {
		mPreStatus4CompleteOrder = pPreStatus4CompleteOrder;
	}

	public int getCompleteStatus() {
		return mCompleteStatus;
	}

	public void setCompleteStatus(final int pCompleteStatus) {
		mCompleteStatus = pCompleteStatus;
	}

	public int[] getPreStatus4CancelOrder() {
		return mPreStatus4CancelOrder;
	}

	public void setPreStatus4CancelOrder(final int[] pPreStatus4CancelOrder) {
		mPreStatus4CancelOrder = pPreStatus4CancelOrder;
	}

	public int getCancelStatus() {
		return mCancelStatus;
	}

	public void setCancelStatus(final int pCancelStatus) {
		mCancelStatus = pCancelStatus;
	}

	public int getTransitStatus() {
		return mTransitStatus;
	}

	public void setTransitStatus(int mTransitStatus) {
		this.mTransitStatus = mTransitStatus;
	}
}
