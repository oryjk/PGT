package com.pgt.cart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yove on 3/20/2016.
 */
@Service(value = "productBrowseTrackService")
public class P2PProductBrowseTrackService extends ProductBrowseTrackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(P2PProductBrowseTrackService.class);

	private String mTrackingURL = "/tender/";

	public P2PProductBrowseTrackService() {
		LOGGER.info("Track browsed product with URL that start with: {}", mTrackingURL);
	}

	@Override
	public boolean track(final HttpServletRequest pRequest) {
		return pRequest.getRequestURI().contains(mTrackingURL);
	}

	@Override
	public String getRequestedProductId(final HttpServletRequest pRequest) {
		int index = pRequest.getRequestURI().indexOf(mTrackingURL);
		if (index > -1) {
			String tenderId = pRequest.getRequestURI().substring(index + mTrackingURL.length());
			return tenderId;
		}
		return null;
	}
}
