package com.pgt.category.service;

import java.util.List;

import com.pgt.category.bean.Category;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.utils.PaginationBean;

/**
 * Created by carlwang on 11/13/15.
 */
public interface CategoryService {

    String createCategory(Category category);

    Integer updateCategory(Category category);

    Integer deleteCategory(Integer categoryId);

    Category queryCategory(Integer categoryId);
    Integer queryCategoryByCode(String code);

    Category queryParentCategoryByProductId(Integer productId);

    Category queryRootCategoryByProductId(Integer productId);

    List<Category> queryAllParentCategories();

    List<Category> queryCategories(Category category, PaginationBean paginationBean);

    CategoryHierarchy queryCategoryHierarchy(Integer categoryId);
    
    Integer getHelpCategoryCount();

    List<Category> querySubCategories(Integer rootCategoryId);
}
