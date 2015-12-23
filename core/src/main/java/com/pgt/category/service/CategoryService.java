package com.pgt.category.service;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.utils.PaginationBean;

import java.util.List;

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

}
