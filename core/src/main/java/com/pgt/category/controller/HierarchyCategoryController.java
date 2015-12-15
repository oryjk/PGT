package com.pgt.category.controller;

import com.pgt.category.service.CategoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by carlwang on 11/19/15.
 */
public class HierarchyCategoryController {
    @Autowired
    private CategoryHelper categoryHelper;


    @RequestMapping

    
    public CategoryHelper getCategoryHelper() {
        return categoryHelper;
    }

    public void setCategoryHelper(CategoryHelper categoryHelper) {
        this.categoryHelper = categoryHelper;
    }
}
