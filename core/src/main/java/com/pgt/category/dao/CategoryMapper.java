package com.pgt.category.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.utils.PaginationBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by carlwang on 11/13/15.
 */
@Component
public interface CategoryMapper extends SqlMapper {

	void createCategory(Category category);

	void updateCategory(Category category);

	void deleteCategory(Integer categoryId);

	Category queryCategory(Integer categoryId);

	Category queryParentCategoryByProductId(Integer productId);

	List<Category> queryAllCategories();

	List<Category> queryCategories(@Param("category") Category category,
								   @Param("paginationBean") PaginationBean paginationBean);

}
