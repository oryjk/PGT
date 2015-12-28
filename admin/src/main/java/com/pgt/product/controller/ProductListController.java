package com.pgt.product.controller;

import com.google.common.collect.Lists;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.ViewMapperConfiguration;
import com.pgt.configuration.Configuration;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.bean.SortBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by carlwang on 12/25/15.
 */

@RestController
@RequestMapping("/category/{categoryId}")
public class ProductListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductListController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ViewMapperConfiguration viewMapperConfiguration;
    @Autowired
    private Configuration configuration;

    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable(value = "categoryId") Integer categoryId, @RequestParam(value = "stock", required = false) Integer stock,
                            @RequestParam(value = "term", required = false) String term,
                            @RequestParam(value = "sortProperty", required = false) String sortProperty,
                            @RequestParam(value = "sortValue", required = false, defaultValue = "ASC") String sortValue,
                            @RequestParam(value = "currentIndex", required = false) Long currentIndex, ModelAndView modelAndView) {
        LOGGER.debug("The category is {}.", categoryId);
        SearchPaginationBean searchPaginationBean = buildSearchPagination(categoryId, stock, term, sortProperty, sortValue, currentIndex);
        List<Product> productList = productService.queryProducts(searchPaginationBean);
        CategoryHierarchy categoryHierarchy = categoryService.queryCategoryHierarchy(categoryId);
        if (ObjectUtils.isEmpty(categoryHierarchy)) {
            LOGGER.debug("Can not find the categoryHierarchy with id is {}.", categoryId);
        }
        if (ObjectUtils.isEmpty(productList)) {
            LOGGER.debug("Can not find products with category id is {}", categoryId);
        }
        modelAndView.addObject("categoryHierarchy", categoryHierarchy);
        modelAndView.addObject("productList", productList);
        modelAndView.setViewName(viewMapperConfiguration.getProductListPage());
        return modelAndView;
    }

    private SearchPaginationBean buildSearchPagination(Integer categoryId,
                                                       Integer stock,
                                                       String term,
                                                       String sortProperty,
                                                       String sortValue,
                                                       Long currentIndex) {
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setCategoryId(String.valueOf(categoryId));
        if (ObjectUtils.isEmpty(stock)) {
            stock = -1;
        }
        searchPaginationBean.setStock(stock);
        if (!StringUtils.isBlank(term)) {
            searchPaginationBean.setTerm(term);
        }
        if (!StringUtils.isBlank(sortProperty)) {
            SortBean sortBean = new SortBean();
            sortBean.setPropertyName(sortProperty);
            sortBean.setSort(sortValue);
            searchPaginationBean.setSortBeans(Lists.newArrayList(sortBean));
        }
        if (currentIndex == null) {
            currentIndex = 0L;
        }
        searchPaginationBean.setCurrentIndex(currentIndex);
        searchPaginationBean.setCapacity(configuration.getAdminPlpCapacity());
        return searchPaginationBean;
    }

    public ViewMapperConfiguration getViewMapperConfiguration() {
        return viewMapperConfiguration;
    }

    public void setViewMapperConfiguration(ViewMapperConfiguration viewMapperConfiguration) {
        this.viewMapperConfiguration = viewMapperConfiguration;
    }
}