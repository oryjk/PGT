package com.pgt.product.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.ViewMapperConfiguration;
import com.pgt.configuration.Configuration;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.SortBeanList;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.bean.SortBean;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by carlwang on 12/25/15.
 */

@RestController
public class ProductListController extends InternalTransactionBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductListController.class);
	@Autowired
	private ProductService          productService;
	@Autowired
	private CategoryService         categoryService;
	@Autowired
	private ViewMapperConfiguration viewMapperConfiguration;
	@Autowired
	private Configuration           configuration;


	@RequestMapping(value = "/product/productList", method = RequestMethod.GET)
	public ModelAndView get (HttpServletRequest pRequest,
	                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
	                         @RequestParam(value = "stock", required = false) Integer stock,
	                         @RequestParam(value = "term", required = false) String term,
							 SortBeanList sortBeanList,
	                         @RequestParam(value = "currentIndex", required = false) Long currentIndex,
							 @RequestParam(value = "isHot", required = false) Integer isHot,
							 ModelAndView modelAndView) {
		// verify permission
		if (!verifyPermission(pRequest)) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		// main logic
		LOGGER.debug("The category is {}.", categoryId);
		SearchPaginationBean searchPaginationBean = buildSearchPagination(categoryId, stock, term,sortBeanList, currentIndex,isHot,modelAndView);
		List<Product> productList = productService.queryProducts(searchPaginationBean);
		CategoryHierarchy categoryHierarchy = categoryService.queryCategoryHierarchy(categoryId);
		if (!ObjectUtils.isEmpty(categoryHierarchy)) {
			LOGGER.debug("Can not find the categoryHierarchy with id is {}.", categoryId);

			Integer rootCategoryId = categoryHierarchy.getCategoryId();
			if (!ObjectUtils.isEmpty(categoryHierarchy.getParentCategory())) {
				rootCategoryId = categoryHierarchy.getParentCategory().getCategoryId();
			}
			List<Category> subCategories = categoryService.querySubCategories(rootCategoryId);
			modelAndView.addObject("subCategories", subCategories);
		}
		if (ObjectUtils.isEmpty(productList)) {
			LOGGER.debug("Can not find products with category id is {}", categoryId);
			return modelAndView;
		}


		Category categoryRequest = new Category();
		categoryRequest.setCode(null);
		PaginationBean paginationBean = new PaginationBean();
		paginationBean.setCapacity(10000L);
		paginationBean.setCurrentIndex(-1);
		categoryRequest.setType(CategoryType.ROOT);
		Integer total = categoryService.queryCategoryTotal(categoryRequest);
		paginationBean.setTotalAmount(total);
		paginationBean.setCurrentIndex(0);
		List<Category> categories = categoryService.queryCategories(categoryRequest, paginationBean);
		modelAndView.addObject("categoryHierarchy", categoryHierarchy);
		modelAndView.addObject("term", term);
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("productList", productList);
		modelAndView.addObject("staticServer", configuration.getStaticServer());
		modelAndView.addObject("searchPaginationBean", searchPaginationBean);
		modelAndView.setViewName(viewMapperConfiguration.getProductListPage());
		return modelAndView;
	}

	private SearchPaginationBean buildSearchPagination (Integer categoryId,
	                                                    Integer stock,
	                                                    String term,
														SortBeanList sortBeanList,
	                                                    Long currentIndex,
														Integer isHot,ModelAndView modelAndView) {
		SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
		searchPaginationBean.setCategoryId(String.valueOf(categoryId));
		if (ObjectUtils.isEmpty(categoryId)) {
			searchPaginationBean.setCategoryId(null);
		}
		if (!ObjectUtils.isEmpty(categoryId)) {
            modelAndView.addObject("categoryId",categoryId);
		}

		if (ObjectUtils.isEmpty(stock)) {
			stock = -1;
		}
		searchPaginationBean.setStock(stock);
		if (!StringUtils.isBlank(term)) {
			searchPaginationBean.setTerm(term);
			modelAndView.addObject("term",term);
		}

		if(!ObjectUtils.isEmpty(sortBeanList)){
			if (!CollectionUtils.isEmpty(sortBeanList.getSortBeans())) {
				searchPaginationBean.setSortBeans(sortBeanList.getSortBeans());
				modelAndView.addObject("sortBean",sortBeanList.getSortBeans());
			}
		}

		if (!ObjectUtils.isEmpty(isHot)) {
             searchPaginationBean.setIsHot(isHot);
			modelAndView.addObject("isHot",isHot);
		}
		if (ObjectUtils.isEmpty(currentIndex)) {
			currentIndex = 0L;
		}
		searchPaginationBean.setCurrentIndex(-1);
		searchPaginationBean.setCapacity(100000);
		Integer total = productService.queryProductTotal(searchPaginationBean);
		searchPaginationBean.setCurrentIndex(currentIndex);
		searchPaginationBean.setCapacity(configuration.getAdminPlpCapacity());
		searchPaginationBean.setTotalAmount(total);
		return searchPaginationBean;
	}

	public ViewMapperConfiguration getViewMapperConfiguration () {
		return viewMapperConfiguration;
	}

	public void setViewMapperConfiguration (ViewMapperConfiguration viewMapperConfiguration) {
		this.viewMapperConfiguration = viewMapperConfiguration;
	}
}
