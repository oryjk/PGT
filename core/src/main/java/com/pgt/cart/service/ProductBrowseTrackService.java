package com.pgt.cart.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pgt.cart.bean.BrowsedProductVO;
import com.pgt.cart.constant.CookieConstant;
import com.pgt.cart.dao.UserOrderDao;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.user.bean.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yove on 11/26/2015.
 */
@Service(value = "productBrowseTrackService")
public class ProductBrowseTrackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductBrowseTrackService.class);

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Autowired
	private URLConfiguration mURLConfiguration;

	@Resource(name = "userOrderDao")
	private UserOrderDao mUserOrderDao;

	private int mCookieExpiredTime = 604800;//60s * 60m * 24h * 7d

	private int mBrowsedProductCount = 20;

	private boolean mInitialized;

	private String mTrackingURL;

	private boolean mEnableCookieEncode = true;

	public boolean track(HttpServletRequest pRequest) {
		if (!mInitialized) {
			mTrackingURL = pRequest.getContextPath() + mURLConfiguration.getPdpPage();
			mInitialized = true;
			LOGGER.info("Track browsed product with URL that start with: {}", mTrackingURL);
		}
		return pRequest.getRequestURI().startsWith(mTrackingURL);
	}

	public String getRequestedProductId(HttpServletRequest pRequest) {
		int index = pRequest.getRequestURI().indexOf(mURLConfiguration.getPdpPage());
		if (index > -1) {
			String productId = pRequest.getRequestURI().substring(index + mURLConfiguration.getPdpPage().length() + 1);
			return productId;
		}
		return null;
	}

	public LinkedList<String> getBrowsedProductIdsFromCookies(HttpServletRequest pRequest) {
		String idString = getBrowsedProductCookie(pRequest);
		return deserializeCookie(idString);
	}

	protected String getBrowsedProductCookie(HttpServletRequest pRequest) {
		String idString = StringUtils.EMPTY;
		// get product ids string in cookie
		Cookie[] cookies = pRequest.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (StringUtils.equals(CookieConstant.BROWSED_PRODUCTS, cookie.getName())) {
					idString = cookie.getValue();
					break;
				}
			}
		}
		LOGGER.debug("Get product ids: " + idString + " from cookies.");
		if (isEnableCookieEncode()) {
			try {
				idString = URLDecoder.decode(idString, CharEncoding.UTF_8);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Cannot decode recently browsed product id string: {}", idString);
				// reset id string
				idString = StringUtils.EMPTY;
			}
			LOGGER.debug("Get decoded product ids: " + idString + " from cookies.");
		}
		return idString;
	}

	public String getEncodeCookie(String pCookieValue) {
		if (isEnableCookieEncode()) {
			String encodedCookieValue;
			try {
				encodedCookieValue = URLEncoder.encode(pCookieValue, CharEncoding.UTF_8);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Cannot encode recently browsed product id string: {}", pCookieValue);
				// reset id string
				encodedCookieValue = StringUtils.EMPTY;
			}
			LOGGER.debug("Get encoded product ids: " + encodedCookieValue + " to set into cookies.");
		}
		return pCookieValue;
	}

	protected LinkedList<String> deserializeCookie(String pCookieValue) {
		if (StringUtils.isBlank(pCookieValue)) {
			return new LinkedList<>();
		}
		try {
			return new Gson().fromJson(pCookieValue, LinkedList.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error("Cannot parse json string: {} to browsed product id list.", pCookieValue);
		}
		return new LinkedList<>();
	}

	public boolean recordBrowsedProduct(final int pBrowsedProductId) {
		return getUserOrderDao().resetBrowsedProductUpdateDate(pBrowsedProductId) > 0;
	}

	public boolean replaceBrowsedProduct(final int pBrowsedProductId, final int pNewProductId) {
		return getUserOrderDao().replaceBrowsedProductRecord(pBrowsedProductId, pNewProductId) > 0;
	}

	public boolean createBrowsedProduct(final BrowsedProductVO pBrowsedProduct) {
		return getUserOrderDao().createBrowsedProductRecord(pBrowsedProduct) > 0;
	}

	public List<BrowsedProductVO> queryBrowsedProducts(final int pUserId) {
		return getUserOrderDao().queryBrowsedProducts(pUserId);
	}

	public void mergeBrowsedProductsForLoginUser(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		int userId = user.getId().intValue();
		LinkedList<String> cookieProductIds = getBrowsedProductIdsFromCookies(pRequest);
		if (CollectionUtils.isEmpty(cookieProductIds)) {
			LOGGER.debug("No browsed products need to be merged from cookies for user: {}", userId);
			return;
		}
		// merge logic
		List<BrowsedProductVO> browsedProducts;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		synchronized (user) {
			browsedProducts = queryBrowsedProducts(userId);
			LOGGER.debug("Found browsed products: {} of user: {}", browsedProducts, userId);
			// pending update or create browsed products list, collect items to persist
			BrowsedProductVO persistBrowsedProduct;
			List<BrowsedProductVO> pendingCreateBrowsedProducts = new ArrayList<>(cookieProductIds.size());
			List<Integer> pendingRecordBrowsedProducts = new ArrayList<>(cookieProductIds.size());
			for (int i = cookieProductIds.size() - 1; i >= 0; i--) {
				persistBrowsedProduct = null;
				int cookieProductId = RepositoryUtils.safeParseId(cookieProductIds.get(i));
				Iterator<BrowsedProductVO> it = browsedProducts.iterator();
				while (it.hasNext()) {
					BrowsedProductVO bp = it.next();
					if (cookieProductId == bp.getProductId()) {
						persistBrowsedProduct = bp;
						// remove it firstly, then update it again
						it.remove();
						break;
					}
				}
				if (persistBrowsedProduct == null) {
					pendingCreateBrowsedProducts.add(new BrowsedProductVO(userId, cookieProductId));
				} else {
					pendingRecordBrowsedProducts.add(persistBrowsedProduct.getId());
				}
			}
			try {
				// reset update date to current time
				if (pendingRecordBrowsedProducts.size() > 0) {
					recordBrowsedProductBatch(pendingRecordBrowsedProducts);
				}
				// continue if reset operation success
				int balance = getBrowsedProductCount() - (pendingRecordBrowsedProducts.size() + browsedProducts.size());
				// it should not be happened, in case
				if (balance < 0) {
					balance = 0;
				}
				for (int i = 0, j = 0; i < pendingCreateBrowsedProducts.size(); i++) {
					if (i < balance) {
						createBrowsedProduct(pendingCreateBrowsedProducts.get(i));
					} else {
						// browsedProducts orders by update date desc, so get the index backward
						int k = browsedProducts.size() - 1 - j++;
						// it should not be happened, in case
						if (k < 0 || k > browsedProducts.size()) {
							continue;
						}
						BrowsedProductVO oldBrowsedProduct = browsedProducts.get(k);
						replaceBrowsedProduct(oldBrowsedProduct.getId(), pendingCreateBrowsedProducts.get(i).getProductId());
					}
				}
				browsedProducts = queryBrowsedProducts(userId);
			} catch (Exception e) {
				LOGGER.error("Merge browsed products from cookie to database during login for user: {}", userId);
				status.setRollbackOnly();
			} finally {
				getTransactionManager().commit(status);
			}
		}
		// reset cookies
		List<String> browsedProductIds = new ArrayList<>(getBrowsedProductCount());
		browsedProducts.forEach(bp -> {
			browsedProductIds.add(String.valueOf(bp.getProductId()));
		});
		LOGGER.debug("Get product ids: " + browsedProductIds + " after maintain.");
		// generate new ids string in json structure
		String idString = new Gson().toJson(browsedProductIds);
		LOGGER.debug("Generate new browsed product id string: {}", idString);
		Cookie cookie = new Cookie(CookieConstant.BROWSED_PRODUCTS, getEncodeCookie(idString));
		cookie.setMaxAge(getCookieExpiredTime());
		// global path could share the cookie
		cookie.setPath(pRequest.getContextPath());
		// set http only to avoid xss attack
		// cookie.setHttpOnly(true);
		pResponse.addCookie(cookie);
	}

	public boolean recordBrowsedProductBatch(final List<Integer> pBrowsedProductIds) {
		return getUserOrderDao().resetBrowsedProductsBatchUpdateDate(pBrowsedProductIds) > 0;
	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

	public URLConfiguration getURLConfiguration() {
		return mURLConfiguration;
	}

	public void setURLConfiguration(final URLConfiguration pURLConfiguration) {
		mURLConfiguration = pURLConfiguration;
	}

	public UserOrderDao getUserOrderDao() {
		return mUserOrderDao;
	}

	public void setUserOrderDao(final UserOrderDao pUserOrderDao) {
		mUserOrderDao = pUserOrderDao;
	}

	public int getCookieExpiredTime() {
		return mCookieExpiredTime;
	}

	public void setCookieExpiredTime(final int pCookieExpiredTime) {
		mCookieExpiredTime = pCookieExpiredTime;
	}

	public int getBrowsedProductCount() {
		return mBrowsedProductCount;
	}

	public void setBrowsedProductCount(final int pBrowsedProductCount) {
		mBrowsedProductCount = pBrowsedProductCount;
	}

	public boolean isEnableCookieEncode() {
		return mEnableCookieEncode;
	}

	public void setEnableCookieEncode(final boolean pEnableCookieEncode) {
		mEnableCookieEncode = pEnableCookieEncode;
	}
}
