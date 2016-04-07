package com.pgt.order.bean;

import com.pgt.cart.util.RepositoryUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yove on 12/22/2015.
 */
public class OrderSearchVO {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSearchVO.class);

	private static final DateFormat DT_FORMAT = new SimpleDateFormat("yyyy-MM-dd H:mm");

	private int mOrderId;
	private String mUserName;
	private Double mPriceBeg;
	private Double mPriceEnd;
	private Date mSubmitTimeBeg;
	private Date mSubmitTimeEnd;

	private OrderSearchVO() {

	}

	public static OrderSearchVO getInstance() {
		return new OrderSearchVO();
	}

	@Override
	public String toString() {
		return "OrderSearchVO{" +
				"mOrderId=" + mOrderId +
				", mUserName='" + mUserName + '\'' +
				", mPriceBeg=" + mPriceBeg +
				", mPriceEnd=" + mPriceEnd +
				", mSubmitTimeBeg=" + mSubmitTimeBeg +
				", mSubmitTimeEnd=" + mSubmitTimeEnd +
				'}';
	}

	public int getOrderId() {
		return mOrderId;
	}

	public OrderSearchVO setOrderId(final String pOrderId) {
		mOrderId = RepositoryUtils.safeParseId(pOrderId);
		return this;
	}

	public OrderSearchVO setOrderId(final int pOrderId) {
		mOrderId = pOrderId;
		return this;
	}

	public String getUserName() {
		return mUserName;
	}

	public OrderSearchVO setUserName(final String pUserName) {
		mUserName = pUserName;
		return this;
	}

	public Double getPriceBeg() {
		return mPriceBeg;
	}

	public OrderSearchVO setPriceBeg(final String pPriceBeg) {
		if (StringUtils.isNotBlank(pPriceBeg)) {
			try {
				mPriceBeg = new Double(pPriceBeg);
			} catch (NumberFormatException nfe) {
				StackTraceElement[] elements = nfe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mPriceBeg = null;
			}
		}
		return this;
	}

	public OrderSearchVO setPriceBeg(final Double pPriceBeg) {
		mPriceBeg = pPriceBeg;
		return this;
	}

	public Double getPriceEnd() {
		return mPriceEnd;
	}

	public OrderSearchVO setPriceEnd(final String pPriceEnd) {
		if (StringUtils.isNotBlank(pPriceEnd)) {
			try {
				mPriceEnd = new Double(pPriceEnd);
			} catch (NumberFormatException nfe) {
				StackTraceElement[] elements = nfe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mPriceEnd = null;
			}
		}
		return this;
	}

	public OrderSearchVO setPriceEnd(final Double pPriceEnd) {
		mPriceEnd = pPriceEnd;
		return this;
	}

	public Date getSubmitTimeBeg() {
		return mSubmitTimeBeg;
	}

	public OrderSearchVO setSubmitTimeBeg(final String pSubmitTimeBeg) {
		if (StringUtils.isNotBlank(pSubmitTimeBeg)) {
			try {
				mSubmitTimeBeg = DT_FORMAT.parse(pSubmitTimeBeg);
			} catch (ParseException pe) {
				StackTraceElement[] elements = pe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mSubmitTimeBeg = null;
			}
		}
		return this;
	}

	public OrderSearchVO setSubmitTimeBeg(final Date pSubmitTimeBeg) {
		mSubmitTimeBeg = pSubmitTimeBeg;
		return this;
	}

	public Date getSubmitTimeEnd() {
		return mSubmitTimeEnd;
	}

	public OrderSearchVO setSubmitTimeEnd(final String pSubmitTimeEnd) {
		if (StringUtils.isNotBlank(pSubmitTimeEnd)) {
			try {
				mSubmitTimeEnd = DT_FORMAT.parse(pSubmitTimeEnd);
			} catch (ParseException pe) {
				StackTraceElement[] elements = pe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mSubmitTimeEnd = null;
			}
		}
		return this;
	}

	public OrderSearchVO setSubmitTimeEnd(final Date pSubmitTimeEnd) {
		mSubmitTimeEnd = pSubmitTimeEnd;
		return this;
	}
}
