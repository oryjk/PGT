package com.pgt.search.service;

import com.pgt.search.bean.ESFilter;
import com.pgt.search.bean.ESSort;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;

import java.util.List;

/**
 * Created by carlwang on 1/27/16.
 */
public class CategorySearchEngineService extends AbstractSearchEngineService {
    @Override
    public void index() {

    }

    @Override
    public void update(Integer id) {

    }

    @Override
    public void initialIndex() {

    }

    @Override
    protected void buildSort(SearchRequestBuilder searchRequestBuilder, List<ESSort> esSorts) {

    }

    @Override
    protected void buildFilter(BoolQueryBuilder boolQueryBuilder, ESFilter esFilter) {

    }
}
