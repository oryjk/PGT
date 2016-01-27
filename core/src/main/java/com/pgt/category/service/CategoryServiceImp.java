package com.pgt.category.service;

import java.util.List;

import com.pgt.base.service.TransactionService;
import com.pgt.media.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import com.pgt.category.bean.Category;
import com.pgt.category.dao.CategoryMapper;
import com.pgt.common.bean.Media;
import com.pgt.common.dao.MediaMapper;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.CategoryHierarchy;
import com.pgt.utils.PaginationBean;

/**
 * Created by carlwang on 11/13/15.
 */
@Service
public class CategoryServiceImp extends TransactionService implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImp.class);
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private MediaService mediaService;

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
    public String createCategory(Category category, Integer mediaId) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            LOGGER.debug("Begin create category.");
            categoryMapper.createCategory(category);
            Media media = mediaService.findMedia(mediaId, MediaType.category);
            if (!ObjectUtils.isEmpty(media)) {
                media.setReferenceId(category.getId());
                mediaService.updateMedia(media);
            }

            LOGGER.debug("End create category.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }

        return String.valueOf(category.getId());
    }

    @Override
    public Integer updateCategory(Category category) {
        Integer count = categoryMapper.updateCategory(category);
        return count;
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
    public Integer queryCategoryByCode(String code) {
        return categoryMapper.queryCategoryByCode(code);
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
    public List<Category> queryRootCategories() {
        return categoryMapper.queryRootCategories();
    }

    @Override
    public List<Category> queryRootTenderCategories() {
        LOGGER.debug("Begin to query root tender category.");
        return categoryMapper.queryRootTenderCategories();
    }

    @Override
    public List<Category> queryCategories(Category category, PaginationBean paginationBean) {
        List<Category> categories = categoryMapper.queryCategories(category, paginationBean);
        return categories;
    }

    @Override
    public Integer queryCategoryTotal(Category category) {

        return categoryMapper.queryCategoryTotal(category);
    }

    @Override
    public CategoryHierarchy queryCategoryHierarchy(Integer categoryId) {
        LOGGER.debug("The category is {}.", categoryId);
        return categoryMapper.queryCategoryHierarchy(categoryId);

    }

    @Override
    public Integer getHelpCategoryCount() {
        return categoryMapper.getHelpCategoryCount();
    }

    @Override
    public List<Category> querySubCategories(Integer rootCategoryId) {
        return categoryMapper.querySubCategories(rootCategoryId);
    }

    public CategoryMapper getCategoryMapper() {
        return categoryMapper;
    }

    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
}
