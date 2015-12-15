package com.pgt.product.bean;

import java.io.Serializable;

/**
 * Created by carlwang on 11/12/15.
 */
public class ProductCategoryRelation implements Serializable{
    private Integer productCategoryRelationId;
    private Integer productId;
    private Integer categoryId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductCategoryRelationId() {
        return productCategoryRelationId;
    }

    public void setProductCategoryRelationId(Integer productCategoryRelationId) {
        this.productCategoryRelationId = productCategoryRelationId;
    }
}
