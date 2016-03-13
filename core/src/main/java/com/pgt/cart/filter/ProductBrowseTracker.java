package com.pgt.cart.filter;

import com.google.gson.Gson;
import com.pgt.cart.bean.BrowsedProductVO;
import com.pgt.cart.constant.CookieConstant;
import com.pgt.cart.constant.SessionConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.cart.service.ShoppingCartConfiguration;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yove on 11/19/2015.
 */
public class ProductBrowseTracker implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductBrowseTracker.class);

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Resource(name = "productBrowseTrackService")
	private ProductBrowseTrackService mProductBrowseTrackService;

	@Resource(name = "shoppingCartConfiguration")
	private ShoppingCartConfiguration mShoppingCartConfiguration;

	@Override
	public boolean preHandle(HttpServletRequest pRequest, HttpServletResponse pResponse, Object pHandler)
			throws Exception {
		// current uri not match pdp uri
		if (!getProductBrowseTrackService().track(pRequest)) {
			return true;
		}
		// current uri not contains correct product id parameter
		String currProdIdStr = getProductBrowseTrackService().getRequestedProductId(pRequest);
		if (StringUtils.isBlank(currProdIdStr)) {
			return true;
		}
		int curProdId = RepositoryUtils.safeParseId(currProdIdStr);
		// if current product id is not a valid id
		if (!RepositoryUtils.idIsValid(curProdId)) {
			return true;
		}

		int maxBrowsedProductCount = getShoppingCartConfiguration().getBrowsedProductCount();
		String idString = null;
		// check session user existence
		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		List<Integer> sessionBrowsedProductIds = new ArrayList<>(maxBrowsedProductCount);
		// no order for user without login state
		if (user == null || !RepositoryUtils.idIsValid(user.getId().intValue())) {
			LOGGER.debug("No user found from session, record browse product to cookie only.");
			// get product ids string in cookie
			LinkedList<String> browsedProductIds = getProductBrowseTrackService().getBrowsedProductIdsFromCookies(pRequest);
			// LRU for cached product ids
			// maintain logic, remove current product id firstly if it exists
			browsedProductIds.remove(currProdIdStr);
			// add it to first to mark as latest
			browsedProductIds.addFirst(currProdIdStr);
			// limit total count of records
			if (browsedProductIds.size() > maxBrowsedProductCount) {
				browsedProductIds.removeLast();
			}
			LOGGER.debug("Get product ids: " + browsedProductIds + " after maintain.");
			// generate new ids string in json structure
			idString = new Gson().toJson(browsedProductIds);
			// save browsed product ids to session
			browsedProductIds.forEach(id -> {
				final int pid = RepositoryUtils.safeParseId(id);
				if (RepositoryUtils.idIsValid(pid)) {
					sessionBrowsedProductIds.add(pid);
				}
			});
		} else {
			int userId = user.getId().intValue();
			LOGGER.debug("User: {} found from session, record browse product to database.", userId);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = getTransactionManager().getTransaction(def);
			boolean result;
			synchronized (user) {
				//persist product ids to database
				LinkedList<BrowsedProductVO> browsedProducts = new LinkedList<>(getProductBrowseTrackService().queryBrowsedProducts(userId));
				LOGGER.debug("Found browsed products: {} of user: {}", browsedProducts, userId);
				BrowsedProductVO currentBrowse = null;
				// valid product id parameter, update database record
				for (BrowsedProductVO bp : browsedProducts) {
					if (curProdId == bp.getProductId()) {
						currentBrowse = bp;
						break;
					}
				}
				try {
					if (currentBrowse != null) {
						LOGGER.debug("Found browsed product: {} of user: {}, just reset the update date.", currentBrowse.getProduct(), userId);
						// current product had been record as recently browsed product
						result = getProductBrowseTrackService().recordBrowsedProduct(currentBrowse.getId());
					} else if (browsedProducts.size() >= maxBrowsedProductCount) {
						LOGGER.debug("Browsed products had been reached count limit of user: {}", userId);
						BrowsedProductVO oldestBrowsed = browsedProducts.removeLast();
						oldestBrowsed.setProductId(curProdId);
						browsedProducts.addFirst(oldestBrowsed);
						LOGGER.debug("Replace Browsed products: {} by {} for user: {}", oldestBrowsed.getId(), curProdId, userId);
						// recently browsed product had reached the limit
						result = getProductBrowseTrackService().replaceBrowsedProduct(oldestBrowsed.getId(), curProdId);
						oldestBrowsed.setProductId(curProdId);
					} else {
						LOGGER.debug("Record new browsed product: {} for user: {}", curProdId, userId);
						// create a new record
						BrowsedProductVO newBrowsedProduct = new BrowsedProductVO(userId, curProdId, getShoppingCartConfiguration().getDefaultBrowsedType());
						result = getProductBrowseTrackService().createBrowsedProduct(newBrowsedProduct);
					}
					if (!result) {
						status.setRollbackOnly();
					}
				} catch (Exception e) {
					LOGGER.error("Exception happen during record browsed product: " + curProdId + " for user: " + userId, e);
					status.setRollbackOnly();
				} finally {
					getTransactionManager().commit(status);
					List<String> browsedProductIds = new ArrayList<>(maxBrowsedProductCount);
					browsedProducts.forEach(bp -> {
						browsedProductIds.add(String.valueOf(bp.getProductId()));
						sessionBrowsedProductIds.add(bp.getProductId());
					});
					LOGGER.debug("Get product ids: " + browsedProductIds + " after maintain.");
					// generate new ids string in json structure
					idString = new Gson().toJson(browsedProductIds);
				}
			}
		}
		// set cookie
		LOGGER.debug("Generate new browsed product id string: {}", idString);
		Cookie cookie = new Cookie(CookieConstant.BROWSED_PRODUCTS, getProductBrowseTrackService().getEncodeCookie(idString));
		cookie.setMaxAge(getShoppingCartConfiguration().getBrowsedCookieExpired());
		// global path could share the cookie
		cookie.setPath("/");
		// set http only to avoid xss attack
		// cookie.setHttpOnly(true);
		pResponse.addCookie(cookie);
		// add recent browsed product ids to session
		pRequest.getSession().setAttribute(SessionConstant.RECENT_PRODUCT_IDS, sessionBrowsedProductIds);
		return true;
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {

	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

	public ProductBrowseTrackService getProductBrowseTrackService() {
		return mProductBrowseTrackService;
	}

	public void setProductBrowseTrackService(final ProductBrowseTrackService pProductBrowseTrackService) {
		mProductBrowseTrackService = pProductBrowseTrackService;
	}

	public ShoppingCartConfiguration getShoppingCartConfiguration() {
		return mShoppingCartConfiguration;
	}

	public void setShoppingCartConfiguration(final ShoppingCartConfiguration pShoppingCartConfiguration) {
		mShoppingCartConfiguration = pShoppingCartConfiguration;
	}
}