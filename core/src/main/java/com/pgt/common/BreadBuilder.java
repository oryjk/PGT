package com.pgt.common;

import com.google.common.collect.Lists;
import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryHelper;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.BreadCrumb;
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

    public List<BreadCrumb> buildSearchPageBreadCrumb(String rootCategory, String category, String searchTerm) {
        List<BreadCrumb> breadCrumb = new ArrayList<>();
        if (!StringUtils.isBlank(rootCategory)) {
            BreadCrumb rootBreadCrumb=new BreadCrumb();
            rootBreadCrumb.setBreadName(rootCategory);
            breadCrumb.add(rootBreadCrumb);
        }
        if (!StringUtils.isBlank(category)) {
            BreadCrumb categoryBreadCrumb=new BreadCrumb();
            categoryBreadCrumb.setBreadName(category);
            breadCrumb.add(categoryBreadCrumb);
        }
        if (!StringUtils.isBlank(searchTerm)) {
            BreadCrumb searchBreadCrumb=new BreadCrumb();
            searchBreadCrumb.setBreadName(searchTerm);
            breadCrumb.add(searchBreadCrumb);
        }
        return breadCrumb;
    }



    public List<BreadCrumb> buildPdpBreadCrumb(String productId) {
        List<BreadCrumb> breadCrumb = new ArrayList<>();

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
        BreadCrumb rootBreadCrumb = new BreadCrumb();
        rootBreadCrumb.setBreadUrl("/essearch?rootCategoryId=" + category.getParent().getId());
        rootBreadCrumb.setBreadName(category.getParent().getName());
        breadCrumb.add(rootBreadCrumb);

        BreadCrumb categoryBreadCrumb = new BreadCrumb();
        categoryBreadCrumb.setBreadUrl("/essearch?parentCategoryId=" + category.getId());
        categoryBreadCrumb.setBreadName(category.getName());
        breadCrumb.add(categoryBreadCrumb);

        BreadCrumb productBreadCrumb = new BreadCrumb();
        productBreadCrumb.setBreadUrl("/product/" + products.get(0).getProductId());
        productBreadCrumb.setBreadName(products.get(0).getName());

        breadCrumb.add(productBreadCrumb);
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
