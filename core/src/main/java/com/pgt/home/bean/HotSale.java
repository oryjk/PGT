package com.pgt.home.bean;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.product.bean.Product;

import java.util.List;

/**
 * Created by carlwang on 12/8/15.
 */
public class HotSale {


    public HotSale(Category category, List<Product> hotProduct) {
        this.category = category;
        this.hotProduct = hotProduct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setHotProduct(List<Product> hotProduct) {
        this.hotProduct = hotProduct;
    }

    private Category category;
    private List<Product> hotProduct;

    public Integer getId() {
        return category.getId();
    }

    public String getName() {
        return category.getName();
    }

    public String getCode() {
        return category.getCode();
    }

    public Category getParent() {
        return category.getParent();
    }

    public List<Category> getChildren() {
        return category.getChildren();
    }

    public Integer getStatus() {
        return category.getStatus();
    }

    public CategoryType getType() {
        return category.getType();
    }

    public List<Product> getProducts() {
        return category.getProducts();
    }

    public List<Product> getHotProduct() {
        return this.hotProduct;
    }

    public Integer getSort() {
        if (this.category.getSort() != null) {
            return this.category.getSort();
        }
        return 1;
    }


}
