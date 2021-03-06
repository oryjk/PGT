package com.pgt.category.service;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryQuery;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.utils.PaginationBean;

import java.util.List;

/**
 * Created by carlwang on 11/13/15.
 */
public interface CategoryService {
    String createCategoryAndUpdateMedia(Category category, Integer mediaId);

    String createCategory(Category category);

    String createCategory(Category category, Integer mediaId);

    Integer updateCategory(Category category);

    Integer deleteCategory(Integer categoryId);

    Category queryCategory(Integer categoryId);

    Integer queryCategoryByCode(String code);

    Category queryParentCategoryByProductId(Integer productId);

    Category queryRootCategoryByProductId(Integer productId);

    List<Category> queryAllParentCategories();

    List<Category> queryAllTenderParentCategories();

    List<Category> queryRootCategories();

    List<Category> queryTenderRootCategories();

    List<Category> queryRootTenderCategories();

    List<Category> queryCategories(Category category, PaginationBean paginationBean);

    Integer queryCategoryTotal(Category category);

    CategoryHierarchy queryCategoryHierarchy(Integer categoryId);

    Integer getHelpCategoryCount();

    List<Category> querySubCategories(Integer rootCategoryId);

    List<Category> queryOnlinePawnCategories ();

    List<Category> queryCategoryByQuery(CategoryQuery categoryQuery);

    List<Category> queryLivepawnCategroys();
}
