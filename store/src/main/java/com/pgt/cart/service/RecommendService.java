package com.pgt.cart.service;

import com.pgt.cart.dao.RecommendDao;
import com.pgt.configuration.URLConfiguration;
import com.pgt.internal.util.RepositoryUtils;
import com.pgt.product.bean.Product;
import com.pgt.product.dao.ProductMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 12/3/2015.
 */
@Service(value = "recommendService")
public class RecommendService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductBrowseTrackService.class);

	@Resource(name = "productBrowseTrackService")
	private ProductBrowseTrackService mBrowseTrackService;

	@Resource(name = "recommendDao")
	private RecommendDao mRecommendDao;

	@Resource(name = "productMapper")
	private ProductMapper mProductDao;

	@Autowired
	private URLConfiguration mURLConfiguration;

	private int mRecommendCount = 20;

	private String[] mRecommendURLs = new String[] {"/myAccount/favourites", "/myAccount/orderHistory", "/404"};

	private static final String SUFFIX_JS = ".js";
	private static final String SUFFIX_CSS = ".css";
	private static final String SUFFIX_JPG = ".jpg";
	private static final String SUFFIX_PNG = ".png";

	public boolean recommend(final HttpServletRequest pRequest) {
		String requestURI = pRequest.getRequestURI();
		if (requestURI.endsWith(SUFFIX_JS) || requestURI.endsWith(SUFFIX_CSS)) {
			return false;
		}
		if (requestURI.endsWith(SUFFIX_JPG) || requestURI.endsWith(SUFFIX_PNG)) {
			return false;
		}
		if (requestURI.contains(getURLConfiguration().getShoppingCartPage())) {
			return true;
		}
		if (requestURI.contains(getURLConfiguration().getPdpPage())) {
			return true;
		}
		if (requestURI.contains(getURLConfiguration().getPlpPage()) || requestURI.contains("/essearch")) {
			return true;
		}
		if (ArrayUtils.isNotEmpty(getRecommendURLs())) {
			for (String recommendURL : getRecommendURLs()) {
				if (requestURI.contains(recommendURL)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Product> figureRecommendProducts(List<Integer> pRecentBrowsedProductIds, int pCurrentProductId) {
		List<Integer> recommendProductIds = new ArrayList<>(getRecommendCount());
		if (CollectionUtils.isEmpty(pRecentBrowsedProductIds)) {
			LOGGER.debug("Empty recently browsed products, recommend products randomly.");
			recommendRandomly(recommendProductIds, null);
		} else {
			List<Map<String, Object>> categoryProductCounts = getRecommendDao().countCategoryProductMapping(pRecentBrowsedProductIds);
			LOGGER.debug("Get category product count mapping: {}", categoryProductCounts);
			int presentCount = 0;
			for (int i = 0; i < categoryProductCounts.size(); i++) {
				Map<String, Object> categoryProductCount = categoryProductCounts.get(i);
				int categoryRecommendCount;
				if (i < categoryProductCounts.size() - 1) {
					int browsedProductCount = ((Long) categoryProductCount.get("count")).intValue();
					double countDouble = 1d * getRecommendCount() * browsedProductCount / pRecentBrowsedProductIds.size();
					categoryRecommendCount = new BigDecimal(countDouble).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				} else {
					// last category should calculate count backwards
					categoryRecommendCount = getRecommendCount() - presentCount;
				}
				int categoryId = RepositoryUtils.safeParseId((String) categoryProductCount.get("categoryId"));
				LOGGER.debug("Get recommend count: {} for category: {}");
				if (categoryRecommendCount > 0) {
					if (!RepositoryUtils.idIsValid(categoryId)) {
						continue;
					}
					List<Integer> categoryProductIds = getRecommendDao().queryAvailableProductIds(categoryId);
					if (categoryProductIds.size() == 0) {
						continue;
					}
					if (categoryProductIds.size() < categoryRecommendCount) {
						// fix count to max product id count
						categoryRecommendCount = categoryProductIds.size();
						LOGGER.debug("Fix recommend count to: {} for less category products found", categoryRecommendCount);
					}
					// exclude current product
					int currentProductIndex = -1;
					if (RepositoryUtils.idIsValid(pCurrentProductId)) {
						currentProductIndex = categoryProductIds.indexOf(pCurrentProductId);
						if (currentProductIndex >= 0) {
							categoryRecommendCount--;
							LOGGER.debug("Fix recommend count to: {} for exclude current visit product", categoryRecommendCount);
						}
					}
					presentCount += categoryRecommendCount;
					// generate start index by random
					for (int j = RandomUtils.nextInt(0, categoryProductIds.size()); categoryRecommendCount > 0; j++) {
						// exclude current product id
						if (j == currentProductIndex) {
							continue;
						}
						categoryRecommendCount--;
						// restart loop at the begin of list
						if (j == categoryProductIds.size() - 1) {
							// reset
							recommendProductIds.add(categoryProductIds.get(j));
							j = 0;
						} else {
							recommendProductIds.add(categoryProductIds.get(j));
						}
					}
				}
			}
			if (recommendProductIds.size() < getRecommendCount()) {
				List<String> recommendedCategoryIds = new ArrayList<>(categoryProductCounts.size());
				for (Map<String, Object> categoryProductCount : categoryProductCounts) {
					recommendedCategoryIds.add((String) categoryProductCount.get("categoryId"));
				}
				recommendRandomly(recommendProductIds, recommendedCategoryIds);
			}
		}
		List<Product> supposeProducts = getProductDao().queryProductByIds(recommendProductIds);
		return supposeProducts;
	}

	public void recommendRandomly(List<Integer> pRecommendProducts, List<String> pRecommendedCategoryIds) {
		List<Integer> randomRecommendCategoryIds = getRecommendDao().queryCategoryIdsExclude(pRecommendedCategoryIds);
		for (Integer categoryId : randomRecommendCategoryIds) {
			List<Integer> categoryProductIds = getRecommendDao().queryAvailableProductIds(categoryId);
			for (int i = RandomUtils.nextInt(0, categoryProductIds.size()); pRecommendProducts.size() < getRecommendCount(); i++) {
				pRecommendProducts.add(categoryProductIds.get(i));
				// restart loop at the begin of list
				if (i == categoryProductIds.size() - 1) {
					// reset
					i = 0;
				}
			}
			if (pRecommendProducts.size() == getRecommendCount()) {
				break;
			}
		}
	}

	public ProductBrowseTrackService getBrowseTrackService() {
		return mBrowseTrackService;
	}

	public void setBrowseTrackService(final ProductBrowseTrackService pBrowseTrackService) {
		mBrowseTrackService = pBrowseTrackService;
	}

	public RecommendDao getRecommendDao() {
		return mRecommendDao;
	}

	public void setRecommendDao(final RecommendDao pRecommendDao) {
		mRecommendDao = pRecommendDao;
	}

	public String[] getRecommendURLs() {
		return mRecommendURLs;
	}

	public void setRecommendURLs(final String[] pRecommendURLs) {
		mRecommendURLs = pRecommendURLs;
	}

	public ProductMapper getProductDao() {
		return mProductDao;
	}

	public void setProductDao(final ProductMapper pProductDao) {
		mProductDao = pProductDao;
	}

	public int getRecommendCount() {
		return mRecommendCount;
	}

	public void setRecommendCount(final int pRecommendCount) {
		mRecommendCount = pRecommendCount;
	}

	public URLConfiguration getURLConfiguration() {
		return mURLConfiguration;
	}

	public void setURLConfiguration(final URLConfiguration pURLConfiguration) {
		mURLConfiguration = pURLConfiguration;
	}
}
