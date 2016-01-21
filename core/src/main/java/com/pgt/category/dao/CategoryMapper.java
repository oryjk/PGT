package com.pgt.category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.category.bean.Category;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.utils.PaginationBean;

/**
 * Created by carlwang on 11/13/15.
 */
@Component
public interface CategoryMapper extends SqlMapper {

    Integer createCategory(Category category);

    Integer updateCategory(Category category);

    Integer deleteCategory(Integer categoryId);

    Category queryCategory(Integer categoryId);

    Integer queryCategoryByCode(String code);

    Category queryParentCategoryByProductId(Integer productId);

    List<Category> queryAllParentCategories();

    List<Category> queryCategories(@Param("category") Category category,
                                   @Param("paginationBean") PaginationBean paginationBean);

    CategoryHierarchy queryCategoryHierarchy(@Param("categoryId")Integer categoryId);
    
    Integer getHelpCategoryCount();

    List<Category> querySubCategories(@Param("rootCategoryId")Integer rootCategoryId);

    Integer queryCategoryTotal(Category category);

    List<Category> queryRootCategories();

    Category queryCategoryByTenderId(@Param("tenderId")Integer tenderId);


}
