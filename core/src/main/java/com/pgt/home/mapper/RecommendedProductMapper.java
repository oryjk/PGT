package com.pgt.home.mapper;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.home.bean.RecommendedProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carlwang on 3/29/16.
 * Used in domain home page to recommend products
 */
@Repository
public interface RecommendedProductMapper extends SqlMapper {
    void createRecommendedProduct(RecommendedProduct recommendedProduct);

    void updateRecommendedProduct(RecommendedProduct recommendedProduct);

    void deleteRecommendedProduct(Integer recommendedProductId);

    List<RecommendedProduct> queryAllRecommendedProduct(RecommendedProduct recommendedProduct);
}
