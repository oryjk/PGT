package com.pgt.cart.filter;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.cart.service.RecommendService;
import com.pgt.cart.service.UserFavouriteService;
import com.pgt.constant.UserConstant;
import com.pgt.internal.bean.pagination.InternalPagination;
import com.pgt.internal.bean.pagination.InternalPaginationBuilder;
import com.pgt.internal.constant.SessionConstant;
import com.pgt.internal.util.RepositoryUtils;
import com.pgt.product.bean.Product;
import com.pgt.user.bean.User;
import com.pgt.user.service.RecentlyViewService;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yove on 12/3/2015.
 */
public class RecommendInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecommendInterceptor.class);

	@Resource(name = "recommendService")
	private RecommendService mRecommendService;

	@Resource(name = "productBrowseTrackService")
	private ProductBrowseTrackService mBrowseTrackService;

	@Autowired
	private RecentlyViewService mRecentlyViewService;

	@Resource(name = "userFavouriteService")
	private UserFavouriteService mUserFavouriteService;


	@Override
	public boolean preHandle(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler) throws Exception {
		if (!getRecommendService().recommend(pRequest)) {
			return true;
		}
		List<Integer> productIds = (List<Integer>) pRequest.getSession().getAttribute(SessionConstant.RECENT_PRODUCT_IDS);
		LOGGER.debug("Get recently browsed products: " + productIds);
		String idString = getBrowseTrackService().getRequestedProductId(pRequest);
		int currentProductId = RepositoryUtils.safeParseId(idString);
		LOGGER.debug("Get current visit product: " + currentProductId);
		List<Product> recommendProducts = getRecommendService().figureRecommendProducts(productIds, currentProductId);
		LOGGER.debug("Get recommend products: " + recommendProducts);
		pRequest.setAttribute(CartConstant.RECOMMEND_PRODUCTS, recommendProducts);
		// append logic to load favourites & recently browsed products
		List<SearchHit> recentBrowsedProducts = getRecentlyViewService().getRecentBrowsedProducts(pRequest.getSession());
		pRequest.setAttribute(CartConstant.BROWSED_PRODUCTS, recentBrowsedProducts);
		User currentUser = getCurrentUser(pRequest);
		if (currentUser != null) {
			InternalPagination favouritePage = InternalPaginationBuilder.createDefaultInternalPagination();
			getUserFavouriteService().queryFavouritePage(currentUser.getId().intValue(), favouritePage);
			pRequest.setAttribute(CartConstant.FAVOURITES, favouritePage);
		}
		return true;
	}

	@Override
	public void postHandle(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler, final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler, final Exception ex) throws Exception {

	}

	protected User getCurrentUser(HttpServletRequest pRequest) {
		return (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
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

	public RecentlyViewService getRecentlyViewService() {
		return mRecentlyViewService;
	}

	public void setRecentlyViewService(final RecentlyViewService pRecentlyViewService) {
		mRecentlyViewService = pRecentlyViewService;
	}

	public UserFavouriteService getUserFavouriteService() {
		return mUserFavouriteService;
	}

	public void setUserFavouriteService(final UserFavouriteService pUserFavouriteService) {
		mUserFavouriteService = pUserFavouriteService;
	}
}
