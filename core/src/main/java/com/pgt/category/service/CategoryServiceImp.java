package com.pgt.category.service;

import com.pgt.category.bean.Category;
import com.pgt.category.dao.CategoryMapper;
import com.pgt.common.bean.Media;
import com.pgt.common.dao.MediaMapper;
import com.pgt.media.bean.MediaType;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by carlwang on 11/13/15.
 */
@Service
public class CategoryServiceImp implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImp.class);
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MediaMapper mediaMapper;

    @Override
    public String createCategory(Category category) {
        LOGGER.debug("Begin create category.");
        categoryMapper.createCategory(category);
        Integer rootCategory = category.getId();
        Media media = category.getFrontMedia();
        if (!ObjectUtils.isEmpty(media)) {
            LOGGER.debug("Create category media,the category id is {}.", rootCategory);
            media.setReferenceId(rootCategory);
            media.setType(MediaType.category.toString());
            mediaMapper.createMedia(media);
        }
        LOGGER.debug("The category id is {}.", rootCategory);
        category.setId(rootCategory);
        List<Category> subCategories = category.getChildren();
        if (!ObjectUtils.isEmpty(subCategories)) {
            subCategories.stream().forEach(subCategory -> {
                subCategory.setParent(category);
                createCategory(subCategory);
            });
        }
        LOGGER.debug("End create category.");
        return String.valueOf(rootCategory);
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
    public List<Category> queryAllParentCategories() {
        List<Category> categories = categoryMapper.queryAllParentCategories();
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
