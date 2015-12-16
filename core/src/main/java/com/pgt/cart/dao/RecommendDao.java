package com.pgt.cart.dao;

import com.pgt.base.mapper.SqlMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 12/3/2015.
 */
@Repository(value = "recommendDao")
public interface RecommendDao extends SqlMapper {

	List<Map<String, Object>> countCategoryProductMapping(List<Integer> pRecentBrowsedProductIds);

	List<Integer> queryAvailableProductIds(@Param("categoryId") int pCategoryId, @Param("includeOutOfStock") boolean pIncludeOutOfStock);

	List<Integer> queryCategoryIdsExclude(List<String> pRecommendedCategoryIds);

}
