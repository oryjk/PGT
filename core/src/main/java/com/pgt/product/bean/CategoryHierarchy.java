package com.pgt.product.bean;

import com.pgt.category.bean.CategoryType;

/**
 * Created by carlwang on 12/25/15.
 */
public class CategoryHierarchy {
    private Integer categoryId;
    private String name;
    private CategoryType categoryType;
    private CategoryHierarchy parentCategory;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryHierarchy getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryHierarchy parentCategory) {
        this.parentCategory = parentCategory;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
