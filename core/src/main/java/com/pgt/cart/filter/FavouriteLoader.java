package com.pgt.cart.filter;

import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.UserFavouriteService;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 16/2/5.
 */
public class FavouriteLoader extends ItemLoadInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavouriteLoader.class);

	@Resource(name = "userFavouriteService")
	private UserFavouriteService mUserFavouriteService;

	@Override
	public boolean loadItems(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler) {
		if (!interceptRequest(pRequest)) {
			LOGGER.debug("Skip load favourite for request URI not match intercept rules.");
			return true;
		}
		if (!checkLoginState(pRequest)) {
			LOGGER.debug("Skip load browsed product for login state don't satisfy.");
			return true;
		}
		User currentUser = getCurrentUser(pRequest);
		// default pagination with 20 capacity
		InternalPagination favouritePage = InternalPaginationBuilder.createDefaultInternalPagination();
		getUserFavouriteService().queryFavouritePage(currentUser.getId().intValue(), favouritePage);
		pRequest.setAttribute(CartConstant.FAVOURITES, favouritePage);
		return true;
	}

	public UserFavouriteService getUserFavouriteService() {
		return mUserFavouriteService;
	}

	public void setUserFavouriteService(final UserFavouriteService pUserFavouriteService) {
		mUserFavouriteService = pUserFavouriteService;
	}
}
