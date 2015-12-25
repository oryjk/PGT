package com.pgt.common.bean;

import org.springframework.stereotype.Component;

/**
 * Created by carlwang on 12/25/15.
 */
@Component
public class ViewMapperConfiguration {
    private String productListPage="/product/productList";

    public String getProductListPage() {
        return productListPage;
    }

    public void setProductListPage(String productListPage) {
        this.productListPage = productListPage;
    }
}
