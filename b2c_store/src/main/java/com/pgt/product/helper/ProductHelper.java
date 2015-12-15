package com.pgt.product.helper;

import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 11/17/15.
 */
@Service
public class ProductHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductHelper.class);
    @Autowired
    private ProductService productService;

    public List<Product> findProductsByCategoryId(String categoryId) {
        return findProductsByCategoryId(categoryId, -1);
    }

    public List<Product> findInStockProductsByCategoryId(String categoryId) {
        return findProductsByCategoryId(categoryId, 1);
    }

    public List<Product> findProductsByName(String productName) {
        if (StringUtils.isBlank(productName)) {
            LOGGER.debug("The product name is null.");
            return new ArrayList<>();
        }
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setTerm(productName);
        return productService.queryProducts(searchPaginationBean);

    }

    public List<Product> findProductsByIds(List<Integer> productIds) {
        if (ObjectUtils.isEmpty(productIds)) {
            LOGGER.debug("The productIds is empty.");
            return new ArrayList<>();
        }
        List<Product> products = new ArrayList<>();
        productIds.stream().forEach(productId -> products.add(productService.queryProduct(productId)));
        return products;
    }

    public List<Product> findProductsByName(String productName, int limit, int offset) {
        if (StringUtils.isBlank(productName)) {
            LOGGER.debug("The product name is null.");
            return new ArrayList<>();
        }
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setTerm(productName);
        searchPaginationBean.setCurrentIndex(offset);
        searchPaginationBean.setCapacity(limit);
        return productService.queryProducts(searchPaginationBean);
    }

    public List<Product> findProducts(SearchPaginationBean searchPaginationBean) {
        return productService.queryProducts(searchPaginationBean);
    }


    /**
     * Find all products,whether or not in stock.
     *
     * @return
     */
    public List<Product> findAllProduct() {
        return findAllProducts(-1);

    }

    /**
     * @param stock, if -1,all product will return,opposition,will just return in stock products.
     * @return
     */
    public List<Product> findAllProducts(Integer stock) {
        return productService.queryAllProducts(stock);
    }

    private List<Product> findProductsByCategoryId(String categoryId, Integer stock) {
        if (StringUtils.isBlank(categoryId)) {
            LOGGER.debug("The category id is null.");
            return new ArrayList<>();
        }

        LOGGER.debug("The category id is {categoryId}.", categoryId);
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setStock(stock);
        searchPaginationBean.setCategoryId(categoryId);
        searchPaginationBean.setStock(stock);
        searchPaginationBean.setCapacity(-1);
        return findProducts(searchPaginationBean);
    }

    public void createProduct(Integer categoryId, Product product) {
        LOGGER.debug("The categoryId is {categoryId}.", categoryId);
        productService.createProduct(categoryId, product);
    }


    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
