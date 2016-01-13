package com.pgt.hot.service;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.configuration.Configuration;
import com.pgt.product.bean.Product;
import com.pgt.product.dao.ProductMapper;
import com.pgt.search.bean.SearchPaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 11/14/15.
 */

@Service
public class HotProductHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotProductHelper.class);
    @Autowired
    private HotProductService hotProductService;

    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private Configuration configuration;

    @Autowired
    private ProductMapper productMapper;
    private Integer hotAmount = 6;

    public List<Product> findHomePageHotProducts() {
        List<Product> products = hotProductService.queryProductCategoryRelationProducts(CategoryType.HOME_PAGE_HOT);
        if (ObjectUtils.isEmpty(products)) {
            LOGGER.debug("Can not find the hot products in home page.");
            return new ArrayList<>();
        }
        LOGGER.debug("The hot products size is ${size} in home page. ", products.size());
        if (products.size() > configuration.getHomeHotProductCount()) {
            return products.subList(0, configuration.getHomeHotProductCount());
        }
        return products;
    }

    public List<Product> findShoppingCartHotProducts() {
        return hotProductService.queryProductCategoryRelationProducts(CategoryType.SHOPPING_CART_HOT);
    }

    public List<Product> findOrderConfirmationHotProducts() {
        return hotProductService.queryProductCategoryRelationProducts(CategoryType.ORDER_CONFIRMATION_HOT);
    }

    public List<Product> findCategoryHotProduct(Integer categoryId, boolean autoComplete) {

        List<Product> hotProducts = hotProductService.queryHotProductByCategoryId(categoryId);

        if (ObjectUtils.isEmpty(hotProducts)) {
            hotProducts = new ArrayList<>();
        }

        if (autoComplete) {
            return autoCompleteHotSale(categoryId, hotProducts);

        }

        return hotProducts;
    }

    public List<Product> autoCompleteHotSale(Integer categoryId, List<Product> hotProducts) {
        if (hotProducts.size() < hotAmount) {
            SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
            searchPaginationBean.setCategoryId(String.valueOf(categoryId));
            searchPaginationBean.setStock(-1);
            searchPaginationBean.setCapacity(hotAmount * 10);
            List<Product> products = productMapper.queryProducts(searchPaginationBean);
            hotProducts.addAll(products);
        }
        List<Product> products = new ArrayList<>();
        hotProducts.stream().forEach(product -> {
            if (!products.contains(product)) {
                products.add(product);
            }
        });

        return products;
    }

    public List<Product> findCategoryHotProductByRootCategoryId(Integer rootCategoryId) {
        Category category = categoryHelper.findCategory(rootCategoryId);
        List<Category> categories = category.getChildren();
        if (ObjectUtils.isEmpty(categories)) {
            LOGGER.debug("Can not find the child categories in category {rootCategoryId}.", rootCategoryId);
            return new ArrayList<>();
        }
        List<Product> products = new ArrayList<>();
        categories.stream().forEach(category1 -> {
            List<Product> productList = findCategoryHotProduct(category1.getId(), true);
            products.addAll(productList);
        });
        return products;
    }


    public HotProductService getHotProductService() {
        return hotProductService;
    }

    public void setHotProductService(HotProductService hotProductService) {
        this.hotProductService = hotProductService;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public CategoryHelper getCategoryHelper() {
        return categoryHelper;
    }

    public void setCategoryHelper(CategoryHelper categoryHelper) {
        this.categoryHelper = categoryHelper;
    }

    public Integer getHotAmount() {
        return hotAmount;
    }

    public void setHotAmount(Integer hotAmount) {
        this.hotAmount = hotAmount;
    }
}
