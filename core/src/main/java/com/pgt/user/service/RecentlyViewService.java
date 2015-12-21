package com.pgt.user.service;

import com.pgt.constant.Constants;
import com.pgt.cart.constant.SessionConstant;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 12/10/15.
 */

@Service
public class RecentlyViewService {

	@Resource(name = "esSearchService")
	private ESSearchService mESSearchService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RecentlyViewService.class);

	public List<SearchHit> getRecentBrowsedProducts(HttpSession session) {
		List<Integer> productIds = (List<Integer>) session.getAttribute(SessionConstant.RECENT_PRODUCT_IDS);
		List<SearchHit> browsedProducts = new ArrayList<>();
		if (ObjectUtils.isEmpty(productIds)) {
			LOGGER.debug("The recent browsed products is empty.");
			return browsedProducts;
		}
		productIds.stream().forEach(integer -> {
			ESTerm esTerm = new ESTerm();
			esTerm.setTermValue(String.valueOf(integer));
			esTerm.setPropertyName(Constants.PRODUCT_ID);
			SearchResponse searchResponse = getESSearchService().findProducts(esTerm, null, null, null, null, null, null);
			SearchHits searchHits = searchResponse.getHits();
			if (!ObjectUtils.isEmpty(searchHits)) {
				if (!ObjectUtils.isEmpty(searchHits.getHits())) {
					SearchHit searchHit = searchHits.getHits()[0];
					browsedProducts.add(searchHit);
				}
			}
		});
		LOGGER.debug("The recent browsed products size is {}.", browsedProducts.size());
		return browsedProducts;
	}

	public ESSearchService getESSearchService() {
		return mESSearchService;
	}

	public void setESSearchService(final ESSearchService pESSearchService) {
		mESSearchService = pESSearchService;
	}
}
