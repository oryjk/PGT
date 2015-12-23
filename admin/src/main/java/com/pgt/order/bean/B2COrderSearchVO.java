package com.pgt.order.bean;

import com.pgt.cart.util.RepositoryUtils;

import java.util.Date;

/**
 * Created by Yove on 12/22/2015.
 */
public class B2COrderSearchVO {

	private int mOrderId;
	private String mUserName;
	private Double mPriceBeg;
	private Double mPriceEnd;
	private Date mSubmitTimeBeg;
	private Date mSubmitTimeEnd;

	public int getOrderId() {
		return mOrderId;
	}

	public B2COrderSearchVO setOrderId(final int pOrderId) {
		mOrderId = pOrderId;
		return this;
	}

	public void setOrderId(final String pOrderId) {
		mOrderId = RepositoryUtils.safeParseId(pOrderId);
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(final String pUserName) {
		mUserName = pUserName;
	}

	public Double getPriceBeg() {
		return mPriceBeg;
	}

	public void setPriceBeg(final Double pPriceBeg) {
		mPriceBeg = pPriceBeg;
	}

	public Double getPriceEnd() {
		return mPriceEnd;
	}

	public void setPriceEnd(final Double pPriceEnd) {
		mPriceEnd = pPriceEnd;
	}

	public Date getSubmitTimeBeg() {
		return mSubmitTimeBeg;
	}

	public void setSubmitTimeBeg(final Date pSubmitTimeBeg) {
		mSubmitTimeBeg = pSubmitTimeBeg;
	}

	public Date getSubmitTimeEnd() {
		return mSubmitTimeEnd;
	}

	public void setSubmitTimeEnd(final Date pSubmitTimeEnd) {
		mSubmitTimeEnd = pSubmitTimeEnd;
	}
}
