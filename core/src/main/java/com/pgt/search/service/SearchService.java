package com.pgt.search.service;

import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by carlwang on 11/15/15.
 */
@Service
public class SearchService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductHelper productHelper;

    public List<Product> queryProduct(SearchPaginationBean searchPaginationBean) {

        return productHelper.findProducts(searchPaginationBean);

    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ProductHelper getProductHelper() {
        return productHelper;
    }

    public void setProductHelper(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }

}
