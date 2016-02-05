package com.pgt.cart.filter;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.constant.SessionConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.cart.service.RecommendService;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.product.bean.Product;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yove on 12/3/2015.
 */
public class RecommendInterceptor extends ItemLoadInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecommendInterceptor.class);

	@Resource(name = "recommendService")
	private RecommendService mRecommendService;

	@Resource(name = "productBrowseTrackService")
	private ProductBrowseTrackService mBrowseTrackService;

	@Resource(name = "productBrowseTrackService")
	private ProductBrowseTrackService mProductBrowseTrackService;


	@Override
	public boolean loadItems(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler) {
		List<Integer> productIds = (List<Integer>) pRequest.getSession().getAttribute(SessionConstant.RECENT_PRODUCT_IDS);
		if (CollectionUtils.isEmpty(productIds)) {
			productIds = new ArrayList<>();
			LinkedList<String> browsedProductIds = getProductBrowseTrackService().getBrowsedProductIdsFromCookies(pRequest);
			for (String id : browsedProductIds) {
				productIds.add(Integer.parseInt(id));
			}
			pRequest.getSession().setAttribute(SessionConstant.RECENT_PRODUCT_IDS, productIds);
		}
		LOGGER.debug("Get recently browsed products: " + productIds);
		String idString = getBrowseTrackService().getRequestedProductId(pRequest);
		int currentProductId = RepositoryUtils.safeParseId(idString);
		LOGGER.debug("Get current visit product: " + currentProductId);
		List<Product> recommendProducts = getRecommendService().figureRecommendProducts(productIds, currentProductId);
		LOGGER.debug("Get recommend products: " + recommendProducts);
		pRequest.setAttribute(CartConstant.RECOMMEND_PRODUCTS, recommendProducts);
		return true;
	}


	public RecommendService getRecommendService() {
		return mRecommendService;
	}

	public void setRecommendService(final RecommendService pRecommendService) {
		mRecommendService = pRecommendService;
	}

	public ProductBrowseTrackService getBrowseTrackService() {
		return mBrowseTrackService;
	}

	public void setBrowseTrackService(final ProductBrowseTrackService pBrowseTrackService) {
		mBrowseTrackService = pBrowseTrackService;
	}

	public ProductBrowseTrackService getProductBrowseTrackService() {
		return mProductBrowseTrackService;
	}

	public void setProductBrowseTrackService(final ProductBrowseTrackService pProductBrowseTrackService) {
		mProductBrowseTrackService = pProductBrowseTrackService;
	}
}
