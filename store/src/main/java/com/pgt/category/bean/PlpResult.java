package com.pgt.category.bean;

import com.pgt.product.bean.Product;

import java.util.List;

/**
 * Created by carlwang on 11/22/15.
 */
public class PlpResult {
    private List<Product> productList;
    private Category category;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
