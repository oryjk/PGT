package com.pgt.category.service;

import com.pgt.category.bean.Category;
import com.pgt.category.dao.CategoryMapper;
import com.pgt.utils.PaginationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by carlwang on 11/13/15.
 */
@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public String createCategory(Category category) {
        categoryMapper.createCategory(category);
        return category.getName();
    }

    @Override
    public String updateCategory(Category category) {
        categoryMapper.updateCategory(category);
        return category.getName();
    }

    @Override
    public Integer deleteCategory(Integer categoryId) {
        categoryMapper.deleteCategory(categoryId);
        return categoryId;
    }

    @Override
    public Category queryCategory(Integer categoryId) {
        Category category = categoryMapper.queryCategory(categoryId);
        return category;
    }

    @Override
    public Category queryParentCategoryByProductId(Integer productId) {
        return categoryMapper.queryParentCategoryByProductId(productId);
    }

    @Override
    public Category queryRootCategoryByProductId(Integer productId) {
        return null;
    }

    @Override
    public List<Category> queryAllCategories() {
        List<Category> categories = categoryMapper.queryAllCategories();
        return categories;
    }

    @Override
    public List<Category> queryCategories(Category category, PaginationBean paginationBean) {
        List<Category> categories = categoryMapper.queryCategories(category, paginationBean);
        return categories;
    }

    public CategoryMapper getCategoryMapper() {
        return categoryMapper;
    }

    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
}
