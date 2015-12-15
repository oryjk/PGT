package com.pgt.cart.service;

import com.pgt.cart.bean.Favourite;
import com.pgt.cart.bean.FavouriteBuilder;
import com.pgt.cart.dao.UserFavouriteDao;
import com.pgt.internal.bean.pagination.InternalPagination;
import com.pgt.product.bean.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yove on 11/12/2015.
 */
@Service("userFavouriteService")
public class UserFavouriteService {

	@Resource(name = "userFavouriteDao")
	private UserFavouriteDao mUserFavouriteDao;

	public void queryFavouritePage(final int pUserId, final InternalPagination pPagination) {
		List<Favourite> favourites = getUserFavouriteDao().queryFavouritePage(pUserId, pPagination);
		pPagination.setResult(favourites);
	}

	public Favourite queryFavouriteByProduct(final int pUserId, final int pProductId) {
		return getUserFavouriteDao().queryFavouriteByProduct(pUserId, pProductId);
	}

	public List<Favourite> queryFavourites(final int pUserId) {
		return getUserFavouriteDao().queryFavourites(pUserId);
	}

	public Favourite queryFavourite(final int pFavouriteId) {
		return getUserFavouriteDao().queryFavourite(pFavouriteId);
	}

	public boolean createFavouriteItem(final Favourite pFavourite) {
		return getUserFavouriteDao().createFavouriteItem(pFavourite) > 0;
	}

	public boolean deleteFavouriteItem(final int pFavouriteId) {
		return getUserFavouriteDao().deleteFavouriteItem(pFavouriteId) > 0;
	}

	public Favourite convertProductToFavourite(int pUserId, Product pProduct) {
		FavouriteBuilder fb = new FavouriteBuilder().setUserId(pUserId);
		fb.setProductId(pProduct.getProductId()).setName(pProduct.getName()).setDescription(pProduct.getDescription());
		// set final price
		if (pProduct.getSalePrice() != null && pProduct.getSalePrice().doubleValue() > 0d) {
			fb.setFinalPrice(pProduct.getSalePrice().doubleValue());
		} else if (pProduct.getListPrice() != null) {
			fb.setFinalPrice(pProduct.getListPrice().doubleValue());
		} else {
			fb.setFinalPrice(0d);
		}
		if (!CollectionUtils.isEmpty(pProduct.getThumbnailMedias())) {
			fb.setSnapshotId(pProduct.getThumbnailMedias().get(0).getId());
		}
		return fb.createFavourite();
	}

	public UserFavouriteDao getUserFavouriteDao() {
		return mUserFavouriteDao;
	}

	public void setUserFavouriteDao(final UserFavouriteDao pUserFavouriteDao) {
		mUserFavouriteDao = pUserFavouriteDao;
	}
}
