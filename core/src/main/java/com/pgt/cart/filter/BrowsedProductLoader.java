package com.pgt.cart.filter;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.constant.SessionConstant;
import com.pgt.user.service.ESSearchProductService;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yove on 16/2/4.
 */
public class BrowsedProductLoader extends ItemLoadInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowsedProductLoader.class);

    @Autowired
    private ESSearchProductService mESSearchProductService;

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
        List<SearchHit> recentBrowsedProducts = mESSearchProductService.queryProducts(productIds);
        pRequest.setAttribute(CartConstant.BROWSED_PRODUCTS, recentBrowsedProducts);
        return true;
    }

}
