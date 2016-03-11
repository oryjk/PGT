package com.pgt.search.service;

import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import com.pgt.product.service.ProductServiceImp;
import com.pgt.search.bean.SearchPaginationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carlwang on 11/15/15.
 */
@Service
public class SearchService {

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private ProductHelper productHelper;

    public List<Product> queryProduct(SearchPaginationBean searchPaginationBean) {

        return productHelper.findProducts(searchPaginationBean);

    }

    public ProductServiceImp getProductServiceImp() {
        return productServiceImp;
    }

    public void setProductServiceImp(ProductServiceImp ProductServiceImp) {
        this.productServiceImp = ProductServiceImp;
    }

    public ProductHelper getProductHelper() {
        return productHelper;
    }

    public void setProductHelper(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }

}
