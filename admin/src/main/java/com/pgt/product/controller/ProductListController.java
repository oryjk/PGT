package com.pgt.product.controller;

import com.google.common.collect.Lists;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.ViewMapperConfiguration;
import com.pgt.configuration.Configuration;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.bean.SortBean;
import com.pgt.utils.PaginationBean;
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


    @RequestMapping(value = "/product/productList", method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                            @RequestParam(value = "stock", required = false) Integer stock,
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

        Category categoryRequest = new Category();
        categoryRequest.setCode(null);
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setCurrentIndex(0L);
        paginationBean.setCapacity(10000L);
        Integer total = categoryService.queryCategoryTotal(categoryRequest);
        paginationBean.setTotalAmount(total);
        categoryRequest.setType(CategoryType.ROOT);
        List<Category> categories = categoryService.queryCategories(categoryRequest, paginationBean);
        modelAndView.addObject("categoryHierarchy", categoryHierarchy);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("staticServer", configuration.getStaticServer());
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
        if (ObjectUtils.isEmpty(categoryId)) {
            searchPaginationBean.setCategoryId(null);
        }

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
        Integer total = productService.queryProductTotal(searchPaginationBean);
        searchPaginationBean.setCurrentIndex(currentIndex);
        searchPaginationBean.setCapacity(configuration.getAdminPlpCapacity());
        searchPaginationBean.setTotalAmount(total);
        return searchPaginationBean;
    }

    public ViewMapperConfiguration getViewMapperConfiguration() {
        return viewMapperConfiguration;
    }

    public void setViewMapperConfiguration(ViewMapperConfiguration viewMapperConfiguration) {
        this.viewMapperConfiguration = viewMapperConfiguration;
    }
}
