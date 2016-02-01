package com.pgt.common.bean;

import org.springframework.stereotype.Component;

/**
 * Created by carlwang on 12/25/15.
 */
@Component
public class ViewMapperConfiguration {
    private String productListPage ="/product/productList";

    private String tenderListPage  = "/tender/tenderList";

    public String getTenderListPage () {
        return tenderListPage;
    }

    public void setTenderListPage (String tenderListPage) {
        this.tenderListPage = tenderListPage;
    }
    public String getProductListPage() {
        return productListPage;
    }

    public void setProductListPage(String productListPage) {
        this.productListPage = productListPage;
    }
}
