package com.pgt.cart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yove on 3/20/2016.
 */
@Service(value = "productBrowseTrackService")
public class B2CProductBrowseTrackService extends ProductBrowseTrackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(B2CProductBrowseTrackService.class);

	private boolean mInitialized;

	private String mTrackingURL;

	public boolean track(HttpServletRequest pRequest) {
		if (!mInitialized) {
			// avoid initialize multiple times
			synchronized (this) {
				mTrackingURL = pRequest.getContextPath() + getURLConfiguration().getPdpPage();
				mInitialized = true;
			}
			LOGGER.info("Track browsed product with URL that start with: {}", mTrackingURL);
		}
		return pRequest.getRequestURI().startsWith(mTrackingURL);
	}


	public String getRequestedProductId(HttpServletRequest pRequest) {
		int index = pRequest.getRequestURI().indexOf(mTrackingURL);
		if (index > -1) {
			String productId = pRequest.getRequestURI().substring(index + mTrackingURL.length() + 1);
			return productId;
		}
		return null;
	}
}
