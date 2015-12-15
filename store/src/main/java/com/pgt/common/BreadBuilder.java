package com.pgt.common;

import com.google.common.collect.Lists;
import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryHelper;
import com.pgt.category.service.CategoryService;
import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by carlwang on 12/6/15.
 */
@Service
public class BreadBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreadBuilder.class);
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductHelper productHelper;

    public List<String> buildSeachPageBreadCrumb(String rootCategory, String category, String searchTerm) {
        List<String> breadCrumb = new ArrayList<>();
        if (!StringUtils.isBlank(rootCategory)) {
            breadCrumb.add(rootCategory);
        }
        if (!StringUtils.isBlank(category)) {
            breadCrumb.add(category);
        }
        if (!StringUtils.isBlank(searchTerm)) {
            breadCrumb.add(searchTerm);
        }
        return breadCrumb;
    }

    public List<String> buildPdpBreadCrumb(String productId) {
        List<String> breadCrumb = new ArrayList<>();

        List<Product> products = productHelper.findProductsByIds(Lists.newArrayList(Integer.valueOf(productId)));
        if (ObjectUtils.isEmpty(products)) {
            LOGGER.debug("Can not find the product {}.", productId);
            return breadCrumb;
        }
        Category category = categoryService.queryParentCategoryByProductId(Integer.valueOf(productId));
        if (ObjectUtils.isEmpty(category)) {
            LOGGER.debug("Can not find the parent category with product is {}.", productId);
            return breadCrumb;
        }
        breadCrumb.add(category.getParent().getName());

        breadCrumb.add(category.getName());
        breadCrumb.add(products.get(0).getName());
        return breadCrumb;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ProductHelper getProductHelper() {
        return productHelper;
    }

    public void setProductHelper(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }
}
