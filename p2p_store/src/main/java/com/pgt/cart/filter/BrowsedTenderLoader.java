package com.pgt.cart.filter;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.constant.SessionConstant;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.service.TenderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yove on 3/23/2016.
 */
public class BrowsedTenderLoader extends ItemLoadInterceptor {


	@Autowired
	private TenderService mTenderService;

	@Override
	public boolean loadItems(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler) {
		if (!interceptRequest(pRequest)) {
			LOGGER.debug("Skip load browsed product for request URI not match intercept rules.");
			return true;
		}
		List<Integer> productIds = (List<Integer>) pRequest.getSession().getAttribute(SessionConstant.RECENT_PRODUCT_IDS);
		if (CollectionUtils.isEmpty(productIds)) {
			LOGGER.debug("No recent browsed products ids were found.");
			return true;
		}
		// append logic to load favourites & recently browsed products
		List<Tender> recentBrowsedProducts = queryTenders(productIds);
		pRequest.setAttribute(CartConstant.BROWSED_PRODUCTS, recentBrowsedProducts);
		return true;
	}

	private List<Tender> queryTenders(final List<Integer> pProductIds) {
		if (CollectionUtils.isEmpty(pProductIds)) {
			return Collections.emptyList();
		}
		List<Tender> result = new ArrayList<>(pProductIds.size());
		pProductIds.forEach(id -> result.add(getTenderService().queryTenderById(id, Boolean.FALSE)));
		return result;
	}

	public TenderService getTenderService() {
		return mTenderService;
	}

	public void setTenderService(final TenderService pTenderService) {
		mTenderService = pTenderService;
	}
}
