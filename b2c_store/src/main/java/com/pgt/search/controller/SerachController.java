package com.pgt.search.controller;

import com.pgt.configuration.Configuration;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.bean.SortBean;
import com.pgt.search.service.SearchService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by carlwang on 10/31/15.
 */

@RestController
@RequestMapping("/search")
public class SerachController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerachController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private Configuration configuration;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get(@RequestParam("term") String term, @RequestParam(value = "categoryId", required = false) String categoryId,
                            @RequestParam(value = "sortKey", required = false) String sortKey,
                            @RequestParam(value = "sortValue", required = false) String sortValue,
                            @RequestParam(value = "priceStart", required = false) String priceStart,
                            @RequestParam(value = "priceEnd", required = false) String priceEnd,
                            @RequestParam(value = "currentIndex") String currentIndex, ModelAndView modelAndView,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Has some error when search.");
            return modelAndView;
        }
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setCurrentIndex(0);
        searchPaginationBean.setCapacity(configuration.getProductListPageCapacity());
        if (!StringUtils.isBlank(currentIndex)) {
            searchPaginationBean.setCurrentIndex(Long.valueOf(currentIndex));
        }
        if (!StringUtils.isBlank(term)) {
            LOGGER.debug("The search term is {term}.", term);
            searchPaginationBean.setTerm(term);
        }
        if (!StringUtils.isBlank(categoryId)) {
            LOGGER.debug("The categoryId is {categoryId}.", categoryId);
            searchPaginationBean.setCategoryId(categoryId);
        }
        if (!StringUtils.isBlank(sortKey) && !StringUtils.isBlank(sortValue)) {
            SortBean sortBean = new SortBean();
            sortBean.setPropertyName(sortKey);
            sortBean.setSort(sortValue);
            LOGGER.debug("The sort key is {sortKey},the sort value is {sortValue}.", sortKey, sortValue);
            searchPaginationBean.setSortBeans(Arrays.asList(sortBean));
        }
        if (!StringUtils.isBlank(priceStart) && !StringUtils.isBlank(priceEnd)) {
            LOGGER.debug("The start price is {priceStart},the end price is {priceEnd}.", priceStart, priceEnd);
            searchPaginationBean.setPriceStart(Integer.valueOf(priceStart));
            searchPaginationBean.setPriceEnd(Integer.valueOf(priceEnd));
        }
        List<Product> products = productService.queryProducts(searchPaginationBean);
        if (ObjectUtils.isEmpty(products)) {
            LOGGER.debug("The current page is {currentIndex},capacity is {capacity},categoryId is {categoryId}",
                    searchPaginationBean.getCurrentIndex(), searchPaginationBean.getCapacity(),
                    searchPaginationBean.getCategoryId());
            if (!ObjectUtils.isEmpty(searchPaginationBean.getSortBeans())) {
                searchPaginationBean.getSortBeans().stream().forEach(sort -> {
                    LOGGER.debug("The sort property is {property},the sort is {sort}.", sort.getPropertyName(),
                            sort.getSort());
                });
            }
            modelAndView.setViewName("/noResult");
            return modelAndView;
        }
        modelAndView.setViewName("/searchResult");
        modelAndView.addObject("searchResult", products);
        return modelAndView;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
