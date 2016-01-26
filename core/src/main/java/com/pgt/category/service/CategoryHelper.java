package com.pgt.category.service;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.common.bean.Banner;
import com.pgt.common.bean.BannerQuery;
import com.pgt.common.service.BannerService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 11/18/15.
 */

@Service
public class CategoryHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryHelper.class);
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BannerService bannerService;

	public List<Category> findCategoryByType(CategoryType categoryType) {
		Category category = new Category();
		category.setType(categoryType);
		return categoryService.queryCategories(category, null);
	}

	public List<Category> findRootCategories() {
		List<Category>	categoryList=findCategories(CategoryType.ROOT);
		List<Category>  newCategoryList= new ArrayList<>();
		for (Category category:categoryList) {
			BannerQuery bannerQuery= new BannerQuery();
			bannerQuery.setType(category.getName()+":"+category.getId());
			List<Banner> bannerList=bannerService.queryBannerByQuery(bannerQuery);
			if(!CollectionUtils.isEmpty(bannerList)){
				category.setBanner(bannerList.get(0));
			}
			newCategoryList.add(category);
		}
		return newCategoryList;
	}

	public List<String> findRootCategoriesName() {
		List<Category>	categoryList=findCategories(CategoryType.ROOT);
		List<String>   categoryNameList= new ArrayList<>();
		for (Category category:categoryList) {
			categoryNameList.add(category.getName()+":"+category.getId());
		}
		return categoryNameList;
	}


	public List<Category> findHelpCenterCategories() {
		return findCategories(CategoryType.HELP_ROOT);
	}

	public List<Category> findNavigationCategories() {
		return findCategories(CategoryType.NAVIGATION);
	}

	private List<Category> findCategories(CategoryType categoryType) {
		Category category = new Category();
		category.setType(categoryType);
		return categoryService.queryCategories(category, null);
	}

	public List<Category> findSubCategory(Integer categoryId) {
		Category category = findCategory(categoryId);
		if (category == null) {
			LOGGER.debug("Can not find category.");
			return new ArrayList<>();
		}
		return category.getChildren();
	}

	public List<Category> findCategoryByName(String name, PaginationBean paginationBean) {
		LOGGER.debug("The category name is {name}", name);
		Category category = new Category();
		category.setName(name);
		return categoryService.queryCategories(category, paginationBean);
	}

	public Category findCategory(Integer categoryId) {
		LOGGER.debug("The categoryId is {categoryId}", categoryId);
		Category category = categoryService.queryCategory(categoryId);
		return category;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
