package com.pgt.global.interceptor;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryHelper;
import com.pgt.common.service.BannerService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.home.bean.HomeCategory;
import com.pgt.hot.bean.HotSearch;
import com.pgt.hot.service.HotProductHelper;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESSort;
import com.pgt.search.service.ESSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 11/20/15.
 */
public class GlobalConfigInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfigInterceptor.class);

    @Autowired
    private ServletContext applicationContext;
    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private HotProductHelper hotProductHelper;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryHelper categoryHelper;

    @Autowired
    private Configuration configuration;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ESSearchService esSearchService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute(Constants.URL_CONFIGURATION, urlConfiguration);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


        //not interceptor static files.
        if (request.getRequestURI().contains(request.getContextPath() + configuration.getResourcePath())) {
            return;
        }


        if (configuration.getUseES() == true) {
            // get root categories
            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.ROOT_CATEGORIES))) {
                SearchResponse rootSearchResponse = esSearchService.findRootCategory();
                if (!ObjectUtils.isEmpty(rootSearchResponse)) {
                    SearchHits searchHits = rootSearchResponse.getHits();
                    if (!ObjectUtils.isEmpty(searchHits)) {
                        SearchHit[] rootCategory = searchHits.getHits();
                        LOGGER.debug("The root category size is {}.", ObjectUtils.isEmpty(rootCategory) ? 0 : rootCategory.length);
                        applicationContext.setAttribute(Constants.ROOT_CATEGORIES, rootCategory);
                    }
                }
            }


            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.HOT_PRODUCTS))) {
                ESSort esSort = new ESSort();
                esSort.setPropertyName(Constants.SORT);
                esSort.setSortOrder(SortOrder.ASC);
                SearchResponse searchResponse = esSearchService.findHotSales(esSort);
                SearchHits searchHits = searchResponse.getHits();
                SearchHit[] hotProducts = searchHits.getHits();
                if (hotProducts.length != 0) {
                    applicationContext.setAttribute(Constants.HOT_PRODUCTS, hotProducts);
                }

            }


            // get navigation categories
            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.NAVIFATION_CATEGORIES))) {
                List<Category> navigationCategories = categoryHelper.findNavigationCategories();
                applicationContext.setAttribute(Constants.NAVIFATION_CATEGORIES, navigationCategories);
            }

            // get hot search
            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.HOT_SEARCH_LIST))) {
                List<HotSearch> hotSearchList = productService.queryAllHotsearch();
                applicationContext.setAttribute(Constants.HOT_SEARCH_LIST, hotSearchList);

            }


        } else {
            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.ROOT_CATEGORIES))) {
                // get root categories
                List<Category> rootCategories = categoryHelper.findRootCategories();

                if (!ObjectUtils.isEmpty(rootCategories)) {
                    List<HomeCategory> rootHomeCategories = new ArrayList<>();
                    rootCategories.stream().forEach(category -> {
                        List<Product> products = hotProductHelper.findCategoryHotProductByRootCategoryId(category.getId());
                        HomeCategory homeCategory = new HomeCategory(category, products);
                        rootHomeCategories.add(homeCategory);
                    });
                    applicationContext.setAttribute(Constants.ROOT_CATEGORIES, rootHomeCategories);
                }
            }

            // get navigation categories
            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.NAVIFATION_CATEGORIES))) {
                List<Category> navigationCategories = categoryHelper.findNavigationCategories();
                if (!ObjectUtils.isEmpty(navigationCategories)) {
                    applicationContext.setAttribute(Constants.NAVIFATION_CATEGORIES, navigationCategories);
                }
            }


            // get hot search
            if (ObjectUtils.isEmpty(applicationContext.getAttribute(Constants.HOT_PRODUCTS))) {
                List<HotSearch> hotSearchList = productService.queryAllHotsearch();
                applicationContext.setAttribute(Constants.HOT_SEARCH_LIST, hotSearchList);
            }


        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public URLConfiguration getUrlConfiguration() {
        return urlConfiguration;
    }

    public void setUrlConfiguration(URLConfiguration urlConfiguration) {
        this.urlConfiguration = urlConfiguration;
    }

    public HotProductHelper getHotProductHelper() {
        return hotProductHelper;
    }

    public void setHotProductHelper(HotProductHelper hotProductHelper) {
        this.hotProductHelper = hotProductHelper;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public CategoryHelper getCategoryHelper() {
        return categoryHelper;
    }

    public void setCategoryHelper(CategoryHelper categoryHelper) {
        this.categoryHelper = categoryHelper;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public BannerService getBannerService() {
        return bannerService;
    }

    public void setBannerService(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    public ESSearchService getEsSearchService() {
        return esSearchService;
    }

    public void setEsSearchService(ESSearchService esSearchService) {
        this.esSearchService = esSearchService;
    }
}
