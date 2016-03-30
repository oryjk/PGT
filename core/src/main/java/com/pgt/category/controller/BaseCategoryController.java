package com.pgt.category.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryHelper;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by carlwang on 11/22/15.
 */

@RestController
@RequestMapping("/category")
public class BaseCategoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCategoryController.class);

	@Autowired
	private Configuration configuration;
	@Autowired
	private CategoryHelper categoryHelper;
	@Autowired
	private ProductHelper productHelper;

	@Autowired
	private ESSearchService esSearchService;

	@RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
	public ModelAndView loadProductListPage(@PathVariable("categoryId") String categoryId, ModelAndView modelAndView) {
		return loadProducts(categoryId, "0", modelAndView);
	}

	@RequestMapping(value = "/{categoryId}/{currentIndex}", method = RequestMethod.GET)
	public ModelAndView loadProductListPage(@PathVariable("categoryId") String categoryId,
			@PathVariable("currentIndex") String currentIndex, ModelAndView modelAndView) {
		return loadProducts(categoryId, currentIndex, modelAndView);
	}

	private ModelAndView loadProducts(@PathVariable("categoryId") String categoryId,
			@PathVariable("currentIndex") String currentIndex, ModelAndView modelAndView) {

		if (StringUtils.isBlank(categoryId)) {
			LOGGER.warn("The category id is empty.");
			return null;
		}

		if (configuration.getUseES()) {

			// 设置分页条件
			PaginationBean paginationBean = new PaginationBean();
			paginationBean.setCapacity(configuration.getPlpCapacity());
			paginationBean.setCurrentIndex(Long.parseLong(currentIndex));
			SearchResponse searchResponse = esSearchService.findProductsByCategoryId(categoryId, null, null,
					paginationBean, null, null);

			SearchHits searchHits = searchResponse.getHits();
			Long total = searchHits.getTotalHits();
			CommPaginationBean commPaginationBean = new CommPaginationBean(configuration.getPlpCapacity(),
					Long.parseLong(currentIndex), total);
			SearchHit[] categoryProducts = searchHits.getHits();

			modelAndView.addObject("categoryProducts", categoryProducts);
			modelAndView.addObject("commPaginationBean", commPaginationBean);
			modelAndView.addObject("ES", configuration.getUseES());
			modelAndView.addObject("categoryId", categoryId);   
			
			
		} else {

			SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
			searchPaginationBean.setCategoryId(categoryId);
			searchPaginationBean.setStock(-1);
			if (configuration.getOnlyShowInStock()) {
				searchPaginationBean.setStock(1);
			}
			searchPaginationBean.setCurrentIndex(0);

			if (StringUtils.isEmpty(currentIndex)) {
				searchPaginationBean.setCurrentIndex(Long.parseLong(currentIndex));
			}
			searchPaginationBean.setCapacity(configuration.getProductListPageCapacity());
			List<Product> productList = productHelper.findProducts(searchPaginationBean);
			Category category = categoryHelper.findCategory(Integer.valueOf(categoryId));
			modelAndView.addObject("productList", productList);
			modelAndView.addObject("category", category);

		}
		
		modelAndView.setViewName(Constants.PLP_PAGE);
		return modelAndView;

	}

	public CategoryHelper getCategoryHelper() {
		return categoryHelper;
	}

	public void setCategoryHelper(CategoryHelper categoryHelper) {
		this.categoryHelper = categoryHelper;
	}

	public ProductHelper getProductHelper() {
		return productHelper;
	}

	public void setProductHelper(ProductHelper productHelper) {
		this.productHelper = productHelper;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
