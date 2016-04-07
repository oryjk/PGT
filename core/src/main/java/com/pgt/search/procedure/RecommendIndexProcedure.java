package com.pgt.search.procedure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.home.bean.RecommendedProduct;
import com.pgt.home.service.RecommendedProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 4/7/16.
 */
@Service("recommendIndexProcedure")
public class RecommendIndexProcedure implements IndexProcedure {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendIndexProcedure.class);
    @Autowired
    private RecommendedProductService recommendedProductService;

    @Override
    public List<Pair<String, String>> buildSourceList() {

        List<RecommendedProduct> recommendedProducts = recommendedProductService.queryAllRecommendedProduct(null);
        if (CollectionUtils.isEmpty(recommendedProducts)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Pair<String, String>> recommendList = new ArrayList<>();
        recommendedProducts.stream().forEach(recommendedProduct ->
        {
            try {
                Pair<String, String> recommendedProductPair = Pair.of(String.valueOf(recommendedProduct.getRecommendedProductId()), mapper
                        .writeValueAsString(recommendedProduct));
                recommendList.add(recommendedProductPair);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        LOGGER.debug("The recommend list size is {}.", recommendList.size());
        return recommendList;
    }
}
