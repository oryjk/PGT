package com.pgt.user.service;

import com.pgt.search.service.ESSearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by carlwang on 12/10/15.
 */

@Service
public class ESSearchProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESSearchProductService.class);
    @Resource(name = "esSearchService")
    private ESSearchService mESSearchService;

    public List<SearchHit> queryProducts(final List<Integer> pProductIds) {
        if (CollectionUtils.isEmpty(pProductIds)) {
            return Collections.emptyList();
        }
        String[] productIds = new String[pProductIds.size()];
        for (int i = 0; i < pProductIds.size(); i++) {
            productIds[i] = String.valueOf(pProductIds.get(i));
        }
        SearchResponse searchResponse = getESSearchService().findProducts(productIds);
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits != null && ArrayUtils.isNotEmpty(searchHits.getHits())) {
            SearchHit[] searchHitArray = searchHits.getHits();
            List<SearchHit> products = new ArrayList<>(Arrays.asList(searchHitArray));
            LOGGER.debug("Found {} products from es index.", products.size());
            return products;
        }
        return Collections.emptyList();
    }

    public ESSearchService getESSearchService() {
        return mESSearchService;
    }

    public void setESSearchService(final ESSearchService pESSearchService) {
        mESSearchService = pESSearchService;
    }
}
