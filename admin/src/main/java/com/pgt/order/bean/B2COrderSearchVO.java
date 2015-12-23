package com.pgt.order.bean;

import com.pgt.cart.util.RepositoryUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Yove on 12/22/2015.
 */
public class B2COrderSearchVO {

	private static final Logger LOGGER = LoggerFactory.getLogger(B2COrderSearchVO.class);

	private static final FastDateFormat DT_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

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

	public B2COrderSearchVO setOrderId(final String pOrderId) {
		mOrderId = RepositoryUtils.safeParseId(pOrderId);
		return this;
	}

	public String getUserName() {
		return mUserName;
	}

	public B2COrderSearchVO setUserName(final String pUserName) {
		mUserName = pUserName;
		return this;
	}

	public Double getPriceBeg() {
		return mPriceBeg;
	}

	public B2COrderSearchVO setPriceBeg(final Double pPriceBeg) {
		mPriceBeg = pPriceBeg;
		return this;
	}

	public B2COrderSearchVO setPriceBeg(final String pPriceBeg) {
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


	public Double getPriceEnd() {
		return mPriceEnd;
	}

	public B2COrderSearchVO setPriceEnd(final Double pPriceEnd) {
		mPriceEnd = pPriceEnd;
		return this;
	}

	public B2COrderSearchVO setPriceEnd(final String pPriceEnd) {
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

	public Date getSubmitTimeBeg() {
		return mSubmitTimeBeg;
	}

	public B2COrderSearchVO setSubmitTimeBeg(final Date pSubmitTimeBeg) {
		mSubmitTimeBeg = pSubmitTimeBeg;
		return this;
	}

	public B2COrderSearchVO setSubmitTimeBeg(final String pSubmitTimeBeg) {
		if (StringUtils.isNotBlank(pSubmitTimeBeg)) {
			try {
				mSubmitTimeBeg = DT_FORMAT.getInstance().parse(pSubmitTimeBeg);
			} catch (ParseException pe) {
				StackTraceElement[] elements = pe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mSubmitTimeBeg = null;
			}
		}
		return this;
	}

	public Date getSubmitTimeEnd() {
		return mSubmitTimeEnd;
	}

	public B2COrderSearchVO setSubmitTimeEnd(final Date pSubmitTimeEnd) {
		mSubmitTimeEnd = pSubmitTimeEnd;
		return this;
	}

	public B2COrderSearchVO setSubmitTimeEnd(final String pSubmitTimeEnd) {
		if (StringUtils.isNotBlank(pSubmitTimeEnd)) {
			try {
				mSubmitTimeEnd = DT_FORMAT.getInstance().parse(pSubmitTimeEnd);
			} catch (ParseException pe) {
				StackTraceElement[] elements = pe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mSubmitTimeEnd = null;
			}
		}
		return this;
	}
}
