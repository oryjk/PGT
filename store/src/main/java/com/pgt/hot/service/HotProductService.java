package com.pgt.hot.service;

import com.pgt.category.bean.CategoryType;
import com.pgt.hot.bean.HotBean;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductCategoryRelation;

import java.util.List;

/**
 * Created by carlwang on 11/3/15.
 */

public interface HotProductService {


    Integer createProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    Integer updateProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    List<Product> queryProductCategoryRelationProducts(CategoryType categoryType);

    ProductCategoryRelation queryProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    List<Product> queryHotProductByCategoryId(Integer categoryId);

    Integer deleteProductCategoryRelation(ProductCategoryRelation productCategoryRelation);


}
