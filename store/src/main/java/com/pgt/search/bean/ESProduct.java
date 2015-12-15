package com.pgt.search.bean;

import com.pgt.product.bean.Product;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESProduct extends Product {
    private String parentCategoryId;
    private String parentCategoryName;
    private String rootCategoryId;
    private String rootCategoryName;


    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(String rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public String getRootCategoryName() {
        return rootCategoryName;
    }

    public void setRootCategoryName(String rootCategoryName) {
        this.rootCategoryName = rootCategoryName;
    }
}
