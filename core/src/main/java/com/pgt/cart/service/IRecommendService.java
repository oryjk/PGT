package com.pgt.cart.service;

import com.pgt.product.bean.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Yove on 4/6/2016.
 */
public interface IRecommendService {

	boolean recommend(final HttpServletRequest pRequest);

	List<Product> figureRecommendProducts(List<Integer> pRecentBrowsedProductIds, int pCurrentProductId);

	void recommendRandomly(List<Integer> pRecommendProducts, List<String> pRecommendedCategoryIds);
}
