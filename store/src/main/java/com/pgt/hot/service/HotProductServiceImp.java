package com.pgt.hot.service;

import com.pgt.category.bean.CategoryType;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductCategoryRelation;
import com.pgt.product.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carlwang on 11/3/15.
 */
@Service
public class HotProductServiceImp implements HotProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer createProductCategoryRelation(ProductCategoryRelation productCategoryRelation) {
        ProductCategoryRelation productCategoryRelationResult = queryProductCategoryRelation(productCategoryRelation);
        if (productCategoryRelationResult == null) {
            productMapper.createProductCategoryRelation(productCategoryRelation);
        }
        return productCategoryRelation.getProductId();
    }

    @Override
    public Integer updateProductCategoryRelation(ProductCategoryRelation productCategoryRelation) {
        productMapper.updateProductCategoryRelation(productCategoryRelation);
        return productCategoryRelation.getProductId();
    }

    @Override
    public List<Product> queryProductCategoryRelationProducts(CategoryType categoryType) {
        return productMapper.queryProductCategoryRelationProducts(categoryType);
    }

    @Override
    public ProductCategoryRelation queryProductCategoryRelation(ProductCategoryRelation productCategoryRelation) {
        return productMapper.queryProductCategoryRelation(productCategoryRelation);
    }

    @Override
    public List<Product> queryHotProductByCategoryId(Integer categoryId) {
        return productMapper.queryProductsByCategoryIdAndCategoryType(categoryId, true);
    }

    @Override
    public Integer deleteProductCategoryRelation(ProductCategoryRelation productCategoryRelation) {
        productMapper.deleteProductCategoryRelation(productCategoryRelation);
        return null;
    }


    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
}
