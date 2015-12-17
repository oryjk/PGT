package com.pgt.cart.bean;

import com.pgt.internal.util.RepositoryUtils;
import com.pgt.shipping.bean.ShippingVO;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Yove on 10/26/2015.
 */
public class Order {

	private static final String PATTERN_AMOUNT = "%.2f";

	private int mId;
	private int mUserId;
	private int mStatus = OrderStatus.INITIAL;
	private List<CommerceItem> mCommerceItems = new ArrayList<>();
	private ShippingVO mShippingVO;
	private double mShippingFee;
	private double mSubtotal;
	private double mTotal;
	private String mUserComments;
	private Date mCreationDate = new Date();
	private Date mUpdateDate;
	private Date mSubmitDate;

	public boolean emptyOrder() {
		return CollectionUtils.isEmpty(mCommerceItems);
	}

	public int getCommerceItemCount() {
		if (mCommerceItems instanceof List) {
			return mCommerceItems.size();
		}
		return 0;
	}

	public CommerceItem getCommerceItemByProduct(int pProductId) {
		CommerceItem commerceItem = null;
		if (!emptyOrder()) {
			for (CommerceItem ci : mCommerceItems) {
				if (ci.getReferenceId() != pProductId) {
					continue;
				}
				commerceItem = ci;
				break;
			}
		}
		return commerceItem;
	}

	public List<CommerceItem> emptyCommerceItems() {
		List<CommerceItem> removedCommerceItems = mCommerceItems;
		mCommerceItems = new ArrayList<>();
		return removedCommerceItems;
	}

	public CommerceItem removeCommerceItemByProduct(final int pProductId) {
		CommerceItem commerceItem = null;
		if (!emptyOrder()) {
			Iterator<CommerceItem> it = mCommerceItems.iterator();
			while (it.hasNext()) {
				CommerceItem ci = it.next();
				if (ci.getReferenceId() != pProductId) {
					continue;
				}
				it.remove();
				return ci;
			}
		}
		return commerceItem;
	}

	public List<CommerceItem> getPersistedCommerceItems() {
		if (emptyOrder()) {
			return Collections.EMPTY_LIST;
		}
		List<CommerceItem> persistentCommerceItems = new ArrayList<>(mCommerceItems.size());
		mCommerceItems.forEach(ci -> {
			if (RepositoryUtils.idIsValid(ci.getId())) {
				persistentCommerceItems.add(ci);
			}
		});
		return persistentCommerceItems;
	}

	public List<CommerceItem> getTransientCommerceItems() {
		if (emptyOrder()) {
			return Collections.EMPTY_LIST;
		}
		List<CommerceItem> transientCommerceItems = new ArrayList<>(mCommerceItems.size());
		mCommerceItems.forEach(ci -> {
			if (!RepositoryUtils.idIsValid(ci.getId())) {
				transientCommerceItems.add(ci);
			}
		});
		return transientCommerceItems;
	}

	public void resetCommerceItemIndex() {
		if (emptyOrder()) {
			return;
		}
		Collections.sort(mCommerceItems);
		for (int i = 0; i < mCommerceItems.size(); i++) {
			mCommerceItems.get(i).setIndex(i);
		}
	}

	public Set<Integer> populateProductIdSet() {
		if (emptyOrder()) {
			return Collections.EMPTY_SET;
		}
		Set<Integer> productIds = new HashSet<>();
		mCommerceItems.forEach(ci -> {
			productIds.add(ci.getReferenceId());
		});
		return productIds;
	}

	public void resetOrderPrice() {
		mSubtotal = 0d;
		mShippingFee = 0d;
		mTotal = 0d;
	}

	@Override
	public String toString() {
		return "Order{" +
				"mId=" + mId +
				", mUserId=" + mUserId +
				", mStatus=" + mStatus +
				", mCommerceItems=" + mCommerceItems +
				", mShippingVO=" + mShippingVO +
				", mShippingFee=" + mShippingFee +
				", mSubtotal=" + mSubtotal +
				", mTotal=" + mTotal +
				", mUserComments='" + mUserComments + '\'' +
				", mCreationDate=" + mCreationDate +
				", mUpdateDate=" + mUpdateDate +
				", mSubmitDate=" + mSubmitDate +
				'}';
	}

	public String getTotalDisplay() {
		return String.format(PATTERN_AMOUNT, getTotal());
	}

	public String getSubtotalDisplay() {
		return String.format(PATTERN_AMOUNT, getSubtotal());
	}

	public int getId() {
		return mId;
	}

	public void setId(final int pId) {
		mId = pId;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(final int pUserId) {
		mUserId = pUserId;
	}

	public int getStatus() {
		return mStatus;
	}

	public void setStatus(final int pStatus) {
		mStatus = pStatus;
	}

	public List<CommerceItem> getCommerceItems() {
		return mCommerceItems;
	}

	public void setCommerceItems(final List<CommerceItem> pCommerceItems) {
		mCommerceItems = pCommerceItems;
	}

	public ShippingVO getShippingVO() {
		return mShippingVO;
	}

	public void setShippingVO(ShippingVO shippingVO) {
		this.mShippingVO = shippingVO;
	}

	public double getShippingFee() {
		return mShippingFee;
	}

	public void setShippingFee(final double pShippingFee) {
		mShippingFee = pShippingFee;
	}

	public double getSubtotal() {
		return mSubtotal;
	}

	public void setSubtotal(final double pSubtotal) {
		mSubtotal = pSubtotal;
	}

	public double getTotal() {
		return mTotal;
	}

	public void setTotal(final double pTotal) {
		mTotal = pTotal;
	}

	public Date getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Date pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Date getUpdateDate() {
		return mUpdateDate;
	}

	public void setUpdateDate(final Date pUpdateDate) {
		mUpdateDate = pUpdateDate;
	}

	public Date getSubmitDate() {
		return mSubmitDate;
	}

	public void setSubmitDate(final Date pSubmitDate) {
		mSubmitDate = pSubmitDate;
	}

	public String getUserComments() {
		return mUserComments;
	}

	public void setUserComments(final String pUserComments) {
		mUserComments = pUserComments;
	}


}
