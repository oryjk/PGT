package com.pgt.cart.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Yove on 1/2/2016.
 */
public class Delivery {

	private static final Logger LOGGER = LoggerFactory.getLogger(Delivery.class);

	private static final FastDateFormat DT_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

	private int mCommerceItemId;
	private String mConsignor;
	private boolean mDelivered;
	private boolean mReceived;
	private String mLogistics;
	private Date mDeliveryTime;
	private String mTrackingNo;

	public int getCommerceItemId() {
		return mCommerceItemId;
	}

	public void setCommerceItemId(final int pCommerceItemId) {
		mCommerceItemId = pCommerceItemId;
	}

	public String getConsignor() {
		return mConsignor;
	}

	public void setConsignor(final String pConsignor) {
		mConsignor = pConsignor;
	}

	public boolean isDelivered() {
		return mDelivered;
	}

	public void setDelivered(final boolean pDelivered) {
		mDelivered = pDelivered;
	}

	public boolean isReceived() {
		return mReceived;
	}

	public void setReceived(final boolean pReceived) {
		mReceived = pReceived;
	}

	public String getLogistics() {
		return mLogistics;
	}

	public void setLogistics(final String pLogistics) {
		mLogistics = pLogistics;
	}

	public Date getDeliveryTime() {
		return mDeliveryTime;
	}

	public void setDeliveryTime(final Date pDeliveryTime) {
		mDeliveryTime = pDeliveryTime;
	}

	public String getTrackingNo() {
		return mTrackingNo;
	}

	public void setTrackingNo(final String pTrackingNo) {
		mTrackingNo = pTrackingNo;
	}

	public void setDeliveryTime(final String pSubmitTimeBeg) {
		if (StringUtils.isNotBlank(pSubmitTimeBeg)) {
			try {
				mDeliveryTime = DT_FORMAT.getInstance().parse(pSubmitTimeBeg);
			} catch (ParseException pe) {
				StackTraceElement[] elements = pe.getStackTrace();
				LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
				mDeliveryTime = null;
			}
		}
	}
}