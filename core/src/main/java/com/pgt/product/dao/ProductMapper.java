package com.pgt.product.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.category.bean.CategoryType;
import com.pgt.hot.bean.HotSearch;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductCategoryRelation;
import com.pgt.product.bean.ProductMedia;
import com.pgt.search.bean.SearchPaginationBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by carlwang on 10/28/15.
 */
@Component
public interface ProductMapper extends SqlMapper {

    Product queryProduct(@Param("productId") int productId);

    List<Product> queryProducts(SearchPaginationBean searchPaginationBean);

    List<Product> queryAllProducts(Integer stock);

    Integer createProduct(Product product);

    Integer updateProduct(Product product);

    void deleteProduct(@Param("productId") Integer productId);

    void deleteProducts(List<String> productIds);

    List<ProductMedia> queryProductMedias(@Param("productId") int pProductId);

    List<ProductMedia> queryProductHeroMedias(@Param("productId") int pProductId);

    List<ProductMedia> queryProductMainMedias(@Param("productId") int pProductId);

    ProductMedia queryProductThumbnailMedias(@Param("productId") int pProductId);
    ProductMedia queryProductMobileDetailMedias(@Param("productId") int pProductId);

    ProductMedia queryProductExpertMedia(@Param("productId") int pProductId);

    ProductMedia queryProductAdvertisementMedia(@Param("productId") int pProductId);

    ProductMedia queryProductFrontMedia(@Param("productId") int pProductId);

    List<Product> queryProducts(@Param("categoryId") String categoryId, @Param("inStock") Boolean inStock);

    void createProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    void updateProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    ProductCategoryRelation queryProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    List<Product> queryProductsByCategoryIdAndCategoryType(@Param("categoryId") Integer categoryId, @Param("isHot") Boolean isHot);

    List<Product> queryProductCategoryRelationProducts(CategoryType categoryType);

    void deleteProductCategoryRelation(ProductCategoryRelation productCategoryRelation);

    void deleteProductCategoryRelationByProductId(@Param(value = "productId") Integer productId);


    List<HotSearch> queryAllHotsearch();

    List<Product> queryProductByIds(List<Integer> pSupposeProductIds);

    Integer queryProductTotal(SearchPaginationBean searchPaginationBean);
}
